package com.howell.mytest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/15.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class FlashActivity extends Activity {

    private CameraManager manager;
    private Camera camera = null;
    private Camera.Parameters parameters = null;
    boolean mFlashlightEnabled = false;


    Handler mHandler = new Handler(){

    };

    CameraManager.TorchCallback mtorchCallback = new CameraManager.TorchCallback() {
        @Override
        public void onTorchModeUnavailable(@NonNull String cameraId) {
            Log.i("123","onTorchModeUnavailable   cameraID="+cameraId);

            super.onTorchModeUnavailable(cameraId);
        }

        @Override
        public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
            Log.i("123","onTorchModeChanged   cameraID="+cameraId+"  enabled="+enabled);

            super.onTorchModeChanged(cameraId, enabled);
        }
    };


    @BindView(R.id.button) Button btnOpen;
    @BindView(R.id.button2) Button btnClose;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        ButterKnife.bind(this);
        try {
            init();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    void init() throws CameraAccessException {
        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String [] list = manager.getCameraIdList();
        for (String str:list){
            Log.i("123","str:  "+str);
        }
        manager.registerTorchCallback(mtorchCallback,mHandler);
    }

    void setFlashLight(boolean enable){

        synchronized (this){
            if (mFlashlightEnabled!=enable){
                mFlashlightEnabled = enable;
                try {
                    manager.setTorchMode("0",enable);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.button) void clickOpen(){
        setFlashLight(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.button2) void clickClose(){
        setFlashLight(false);
    }

}
