package com.example.viewmodel.room

import android.content.Context
import android.util.Log
import com.example.room.dagger.DaggerRoomComponent
import com.example.room.dagger.RoomModel
import com.example.room.db.DataBase
import com.example.room.db.User
import com.example.room.db.UserDao
import com.example.viewmodel.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.Observable
import io.reactivex.Observable.create
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/26.
 */
class RoomViewModel:BaseViewModel {
    override fun onCreate() {
    }

    val mContext:Context

    val compositeDisposable = CompositeDisposable()

    var num = 0

    @Inject
    lateinit var number:Number

    @Inject
    lateinit var dao:UserDao

    @Inject
    lateinit var db:DataBase


    constructor(c: Context){
        this.mContext = c
        DaggerRoomComponent
                .builder()
                .roomModel(RoomModel(mContext))
                .build()
                .inject(this)
    }

    override
    fun onDestory(){
        compositeDisposable.dispose()
    }


    val onClickInsert = Action {
        Log.i("123","num=$number")
        num++
        var usr = User(name ="user: $num")

        Observable.fromCallable({dao.insertUser(usr)})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("123","insert ok")
                },{e->e.printStackTrace()},{
                    Log.i("123","insert finish")
                })

    }

    val onClickQuery = Action {
        dao.getAllUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("123","$it   ")
                    (0 until it.size).forEach { i-> Log.i("123","id: ${it[i].id}   name:${it[i].name}")}
                },
                        {e->e.printStackTrace()},
                        {Log.i("123","query finish")})
    }
}