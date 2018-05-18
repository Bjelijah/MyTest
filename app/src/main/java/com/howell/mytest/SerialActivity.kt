package com.howell.mytest

import com.example.viewmodel.BaseViewModel
import com.example.viewmodel.serial.SerialViewModel

class SerialActivity :BaseActivity() {
    override fun createViewModel(): BaseViewModel = SerialViewModel(this)

    override fun getLayoutId(): Int = R.layout.activity_serial
}