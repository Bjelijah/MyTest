package camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;



import com.example.utils.JniUtil;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import camera.utils.DisplayUtil;


@SuppressWarnings("deprecation")
public class YUVGLSurfaceView extends ZoomableGLSurfaceView implements CameraInterface.CamOpenOverCallback,PreviewCallback {

	Context mContext;
	YV12Renderer mRenderer;
	DoFrameThread doFrameThread = null;
	AvcEncoder mAvcEncoder = null;
	byte [] mH264 = null;
	boolean isPreviewing = false;
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
	
	
	public YUVGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		setEGLContextClientVersion(2);
		mRenderer = new YV12Renderer(context, (GLSurfaceView)this,mHandler);
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		JniUtil.hwPlayerInit();
	}

	public void openCamera(){
		Log.i("123", "open carmera");
		new Thread(){
			public void run() {
				CameraInterface.getInstance().doOpenCamera(YUVGLSurfaceView.this);
			};
		}.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//open camera
		openCamera();
		JniUtil.hwPlayerPlay();
		super.surfaceCreated(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//stop camera
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
		super.surfaceDestroyed(holder);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// TODO Auto-generated method stub
		super.surfaceChanged(holder, format, w, h);
	}


	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		// camera display preview
		Log.i("123", "start preview");
		CameraInterface.getInstance().doStartPreview(YUVGLSurfaceView.this);

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
	public void onPreviewFrame(byte[] data, Camera camera) {//25frames spend 1600ms FIXME
		// TODO Auto-generated method stub
	
		CameraInterface.getInstance().recoveryCallbackData(data);//
		
//		CameraInterface.getInstance().updataSurfaceTexture();
	
		final Size size = camera.getParameters().getPreviewSize();
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
	
	public class FrameData{
		byte [] data;
		int w,h;
		public byte[] getData() {
			return data;
		}
		public void setData(byte[] data) {
			this.data = data;
		}
		public int getW() {
			return w;
		}
		public void setW(int w) {
			this.w = w;
		}
		public int getH() {
			return h;
		}
		public void setH(int h) {
			this.h = h;
		}
		public FrameData(byte[] data, int w, int h) {
			super();
			this.data = data;
			this.w = w;
			this.h = h;
		}
		
	}
	boolean [] bI = new boolean[1];
	public class DoFrameThread extends Thread {
		private Queue<FrameData> pushDataQueue =  new ArrayBlockingQueue<FrameData>(2);
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
					boolean useHw = false;
					if (useHw){
						int h264Len = mAvcEncoder.offerEncoder(data, mH264,bI);
						JniUtil.setH264Data(mH264, h264Len, w, h, bI[0]?1:0);
					}else{
						byte [] yuv420 = new byte[data.length];
//					DisplayUtil.YUV420SP2YUV420(data, yuv420, w, h);
						DisplayUtil.YV12toYUV420Planar(data, yuv420, w, h);
						JniUtil.YUVsetData(yuv420, yuv420.length,w, h);
					}

				


					
					try {//FIXME time spend on encode
						sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else{
					try {
//						Log.e("123", "no frame");
						sleep(40);
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
