package com.howell.mytest

import com.example.viewmodel.BaseViewModel
import com.example.viewmodel.main.MainViewModel


/**
 * Created by Administrator on 2017/6/15.
 */

class MainActivity : BaseActivity() {

    override fun createViewModel(): BaseViewModel = MainViewModel(this)
    override fun getLayoutId(): Int = R.layout.activity_main

}
