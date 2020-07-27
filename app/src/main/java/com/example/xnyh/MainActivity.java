package com.example.xnyh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import com.example.xnyh.sjms.OKHttpsModel;
import com.example.xnyh.sjms.VolleyModel;
import com.example.xnyh.sjms.httpProxy;
import com.example.xnyh.tpysbitmap.BigBitMapLoadView;
import com.example.xnyh.tpysbitmap.BitmapYaSuo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageView ivYasuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivYasuo = (ImageView) findViewById(R.id.iv_yasuo);
        //原始加载
       //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_mv);
        //第一种优化,压缩加载
        //Bitmap bitmap = BitmapYaSuo.resizeBitmap(this, R.mipmap.icon_mv_p, 80, 80, false);
        //ivYasuo.setImageBitmap(bitmap);

        //初始化OkHttps网络库,网络设计模式,静态代理
        httpProxy.init(OKHttpsModel.getInstance());

        //初始化Volley网络库
        //httpProxy.init(VolleyModel.getInstance());

        //startActivity(new Intent(MainActivity.this,HanlderActivity.class));
        //startActivity(new Intent(MainActivity.this,BitmapActivity.class));
        startActivity(new Intent(MainActivity.this, BigBitmapActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
