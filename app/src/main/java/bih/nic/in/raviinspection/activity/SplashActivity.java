package bih.nic.in.raviinspection.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.database.DataBaseHelper;
import bih.nic.in.raviinspection.database.WebServiceHelper;
import bih.nic.in.raviinspection.entity.Versioninfo;
import bih.nic.in.raviinspection.utilities.CommonPref;
import bih.nic.in.raviinspection.utilities.GlobalVariables;
import bih.nic.in.raviinspection.utilities.MarshmallowPermission;
import bih.nic.in.raviinspection.utilities.Utiilties;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends Activity {

    Context context;
    MarshmallowPermission permission;
    long isDataDownloaded = -1;
    public static SharedPreferences prefs;
    private static int SPLASH_TIME_OUT = 1000;
    @SuppressLint("NewApi")
    ActionBar actionBar;
    private static final int PERMISSION_ALL = 0;
    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;


        DataBaseHelper helper = new DataBaseHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*if (!Utiilties.isGPSEnabled(SplashActivity.this)) {
            Utiilties.displayPromptForEnablingGPS(SplashActivity.this);
        } else {
            new CheckUpdate().execute();
        }*/

    }

    private void checkAppUseMode()
    {
            boolean net = false;

            permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
            if (permission.result == -1 || permission.result == 0)
                net = Utiilties.isOnline(SplashActivity.this);
            if (net) {

                new CheckUpdate().execute();

            }  else {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);

            }

    }

    private void start() {

        final String check = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        if (!check.equals("")) {
            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }else{
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }


    private void showDailog(AlertDialog.Builder ab,
                            final Versioninfo versioninfo) {

        if (versioninfo.isVerUpdated()) {

            if (versioninfo.getPriority() == 0) {

                start();
            } else if (versioninfo.getPriority() == 1) {

                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());

                ab.setPositiveButton(getResources().getString(R.string.update),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {



                                Intent launchIntent = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package
                                // name
                                // and
                                // activity
                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri
                                        .parse("market://details?id="
                                                + getApplicationContext()
                                                .getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                            }


                        });
                ab.setNegativeButton(getResources().getString(R.string.ignore),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                dialog.dismiss();

                                start();
                            }

                        });

                ab.show();

            } else if (versioninfo.getPriority() == 2) {

                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());
                ab.setPositiveButton(getResources().getString(R.string.update),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

								/*Intent myWebLink = new Intent(
										android.content.Intent.ACTION_VIEW);
								myWebLink.setData(Uri.parse(versioninfo
										.getAppUrl()));

								startActivity(myWebLink);

								dialog.dismiss();*/

                                Intent launchIntent = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package
                                // name
                                // and
                                // activity
                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri
                                        .parse("market://details?id="
                                                + getApplicationContext()
                                                .getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                                // finish();
                            }
                        });

                ab.setNegativeButton(getResources().getString(R.string.ignore),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                dialog.dismiss();

                                start();
                            }

                        });

                ab.show();
            }
        } else {

            start();
        }

    }


    private class CheckUpdate extends AsyncTask<Void, Void, Versioninfo> {


        CheckUpdate() {

        }


        @Override
        protected void onPreExecute() {

        }

        @SuppressLint("MissingPermission")
        @Override
        protected Versioninfo doInBackground(Void... Params) {

            TelephonyManager tm = null;
            String imei = null;

            permission=new MarshmallowPermission(SplashActivity.this,Manifest.permission.READ_PHONE_STATE);
            if(permission.result==-1 || permission.result==0)
            {
                try
                {
                    tm= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    if(tm!=null) imei = tm.getDeviceId();
                }catch(Exception e){}
            }

            String version = null;
            try {
                version = getPackageManager().getPackageInfo(getPackageName(),
                        0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Versioninfo versioninfo = WebServiceHelper.CheckVersion(imei,
                    version);

            return versioninfo;

        }

        @Override
        protected void onPostExecute(final Versioninfo versioninfo) {

            final AlertDialog.Builder ab = new AlertDialog.Builder(
                    SplashActivity.this);
            ab.setCancelable(false);
            if (versioninfo != null && versioninfo.isValidDevice()) {

                CommonPref.setCheckUpdate(getApplicationContext(),
                        System.currentTimeMillis());

                if (versioninfo.getAdminMsg().trim().length() > 0
                        && !versioninfo.getAdminMsg().trim()
                        .equalsIgnoreCase("anyType{}")) {

                    ab.setTitle(versioninfo.getAdminTitle());
                    ab.setMessage(Html.fromHtml(versioninfo.getAdminMsg()));
                    ab.setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    dialog.dismiss();

                                    showDailog(ab, versioninfo);

                                }
                            });
                    ab.show();
                } else {
                    showDailog(ab, versioninfo);
                }
            } else {
                if (versioninfo != null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_device_text),
                            Toast.LENGTH_LONG).show();

                }
                else
                {
                    start();

                }
            }

        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//        checkAppUseMode();
        requestRequiredPermission();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        int index = 0;
        Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
        for (String permission : permissions)
        {
            PermissionsMap.put(permission, grantResults[index]);
            index++;
        }

        if(PermissionsMap.get(ACCESS_FINE_LOCATION) != null && PermissionsMap.get(CAMERA) != null&& PermissionsMap.get(WRITE_EXTERNAL_STORAGE) != null){
            if((PermissionsMap.get(ACCESS_FINE_LOCATION) != 0)||PermissionsMap.get(ACCESS_COARSE_LOCATION) != 0 || PermissionsMap.get(CAMERA) != 0 || PermissionsMap.get(WRITE_EXTERNAL_STORAGE) != 0){

                Toast.makeText(this, "Location and Camera permissions are required", Toast.LENGTH_SHORT).show();
                //finish();
                requestRequiredPermission();
            }
            else
            {
                checkOnline();
            }
        }else{
            finish();
        }

    }


    private void requestRequiredPermission(){
        String[] PERMISSIONS = {
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
                CAMERA,
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE,
                READ_PHONE_STATE,
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }else{
            checkOnline();
        }
    }

    public  boolean hasPermissions(Context context, String... allPermissionNeeded)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && allPermissionNeeded != null)
            for (String permission : allPermissionNeeded)
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
        return true;
    }


    protected void checkOnline() {
        // TODO Auto-generated method stub
        super.onResume();


        if (Utiilties.isOnline(SplashActivity.this) == false) {

            AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setTitle("Alert Dialog !!!");
            ab.setMessage(Html.fromHtml("<font color=#000000>Internet Connection is not avaliable... \n Please Turn ON Network Connection \n To Turn ON Network Connection Press Yes Button Else To Exit Press Cancel Button.</font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                                    int whichButton) {
                    GlobalVariables.isOffline = false;
                    Intent I = new Intent(
                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });
            ab.setNegativeButton("Go Offline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                                    int whichButton) {

                    start();
                }
            });


            ab.show();

        } else {

            GlobalVariables.isOffline = false;
            new CheckUpdate().execute();
           // CheckUpdate();
        }
    }

}
