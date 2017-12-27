package com.howell.mytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.utils.Config;
import com.example.utils.RxUtil;
import com.net.HttpManager;
import com.net.bean.UserNonce;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/7.
 */

public class RxActivity extends AppCompatActivity{

    @BindView(R.id.btn_rx_thread_ui) Button uiBtn;
    @BindView(R.id.btn_rx_thread_io) Button ioBtn;
    @BindView(R.id.btn_rx_task) Button taskBtn;
    @BindView(R.id.btn_rx_retrofit) Button retrofitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxtest);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_rx_thread_ui)void clickUI(){
        Log.i("123","on click ui");
        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.i("123"," in thread");
                RxUtil.doInUIThread(new RxUtil.RxSimpleTask<String>() {

                    @Override
                    public void doTask() {
                        Log.i("123","click UI");
                    }
                });
            }
        }.start();
    }

    @OnClick(R.id.btn_rx_thread_io)void clickIO(){
        Log.i("123","on click io ui");
        RxUtil.doInIOTthread(new RxUtil.RxSimpleTask<Void>() {
            @Override
            public void doTask() {
                Log.i("123","click io");
            }
        });
    }

    @OnClick(R.id.btn_rx_task)void clickTask(){
        Log.i("123","on click task");
        RxUtil.doRxTask(new RxUtil.CommonTask<Object>() {
            boolean ret = false;
            @Override
            public void doInIOThread() {
                Log.i("123","do in io thread  start sleep");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    ret = false;
                }
                Log.i("123","do in io thread sleep end");
                ret = true;
            }

            @Override
            public void doInUIThread() {
                Log.i("123","do in ui thread ret="+ret);
            }
        });
    }

    @OnClick(R.id.btn_rx_retrofit)void clickRetrofit(){
        try {
            HttpManager.getInstance().initClient(this, Config.IS_SSL)
                    .getHttpService()
                    .getUserNonce("howell")
                    .map(new Function<UserNonce, UserNonce>() {

                        @Override
                        public UserNonce apply(@NonNull UserNonce userNonce) throws Exception {
                            Log.i("123","userNonce="+userNonce);
                            return userNonce;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UserNonce>() {
                        @Override
                        public void accept(@NonNull UserNonce userNonce) throws Exception {
                            Log.i("123", "userNonce=" + userNonce);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
