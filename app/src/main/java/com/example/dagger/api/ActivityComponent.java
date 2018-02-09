package com.example.dagger.api;

import com.howell.mytest.Dagger2Activity;

import dagger.Component;

/**
 * Created by Administrator on 2018/2/9.
 */
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(Dagger2Activity activity);
}
