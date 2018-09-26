package com.netease.api;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.netease.live.streaming.CapturePreviewContract;
import com.netease.live.streaming.CapturePreviewController;
import com.netease.live.streaming.PublishParam;
import com.netease.nim.uikit.R;
import com.netease.vcloud.video.render.NeteaseView;

public class CaptureActivity extends AppCompatActivity implements CapturePreviewContract.CapturePreviewUi {
    CapturePreviewController controller;
    private NeteaseView normalSurfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        initView();
        initData();

    }

    private void initView(){
        normalSurfaceView = findViewById(R.id.live_normal_view);
        findViewById(R.id.live_start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.liveStartStop();
            }
        });
    }

    private  void initData(){
        controller = new CapturePreviewController(this,this);
        controller.handleIntent(getIntent());
    }


    @Override
    protected void onStart() {
        super.onStart();
        controller.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.onPause();
    }

    public static void startCaptureActivity(Context c,String roomId,PublishParam param){
        Intent intent = new Intent(c,CaptureActivity.class);
        intent.putExtra(CapturePreviewController.EXTRA_PARAMS, param);
        c.startActivity(intent);

    }

    @Override
    public void setStartBtnClickable(boolean clickable) {
        Log.i("123","on start btn click able   :"+clickable);
    }

    @Override
    public void checkInitVisible(PublishParam mPublishParam) {
        Log.i("123","public param="+mPublishParam);
    }

    @Override
    public void setSurfaceView(boolean hasFilter) {
        Log.i("123","has Filter="+hasFilter);
    }

    @Override
    public void setPreviewSize(boolean hasFilter, int previewWidth, int previewHeight) {
        Log.i("123","size  "+hasFilter+"  width="+previewWidth+"   height="+previewHeight);
    }

    @Override
    public View getDisplaySurfaceView(boolean hasFilter) {
        return normalSurfaceView;
    }

    @Override
    public void onStartLivingFinished() {
        Log.i("123","onStartLivingFinished");
    }

    @Override
    public void onStopLivingFinished() {
        Log.i("123","onStopLivingFinish");
    }

    @Override
    public void setAudioBtnState(boolean isPlay) {
        Log.i("123","set AudioBtn State  isPlay:"+isPlay);
    }

    @Override
    public void setVideoBtnState(boolean isPlay) {
        Log.i("123","setVideo Btn State isPlay="+isPlay);
    }

    @Override
    public void setFilterSeekBarVisible(boolean visible) {
        Log.i("123","setFilter SeekBar Visible "+visible);
    }

    @Override
    public void showAudioAnimate(boolean b) {
        Log.i("123","show AudioAnimate   "+b);
        controller.liveStartStop();
    }

    @Override
    public void onDisconnect() {
        Log.i("123","onDisconnect");
    }

    @Override
    public void normalFinish() {
        Log.i("123","normalFinish");
    }

    @Override
    public void onStartInit() {
        Log.i("123","onStart Init");
    }

    @Override
    public void onCameraPermissionError() {
        Log.i("123","onCameraPermissionError");
        showConfirmDialog("无法打开相机", "可能没有相关的权限,请开启权限后重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, null);
    }

    @Override
    public void onAudioPermissionError() {
        Log.i("123","onAudioPermisssionError");
        showConfirmDialog("无法开启录音", "可能没有相关的权限,请开启权限后重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, null);
    }


    protected void showConfirmDialog(String title, String message, DialogInterface.OnClickListener okEvent, DialogInterface.OnClickListener cancelEvent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok,
                okEvent);
        if (cancelEvent != null) {
            builder.setNegativeButton(R.string.cancel,
                    cancelEvent);
        }
        builder.setCancelable(false);
        builder.show();
    }
    public void destroyController() {
        controller.tryToStopLivingStreaming();
        controller.onDestroy();
    }
}
