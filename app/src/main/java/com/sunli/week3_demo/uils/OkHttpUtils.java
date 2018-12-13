package com.sunli.week3_demo.uils;

import android.os.Handler;
import android.os.Looper;

import com.sunli.week3_demo.callback.OkCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils okInstance;

    private OkHttpClient okClient;

    private Handler okHandler = new Handler(Looper.getMainLooper());

    public static OkHttpUtils getOkInstance() {
        if (okInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (null == okInstance) {
                    okInstance = new OkHttpUtils();
                }
            }
        }
        return okInstance;
    }

    public OkHttpUtils() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public void getEnqueue(final String url, final OkCallback callBack, final Class clazz) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Call call = okClient.newCall(request);
        call.enqueue(new Callback() {
            //网络请求连接失败
            @Override
            public void onFailure(Call call, final IOException e) {
                okHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            //网络请求连接成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(result, clazz);
                okHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.success(o);
                    }
                });
            }
        });
    }

}