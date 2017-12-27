package camera;



import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glview.texture.gles.EglContextWrapper;
import com.chillingvan.canvasgl.glview.texture.gles.GLThread;

public class CameraTextureView extends ZoomableTextureView implements SurfaceTextureListener,CameraInterface.CamOpenOverCallback {

	private static final String TAG = "CameraTextureView";  
	Context mContext;  
	SurfaceTexture mSurface;  

	public CameraTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.setSurfaceTextureListener(this);
	}

	@Override
	public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onSurfaceTextureAvailable...");  
		mSurface = surface;




		new Thread(){
			public void run() {
//				CameraInterface.getInstance().doOpenCamera(CameraTextureView.this);
				CameraInterface2.getInstance().doOpenCamera(CameraTextureView.this.getContext(),surface);
			};
		}.start();
		
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onSurfaceTextureSizeChanged...");  
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onSurfaceTextureDestroyed...");  
//		CameraInterface.getInstance().doStopCamera();
		CameraInterface2.getInstance().doCloseCamera();
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		//Log.i(TAG, "onSurfaceTextureUpdated...");
	}

	
	
	
	public SurfaceTexture getSurfaceTexture(){
		return mSurface;
	}

	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		float previewRate = 1.33f;
		CameraInterface.getInstance().doStartPreview(mSurface, previewRate);
	}

}
