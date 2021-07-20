package bih.nic.in.raviinspection.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.database.DataBaseHelper;
import bih.nic.in.raviinspection.entity.FarmerDetails;
import bih.nic.in.raviinspection.utilities.Utiilties;
/**
 * Created by nicsi on 14-Jul-17.
 */

public class SinglePhotoActivity extends Activity implements View.OnClickListener {

    ImageView img1, img2, img3, img4,img5;
    Button btnOk;
    Intent imageData1, imageData2, imageData3, imageData4,imageData5;
    private final static int CAMERA_PIC = 99;
    int ThumbnailSize = 0;
    String PID = "0";
    FarmerDetails dataProgress;
    public boolean isPictureTaken = false;
    String purpose= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);

      PID = getIntent().getStringExtra("index");
      Log.d("duhoeh",""+PID);
    //isOpen = getIntent().getStringExtra("isOpen");
      purpose = getIntent().getStringExtra("edited");

        if (PID == null) {
        PID = "0";
    }


    DisplayMetrics displaymetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    img1 = (ImageView) findViewById(R.id.iv_Ramp_Facility);
    img2 = (ImageView) findViewById(R.id.iv_Drinking_Water);
    img3 = (ImageView) findViewById(R.id.iv_Toilet_Male);
    img4 = (ImageView) findViewById(R.id.img_land1);
    img5 = (ImageView) findViewById(R.id.img_land2);


    btnOk = (Button) findViewById(R.id.btn_Save);
        btnOk.setOnClickListener(this);

    // intent.putExtra("edited", isEdit);
       if( !purpose.equalsIgnoreCase("yes"))
    //if(!purpose.equalsIgnoreCase("isEdit"))
    {
        btnOk.setEnabled(false);
         btnOk.setBackgroundResource(R.drawable.buttonbackshape1);
    }

        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
        ThumbnailSize = displaymetrics.widthPixels / 2;
    } else {

        ThumbnailSize = displaymetrics.widthPixels / 4;
        img1.getLayoutParams().height = img2.getLayoutParams().height = img3
                .getLayoutParams().height = img4.getLayoutParams().height = ThumbnailSize;
    }

    DataBaseHelper placeData = new DataBaseHelper(this);
    SQLiteDatabase db = placeData.getReadableDatabase();

    Cursor cursor = db
            .rawQuery("SELECT Photo1,Photo2,Photo3,Photo4,Photo5 FROM InsertFarmer where RegNo =?",new String[]{String.valueOf(PID)});


            if (cursor.moveToNext()) {

        if (!cursor.isNull(0)) {

            byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo1"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                    imgData.length);
            img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                    ThumbnailSize, ThumbnailSize));
            img1.setOnClickListener(this);
        } else {
            img1.setOnClickListener(this);
            img2.setEnabled(false);
            img3.setEnabled(false);
            img4.setEnabled(false);


        }

        if (!cursor.isNull(1)) {
            byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo2"));

            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                    imgData.length);
            img2.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                    ThumbnailSize, ThumbnailSize));
            img2.setOnClickListener(this);

        } else {
            img2.setOnClickListener(this);
            img3.setEnabled(false);
            img4.setEnabled(false);

        }
        if (!cursor.isNull(2)) {
            byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo3"));

            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                    imgData.length);
            img3.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                    ThumbnailSize, ThumbnailSize));
            img3.setOnClickListener(this);
        } else {
            img3.setOnClickListener(this);
            img4.setEnabled(false);
        }
        if (!cursor.isNull(3)) {
            byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo4"));

            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                    imgData.length);
            img4.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                    ThumbnailSize, ThumbnailSize));
            img4.setOnClickListener(this);
        }
        if (!cursor.isNull(4)) {
            byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo5"));

            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                    imgData.length);
            img5.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                    ThumbnailSize, ThumbnailSize));
            img5.setOnClickListener(this);
        }

    }
            cursor.close();
            db.close();


}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");


                    //imageData.add(data);


                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            imageData1=data;
                            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                                    imgData.length);
                            img1.setScaleType(ImageView.ScaleType.FIT_XY);
                            img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                                    500, 500));
                            //img1.setOnClickListener(null);
                            img2.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonbackshape);
                            //btnOk.setEnabled(true);
                            break;

                        case 2:
                            imageData2=data;
                            img2.setScaleType(ImageView.ScaleType.FIT_XY);
                            img2.setImageBitmap(Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            //img2.setOnClickListener(null);
                            img3.setEnabled(true);
                            //btnOk.setEnabled(true);
                            break;

                        case 3:
                            imageData3=data;
                            img3.setScaleType(ImageView.ScaleType.FIT_XY);
                            img3.setImageBitmap(Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            //img3.setOnClickListener(null);
                            img4.setEnabled(true);
                            btnOk.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonbackshape);
                            break;

                        case 4:
                            imageData4=data;
                            img4.setScaleType(ImageView.ScaleType.FIT_XY);
                            img4.setImageBitmap(Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500,
                                    500));
                            btnOk.setEnabled(true);
                            //img4.setOnClickListener(null);
                            break;
                            case 5:
                            imageData5=data;
                            img5.setScaleType(ImageView.ScaleType.FIT_XY);
                            img5.setImageBitmap(Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500,
                                    500));
                            btnOk.setEnabled(true);
                            //img4.setOnClickListener(null);
                            break;

                    }


                }

        }

    }


    @Override
    public void onClick(View view) {
        if (view.equals(btnOk)) {
            if (imageData1==null&& imageData2==null&& imageData3==null&& imageData4==null && imageData5==null &&  purpose.equals("isEdit")){
                finish();
            }else {
                saveImage();
                Intent iUserHome = new Intent(getApplicationContext(),
                        ExistingEntry.class);
                iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iUserHome);
            }
        } else {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                buildAlertMessageNoGps();
            }
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

                Intent iCamera = new Intent(getApplicationContext(),
                        CameraActivity.class);
                if (view.equals(img1))
                    iCamera.putExtra("KEY_PIC", "1");
                else if (view.equals(img2))
                    iCamera.putExtra("KEY_PIC", "2");
                else if (view.equals(img3))
                    iCamera.putExtra("KEY_PIC", "3");
                else if (view.equals(img4))
                    iCamera.putExtra("KEY_PIC", "4");
                else if (view.equals(img5))
                    iCamera.putExtra("KEY_PIC", "5");

                startActivityForResult(iCamera, CAMERA_PIC);
            }
        }

    }

    private void saveImage() {

        int i = 0;
        DataBaseHelper placeData = new DataBaseHelper(
                SinglePhotoActivity.this);
        SQLiteDatabase db = placeData.getWritableDatabase();
        for (i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            String[] whereArgs;
            byte[] imgData;
            switch (i) {
                case 0:
                    //GPSTime
                    if (imageData1!=null) {

                        imgData = imageData1.getByteArrayExtra("CapturedImage");
                        values.put("Photo1", imgData);
                        values.put("EntryDate",
                                imageData1.getStringExtra("CapturedTime"));
                    /*    values.put("Latitude",
                                String.valueOf(imageData1.getStringExtra("Lat")));
                        values.put("Longitude",
                                String.valueOf(imageData1.getStringExtra("Lng")));
                   */     values.put("UploadDate",
                                String.valueOf(imageData1.getStringExtra("GPSTime")));
                        whereArgs = new String[]{String.valueOf(PID)};
                        db.update("InsertFarmer", values, "RegNo=?", whereArgs);
                    }
                    break;
                case 1:
                    if (imageData2!=null) {
                        imgData = imageData2.getByteArrayExtra("CapturedImage");
                        values.put("Photo2", imgData);
                        whereArgs = new String[]{String.valueOf(PID)};
                        db.update("InsertFarmer", values, "RegNo=?", whereArgs);
                    }
                    break;
                case 2:
                    if (imageData3!=null) {
                        imgData = imageData3.getByteArrayExtra("CapturedImage");
                        values.put("Photo3", imgData);
                        values.put("Latitude",
                                String.valueOf(imageData3.getStringExtra("Lat")));
                        values.put("Longitude",
                                String.valueOf(imageData3.getStringExtra("Lng")));

                        whereArgs = new String[]{String.valueOf(PID)};
                        db.update("InsertFarmer", values, "RegNo=?", whereArgs);
                    }
                    break;
                case 3:
                    if (imageData4!=null) {
                        imgData = imageData4.getByteArrayExtra("CapturedImage");
                        values.put("Photo4", imgData);
                        values.put("Pic1landLat",String.valueOf(imageData4.getStringExtra("Lat")));
                        values.put("Pic1landLong",  String.valueOf(imageData4.getStringExtra("Lng")));
                        whereArgs = new String[]{String.valueOf(PID)};
                        db.update("InsertFarmer", values, "RegNo=?", whereArgs);
                    }
                    break;
                    case 4:
                    if (imageData5!=null) {
                        imgData = imageData5.getByteArrayExtra("CapturedImage");
                        values.put("Photo5", imgData);
                        values.put("Pic2landLat",String.valueOf(imageData5.getStringExtra("Lat")));
                        values.put("Pic2landLong",  String.valueOf(imageData5.getStringExtra("Lng")));
                        whereArgs = new String[]{String.valueOf(PID)};
                        db.update("InsertFarmer", values, "RegNo=?", whereArgs);
                    }
                    break;

            }
        }

        db.close();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS..")
//		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();

        Cursor cursor = db
                .rawQuery("SELECT Photo1,Photo2,Photo3,Photo4,Photo5 FROM InsertFarmer where RegNo =?",new String[]{String.valueOf(PID)});


        if (cursor.moveToNext()) {

            if (!cursor.isNull(0)) {

                byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo1"));
                if(imgData!=null){
                    finish();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Please capture image",Toast.LENGTH_LONG).show();
            }

        }
    }
}
//    ImageButton img1;
//    Button save_img;
//    Intent imageData;
//    private final static int CAMERA_PIC = 99;
//    int ThumbnailSize = 0;
//    String PID;
//    DataProgress thanaInfo;
//        public boolean isPictureTaken = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_single_photo);
//        img1 = (ImageButton) findViewById(R.id.imageButton1);
//        save_img = (Button) findViewById(R.id.save_img);
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//
//        if (getIntent().getStringExtra("edited").equals("yes")) {
//            thanaInfo = (DataProgress) getIntent().getSerializableExtra("Object");
//            byte[] decodedString = Base64.decode(thanaInfo.getPhoto1(), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            img1.setImageBitmap(decodedByte);
//            isPictureTaken = true;
//        }
//
//        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
//            ThumbnailSize = displaymetrics.widthPixels / 2;
//            //img1.getLayoutParams().height = ThumbnailSize;
//            //img3.getLayoutParams().height = ThumbnailSize;
//
//
//        } else {
//
//            ThumbnailSize = displaymetrics.widthPixels / 4;
//            img1.getLayoutParams().height = ThumbnailSize;
//
//        }
//        img1.setOnClickListener(this);
//        save_img.setOnClickListener(this);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case CAMERA_PIC:
//                if (resultCode == RESULT_OK) {
//                    isPictureTaken = true;
//                    byte[] imgData = data.getByteArrayExtra("CapturedImage");
//
//
//                    imageData = data;
//                    switch (data.getIntExtra("KEY_PIC", 0)) {
//                        case 1:
//                            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
//                                    imgData.length);
//                            img1.setScaleType(ImageView.ScaleType.FIT_XY);
//                            img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
//                                    ThumbnailSize, ThumbnailSize));
//                            break;
//                    }
//                }
//
//        }
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.imageButton1) {
//            if (!isPictureTaken || getIntent().getStringExtra("edited").equals("yes")) {
//                Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
//                iCamera.putExtra("KEY_PIC", "1");
//                startActivityForResult(iCamera, CAMERA_PIC);
//            }
//        } else if (v.getId() == R.id.save_img) {
//            if (isPictureTaken) {
//                //save image
//                PID = getIntent().getStringExtra("index");
//                //updating image
//                new DataBaseHelper(SinglePhotoActivity.this).updateRowBySlno(PID, imageData);
//                Intent intent = new Intent(SinglePhotoActivity.this, HomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            } else {
//                Toast.makeText(this, "Please capture image to save data", Toast.LENGTH_SHORT).show();
//            }
//
//
//        }
//    }
//}
