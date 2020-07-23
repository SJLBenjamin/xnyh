package com.example.xnyh.tpysbitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/*
* 图片压缩类,等比例压缩
*
* */
public class BitmapYaSuo {

static String TAG ="BitmapYaSuo";

    /**
     * 图片重新设置大小
     *
     * @param context  上下文
     * @param id   bitMap资源id
     * @param maxW  显示区域的宽
     * @param maxH  显示区域的高
     * @param hasAlpha  是否需要透明度
     * @param reusable  复用的bitMap对象
     * @return
     */
    public static Bitmap resizeBitmap(Context context,int id,int maxW, int maxH,boolean hasAlpha,Bitmap reusable){
        Resources resources = context.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置只测量图片大小,这,设置为true后，再去解析，就只解析 out 参数,不会返回bitMap
        options.inJustDecodeBounds =true;

        //解析资源,C语言写法
      BitmapFactory.decodeResource(resources, id, options);
      //得到宽度
        int outWidth = options.outWidth;
        //得到高度
        int outHeight = options.outHeight;

        //进行压缩,得到压缩比例
        options.inSampleSize= yasuo(outWidth, outHeight, maxW, maxH);

        //如果不需要设置透明度,那么改变编码
        if(!hasAlpha){
            options.inPreferredConfig= Bitmap.Config.RGB_565;
        }

        //重新设置
        options.inJustDecodeBounds =false;

        // 复用, inMutable 为true 表示图片容易改变,需要这样设置才能实现图片复用
        options.inMutable = true;
        //设置复用的bitMap对象,如果此对象为null的话,那么
        options.inBitmap = reusable;

        return BitmapFactory.decodeResource(resources, id, options);
    }

    //压缩方法
   static int yasuo(int outWidth, int outHeight,int maxWidth,int maxHeight){
        int yasuo =1;
        //如果最大宽高都小于原图片,那么先压缩为原先的二分之一
        if(maxHeight<outHeight&maxWidth<outWidth){
            yasuo=2;
            while (maxHeight<outHeight/yasuo&maxWidth<outWidth/yasuo){
                yasuo=yasuo*2;
            }
        }
       Log.d(TAG,"压缩比例==="+yasuo);
       return yasuo;
   }


}
