package com.example.viewmodel.soundSend

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.example.viewmodel.BaseViewModel
import io.reactivex.functions.Action
import xyz.mercs.soundsend.JniUtil
import java.nio.ByteBuffer

class SoundSendViewModel(val mContext:Context):BaseViewModel{

    private var SAMPLE_RATE   = 0    //44100
    private var ENCODING_PCM  = 0
    private var mRecord:AudioRecord?=null


    private val RATE = SoundCommon.RATE.SAMPLE_RATE_8.value
    private val CHANNEL = SoundCommon.CHANNEL.CHANNEL_MONO.value
    private val ENCODE = SoundCommon.ENCODING.ENCODING_PCM_16.value
    private var mData: ByteArray?=null
//    private var mData :ByteBuffer ?= null
    private var mTask:Task?=null
    override fun onCreate() {
        val buffSize = AudioRecord.getMinBufferSize(RATE,
                CHANNEL,
                ENCODE)
        Log.i("123","buffsize=$buffSize")
        mRecord = AudioRecord(MediaRecorder.AudioSource.MIC,
                RATE,
                CHANNEL,
                ENCODE,
                buffSize)
        mData = ByteArray(buffSize)

        mTask = Task()
        //jni init
        JniUtil.init("116.228.67.70",8812,true)
    }

    override fun onDestory() {
        if (mRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING){
            mRecord?.stop()
        }
        if (mTask?.isLoop==true){
            mTask?.cancel()
        }
        mRecord?.release()
        // deinit
        JniUtil.deinit()
    }

    val onStartClick = Action {
        //step 1  录音
        //step 2  连接
        //step 3  发送
        if(mRecord?.state == AudioRecord.STATE_INITIALIZED &&
                mRecord?.recordingState == AudioRecord.RECORDSTATE_STOPPED){
            mRecord?.startRecording()
        }



        if (mTask?.isLoop==false){
            mTask?.start()
        }

    }

    val onStopClick = Action {
        //step 1  停止发送
        //step 2  断开连接
        //step 3  停止录音
        if (mTask?.isLoop==true){
            mTask?.cancel()
        }
        //step2

        //step3
        if (mRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING){
            mRecord?.stop()
        }


    }

    inner class Task : Thread(){

        var isLoop:Boolean = false

        override fun start() {
            isLoop = true

            super.start()
        }

        fun cancel(){
            isLoop = false
            interrupt()
        }

        override fun run() {
            super.run()
            while (isLoop && !Thread.currentThread().isInterrupted){
                var len = mRecord?.read(mData!!,0,mData!!.size)?:0
                Log.i("123","read len=$len")
                if(len>0){
                    //send


                }
            }
        }
    }


}