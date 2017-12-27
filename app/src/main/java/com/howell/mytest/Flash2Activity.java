package com.howell.mytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.utils.FlashLightManager;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/11.
 */

public class Flash2Activity extends AppCompatActivity{
    FlashLightManager mgr ;
    @BindView(R.id.button) Button btnOpen;
    @BindView(R.id.button2) Button btnClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        ButterKnife.bind(this);

        mgr = new FlashLightManager(this);
        mgr.init();

    }

    @OnClick(R.id.button) void clickOpen(){
        mgr.turnOn();

//        try {
//            mgr.ctrlLamp(true);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

    }
    @OnClick(R.id.button2) void clickClose(){
        mgr.turnOff();

//        try {
//            mgr.ctrlLamp(false);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }
}
