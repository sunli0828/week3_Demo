package com.sunli.week3_demo;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sunli.week3_demo.adapter.NewsAdapter;
import com.sunli.week3_demo.bean.NewsBean;
import com.sunli.week3_demo.presenter.IPresenterImpl;
import com.sunli.week3_demo.uils.ApiUtils;
import com.sunli.week3_demo.view.IView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private ImageView main_QQ_icon;
    private TextView mian_QQ_nickname;
    private Button main_QQ_btnLogin;
    private IPresenterImpl iPresenter;
    private int i = 0;
    private NewsAdapter adapter;
    List<NewsBean.ResultBean.DataBean> list = new ArrayList<>();
    private XRecyclerView xRecyclerView;
    private int TYPE_NEWS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPresenter = new IPresenterImpl(this);

        initView();
    }

    private void initView() {
        main_QQ_icon = findViewById(R.id.main_QQ_icon);
        mian_QQ_nickname = findViewById(R.id.mian_QQ_nickname);
        main_QQ_btnLogin = findViewById(R.id.main_QQ_btnLogin);
        xRecyclerView = findViewById(R.id.xRecyclerView);
        main_QQ_btnLogin.setOnClickListener(this);
        main_QQ_icon.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);

        adapter = new NewsAdapter(MainActivity.this);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                list.clear();
                i = 0;

                xRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                xRecyclerView.loadMoreComplete();
            }
        });
        ckPermission(TYPE_NEWS);
        //对头像放大缩小
        ScaleAnimation animation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f);
        animation.setDuration(5000);
        main_QQ_icon.startAnimation(animation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    private void ckPermission(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
            iPresenter.startRequset(ApiUtils.TYPE_NEWS, null, NewsBean.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_QQ_btnLogin:
                UMShareAPI umShareAPI = UMShareAPI.get(MainActivity.this);
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        String name = map.get("screen_name");
                        String img = map.get("profile_image_url");
                        //Log.i("sl", "name is "+name);
                        //Log.i("sl",img);
                        mian_QQ_nickname.setText(name);
                        Glide.with(MainActivity.this).load(img).into(main_QQ_icon);
                        //对头像放大缩小
                        ScaleAnimation animation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f);
                        animation.setDuration(5000);
                        main_QQ_icon.startAnimation(animation);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void showResponseData(Object data) {
        NewsBean newsBean = (NewsBean) data;
        if (newsBean.getReason().equals("成功的返回")) {
            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            List<NewsBean.ResultBean.DataBean> list = newsBean.getResult().getData();
            adapter.addDatas(list);
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPresenter != null) {
            iPresenter = null;
        }
    }
}
