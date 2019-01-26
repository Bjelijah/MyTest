package com.howell.mytest

import com.example.viewmodel.BaseViewModel
import com.example.viewmodel.soundSend.SoundSendViewModel

class SoundSendActivity :BaseActivity() {
    override fun createViewModel(): BaseViewModel = SoundSendViewModel(this)

    override fun getLayoutId(): Int = R.layout.activity_sound_send
}