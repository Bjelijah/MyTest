package com.example.viewmodel.dagger2;

import android.content.Context;
import android.util.Log;

import com.example.dagger.sample1.Car;
import com.example.viewmodel.BaseViewModel;



import io.reactivex.functions.Action;

/**
 * Created by Administrator on 2018/2/2.
 */

public class DaggerViewModel implements BaseViewModel {
    private Context mContext;
    private Car mCar1;
    private Car mCar2;
    public DaggerViewModel(Context c, Car car1,Car car2){
        mContext = c;
        mCar1 = car1;
        mCar2 = car2;
    }

    public Action onBtnClick(){
        return new Action() {
            @Override
            public void run() throws Exception {
                Log.i("123","on btnClick");
                mCar1.print();
            }
        };
    }

    public Action onBtnClick2(){
        return new Action() {
            @Override
            public void run() throws Exception {
                Log.i("123","on btn2 click");
                mCar2.print();
            }
        };
    }


}
