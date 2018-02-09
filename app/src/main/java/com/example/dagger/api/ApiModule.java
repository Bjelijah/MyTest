package com.example.dagger.api;

import com.example.dagger.sample1.Car;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/2/9.
 */
@Module
public class ApiModule {
    @Provides
    Car provideCar(){
        return new Car();
    }
}
