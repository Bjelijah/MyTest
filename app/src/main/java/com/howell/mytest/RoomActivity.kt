package com.howell.mytest

import com.example.viewmodel.BaseViewModel
import com.example.viewmodel.room.RoomViewModel

/**
 * Created by Administrator on 2018/2/26.
 */
class RoomActivity : BaseActivity() {

    override fun createViewModel(): BaseViewModel = RoomViewModel(this)

    override fun getLayoutId(): Int = R.layout.activity_room

}