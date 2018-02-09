package com.example.viewmodel.sinvoice

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import com.example.viewmodel.sinvoice.VoiceCommon.TEST_CODE_BOOK
import com.example.viewmodel.sinvoice.VoiceCommon.DEFAULT_GEN_DURATION
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlin.math.PI
import kotlin.math.max
import kotlin.math.sin


/**
 * Created by Administrator on 2017/12/28.
 */

class SinVoiceSendMgr() {

    private var SAMPLE_RATE   = 0    //44100
    private var ENCODING_PCM  = 0
    private var BITS_PEAK     = 0   //峰值
    private var mAudio :AudioTrack?=null
    private var mData:String?=null
    var sendALL = 0

    var mRecMgr:SinVoiceReceiveMgr? = null

    private constructor(sample:VoiceCommon.Type,encode:VoiceCommon.Type):this(){
        SAMPLE_RATE = sample.value
        BITS_PEAK = encode.value
        ENCODING_PCM = when(encode){
            VoiceCommon.Type.BITS_8     -> AudioFormat.ENCODING_PCM_8BIT
            VoiceCommon.Type.BITS_16    -> AudioFormat.ENCODING_PCM_16BIT
            VoiceCommon.Type.BITS_FLOAT -> AudioFormat.ENCODING_PCM_FLOAT
            else                        -> AudioFormat.ENCODING_DEFAULT
        }

        mAudio = createAudioTrack()
        mRecMgr = SinVoiceReceiveMgr.Builder().setSimple(sample).setEncodeBufType(encode).create()
        Log.i("123","code book size= ${CodeBook.CODE_BOOK.size}")
    }

    class Builder{
        var s:VoiceCommon.Type?=null
        var e:VoiceCommon.Type?=null
        fun create():SinVoiceSendMgr{
            return SinVoiceSendMgr(s?:error("should set sample"),e?:error("should set encode"))
        }

        fun setSample(s:VoiceCommon.Type):Builder{
            this.s = s
            return this
        }

        fun setEncodeBufType(e:VoiceCommon.Type):Builder{
            this.e = e
            return this
        }

        private fun error(msg:String):VoiceCommon.Type{
            return VoiceCommon.Type.ERROR
        }


    }


    private fun createAudioTrack():AudioTrack = AudioTrack(AudioManager.STREAM_MUSIC,SAMPLE_RATE,AudioFormat.CHANNEL_OUT_MONO,
                    ENCODING_PCM,DEFAULT_BUFFER_SIZE*2,AudioTrack.MODE_STREAM)


    fun setData(d:String): SinVoiceSendMgr { mData = d;return this }


    fun play(): Observable<Boolean> {
        return Observable.create { o->
            try {
                //todo play
                if (mAudio==null) mAudio = createAudioTrack()
                mAudio?.play()
                //todo do encode and send buffer
                setTextData()
                doFun(mData!!)
                o.onNext(true)
            }catch (e:Exception){
                o.onError(e)
            }finally {
                o.onComplete()
            }
        }
    }

    fun playNative(buf:ByteArray):Observable<Boolean>{
        return Observable.create { o->
            try {
                if (mAudio==null)mAudio = createAudioTrack()
                mAudio?.play()
                mAudio?.write(buf,0,buf.size)
                o.onNext(true)
            }catch (e:Exception){
                o.onError(e)
            }finally {
                o.onComplete()
            }
        }
    }





    private fun setTextData(){
//        mData = " !\"#$%&\'()*+,-./0123456789:;<=>?"
//        mData = "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_"
//        mData = "`abcdefghijklmnopqrstuvwxyz{|}~"

        var str = "{cocalwayswin}"

        mData = CodeBookEx.getASCIICode(str)
        mRecMgr?.mSb?.setLength(0)
        mData = "0123456789#"
//        mData = "0123456"
//        mData = VoiceCommon.TEST_DATA
//        mData = "{"
//        mData = "~~~~~~~~~"
        sendALL = 0
    }


    private fun doFun(data:String){
        Observable.create<ShortArray> { o->
            //todo 编码
            try {
                var codeFeqList = convertTextToCodes(data)
//                var codeFeqList = testConvertTextToCodes(data)

                (0 until codeFeqList.size)
                        .map { codeFeqList[it] }
                        .map { gen(it, DEFAULT_GEN_DURATION) }
                        .forEach { o.onNext(it) }
                o.onComplete()
            }catch (e:Exception){
                o.onError(e)
            }
        }.subscribeOn(Schedulers.trampoline())
                .subscribe(Consumer { bufs->
                    //todo
//                    Log.i("123","size=${bufs.size}  byte:$bufs ")
//                    for (i in 0 until bytes.size){
//                        Log.i("123","bytes i=${bytes[i]}")
//                    }
//                    mAudio?.write(bufs,0,bufs.size)
//
//
                    //fixme test
                    mRecMgr?.process(bufs)

                }, Consumer { e->e.printStackTrace() }, Action {
                    Log.i("123","write all code finish   sendall=$sendALL")
                    //todo test
                    mRecMgr?.testprint()

                    sendALL = 0
                })
    }



    private fun convertTextToCodes(text:String):ArrayList<Int>{//test  1..5
        val codeFeqList = ArrayList<Int>()
//        codeFeqList.add(CODE_BOOK.getValue(CODE_TOKE_START))just test one data
        (0 until text.length)
                .map { text[it] }
                .mapTo(codeFeqList) { CodeBookEx.getCode(it) }
//        codeFeqList.add(CODE_BOOK.getValue(CODE_TOKE_END))//
        return codeFeqList
    }

    private fun testConvertTextToCodes(text:String):ArrayList<Int>{
        val codeFeqList = ArrayList<Int>()
        (0 until text.length)
                .map { text[it] }
                .mapTo(codeFeqList) {VoiceCommon.TEST_CODE_BOOK.getValue(it)}
        return codeFeqList
    }






    private fun gen(genRate:Int,duration:Int):ShortArray{ //genRate  1440 - 4410
        val n = (BITS_PEAK) / 2 //16384 震幅 声音轻弱
        val totalCount = duration * SAMPLE_RATE / 1000  // 100ms 采 4410 个 16bit 的点
        val buf = ShortArray(totalCount)
        val per:Double = genRate / SAMPLE_RATE.toDouble() * 2 * PI
        var d = 0.0
        var filledSize = 0
        var num = 0
        var count = 0 //完整的上半波 点数
        var last:Short = 0
        var isStart = false
        var maxCount = 0
        for (i in 0 until totalCount){

            var out = (sin(d) * n +128).toShort()

            if (!isStart){
                if (out>=0){
                    if (last<0){
                        isStart = true
                        num = 0
                    }
                }
            }else{
                num++
                if (out>=0){
                    if (last<0){
                        count = num
                        if (count>maxCount) maxCount = count
                        num = 0
//                        isStart = false
                    }
                }

            }

            last = out
            buf[filledSize++] = out
            d += per
//            Log.i("123","out=$out")
        }
        Log.e("123","send num=$num     count=$count   max=$maxCount   ")
        sendALL += num
        return buf
    }

    fun stop(){
        mAudio?.stop()
        mAudio?.release()
        mAudio = null
    }


    fun calcNum(out:Short,last:Short):Int{
        var num = 0
        if (out > 0){
            if (last <= 0){
                num++
            }
        }else{
            if ( last > 0 ){
                num++
            }
        }
        return num
    }


}