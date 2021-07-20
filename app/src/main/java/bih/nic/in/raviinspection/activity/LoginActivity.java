package bih.nic.in.raviinspection.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.database.DataBaseHelper;
import bih.nic.in.raviinspection.database.WebServiceHelper;
import bih.nic.in.raviinspection.entity.Remarks;
import bih.nic.in.raviinspection.entity.UserDetails;
import bih.nic.in.raviinspection.utilities.CommonPref;
import bih.nic.in.raviinspection.utilities.GlobalVariables;
import bih.nic.in.raviinspection.utilities.MarshmallowPermission;
import bih.nic.in.raviinspection.utilities.Utiilties;

public class LoginActivity extends Activity {


    String version;
    TelephonyManager tm;
    private static String imei = "";

    public String zonecode, maxid;
    DataBaseHelper dataBaseHelper;
    boolean doubleBackToExitPressedOnce = false;


    MarshmallowPermission MARSHMALLOW_PERMISSION;
    MarshMallowPermission marshMallowPermission;

    SQLiteDatabase db;
    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataBaseHelper = new DataBaseHelper(LoginActivity.this);
        try {
            dataBaseHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("डेटाबेस बनाने में असमर्थ");

        }

        try {

            dataBaseHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }
        ModifyTable();


        marshMallowPermission = new MarshMallowPermission(LoginActivity.this);
        String[] PERMISSIONS_ARRAY = {android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_INPUT_STATE};
        int PERMISSION_ALL = 1;


        if(!marshMallowPermission.hasPermissions(LoginActivity.this, PERMISSIONS_ARRAY)) {
            ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_ARRAY, PERMISSION_ALL);
        }
    }
    public void ModifyTable() {
        if (isColumnExists("InsertFarmer", "swaghosana_sambandhit_id" ) == false) {
            AlterTable("InsertFarmer","swaghosana_sambandhit_id");
        }

        if (isColumnExists("InsertFarmer", "swaghosana_sambandhit_nm" ) == false) {
            AlterTable("InsertFarmer","swaghosana_sambandhit_nm");
        }
        if (isColumnExists("InsertFarmer", "swaghosana_signer_name" ) == false) {
            AlterTable("InsertFarmer","swaghosana_signer_name");
        }

    }
    public boolean isColumnExists (String table, String column) {
        db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }

        return false;
    }
    public void AlterTable(String tableName,String columnName)
    {
        db = dataBaseHelper.getReadableDatabase();

        try{

            db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" TEXT");
            Log.e("ALTER Done",tableName +"-"+ columnName);
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed",tableName +"-"+ columnName);
        }
    }
    public void readPhoneState() {
        MARSHMALLOW_PERMISSION = new MarshmallowPermission(LoginActivity.this, android.Manifest.permission.READ_PHONE_STATE);
        if (MARSHMALLOW_PERMISSION.result == -1 || MARSHMALLOW_PERMISSION.result == 0) {
            try {
                tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) imei = tm.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) imei = tm.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        AutoCompleteTextView userName = (AutoCompleteTextView) findViewById(R.id.et_User_Id);
        userName.setText(CommonPref.getUserDetails(getApplicationContext()).getUserID());

        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView tv = (TextView) findViewById(R.id.txtVersion);
            tv.setText("ऐप का वर्जन : " + version + " ( " + imei + " )");

        } catch (PackageManager.NameNotFoundException e) {

        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getDeviceIMEI();

        AutoCompleteTextView userName = (AutoCompleteTextView) findViewById(R.id.et_User_Id);
        userName.setText(CommonPref.getUserDetails(getApplicationContext()).getUserID());
        try {

            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView tv = (TextView) findViewById(R.id.txtVersion);
            tv.setText("ऐप का वर्जन : " + version + " ( " + imei + " )");


        } catch (PackageManager.NameNotFoundException e) {

        }
        //readPhoneState();
        checkOnline();
    }
    public String getDeviceIMEI() {
        //String deviceUniqueIdentifier = null;
        MarshmallowPermission permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);
