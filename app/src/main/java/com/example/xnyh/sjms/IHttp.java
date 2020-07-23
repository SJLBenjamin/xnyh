package com.example.xnyh.sjms;

import javax.security.auth.callback.Callback;

//代理和被代理类统一接口
public interface IHttp {
    public void post(String url, Callback callback);
    public void get(String url, Callback callback);

}
