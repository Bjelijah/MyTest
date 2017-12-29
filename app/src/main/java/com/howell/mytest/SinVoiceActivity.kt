package com.howell.mytest

import com.example.viewmodel.BaseViewModel
import com.example.viewmodel.sinvoice.SinVoiceViewModel

/**
 * Created by Administrator on 2017/12/28.
 */
class SinVoiceActivity:BaseActivity() {
    override fun createViewModel(): BaseViewModel = SinVoiceViewModel(this)

    override fun getLayoutId(): Int = R.layout.activity_sinvoice
}