package com.howell.mytest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.dagger.sample2.ActivityComponent


class LiveActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)
        ButterKnife.bind(this)
        initData()
    }
    private fun initData(){

    }

    @OnClick(R.id.live_room) fun onClickLiveRoom(){
        //
        live()
    }

    @OnClick(R.id.live_watch) fun onClickWatch(){

    }


    private fun live(){
        //直播
        //登入




    }


}