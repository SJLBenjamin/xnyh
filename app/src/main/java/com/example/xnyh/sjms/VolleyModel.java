package com.example.xnyh.sjms;

import javax.security.auth.callback.Callback;

//被代理模块
public class VolleyModel implements IHttp {

    static VolleyModel mVolleyModel;
    public static VolleyModel getInstance(){
        if(mVolleyModel==null){
            synchronized (httpProxy.class){
                if(mVolleyModel==null){
                    mVolleyModel =new VolleyModel();
                }
            }
        }
        return mVolleyModel;
    }

    @Override
    public void post(String url, Callback callback) {
        System.out.println("VolleyModel post");
    }

    @Override
    public void get(String url, Callback callback) {
        System.out.println("VolleyModel get");
    }

}
