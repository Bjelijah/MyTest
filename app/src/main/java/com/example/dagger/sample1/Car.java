package com.example.dagger.sample1;

import android.util.Log;



import javax.inject.Inject;

/**
 * Created by Administrator on 2018/2/8.
 */

public class Car {
    @Inject Engine engine;
    @Inject Seat seat;
    @Inject Wheel wheel;


    public Car(){
        Log.i("123","~~~~~~~~~~~~new Car");
        DaggerCarComponent.create().inject(this);
    }

    public void print(){
        Log.i("123",toString());
    }

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", seat=" + seat +
                ", wheel=" + wheel +
                '}';
    }

}
