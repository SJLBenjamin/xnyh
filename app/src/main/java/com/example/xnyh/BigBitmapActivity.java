package com.example.xnyh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.xnyh.tpysbitmap.BigBitMapLoadView;

import java.io.IOException;
import java.io.InputStream;

/*
* 大图加载的Activity
* */
public class BigBitmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_bitmap);

         BigBitMapLoadView imBigBitmap = (BigBitMapLoadView) findViewById(R.id.im_big_bitmap);

        InputStream is = null;
        try {
            is = getAssets().open("big.png");
//            is = getAssets().open("world.jpg");
            imBigBitmap.setImage(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
