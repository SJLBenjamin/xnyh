package com.example.xnyh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.xnyh.tpysbitmap.BitMapAdapter;
import com.example.xnyh.tpysbitmap.ImageCache;


public class BitmapActivity extends AppCompatActivity {

    private RecyclerView rvBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ImageCache.getInstance().init(this);
        rvBitmap = (RecyclerView) findViewById(R.id.rv_bitmap);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);//竖直方向
        rvBitmap.setLayoutManager(layoutManager);
        rvBitmap.setAdapter(new BitMapAdapter(this));
    }

}
