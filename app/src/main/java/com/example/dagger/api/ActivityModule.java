package com.example.dagger.api;

import android.content.Context;

import com.example.dagger.sample1.Car;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/2/9.
 */
@Module
public class ActivityModule {
    Context c;
    public ActivityModule(Context c){
        this.c = c;
    }

    @Provides
    Car provideCar(){
        return new Car();
    }
}
