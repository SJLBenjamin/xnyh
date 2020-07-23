package com.example.xnyh.tpysbitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.xnyh.R;

public class BitMapAdapter extends RecyclerView.Adapter<BitMapAdapter.BitMapHolder> {
    Context mContext;
    class BitMapHolder extends RecyclerView.ViewHolder {
        ImageView mBitmap;
        public BitMapHolder(@NonNull View itemView) {
            super(itemView);
            mBitmap = (ImageView) itemView.findViewById(R.id.iv_bitmap);
        }
    }

    public BitMapAdapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public BitMapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bitmap_layout, parent, false);
        return  new BitMapHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BitMapHolder holder, int position) {
        //原始加载
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_mv_p);
        //第一种优化,压缩加载
        //Bitmap bitmap = BitmapYaSuo.resizeBitmap(this, R.mipmap.icon_mv_p, 80, 80, false);
        //第二种优化,获取最少最近算法池中的bitmap
        Bitmap bitmapFromMemory = ImageCache.getInstance().getBitmapFromMemory(String.valueOf(position));
        if(bitmapFromMemory==null){//如果最少最小算法池中bitmap不存在,那么查看复用池中有没有对应的bitmap对象
            bitmapFromMemory = ImageCache.getInstance().getResult(80, 80, 1);//从复用池中查看有没有可用对象
            //然后创建需要用的一个bitmap,此处不需要判断result是否为null,是因为resizeBitmap方法中decodeResource方法,如果传入的bitmap为null,则会自动创建一个
            bitmapFromMemory = BitmapYaSuo.resizeBitmap(mContext, R.mipmap.icon_mv_p, 80, 80, false, bitmapFromMemory);
        }
        holder.mBitmap.setImageBitmap(bitmapFromMemory);
    }


    @Override
    public int getItemCount() {
        return 1000;
    }

}
