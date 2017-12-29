package com.example.viewmodel.sinvoice

import android.content.Context
import android.databinding.ObservableField
import android.util.AndroidException
import android.util.Log
import com.example.viewmodel.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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

    private val mSendMgr = SinVoiceMgr()


    val toOnSend = Action {
        Log.i("123","do send")
        mSendMgr.setData(mSend.get()).play()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { b->




                }, Consumer { e->e.printStackTrace() }, Action { Log.i("123","play finish") })
    }

    val toOnReceive = Action {
        Log.i("123","do receive")
    }

}