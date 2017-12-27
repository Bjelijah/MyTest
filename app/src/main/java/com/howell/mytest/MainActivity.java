package com.howell.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.howell.p.mylibrary.MyTest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/15.
 */

public class MainActivity extends Activity{

    @BindView(R.id.btn_flash)Button mflaskBtn;
    @BindView(R.id.btn_view)Button mViewBtn;
    @BindView(R.id.btn_retrofit) Button mRetBtn;
    @BindView(R.id.btn_test) Button mTestBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_flash) void clickFlash(){
        startActivity(new Intent(this,Flash2Activity.class));
    }

    @OnClick(R.id.btn_view)void clickView(){
        startActivity(new Intent(this,CameraViewActivity.class));
    }

    @OnClick(R.id.btn_retrofit)void clickRetrofit(){
        startActivity(new Intent(new Intent(this,RxActivity.class)));
    }

    @OnClick(R.id.btn_test) void clickTest(){
        Log.i("123","test ="+ MyTest.justTest());
    }



}
