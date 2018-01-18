package com.example.viewmodel.sinvoice

import java.util.concurrent.ArrayBlockingQueue

/**
 * Created by Administrator on 2018/1/3.
 */
class MyBuffer {
    var buffers : ArrayBlockingQueue<Short> ? = null


    constructor(size : Int){
        buffers = ArrayBlockingQueue(size)
    }

    fun poll(arr:ShortArray,len:Int){
        for (i in 0 until len){
            arr[i] = buffers?.poll()!!
        }
    }


    fun offer (arr:ShortArray){
        for (s in arr){
            buffers?.offer(s)
        }
    }





}