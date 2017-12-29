package com.example.viewmodel

import android.databinding.BindingConversion
import android.databinding.ViewDataBinding
import android.util.Log
import android.view.View
import com.howell.mytest.BR
import io.reactivex.functions.Action


/**
 * Created by Administrator on 2017/12/27.
 */
object BindHelper {



    val sDefaultBinder:ViewModelBinder = object :ViewModelBinder{
        override fun bind(viewDataBinding: ViewDataBinding, viewModel: BaseViewModel?) {
            viewDataBinding.setVariable(BR.vm,viewModel)
        }
    }

    @BindingConversion
    @JvmStatic
    fun toOnClick(listener:Action?) = View.OnClickListener {
        if(listener==null) Log.e("123","listener==null")
        listener?.run()
    }





}