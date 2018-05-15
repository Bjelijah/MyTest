package com.example.room.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Administrator on 2018/2/27.
 */
@Database(entities = arrayOf(User::class),version = 1,exportSchema = false)
abstract class DataBase:RoomDatabase() {
      abstract fun userDao():UserDao
}