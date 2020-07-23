package com.example.xnyh.sjms;

import android.util.Log;

import javax.security.auth.callback.Callback;

//被代理模块
public class OKHttpsModel implements IHttp{
    static OKHttpsModel mOKHttpsModel;
    public static OKHttpsModel getInstance(){
        if(mOKHttpsModel==null){
            synchronized (httpProxy.class){
                if(mOKHttpsModel==null){
                    mOKHttpsModel =new OKHttpsModel();
                }
            }
        }
        return mOKHttpsModel;
    }

    @Override
    public void post(String url, Callback callback) {
        System.out.println("OKHttpsModel post");
    }

    @Override
    public void get(String url, Callback callback) {
        System.out.println("OKHttpsModel get");
    }
}
