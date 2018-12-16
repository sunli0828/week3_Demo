package com.sunli.weekend_demo.model;

import com.sunli.weekend_demo.callback.ICallBack;

public interface IModel {
    void getResponseData(String urlStr, String params, Class clazz, ICallBack iCallBack);
}
