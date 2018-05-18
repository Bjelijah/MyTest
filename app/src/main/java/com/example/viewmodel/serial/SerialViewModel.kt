package com.example.viewmodel.serial

import android.content.Context
import android.hardware.usb.UsbManager
import android.util.Log
import com.example.serial.dagger.DaggerSerialComponent
import com.example.serial.dagger.SerialModel
import com.example.viewmodel.BaseViewModel
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.kongqw.serialportlibrary.Device
import com.kongqw.serialportlibrary.SerialPortFinder
import com.kongqw.serialportlibrary.SerialPortManager
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener
import io.reactivex.functions.Action
import java.io.File
import java.util.*
import javax.inject.Inject

class SerialViewModel:BaseViewModel, OnOpenSerialPortListener, OnSerialPortDataListener {



    val mContext: Context

    @Inject
    lateinit var mSerialPortFinder:SerialPortFinder

    @Inject
    lateinit var mSerialPortManager:SerialPortManager

    @Inject
    lateinit var mUsbSerialProber : UsbSerialProber

    @Inject
    lateinit var mUsbManager:UsbManager

    var mDevice: Device?=null

    override fun onCreate() {
//        initSerial()
        initUsbSerial()
    }
    override fun onDestory() {
    }

    constructor(c:Context){
        this.mContext = c
        DaggerSerialComponent.builder()
                .serialModel(SerialModel(c))
                .build()
                .inject(this)
    }


    val onClickOpen = Action {
        Log.i("123","send serial")
        mSerialPortManager.openSerialPort(mDevice?.file,9600)
    }

    val onClickClose = Action {
        mSerialPortManager.closeSerialPort()
    }

    val onClickSend = Action {
        var bs = ByteArray(7)
        bs[0] = 0xff.toByte()
        bs[1] = 0x10.toByte()
        bs[2] = 0x00.toByte()
        bs[3] = 0x00.toByte()
        bs[4] = 0x00.toByte()
        bs[5] = 0x00.toByte()
        bs[6] = 0x00.toByte()

        mSerialPortManager.sendBytes(bs)
    }

    private fun initUsbSerial(){
        var devices = mUsbSerialProber.findAllDrivers(mUsbManager)
        Log.i("123","size= ${devices.size}")
        for (d in devices){
            Log.i("123","$d")
        }


    }


    private fun initSerial(){
        var devices = mSerialPortFinder?.devices

        for( d in devices){
            Log.i("123","d name= ${d.name}")
            mDevice = d
        }

        mSerialPortManager
                .setOnOpenSerialPortListener(this)
                .setOnSerialPortDataListener(this)
    }


    override fun onFail(p0: File?, p1: OnOpenSerialPortListener.Status?) {
        Log.e("123","onFail  file=${p0?.name}  status=$p1 ")
    }

    override fun onSuccess(p0: File?) {
        Log.i("123","onSuccess  file=${p0?.name}")
    }
    override fun onDataReceived(p0: ByteArray?) {
        Log.i("123","  on Data Receive  ${Arrays.toString(p0)}")
    }

    override fun onDataSent(p0: ByteArray?) {
        Log.i("123","  on Data Send  ${Arrays.toString(p0)}")
    }
}