package com.sunli.week3_demo.presenter;

import com.sunli.week3_demo.bean.NewsBean;
import com.sunli.week3_demo.callback.ICallback;
import com.sunli.week3_demo.model.IModelImpl;
import com.sunli.week3_demo.view.IView;

public class IPresenterImpl implements IPresenter {

    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void startRequset(String urlStr, String params, Class clazz) {
        iModel.getResponseData(urlStr, params, clazz, new ICallback<NewsBean>() {
            @Override
            public void setData(NewsBean data) {
                iView.showResponseData(data);
            }
        });
    }

    public void onDetach() {
        if (iView != null) {
            iView = null;
        }
        if (iModel != null) {
            iModel = null;
        }
    }
}
