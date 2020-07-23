package com.example.xnyh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import java.lang.ref.WeakReference;


/*
* handler内存泄露,handler延迟方法
* */
public class HanlderActivity extends AppCompatActivity {
  String TAG ="HanlderActivity";
    private Button btHaha;
    int i=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanlder);
        btHaha = (Button) findViewById(R.id.bt_haha);
    }

    //内存泄露第二种解决办法
    static class imRunnable implements  Runnable{

        WeakReference<HanlderActivity> mActivity;

         public  imRunnable(HanlderActivity activity){
             mActivity =new WeakReference<HanlderActivity>(activity);
         }

        @Override
        public void run() {
            //mActivity.get().btHaha.setText("1234");
            if(mActivity.get()!=null){
                Log.d( mActivity.get().TAG,"弱引用i==="+mActivity.get().i);
            }else {
                Log.d(mActivity.get().TAG,"清空了");
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        //虚引用
        handler.postDelayed(new imRunnable(this),5000);

        //new Runnable内部类被外部类引用,外部类--->new Runnable--->HanlderActivity导致HanlderActivity无法被正常回收
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btHaha.setText("1234");
                            Log.d(TAG,"i==="+i);
                        }
                    });
                }
            },5000);
    }


   //WeakReference<Handler>  handlerWeakReference= new WeakReference<Handler>( new Handler());

    Handler handler = new Handler();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"HanlderActivity onDestroy");

  //      Runtime.getRuntime().gc();
//        System.runFinalization();
 //       System.gc();

        //第一种解决办法,移除handler所有对象
       // handler.removeCallbacksAndMessages(null);
    }
}
