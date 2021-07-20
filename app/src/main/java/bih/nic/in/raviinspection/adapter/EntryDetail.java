//package bih.nic.in.fsyinspection.adapter;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.ContentValues;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.provider.Settings;
//import android.support.v7.app.AppCompatActivity;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.List;
//
//import bih.nic.in.sbmurban.Utilities.CommonPref;
//import bih.nic.in.sbmurban.Utilities.Utiilties;
//import bih.nic.in.sbmurban.database.DataBaseHelper;
//import bih.nic.in.sbmurban.database.WebServiceHelper;
//import bih.nic.in.sbmurban.entity.GetDataListClass;
//import bih.nic.in.sbmurban.entity.wardList;
//
//public class EntryDetail extends AppCompatActivity implements View.OnClickListener{
//    EditText edtname,edtfname,edtaddress,edtapplicationId,edt_applicantId,edtcntctnumber,edtscheme;
//    String str_wardId="",str_edtfname="",str_edtaddress="",str_edtapplicationId="",str_edtcntctnumber="",str_edtscheme="",str_aapplictnId="",str_img="",str_lat="",str_long="",str_gpstime="";
//    DataBaseHelper dbhelper;
//    String flag="";
//    long applicationIds=0;
//    GetDataListClass GetdataList;
//    wardList wardL;
//    Button btn_submit,btnupload;
//    ImageView img;
//    byte[] imageData2;
//    int ThumbnailSize = 0;
//    private final static int CAMERA_PIC = 1;
//    TextView lblinfo;
//    String issaved="n";
//    boolean ispictureTaken=false;
//    int count=1;
//    String version = "";
//    TextView textversion;
//    ScrollView scrollview;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_entry_detail);
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        flag=getIntent().getExtras().getString("screenflag").trim();
//        str_wardId=getIntent().getExtras().getString("WardId").trim();
//        str_aapplictnId=getIntent().getExtras().getString("AppId").trim();
//
//        lblinfo=(TextView) findViewById(R.id.txtinfo);
//
//        if(str_wardId.equalsIgnoreCase(""))
//        {
//            getwardId(str_aapplictnId);
//        }
//        dbhelper=new DataBaseHelper(EntryDetail.this);
//        applicationIds= getApplicationId(str_aapplictnId,flag);
//        btn_submit=(Button)findViewById(R.id.submit);
//        btnupload=(Button)findViewById(R.id.btnupload);
//        //btnupload.setEnabled(false);
//        edtname=(EditText)findViewById(R.id.edt_name);
//        edtfname=(EditText)findViewById(R.id.edt_fname);
//        edtaddress=(EditText)findViewById(R.id.edt_address);
//        edtapplicationId=(EditText)findViewById(R.id.edt_app_Id);
//        edt_applicantId=(EditText)findViewById(R.id.edt_applicnt_Id);
//        edtcntctnumber=(EditText)findViewById(R.id.edt_cntct);
//        edtscheme=(EditText)findViewById(R.id.edt_scheme);
//         scrollview = (ScrollView) findViewById(R.id.scrollview);
//        img=(ImageView)findViewById(R.id.img_pic);
//        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
//            ThumbnailSize = displaymetrics.widthPixels / 2;
//            img.getLayoutParams().height = ThumbnailSize;
//
//        }
//        else
//        {
//            ThumbnailSize = displaymetrics.widthPixels / 4;
//            img.getLayoutParams().height = ThumbnailSize;
//        }
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//               // if (Utiilties.isOnline(EntryDetail.this)) {
//                    if (Utiilties.isGPSEnabled(EntryDetail.this)) {
//                        Intent iCamera = new Intent(getApplicationContext(),
//                                CameraActivity.class);
//                        if (v.equals(img))
//                            iCamera.putExtra("KEY_PIC", "1");
//                        startActivityForResult(iCamera, CAMERA_PIC);
//                    } else {
//
//                        turnGPSOn();
//                    }
//                //}
//            }
//        });
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//
//                if(!str_aapplictnId.equalsIgnoreCase("0")) {
//                    if (ispictureTaken) {
//                        long c = InsertoDatabase();
//                        if (c > 0) {
//                            Toast.makeText(EntryDetail.this, "Record saved", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(EntryDetail.this, "Record not saved", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(EntryDetail.this, "Please take picture.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else
//                {
//                    Toast.makeText(EntryDetail.this, "No record to submit", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        btnupload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if( issaved.contains("y"))
//                {
//                    if (Utiilties.isOnline(EntryDetail.this)) {
//
//                        android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(
//                                EntryDetail.this);
//                        ab.setTitle("Upload !");
//                        ab.setMessage("Do you want to upload  this Image to the server ?");
//                        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                dialog.dismiss();
//                                dbhelper=new DataBaseHelper(EntryDetail.this);
//
//                                if(flag.equalsIgnoreCase("iihl")) {
//                                    if (dbhelper.getUploadCount() > 0) {
////                                        List<GetDataListClass> locDatas = dbhelper.getAllData();
//                                        List<GetDataListClass> locDatas = dbhelper.getThisData(str_aapplictnId);
//
//                                        for (GetDataListClass cn : locDatas) {
//                                            new uploadingLocData(cn).execute();
////                                            if(locDatas.size()==count)
////                                            {
////                                                finish();
////                                            }
//                                            clearFields();
//                                        }
//                                    } else {
//                                        Toast.makeText(getApplicationContext(), "No Records Found with Photo",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                                else if(flag.equalsIgnoreCase("constructed"))
//                                {
//                                    dbhelper=new DataBaseHelper(EntryDetail.this);
//
//                                    if (dbhelper.getConstructedUploadCount() > 0) {
//                                       // List<GetDataListClass> locDatas = dbhelper.getAllConstructedData();
//                                        List<GetDataListClass> locDatas = dbhelper.getThisConstructedData(str_aapplictnId);
//                                        for (GetDataListClass cn : locDatas) {
//                                            new uploadingConstructedData(cn).execute();
////                                            if(locDatas.size()==count)
////                                            {
////                                                finish();
////                                            }
//                                            clearFields();
//
//                                        }
//                                    } else {
//                                        Toast.makeText(getApplicationContext(), "No Records Found with Photo",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                        });
//                        ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
//                        ab.show();
//
//                    } else
//                    {
//                        Toast.makeText(EntryDetail.this, " No Internet connection ! \n Please check your internet connectivity.",
//                                Toast.LENGTH_LONG).show();
//                    }
//                }
//                else
//                {
//                    Toast.makeText(EntryDetail.this, "Please submit the records before uploading the records.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        setLocaldata();
//        showImage(str_aapplictnId,flag);
//
//        try {
//            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//            textversion=(TextView)findViewById(R.id.textversion);
//            //txtversion.setText(	"Version :" +" "+ version + " ( " + imei + " )");
//            textversion.setText(	"Version" +" ("+ version +")");
//        } catch (PackageManager.NameNotFoundException e) {e.getMessage();}
//
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
////        scrollview.post(new Runnable() {
////            @Override
////            public void run() {
////                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
////            }
////        });
//    }
//    public void clearFields()
//    {
//        edtapplicationId.setText("");
//        edt_applicantId.setText("");
//        edtname.setText("");
//        edtfname.setText("");
//        edtaddress.setText("");
//        edtcntctnumber.setText("");
//        edtscheme.setText("");
//        img.setScaleType(ImageView.ScaleType.FIT_XY);
//        img.setImageResource(R.drawable.takephoto);
//        img.setOnClickListener(EntryDetail.this);
//        lblinfo.setVisibility(View.GONE);
//        issaved="n";
//        str_aapplictnId="0";
//
//    }
//    private void turnGPSOn() {
////        AlertDialog.Builder ab = new AlertDialog.Builder(EntryDetail.this);
////        ab.setTitle("GPS REQUIRED");
////        ab.setMessage("Please turn on your GPS to get your location");
////        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int whichButton) {
////
////                dialog.dismiss();
////
////            }
////        });
////
////        //noinspection ConstantConditions
////        ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
////        ab.show();
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("GPS");
//        builder.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS..")
////		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//
//    }
//
//    public void turnOnMyGPS(){
//        try
//        {
//            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//
//            if(!provider.contains("gps")){ //if gps is disabled
//                final Intent poke = new Intent();
//                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//                poke.setData(Uri.parse("3"));
//                sendBroadcast(poke);
//            }
//        }
//        catch (Exception e) {
//
//        }
//    }
//    public void setLocaldata()
//    {
//        GetdataList= dbhelper.getEditabeldata(str_aapplictnId,flag);
//        if (!GetdataList.get_ApplicationId().equalsIgnoreCase("") ) {
//            edtapplicationId.setText(GetdataList.get_ApplicationId());
//        }
//
//        if (!GetdataList.get_AppliicantId().equalsIgnoreCase("")) {
//            edt_applicantId.setText(GetdataList.get_AppliicantId());
//        }
//
//        if (!GetdataList.get_Name().equalsIgnoreCase("") ) {
//            edtname.setText(GetdataList.get_Name());
//        }
//
//        if (!GetdataList.get_FatherName().equalsIgnoreCase("") ) {
//            edtfname.setText(GetdataList.get_FatherName());
//        }
//        if (!GetdataList.get_Address().equalsIgnoreCase("") ) {
//            edtaddress.setText(GetdataList.get_Address());
//        }
//        if (!GetdataList.get_Contactno().equalsIgnoreCase("")) {
//            edtcntctnumber.setText(GetdataList.get_Contactno());
//        }
//
//        if (!GetdataList.get_schemecode().equalsIgnoreCase("") ) {
//            edtscheme.setText(GetdataList.get_schemecode());
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case CAMERA_PIC:
//                if (resultCode == RESULT_OK) {
//                    byte[] imgData = data.getByteArrayExtra("CapturedImage");
//                    //imageData.add(imgData);
//                    switch (data.getIntExtra("KEY_PIC", 0)) {
//                        case 1:
//                            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
//                                    imgData.length);
//                            img.setScaleType(ImageView.ScaleType.FIT_XY);
//                            img.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
//                                    ThumbnailSize, ThumbnailSize));
//                            str_img="Y";
//                            imageData2 = imgData;
//                            str_lat = data.getStringExtra("Lat");
//                            str_long = data.getStringExtra("Lng");
//                            str_gpstime= data.getStringExtra("GPSTime");
//                            ispictureTaken=true;
//
//                            break;
//                    }
//                }
//        }
//        scrollview.post(new Runnable() {
//            @Override
//            public void run() {
//                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        });
//
//    }
//    private void LCbuildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("GPS");
//        builder.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS..")
//                .setCancelable(false)
//                .setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }
//    public String getwardId(String appID)
//    {
//        try {
//            long x = 0;
//            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
//            SQLiteDatabase db;
//            db = dataBaseHelper.getReadableDatabase();
//            Cursor cur = db.rawQuery("Select wardId from RecordList where AopplicationId='" + appID + "'", null);
//            x = cur.getCount();
//            cur.moveToNext();
//            str_wardId = cur.getString(0);
//            cur.close();
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return str_wardId;
//    }
//
//
//    public long getApplicationId(String appId, String str_flag) {
//        long x = 0;
//        Cursor cur=null;
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
//        SQLiteDatabase db;
//        db = dataBaseHelper.getReadableDatabase();
//        try {
//
//            dataBaseHelper = new DataBaseHelper(this);
//            db = dataBaseHelper.getReadableDatabase();
//            if(str_flag.equalsIgnoreCase("iihl"))
//            { cur = db.rawQuery("Select * from InsertData where ApplicationId='" + appId + "'", null);}
//            else if(str_flag.equalsIgnoreCase("constructed"))
//            {
//                cur = db.rawQuery("Select * from InsertData2 where ApplicationId='" + appId + "'", null);
//            }
//            x = cur.getCount();
//            cur.close();
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return x;
//    }
//
//
//
//    public long InsertoDatabase() {
//        long c = -1;
//        if(str_img.equalsIgnoreCase("Y")) {
//            try {
//                DataBaseHelper db = new DataBaseHelper(EntryDetail.this);
//                SQLiteDatabase sqldb = db.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("longitude", str_long);
//                values.put("latitude", str_lat);
//                values.put("Name", GetdataList.get_Name());
//                values.put("Gpstime",str_gpstime);
//                values.put("ApplicationId", GetdataList.get_ApplicationId());
//                values.put("UserId", CommonPref.getUserDetails(getApplicationContext()).get_UserID());
//                values.put("ApplicantId",GetdataList.get_AppliicantId());
//                values.put("tkPhoto", imageData2);
//                //values.put("WardID", str_wardId);
//                values.put("WardID",GetdataList.get_wardcode());
//                values.put("Currentstatus", GetdataList.get_Currentstatus());
//                values.put("ULBId", CommonPref.getUserDetails(getApplicationContext()).get_ULBCode());
//                String[] whereArgs = new String[]{GetdataList.get_ApplicationId()};
//                if(flag.equalsIgnoreCase("iihl")) {
//                    if (applicationIds > 0) {
//
//                        c = sqldb.update("InsertData", values, "ApplicationId=?", whereArgs);
//                        Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
////                        Intent i = new Intent(EntryDetail.this, UrbanListActivity.class);
////                        i.putExtra("ScreenFlag", flag);
////                        startActivity(i);
//                        //finish();
//                    } else {
//                        c = sqldb.insert("InsertData", null, values);
//                        Toast.makeText(getApplicationContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
////                        Intent i = new Intent(EntryDetail.this, UrbanListActivity.class);
////                        i.putExtra("ScreenFlag", flag);
////                        startActivity(i);
//                       // finish();
//                    }
//                   // btnupload.setEnabled(true);
//                    issaved="y";
//                }
//                else if(flag.equalsIgnoreCase("constructed"))
//                {
//                    if (applicationIds > 0) {
//
//                        c = sqldb.update("InsertData2", values, "ApplicationId=?", whereArgs);
//                        Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
////                        Intent i = new Intent(EntryDetail.this, UrbanListActivity.class);
////                        i.putExtra("ScreenFlag", flag);
////                        startActivity(i);
//                        //finish();
//
//                    } else {
//                        c = sqldb.insert("InsertData2", null, values);
//                        Toast.makeText(getApplicationContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
////                        Intent i = new Intent(EntryDetail.this, EntryDetail.class);
////                        Intent i = new Intent(EntryDetail.this, UrbanListActivity.class);
////                        i.putExtra("ScreenFlag", flag);
////                        startActivity(i);
//                       // finish();
//                    }
//                    //btnupload.setEnabled(true);
//                    issaved="y";
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return c;
//            }
//        }
//        else{
//            Toast.makeText(getApplicationContext(),"Please take photo!", Toast.LENGTH_LONG).show();
//        }
//        return c;
//
//    }
//    public void onClick(View view) {
//
//        if(!str_aapplictnId.equalsIgnoreCase("0")) {
//            if (view.equals(img)) {
//                if (Utiilties.isGPSEnabled(EntryDetail.this)) {
//                    Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
//                    //if (view.equals(img))
//                    iCamera.putExtra("KEY_PIC", "1");
//                    startActivityForResult(iCamera, CAMERA_PIC);
//                } else {
//
//                    turnGPSOn();
//                }
//            }
//        }
//
//    }
//    public void showImage(String appId, String str_flag) {
//
//        ThumbnailSize = 250;
//        ispictureTaken=false;
//        lblinfo.setVisibility(View.GONE);
//        Cursor cur=null;
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
//        SQLiteDatabase db;
//        db = dataBaseHelper.getReadableDatabase();
//        try {
//
//            dataBaseHelper = new DataBaseHelper(this);
//            db = dataBaseHelper.getReadableDatabase();
//            if(str_flag.equalsIgnoreCase("iihl"))
//            { cur = db.rawQuery("Select tkPhoto from InsertData where ApplicationId='" + appId + "'", null);}
//            else if(str_flag.equalsIgnoreCase("constructed"))
//            {
//                cur = db.rawQuery("Select tkPhoto from InsertData2 where ApplicationId='" + appId + "'", null);
//            }
//            if (cur.moveToNext()) {
//
//                if (!cur.isNull(0)) {
//                    ispictureTaken=true;
//                    byte[] imgData = cur.getBlob(0);
//                    str_img="Y";
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
//                    img.setScaleType(ImageView.ScaleType.FIT_XY);
//                    img.setImageBitmap(Utiilties.GenerateThumbnail(bmp, ThumbnailSize, ThumbnailSize)); //(image, height,width)
//                    img.setOnClickListener(this);
//                    issaved="y";
//                    lblinfo.setVisibility(View.VISIBLE);
//                } else {
//                    ispictureTaken=false;
//                    img.setScaleType(ImageView.ScaleType.FIT_XY);
//                    img.setImageResource(R.drawable.takephoto);
//                    img.setOnClickListener(EntryDetail.this);
//                    lblinfo.setVisibility(View.GONE);
//                    issaved="n";
//                    str_img="N";
//                }
//            }
//            cur.close();
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public class uploadingLocData extends AsyncTask<String, Void, String> {
//        String result11;
//        ProgressDialog pd1;
//        GetDataListClass getdatalist;
//        public uploadingLocData(GetDataListClass getdatalist){
//            this.getdatalist = getdatalist;
//        }
//
//
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//            pd1 = new ProgressDialog(EntryDetail.this);
//            pd1.setMessage("Data is Uploading Wait");
//            pd1.setCancelable(false);
//            pd1.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String USERID = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
//            String PWD = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PWD", "");
//
//            result11 = WebServiceHelper.sendLocData(getdatalist,CommonPref.getUserDetails(getApplicationContext()).get_DistrictCode(),CommonPref.getUserDetails(getApplicationContext()).get_IMEI(),CommonPref.getUserDetails(getApplicationContext()).get_UserID(),PWD);
//
//
//            return result11;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            pd1.dismiss();
//            if (result.toString().contains("1")) {
//                Toast.makeText(getApplicationContext(), "Data is uploaded successfully !", Toast.LENGTH_SHORT).show();
//                boolean isDel=dbhelper.deleterowPhase1(getdatalist.get_ApplicationId());
//                if (isDel) {
//                    //delete from actual list
//                    boolean is_del=dbhelper.deleterowPhase1Actual(getdatalist.get_ApplicationId());
//                    if(is_del)
//                    {
//                        count++;
//                        Toast.makeText(EntryDetail.this, "Removed from actual data list ID:" + getdatalist.get_ApplicationId(), Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(EntryDetail.this, "failed to removed from actual data list ID:" + getdatalist.get_ApplicationId(), Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Log.e("message", "data is uploaded but not deleted !!");
//                }
//
//            } else {
//                Toast.makeText(getApplicationContext(),
//                        "Uploading failed !\n\n" + result.toString(), Toast.LENGTH_LONG)
//                        .show();
//            }
//        }
//
//    }
//    public class uploadingConstructedData extends AsyncTask<String, Void, String> {
//        String result11;
//        ProgressDialog pd1;
//        GetDataListClass getdatalist;
//        public uploadingConstructedData(GetDataListClass getdatalist)
//        {
//            this.getdatalist = getdatalist;
//        }
//
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pd1 = new ProgressDialog(EntryDetail.this);
//            pd1.setMessage("Data is Uploading Wait");
//            pd1.setCancelable(false);
//            pd1.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            // result11 = WebServiceHelper.sendConstructedData(getdatalist,CommonPref.getUserDetails(getApplicationContext()).get_DistrictCode());
//            // return result11;
//
//
//            //String USERID = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
//            String PWD = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PWD", "");
//            String IMEI = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("IMEI", "");
//            result11 = WebServiceHelper.sendConstructedData(getdatalist,CommonPref.getUserDetails(getApplicationContext()).get_DistrictCode(),IMEI,CommonPref.getUserDetails(getApplicationContext()).get_UserID(),PWD);
//            return result11;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if(pd1.isShowing())
//            {
//                pd1.dismiss();
//            }
//            if (result.toString().contains("1")) {
//                Toast.makeText(getApplicationContext(), "Data is uploaded successfully !", Toast.LENGTH_SHORT).show();
//
//                boolean isDel=dbhelper.deleterow(getdatalist.get_ApplicationId());
//                if (isDel) {
//                    boolean is_del=dbhelper.deleterowConstructedActual(getdatalist.get_ApplicationId());
//                    if(is_del)
//                    {
//                        Toast.makeText(EntryDetail.this, "Removed from actual data list ID:" + getdatalist.get_ApplicationId(), Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(EntryDetail.this, "failed to removed from actual data list ID:" + getdatalist.get_ApplicationId(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }else{
//                    Log.e("message", "data is uploaded but not deleted !!");
//                }
//
//            } else {
//
//                Toast.makeText(getApplicationContext(),
//                        "Uploading failed !\n\n" + result.toString(), Toast.LENGTH_LONG)
//                        .show();
//            }
//            if (pd1.isShowing()) {
//                pd1.dismiss();
//            }
//        }
//    }
//}
