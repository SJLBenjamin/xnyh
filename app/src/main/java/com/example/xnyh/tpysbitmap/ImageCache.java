package com.example.xnyh.tpysbitmap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 * 图片缓存机制.lru最少最近使用
 * */
public class ImageCache {
    private static ImageCache mImageCache;
    int anInt = 123;
    LruCache<String, Bitmap> lruCache;
    private Set<WeakReference<Bitmap>> weakReferences;

    private ImageCache(){}

 public   static ImageCache getInstance(){
        if(mImageCache==null){
            synchronized (ImageCache.class){
                if(mImageCache==null){
                    mImageCache = new ImageCache();

                }
            }
        }
        return mImageCache;
    }


    public void init(Context context) {
        //对象复用池,创建bitMap的时候查看对象池中是否还有对象,如果有直接复用
        weakReferences = Collections.synchronizedSet(new HashSet<WeakReference<Bitmap>>());

        // 内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();//返回M单位
        //设置内存大小为可用内存的八分之一  m*1024==KB   kb*1024==字节   除以8表示用申请大小为可用内存的八分之一

        lruCache = new LruCache<String, Bitmap>(memoryClass * 1024 * 1024 / 8) {
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//如果大于4.4
                    return value.getAllocationByteCount();
                }
                return value.getByteCount();
            }

            @Override
            protected void entryRemoved(boolean evicted, @NonNull String key, @NonNull Bitmap oldValue, @Nullable Bitmap newValue) {
                // 3.0 bitmap 缓存 native
                // <8.0  bitmap 缓存 java
                // 8.0 native  而native层不会自动回收,所以需要手动回收
                if (oldValue.isMutable()) {//如果还可以复用,那么存起来
                    //此处有引用队列,用法:如果弱引用修饰的对象将要被回收了,那么这个弱引用对象就会放到引用队列中,通过此队列去判断弱引用修饰的对象是否被回收
                    weakReferences.add(new WeakReference<Bitmap>(oldValue,getReferenceQueue()));
                } else {//直接回收
                    oldValue.recycle();
                }
            }
        };
    }

    boolean isShoutDown =false;
    /*
    * 获取引用队列对象
    * */
    ReferenceQueue referenceQueue;
    ReferenceQueue<Bitmap> getReferenceQueue(){
        if(referenceQueue==null){
            referenceQueue = new ReferenceQueue<Bitmap>();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isShoutDown){//设置终止标识,是否关闭此线程
                try {
                    //此方法是获取引用队列中的元素,是阻塞方法,所以需要try catch,此处的元素是弱引用对象
                    Reference<? extends Bitmap > remove = referenceQueue.remove();
                    Bitmap bitmap = remove.get();//然后获取引用队列中的元素,即虚引用修饰的对象
                    if(bitmap!=null&!bitmap.isRecycled()){//如果bitMap没被回收的话
                            bitmap.recycle();//进行回收
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }
        }).start();
        return referenceQueue;
    }

    /**
     * 查找复用池中是否有满足条件的可复用对象
     * <p>
     * 3.0 之前不能复用
     * 3.0-4.4 宽高一样，inSampleSize = 1
     * 4.4 只要内存小于等于就行了
     *
     * @param w             宽
     * @param h             高
     * @param intSampleSize 缩放比例
     * @return 返回值 满足条件的可复用对象
     */
    Bitmap getResult(int w, int h, int intSampleSize) {
        //如果当前版本小于3.0.那么直接返回null
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return null;
        }
        Bitmap result = null;
        //从复用池遍历
        Iterator<WeakReference<Bitmap>> iterator = weakReferences.iterator();
        while (iterator.hasNext()) {
            result = iterator.next().get();
            if (result != null) {//需要判断是否为null
                if (isCanReUse(result, w, h, intSampleSize)) {//如果可以复用
                    //从复用池中移除此对象
                    iterator.remove();
                    //返回复用对象
                    return result;
                }
            }else {//如果都为null了,那么从复用池中移除此对象
                iterator.remove();
            }
        }
        return result;
    }

    //判断是否满足条件
    boolean isCanReUse(Bitmap bitmap, int w, int h, int intSampleSize) {
        //如果版本在3.0-4.4之间,那么需要判断宽高比例
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB & Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getWidth() == w & bitmap.getHeight() == h & intSampleSize == 1;
        }
        //如果缩放比例大于1
        if(intSampleSize>1){
            w/=intSampleSize;
            h/=intSampleSize;
        }
        //求出bitMap占有的内存
        int byteCount =w*h*getBytesPerPixel(bitmap.getConfig());
        //bitmap.getAllocationByteCount系统分配的内存
        //bitmap.getByteCount()图片内存,图片内存小于等于系统分配的内存
        return byteCount<=bitmap.getAllocationByteCount();
    }

    /**
     * 通过像素格式计算每一个像素占用多少字节
     *
     * @param config
     * @return
     */
    private int getBytesPerPixel(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        }
        return 2;
    }



    /**
     * 把bitmap 放入内存
     *
     * @param key
     * @param bitmap
     */
    public void putBitmap2Memory(String key, Bitmap bitmap) {
        lruCache.put(key, bitmap);
    }

    /**
     * 从内存中取出bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getBitmapFromMemory(String key) {
        return lruCache.get(key);
    }
}


