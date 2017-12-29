package com.example.viewmodel.main

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.viewmodel.BaseViewModel
import com.howell.mytest.CameraViewActivity
import com.howell.mytest.Flash2Activity
import com.howell.mytest.RxActivity
import com.howell.mytest.SinVoiceActivity
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

}