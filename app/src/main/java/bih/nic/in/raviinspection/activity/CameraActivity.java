package bih.nic.in.raviinspection.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.utilities.CameraPreview;
import bih.nic.in.raviinspection.utilities.GlobalVariables;
import bih.nic.in.raviinspection.utilities.MarshmallowPermission;
import bih.nic.in.raviinspection.utilities.Utiilties;



@SuppressLint("NewApi")
public class CameraActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mPreview;

    Location LastLocation = null;
    LocationManager mlocManager = null;

    AlertDialog.Builder alert;

    private final int UPDATE_ADDRESS = 1;
    private final int UPDATE_LATLNG = 2;

    private Handler mHandler;
    private static byte[] CompressedImageByteArray;
    private static Bitmap CompressedImage;
    private boolean isTimerStarted = false;
    Chronometer chronometer;

    MarshmallowPermission permission;
    boolean init;
    int camType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        chronometer = (Chronometer) findViewById(R.id.chronometer1);
        isTimerStarted = false;
        if (Utiilties.isfrontCameraAvalable() && getIntent().getStringExtra("KEY_PIC").equals("1")) {
            camType = CameraInfo.CAMERA_FACING_FRONT;
        } else {
            camType = CameraInfo.CAMERA_FACING_BACK;
        }
       /* if (camType == CameraInfo.CAMERA_FACING_BACK) {
            camType = CameraInfo.CAMERA_FACING_FRONT;

        } else {
            camType = CameraInfo.CAMERA_FACING_BACK;

        }*/
		/*mCamera = getCameraInstance();
        Parameters param = mCamera.getParameters();

		List<Size> sizes = param.getSupportedPictureSizes();
		int iTarget = 0;
		for (int i = 0; i < sizes.size(); i++) {
			Camera.Size size = sizes.get(i);

			if (size.width < 1000) {
				iTarget = i;
				break;
			}
		}
		param.setJpegQuality(20);
		param.setPictureSize(sizes.get(iTarget).width,
				sizes.get(iTarget).height);
		mCamera.setParameters(param);
		alert = new AlertDialog.Builder(this);
		Display getOrient = getWindowManager().getDefaultDisplay();

		int rotation = getOrient.getRotation();

		switch (rotation) {
		case Surface.ROTATION_0:
			mCamera.setDisplayOrientation(90);
			break;
		case Surface.ROTATION_90:
			break;
		case Surface.ROTATION_180:
			break;
		case Surface.ROTATION_270:
			mCamera.setDisplayOrientation(90);
			break;
		default:
			break;
		}


		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);*/


		/*if (!GlobalVariables.isOfflineGPS) {
			mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

				mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, (float) 0.01, listener);
				mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01,listener);
			}

		}
*/


	/*	mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case UPDATE_ADDRESS:
				case UPDATE_LATLNG:
					String[] LatLon = ((String) msg.obj).split("-");
					TextView tv_Lat = (TextView) findViewById(R.id.tvLat);
					TextView tv_Lon = (TextView) findViewById(R.id.tvLon);

					tv_Lat.setText(LatLon[0]);
					tv_Lon.setText(LatLon[1]);
					Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);

					if (!isTimerStarted) {
						startTimer();
					}

					break;
				}
			}
		};*/
    }


    /*@Override
    protected void onResume() {
        init = false;
        try {
            if (!init) initializeCamera();
        } catch (Exception e) {
        }

        permission = new MarshmallowPermission(this, Manifest.permission.CAMERA);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera();
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera();
        }

        try {
            if (!init) initializeCamera();
        } catch (Exception e) {
        }


        permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera();
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera();
        }

        super.onResume();
    }*/


    private void locationManager() {
        if (!GlobalVariables.isOfflineGPS) {
            mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.01, listener);

                mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, listener);


            }

        }


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case UPDATE_ADDRESS:
                    case UPDATE_LATLNG:
                        String[] LatLon = ((String) msg.obj).split("-");
                        TextView tv_Lat = (TextView) findViewById(R.id.tvLat);
                        TextView tv_Lon = (TextView) findViewById(R.id.tvLon);

                        tv_Lat.setText(LatLon[0]);
                        tv_Lon.setText(LatLon[1]);
                        Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);

                        if (!isTimerStarted) {
                            startTimer();
                        }

                        break;
                }
            }
        };

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

        init = false;
        permission = new MarshmallowPermission(this, Manifest.permission.CAMERA);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera(camType);
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera(camType);
        }

        permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission.result == -1 || permission.result == 0) {
            try {
                if (!init) initializeCamera(camType);
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            if (!init) initializeCamera(camType);
        }

        super.onResume();

    }



   /* private void initializeCamera() {
        init = true;
        mCamera = getCameraInstance();
        Parameters param = mCamera.getParameters();
        List<Camera.Size> sizes = param.getSupportedPictureSizes();
        int iTarget = 0;
        for (int i = 0; i < sizes.size(); i++) {
            Camera.Size size = sizes.get(i);

            if (size.width >= 1024 && size.width <= 1280) {
                iTarget = i;
                break;
            } else {
                if (size.width < 1024) {
                    iTarget = i;

                }
            }

        }
        param.setJpegQuality(20);
        param.setPictureSize(sizes.get(iTarget).width,
                sizes.get(iTarget).height);
        mCamera.setParameters(param);
        alert = new AlertDialog.Builder(this);
        Display getOrient = getWindowManager().getDefaultDisplay();

        int rotation = getOrient.getRotation();

        switch (rotation) {
            case Surface.ROTATION_0:
                mCamera.setDisplayOrientation(90);
                break;
            case Surface.ROTATION_90:
                break;
            case Surface.ROTATION_180:
                break;
            case Surface.ROTATION_270:
                mCamera.setDisplayOrientation(90);
                break;
            default:
                break;
        }
        try {

            mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);

        } catch (Exception e) {
            finish();
        }
        locationManager();
    }*/

    private void initializeCamera(int camType) {

        init = true;
        chronometer = (Chronometer) findViewById(R.id.chronometer1);
        isTimerStarted = false;

        mCamera = getCameraInstance(camType);
        Parameters param;
        if (mCamera != null) {
            param = mCamera.getParameters();


            List<Camera.Size> sizes = param.getSupportedPictureSizes();
            int iTarget = 0;
            for (int i = 0; i < sizes.size(); i++) {
                Camera.Size size = sizes.get(i);
			/*if (size.width < 1000) {
				iTarget = i;
				break;
			}*/


                if (size.width >= 1024 && size.width <= 1280) {
                    iTarget = i;
                    break;
                } else {
                    if (size.width < 1024) {
                        iTarget = i;

                    }
                }

            }
            param.setJpegQuality(100);
            param.setPictureSize(sizes.get(iTarget).width,
                    sizes.get(iTarget).height);
            mCamera.setParameters(param);
            alert = new AlertDialog.Builder(this);
            Display getOrient = getWindowManager().getDefaultDisplay();

            int rotation = getOrient.getRotation();

            switch (rotation) {
                case Surface.ROTATION_0:
                    mCamera.setDisplayOrientation(90);
                    break;
                case Surface.ROTATION_90:

                    break;
                case Surface.ROTATION_180:
                    mCamera.setDisplayOrientation(0);

                    break;
                case Surface.ROTATION_270:
                    mCamera.setDisplayOrientation(90);
                    break;
                default:
                    break;
            }


            try {

                mPreview = new CameraPreview(this, mCamera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.addView(mPreview);


            } catch (Exception e) {
                finish();
            }
            locationManager();
        }

    }
    public void startTimer() {
        // chronometer=(Chronometer)findViewById(R.id.chronometer1);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        isTimerStarted = true;
		/*
		 * long elapsedMillis = SystemClock.elapsedRealtime() -
		 * chronometer.getBase(); Toast.makeText(CameraActivity.this,
		 * "Elapsed milliseconds: " + elapsedMillis, Toast.LENGTH_SHORT).btnShowPendingEntries();
		 */
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public static Camera getCameraInstance(int cameraType) {
        // Camera c = null;
        try {

            int numberOfCameras = Camera.getNumberOfCameras();
            int cameraId = 0;
            for (int i = 0; i < numberOfCameras; i++) {
                CameraInfo info = new CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == cameraType) {
                    // Log.d(DEBUG_TAG, "Camera found");
                    cameraId = i;
                    break;

                }
            }

            return Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_camera, menu);
        return true;
    }

    private void updateUILocation(Location location) {

        Message.obtain(
                mHandler,
                UPDATE_LATLNG,
                new DecimalFormat("#.0000000").format(location.getLatitude())
                        + "-"
                        + new DecimalFormat("#.0000000").format(location
                        .getLongitude())).sendToTarget();

    }


    private final LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // A new location update is received. Do something useful with it.
            // Update the UI with
            // the location update.

            if (GlobalVariables.isOfflineGPS == false) {
                LastLocation = location;
                updateUILocation(location);

                if (location.getLatitude() > 0.0) {
                    Button takePhoto = (Button) findViewById(R.id.btnCapture);
                    takePhoto.setText("Take Photo");
                    takePhoto.setEnabled(true);
                }

            } else {
                LastLocation.setLatitude(0.0);
                LastLocation.setLongitude(0.0);
                updateUILocation(LastLocation);
                Button takePhoto = (Button) findViewById(R.id.btnCapture);
                takePhoto.setText("Take Photo");
                takePhoto.setEnabled(true);
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

















    PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                Log.d(TAG, "Start");
                Bitmap bmp = BitmapFactory
                        .decodeByteArray(data, 0, data.length);

//                Matrix mat = new Matrix();
//                mat.postRotate(-90);
//                Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0,
//                        bmp.getWidth(), bmp.getHeight(), mat, true);


                Matrix mat = new Matrix();
                if (camType == android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    mat.postRotate(-90);

                } else mat.postRotate(90);

                Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0,
                        bmp.getWidth(), bmp.getHeight(), mat, true);
                // @SuppressWarnings("deprecation")
                // BitmapDrawable bmd = new BitmapDrawable(bMapRotate);

                // image.setImageBitmap(bMapRotate);
                // image.setImageDrawable(bmd);

                setCameraImage(Utiilties
                        .GenerateThumbnail(bMapRotate, 300, 400));

            } catch (Exception ex) {
                Log.d(TAG, ex.getMessage());
            }
        }
    };
    private static final String TAG = "MyActivity";
    ShutterCallback shutterCallback = new ShutterCallback() {
        @Override
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
        }
    };
    /**
     * Handles data for raw picture
     */
    PictureCallback rawCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
        }
    };

    public void onCaptureClick(View view) {
        // System.gc();

        mCamera.takePicture(shutterCallback, rawCallback, mPicture);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();


        // mCamera.takePicture(null, null, mPicture);

    }

    private void setCameraImage(Bitmap bitmap) {

        String DisplayText1 = "", DisplayText2 = "", DisplayText3 = "", DisplayText4 = "";
        if (LastLocation != null) {
            DisplayText1 = "Lat : "
                    + String.valueOf(new DecimalFormat("#.0000000")
                    .format(LastLocation.getLatitude()));
            DisplayText2 = "Lon : "
                    + String.valueOf(new DecimalFormat("#.0000000")
                    .format(LastLocation.getLongitude()));
            DisplayText3 = Utiilties.getDateString("MMM dd, yyyy hh:mm a");

        } else {
            // DisplayText = "Lat : NA Lon : NA ";
        }

        // DisplayText4 = GlobalVariables.Pid + " (" + GlobalVariables.area +
        // ")";
        DisplayText4 = "";

        Display getOrient = getWindowManager().getDefaultDisplay();
        int rotation = getOrient.getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                break;
            case Surface.ROTATION_270:
                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);

                break;
            case Surface.ROTATION_90:
                break;
            case Surface.ROTATION_180:
                break;
            default:
                break;
        }
        // bitmap = Utiilties.GenerateThumbnail(bitmap, 300, 400);
        bitmap = Utiilties.DrawText(bitmap, DisplayText1, DisplayText2,
                DisplayText3, DisplayText4);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);

        byte[] byte_arr = stream.toByteArray();
        CompressedImageByteArray = byte_arr;

        bitmap.recycle();

		/*
		 * ImageData RetData = new ImageData(); RetData.Image = CompressedImage;
		 * RetData.CapturedLocation = LastLocation; RetData.CapturedDate = new
		 * Date(0);
		 *
		 * RetData.ImageNumber = Integer.parseInt(getIntent().getStringExtra(
		 * "KEY_PIC"));
		 */
        Intent returnIntent = new Intent();
        returnIntent.putExtra("CapturedImage", CompressedImageByteArray);
        returnIntent.putExtra("Lat", new DecimalFormat("#.0000000")
                .format(LastLocation.getLatitude()));
        returnIntent.putExtra("Lng", new DecimalFormat("#.0000000")
                .format(LastLocation.getLongitude()));
        returnIntent.putExtra("CapturedTime", Utiilties.getDateString());
        returnIntent.putExtra("KEY_PIC",
                Integer.parseInt(getIntent().getStringExtra("KEY_PIC")));
        // returnIntent.putExtra("ss", 0);
        setResult(RESULT_OK, returnIntent);

        finish();

    }

    public static byte[] getCompressedImage() {
        return CompressedImageByteArray;
    }

    public static void setCompressedImage(byte[] compressedImageByteArray) {
        CompressedImageByteArray = compressedImageByteArray;
    }

    public static Bitmap getCompressedBitmap() {
        return CompressedImage;
    }

    public static void setCompressedBitmap(Bitmap compressedImage) {
        CompressedImage = compressedImage;
    }

}

