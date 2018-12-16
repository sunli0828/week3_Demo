package com.sunli.weekend_demo.presenter;

import com.sunli.weekend_demo.callback.ICallBack;
import com.sunli.weekend_demo.model.IModelImpl;
import com.sunli.weekend_demo.view.IView;

public class IPresenterImpl implements IPresenter {

    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void startRequest(String urlStr, String params, Class clazz) {
        iModel.getResponseData(urlStr, params, clazz, new ICallBack() {
            @Override
            public void setData(Object data) {
                iView.showResponseData(data);
            }
        });
    }

    public void onDetach() {
        if (iModel != null) {
            iModel = null;
        }
        if (iView != null) {
            iView = null;
        }
    }
}
