package com.example.viewmodel

import android.databinding.ViewDataBinding

/**
 * Created by Administrator on 2017/12/27.
 */
interface ViewModelBinder {
    fun bind(viewDataBinding: ViewDataBinding, viewModel: BaseViewModel?)
}
