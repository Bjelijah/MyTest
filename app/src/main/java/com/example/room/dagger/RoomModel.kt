package com.example.room.dagger

import android.arch.persistence.room.Room
import android.content.Context
import android.support.transition.Visibility
import com.example.room.db.DataBase
import com.example.room.db.UserDao
import dagger.Module
import dagger.Provides

/**
 * Created by Administrator on 2018/2/26.
 */
@Module
class RoomModel(private val mContext:Context) {
    @Provides
    fun provideNumber():Number = 12

    @Provides
    fun provideDataBase():DataBase = Room.databaseBuilder(mContext,DataBase::class.java,"myUser.db").build()

    @Provides
    fun provideUserDao(db:DataBase):UserDao = db.userDao()

}