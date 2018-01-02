package com.example.viewmodel.sinvoice

/**
 * Created by Administrator on 2017/12/29.
 */
object VoiceCommon {
    val SAMPLE_RATE_8        = 8000
    val SAMPLE_RATE_11       = 11250
    val SAMPLE_RATE_16       = 16000
    val SAMPLE_RATE_44       = 44100

    val DEFAULT_BUFFER_SIZE  = 1024*8
    val BITS_8               = 128    //2^8
    val BITS_16              = 32768  //2^16

    val DEFAULT_GEN_DURATION = 100  //一个数据持续时间100 ms


    val CODE_BOOK = mapOf('0' to 1442,//key:要编码的文字，val 频率
            '1' to 1575,
            '2' to 1764,//25
            '3' to 2004,
            '4' to 2205,//2321
            '5' to 2940,
            '6' to 4410)//10

    val CODE_TOKE_START         = '0'
    val CODE_TOKE_END           = '6'

}