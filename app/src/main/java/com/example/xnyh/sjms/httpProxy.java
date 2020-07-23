package com.example.xnyh.sjms;

import javax.security.auth.callback.Callback;

//代理类
public class httpProxy implements IHttp {

    static  httpProxy mHttpProxy;

    //设置单例
    public static httpProxy getInstance(){
        if(mHttpProxy==null){
            synchronized (httpProxy.class){
                if(mHttpProxy==null){
                    mHttpProxy =new httpProxy();
                }
            }
        }
        return mHttpProxy;
    }


    private httpProxy(){}


   static  IHttp iHttp;
    public static void init(IHttp http){
        //多态,接口接收对象
        iHttp = http;
    }


    @Override
    public void post(String url, Callback callback) {
        iHttp.post(url,callback);
    }

    @Override
    public void get(String url, Callback callback) {
        iHttp.get(url,callback);
    }

}
