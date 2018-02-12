package com.example.viewmodel.main

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.viewmodel.BaseViewModel
import com.howell.mytest.*
import com.howell.p.mylibrary.MyTest
import io.reactivex.functions.Action

/**
 * Created by Administrator on 2017/12/27.
*/
class MainViewModel(private val mContext: Context) : BaseViewModel {

    val onClickFlash     = Action { mContext.startActivity(Intent(mContext, Flash2Activity::class.java)) }
    val onClickView      = Action { mContext.startActivity(Intent(mContext, CameraViewActivity::class.java)) }
    val onClickRetrofit  = Action { mContext.startActivity(Intent(mContext,RxActivity::class.java)) }
    val onClickTest      = Action { Log.i("123",MyTest.justTest()) }
    val onClickVoice     = Action { mContext.startActivity(Intent(mContext,SinVoiceActivity::class.java)) }
    val onClickDagger    = Action { mContext.startActivity(Intent(mContext,Dagger2Activity::class.java)) }
    val onClickDagger2   = Action { mContext.startActivity(Intent(mContext,ExDagger2Activity::class.java)) }
}