package com.example.dagger;

import android.app.Application;
import android.content.Context;

import com.example.dagger.sample1.CarComponent;
import com.howell.mytest.APP;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/2/8.
 */
@Singleton
@Module
public class AppModule {
    private Application application;
    public AppModule(Application application){
        this.application = application;
    }


    /**
     * no use in DaggerApp  used to in APP
     */
//    @Provides
//    @Singleton
//    public Application provideApplication(){
//        return application;
//    }




}
