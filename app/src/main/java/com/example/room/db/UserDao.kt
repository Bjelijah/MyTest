package com.example.room.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable

/**
 * Created by Administrator on 2018/2/27.
 */
@Dao
interface UserDao {
    @Query("Select * from user")
    fun getAllUser():Flowable<List<User>>

    @Query("Select * from user where name = :userName")
    fun getUserByName(userName : String):Flowable<User>

    @Insert(onConflict = REPLACE)
    fun insertUser(user: User)

    @Update(onConflict = REPLACE)
    fun updateUser(user:User)

    @Delete
    fun delectUser(user:User)
}