package bih.nic.in.raviinspection.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.database.DataBaseHelper;
import bih.nic.in.raviinspection.database.WebServiceHelper;
import bih.nic.in.raviinspection.entity.Block;
import bih.nic.in.raviinspection.entity.District;
import bih.nic.in.raviinspection.entity.FarmerDetails;
import bih.nic.in.raviinspection.entity.Panchayat;
import bih.nic.in.raviinspection.utilities.CommonPref;
import bih.nic.in.raviinspection.utilities.Utiilties;

public class HomeActivity extends AppCompatActivity {
    ImageView menu_inflater;
    LinearLayout ll_sync,ll_inspection,ll_upload;
    Dialog dialogselectdata;
    String str_panchayat="",str_pachayat_name="";
    Spinner spn_dialogward,Spn_dist,Spn_block,Spn_crop_type;
    Panchayat panchayat;
    TextView pendingTask,userName,distName;
    int indexposition = 0;
    DataBaseHelper localDBHelper;
    ArrayAdapter<String> panchayatadapter;
    ArrayList<Panchayat> Panchayatlist = new ArrayList<Panchayat>();
    Button dialog_btnOk,btn_ok_ok;
    private ViewFlipper mViewFlipper;
    DataBaseHelper dataBaseHelper;
    long count;
    TextView tv_version;
    ArrayList<District> DistrictList = new ArrayList<District>();
    ArrayList<Block> BlockList = new ArrayList<Block>();
    String version="";
    SQLiteDatabase db;
    public static String _vardistID="",_vardistName="";
    public static String _varblockID="",_varblockName="";
    ArrayAdapter<String> distadapter;
    ArrayAdapter<String> blockadapter;
    String DistCode="",BlockCode="",Distname="",Blockname="",choosenBlock="",panchayatName="",cropName="";
    String arr_cropType[] = {"-फसल का प्रकार चयन करे-", "रबी"};
    //String arr_cropType[] = {"Ravi"};
    String crop_Type_Name="",crop_Type_Id="";
    ArrayAdapter crop_Type;
    TextView tv_block,tv_panchayat,tv_croptype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dataBaseHelper= new DataBaseHelper(this);
        dataBaseHelper = new DataBaseHelper(HomeActivity.this);
        _vardistID=CommonPref.getUserDetails(HomeActivity.this).getDistCode();
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
        ModifyTable_New();
        ModifyTable_NewFarmer();


        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);

        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(1000);
        mViewFlipper.startFlipping();

        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out));
        mViewFlipper.showNext();

        menu_inflater = (ImageView) findViewById(R.id.menu_inflater);
        ll_sync = (LinearLayout) findViewById(R.id.ll_sync);
        ll_inspection = (LinearLayout) findViewById(R.id.ll_inspection);
        ll_upload = (LinearLayout) findViewById(R.id.ll_upload);
        pendingTask = (TextView) findViewById(R.id.pendingTask);
        userName = (TextView) findViewById(R.id.userName);
        distName = (TextView) findViewById(R.id.distName);


        userName.setText(CommonPref.getUserDetails(HomeActivity.this).getUserName());
        distName.setText(CommonPref.getUserDetails(HomeActivity.this).getDistName());

        DistCode=CommonPref.getUserDetails(HomeActivity.this).getDistCode();
        Distname=CommonPref.getUserDetails(HomeActivity.this).getDistName();
        BlockCode=CommonPref.getUserDetails(HomeActivity.this).getBlockCode();
        Blockname=CommonPref.getUserDetails(HomeActivity.this).getBlockName();





        ll_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                chk_msg("डाटा और इंटरनेट की स्पीड के अनुसार आपको सिंक्रोनाइजेशन में 1-3 मिनट का समय लग सकता है !");


            }
        });

        ll_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ExistingEntry.class);
                startActivity(i);
            }
        });
        ll_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (Utiilties.isOnline(HomeActivity.this)) {

                    android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(
                            HomeActivity.this);
                    ab.setTitle("अपलोड!");
                    ab.setIcon(R.drawable.iconsuploadd);
                    ab.setMessage("क्या आप सर्वर पर लंबित सभी अपलोड करना चाहते हैं ?");
                    ab.setNegativeButton("नहीं", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    ab.setPositiveButton("हाँ", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            localDBHelper=new DataBaseHelper(HomeActivity.this);

                            if (localDBHelper.getUploadCommunityCount() > 0) {
                                List<FarmerDetails> locDatas = localDBHelper.getAllDataListCommunityData();
                                // Log.d("Datainsecondtable",""+locDatas.size());
                                if(locDatas!=null)
                                {
                                    if (locDatas.size()>0)
                                    {
                                        for (FarmerDetails cn : locDatas) {
                                            new uploadingLocData(cn).execute();
                                        }
                                    }

                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "कोई रिकॉर्ड नहीं मिला",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();

                } else

                {
                    Toast.makeText(HomeActivity.this, " कोई इंटरनेट कनेक्शन नहीं ! \n कृपया अपनी इंटरनेट कनेक्टिविटी की जांच करें.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });






        menu_inflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(HomeActivity.this, menu_inflater);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        //noinspection SimplifiableIfStatement
                        if (id == R.id.action_logout) {

                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID","").commit();
                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();


                            return true;
//
                        }
                       /* if (id == R.id.action_feedbaupwck) {


                            Intent intent=new Intent(getApplicationContext(),Feedbackform.class);
                            startActivity(intent);
                            finish();


                            return true;
//
                        }*/
                        return true;
                    }
                });

                popup.show();
            }
        });
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tv_version = (TextView) findViewById(R.id.tv_version);
            tv_version.setText("ऐप का वर्जन : " + version);

        } catch (PackageManager.NameNotFoundException e) {

        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    public void ModifyTable() {
        if (isColumnExists("InsertFarmer", "RashanCardNumber" ) == false) {
            AlterTable("InsertFarmer","RashanCardNumber");
        }
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
    public void ModifyTable_New() {
        if (isColumnExists1("InsertFarmer", "ConsumerNumberIsPresent" ) == false) {
            AlterTable("InsertFarmer","ConsumerNumberIsPresent");
        }
    }
    public void ModifyTable_NewFarmer() {
        if (isColumnExists1("FarmerDetails", "ConsumberNumberPresent" ) == false) {
            AlterTable("FarmerDetails","ConsumberNumberPresent");
        }
    }
    public void ModifyTable_New1() {
        if (isColumnExists("InsertFarmer", "RashanCardNumber" ) == false) {
            AlterTable("InsertFarmer","RashanCardNumber");
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
    public boolean isColumnExists1 (String table, String column) {
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

    public void ShowPanchayatDialogue() {
        dialogselectdata = new Dialog(HomeActivity.this);
        dialogselectdata.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogselectdata.setContentView(R.layout.selectvaluedialog);
        tv_block=(TextView) dialogselectdata.findViewById(R.id.tv_block) ;
        tv_panchayat=(TextView) dialogselectdata.findViewById(R.id.tv_panchayat) ;
        tv_croptype=(TextView) dialogselectdata.findViewById(R.id.tv_croptype) ;
        dialog_btnOk=(Button)dialogselectdata.findViewById(R.id.btn_ok) ;
        btn_ok_ok=(Button)dialogselectdata.findViewById(R.id.btn_ok_ok) ;
        Spn_dist=(Spinner)dialogselectdata.findViewById(R.id.Spn_dist);
        Spn_block=(Spinner)dialogselectdata.findViewById(R.id.Spn_block);
        Spn_crop_type=(Spinner)dialogselectdata.findViewById(R.id.Spn_crop_type);
        spn_dialogward=(Spinner)dialogselectdata.findViewById(R.id.Spn_ward);

        crop_Type = new ArrayAdapter(this, R.layout.dropdownlist, arr_cropType);
        Spn_crop_type.setAdapter(crop_Type);
        cropName=PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("ChoosenCropTypeName", "");
        if(!cropName.equalsIgnoreCase("")){
            Spn_crop_type.setSelection(((ArrayAdapter<String>) Spn_crop_type.getAdapter()).getPosition(cropName));
        }

        // if((DistCode.equalsIgnoreCase(""))||(BlockCode.equalsIgnoreCase(""))){
        loadDistrictSpinnerData();
        loadBlockSpinnerData();
        if(!((DistCode.equalsIgnoreCase(""))||(BlockCode.equalsIgnoreCase("")))) {
            //ReloadWardData();
            setPanchayatSpinnerData();
        }

        // }
//        if(!DistCode.equalsIgnoreCase("")){
//            if(!BlockCode.equalsIgnoreCase("")){
//                ReloadWardData();
//            }
//        }


/*
        final DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        // Wardlist = dataBaseHelper.getwardList();


        // UlbCode=CommonPref.getUserDetails(getApplicationContext()).get_ULBCode();
        Panchayatlist = dataBaseHelper.getPanchayatList(CommonPref.getUserDetails(getApplicationContext()).getUserID());
        String[] divNameArray = new String[Panchayatlist.size() + 1];
        divNameArray[0] = "-पंचायत चयन करे-";
        int i = 1;
        for (Panchayat apl : Panchayatlist) {
            String x=apl.getPanchayatCode();
            long wrdcount= dataBaseHelper.getpanchayatcount(x, CommonPref.getUserDetails(HomeActivity.this).getUserID());
            if(wrdcount>0) {
                divNameArray[i] = apl.getPanchayatName() +"  "+"(" +wrdcount+")";

            }
            else{
                divNameArray[i] = apl.getPanchayatName();
                Log.e("PCOde",x);
                Log.e("wrdcount",String.valueOf(wrdcount));
            }
            i++;
        }

        panchayatadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
        panchayatadapter.setDropDownViewResource(R.layout.dropdownlist);
        spn_dialogward.setAdapter(panchayatadapter);
        if (indexposition != 0) {
            spn_dialogward.setSelection(indexposition);

        }*/
        Spn_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    District district = DistrictList.get(arg2 - 1);
                    _vardistID = district.getDist_Code();
                    _vardistName = district.getDist_Name();
                    loadBlockSpinnerData();

                }else {
                    _vardistID = "";
                    _vardistName = "";


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        Spn_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {
                    spn_dialogward.setSelection(0);

                    Block block = BlockList.get(arg2 - 1);
                    _varblockID = block.getBlockCode();
                    _varblockName = block.getBlockName();
                    //new DwnldPanchayat(CommonPref.getUserDetails(getApplicationContext()).getUserID()).execute();
                    // ReloadWardData();
                    setPanchayatSpinnerData();

                }else {
                    _varblockID = "";
                    _varblockName = "";


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spn_dialogward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long id)
            {
                DataBaseHelper db=new DataBaseHelper(getApplicationContext());
                Panchayat panchayat=new Panchayat();
                if (arg2 != 0) {
                    indexposition = arg2;

                    panchayat = Panchayatlist.get(arg2 - 1);
                    str_panchayat=panchayat.getPanchayatCode().trim();
                    str_pachayat_name=panchayat.getPanchayatName().trim();
                    Log.e("App Pan",str_panchayat);
//                }else {
//                    panchayat = Panchayatlist.get(arg2 - 1);
//                    str_panchayat=panchayat.getPanchayatCode().trim();
//                    Log.e("App Pan",str_panchayat);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spn_crop_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long id)
            {
                if (arg2 > 0) {
                    crop_Type_Name = arr_cropType[arg2].toString();
                    if (crop_Type_Name.equals("रबी")) {
                        crop_Type_Id = "2";
//                    } else if (crop_Type_Name.equals("Ravi")) {
//                        crop_Type_Id = "2";
//
                    }
                }else {
                    crop_Type_Name= "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        dialog_btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!_varblockID.equalsIgnoreCase("")) {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ChoosenBlock", _varblockID).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ChoosenBlockName", _varblockName).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ChoosenPanchName", str_pachayat_name).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ChoosenDistName", _vardistName).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ChoosenCropTypeName", crop_Type_Name).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ChoosenCropTypeId", crop_Type_Id).commit();


                }else {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ChoosenBlock", BlockCode).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ChoosenDistName", Distname).commit();

                }

                // panchayat= dataBaseHelper.getPanchayat();
                if (validateData()) {
                    new SynchronizeData(str_panchayat, crop_Type_Id).execute();
                    Log.e("App Pan", str_panchayat);
                }



//                if(!str_panchayat.equalsIgnoreCase("0") || !str_panchayat.equalsIgnoreCase("-1")) {
//
//                   new SynchronizeData(str_panchayat).execute();
//                }

            }

        });
        btn_ok_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogselectdata.dismiss();
            }
        });

        if (dialogselectdata.isShowing()) dialogselectdata.dismiss();
        dialogselectdata.show();
        //panchayatadapter.setNotifyOnChange(true);
    }


    //    private void showPending() {
