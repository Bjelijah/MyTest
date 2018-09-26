package com.netease.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.netease.live.liveplayer.LivePlayerController;
import com.netease.live.liveplayer.NEVideoView;
import com.netease.live.liveplayer.PlayerContract;
import com.netease.live.liveplayer.VideoConstant;
import com.netease.nim.uikit.R;

public class AudienceActivity extends AppCompatActivity implements PlayerContract.PlayerUi {
    NEVideoView mVideoView;
    private String mUrl; // 拉流地址
    LivePlayerController mediaPlayController;

    public static final String EXTRA_URL = "extra_url";
    public static final String IS_LIVE = "is_live";
    public static final String IS_SOFT_DECODE = "is_soft_decode";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience);
        initView();
        initAudienceParam();
    }

    private void initView(){
        mVideoView = findViewById(R.id.video_view);
    }
    private void initAudienceParam() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(EXTRA_URL);
        boolean isLive = intent.getBooleanExtra(IS_LIVE, true);
        boolean isSoftDecode = intent.getBooleanExtra(IS_SOFT_DECODE, true);
        mediaPlayController = new LivePlayerController(this, this, mVideoView, null, mUrl, VideoConstant.VIDEO_SCALING_MODE_FILL_BLACK, isLive, !isSoftDecode);
        mediaPlayController.initVideo();
    }

    public void stopWatching() {
        mediaPlayController.stopPlayback();
      //  showLoading(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayController.onActivityResume();
    }

    @Override
    protected void onPause() {
        mediaPlayController.onActivityPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mediaPlayController.onActivityDestroy();
        super.onDestroy();
    }

    @Override
    public void onBufferingUpdate() {

    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public boolean onCompletion() {
        mediaPlayController.restart();
        return true;
    }

    @Override
    public boolean onError(String errorInfo) {
        Log.e("123","onError    errorInfo="+errorInfo);
        return true;
    }

    @Override
    public void setFileName(String name) {
        Log.i("123","set File name="+name);
    }

    @Override
    public void showLoading(boolean show) {
        Log.i("123","show  loading"+show);
    }

    @Override
    public void showAudioAnimate(boolean b) {
        Log.i("123","show audio animate"+b);
    }


    public static void startActivity(Context c,String url,boolean isSoftDecode){
        Intent intent = new Intent(c,AudienceActivity.class);
        intent.putExtra(IS_LIVE, true); //观众默认为直播, 另一个种模式为点播.
        intent.putExtra(IS_SOFT_DECODE, isSoftDecode);
        intent.putExtra(EXTRA_URL, url);
        c.startActivity(intent);
    }
}
