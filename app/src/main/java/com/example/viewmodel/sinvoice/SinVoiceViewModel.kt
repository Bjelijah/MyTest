package com.example.viewmodel.sinvoice

import android.content.Context
import android.databinding.ObservableField
import android.util.Log
import com.example.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2017/12/28.
 */
class SinVoiceViewModel(private val mContext: Context):BaseViewModel {

    val mSend    = ObservableField<String>("send")
    val mReceive = ObservableField<String>("receive")
    val mState   = ObservableField<String>("Ready")

    private val mSendMgr = SinVoiceSendMgr()
    private val mRecMgr  = SinVoiceReceiveMgr()

    val toOnSendStart = Action {
        Log.i("123","do send")
        mSendMgr.setData(mSend.get()).play()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { b->
                Log.i("123","send play $b")
                }, Consumer { e->e.printStackTrace() }, Action { Log.i("123","play finish") })
    }

    val toOnSendStop = Action {
        mSendMgr.stop()
    }



    val toOnReceiveStart = Action {
        Log.i("123","do receive start")
        mRecMgr.start()
    }

    val toOnReceiveStop = Action {
        Log.i("123","do receive stop")
        mRecMgr.stop()
    }


}