//        int count = localDBHelper.getNumberOfPendingData(CommonPref.getUserDetails(this).getUserID());
//        if (count > 0) {
//            if (count >= 10) {
//                pendingTask.setText(String.valueOf(count));
//            } else {
//                pendingTask.setText(String.valueOf(count));
//            }
//        } else pendingTask.setText("0");
//
//    }
//    public void ReloadWardData(){
//
//
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
//        Panchayatlist.clear();
//
//        // Wardlist = dataBaseHelper.getwardList();
//
//        String UlbCode = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UlbCode", "");
//        // UlbCode=CommonPref.getUserDetails(getApplicationContext()).get_ULBCode();
//
//       if((!(DistCode.equalsIgnoreCase("")))&& (!(BlockCode.equalsIgnoreCase("")))) {
//           Panchayatlist = dataBaseHelper.getPanchayatList(CommonPref.getUserDetails(getApplicationContext()).getUserID().toLowerCase(),BlockCode);
//       }
//       else{
//           Panchayatlist = dataBaseHelper.getPanchayatListwithblockid(CommonPref.getUserDetails(HomeActivity.this).getUserID(),_varblockID);
//       }
//           if(Panchayatlist.size()<=0){
//               new DwnldPanchayat(CommonPref.getUserDetails(getApplicationContext()).getUserID()).execute();
//           }else {
////       }else {
////           Panchayatlist = dataBaseHelper.getPanchayatListwithblockid(CommonPref.getUserDetails(HomeActivity.this).getUserID(),_varblockID);
////       }
//               String[] divNameArray = new String[Panchayatlist.size() + 1];
//
//               divNameArray[0] = "-पंचायत चयन करे-";
//               int i = 1;
//               for (Panchayat apl : Panchayatlist) {
//                   String x = apl.getPanchayatCode();
//                   long wrdcount = dataBaseHelper.getpanchayatcount(x, CommonPref.getUserDetails(HomeActivity.this).getUserID());
//                   if (wrdcount > 0) {
//                       divNameArray[i] = apl.getPanchayatName() + "  " + "(" + wrdcount + ")";
//                   } else {
//                       divNameArray[i] = apl.getPanchayatName();
//                   }
//                   i++;
//               }
//
//               panchayatadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
//               panchayatadapter.setDropDownViewResource(R.layout.dropdownlist);
//               spn_dialogward.setAdapter(panchayatadapter);
//               if (indexposition != 0) {
//                   spn_dialogward.setSelection(indexposition);
//
//               }
//               panchayatadapter.notifyDataSetChanged();
//           }
//    }
    public void setPanchayatSpinnerData() {
        DataBaseHelper placeData = new DataBaseHelper(HomeActivity.this);

        if((!(DistCode.equalsIgnoreCase("")))&& (!(BlockCode.equalsIgnoreCase("")))) {
            Panchayatlist = dataBaseHelper.getPanchayatList(CommonPref.getUserDetails(getApplicationContext()).getUserID().toLowerCase(),BlockCode);
        }
        else{
            Panchayatlist = dataBaseHelper.getPanchayatListwithblockid(CommonPref.getUserDetails(HomeActivity.this).getUserID(),_varblockID);
        }
        if(Panchayatlist.size()<=0)
        {
            new DwnldPanchayat(CommonPref.getUserDetails(getApplicationContext()).getUserID()).execute();
        }
        else {
            loadPanchayatSpinnerData(Panchayatlist);//loadSHGSpinnerData(SHGList);
        }


    }
    private void loadPanchayatSpinnerData(ArrayList<Panchayat> pList) {
        ArrayList<String> shgStringList = new ArrayList<String>();
        int pos = 0;
        shgStringList.add("-Select-");
        if(pList != null) {
            for (int i = 0; i < pList.size(); i++) {
                // shgStringList.add(pList.get(i).getPanchayatName());
                String x = pList.get(i).getPanchayatCode();
                long wrdcount = dataBaseHelper.getpanchayatcount(x, CommonPref.getUserDetails(HomeActivity.this).getUserID());
                if (wrdcount > 0) {
                    shgStringList.add(pList.get(i).getPanchayatName() + "  " + "(" + wrdcount + ")");
                } else {
                    shgStringList.add( pList.get(i).getPanchayatName());
                }
            }
        }
        panchayatadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shgStringList);

        if (spn_dialogward != null) {
            spn_dialogward.setAdapter(panchayatadapter);
            spn_dialogward.setSelection(pos);
        }

        panchayatName=PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("ChoosenPanchName", "");
        if(!panchayatName.equalsIgnoreCase("")){
            spn_dialogward.setSelection(((ArrayAdapter<String>) spn_dialogward.getAdapter()).getPosition(panchayatName));
        }
    }
    public class SynchronizeData extends AsyncTask<String, Void, ArrayList<FarmerDetails>> {

        String Panchayatid = "";
        String crop_id = "";

        public SynchronizeData(String panchayatcode,String Crop_id) {
            this.Panchayatid = panchayatcode;
            this.crop_id = Crop_id;
            Log.e("PCode",Panchayatid);

        }

        private final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("डेटा लोड हो रहा है.\nकृपया प्रतीक्षा करें...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<FarmerDetails> doInBackground(String... params) {
            String UserRole="";
            UserRole=CommonPref.getUserDetails(HomeActivity.this).getRole();


            ArrayList<FarmerDetails> res1 = WebServiceHelper.GetDataLIst1(Panchayatid,crop_id,CommonPref.getUserDetails(HomeActivity.this).getUserID());
            return res1;

        }

        @Override
        protected void onPostExecute(final ArrayList<FarmerDetails> result) {
            if(result!=null)
            {
                if(!result.isEmpty())
                {
                    if (this.dialog.isShowing()) {
                        this.dialog.dismiss();
                        DataBaseHelper placeData = new DataBaseHelper(HomeActivity.this);
                        placeData.Datainsert(result,str_panchayat,CommonPref.getUserDetails(getApplicationContext()).getUserID());
                        //ReloadWardData();
                        //loadPanchayatSpinnerData(result);
                        //ReloadWardData();
                        //dialogselectdata.dismiss();
//                        if(localDBHelper.getPendingUploadCount()>0)
//                        {
//                            ll_upload.setText("UPLOAD  COMMENCED PHASE       "+localDBHelper.getPendingUploadCount());
//                        }else
//                            ll_upload.setText("UPLOAD COMMENCED PHASE");

                        Toast.makeText(HomeActivity.this, "सिंक्रनाइज़ेशन सफलतापूर्वक", Toast.LENGTH_LONG).show();

                    }
                }
                else

                {
                    indexposition = 0;

                    Toast.makeText(HomeActivity.this, "कोई रिकॉर्ड नहीं मिला", Toast.LENGTH_SHORT).show();
                    this.dialog.dismiss();
                }
            }
            else
            {

                Toast.makeText(HomeActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                this.dialog.dismiss();
            }

        }
    }

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
            pd1 = new ProgressDialog(HomeActivity.this);
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
                //  Toast.makeText(getApplicationContext(), "डेटा सफलतापूर्वक अपलोड किया गया है !", Toast.LENGTH_SHORT).show();
                boolean isDel=localDBHelper.deleterowCommunity1(farmerDetails.getRegistrationNO());
                if (isDel) {
                    count = dataBaseHelper.getPendingUploadCount(CommonPref.getUserDetails(HomeActivity.this).getUserID());
                    pendingTask.setText("" + count);
                    boolean is_del=localDBHelper.deleterowPhaseCommunity1Actual(farmerDetails.getRegistrationNO());
                    if(is_del)
                    {
                        reg_num=farmerDetails.getRegistrationNO().toString().trim();
                        // Toast.makeText(HomeActivity.this, "वास्तविक डेटा सूची आईडी से हटाया गया:" + reg_num, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(HomeActivity.this, "वास्तविक डेटा सूची आईडी से निकालने में विफल रहा:" + reg_num, Toast.LENGTH_SHORT).show();
                    }
                    showCustomDialoguploadsuccess();





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
                }

                else{
                    Log.e("message", "data is uploaded but not deleted !!");
                }

            }
            else {
                Toast.makeText(getApplicationContext(), "अपलोडिंग विफल !\n" + result.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        count = dataBaseHelper.getPendingUploadCount(CommonPref.getUserDetails(HomeActivity.this).getUserID());
        pendingTask.setText("" + count);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showCustomDialog();
            //backButtonHandler();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public void backButtonHandler() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("लीव एप्लीकेशन?");
        // Setting Dialog Message
        alertDialog.setMessage("क्या आप वाकई एप्लीकेशन से बाहर आना चाहते हैं?");
        // Setting Icon to Dialog
        // alertDialog.setIcon(R.drawable.bulb_1);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("नहीं", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
    public void loadDistrictSpinnerData() {

        DistrictList = dataBaseHelper.getDistLocal();
        String[] typeNameArray = new String[DistrictList.size() + 1];
        typeNameArray[0] = "-जिला चयन करे-";
        int i = 1;
        for (District type : DistrictList) {
            typeNameArray[i] = type.getDist_Name();
            i++;
        }
        distadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, typeNameArray);
        distadapter.setDropDownViewResource(R.layout.dropdownlist);
        Spn_dist.setAdapter(distadapter);
        int setID=0;
        String choosendist="";
        choosendist=PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("ChoosenDistName", "");
        for ( i = 0; i < DistrictList.size(); i++) {
            if (DistrictList.get(i).getDist_Code().equalsIgnoreCase(DistCode)) {
                setID = i;
            }
            // if(setID!=0) {
            if (!Distname.equalsIgnoreCase("")) {
                Spn_dist.setSelection(((ArrayAdapter<String>) Spn_dist.getAdapter()).getPosition(Distname));

                // Spn_dist.setSelection(setID+1);
                Spn_dist.setEnabled(false);
            }else if(!choosendist.equalsIgnoreCase("")){
                Spn_dist.setSelection(((ArrayAdapter<String>) Spn_dist.getAdapter()).getPosition(choosendist));
            }

            //}
        }

    }
    public void loadBlockSpinnerData() {

        BlockList = dataBaseHelper.getblockLocal(_vardistID);
        String[] typeNameArray = new String[BlockList.size() + 1];
        typeNameArray[0] = "-प्रखंड चयन करे-";
        int i = 1;
        for (Block type : BlockList) {
            typeNameArray[i] = type.getBlockName();
            i++;
        }
        blockadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, typeNameArray);
        blockadapter.setDropDownViewResource(R.layout.dropdownlist);
        Spn_block.setAdapter(blockadapter);
        int setID=0;
        for ( i = 0; i < BlockList.size(); i++) {

            if (BlockList.get(i).getBlockCode().equalsIgnoreCase(BlockCode)) {
                setID = i;
            }
            if(setID!=0) {
                Spn_block.setSelection(setID+1);
                Spn_block.setEnabled(false);
            }
        }
        choosenBlock=PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString("ChoosenBlockName", "");
        if(!Blockname.equalsIgnoreCase("")){
            Spn_block.setSelection(((ArrayAdapter<String>) Spn_block.getAdapter()).getPosition(Blockname));
        }else if(!choosenBlock.equalsIgnoreCase("")){
            Spn_block.setSelection(((ArrayAdapter<String>) Spn_block.getAdapter()).getPosition(choosenBlock));
        }

    }
    public class DwnldPanchayat extends AsyncTask<String, Void, ArrayList<Panchayat>>    {
        String Userid = "";
        public DwnldPanchayat(String userid) {
            this.Userid = userid;

        }
        private final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("पंचायत लोड हो रहा है.\nकृपया प्रतीक्षा करें...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Panchayat> doInBackground(String... params) {
            if(DistCode.equalsIgnoreCase("")||BlockCode.equalsIgnoreCase("")){
                ArrayList<Panchayat> res1 = WebServiceHelper.loadPanchayatList(Userid,_varblockID);
                return res1;
            }else {
                ArrayList<Panchayat> res1 = WebServiceHelper.loadPanchayatList(Userid,BlockCode);
                return res1;

            }
        }

        @Override
        protected void onPostExecute(final ArrayList<Panchayat> result) {

            if (this.dialog.isShowing()) {
                if (result != null) {
                    if (result.size() > 0) {
                        DataBaseHelper placeData = new DataBaseHelper(HomeActivity.this);
                        placeData.insertPanchayat(result);
                        Panchayatlist=result;
                        //ReloadWardData();
                        loadPanchayatSpinnerData(result);
                    } else {
                        AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
                        //ab.setIcon(R.drawable.logo);
                        ab.setTitle("पंचायत !");
                        ab.setMessage("क्षमा कीजिये,आपने  प्रखंड : " + _varblockName + " का चयन किया है|इसमे पंचायत मैप नहीं है| ");
                        ab.setPositiveButton("[ Ok ]", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Panchayatlist=result;
                                loadPanchayatSpinnerData(result);
                                //ReloadWardData();
                                dialog.dismiss();
                            }
                        });

                        ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                        ab.show();

                    }


                } else {
                    Toast.makeText(HomeActivity.this, "Something Went Wrong For Member Name + Husband Name", Toast.LENGTH_SHORT).show();
                }


//                if(c>0)
//                {
//                    setSHG_VillageSpinnerData(_varpanchayatID,CommonPref.getUserDetails(PhasuLevelActivity.this).getUserID());
//                }
                this.dialog.dismiss();
            }

