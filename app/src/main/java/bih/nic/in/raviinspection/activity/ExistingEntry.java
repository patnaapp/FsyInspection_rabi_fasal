package bih.nic.in.raviinspection.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.adapter.ListDataADapter;
import bih.nic.in.raviinspection.database.DataBaseHelper;
import bih.nic.in.raviinspection.database.WebServiceHelper;
import bih.nic.in.raviinspection.entity.FarmerDetails;
import bih.nic.in.raviinspection.entity.Panchayat;
import bih.nic.in.raviinspection.entity.Village;
import bih.nic.in.raviinspection.utilities.CommonPref;
import bih.nic.in.raviinspection.utilities.GlobalVariables;
import bih.nic.in.raviinspection.utilities.Utiilties;

public class ExistingEntry extends AppCompatActivity {
    ImageView imgupp,imgcomp;
    TextView txtdstname,txtulbname,txtuser,txt_userrole,textversion;
    Toolbar toolbarurban;
    RadioGroup radioGroup1;
    String version ="";
    Spinner spn_panchayat,spn_village;
    ArrayAdapter<String> spnpanchayatadapter;
    ArrayList<Panchayat> Spnpanchayatlist = new ArrayList<Panchayat>();
    ArrayList<Village> VillageList = new ArrayList<Village>();
    String str_panchayat="",str_Village="";
    EditText et_search;
    ImageView iv_serch,iv_download;
    ArrayAdapter<String>Villageadapter;
    long count;
    EditText edtsearch;
    ListView listdata;
    LinearLayout linsearch_lt;
    ArrayList<FarmerDetails> dataList;
    ListDataADapter adapter;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> namespanchayat;
    public static ProgressDialog progressDialog;
    int _varOnPostCounterViilage =1;
    DataBaseHelper localDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_entry);
        dataBaseHelper= new DataBaseHelper(ExistingEntry.this);
        progressDialog = new ProgressDialog(this);
        //pd1 = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        imgcomp=(ImageView)findViewById(R.id.imgcompleted);
        imgupp=(ImageView)findViewById(R.id.imguploadnew);
        txtdstname=(TextView)findViewById(R.id.txtdname);
        txtulbname=(TextView)findViewById(R.id.txtname);
        toolbarurban=(Toolbar)findViewById(R.id.toolbar);
        edtsearch=(EditText)findViewById(R.id.edtsearch);
        radioGroup1=(RadioGroup)findViewById(R.id.radiogrp);
        listdata=(ListView)findViewById(R.id.listView);

        et_search=(EditText) findViewById(R.id.et_search);
        iv_serch=(ImageView) findViewById(R.id.iv_serch);
        iv_download=(ImageView) findViewById(R.id.iv_download);

        spn_panchayat=(Spinner) findViewById(R.id.spn_ward);
        spn_village=(Spinner) findViewById(R.id.spn_village);
        spn_panchayat.setFocusable(true);
        spn_panchayat.setFocusableInTouchMode(true);
        spn_panchayat.requestFocus();

        txtdstname.setText(CommonPref.getUserDetails(getApplicationContext()).getDistName());
        txtulbname.setText(CommonPref.getUserDetails(getApplicationContext()).getBlockName());

        BindListData();
        loadwardSpinnerData();
        spn_panchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long id) {
                Panchayat ward = new Panchayat();
                spn_village.setSelection(0);
                if (arg2 != 0) {
                    ward = Spnpanchayatlist.get(arg2 - 1);
                    str_panchayat = ward.getPanchayatCode().trim();


                   // BindListData();
                    loadVillagelist(str_panchayat);

                }
                else{
                    str_panchayat="";

                        BindListData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                str_panchayat="";
            }
        });
        spn_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long id) {
                Village ward = new Village();
                if (arg2 != 0) {
                    ward = VillageList.get(arg2 - 1);
                    str_Village = ward.getVillageCode().trim();

                    BindListDataPanVillWise();


                }
                else{
                    str_Village="";

                    BindListDataPanVillWise();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                str_panchayat="";
            }
        });

        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utiilties.isOnline(ExistingEntry.this) == true) {
                    if (isPanchayatListDownloaded().equalsIgnoreCase("yes")) {
                        namespanchayat = new ArrayList<String>();
                        DataBaseHelper placeData = new DataBaseHelper(ExistingEntry.this);
                        SQLiteDatabase db = placeData.getReadableDatabase();
                        Cursor cursor = db.rawQuery(" SELECT * FROM Panchayat", null);

                        Log.e("PAN_COUNT",String.valueOf( cursor.getCount()));
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                Log.e("PAN_CODE_DB",cursor.getString(cursor.getColumnIndex("PanchayatCode")));
                                namespanchayat.add(cursor.getString(cursor.getColumnIndex("PanchayatCode")));
                            }
                        }
                        cursor.close();
                        db.close();
                        placeData.getReadableDatabase().close();

                        progressDialog.show();
                        if(namespanchayat.size()>0) {
                            new GetVillageList(namespanchayat.get(0)).execute(); //for first id
                        }
                        else
                        {
                            Toast.makeText(ExistingEntry.this,"First download Panchayat List",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        POPUpMsg("Please download Village Data.");
                    }
                } else {
                    checkOnline();
                }

            }
        });
        listdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id)
            {
                Intent i=new Intent(getApplicationContext(),EntryDetail.class);
                i.putExtra("index", "");
                i.putExtra("isEdit", "no");
                i.putExtra("User_Id",dataList.get(position).getUser_ID());
                i.putExtra("WardId",str_panchayat);
                //i.putExtra("RegNo",dataList.get(position).getRegistrationNO());
                String RegNo=dataList.get(position).getRegistrationNO();
                //String Userrole=result.getUserRole();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("REG_NO", RegNo ).commit();
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        iv_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_search.getText().toString().trim().length()>0)
                {
                       //BindListData();
                        getDataForThisSearchStr(et_search.getText().toString());

                }
                else
                {
                    Toast.makeText(ExistingEntry.this, "कृपया मोबाइल नंबर का अन्तिम तीन संख्या या किसान का नाम डाले", Toast.LENGTH_SHORT).show();
                }


            }
        });

