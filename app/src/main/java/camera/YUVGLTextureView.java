package camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import com.chillingvan.canvasgl.glview.texture.GLViewRenderer;
import com.example.utils.JniUtil;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import camera.utils.DisplayUtil;

/**
 * Created by Administrator on 2017/6/22.
 */

public class YUVGLTextureView extends ZoomableTextureView implements CameraInterface.CamOpenOverCallback, Camera.PreviewCallback, TextureView.SurfaceTextureListener {

    SurfaceTexture mSurface;

    Context mContext;
    GLESRendererImpl mRenderer;
    DoFrameThread doFrameThread = null;

    AvcEncoder mAvcEncoder = null;
    boolean isPreviewing = false;
    byte [] mH264 = null;
    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {

        };
    };


    public YUVGLTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mRenderer = new GLESRendererImpl(context,this,mHandler);
        setRenderer(mRenderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);

        JniUtil.hwPlayerInit();
        setSurfaceTextureListener(this);
    }

    @Override
    public void cameraHasOpened() {
        Log.i("123", "start preview");
        CameraInterface.getInstance().doStartPreview(YUVGLTextureView.this);
        isPreviewing = true;
        if(doFrameThread == null){
            doFrameThread = new DoFrameThread();
            doFrameThread.start();
        }
        if (mAvcEncoder == null) {
            mAvcEncoder = CameraInterface.getInstance().getAvcEncoder();
            mH264 = CameraInterface.getInstance().getH264Buffer();
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        CameraInterface.getInstance().recoveryCallbackData(data);//

//		CameraInterface.getInstance().updataSurfaceTexture();

        final Camera.Size size = camera.getParameters().getPreviewSize();
        int w = size.width;
        int h = size.height;
        int len = w*h+w*h/2;
//		Log.i("123", "onpreviewframe   len="+data.length+" w="+w+" h="+h+"   buf="+len);
//		JniUtil.YUVsetData(_data, _data.length, size.width, size.height);

        FrameData frameData = new FrameData(data, w, h);
        if (doFrameThread!=null) {
            doFrameThread.pushData(frameData);
        }
    }


    boolean [] bI = new boolean[1];

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        super.onSurfaceTextureAvailable(surface,width,height);
        Log.e("123","onSurfaceTextureAvailable");
        new Thread(){
            public void run() {
                CameraInterface.getInstance().doOpenCamera(YUVGLTextureView.this);
            };
        }.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        super.onSurfaceTextureSizeChanged(surface,width,height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        CameraInterface.getInstance().doStopCamera();
        isPreviewing = false;
        if (doFrameThread!=null) {
            try {
                doFrameThread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            doFrameThread = null;
        }
        mAvcEncoder.close();
        return super.onSurfaceTextureDestroyed(surface);
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        super.onSurfaceTextureUpdated(surface);
    }

    public class DoFrameThread extends Thread{
        private Queue<FrameData> pushDataQueue =  new ArrayBlockingQueue<FrameData>(5);
        public void pushData(FrameData frameData){
            pushDataQueue.offer(frameData);

        }
        private FrameData popData(){

            return pushDataQueue.poll();
        }

        public DoFrameThread(){
        }
        private int frameNum = 0;
        private long startTime = 0;
        private long endTime = 0;
        private boolean bDebug = true;
        @SuppressLint("NewApi")
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(isPreviewing){
                FrameData frameData = popData();
                if (frameData!=null) {
//					Log.e("123", "do one frame");
                    byte [] data = frameData.getData();
                    int w = frameData.getW();
                    int h = frameData.getH();
                    if (bDebug) {
                        if (frameNum==0) {
                            startTime = System.currentTimeMillis();
                            Log.i("123", "start ="+startTime);
                        }
                        frameNum++;
                        if (frameNum==25) {
                            endTime = System.currentTimeMillis();
                            Log.i("123", "end ="+endTime);
                            long spendTime = endTime-startTime;
                            Log.e("123", "25 frame spend time="+spendTime);
                            frameNum=0;
                        }
                    }

					byte [] yuv420 = new byte[data.length];
//					DisplayUtil.YUV420SP2YUV420(data, yuv420, w, h);
					DisplayUtil.YV12toYUV420Planar(data, yuv420, w, h);
					JniUtil.YUVsetData(yuv420, yuv420.length,w, h);

//                    YuvImage image = new YuvImage(data, ImageFormat.YV12,w,h,null);

//                    int h264Len = mAvcEncoder.offerEncoder(data, mH264,bI);
//                    JniUtil.setH264Data(mH264, h264Len, w, h, bI[0]?1:0);

                    try {//FIXME time spend on encode
                        sleep(0);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }else{
                    try {
//						Log.e("123", "no frame");
                        sleep(0);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            super.run();
        }
    }
}