//        try {
//            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//            if (null != tm) {
//                imei = tm.getDeviceId();
//            }
//            if (null == imei || 0 == imei.length()) {
//                imei = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            imei = Settings.Secure.getString(
                    this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        else
        {
            final TelephonyManager mTelephony = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null)
            {
                imei = mTelephony.getDeviceId();
            }
            else
            {
                imei = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
            }
        }


        return imei;
    }

    protected void checkOnline() {
        // TODO Auto-generated method stub
        super.onResume();

        if (Utiilties.isOnline(LoginActivity.this) == false) {

            AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this);
            ab.setIcon(R.drawable.wifi);
            ab.setTitle("इंटरनेट नहीं है");
            ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें या ऑफ़लाइन मोड के साथ जारी रखें.");
            ab.setPositiveButton("नेटवर्क कनेक्शन चालू करें", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    GlobalVariables.isOffline = false;
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            ab.setNegativeButton("ऑफ़लाइन जारी रखें", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {

                    GlobalVariables.isOffline = true;
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();

        } else {

            GlobalVariables.isOffline = false;
            // new CheckUpdate().execute();
        }
    }
    public void Login(View view) {

        if (!GlobalVariables.isOffline && !Utiilties.isOnline(LoginActivity.this)) {

            AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this);
            ab.setIcon(R.drawable.wifi);
            ab.setTitle("NO INTERNET");
            ab.setMessage("Internet Connection is not avaliable.Please Turn ON Network Connection OR Continue With Off-line Mode.");
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });
            ab.setNegativeButton("Continue Offline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    GlobalVariables.isOffline = true;
                }

            });
            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();

        } else {

            final AutoCompleteTextView userName = (AutoCompleteTextView) findViewById(R.id.et_User_Id);
            final EditText userPass = (EditText) findViewById(R.id.et_Password);
            String[] param = new String[2];
            param[0] = userName.getText().toString();
            param[1] = userPass.getText().toString();

            if (!param[0].equals("") && !param[0].equals("null")) {

                if (!param[1].equals("") && !param[1].equals("null")) {
                    new LoginTask().execute(param);
                } else {
                    userPass.setError("password cannot be blank");
                }
            } else if (param[1].equals("") || param[1].equals("null")) {
                userName.setError("User Id cannot be blank");
                userPass.setError("password cannot be blank");
            } else {
                userName.setError("User Id cannot be blank");
            }
        }
    }

    private class LoginTask extends AsyncTask<String, Void, UserDetails>
    {

        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

        @Override
        protected void onPreExecute()
        {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("प्रमाणित कर रहा है...");
            this.dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... param)
        {

            if (GlobalVariables.isOffline)
            {
                UserDetails userInfo = new UserDetails();
                userInfo.set_isAuthenticated(true);
                return userInfo;
            } else
                return WebServiceHelper.Authenticate(param[0], param[1]);

        }

        @Override
        protected void onPostExecute(UserDetails result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
                final EditText userPass = (EditText) findViewById(R.id.et_Password);
                final AutoCompleteTextView userName = (AutoCompleteTextView) findViewById(R.id.et_User_Id);
                if(imei.equalsIgnoreCase(imei)) {
                    imei = result.getIMEI();
                    //imei = "862183044263136";
                    //imei = "359376097764329";
                    //imei = "866778040112652";
                }

                if (result == null|| result.is_isAuthenticated() == false )
                {

                    alertDialog.setTitle("असफल हुआ");
                    alertDialog.setMessage("प्रमाणीकरण विफल होना। कृपया पुन: प्रयास करें.");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            userName.setFocusable(true);
                        }
                    });

                    alertDialog.show();

                }
                else
                {

                    DataBaseHelper placeData = new DataBaseHelper(LoginActivity.this);
                    // Intent cPannel = new Intent(getApplicationContext(), HomeActivity.class);
                    //Intent cPannel=new Intent(getApplicationContext(),MainActivity.class);
                    // NOt offline
                    //imei="911508454604323"; Toast.makeText(LoginActivity.this,"IMEI is hardcoded " + imei.toString(),Toast.LENGTH_SHORT).show();

                    if (GlobalVariables.isOffline == false)
                    {
                        if (result != null)
                        {
                            //|| imei.equalsIgnoreCase("358827060487190")|| imei.equalsIgnoreCase("863910041502895")|| imei.equalsIgnoreCase("1dd2359509a4f194")
                            if (imei.equalsIgnoreCase(result.getIMEI()))
                            {
                                try
                                {
                                    GlobalVariables.LoggedUser = result;
                                    GlobalVariables.LoggedUser.setUserID(userName.getText().toString().trim());
                                    GlobalVariables.LoggedUser.setPassword(userPass.getText().toString().trim());
                                    CommonPref.setUserDetails(getApplicationContext(), GlobalVariables.LoggedUser);
                                    placeData.insertUserDetails(GlobalVariables.LoggedUser);
                                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", result.getUserID() ).commit();

                                    maxid = null;
                                    //new DwnldPanchayat(CommonPref.getUserDetails(getApplicationContext()).getUserID()).execute();
                                    new DwnldProvisionalMarks().execute();

                                    //startActivity(cPannel);

                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                alertDialog.setTitle("डिवाइस पंजीकृत नहीं है");
                                alertDialog.setMessage("क्षमा करें, आपका डिवाइस पंजीकृत नहीं है.\r\nकृपया अपने व्यवस्थापक से संपर्क करें.");
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        userName.setFocusable(true);
                                    }
                                });
                                alertDialog.show();
                            }
                        }
                        else
                        {
                            if (this.dialog.isShowing())
                            {
                                this.dialog.dismiss();
                            }
                            alertDialog.setTitle("सर्वर पहुँच से बाहर");
                            alertDialog.setMessage("सर्वर व्यस्त है. बाद में कोशिश करें.");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }

                    }
                    else
                    {
                        if (placeData.getUserCount() > 0)
                        {
                            GlobalVariables.LoggedUser = placeData.getUserDetails(userName.getText().toString().trim(), userPass.getText().toString());

                            if (GlobalVariables.LoggedUser != null)
                            {
                                CommonPref.setUserDetails(getApplicationContext(), GlobalVariables.LoggedUser);
                                //new DwnldPanchayat(CommonPref.getUserDetails(getApplicationContext()).getUserID()).execute();
                                new DwnldProvisionalMarks().execute();
                                //startActivity(cPannel);
                                //finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "उपयोगकर्ता नाम और पासवर्ड मेल नहीं खाता है !", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "कृपया पहली बार लॉगिन के लिए इंटरनेट कनेक्शन सक्षम करें.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }

//    public class DwnldPanchayat extends AsyncTask<String, Void, ArrayList<Panchayat>>    {
//        String Userid = "";
//        public DwnldPanchayat(String userid) {
//            this.Userid = userid;
//
//        }
//
//        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
//
//        private final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//
//        @Override
//        protected void onPreExecute() {
//            this.dialog.setCanceledOnTouchOutside(false);
//            this.dialog.setMessage("पंचायत लोड हो रहा है.\nकृपया प्रतीक्षा करें...");
//            this.dialog.show();
//        }
//
//        @Override
//        protected ArrayList<Panchayat> doInBackground(String... params) {
//
//            ArrayList<Panchayat> res1 = WebServiceHelper.loadPanchayatList(Userid);
//
//            return res1;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Panchayat> result) {
//
//            if (this.dialog.isShowing()) {
//
//                this.dialog.dismiss();
//
//                DataBaseHelper placeData = new DataBaseHelper(LoginActivity.this);
//                placeData.insertPanchayat(result);
//                //new DwnldProvisionalMarks().execute();
//            }
//
//
//        }
//
//
//    }

    public class DwnldProvisionalMarks extends AsyncTask<String, Void, ArrayList<Remarks>> {
        public DwnldProvisionalMarks() {


        }

        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("कृपया प्रतीक्षा करें....");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Remarks> doInBackground(String... params) {

            ArrayList<Remarks> res1 = WebServiceHelper.loadProvisionList();

            return res1;
        }

        @Override
        protected void onPostExecute(ArrayList<Remarks> result) {

            if (this.dialog.isShowing()) {

                this.dialog.dismiss();

                DataBaseHelper placeData = new DataBaseHelper(LoginActivity.this);
                placeData.insertProvisional(result);
                Intent userlogin = new Intent(getApplicationContext(), HomeActivity.class);
                userlogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(userlogin);
                finish();
            }


        }


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "बाहर निकलने के लिए कृपया वापस क्लिक करें", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
