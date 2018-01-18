package com.example.viewmodel.sinvoice

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.speech.tts.Voice
import android.util.Log
import com.example.viewmodel.sinvoice.CodeBookEx.getStringFromASCIICode
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.math.min

/**
 * Created by Administrator on 2017/12/29.
 */
class SinVoiceReceiveMgr {

    private var SAMPLE_RATE   = 0  //8000
    private var ENCODING_PCM  = 0
    private var BITS_PEAK     = 0   //峰值
    private var minBufferSize = 0

    private var mAudioRecord:AudioRecord?  = null
    private var mIsStart:Boolean = true
    var recAll = 0
    private var mCirclePointCount = 0

    private var myBuffer:MyBuffer?=null

    var mBuffers : ArrayBlockingQueue<Short>? = null

    public var mSb = StringBuffer()

    constructor(simple:VoiceCommon.Type,encode:VoiceCommon.Type){
        SAMPLE_RATE  = simple.value
        BITS_PEAK    = encode.value
        ENCODING_PCM = when(encode){
            VoiceCommon.Type.BITS_8     -> AudioFormat.ENCODING_PCM_8BIT
            VoiceCommon.Type.BITS_16    -> AudioFormat.ENCODING_PCM_16BIT
            VoiceCommon.Type.BITS_FLOAT -> AudioFormat.ENCODING_PCM_FLOAT
            else                        -> AudioFormat.ENCODING_DEFAULT
        }
      //  minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,  ENCODING_PCM) * 2

        minBufferSize = VoiceCommon.DEFAULT_GEN_DURATION * SAMPLE_RATE / 1000  // 100ms 采 4410 个 16bit 的点

        mAudioRecord  = AudioRecord(MediaRecorder.AudioSource.MIC,SAMPLE_RATE,AudioFormat.CHANNEL_IN_MONO,ENCODING_PCM, minBufferSize*2)
        myBuffer = MyBuffer(minBufferSize*2)
        mSb.setLength(0)
//        mBuffers = ArrayBlockingQueue(minBufferSize*2)
    }

    class Builder{
        var s:VoiceCommon.Type?=null
        var e:VoiceCommon.Type?=null
        fun create():SinVoiceReceiveMgr{
            return SinVoiceReceiveMgr(s?:error("should set simple"),e?:error("should set encode"))
        }
        fun setSimple(s:VoiceCommon.Type):Builder{
            this.s = s
            return this
        }


        fun setEncodeBufType(e:VoiceCommon.Type): Builder {
            this.e = e
            return this
        }

        private fun error(msg:String):VoiceCommon.Type{
            return VoiceCommon.Type.ERROR
        }


    }

    fun start(){
        mIsStart = true
        mSb.setLength(0)
        Observable.create<Boolean> { o->
//            mAudioRecord?.prepare()
            mAudioRecord?.startRecording()
            Log.i("123","minBufferSize=$minBufferSize")
//            var buffers = ByteArray(minBufferSize)
            var buffers = ShortArray(minBufferSize)
            var offset = 0

            var temp = ShortArray(minBufferSize*2)
            var tempOffset = 0
            var beg = false
            while (mIsStart){

//                while(!beg && mIsStart){//寻找开始点
//                    mAudioRecord?.read(temp!!,0,minBufferSize*2)
//                    offset = findBeg(temp)
//                    if (offset > -1) beg = true
//                }

                mAudioRecord?.read(buffers!!,offset,minBufferSize-offset)//
//                myBuffer?.offer(buffers)
//                Log.i("123","rec=$rec")

//                (0 until buffers.size)
//                        .filter { it %10==0 }
//                        .forEach { Log.i("123","buffers=  ${buffers[it]}") }




                var num = process(buffers)
                var str = handleSample(num)
//                var str = handleSample(num)
                Log.i("123","num=$num    get str=$str")
                offset = 0
            }
            o.onNext(true)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                },{e->e.printStackTrace()},{Log.i("123","receive finish")})
    }

    fun process(buffer:ByteArray){
        var sh:Short = 0
        var i = 0
        var dataList = ArrayList<Short>()

        while(i<buffer.size-1){
            var sh1:Int = (buffer[i] and 0xff.toByte()).toInt()
            var sh2:Int = buffer[++i].toInt()
            sh2 = (sh2 shl 8) and 0xff
            sh = (sh1 or sh2).toShort()
            Log.i("123","sh=$sh")
            dataList.add(sh)
            i++
        }

        Log.i("123","ByteList size=${buffer.size}    ShortLIst size= ${dataList.size}")

        var j = 0
        var zero:Short = 0
        for (i in 0 until dataList.size){
//            Log.i("123","${dataList[i]}")

        }
    }

    fun findBeg(arr:ShortArray):Int{
        var buf = ShortArray(minBufferSize)
        var offset = 0
        var ret = false
        while (true) {
            if (offset >= minBufferSize) {
                ret = false
                break
            }
            for (i in (0 + offset) until (minBufferSize + offset)) {
                buf[i - offset] = arr[i]
            }
            var num = process(buf)
//            Log.i("123","num=$num")
            if(handleSample(num).equals("{",true)){
                ret = true
                break
            }else{
                offset++
            }
        }
        if (!ret) offset = -1
        return offset
    }




    fun process(buffer:ShortArray) : Int{  //fixme 去干扰 避免连续变化
        var num = 0
        var last:Short = 0
        var next:Short = 0
        for (i in 0 until buffer.size-1){
            next = buffer[i+1]
            if (buffer[i] > 0){
                if (last <= 0)num++
            }else{
                if (last > 0 )num++
            }
            last = buffer[i]
//            Log.i("123","buffer=${buffer[i]}")
        }
        Log.e("123","rec num=$num")

        //fixme for test
        var str = handleSample(num)

        return num
    }


    fun newProcess(buffer:ShortArray):Int{
        var num =0
        for (i in 0 until  buffer.size){

        }
        return num
    }




    fun handleSample(num:Int):String{
        if (num !in 261..999) return ""

        var str = CodeBookEx.getCodeFromNum(num)
        if (!str.equals("")) {
            recAll += num
            Log.e("123", "str = $str")
        }

        mSb.append(str)
        return str
    }


    fun testprint(){

        var strArr = mSb.split("#")
        Log.i("123","msb=$mSb        $strArr")
        var sb = StringBuffer()
        strArr
                .filterNot { it.equals("") }
                .forEach {

                    try{
                        sb.append(getStringFromASCIICode(Integer.parseInt(it)))
                    }catch (e:Exception){
                        Log.e("123","error  it=$it")
                    }

                }
        Log.i("123","new str = $sb")
    }




    fun testHandleSample(num:Int):String{
        return VoiceCommon.getStringFromNum(num)
    }











    fun stop(){
        mIsStart = false
        mAudioRecord?.stop()
        Log.i("123","recall=$recAll")
        recAll = 0
        testprint()
    }

}