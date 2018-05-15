//package com.example.dagger.sample2;
//
//import android.content.Context;
//
//import com.example.dagger.sample1.Car;
//
//import javax.inject.Named;
//
//import dagger.Module;
//import dagger.Provides;
//
///**
// * Created by Administrator on 2018/2/9.
// */
//@Module
//public class ActivityModule {
//    Context c;
//    public ActivityModule(Context c){
//        this.c = c;
//    }
//
//    @Provides
//    Car provideCar(){
//        return new Car();
//    }
//
//    @Provides
//    @Named("car1")
//    Car provideCar1(){
//        return new Car("this car1");
//    }
//
//    @Provides
//    @Named("car2")
//    Car provideCar2(){
//        return new Car("this car2");
//    }
//
//}
