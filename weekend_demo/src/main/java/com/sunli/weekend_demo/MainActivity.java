package com.sunli.weekend_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sunli.weekend_demo.presenter.IPresenterImpl;
import com.sunli.weekend_demo.view.IView;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private IPresenterImpl iPresenter;
    private Button main_btn_multiple, main_btn_sale, main_btn_price, main_btn_choose;
    private ImageView main_image_change;
    private XRecyclerView main_xRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPresenter = new IPresenterImpl(this);
        initView();
    }

    private void initView() {
        main_image_change = findViewById(R.id.main_image_change);
        main_btn_multiple = findViewById(R.id.main_btn_multiple);
        main_btn_sale = findViewById(R.id.main_btn_sale);
        main_btn_price = findViewById(R.id.main_btn_price);
        main_btn_choose = findViewById(R.id.main_btn_choose);
        main_xRecyclerView = findViewById(R.id.main_xRecyclerview);

        main_image_change.setOnClickListener(this);
        main_btn_multiple.setOnClickListener(this);
        main_btn_sale.setOnClickListener(this);
        main_btn_price.setOnClickListener(this);
        main_btn_choose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_image_change:
                break;
            case R.id.main_btn_multiple:
                break;
            case R.id.main_btn_sale:
                break;
            case R.id.main_btn_price:
                break;
            case R.id.main_btn_choose:
                break;
            default:
                break;
        }
    }

    @Override
    public void showResponseData(Object data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
