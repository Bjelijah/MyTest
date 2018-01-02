package com.example.viewmodel.sinvoice

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import com.example.viewmodel.sinvoice.VoiceCommon.BITS_16
import com.example.viewmodel.sinvoice.VoiceCommon.CODE_BOOK
import com.example.viewmodel.sinvoice.VoiceCommon.CODE_TOKE_END
import com.example.viewmodel.sinvoice.VoiceCommon.CODE_TOKE_START
import com.example.viewmodel.sinvoice.VoiceCommon.DEFAULT_GEN_DURATION
import com.example.viewmodel.sinvoice.VoiceCommon.SAMPLE_RATE_44

import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlin.math.PI
import kotlin.math.sin


/**
 * Created by Administrator on 2017/12/28.
 */

class SinVoiceSendMgr() {

    private val SAMPLE_RATE   = SAMPLE_RATE_44    //44100
    private val ENCODING_PCM  = AudioFormat.ENCODING_PCM_16BIT
    private val BITS_PEAK     = BITS_16   //峰值



    private val mAudio = AudioTrack(AudioManager.STREAM_MUSIC,SAMPLE_RATE,AudioFormat.CHANNEL_OUT_MONO,
            ENCODING_PCM,DEFAULT_BUFFER_SIZE*2,AudioTrack.MODE_STREAM)

    private var mData:String?=null

    var mRecMgr = SinVoiceReceiveMgr()

    fun setData(d:String): SinVoiceSendMgr { mData = d;return this }


    fun play(): Observable<Boolean> {
        return Observable.create { o->
            try {
                //todo play

//                mAudio.play()
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
        Observable.create<ByteArray> { o->
            //todo 编码
            try {
                var codeFeqList = convertTextToCodes(data)
                (0 until codeFeqList.size)
                        .map { codeFeqList[it] }
                        .map { gen(it, DEFAULT_GEN_DURATION) }
                        .forEach { o.onNext(it) }
                o.onComplete()
            }catch (e:Exception){
                o.onError(e)
            }
        }.subscribeOn(Schedulers.trampoline())
                .subscribe(Consumer { bytes->
            //todo
                    Log.i("123","size=${bytes.size}  byte:$bytes ")
//                    for (i in 0 until bytes.size){
//                        Log.i("123","bytes i=${bytes[i]}")
//                    }
//                    mAudio.write(bytes,0,bytes.size)
//                    mAudio.flush()
                    //fixme test
                    mRecMgr.process(bytes)

                }, Consumer { e->e.printStackTrace() }, Action {
                    Log.i("123","write all code finish")

                })
    }



    private fun convertTextToCodes(text:String):ArrayList<Int>{//test  1..5
        val codeFeqList = ArrayList<Int>()
//        codeFeqList.add(CODE_BOOK.getValue(CODE_TOKE_START))just test one data
        (0 until text.length)
                .map { text[it] }
                .mapTo(codeFeqList) { CODE_BOOK.getValue(it) }
//        codeFeqList.add(CODE_BOOK.getValue(CODE_TOKE_END))//
        return codeFeqList
    }


    private fun gen(genRate:Int,duration:Int):ByteArray{
        val n = (BITS_PEAK) / 2 //16384 震幅
        val totalCount = duration * SAMPLE_RATE / 1000  // 100ms 采 4410 个 16bit 的点
        val buffer = ByteArray(totalCount*2)//byte=8bit
        val per:Double = genRate / SAMPLE_RATE.toDouble() * 2 * PI
        var d:Double = 0.0
        var filledSize = 0
        for (i in 0 until totalCount){
            var out = (sin(d) * n ).toInt()
//            var out = (sin(d) * n ).toInt()
            buffer[filledSize++] = (out and 0xff).toByte()
            buffer[filledSize++] = (out shr 8 and 0xff).toByte()
            d += per
        }
        return buffer
    }

    fun stop(){
        mAudio.stop()
//        mAudio.flush()
        mAudio.release()
    }


}