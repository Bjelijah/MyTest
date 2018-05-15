//package com.example.dagger;
//
//import android.app.Application;
//
//import com.example.dagger.sample3.ActivityBindModule;
//import com.howell.mytest.DaggerApp;
//
//import javax.inject.Singleton;
//
//import dagger.BindsInstance;
//import dagger.Component;
//import dagger.android.AndroidInjector;
//import dagger.android.support.AndroidSupportInjectionModule;
//
///**
// * Created by Administrator on 2018/2/12.
// */
//@Singleton
//@Component(modules = {ApplicationModule.class, ActivityBindModule.class,AndroidSupportInjectionModule.class})
//public interface ApplicationComponent extends AndroidInjector<DaggerApp>{
//
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        ApplicationComponent.Builder application(Application application);
//        ApplicationComponent build();
//    }
//}