//	MarshmallowPermission permission;
//
//	Button btnCamType;
//	Button takePhoto;
//
//	boolean init;
//	boolean backCam=true;
//	int camType;
//
//
//
//	FrameLayout preview;
//
//	TextView tv_Lat,tv_Lon,tv_ac;
//
//	GoogleApiClient googleApiClient,mGoogleApiClint;
//	private GoogleMap mMap;
//	double latitude=0.0,longitude=0.0;
//
//	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
//	private LocationRequest mLocationRequest;
//
//	@Override
//	protected void onResume() {
//
//		init=false;
//		 permission=new MarshmallowPermission(this, Manifest.permission.CAMERA);
//			if(permission.result==-1 || permission.result==0)
//			{
//				try
//				{
//					if(!init) initializeCamera(camType);
//				}catch(Exception e){}
//			}
//			else if(permission.result==1)
//			{
//				if(!init) initializeCamera(camType);
//			}
//
//		permission=new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//		if(permission.result==-1 || permission.result==0)
//		{
//			try
//			{
//				if(!init) initializeCamera(camType);
//			}catch(Exception e){}
//		}
//		else if(permission.result==1)
//		{
//			if(!init) initializeCamera(camType);
//		}
//
//				super.onResume();
//		mGoogleApiClint.connect();
//
//	}
//
//
//
//
//	private Camera mCamera;
//	private CameraPreview mPreview;
//
//	Location LastLocation = null;
//	LocationManager mlocManager = null;
//
//	AlertDialog.Builder alert;
//
//
//	private final int UPDATE_ADDRESS = 1;
//	private final int UPDATE_LATLNG = 2;
//	private static final String TAG = "MyActivity";
//	private static byte[] CompressedImageByteArray;
//	private static Bitmap CompressedImage;
//	private boolean isTimerStarted = false;
//	Chronometer chronometer;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_camera);
//
//		 tv_Lat = (TextView) findViewById(R.id.tvLat);
//		 tv_Lon = (TextView) findViewById(R.id.tvLon);
//		chronometer = (Chronometer) findViewById(R.id.chronometer1);
//		isTimerStarted = false;
//
//		mGoogleApiClint = new GoogleApiClient.Builder(this)
//				// The next two lines tell the new client that “this” current class will handle connection stuff
//				.addConnectionCallbacks(this)
//				.addOnConnectionFailedListener(this)
//				//fourth line adds the LocationServices API endpoint from GooglePlayServices
//				.addApi(LocationServices.API)
//				.build();
//		mGoogleApiClint.connect();
//
//		// Create the LocationRequest object
//		mLocationRequest = LocationRequest.create()
//				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//				.setInterval(10 * 1000)        // 10 seconds, in milliseconds
//				.setFastestInterval(1 * 1000); // 1 second, in milliseconds
//
//
//		btnCamType=(Button)findViewById(R.id.btnCamType);
//		/*if (Utiilties.isfrontCameraAvalable()&&getIntent().getStringExtra("KEY_PIC").equals("2")) {
//			camType = CameraInfo.CAMERA_FACING_FRONT;
//		}else{*/
//			camType = CameraInfo.CAMERA_FACING_BACK;
//		//}
//		preview = (FrameLayout) findViewById(R.id.camera_preview);
//
//		takePhoto = (Button) findViewById(R.id.btnCapture);
//
//		//takePhoto.setEnabled(true);
//
//
//		btnCamType.setOnClickListener(new OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v) {
//				if(mCamera!=null){
//					mCamera.stopPreview();
//
//
//
//		        }
//
//
//				if(camType== CameraInfo.CAMERA_FACING_BACK)
//				{
//					camType= CameraInfo.CAMERA_FACING_FRONT;
//
//				}
//				else
//					{
//					camType= CameraInfo.CAMERA_FACING_BACK;
//
//					}
//
//			       preview.removeAllViews();
//
//				initializeCamera(camType);
//
//
//			}
//
//		});
//
//
//	}
//
//
//
//
//	private void initializeCamera(int camType)
//	{
//
//		init=true;
//
//
//
//		mCamera = getCameraInstance(camType);
//		Parameters param;
//		if(mCamera!=null)
//		{
//		param= mCamera.getParameters();
//
//
//
//		List<Size> sizes = param.getSupportedPictureSizes();
//		int iTarget = 0;
//		for (int i = 0; i < sizes.size(); i++) {
//			Size size = sizes.get(i);
//			/*if (size.width < 1000) {
//				iTarget = i;
//				break;
//			}*/
//
//
//			if(size.width>=1024 && size.width<=1280)
//			{
//				iTarget=i;
//				break;
//			}
//			else
//			{
//				if (size.width < 1024) {
//					iTarget = i;
//
//				}
//			}
//
//		}
//		param.setJpegQuality(100);
//		param.setPictureSize(sizes.get(iTarget).width,
//				sizes.get(iTarget).height);
//		mCamera.setParameters(param);
//		alert = new AlertDialog.Builder(this);
//		Display getOrient = getWindowManager().getDefaultDisplay();
//
//		int rotation = getOrient.getRotation();
//
//		switch (rotation) {
//		case Surface.ROTATION_0:
//			mCamera.setDisplayOrientation(90);
//			break;
//		case Surface.ROTATION_90:
//
//			break;
//		case Surface.ROTATION_180:
//			mCamera.setDisplayOrientation(0);
//
//			break;
//		case Surface.ROTATION_270:
//			mCamera.setDisplayOrientation(90);
//			break;
//		default:
//			break;
//		}
//
//
//		try {
//
//
//
//
//			mPreview = new CameraPreview(this, mCamera);
//			preview.addView(mPreview);
//
//
//		} catch (Exception e) {
//			finish();
//		}
//			//locationManager();
//		}
//
//	}
//
//
//
//	public void startTimer() {
//		isTimerStarted = true;
//		chronometer.setBase(SystemClock.elapsedRealtime());
//		chronometer.start();
//
//
//	}
//
//
//	public static Camera getCameraInstance(int cameraType) {
//		// Camera c = null;
//		try {
//
//			int numberOfCameras = Camera.getNumberOfCameras();
//			int cameraId = 0;
//			for (int i = 0; i < numberOfCameras; i++) {
//				CameraInfo info = new CameraInfo();
//				Camera.getCameraInfo(i, info);
//				if (info.facing == cameraType) {
//					// Log.d(DEBUG_TAG, "Camera found");
//					cameraId = i;
//					break;
//
//				}
//			}
//
//			return Camera.open(cameraId); // attempt to get a Camera instance
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	PictureCallback mPicture = new PictureCallback() {
//		@Override
//		public void onPictureTaken(byte[] data, Camera camera) {
//			try {
//
//				Log.e("pic callback", "Yes");
//				Log.d(TAG, "Start");
//				Bitmap bmp = BitmapFactory
//						.decodeByteArray(data, 0, data.length);
//
//				Matrix mat = new Matrix();
//				if(camType== CameraInfo.CAMERA_FACING_FRONT)
//				{
//					mat.postRotate(-90);
//
//				}
//				else mat.postRotate(90);
//
//
//				Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0,
//						bmp.getWidth(), bmp.getHeight(), mat, true);
//				Date d = new Date(LastLocation.getTime());
//				String dat=d.toLocaleString();
//				Bitmap baddtext=Utiilties.DrawText(CameraActivity.this,bMapRotate,"Lat:"+Double.toString(LastLocation.getLatitude()),"Long:"+Double.toString(LastLocation.getLongitude())
//						,"Accurecy:"+Float.toString(LastLocation.getAccuracy()),"GpsTime:"+dat);
//				setCameraImage(Utiilties
//						.GenerateThumbnail(baddtext, 500,500));
//
//			} catch (Exception ex) {
//				Log.d(TAG, ex.getMessage());
//			}
//		}
//	};
//
//	ShutterCallback shutterCallback = new ShutterCallback() {
//		@Override
//		public void onShutter() {
//			Log.d(TAG, "onShutter'd");
//		}
//	};
//	/** Handles data for raw picture */
//	PictureCallback rawCallback = new PictureCallback() {
//		@Override
//		public void onPictureTaken(byte[] data, Camera camera) {
//			Log.d(TAG, "onPictureTaken - raw");
//		}
//	};
//
//	public void onCaptureClick(View view) {
//		// System.gc();
//
//		if(mCamera!=null)
//		mCamera.takePicture(shutterCallback, rawCallback, mPicture);
//
//		Log.e("pic taken", "Yes");
//
//		chronometer.setBase(SystemClock.elapsedRealtime());
//		chronometer.stop();
//		// mCamera.takePicture(null, null, mPicture);
//
//	}
//
//	private void setCameraImage(Bitmap bitmap) {
//
//
//		String DisplayText1 = "", DisplayText2 = "", DisplayText3 = "", DisplayText4 = "";
//		if (LastLocation == null) {
//			DisplayText1 = "Lat : "
//					+ String.valueOf(new DecimalFormat("#.0000000")
//							.format(LastLocation.getLatitude()));
//			DisplayText2 = "Lon : "
//					+ String.valueOf(new DecimalFormat("#.0000000")
//							.format(LastLocation.getLongitude()));
//			DisplayText3 = Utiilties.getDateString("MMM dd, yyyy hh:mm a");
//
//		} else {
//
//		}
//
//
//
//
//
//		Display getOrient = getWindowManager().getDefaultDisplay();
//		int rotation = getOrient.getRotation();
//		switch (rotation) {
//		case Surface.ROTATION_0:
//			break;
//		case Surface.ROTATION_270:
//			Matrix matrix = new Matrix();
//			matrix.postRotate(90);
//
//			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//					bitmap.getHeight(), matrix, true);
//
//			break;
//		case Surface.ROTATION_90:
//			break;
//		case Surface.ROTATION_180:
//			break;
//		default:
//			break;
//		}
//
//
//
//
//
//		// bitmap = Utiilties.GenerateThumbnail(bitmap, 300, 400);
//		bitmap = Utiilties.DrawText(CameraActivity.this,bitmap, DisplayText1, DisplayText2,
//				DisplayText3, DisplayText4);
//
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//
//		byte[] byte_arr = stream.toByteArray();
//		CompressedImageByteArray = byte_arr;
//
//		bitmap.recycle();
//
//
//		Intent returnIntent = new Intent();
//		returnIntent.putExtra("CapturedImage", CompressedImageByteArray);
//		returnIntent.putExtra("Lat", new DecimalFormat("#.0000000")
//			.format(latitude));
//		returnIntent.putExtra("Lng", new DecimalFormat("#.0000000")
//			.format(longitude));
//		returnIntent.putExtra("CapturedTime", Utiilties.getDateTime());
//		Date d = new Date(LastLocation.getTime());
//		String dat=d.toLocaleString();
//		returnIntent.putExtra("GPSTime", dat);
//		returnIntent.putExtra("KEY_PIC",
//				Integer.parseInt(getIntent().getStringExtra("KEY_PIC")));
//		// returnIntent.putExtra("ss", 0);
//		setResult(RESULT_OK, returnIntent);
//		Log.e("Set camera image", "Yes");
//
//		finish();
//
//	}
//
//	public static byte[] getCompressedImage() {
//		return CompressedImageByteArray;
//	}
//
//	public static void setCompressedImage(byte[] compressedImageByteArray) {
//		CompressedImageByteArray = compressedImageByteArray;
//	}
//
//	public static Bitmap getCompressedBitmap() {
//		return CompressedImage;
//	}
//
//	public static void setCompressedBitmap(Bitmap compressedImage) {
//		CompressedImage = compressedImage;
//	}
//	@Override
//	protected void onPause() {
//		super.onPause();
//		Log.v(this.getClass().getSimpleName(), "onPause()");
//		//Disconnect from API onPause()
//		if (mGoogleApiClint.isConnected()) {
//			//LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClint, (com.google.android.gms.location.LocationListener) this);
//			mGoogleApiClint.disconnect();
//		}
//	}
//	@Override
//	public void onLocationChanged(Location location) {
//		if (GlobalVariables.isOfflineGPS == false) {
//			LastLocation = location;
//			isTimerStarted = true;
//			updateUILocation(LastLocation);
//			if (getIntent().getStringExtra("KEY_PIC").equals("1")) {
//				if (location.getLatitude() > 0.0) {
//					//long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
//					Button takePhoto = (Button) findViewById(R.id.btnCapture);
//					if (location.getAccuracy() > 0 && location.getAccuracy() < 50) {
//
//						tv_Lat.setText(""+ location.getLatitude());
//						tv_Lon.setText(""+ location.getLongitude());
//						tv_ac.setText(""+ location.getAccuracy());
//						takePhoto.setText("Take Photo");
//						takePhoto.setEnabled(true);
//					} else {
//						takePhoto.setText("Wait for GPS to Stable");
//						takePhoto.setEnabled(false);
//					}
//
//				}
//
//			} else {
//				LastLocation.setLatitude(0.0);
//				LastLocation.setLongitude(0.0);
//				LastLocation.setTime(0);
//				updateUILocation(LastLocation);
//				Button takePhoto = (Button) findViewById(R.id.btnCapture);
//				takePhoto.setText("Take Photo");
//				takePhoto.setEnabled(true);
//			}
//		}
//		else{
//			Message.obtain(
//					mHandler,
//					UPDATE_LATLNG,
//					new DecimalFormat("#.0000000").format(location.getLatitude())
//							+ "-"
//							+ new DecimalFormat("#.0000000").format(location
//							.getLongitude()) + "-" + location.getAccuracy()+"-"+location.getTime())
//					.sendToTarget();
//			takePhoto.setText("Take Photo");
//		}
//		//Toast.makeText(this, latitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();
//
//	}
//	private boolean checkLocationPermission() {
//		if( !hasLocationPermission() ) {
//			Log.e("Tuts+", "Does not have location permission granted");
//			requestLocationPermission();
//			return false;
//		}
//
//		return true;
//	}
//
//	private boolean hasLocationPermission() {
//		return ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION )
//				== PackageManager.PERMISSION_GRANTED;
//	}
//	private final static int REQUEST_PERMISSION_RESULT_CODE = 42;
//
//	private void requestLocationPermission() {
//		ActivityCompat.requestPermissions(
//				CameraActivity.this,
//				new String[]{ Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION },
//				REQUEST_PERMISSION_RESULT_CODE );
//	}
//
//	/*@Override
//	public void onMapReady(GoogleMap googleMap) {
//		mMap = googleMap;
//		LatLng TutorialsPoint = new LatLng(latitude, longitude);
//		mMap.addMarker(new
//				MarkerOptions().position(TutorialsPoint).title("Current Location"));
//		mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
//	}*/
//
//	@Override
//	public void onConnected(@Nullable Bundle bundle) {
//		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//			//    ActivityCompat#requestPermissions
//			// here to request the missing permissions, and then overriding
//			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//			//                                          int[] grantResults)
//			// to handle the case where the user grants the permission. See the documentation
//			// for ActivityCompat#requestPermissions for more details.
//			return;
//		}
//		Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClint);
//
//		if (location == null) {
//			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClint, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
//
//		} else {
//			//If everything went fine lets get latitude and longitude
//			isTimerStarted = true;
//			LastLocation=location;
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//			if (getIntent().getStringExtra("KEY_PIC").equals("1")) {
//				if (location.getLatitude() > 0.0) {
//					long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
//					Button takePhoto = (Button) findViewById(R.id.btnCapture);
//					if (location.getAccuracy() > 0 && location.getAccuracy() < 50) {
//
//						tv_Lat.setText(""+ location.getLatitude());
//						tv_Lon.setText(""+ location.getLongitude());
//						tv_ac.setText(""+ location.getAccuracy());
//						takePhoto.setText("Take Photo");
//						takePhoto.setEnabled(true);
//					} else {
//						takePhoto.setText("Wait for GPS to Stable");
//						takePhoto.setEnabled(false);
//					}
//
//				} else {
//					LastLocation.setLatitude(0.0);
//					LastLocation.setLongitude(0.0);
//					LastLocation.setTime(0);
//					updateUILocation(LastLocation);
//					Button takePhoto = (Button) findViewById(R.id.btnCapture);
//					takePhoto.setText("Take Photo");
//					takePhoto.setEnabled(true);
//				}
//				//Toast.makeText(this, latitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();
//			}else{
//
//				Message.obtain(
//						mHandler,
//						UPDATE_LATLNG,
//						new DecimalFormat("#.0000000").format(location.getLatitude())
//								+ "-"
//								+ new DecimalFormat("#.0000000").format(location
//								.getLongitude()) + "-" + location.getAccuracy()+"-"+location.getTime())
//						.sendToTarget();
//				takePhoto.setText("Take Photo");
//			}
//		}
//	}
//
//	@Override
//	public void onConnectionSuspended(int i) {
//
//	}
//
//	@Override
//	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
///*
//             * Google Play services can resolve some errors it detects.
//             * If the error has a resolution, try sending an Intent to
//             * start a Google Play services activity that can resolve
//             * error.
//             */
//		if (connectionResult.hasResolution()) {
//			try {
//				// Start an Activity that tries to resolve the error
//				connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//                    /*
//                     * Thrown if Google Play services canceled the original
//                     * PendingIntent
//                     */
//			} catch (IntentSender.SendIntentException e) {
//				// Log the error
//				e.printStackTrace();
//			}
//		} else {
//                /*
//                 * If no resolution is available, display a dialog to the
//                 * user with the error.
//                 */
//			Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
//		}
//	}
//
//	Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//
//				case UPDATE_ADDRESS:
//				case UPDATE_LATLNG:
//					String[] LatLon = ((String) msg.obj).split("-");
//					 tv_Lat = (TextView) findViewById(R.id.tvLat);
//					 tv_Lon = (TextView) findViewById(R.id.tvLon);
//					 tv_ac = (TextView) findViewById(R.id.tvAcuracy);
//
//					tv_Lat.setText(""+ latitude);
//					tv_Lon.setText(""+ longitude);
//					if (LastLocation!=null)tv_ac.setText(""+ LastLocation.getAccuracy());
//					Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);
//
//					if (!isTimerStarted) {
//						startTimer();
//					}
//
//					break;
//			}
//		}
//	};
//	private void updateUILocation(Location location) {
//
//		Message.obtain(
//				mHandler,
//				UPDATE_LATLNG,
//				new DecimalFormat("#.0000000").format(location.getLatitude())
//						+ "-"
//						+ new DecimalFormat("#.0000000").format(location
//						.getLongitude()) + "-" + location.getAccuracy()+"-"+location.getTime())
//				.sendToTarget();
//
//	}
//}
