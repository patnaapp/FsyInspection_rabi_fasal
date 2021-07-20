//package bih.nic.in.fsyinspection.adapter;
//
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import bih.nic.in.sbmurban.Utilities.CommonPref;
//import bih.nic.in.sbmurban.database.DataBaseHelper;
//import bih.nic.in.sbmurban.entity.GetDataListClass;
//import bih.nic.in.sbmurban.entity.ListDataADapter;
//import bih.nic.in.sbmurban.entity.Schemeclass;
//import bih.nic.in.sbmurban.entity.wardList;
//
//public class UrbanListActivity extends AppCompatActivity {
//TextView txtdstname,txtulbname,txtuser,txt_userrole,textversion;
//    Toolbar toolbarurban;
//    RadioGroup radioGroup1;
//    String version ="";
//
//    EditText edtsearch;
//    Spinner spn_ward,Spn_scheme;
//    ArrayAdapter<String> spnwardadapter;
//    ArrayList<wardList> Spnwardlist = new ArrayList<wardList>();
//    ArrayAdapter<String> schemeadapter;
//    ArrayList<Schemeclass> Schemelist = new ArrayList<Schemeclass>();
//    String str_ward="";
//    EditText et_search;
//    ImageView iv_serch;
//   // ImageView search;
//
//    String searchby = "1",screenflag="";
//    ListView listdata;
//    LinearLayout linsearch_lt;
//    ArrayList<GetDataListClass> dataList;
//    ListDataADapter adapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_urban_list);
//        screenflag=getIntent().getExtras().getString("ScreenFlag").trim();
//        toolbarurban=(Toolbar)findViewById(R.id.toolbar);
//          edtsearch=(EditText)findViewById(R.id.edtsearch);
//        radioGroup1=(RadioGroup)findViewById(R.id.radiogrp);
//        listdata=(ListView)findViewById(R.id.listView);
//        //search=(ImageView)toolbarurban.findViewById(R.id.ivCustomserach);
//        txtdstname=(TextView)findViewById(R.id.txtdname);
//        txtulbname=(TextView)findViewById(R.id.txtname);
//        txtuser=(TextView)findViewById(R.id.txtuser);
//        txt_userrole=(TextView)findViewById(R.id.txt_userrole);
//
//        et_search=(EditText) findViewById(R.id.et_search);
//        iv_serch=(ImageView) findViewById(R.id.iv_serch);
//
//        linsearch_lt=(LinearLayout)findViewById(R.id.linrsearch);
//        spn_ward=(Spinner) findViewById(R.id.spn_ward);
//        spn_ward.setFocusable(true);
//        spn_ward.setFocusableInTouchMode(true);
//        spn_ward.requestFocus();
//        txtdstname.setText(CommonPref.getUserDetails(getApplicationContext()).get_DistrictName());
//        txtulbname.setText(CommonPref.getUserDetails(getApplicationContext()).get_ULBName());
//        txtuser.setText(CommonPref.getUserDetails(getApplicationContext()).get_UserID());
//        txt_userrole.setText(CommonPref.getUserDetails(getApplicationContext()).get_UserRole());
//
//        if(screenflag.equalsIgnoreCase("iihl"))
//        {
//            BindListData();
//            loadwardSpinnerData("iihl");
//        }
//       else if(screenflag.equalsIgnoreCase("constructed"))
//        {
//            BindListDataconstructed();
//            loadwardSpinnerData("constructed");
//        }
//       // loadschemeSpinnerDataSpinnerData();
//        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//               switch (checkedId) {
//                    case R.id.radioapplctnId:
//                        edtsearch.setText("");
//                        searchby="1";
//                        edtsearch.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                if(edtsearch.getText().toString().trim().length()> 0){
//                                  Serachdata(edtsearch.getText().toString().trim(),searchby);
//                                }
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable s) {
//
//                            }
//
//
//                        });
//                        break;
//                    case R.id.radioaplicantId:
//                        searchby="2";
//                        edtsearch.setText("");
//                        if(edtsearch.getText().toString().trim().length()> 0){
//                            Serachdata(edtsearch.getText().toString().trim(),searchby);
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//
//
////        search.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                linsearch_lt.setVisibility(View.VISIBLE);
////
////            }
////        });
//        spn_ward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long id) {
//                wardList ward = new wardList();
//                if (arg2 != 0) {
//                    ward = Spnwardlist.get(arg2 - 1);
//                    str_ward = ward.get_wardID().trim();
//                    if(screenflag.equalsIgnoreCase("iihl")) {
//                        BindListData();
//                    }
//                    else if(screenflag.equalsIgnoreCase("constructed"))
//                    {
//                        BindListDataconstructed();
//                    }
//                }
//                else{
//                    str_ward="";
//                    if(screenflag.equalsIgnoreCase("iihl")) {
//                        BindListData();
//                    }
//                    else if(screenflag.equalsIgnoreCase("constructed"))
//                    {
//                        BindListDataconstructed();
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                str_ward="";
//            }
//        });
//
//        iv_serch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(et_search.getText().toString().trim().length()>0)
//                {
//                    if(screenflag.equalsIgnoreCase("iihl")) {
//                        //BindListData();
//                        getDataForThisSearchStr(et_search.getText().toString());
//                    }
//                    else if(screenflag.equalsIgnoreCase("constructed"))
//                    {
//                        getDataForThisSearchStr_constructed(et_search.getText().toString());
//                        //BindListDataconstructed();
//                    }
//                }
//                else
//                {
//                    Toast.makeText(UrbanListActivity.this, "Please enter App id or Name", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//
//
//        listdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id)
//            {
//                Intent i=new Intent(getApplicationContext(),EntryDetail.class);
//                i.putExtra("AppId",dataList.get(position).get_ApplicationId());
//                i.putExtra("WardId",str_ward);
//                i.putExtra("screenflag",screenflag);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
//        try {
//            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//            textversion=(TextView)findViewById(R.id.textversion);
//            //txtversion.setText(	"Version :" +" "+ version + " ( " + imei + " )");
//            textversion.setText(	"Version" +" ("+ version +")");
//        } catch (PackageManager.NameNotFoundException e) {e.getMessage();}
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(screenflag.equalsIgnoreCase("iihl")) {
//            BindListData();
//        }
//        else if(screenflag.equalsIgnoreCase("constructed"))
//        {
//            BindListDataconstructed();
//        }
//    }
//
//    public void loadwardSpinnerData(String ConsWard){
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
//        //Spnwardlist = dataBaseHelper.getwardList();
//
//        String UlbCode = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UlbCode", "");
//
//        Spnwardlist = dataBaseHelper.getwardListOfUlbCode(UlbCode);
//        String[] divNameArray = new String[Spnwardlist.size() + 1];
//        divNameArray[0] = "-All ward-";
//        int i = 1;
////        for (wardList apl : Spnwardlist) {
////            divNameArray[i] = apl.get_wardName();
////            i++;
////        }
//
//        for (wardList apl : Spnwardlist) {
//            String x=apl.get_wardID();
//            long wrdcount= dataBaseHelper.getwardcount(x,ConsWard,CommonPref.getUserDetails(UrbanListActivity.this).get_UserID());
//            if(wrdcount>0) {
//                divNameArray[i] = apl.get_wardName() +"  "+"(" +wrdcount+")";
//            }
//            else{
//                divNameArray[i] = apl.get_wardName();
//            }
//            i++;
//        }
//
//        spnwardadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
//        spnwardadapter.setDropDownViewResource(R.layout.dropdownlist);
//        spn_ward.setAdapter(spnwardadapter);
//    }
//public void getDataForThisSearchStr(String searchstr)
//{
////
//    if(str_ward.equalsIgnoreCase(""))
//    {
//        str_ward="0";
//    }
//    DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
//    try {
//
//        dataList = new ArrayList<GetDataListClass>();
//        List<GetDataListClass> data = helper.getSearcheDataList(searchstr,str_ward);
//        for (GetDataListClass an : data) {
//            dataList.add(an);
//        }
//        if (dataList.size() > 0) {
//            adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//            listdata.setAdapter(adapter);
//        }
//        else {
//            adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//            listdata.setAdapter(adapter);
//            Toast.makeText(UrbanListActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
//        }
//        adapter.notifyDataSetChanged();
//    } catch (Exception ex)
//    {
//        Toast.makeText(UrbanListActivity.this, "Loading Failed",
//                Toast.LENGTH_SHORT).show();
//    }
//}
//
////getDataForThisSearchStr_constructed
//
//    public void getDataForThisSearchStr_constructed(String searchstr)
//    {
//        if(str_ward.equalsIgnoreCase(""))
//        {
//            str_ward="0";
//        }
////
//        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
//        try {
//
//            dataList = new ArrayList<GetDataListClass>();
//            List<GetDataListClass> data = helper.getSearcheDataList_constructed(searchstr,str_ward);
//            for (GetDataListClass an : data) {
//                dataList.add(an);
//            }
//            if (dataList.size() > 0) {
//                adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//                listdata.setAdapter(adapter);
//            }
//            else {
//                adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//                listdata.setAdapter(adapter);
//                Toast.makeText(UrbanListActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
//            }
//            adapter.notifyDataSetChanged();
//        } catch (Exception ex)
//        {
//            Toast.makeText(UrbanListActivity.this, "Loading Failed",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void BindListData() {
//        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
//        try {
//
//            dataList = new ArrayList<GetDataListClass>();
//            List<GetDataListClass> data = helper.getProgressList(str_ward,CommonPref.getUserDetails(UrbanListActivity.this).get_UserID());
//            for (GetDataListClass an : data) {
//                dataList.add(an);
//            }
//            if (dataList.size() > 0) {
//                adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//                listdata.setAdapter(adapter);
//            }
//            else {
//                adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//                listdata.setAdapter(adapter);
//                Toast.makeText(UrbanListActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
//            }
//            adapter.notifyDataSetChanged();
//        } catch (Exception ex)
//        {
//            Toast.makeText(UrbanListActivity.this, "Loading Failed",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void BindListDataconstructed() {
//        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
//        try {
//             dataList = new ArrayList<GetDataListClass>();
//             List<GetDataListClass> data = helper.getProgressListconstructed(str_ward,CommonPref.getUserDetails(UrbanListActivity.this).get_UserID());
//             for (GetDataListClass an : data) {
//                dataList.add(an);
//             }
//            if (dataList.size() > 0) {
//                adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//                listdata.setAdapter(adapter);
//            }
//            else {
//                adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//                listdata.setAdapter(adapter);
//                Toast.makeText(UrbanListActivity.this, "No Data Found!",
//                 Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (Exception ex)
//        {
//            Toast.makeText(UrbanListActivity.this, "Loading Failed",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//public void Serachdata(String searchtext, String serachby)
//{
//    try {
//    DataBaseHelper dbhelper=new DataBaseHelper(UrbanListActivity.this);
//    dataList = new ArrayList<GetDataListClass>();
//
//    List<GetDataListClass> data = dbhelper.getdatasearch(searchtext,serachby,screenflag);
//    for (GetDataListClass an : data) {
//        dataList.add(an);
//    }
//    if (dataList.size() > 0) {
//        adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//        listdata.setAdapter(adapter);
//    }
//    else {
//        adapter = new ListDataADapter(UrbanListActivity.this, R.layout.listdesign, dataList,screenflag);
//        listdata.setAdapter(adapter);
//        Toast.makeText(UrbanListActivity.this, "No Data Found!",  Toast.LENGTH_SHORT).show();
//    }
//
//}
//    catch (Exception ex)
//    {
//        Toast.makeText(UrbanListActivity.this, "Loading Failed",
//                Toast.LENGTH_SHORT).show();
//    }
//}
//}
