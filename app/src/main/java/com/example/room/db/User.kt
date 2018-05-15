package com.example.room.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Administrator on 2018/2/27.
 */


@Entity(tableName = "user")
data class User(@ColumnInfo(name = "completed_flag") var completed:Boolean = false,
                @ColumnInfo(name = "name") var name:String) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}