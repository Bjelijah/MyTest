//package com.howell.mytest;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Button;
//
//
//import com.example.dagger.sample1.Car;
//import com.example.dagger.sample2.ActivityModule;
//import com.example.dagger.sample2.DaggerActivityComponent;
//import com.example.viewmodel.BaseViewModel;
//import com.example.viewmodel.dagger2.DaggerViewModel;
//
//
//import org.jetbrains.annotations.NotNull;
//
//import javax.inject.Inject;
//import javax.inject.Named;
//
//
///**
// * Created by Administrator on 2018/2/2.
// */
//
//public class Dagger2Activity extends BaseActivity {
//
//
//    Car mCar;
//
//    @Inject
//    Car mCar2;
//
//    @Inject
//    @Named("car1")
//    Car mCar3;
//
//    @Inject
//    @Named("car2")
//    Car mCar4;
//
//    @Override
//    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        mCar = APP.Companion.get(this)
//                .getAppComponent()
//                .getCar();
//
//        DaggerActivityComponent.builder()
//                .activityModule(new ActivityModule(this))
//                .build()
//                .inject(this);
//
//
//        super.onCreate(savedInstanceState);
//    }
//
//    @NotNull
//    @Override
//    public BaseViewModel createViewModel() {
//        return new DaggerViewModel(this,mCar).setMoreCar(new Car[]{mCar2,mCar3,mCar4});
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_dagger;
//    }
//}
