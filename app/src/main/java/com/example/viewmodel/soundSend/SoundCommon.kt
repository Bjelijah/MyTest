package com.example.viewmodel.soundSend

import android.media.AudioFormat

object SoundCommon {
    enum class RATE(var value:Int){
        SAMPLE_RATE_8(8000),
        SAMPLE_RATE_11(112500),
        SAMPLE_RATE_16(16000),
        SAMPLE_RATE_44(44100)
    }

    enum class CHANNEL(var value:Int){
        CHANNEL_MONO(AudioFormat.CHANNEL_IN_MONO),
        CHANNEL_STEREO(AudioFormat.CHANNEL_IN_STEREO)
    }

    enum class ENCODING(var value:Int){
        ENCODING_PCM_8( AudioFormat.ENCODING_PCM_8BIT),
        ENCODING_PCM_16( AudioFormat.ENCODING_PCM_16BIT),
    }
}