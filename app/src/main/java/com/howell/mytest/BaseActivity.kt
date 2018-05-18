package com.howell.mytest

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.example.viewmodel.BaseViewModel
import com.example.viewmodel.BindHelper

/**
 * Created by Administrator on 2017/12/27.
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var mBind:ViewDataBinding
    lateinit var mViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this,getLayoutId())
        mViewModel = createViewModel()
        BindHelper.sDefaultBinder.bind(mBind,mViewModel)
        mViewModel.onCreate()
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.e("123","ondestry")
        mViewModel.onDestory()
        BindHelper.sDefaultBinder.bind(mBind,null)
        mBind.executePendingBindings()
    }


    abstract fun createViewModel(): BaseViewModel

    abstract fun getLayoutId():Int
}