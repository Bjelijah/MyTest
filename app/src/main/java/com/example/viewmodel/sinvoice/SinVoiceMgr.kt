package com.example.viewmodel.sinvoice

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Build
import android.support.annotation.RequiresApi
import io.reactivex.Observable
import io.reactivex.Single


/**
 * Created by Administrator on 2017/12/28.
 */

class SinVoiceMgr() {


    private val mAudio = AudioTrack(AudioManager.STREAM_MUSIC,8000,AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT,4092,AudioTrack.MODE_STREAM)

    private var mData:String?=null

    fun setData(d:String):SinVoiceMgr  { mData = d;return this }


    fun play(): Observable<Boolean> {
        return Observable.create { o->
            try {
                //todo play
                mAudio.play()
                //todo do encode and send buffer
                doFun(mData!!)
                o.onNext(true)
            }catch (e:Exception){
                o.onError(e)
            }finally {
                o.onComplete()
            }
        }
    }

    private fun doFun(data:String){

    }






    fun stop(){
        mAudio.stop()
        mAudio.release()
    }


}