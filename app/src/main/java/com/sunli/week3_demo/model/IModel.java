package com.sunli.week3_demo.model;

import com.sunli.week3_demo.callback.ICallback;

public interface IModel {
    void getResponseData(String urlStr, String params, Class clazz, ICallback iCallback);
}
