package com.example.serial.dagger

import android.content.Context
import android.hardware.usb.UsbManager
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.kongqw.serialportlibrary.SerialPortFinder
import com.kongqw.serialportlibrary.SerialPortManager
import dagger.Module
import dagger.Provides

@Module
class SerialModel(private val mContext: Context) {
    @Provides
    fun provideSerialPortFinder():SerialPortFinder = SerialPortFinder()

    @Provides
    fun provideSerialManager():SerialPortManager = SerialPortManager()

    @Provides
    fun provideUsbSerialProber():UsbSerialProber = UsbSerialProber.getDefaultProber()

    @Provides
    fun provideUsbManager():UsbManager = mContext.getSystemService(Context.USB_SERVICE) as UsbManager




}