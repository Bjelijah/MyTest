//package com.example.dagger.sample1;
//
//import android.util.Log;
//
//
//
//import javax.inject.Inject;
//import javax.inject.Named;
//
///**
// * Created by Administrator on 2018/2/8.
// */
//
//public class Car {
//    @Inject Engine engine;
//    @Inject Seat seat;
//    @Inject Wheel wheel;
//
//    private String flag;
//
//    @Inject
//    public Car(){
//        Log.i("123","~~~~~~~~~~~~new Car");
//        DaggerCarComponent.create().inject(this);
//    }
//
//    public Car(String str){
//        Log.i("123","~~~~~~~~~~~new Car "+str);
//        flag = str;
//        DaggerCarComponent.create().inject(this);
//    }
//
//
//    public void print(){
//        Log.i("123",toString()+"  hashcode="+this.hashCode());
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "engine=" + engine +
//                ", seat=" + seat +
//                ", wheel=" + wheel +
//                ", flag=" + flag +
//                '}';
//    }
//
//}
