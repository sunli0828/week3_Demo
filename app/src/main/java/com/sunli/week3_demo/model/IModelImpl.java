package com.sunli.week3_demo.model;

import com.sunli.week3_demo.callback.ICallback;
import com.sunli.week3_demo.callback.OkCallback;
import com.sunli.week3_demo.uils.OkHttpUtils;

public class IModelImpl implements IModel {
    @Override
    public void getResponseData(String urlStr, String params, Class clazz, final ICallback iCallback) {
        OkHttpUtils.getOkInstance().getEnqueue(urlStr, new OkCallback() {
            @Override
            public void success(Object obj) {
                iCallback.setData(obj);
            }

            @Override
            public void failed(Exception e) {
                iCallback.setData(e.getMessage());
            }
        }, clazz);
    }
}
