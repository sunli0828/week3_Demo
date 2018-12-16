package com.sunli.weekend_demo.utils;


import android.os.Handler;
import android.os.Looper;

import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils okIntance;

    private Handler okHandler = new Handler(Looper.getMainLooper());

    public static OkHttpUtils getOkIntance() {
        if (okIntance == null) {
            synchronized (OkHttpUtils.class) {
                if (null == okIntance) {
                    okIntance = new OkHttpUtils();
                }
            }
        }
        return okIntance;
    }

    public OkHttpUtils() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    }
}
