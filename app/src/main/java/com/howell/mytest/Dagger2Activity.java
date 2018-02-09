package com.howell.mytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.dagger.DaggerAppComponent;
import com.example.dagger.api.ActivityModule;
import com.example.dagger.api.DaggerActivityComponent;
import com.example.dagger.sample1.Car;
import com.example.viewmodel.BaseViewModel;
import com.example.viewmodel.dagger2.DaggerViewModel;


import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;



/**
 * Created by Administrator on 2018/2/2.
 */

public class Dagger2Activity extends BaseActivity {


    Car mCar;

    @Inject
    Car mCar2;


    @Override
    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mCar = APP.Companion.get(this)
                .getAppComponent()
                .getCar();

//        APP.Companion.get(this)
//                .getAppComponent()
//                .addSub(new ActivityModule(this))
//                .inject(this);

        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);


        super.onCreate(savedInstanceState);
//        DaggerCarComponent.builder()
//                .carModel(new CarModel())
//                .build()
//                .inject(mCar);
//        DaggerCarComponent.create().inject(mCar);




    }

    @NotNull
    @Override
    public BaseViewModel createViewModel() {
        return new DaggerViewModel(this,mCar,mCar2);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dagger;
    }
}
