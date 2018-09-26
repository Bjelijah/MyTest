package com.howell.mytest

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.netease.api.AudienceActivity
import com.netease.api.CaptureActivity
import com.netease.live.DemoCache
import com.netease.live.server.DemoServerHttpClient
import com.netease.live.server.entity.RoomInfoEntity
import com.netease.live.streaming.PublishParam


class LiveActivity : AppCompatActivity() {

    val QUALITY_HD = "HD"
    val QUALITY_SD = "SD"
    val QUALITY_LD = "LD"


    val instance by lazy { this }
    var mContext : Context?=null
    var mRoomId:String ?=null
    var mPushUrl:String ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)
        ButterKnife.bind(this)
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        destoryRoom()
    }


    private fun initData(){
        DemoCache.setContext(this)
    }

    @OnClick(R.id.live_login) fun onClickLogin(){
        login()
    }

    @OnClick(R.id.live_room) fun onClickLiveRoom(){
        //
        live()
    }

    @OnClick(R.id.live_watch) fun onClickWatch(){
        getRoom()
    }

    private fun login(){
        DemoServerHttpClient.getInstance().login("vasili123","123456",object :DemoServerHttpClient.DemoServerHttpCallback<Void>{
            override fun onSuccess(t: Void?) {
                Log.i("123","on login success")
                Toast.makeText(instance,"登入成功",Toast.LENGTH_SHORT).show()
            }

            override fun onFailed(code: Int, errorMsg: String?) {

                Log.e("123","on Failed  code=$code   errMsg=$errorMsg")
            }
        })

    }

    private fun live(){
        //直播
        //登入
        createRoom {
            CaptureActivity.startCaptureActivity(instance,mRoomId,it)
        }
//        CaptureActivity.startCaptureActivity(this)
    }

    private fun createRoom(onSuccess:(p:PublishParam)->Unit){
        DemoServerHttpClient.getInstance().createRoom(instance,object :DemoServerHttpClient.DemoServerHttpCallback<RoomInfoEntity>{
            override fun onFailed(code: Int, errorMsg: String?) {
                Log.e("123","onFailed  code=$code     errMsg=$errorMsg")
            }

            override fun onSuccess(t: RoomInfoEntity?) {
                Log.i("123","room = $t")
                mRoomId = "${t?.roomid}"
                mPushUrl = t?.pushUrl
                DemoCache.setRoomInfoEntity(t)
                var param = PublishParam()
                param.pushUrl    = mPushUrl
                param.definition = QUALITY_SD
                param.openVideo  = true
                param.openAudio  = true
                param.useFilter  = false
                param.faceBeauty = false
                onSuccess(param)
            }
        })


    }


    private fun getRoom(){


        DemoServerHttpClient.getInstance().getRoomInfo(0,"56666794",object :DemoServerHttpClient.DemoServerHttpCallback<RoomInfoEntity>{
            override fun onSuccess(t: RoomInfoEntity?) {
                Log.i("123","onGetRoomSuccess   t=$t")
                DemoCache.setRoomInfoEntity(t)
                AudienceActivity.startActivity(instance,t?.rtmpPullUrl,true)
            }

            override fun onFailed(code: Int, errorMsg: String?) {
                Log.e("123","onGetRoomFailed  code=$code   errMsg=$errorMsg")
            }
        })



    }

    private fun destoryRoom(){
        DemoServerHttpClient.getInstance().anchorLeave(mRoomId,object :DemoServerHttpClient.DemoServerHttpCallback<Void>{
            override fun onSuccess(t: Void?) {
                Log.i("123","on destory room success")
            }

            override fun onFailed(code: Int, errorMsg: String?) {
                Log.e("123","on destory error  code=$code    errMsg=$errorMsg")
            }
        })
    }

}