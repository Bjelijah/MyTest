package xyz.mercs.soundsend

object JniUtil {
    init {
        System.loadLibrary("sound_client")
    }

    external fun init(url:String,port:Int,isSSL:Boolean)
    external fun deinit()
}