//        imgupp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Utiilties.isOnline(ExistingEntry.this)) {
//
//                    android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(
//                            ExistingEntry.this);
//                    ab.setTitle("अपलोड!");
//                    ab.setMessage("क्या आप सर्वर पर लंबित सभी अपलोड करना चाहते हैं ?");
//                    ab.setNegativeButton("नहीं", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    ab.setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            dialog.dismiss();
//                            localDBHelper=new DataBaseHelper(ExistingEntry.this);
//
//                            if (localDBHelper.getUploadCommunityCount() > 0) {
//                                List<FarmerDetails> locDatas = localDBHelper.getAllDataListCommunityData();
//                                Log.d("Datainsecondtable",""+locDatas.size());
//                                for (FarmerDetails cn : locDatas) {
//                                    new uploadingLocData(cn).execute();
//                                }
//                            } else {
//                                Toast.makeText(getApplicationContext(), "कोई रिकॉर्ड नहीं मिला",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
//                    ab.show();
//
//                } else
//
//                {
//                    Toast.makeText(ExistingEntry.this, " कोई इंटरनेट कनेक्शन नहीं ! \n कृपया अपनी इंटरनेट कनेक्टिविटी की जांच करें.",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }
    public String isPanchayatListDownloaded() {
        String isd = "no";
        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM Panchayat", null);
        int x = cursor.getCount();
        cursor.close();
        db.close();
        placeData.getReadableDatabase().close();
        if (x > 0) {
            isd = "yes";
        }
        return isd;
    }
    protected void checkOnline() {
        // TODO Auto-generated method stub
        super.onResume();

        if (Utiilties.isOnline(ExistingEntry.this) == false) {

            AlertDialog.Builder ab = new AlertDialog.Builder(
                    ExistingEntry.this);
            ab.setMessage("Internet Connection is requird to download data");
            ab.setPositiveButton("Turn On Network Connection",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            GlobalVariables.isOffline = false;
                            Intent I = new Intent(
                                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });
            ab.setNegativeButton("Close",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            dialog.dismiss();
                        }
                    });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();

        } else {

            GlobalVariables.isOffline = false;
            // new CheckUpdate().execute();
        }
    }
    public void POPUpMsg(String msg) {
        // final String wantToUpdate;
        AlertDialog.Builder ab = new AlertDialog.Builder(ExistingEntry.this);
        ab.setIcon(R.drawable.downloadmn);
        ab.setTitle("Alert");
        ab.setMessage(msg);
        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();

            }
        });

        ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }
    public void loadVillagelist(String PanchayatCode) {

        VillageList = dataBaseHelper.getVillageLocal(PanchayatCode);
        String[] typeNameArray = new String[VillageList.size() + 1];
        typeNameArray[0] = "-Select Vilage-";
        int i = 1;
        for (Village type : VillageList) {

            String villcode=type.getVillageCode();
            long wrdcount= dataBaseHelper.getVillagecount(villcode,CommonPref.getUserDetails(ExistingEntry.this).getUserID(),PanchayatCode);
            if(wrdcount>0) {
                typeNameArray[i] = type.getVillageName() +"  "+"(" +wrdcount+")";
            }
            else{
                typeNameArray[i] = type.getVillageName();
            }
            i++;
        }
        Villageadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, typeNameArray);
        Villageadapter.setDropDownViewResource(R.layout.dropdownlist);
        spn_village.setAdapter(Villageadapter);

        BindListData();
    }


    public void getDataForThisSearchStr(String searchstr)
    {
//
        if(str_panchayat.equalsIgnoreCase(""))
        {
            str_panchayat="0";
        }
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {

            dataList = new ArrayList<FarmerDetails>();
            List<FarmerDetails> data = helper.getSearcheDataList(searchstr,str_panchayat);
            for (FarmerDetails an : data) {
                dataList.add(an);
            }
            if (dataList.size() > 0) {
                adapter = new ListDataADapter(ExistingEntry.this, R.layout.listdesign, dataList);
                listdata.setAdapter(adapter);

            }
            else {
                adapter = new ListDataADapter(ExistingEntry.this, R.layout.listdesign, dataList);
                listdata.setAdapter(adapter);
                Toast.makeText(ExistingEntry.this, "कोई डेटा नहीं मिला!", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        } catch (Exception ex)
        {
            Toast.makeText(ExistingEntry.this, "लोडिंग विफल", Toast.LENGTH_SHORT).show();
        }
    }

    public void BindListData() {
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {

            dataList = new ArrayList<FarmerDetails>();
            List<FarmerDetails> data = helper.getProgressList(str_panchayat,CommonPref.getUserDetails(ExistingEntry.this).getUserID());

            for (FarmerDetails an : data) {
                dataList.add(an);
            }
            Log.e("dataList.size()",""+dataList.size());
            if (dataList.size() > 0) {
                adapter = new ListDataADapter(ExistingEntry.this, R.layout.listdesign, dataList);
                listdata.setAdapter(adapter);


            }
            else {
                adapter = new ListDataADapter(ExistingEntry.this, R.layout.listdesign, dataList);
                listdata.setAdapter(adapter);
                Toast.makeText(ExistingEntry.this, "कोई डेटा नहीं मिला!", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        } catch (Exception ex)
        {
            Toast.makeText(ExistingEntry.this, "लोडिंग विफल", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
      //  loadVillagelist(str_panchayat);
    }
    private void BindListDataPanVillWise() {
        if(!str_panchayat.equalsIgnoreCase(""))
        {
            DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
            try {

                dataList = new ArrayList<FarmerDetails>();
                List<FarmerDetails> data = helper.getProgressListPanVillWise(str_panchayat,CommonPref.getUserDetails(ExistingEntry.this).getUserID(),str_Village);


                for (FarmerDetails an : data) {
                    dataList.add(an);
                }
                if (dataList.size() > 0) {
                    adapter = new ListDataADapter(ExistingEntry.this, R.layout.listdesign, dataList);
                    listdata.setAdapter(adapter);
                }


                else {
                    adapter = new ListDataADapter(ExistingEntry.this, R.layout.listdesign, dataList);
                    listdata.setAdapter(adapter);
                    Toast.makeText(ExistingEntry.this, "कोई डेटा नहीं मिला!", Toast.LENGTH_SHORT).show();
                }
                Log.e("dataList.size()2",str_panchayat+":"+str_Village +": "+dataList.size());
                adapter.notifyDataSetChanged();
            } catch (Exception ex)
            {
                Toast.makeText(ExistingEntry.this, "लोडिंग विफल",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void loadwardSpinnerData(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        //Spnwardlist = dataBaseHelper.getwardList();

        //String UlbCode = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UlbCode", "");
        String BlockCode = PreferenceManager.getDefaultSharedPreferences(ExistingEntry.this).getString("ChoosenBlock", "");
        Spnpanchayatlist = dataBaseHelper.getPanchayatList(CommonPref.getUserDetails(ExistingEntry.this).getUserID(),BlockCode);
        String[] divNameArray = new String[Spnpanchayatlist.size() + 1];
        divNameArray[0] = "-All Panchayat-";
        int i = 1;
//        for (wardList apl : Spnwardlist) {
//            divNameArray[i] = apl.get_wardName();
//            i++;
//        }

        for (Panchayat apl : Spnpanchayatlist) {
            String x=apl.getPanchayatCode();
            long wrdcount= dataBaseHelper.getpanchayatcount(x,CommonPref.getUserDetails(ExistingEntry.this).getUserID());
            if(wrdcount>0) {
                divNameArray[i] = apl.getPanchayatName() +"  "+"(" +wrdcount+")";
            }
            else{
                divNameArray[i] = apl.getPanchayatName();
            }
            i++;
        }

        spnpanchayatadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
        spnpanchayatadapter.setDropDownViewResource(R.layout.dropdownlist);
        spn_panchayat.setAdapter(spnpanchayatadapter);

    }


    public class GetVillageList extends AsyncTask<Void, String, ArrayList<Village>> {
        String p_code;
        public GetVillageList(String pancode) {
            p_code=pancode;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading Village list  " + getPanchayatName(p_code));
        }

        public String getPanchayatName(String vcode)
        {
            String vName = "";
            DataBaseHelper placeData = new DataBaseHelper(getBaseContext());
            SQLiteDatabase db = placeData.getReadableDatabase();
            Cursor cursor = db.rawQuery(" SELECT * FROM Panchayat WHERE PanchayatCode='" + vcode + "'", null);


            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    vName = cursor.getString(cursor.getColumnIndex("PanchayatName"));

                }
            }
            cursor.close();
            db.close();
            placeData.getReadableDatabase().close();
            return vName;
        }
        @Override
        protected ArrayList<Village> doInBackground(Void... params) {

            Log.e("PANCODE",p_code);
            ArrayList<Village> res1 = WebServiceHelper.loadVillageList(p_code);
            return res1;
        }

        // protected void onPostExecute(ArrayList<District> result) {
        protected void onPostExecute(ArrayList<Village> result) {


            if (result != null) {
                // for (int i = 0; i < result.size(); i++) {
                // districts.add(result.get(i).getDistrictName().toString());
                DataBaseHelper placeData = new DataBaseHelper(ExistingEntry.this);
                placeData.insertVillageList(result);

                if (_varOnPostCounterViilage < namespanchayat.size() - 1) {
                    _varOnPostCounterViilage++;
                    Log.e("LIST COUNT2",""+namespanchayat.size());
                    Log.e("LIST COUNT3",""+_varOnPostCounterViilage);
                    new GetVillageList(namespanchayat.get(_varOnPostCounterViilage)).execute();
                } else if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Download Completed", Toast.LENGTH_SHORT).show();
                }


            } else {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                AlertDialog.Builder ab = new AlertDialog.Builder(ExistingEntry.this);
                ab.setTitle("SERVER UNREACHABLE");
                ab.setMessage("Server is busy or down. Please try later.");
                ab.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });

                ab.show();
            }
        }
    }



//    public void imgup(View view){
//        if (Utiilties.isOnline(ExistingEntry.this)) {
//
//            android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(
//                    ExistingEntry.this);
//            ab.setTitle("अपलोड!");
//            ab.setMessage("क्या आप सर्वर पर  डाटा अपलोड करना चाहते हैं ?");
//            ab.setNegativeButton("नहीं", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int whichButton) {
//                    dialog.dismiss();
//                }
//            });
//
//            ab.setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog, int whichButton) {
//                    dialog.dismiss();
//                    localDBHelper=new DataBaseHelper(ExistingEntry.this);
//
//                    if (localDBHelper.getUploadCommunityCount() > 0) {
//                        List<FarmerDetails> locDatas = localDBHelper.getAllDataListCommunityData();
//                        Log.d("Datainsecondtable",""+locDatas.size());
//                        for (FarmerDetails cn : locDatas) {
//                            new uploadingLocData(cn).execute();
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "कोई रिकॉर्ड नहीं मिला",
//                                Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });
//            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
//            ab.show();
//
//        } else
//
//        {
//            Toast.makeText(ExistingEntry.this, " कोई इंटरनेट कनेक्शन नहीं ! \n कृपया अपनी इंटरनेट कनेक्टिविटी की जांच करें.",
//                    Toast.LENGTH_LONG).show();
//        }
//
//    }

    public class uploadingLocData extends AsyncTask<String, Void, String> {
        String result11;
        ProgressDialog pd1;
        FarmerDetails farmerDetails;
        public uploadingLocData(FarmerDetails getdatalist){
            this.farmerDetails = getdatalist;
        }



        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd1 = new ProgressDialog(ExistingEntry.this);
            pd1.setMessage("डेटा अपलोड कर रहा है प्रतीक्षा करें");
            pd1.setCancelable(false);
            pd1.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //String USERID = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USER_ID", "");
            //String PWD = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PWD", "");

            result11 = WebServiceHelper.sendLocaldata(farmerDetails);



            return result11;
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd1.dismiss();
            String reg_num="0";
            if (result.toString().contains("1")) {
                Toast.makeText(getApplicationContext(), "डेटा सफलतापूर्वक अपलोड किया गया है !", Toast.LENGTH_SHORT).show();
                boolean isDel=localDBHelper.deleterowCommunity1(farmerDetails.getRegistrationNO());
                if (isDel) {
                    //count = dataBaseHelper.getPendingUploadCount(CommonPref.getUserDetails(ExistingEntry.this).getUserID());
                    //  pendingTask.setText("" + count);
                    boolean is_del=localDBHelper.deleterowPhaseCommunity1Actual(farmerDetails.getRegistrationNO());
                    if(is_del)
                    {
                        reg_num=farmerDetails.getRegistrationNO().toString().trim();
                        Toast.makeText(ExistingEntry.this, "वास्तविक डेटा सूची आईडी से हटाया गया:" + reg_num, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ExistingEntry.this, "वास्तविक डेटा सूची आईडी से निकालने में विफल रहा:" + reg_num, Toast.LENGTH_SHORT).show();
                    }
                    //String root = Environment.getExternalStorageDirectory().toString();
//                    File fid = new File("/storage/emulated/0/ParentFolder/Report/" + "Idproof" + reg_num + ".PDF");
//                    //File fdelete = new File(root + "/images/media/2918");
//                    //File fdelete = new File(uri.getPath());
//                    if (fid.exists()) {
//                        if (fid.delete()) {
//                            //System.out.println("file Deleted :" + uri.getPath());
//                            //Toast.makeText(HomeActivity.this, "Id Proof file Deleted", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //Toast.makeText(HomeActivity.this, "Id Proof file NOT Deleted", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    File fres = new File("/storage/emulated/0/ParentFolder/Report/" + "resedential" + reg_num + ".PDF");
//                    if (fres.exists()) {
//                        if (fres.delete()) {
//                            //System.out.println("file Deleted :" + uri.getPath());
//                            //Toast.makeText(HomeActivity.this, "Residential file Deleted", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //Toast.makeText(HomeActivity.this, "Residential file NOT Deleted", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    File fpassbook = new File("/storage/emulated/0/ParentFolder/Report/" + "passbook" + reg_num + ".PDF");
//                    if (fpassbook.exists()) {
//                        if (fpassbook.delete()) {
//                            //System.out.println("file Deleted :" + uri.getPath());
//                            //Toast.makeText(HomeActivity.this, "Passbook file Deleted", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //Toast.makeText(HomeActivity.this, "Passbook file NOT Deleted", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    File fswamitra = new File("/storage/emulated/0/ParentFolder/Report/" + "swamitwa" + reg_num + ".PDF");
//                    if (fswamitra.exists()) {
//                        if (fswamitra.delete()) {
//                            //System.out.println("file Deleted :" + uri.getPath());
//                           // Toast.makeText(HomeActivity.this, "Swamitra file Deleted", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //Toast.makeText(HomeActivity.this, "Swamitra file NOT Deleted", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    File fswagosna = new File("/storage/emulated/0/ParentFolder/Report/" + "swaGhosna" + reg_num + ".PDF");
//                    if (fswagosna.exists()) {
//                        if (fswagosna.delete()) {
//                            //System.out.println("file Deleted :" + uri.getPath());
//                            //Toast.makeText(HomeActivity.this, "Swagosna file Deleted", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //Toast.makeText(HomeActivity.this, "Swagosna file NOT Deleted", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                }else{
                    Log.e("message", "data is uploaded but not deleted !!");
                }

            } else {
                Toast.makeText(getApplicationContext(), "अपलोडिंग विफल !\n" + result.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
