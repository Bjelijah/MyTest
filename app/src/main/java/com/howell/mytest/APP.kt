package com.howell.mytest

import android.app.Application
import com.example.viewmodel.BindHelper
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * Created by Administrator on 2017/12/27.
 */
class APP : Application() {
//    public static RefWatcher getRefWatcher(Context context) {
//        ExampleApplication application = (ExampleApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        refWatcher = LeakCanary.install(this);
//        BindingUtils.setDefaultBinder(BindingAdapters.defaultBinder);
//    }
//}
    private lateinit var refWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        refWatcher = LeakCanary.install(this)

    }


}