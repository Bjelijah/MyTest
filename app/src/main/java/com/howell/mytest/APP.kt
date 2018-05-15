//package com.howell.mytest
//
//import android.app.Application
//import android.content.Context
//import com.example.dagger.AppComponent
//import com.example.dagger.AppModule
////import com.example.dagger.DaggerAppComponent
//
//import com.example.dagger.sample1.CarModel
//import com.example.viewmodel.BindHelper
//
//import com.squareup.leakcanary.LeakCanary
//import com.squareup.leakcanary.RefWatcher
//import dagger.android.AndroidInjector
//import dagger.android.DaggerApplication
//
///**
// * Created by Administrator on 2017/12/27.
// */
//class APP : Application() {
//
//
////    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
////        return DaggerAppComponent.builder()
////                .appModule(AppModule(this))
////                .carModel(CarModel())
////                .build()
////    }
//
//    //    public static RefWatcher getRefWatcher(Context context) {
////        ExampleApplication application = (ExampleApplication) context.getApplicationContext();
////        return application.refWatcher;
////    }
////
////    private RefWatcher refWatcher;
////
////    @Override
////    public void onCreate() {
////        super.onCreate();
////        refWatcher = LeakCanary.install(this);
////        BindingUtils.setDefaultBinder(BindingAdapters.defaultBinder);
////    }
////}
//    private lateinit var refWatcher: RefWatcher
//
//
//
//    override fun onCreate() {
//        super.onCreate()
//        refWatcher = LeakCanary.install(this)
//        initDaggerModule()
//    }
//
//    private fun initDaggerModule(){
////        mAppComponent = DaggerAppComponent.builder()
////                .appModule(AppModule(this))
////                .build()
//    }
//
//
//    fun getAppComponent():AppComponent? = mAppComponent
//
//    companion object {
//        private var mAppComponent : AppComponent ?= null
//        fun get(c:Context):APP = c.applicationContext as APP
//    }
//
//}