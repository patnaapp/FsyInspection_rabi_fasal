package bih.nic.in.raviinspection.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.database.DataBaseHelper;
import bih.nic.in.raviinspection.utilities.GlobalVariables;
import bih.nic.in.raviinspection.utilities.Utiilties;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;



public class Feedbackform extends AppCompatActivity {

    //
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    public final int REQ_CODE_SPEECH_INPUT = 100;
    LinearLayout lnprogressBar ;
    public Context mContext;
    public Activity mActivity;
    ArrayList mobnum_for_voice_cn_txt_msg=new ArrayList();

    String message="Please help me.My Loc ID is";
    String phoneNo_for_sos;
    public static final String SERVICENAMESPACE = "http://mobapp.bih.nic.in/";

    public static final String SERVICEURL = "http://mobapp.bih.nic.in/CommonMobAppwebservice.asmx";


    public static final String REGISTRATION_METHOD = "UploadAppFeedback";
    public PopupWindow mPopupWindowVoice;
    Random random ;
    public static final int RequestPermissionCode = 1;
    String locid;
    DataBaseHelper helper;
    LinearLayout lnvoice,lnvoicech1,lnvoicech2;


    boolean issms_service_active=true;
    //==============VOICE MESSAGE--------------------
    File _thisFile;

    public TextView mTimerTextView;

    public MediaRecorder mRecorder;
    public long mStartTime = 0;

    public int[] amplitudes = new int[100];
    public int i = 0;


    public Handler mHandler = new Handler();
    public Handler voiceTimeHandler = new Handler();
    final Runnable voiceTimeRunnable = null;

    private float x1,x2;
    static final int MIN_DISTANCE = 75;
    Runnable mTickExecutor=null;
    public File mOutputFile;

    byte[] soundBytes;
    String recordingFilepath;
    String AUDIO_RECORDER_FOLDER="VoiceRecorder";
    String fileName;
    boolean run =false;
    int file_size =0;
    TextView txtuploading,txtbstatus;
    LinearLayout llninternet;
    ImageView imgwif,imgsms;
    //---------------------------------------------

    View _view;
    RelativeLayout rlmain;
    //----------
    EditText txtOtherYojana;
    //EditText txtOtherMotorPumpCapacity;
    DataBaseHelper localDBHelper;
    String wantToEdit="no";
    ImageView img_home;
    ProgressDialog pd;

    String ids,uid;
    Button btnsave ;
    String remarkmessage,mobile;
    EditText txtMobNum,txtRemarks;

    TextView txtheader;
    String _WardCode,_WardName,_FYearCode,_SchemeName,_SchemeCode;
    Spinner spinnerAppName,spinnerMsgType;

    ArrayList<APPNAME> AppNameList = new ArrayList<>();
    ArrayAdapter<String> appnameadapter;
    String _varappnameNAME="",_varappnameID="";