//            if(result.size()!=0) {
//
//                    DataBaseHelper placeData = new DataBaseHelper(HomeActivity.this);
//                    placeData.insertPanchayat(result);
//                    Panchayatlist=result;
//                    ReloadWardData();
//
//
//            }else {
//                Panchayatlist=result;
//                ReloadWardData();
//
//
//            }
//             if (this.dialog.isShowing()) {
//                 dialog.dismiss();
//             }

        }

    }



    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);
        Button buttonok = (Button)dialogView.findViewById(R.id.buttonOk);
        Button buttcancela = (Button)dialogView.findViewById(R.id.buttoncancel);
        //Now we need an AlertDialog.Builder object

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        Dialog d=new Dialog(this);
        d.setCanceledOnTouchOutside(false);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog.dismiss();
                finish();
                // moveTaskToBack(true);
//                Intent a = new Intent(Intent.ACTION_MAIN);
//                a.addCategory(Intent.CATEGORY_HOME);
//                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(a);
            }
        });

        buttcancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                alertDialog.setCancelable(false);
            }
        });
        alertDialog.show();
    }
    private void showCustomDialoguploadsuccess() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialoguploadsucess, viewGroup, false);
        Button buttonnook = (Button)dialogView.findViewById(R.id.buttonOkk);
        Button buttonyes = (Button)dialogView.findViewById(R.id.buttonyes);
        //Now we need an AlertDialog.Builder object

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        Dialog d=new Dialog(this);
        d.setCanceledOnTouchOutside(false);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        buttonnook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                // finish();
            }
        });


        alertDialog.show();
    }

    public void chk_msg(String msg) {
        // final String wantToUpdate;
        AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.rabifasal);
        ab.setTitle("फसल सहायता निरीक्षण");
        ab.setMessage(msg);
        Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                final Boolean value = isNetworkConnected();


                //call method for synce record
                if (value.equals(true)) {

                    ShowPanchayatDialogue();
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });

        ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }
    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

//        if (_varblockName.equals("")) {
//            tv_block.setError(getString(R.string.fieldRequired));
//            focusView = tv_block;
//            validate = false;
//        }
        if (str_pachayat_name.equals("")) {
            tv_panchayat.setError(getString(R.string.fieldRequired));
            focusView = tv_panchayat;
            validate = false;
        }
        if (crop_Type_Name.equals("")) {
            tv_croptype.setError(getString(R.string.fieldRequired));
            focusView = tv_croptype;
            validate = false;
        }


        if (!validate) focusView.requestFocus();

        return validate;
    }

}
