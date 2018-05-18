package com.example.viewmodel.dagger2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dagger.sample1.Car;
import com.example.viewmodel.BaseViewModel;



import io.reactivex.functions.Action;

/**
 * Created by Administrator on 2018/2/2.
 */

public class DaggerViewModel implements BaseViewModel {
    private Context mContext;
    private Car mCar1,mCar2,mCar3,mCar4;
    public DaggerViewModel(Context c, Car car1){
        mContext = c;
        mCar1 = car1;
    }

    public DaggerViewModel setMoreCar(Car... cars){
        if (cars==null)return this;
        if(cars[0]!=null) mCar2 = cars[0];
        if(cars[1]!=null) mCar3 = cars[1];
        if(cars[2]!=null) mCar4 = cars[2];
        return this;
    }




    public Action onBtnClick(){
        return new Action() {
            @Override
            public void run() throws Exception {
                Log.i("123","on btnClick");
                try {
                    mCar1.print();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        };
    }

    public Action onBtnClick2(){
        return new Action() {
            @Override
            public void run() throws Exception {
                Log.i("123","on btn2 click");
                mCar2.print();
                mCar3.print();
                mCar4.print();
            }
        };
    }


    @Override
    public void onDestory() {

    }

    @Override
    public void onCreate() {

    }
}