    String[] MsgType = new String[]{
            "-चुनें-",
            "सुझाव",
            "समस्या"
    };
    final List<String> MsgTypeList = new ArrayList<>(Arrays.asList(MsgType));
    ArrayAdapter<String> spinnerMsgTypeAdapter;
    String _varMsgTypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackform);
        uid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");

        init();

        getMobAppList();
        spinnerAppName.requestFocus();

        spinnerAppName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {

                    APPNAME wrd = AppNameList.get(arg2 - 1);
                    _varappnameID = wrd.get_MobileAppId();
                    _varappnameNAME = wrd.get_AppName();
                }
                else
                {
                    _varappnameID = "0";
                    _varappnameNAME= "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        spinnerMsgType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {
                    _varMsgTypeName = MsgTypeList.get(arg2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utiilties.isOnline(Feedbackform.this))
                {
                    String isvalid = validateRecordBeforeSaving();


                    if (isvalid.equalsIgnoreCase("yes")) {


                        registration();
                    }
                }
                else
                {
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(Feedbackform.this);
                    alertDialog.setTitle("Alert Dialog");
                    alertDialog.setMessage("Plz Check Your Internet Connection");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=alertDialog.create();
                    alert.show();
                }




            }
        });
    }

    public void registration()
    {
        Feedback feedback = new Feedback();
        remarkmessage = txtRemarks.getText().toString();
        mobile = txtMobNum.getText().toString();

        boolean cancelRegistration = false;
        String isValied = "yes";
        View focusView = null;

        if (TextUtils.isEmpty(remarkmessage))
        {
            txtRemarks.setError("सुझाव / समस्या डालें|");

            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(mobile))
        {
            txtMobNum.setError("मोबाइल नंबर डालें |");
            focusView = txtMobNum;
            cancelRegistration = true;
        }
        else if (mobile.length() != 10)
        {
            txtMobNum.setError("मोबाइल नंबर डालें |");
            focusView = txtMobNum;
            cancelRegistration = true;
        }

        if (cancelRegistration)
        {

            focusView.requestFocus();
        }
        else
            {

            feedback.set_MobileAppname(_varappnameID);

            feedback.set_messagetype(_varMsgTypeName);
            feedback.setMessage(remarkmessage);

            feedback.setMobile(mobile);
            feedback.setMobile(uid);

            new Feedbackform.RegistrationTask().execute(feedback);

        }
    }

    public  void init()
    {
        localDBHelper = new DataBaseHelper(this);

        // TextView txtMainHeader=(TextView) findViewById(R.id.txtHeaderTop);

//        if(!_SchemeName.equalsIgnoreCase("")) {
//            txtMainHeader.setText(_SchemeName);
//        }
//        else
//        {
//            txtMainHeader.setText(R.string.headertext);
//        }
//        txtMainHeader.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        txtMainHeader.setSelected(true);
//        txtMainHeader.setSingleLine(true);

        btnsave = (Button) findViewById(R.id.btnsave);
        // img_home=(ImageView) findViewById(R.id.imghome);


        uid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        //  _SchemeName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SCHEME_NAME", "");

        txtRemarks=(EditText) findViewById(R.id.txtRemarks);

        txtMobNum=(EditText) findViewById(R.id.ET_mobile);

        spinnerAppName=(Spinner) findViewById(R.id.spinnerMobileAppName);
        spinnerMsgType=(Spinner) findViewById(R.id.spinnersandesh);

        spinnerMsgTypeAdapter=new ArrayAdapter(this,R.layout.dropdownlist,MsgTypeList);
        spinnerMsgType.setAdapter(spinnerMsgTypeAdapter);
    }


    private void loadWardSpinnerData(ArrayList<APPNAME> pList)
    {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        String setWID="0";
        for(int i=0;i<pList.size();i++)
        {
            StringList.add(pList.get(i).get_AppName());
        }
        appnameadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        spinnerAppName.setAdapter(appnameadapter);

        if(getIntent().hasExtra("EDIT"))
        {
            spinnerAppName.setSelection(Integer.parseInt(setWID));
        }
        if(getIntent().hasExtra("VIEW"))
        {
            spinnerAppName.setSelection(Integer.parseInt(setWID));
        }
    }

    private class LoadAppNameList extends AsyncTask<String, String, ArrayList<APPNAME>>
    {

        private final ProgressDialog dialog = new ProgressDialog(
                Feedbackform.this);

        LoadAppNameList()
        {
        }

        @Override
        protected void onPreExecute()
        {
            pd.setMessage("Loading App List...");
            pd.show();
        }

        @Override
        protected ArrayList<APPNAME> doInBackground(String... param)
        {

            AppNameList =loadAppNameList();
            return AppNameList;
        }

        @Override
        protected void onPostExecute(ArrayList<APPNAME> result)
        {
            if (pd.isShowing())
            {
                pd.dismiss();
            }

            if(result!=null)
            {
                if (result.size()>0)
                {

                    AppNameList=result;
                    loadLocalWardINSpinnerdata(result);
                }
                else
                    {

                    Toast.makeText(getApplicationContext(), "Ward loading failed", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void loadLocalWardINSpinnerdata(ArrayList<APPNAME> result)//yes-no
    {
        AppNameList=result;
        if(AppNameList.size()>0 )
        {
            loadWardSpinnerData(AppNameList);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //Toast.makeText(getApplicationContext(),"Back press is not allowed, To view/update previous page info login again.",Toast.LENGTH_LONG).show();
        finish();
        return;
    }

    public static ArrayList<APPNAME> loadAppNameList()
    {

        String PACS_VM_LIST_METHOD=null;


        SoapObject request = new SoapObject("http://mobapp.bih.nic.in/", "GetAppDtlLst");

        SoapObject res1;
        try
        {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping("http://mobapp.bih.nic.in/",APPNAME.SCHEME_CLASS.getSimpleName(), APPNAME.SCHEME_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    "http://mobapp.bih.nic.in/CommonMobAppwebservice.asmx");
            androidHttpTransport.call("http://mobapp.bih.nic.in/" + "GetAppDtlLst", envelope);

            res1 = (SoapObject) envelope.getResponse();

        }
        catch (Exception e)
        {
            Log.e("Exception1: ",e.getLocalizedMessage());
            Log.e("Exception2: ",e.getMessage());
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<APPNAME> pvmArrayList = new ArrayList<>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    APPNAME vill = new APPNAME(final_object);
                    pvmArrayList.add(vill);
                }
            } else
                return pvmArrayList;
        }

        return pvmArrayList;
    }
    public void getMobAppList()
    {
        if(Utiilties.isOnline( Feedbackform.this))
        {
            new LoadAppNameList().execute();
        }
        else
        {
            AlertDialog.Builder ab = new AlertDialog.Builder(Feedbackform.this);
            ab.setMessage("इंटरनेट कनेक्शन उपलब्ध नहीं है। कृपया नेटवर्क कनेक्शन चालू करें");
            ab.setPositiveButton("कनेक्शन चालू करें", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    GlobalVariables.isOffline = false;
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();
        }
    }

    public void OnClick_VoiceToTextMsg(View v)
    {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0)
        {
            Toast.makeText(mContext, "Speech Recognizer Found", Toast.LENGTH_SHORT).show();
            //new CustomToast().Show_Toast(HomeActivity.this, _view, "Speech Recognizer Found.");
            promptSpeechInput2();
        }
        else
        {
            Toast.makeText(mContext, "No Speech Recognizer Found", Toast.LENGTH_SHORT).show();
            // new CustomToast().Show_Toast(HomeActivity.this, _view, "No Speech Recognizer Found.");
        }
    }
    public void promptSpeechInput2() {
        Log.d("enter speech prompt====", "====enter speech prompt");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Tap and Speak");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(Feedbackform.this,"Speech not Supported", Toast.LENGTH_SHORT).show();

        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == Activity.RESULT_OK && null != data) {
//                    ArrayList<String> matches = data
//                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    Log.d("result==", "" + matches.get(0));
//                    // tvMsgVoiceTxt.setText(matches.get(0).toString().toLowerCase());
//                    String thismessage=matches.get(0).toString().toLowerCase();
//                    Toast.makeText(mContext, thismessage+", message sending.", Toast.LENGTH_SHORT).show();
//                    // new CustomToast().Show_Toast(HomeActivity.this, _view, thismessage+", message sending.");
//                    for (int x = 0; x < mobnum_for_voice_cn_txt_msg.size(); x++) {
//                        txtRemarks.setText(mobnum_for_voice_cn_txt_msg.get(0).toString());
//                    }
//                }
//
//                break;
//            }
//        }
//    }


    private class RegistrationTask extends AsyncTask<Feedback, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(Feedbackform.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(Feedbackform.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Registration...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Feedback... param) {

            return Registration(param[0]);
        }

        @Override

        protected void onPostExecute(String result) {

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {

                if (result.contains("1")){

                    String[] v=result.split(",");
                    String uid="n/a",pwd="n/a";
                    if(v.length>2)
                    {
                        uid=v[1];
                        pwd=v[2];
                    }
                    try {

                        // Toast.makeText(Feedbackform.this, "Success", Toast.LENGTH_SHORT).show();
                        chk_msg("Feedback Submitted Sucessfully");
                    } catch (Exception ex) {
                        Toast.makeText(Feedbackform.this,
                                "ERROR-Exception:" + ex.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                else {

                    //unknown return type
                    Toast.makeText(Feedbackform.this,
                            "Not Registered sucess", Toast.LENGTH_SHORT)
                            .show();
                }
            } else {

                Toast.makeText(Feedbackform.this,
                        "Please try again later: ", Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }

    public void chk_msg(String msg) {
        // final String wantToUpdate;
        AlertDialog.Builder ab = new AlertDialog.Builder(Feedbackform.this);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.logo);
        ab.setTitle("रबी फसल सहायता निरीक्षण");
        ab.setMessage(msg);
        Dialog dialog = new Dialog(Feedbackform.this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }

    public static String Registration(Feedback user) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,REGISTRATION_METHOD);
            request.addProperty("_MobileAppId",user.get_MobileAppname());
            request.addProperty("_Subject",user.get_messagetype());
            request.addProperty("_Message",user.getMessage());
            request.addProperty("_ContactNo",user.getMobile());
            request.addProperty("_EntryBy",user.getUserid());


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    Feedback.USER_CLASS.getSimpleName(),
                    Feedback.USER_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTRATION_METHOD,
                    envelope);

            Object result = envelope.getResponse();

            if (result != null) {
                // Log.d("", result.toString());

                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private String validateRecordBeforeSaving() {
        String isvalid = "no";



        if ((spinnerAppName != null && spinnerAppName.getSelectedItem() != null)) {
            if ((String) spinnerAppName.getSelectedItem() !="-चुनें-") {
                isvalid = "yes";
            } else {
                Toast.makeText(Feedbackform.this, "Please select AppName", Toast.LENGTH_LONG).show();

                spinnerAppName.requestFocus();
                return "no";
            }
        }
        if ((spinnerMsgType != null && spinnerMsgType.getSelectedItem() != null)) {
            if ((String) spinnerMsgType.getSelectedItem() !="-चुनें-") {
                isvalid = "yes";
            } else {
                Toast.makeText(Feedbackform.this, "Please select Message Type", Toast.LENGTH_LONG).show();

                spinnerMsgType.requestFocus();
                return "no";
            }
        }



        return isvalid;
    }
}

