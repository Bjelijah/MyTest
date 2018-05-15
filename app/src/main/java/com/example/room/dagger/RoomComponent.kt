package com.example.room.dagger

import com.example.viewmodel.room.RoomViewModel
import dagger.Component

/**
 * Created by Administrator on 2018/2/26.
 */

@Component(modules = arrayOf(RoomModel::class))
interface RoomComponent {
    fun inject(model:RoomViewModel)
}