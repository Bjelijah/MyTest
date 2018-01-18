package com.example.viewmodel.sinvoice

/**
 * Created by Administrator on 2017/12/29.
 */
object VoiceCommon {

    enum class Type(var value:Int){
        SAMPLE_RATE_8(8000),
        SAMPLE_RATE_11(112500),
        SAMPLE_RATE_16(16000),
        SAMPLE_RATE_44(44100),
        BITS_8(127),
        BITS_16(32767),
        BITS_FLOAT(Float.MAX_VALUE.toInt()),
        ERROR(0)
    }



    val DEFAULT_BUFFER_SIZE  = 1024*8


    val DEFAULT_GEN_DURATION = 100  //一个数据持续时间100 ms


    val TEST_CODE_BOOK = mapOf('0' to 1442,//key:要编码的文字，val 频率   289
            '1' to 1575,//315
            '2' to 1764,//353
            '3' to 2004,//401
            '4' to 2205,//441
            '5' to 2940,//558
            '6' to 4410)//882

    fun getStringFromNum(num:Int):String{
        var str = when(num){
            in 270..289 -> '0'
            in 290..315 -> '1'
            in 320..353 -> '2'
            in 360..401 -> '3'
            in 405..441 -> '4'
            in 500..558 -> '5'
            in 800..882 -> '6'
            else ->""
        }
        return str.toString()
    }



    val CODE_TOKE_START         = '0'
    val CODE_TOKE_END           = '6'

    val TEST_DATA = "065532410531221"

}