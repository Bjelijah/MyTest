//package com.example.dagger.sample1;
//
//import android.util.Log;
//
//import javax.inject.Inject;
//
//import dagger.Module;
//import dagger.Provides;
//
///**
// * Created by Administrator on 2018/2/8.
// */
//@Module
//public class CarModel {
//    @Provides
//    Engine provideEngine(){
//        return new Engine();
//    }
//
//    @Provides
//    Seat provideSeat(){
//        return new Seat();
//    }
//
//    @Provides
//    Wheel provideWheel(){
//        return new Wheel();
//    }
//
//
//}
//
//
//class Engine{
//    @Inject
//     Engine(){
//        Log.w("123","new Engine");
//    }
//
//    @Override
//    public String toString() {
//        return "engine in car";
//    }
//}
//
//
//class Seat{
//    @Inject
//     Seat(){
//        Log.w("123","new Seat");
//    }
//
//    @Override
//    public String toString() {
//        return "seat in car";
//    }
//}
//
//
//class Wheel{
//    @Inject
//     Wheel(){
//        Log.w("123","new wheel");
//    }
//
//    @Override
//    public String toString() {
//        return "wheel in car";
//    }
//}