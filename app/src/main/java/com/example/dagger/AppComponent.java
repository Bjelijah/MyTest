package com.example.dagger;


import android.content.Context;

import com.example.dagger.sample1.ApiModule;
import com.example.dagger.sample1.Car;
import com.example.dagger.sample1.CarComponent;
import com.howell.mytest.APP;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Administrator on 2018/2/8.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent extends AndroidInjector<APP> {

//    ActivityComponent addSub(ActivityModule module);
    Car getCar();

//    CarComponent getApiComponent();

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        AppComponent.Builder application(Application application);
//        AppComponent build();
//    }
}
