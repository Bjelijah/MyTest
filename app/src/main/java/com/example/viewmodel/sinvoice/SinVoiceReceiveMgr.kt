package com.example.viewmodel.sinvoice

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import io.reactivex.Observable
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.math.min

/**
 * Created by Administrator on 2017/12/29.
 */
class SinVoiceReceiveMgr {

    private val SAMPLE_RATE   = VoiceCommon.SAMPLE_RATE_44    //8000
    private val ENCODING_PCM  = AudioFormat.ENCODING_PCM_16BIT
    private val BITS_PEAK     = VoiceCommon.BITS_16   //峰值
    private val minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,  AudioFormat.ENCODING_PCM_16BIT)
    private val mAudioRecord  = AudioRecord(MediaRecorder.AudioSource.MIC,SAMPLE_RATE,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT, minBufferSize)

    private var mIsStart:Boolean = true

    private var mCirclePointCount = 0

    fun start(){
        mIsStart = true
        Observable.create<Boolean> {
            mAudioRecord.startRecording()
            Log.i("123","minBufferSize=$minBufferSize")
            var buffers = ByteArray(minBufferSize)
            while (mIsStart){
                mAudioRecord.read(buffers!!,0,minBufferSize)
            }
        }
    }

    fun process(buffer:ByteArray){
        var sh:Short = 0
        var i = 0
        var dataList = ArrayList<Short>()

        while(i<buffer.size-1){
            var sh1:Int = (buffer[i] and 0xff.toByte()).toInt()
            var sh2:Int = buffer[++i].toInt()
            sh2 = sh2 shl 8
            sh = (sh1 or sh2).toShort()
            dataList.add(sh)
            i++
        }

        Log.i("123","ByteList size=${buffer.size}    ShortLIst size= ${dataList.size}")

        var j = 0
        var zero:Short = 0
        for (i in 0 until dataList.size){
            Log.i("123","${dataList[i]}")
            if (dataList[i] == zero){
                j++
            }
        }
        Log.i("123","j=${j/2}")


    }

/*
    private fun process(data: Buffer.BufferData) {
        val size = data.getFilledSize() - 1
        var sh: Short = 0
        var i = 0
        while (i < size) {
            var sh1 = data.byteData[i]
            sh1 = sh1 and 0xff
            var sh2 = data.byteData[++i]
            sh2 = sh2 shl 8
            sh = (sh1 or sh2).toShort()

            if (!mIsStartCounting) {
                if (STATE_STEP1 == mStep) {
                    if (sh < 0) {
                        mStep = STATE_STEP2
                    }
                } else if (STATE_STEP2 == mStep) {
                    if (sh > 0) {
                        mIsStartCounting = true
                        mCirclePointCount = 0
                        mStep = STATE_STEP1
                    }
                }
            } else {
                ++mCirclePointCount
                if (STATE_STEP1 == mStep) {
                    if (sh < 0) {
                        mStep = STATE_STEP2
                    }
                } else if (STATE_STEP2 == mStep) {
                    if (sh > 0) {
                        // preprocess the circle
                        val circleCount = preReg(mCirclePointCount)

                        // recognise voice
                        reg(circleCount)

                        mCirclePointCount = 0
                        mStep = STATE_STEP1
                    }
                }
            }
            i++
        }
    }
*/










    fun stop(){
        mIsStart = false
    }

}