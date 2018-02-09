package com.example.dagger.sample1;

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
