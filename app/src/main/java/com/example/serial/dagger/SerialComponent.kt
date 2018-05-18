package com.example.serial.dagger

import com.example.viewmodel.serial.SerialViewModel
import dagger.Component

@Component(modules = [(SerialModel::class)])
interface SerialComponent {
    fun inject(model:SerialViewModel)
}