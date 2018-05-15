//package com.howell.mytest;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import com.example.dagger.sample1.Car;
//
//import javax.inject.Inject;
//import javax.inject.Named;
//
//import dagger.android.support.DaggerAppCompatActivity;
//
///**
// * Created by Administrator on 2018/2/12.
// */
//
//public class ExDagger2Activity extends DaggerAppCompatActivity {
//
//    @Inject
//    @Named("new car")
//    Car mCar;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i("123","car="+mCar.toString());
//    }
//}
