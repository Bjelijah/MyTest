package com.howell.mytest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ZoomControls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import camera.CameraInterface;
import camera.CameraInterface2;
import camera.ICameraInterface;

/**
 * Created by Administrator on 2017/6/21.
 */

public class CameraViewActivity extends Activity implements ICameraInterface {
    @BindView(R.id.btn_pic)Button picBtn;
    @BindView(R.id.btn_zoomIn)Button zoomInBtn;
    @BindView(R.id.btn_zoomOut)Button zoomOutBtn;
    @BindView(R.id.zoom_control)ZoomControls zoomControls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camerview);
        ButterKnife.bind(this);
        zoomControls.setIsZoomInEnabled(true);
        zoomControls.setIsZoomOutEnabled(true);
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("123","zoom in");
                CameraInterface.getInstance().zoomIn();
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("123","zoom out");
                CameraInterface.getInstance().zoomOut();
            }
        });
        CameraInterface.getInstance().setCameraInterfaceCallback(this);
    }

    @OnClick(R.id.btn_zoomIn) void clickZoomIn(){
        Log.i("123","zoom in");

    }

    @OnClick(R.id.btn_zoomOut) void clickZoomOut(){
        Log.i("123","zoom out");

    }

    @OnClick(R.id.btn_pic) void clickTackPic(){
//        CameraInterface2.getInstance().doTakePic();
        CameraInterface.getInstance().doTakePicture();
    }


    @Override
    public void takePhotoFinish(Bitmap bitmap) {
        Log.e("123","we get bitmap");
    }
}
