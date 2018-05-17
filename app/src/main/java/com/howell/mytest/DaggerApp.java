package com.howell.mytest;

import android.app.Application;

import com.example.dagger.ApplicationComponent;
import com.example.dagger.DaggerAppComponent;
import com.example.dagger.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by Administrator on 2018/2/12.
 */

public class DaggerApp extends DaggerApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }



    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
//        return null;
    }


}
