package bih.nic.in.raviinspection.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.constraint.BuildConfig;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.database.DataBaseHelper;
import bih.nic.in.raviinspection.database.WebServiceHelper;
import bih.nic.in.raviinspection.entity.Checklist;
import bih.nic.in.raviinspection.entity.DecimalDigitsInputFilter;
import bih.nic.in.raviinspection.entity.FarmerDetails;
import bih.nic.in.raviinspection.entity.Panchayat;
import bih.nic.in.raviinspection.entity.Remarks;
import bih.nic.in.raviinspection.utilities.GlobalVariables;

public class EntryDetail extends AppCompatActivity {
    EditText et_aawedak_name,et_father_husband_name,et_mobile_number,et_aadhar_number,
            et_aadhar_name,et_branch_name,et_ifsc_code,et_account_number,et_khata_number,
            et_khesara_number,et_khestra_rakwa,et_khestra_makka,et_dhan_makka;
    EditText et_gehu_rayti,et_makka_rayti,et_chana_rayti,et_masur_rayti,et_arahar_rayti,et_rae_rayti,et_ekh_rayti,et_pyaj_rayti,et_aloo_rayti,
            et_gehu_gair_rayti,et_makka_gair_rayti,et_chana_gair_rayti,et_masur_gair_rayti,et_arahar_gair_rayti,et_rae_gair_rayti,et_ekh_gair_rayti,et_pyaj_gair_rayti,et_aloo_gair_rayti;
    EditText edt_gehu_rayti,edt_makka_rayti,edt_chana_rayti,edt_masur_rayti,edt_arahar_rayti,edt_rae_rayti,edt_ekh_rayti,edt_pyaj_rayti,edt_aloo_rayti,
            edt_gehu_gair_rayti,edt_makka_gair_rayti,edt_chana_gair_rayti,edt_masur_gair_rayti,edt_arahar_gair_rayti,edt_rae_gair_rayti,edt_ekh_gair_rayti,edt_pyaj_gair_rayti,edt_aloo_gair_rayti;
    EditText et_soyabin, et_fieldwheat,et_field_makka,et_masoor,et_arhar,et_sugarcane,et_potato,et_raisarso,et_onion,et_chana,et_electricconsumernumber;
    TextView txtconsumernumber;
    TextView tv_remarks;
    Button btn_save;
    String Chk_agree="N";
    LinearLayout ll_raiyati_all,ll_gair_raiyati_all,ll_raiyati_all_edt,ll_gair_raiyati_all_edt;
    LinearLayout lin_gehu_rayti,lin_makka_rayti,lin_chana_rayti,lin_masur_rayti,lin_arahar_rayti,lin_Rae_raytti,lin_ekh_rayti,lin_pyaj_rayti,lin_aloo_rayti;
    LinearLayout lin_gair_gehu_rayti,lin_gair_makka_rayti,lin_gair_chana_rayti,lin_gair_masur_rayti,lin_gair_arahar_rayti,lin_gair_Rae_raytti,lin_gair_ekh_rayti,lin_gair_pyaj_rayti,lin_gair_aloo_rayti;

    String str_panchayatId="",str_userId="",str_Reg_No="";
    DataBaseHelper dbhelper;
    EditText spn_farmer_nibandhan,spn_dist,spn_block,spn_panchayat,spn_ward,spn_village,spn_type_farmer,et_crop_Type,spn_gender,
            spn_category,spn_isPacs,spn_bankname,spn_wheather,spn_corps,et_house_id,et_summary;
    long applicationIds=0;
    ArrayAdapter aawedakAbsent;
    ArrayAdapter swaghosanaarrayadapter;
    EditText edt_panchayat_name,et_rashan_card_num;
    FarmerDetails farmerDetails;
    Panchayat wardL;
    Button btn_update,btn_reject;
    String Reg_Number="";
    ScrollView scrollview;
    TextView bankpassbook,bhu_swamitwa,swa_gosna;
    TextView identity_card,resedential;
    LinearLayout ll_remarks,house_hold_id,ll_khata_khesara,lin_wheat,lin_makka,lin_masoor,lin_arhar,lin_sugarcane,lin_potato,lin_raisarso,lin_onion,lin_chana,Lin_consumerIsPresent,lin_soyabin,lin_dhan_makka;
    String _acceptOrReject="A",_var_aawedak_present_Id="",_var_aawedak_present_Nm="";
    ImageView img_farmerphoto;
    String isEdit = "";
    String slno;
    boolean edit = false;
    Spinner sp_remarks,sp_aawedak_absent;
    ArrayAdapter<String> remarksadapter;
    public static String _varremarksID = "", _varremarksName = "0";
    Dialog dialogselectdata;
    RadioGroup rg_ConsumwerIsPresent;
    String ConsumwerIsPresent="Y";
    TextView tv_lpc_swagosna;
    CheckBox chk_agree;
    LinearLayout lin_gehu_rayti_edt,lin_makka_rayti_edt,lin_chana_rayti_edt,lin_masur_rayti_edt,lin_arahar_rayti_edt,lin_Rae_raytti_edt,lin_ekh_rayti_edt,lin_pyaj_rayti_edt,lin_aloo_rayti_edt,
            lin_gair_gehu_rayti_edt,lin_gair_makka_rayti_edt,lin_gair_chana_rayti_edt,lin_gair_masur_rayti_edt,lin_gair_arahar_rayti_edt,lin_gair_Rae_raytti_edt,lin_gair_ekh_rayti_edt,lin_gair_pyaj_rayti_edt,lin_gair_aloo_rayti_edt;
    ArrayList<Remarks> RemarksList = new ArrayList<Remarks>();
    String RemarksName="";
    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            Manifest.permission.RECORD_AUDIO,
    };
    public static  File pictureFile;
    Spinner sp_aawedak_present,sp_lpc_related,sp_lpc_aawedan,sp_ghosit_fasal_kheti,sp_aawedan_ghosit_rakwa,sp_aawedak_one_family;
    String farmerTypeNm="";
    ArrayList<Checklist> ChecklistList = new ArrayList<Checklist>();
    ArrayAdapter<String> checklistadapter;
    String checklist;
    String _var_lpc_rltd_chk_Id="",_var_lpc_rltd_chk_Nm="",_var_lpc_awedn_chk_Id="",_var_lpc_awedn_chk_Nm="",_var_ghosit_fasal_khti_Id="",_var_ghosit_fasal_khti_Nm="";
    String _var_aawedan_ghosit_rakwa_Id="",_var_aawedan_ghosit_rakwa_Nm="",_var_aawedak_one_family_Id="",_var_aawedak_one_family_Nm="";
    LinearLayout ll_aawedak_absent,ll_lpc_related,ll_aawedak_one_family,ll_lpc_aawedan,ll_aawedan_ghosit_rakwa,ll_kinke_dwara_Satyapan;
    EditText et_aawedan_ghosit_rakwa,et_aawedan_ghosit_rakwa_2;
    TextView tv_dhan_makka;
    String aawedak_absent[] = {"-चुनें-","पति","पत्नी","पुत्र","पुत्री","माता","पिता","कोई नहीं"};
    String sevikaabsent_Name="",sevikaabsent_Id="";
    String doc_url="";
    byte[] imageData;
    Spinner sp_swaghoshana_patra_sambandhit;
    LinearLayout ll_swaghoshana_sambandhit,ll_swaghosana_sambandhit_nm;
    String swa_ghosana_sambandhit[] = {"-चुनें-","स्व-घोषणा पत्र है एवं किसान सलाहकार के द्वारा हस्ताक्षरित है","स्व-घोषणा पत्र है एवं वार्ड सदस्य के द्वारा हस्ताक्षरित है","स्व-घोषणा पत्र नहीं है"};
    String _var_swaghosana_sambandhit_Id="",_var_swaghosana_sambandhit_Nm="";
    EditText et_swaghossna_signer_nm;
    TextView tv_swaghosana_signer_nm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);
        hideSoftKeyboard();

        str_panchayatId = getIntent().getExtras().getString("WardId").trim();
        str_userId = getIntent().getExtras().getString("User_Id").trim();
        //str_Reg_No = getIntent().getExtras().getString("RegNo").trim();
        str_Reg_No = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("REG_NO", "");

        Log.e("str_Reg_No",str_Reg_No);

        if (str_panchayatId.equalsIgnoreCase("")) {
            getwardId(str_userId);
        }
        dbhelper = new DataBaseHelper(EntryDetail.this);
        applicationIds = getRegNoId(str_Reg_No);

        spn_farmer_nibandhan = (EditText) findViewById(R.id.spn_farmer_nibandhan);
        spn_dist = (EditText) findViewById(R.id.spn_dist);
        spn_block = (EditText) findViewById(R.id.spn_block);
        spn_panchayat = (EditText) findViewById(R.id.spn_panchayat);
        spn_ward = (EditText) findViewById(R.id.spn_ward);
        spn_village = (EditText) findViewById(R.id.spn_village);
        spn_type_farmer = (EditText) findViewById(R.id.spn_farmertype);
        et_crop_Type = (EditText) findViewById(R.id.et_crop_Type);
        spn_gender = (EditText) findViewById(R.id.spn_gender);
        spn_category = (EditText) findViewById(R.id.spn_category);
        spn_isPacs = (EditText) findViewById(R.id.spn_isPacs);
        spn_bankname = (EditText) findViewById(R.id.spn_bankname);
        spn_wheather = (EditText) findViewById(R.id.spn_wheather);
        spn_corps = (EditText) findViewById(R.id.spn_corps);
        btn_save = (Button) findViewById(R.id.btn_save);
        chk_agree = (CheckBox) findViewById(R.id.chk_agree);
//        sp_remarks = (Spinner) findViewById(R.id.sp_remarks);
        // ll_remarks = (LinearLayout) findViewById(R.id.ll_remarks);
        house_hold_id = (LinearLayout) findViewById(R.id.house_hold_id);
        ll_khata_khesara = (LinearLayout) findViewById(R.id.ll_khata_khesara);

        btn_update = (Button) findViewById(R.id.btn_update);
        btn_reject = (Button) findViewById(R.id.btn_reject);

        tv_lpc_swagosna = (TextView) findViewById(R.id.tv_lpc_swagosna);
        identity_card = (TextView) findViewById(R.id.identity_card);
        resedential = (TextView) findViewById(R.id.resedential);
        bankpassbook = (TextView) findViewById(R.id.bankpassbook);
        bhu_swamitwa = (TextView) findViewById(R.id.bhu_swamitwa);
        bhu_swamitwa.setVisibility(View.GONE);
        swa_gosna = (TextView) findViewById(R.id.swa_gosna);
        //  tv_remarks = (TextView) findViewById(R.id.tv_remarks);

        et_aawedak_name = (EditText) findViewById(R.id.et_aawedak_name);
        et_father_husband_name = (EditText) findViewById(R.id.et_father_husband_name);
        et_mobile_number = (EditText) findViewById(R.id.et_mobile_number);
        et_aadhar_number = (EditText) findViewById(R.id.et_aadhar_number);
        et_aadhar_name = (EditText) findViewById(R.id.et_aadhar_name);
        et_branch_name = (EditText) findViewById(R.id.et_branch_name);
        et_ifsc_code = (EditText) findViewById(R.id.et_ifsc_code);
        et_account_number = (EditText) findViewById(R.id.et_account_number);
        et_khata_number = (EditText) findViewById(R.id.et_khata_number);
        et_khesara_number = (EditText) findViewById(R.id.et_khesara_number);
        et_khestra_rakwa = (EditText) findViewById(R.id.et_khestra_rakwa);
        et_khestra_makka = (EditText) findViewById(R.id.et_khestra_makka);
        et_house_id = (EditText) findViewById(R.id.et_house_id);
        // et_summary = (EditText) findViewById(R.id.et_summary);
        et_rashan_card_num = (EditText) findViewById(R.id.et_rashan_card_num);


        // new feature

        sp_aawedak_absent = (Spinner) findViewById(R.id.sp_aawedak_absent);
        //et_fieldwheat = (EditText) findViewById(R.id.et_fieldwheat);
        //et_field_makka = (EditText) findViewById(R.id.et_field_makka);
        // et_masoor = (EditText) findViewById(R.id.et_masoor);
        // et_arhar = (EditText) findViewById(R.id.et_arhar);
        //et_sugarcane = (EditText) findViewById(R.id.et_sugarcane);
        // et_potato = (EditText) findViewById(R.id.et_potato);
        // et_raisarso = (EditText) findViewById(R.id.et_raisarso);
        //et_onion = (EditText) findViewById(R.id.et_onion);
        //et_chana = (EditText) findViewById(R.id.et_chana);
        //et_electricconsumernumber = (EditText) findViewById(R.id.et_electricconsumernumber);
        //lin_wheat = (LinearLayout) findViewById(R.id.lin_wheat);
        //lin_makka = (LinearLayout) findViewById(R.id.lin_makka);
//        lin_masoor = (LinearLayout) findViewById(R.id.lin_masoor);
//        lin_arhar = (LinearLayout) findViewById(R.id.lin_arhar);
//        lin_sugarcane = (LinearLayout) findViewById(R.id.lin_sugarcane);
//        lin_potato = (LinearLayout) findViewById(R.id.lin_potato);
//        lin_raisarso = (LinearLayout) findViewById(R.id.lin_raisarso);
//        lin_onion = (LinearLayout) findViewById(R.id.lin_onion);
//        lin_chana = (LinearLayout) findViewById(R.id.lin_chana);
        ll_raiyati_all = (LinearLayout) findViewById(R.id.ll_raiyati_all);
        ll_raiyati_all_edt = (LinearLayout) findViewById(R.id.ll_raiyati_all_edt);
        ll_kinke_dwara_Satyapan = (LinearLayout) findViewById(R.id.ll_kinke_dwara_Satyapan);
        ll_gair_raiyati_all = (LinearLayout) findViewById(R.id.ll_gair_raiyati_all);
        ll_gair_raiyati_all_edt = (LinearLayout) findViewById(R.id.ll_gair_raiyati_all_edt);

        ll_raiyati_all_edt.setVisibility(View.GONE);
        ll_gair_raiyati_all_edt.setVisibility(View.GONE);
        //ll_kinke_dwara_Satyapan.setVisibility(View.GONE);
        //lin_dhan_makka = (LinearLayout) findViewById(R.id.lin_dhan_makka);
        ll_aawedak_absent = (LinearLayout) findViewById(R.id.ll_aawedak_absent);
        ll_aawedak_absent.setVisibility(View.GONE);
        sp_aawedak_present =  findViewById(R.id.sp_aawedak_present);
        sp_lpc_related =  findViewById(R.id.sp_lpc_related);
        sp_lpc_aawedan =  findViewById(R.id.sp_lpc_aawedan);
        sp_ghosit_fasal_kheti =  findViewById(R.id.sp_ghosit_fasal_kheti);
        sp_aawedan_ghosit_rakwa =  findViewById(R.id.sp_aawedan_ghosit_rakwa);
        sp_aawedak_one_family =  findViewById(R.id.sp_aawedak_one_family);
        ll_lpc_related =  findViewById(R.id.ll_lpc_related);
        ll_aawedak_one_family =  findViewById(R.id.ll_aawedak_one_family);
        ll_lpc_aawedan =  findViewById(R.id.ll_lpc_aawedan);
        et_aawedan_ghosit_rakwa =  findViewById(R.id.et_aawedan_ghosit_rakwa);
        et_aawedan_ghosit_rakwa_2 =  findViewById(R.id.et_aawedan_ghosit_rakwa_2);
        ll_aawedan_ghosit_rakwa =  findViewById(R.id.ll_aawedan_ghosit_rakwa);

        et_gehu_rayti =  findViewById(R.id.et_gehu_rayti);
        et_makka_rayti =  findViewById(R.id.et_makka_rayti);
        et_chana_rayti =  findViewById(R.id.et_chana_rayti);
        et_masur_rayti =  findViewById(R.id.et_masur_rayti);
        et_arahar_rayti =  findViewById(R.id.et_arahar_rayti);
        et_rae_rayti =  findViewById(R.id.et_rae_rayti);
        et_ekh_rayti =  findViewById(R.id.et_ekh_rayti);
        et_pyaj_rayti =  findViewById(R.id.et_pyaj_rayti);
        et_aloo_rayti =  findViewById(R.id.et_aloo_rayti);

        et_gehu_gair_rayti =  findViewById(R.id.et_gehu_gair_rayti);
        et_makka_gair_rayti =  findViewById(R.id.et_makka_gair_rayti);
        et_chana_gair_rayti =  findViewById(R.id.et_chana_gair_rayti);
        et_masur_gair_rayti =  findViewById(R.id.et_masur_gair_rayti);
        et_arahar_gair_rayti =  findViewById(R.id.et_arahar_gair_rayti);
        et_rae_gair_rayti =  findViewById(R.id.et_rae_gair_rayti);
        et_ekh_gair_rayti =  findViewById(R.id.et_ekh_gair_rayti);
        et_pyaj_gair_rayti =  findViewById(R.id.et_pyaj_gair_rayti);
        et_aloo_gair_rayti =  findViewById(R.id.et_aloo_gair_rayti);

        edt_gehu_rayti =  findViewById(R.id.edt_gehu_rayti);
        edt_makka_rayti =  findViewById(R.id.edt_makka_rayti);
        edt_chana_rayti =  findViewById(R.id.edt_chana_rayti);
        edt_masur_rayti =  findViewById(R.id.edt_masur_rayti);
        edt_arahar_rayti =  findViewById(R.id.edt_arahar_rayti);
        edt_rae_rayti =  findViewById(R.id.edt_rae_rayti);
        edt_ekh_rayti =  findViewById(R.id.edt_ekh_rayti);
        edt_pyaj_rayti =  findViewById(R.id.edt_pyaj_rayti);
        edt_aloo_rayti =  findViewById(R.id.edt_aloo_rayti);

        edt_gehu_gair_rayti =  findViewById(R.id.edt_gehu_gair_rayti);
        edt_makka_gair_rayti =  findViewById(R.id.edt_makka_gair_rayti);
        edt_chana_gair_rayti =  findViewById(R.id.edt_chana_gair_rayti);
        edt_masur_gair_rayti =  findViewById(R.id.edt_masur_gair_rayti);
        edt_arahar_gair_rayti =  findViewById(R.id.edt_arahar_gair_rayti);
        edt_rae_gair_rayti =  findViewById(R.id.edt_rae_gair_rayti);
        edt_ekh_gair_rayti =  findViewById(R.id.edt_ekh_gair_rayti);
        edt_pyaj_gair_rayti =  findViewById(R.id.edt_pyaj_gair_rayti);
        edt_aloo_gair_rayti =  findViewById(R.id.edt_aloo_gair_rayti);

        lin_gehu_rayti =  findViewById(R.id.lin_gehu_rayti);
        lin_gehu_rayti_edt =  findViewById(R.id.lin_gehu_rayti_edt);
        lin_makka_rayti =  findViewById(R.id.lin_makka_rayti);
        lin_makka_rayti_edt =  findViewById(R.id.lin_makka_rayti_edt);
        lin_chana_rayti =  findViewById(R.id.lin_chana_rayti);
        lin_chana_rayti_edt =  findViewById(R.id.lin_chana_rayti_edt);
        lin_masur_rayti =  findViewById(R.id.lin_masur_rayti);
        lin_masur_rayti_edt =  findViewById(R.id.lin_masur_rayti_edt);
        lin_arahar_rayti =  findViewById(R.id.lin_arahar_rayti);
        lin_arahar_rayti_edt =  findViewById(R.id.lin_arahar_rayti_edt);
        lin_Rae_raytti =  findViewById(R.id.lin_Rae_raytti);
        lin_Rae_raytti_edt =  findViewById(R.id.lin_Rae_raytti_edt);
        lin_ekh_rayti =  findViewById(R.id.lin_ekh_rayti);
        lin_ekh_rayti_edt =  findViewById(R.id.lin_ekh_rayti_edt);
        lin_pyaj_rayti =  findViewById(R.id.lin_pyaj_rayti);
        lin_pyaj_rayti_edt =  findViewById(R.id.lin_pyaj_rayti_edt);
        lin_aloo_rayti =  findViewById(R.id.lin_aloo_rayti);
        lin_aloo_rayti_edt =  findViewById(R.id.lin_aloo_rayti_edt);

        lin_gair_gehu_rayti =  findViewById(R.id.lin_gair_gehu_rayti);
        lin_gair_gehu_rayti_edt =  findViewById(R.id.lin_gair_gehu_rayti_edt);
        lin_gair_makka_rayti =  findViewById(R.id.lin_gair_makka_rayti);
        lin_gair_makka_rayti_edt =  findViewById(R.id.lin_gair_makka_rayti_edt);
        lin_gair_chana_rayti =  findViewById(R.id.lin_gair_chana_rayti);
        lin_gair_chana_rayti_edt =  findViewById(R.id.lin_gair_chana_rayti_edt);
        lin_gair_masur_rayti =  findViewById(R.id.lin_gair_masur_rayti);
        lin_gair_masur_rayti_edt =  findViewById(R.id.lin_gair_masur_rayti_edt);
        lin_gair_arahar_rayti =  findViewById(R.id.lin_gair_arahar_rayti);
        lin_gair_arahar_rayti_edt =  findViewById(R.id.lin_gair_arahar_rayti_edt);
        lin_gair_Rae_raytti =  findViewById(R.id.lin_gair_Rae_raytti);
        lin_gair_Rae_raytti_edt =  findViewById(R.id.lin_gair_Rae_raytti_edt);
        lin_gair_ekh_rayti =  findViewById(R.id.lin_gair_ekh_rayti);
        lin_gair_ekh_rayti_edt =  findViewById(R.id.lin_gair_ekh_rayti_edt);
        lin_gair_pyaj_rayti =  findViewById(R.id.lin_gair_pyaj_rayti);
        lin_gair_pyaj_rayti_edt =  findViewById(R.id.lin_gair_pyaj_rayti_edt);
        lin_gair_aloo_rayti =  findViewById(R.id.lin_gair_aloo_rayti);
        lin_gair_aloo_rayti_edt =  findViewById(R.id.lin_gair_aloo_rayti_edt);
        sp_swaghoshana_patra_sambandhit =  findViewById(R.id.sp_swaghoshana_patra_sambandhit);
        swaghosanaarrayadapter = new ArrayAdapter(this, R.layout.dropdownlist, swa_ghosana_sambandhit);
        sp_swaghoshana_patra_sambandhit.setAdapter(swaghosanaarrayadapter);

        ll_swaghoshana_sambandhit =  findViewById(R.id.ll_swaghoshana_sambandhit);
        ll_swaghosana_sambandhit_nm =  findViewById(R.id.ll_swaghosana_sambandhit_nm);
        tv_swaghosana_signer_nm =  findViewById(R.id.tv_swaghosana_signer_nm);
        //ll_kinke_dwara_Satyapan1 =  findViewById(R.id.ll_kinke_dwara_Satyapan1);
        et_swaghossna_signer_nm =  findViewById(R.id.et_swaghossna_signer_nm);
        et_swaghossna_signer_nm.addTextChangedListener(inputTextWatcher1);

        aawedakAbsent = new ArrayAdapter(this, R.layout.dropdownlist, aawedak_absent);
        sp_aawedak_absent.setAdapter(aawedakAbsent);



//        lin_wheat.setVisibility(View.GONE);
//        lin_makka.setVisibility(View.GONE);
//        lin_masoor.setVisibility(View.GONE);
//        lin_arhar.setVisibility(View.GONE);
//        lin_sugarcane.setVisibility(View.GONE);
//        lin_raisarso.setVisibility(View.GONE);
//        lin_potato.setVisibility(View.GONE);
//        lin_onion.setVisibility(View.GONE);
//        lin_chana.setVisibility(View.GONE);



        scrollview = (ScrollView) findViewById(R.id.scrollview);
        img_farmerphoto=(ImageView)findViewById(R.id.img_farmerphoto);

        // rg_ConsumwerIsPresent=(RadioGroup) findViewById(R.id.rg_ConsumwerIsPresent);
        //  Lin_consumerIsPresent = (LinearLayout) findViewById(R.id.Lin_consumerIsPresent);
        setLocaldata();
        //  tv_remarks.setVisibility(View.GONE);
        //  et_summary.setHint("अभियुक्ति डाले");
        // et_summary.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_CLASS_TEXT);
        int maxLength = 70;
        InputFilter[] fArray1 = new InputFilter[1];
        fArray1[0] = new InputFilter.LengthFilter(maxLength);
        //  et_summary.setFilters(fArray1);
        //et_fieldwheat.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        //et_field_makka.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        //et_masoor.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        // et_arhar.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        //et_sugarcane.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        //et_potato.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        //et_raisarso.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        //et_onion.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        //et_chana.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,5)});
        //et_aawedan_ghosit_rakwa.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        //et_aawedan_ghosit_rakwa_2.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_gehu_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_makka_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_chana_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_masur_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_arahar_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_rae_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_ekh_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_pyaj_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_aloo_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_gehu_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_makka_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_chana_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_masur_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_arahar_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_rae_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_ekh_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_pyaj_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        edt_aloo_gair_rayti.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});

        //et_fieldwheat.setFilters(new InputFilter[]{new DigitsInputFilter(4, 5, 9)});

        if (applicationIds > 0)
        {
            setSummary(str_Reg_No);

//                if (_acceptOrReject.equalsIgnoreCase("A")) {
//                    ll_remarks.setVisibility(View.GONE);
//                }
//                else {
//                    ll_remarks.setVisibility(View.VISIBLE);
//                }
        }
        //  loadRemarksSpinnerdata();
        loadChecklistSpinnerdata();
        try {
            if(!str_Reg_No.equals(""))
            {
                getRegNoId(str_Reg_No);
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        identity_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Boolean value = isNetworkConnected();


                //call method for synce record
                if (value.equals(true)) {

                    Intent intent=new Intent(getApplicationContext(),WebViewActivity.class);
                    String imagesr=farmerDetails.getIDProof();
                    intent.putExtra("IDPROOF",imagesr);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "कृपया इंटरनेट कनेक्शन चालू करे!", Toast.LENGTH_LONG).show();
                }
//                dialogselectdata = new Dialog(EntryDetail.this);
//                dialogselectdata.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialogselectdata.setContentView(R.layout.selectedimagedialog);
//                ImageView img = (ImageView)dialogselectdata.findViewById(R.id.img);


//                byte[]  imgData = Base64.decode(imagesr, Base64.DEFAULT);
//
//
//                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
//                img.setScaleType(ImageView.ScaleType.FIT_XY);
//                img.setImageBitmap(bmp);
//                dialogselectdata.show();





//                identity_card.setTextColor(Color.parseColor("#FF4081"));
//                String imagesr = farmerDetails.getIDProof();
//                File file1 = new File("/storage/emulated/0/ParentFolder/Report/" + "Idproof" + str_Reg_No + ".PDF");
//
//
//                if (!imagesr.equals("N")) {
//                    if (!file1.exists()) {
//                        getFileFromBase64AndSaveInSDCard(imagesr, "Idproof" + str_Reg_No, "PDF");
//                    }
//                    File file = new File("/storage/emulated/0/ParentFolder/Report/" + "Idproof" + str_Reg_No + ".PDF");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    Uri uri;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        uri = Uri.parse("content://com.econnect.team.PdfContentProvider/" + "/storage/emulated/0/ParentFolder/Report/" + "Idproof" + str_Reg_No + ".PDF");
//
//                        // uri = FileProvider.getUriForFile(EntryDetail.this,getApplicationContext().getPackageName() + ".fileProvider", file);
//
//                        // Add in case of if We get Uri from fileProvider.
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                    } else {
//                        uri = Uri.fromFile(new File(String.valueOf(file)));
//                    }
//
//                    intent.setDataAndType(uri, "application/pdf");
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show();
//                }



            }
        });

        sp_aawedak_present.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    Checklist wrd = ChecklistList.get(arg2 - 1);
                    _var_aawedak_present_Id =  wrd.getChecklist_Id();
                    _var_aawedak_present_Nm =  wrd.getChecklist_Name();

                    if(_var_aawedak_present_Id.equals("1")){
                        ll_aawedak_absent.setVisibility(View.GONE);
                        ll_kinke_dwara_Satyapan.setVisibility(View.VISIBLE);
                    }
                    else if(_var_aawedak_present_Id.equals("2")) {
                        ll_aawedak_absent.setVisibility(View.VISIBLE);
                    }

                }
                else if(arg2==0)
                {
                    _var_aawedak_present_Id="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        sp_aawedak_absent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    sevikaabsent_Name = aawedak_absent[arg2].toString();

                    if (sevikaabsent_Name.equals("पति")) {

                        sevikaabsent_Id = "6";
                        ll_kinke_dwara_Satyapan.setVisibility(View.VISIBLE);
                        //  ll_kinke_dwara_Satyapan1.setVisibility(View.VISIBLE);
                    } else if (sevikaabsent_Name.equals("पत्नी")) {

                        sevikaabsent_Id = "1";
                        ll_kinke_dwara_Satyapan.setVisibility(View.VISIBLE);
                        //  ll_kinke_dwara_Satyapan1.setVisibility(View.VISIBLE);


                    } else if (sevikaabsent_Name.equals("पुत्र")) {

                        sevikaabsent_Id = "2";
                        ll_kinke_dwara_Satyapan.setVisibility(View.VISIBLE);
                        //  ll_kinke_dwara_Satyapan1.setVisibility(View.VISIBLE);
                    }else if (sevikaabsent_Name.equals("पुत्री")) {

                        sevikaabsent_Id = "3";
                        ll_kinke_dwara_Satyapan.setVisibility(View.VISIBLE);
                        // ll_kinke_dwara_Satyapan1.setVisibility(View.VISIBLE);
                    }else if (sevikaabsent_Name.equals("माता")) {

                        sevikaabsent_Id = "4";
                        ll_kinke_dwara_Satyapan.setVisibility(View.VISIBLE);
                        //   ll_kinke_dwara_Satyapan1.setVisibility(View.VISIBLE);
                    }else if (sevikaabsent_Name.equals("पिता")) {

                        sevikaabsent_Id = "5";
                        ll_kinke_dwara_Satyapan.setVisibility(View.VISIBLE);
                        //    ll_kinke_dwara_Satyapan1.setVisibility(View.VISIBLE);
                    }else if (sevikaabsent_Name.equals("कोई नहीं")) {

                        sevikaabsent_Id = "7";
                        ll_kinke_dwara_Satyapan.setVisibility(View.GONE);
                        //  ll_kinke_dwara_Satyapan1.setVisibility(View.GONE);
                        ll_swaghoshana_sambandhit.setVisibility(View.VISIBLE);
                    }

                }
                else if(arg2==0)
                {
                    sevikaabsent_Name="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sp_lpc_related.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    Checklist wrd = ChecklistList.get(arg2 - 1);
                    _var_lpc_rltd_chk_Id =  wrd.getChecklist_Id();
                    _var_lpc_rltd_chk_Nm =  wrd.getChecklist_Name();

                    if(_var_lpc_rltd_chk_Id.equals("1")){
                        ll_lpc_aawedan.setVisibility(View.VISIBLE);
                    }
                    else if(_var_lpc_rltd_chk_Id.equals("2")) {
                        ll_lpc_aawedan.setVisibility(View.GONE);
                    }

                }
                else if(arg2==0)
                {
                    _var_lpc_rltd_chk_Id="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sp_lpc_aawedan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    Checklist wrd = ChecklistList.get(arg2 - 1);
                    _var_lpc_awedn_chk_Id =  wrd.getChecklist_Id();
                    _var_lpc_awedn_chk_Nm =  wrd.getChecklist_Name();
                }
                else if(arg2==0)
                {
                    _var_lpc_awedn_chk_Id="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sp_ghosit_fasal_kheti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    Checklist wrd = ChecklistList.get(arg2 - 1);
                    _var_ghosit_fasal_khti_Id =  wrd.getChecklist_Id();
                    _var_ghosit_fasal_khti_Nm =  wrd.getChecklist_Name();

                    if(_var_ghosit_fasal_khti_Id.equals("1")){
                        ll_aawedan_ghosit_rakwa.setVisibility(View.VISIBLE);
                    }
                    else if (_var_ghosit_fasal_khti_Id.equals("2")){
                        ll_aawedan_ghosit_rakwa.setVisibility(View.GONE);
                    }

                }
                else if(arg2==0)
                {
                    _var_ghosit_fasal_khti_Id="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        sp_aawedan_ghosit_rakwa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    Checklist wrd = ChecklistList.get(arg2 - 1);
                    _var_aawedan_ghosit_rakwa_Id =  wrd.getChecklist_Id();
                    _var_aawedan_ghosit_rakwa_Nm =  wrd.getChecklist_Name();
                    if (_var_aawedan_ghosit_rakwa_Id.equals("1")){

                        //et_aawedan_ghosit_rakwa.setVisibility(View.GONE);
                        //et_aawedan_ghosit_rakwa_2.setVisibility(View.GONE);
                        ll_raiyati_all_edt.setVisibility(View.GONE);
                        ll_gair_raiyati_all_edt.setVisibility(View.GONE);
                    }else {
                        if (farmerDetails.getTypeofFarmer().equals("1")){
//                            et_aawedan_ghosit_rakwa.setVisibility(View.VISIBLE);
//                            et_aawedan_ghosit_rakwa_2.setVisibility(View.GONE);
                            ll_raiyati_all_edt.setVisibility(View.VISIBLE);
                            ll_gair_raiyati_all_edt.setVisibility(View.GONE);
                        }
                        else if(farmerDetails.getTypeofFarmer().equals("2")){
//                            et_aawedan_ghosit_rakwa_2.setVisibility(View.VISIBLE);
//                            et_aawedan_ghosit_rakwa.setVisibility(View.GONE);
                            ll_raiyati_all_edt.setVisibility(View.GONE);
                            ll_gair_raiyati_all_edt.setVisibility(View.VISIBLE);
                        } else if(farmerDetails.getTypeofFarmer().equals("3")){
//                            et_aawedan_ghosit_rakwa_2.setVisibility(View.VISIBLE);
//                            et_aawedan_ghosit_rakwa.setVisibility(View.VISIBLE);
                            ll_raiyati_all_edt.setVisibility(View.VISIBLE);
                            ll_gair_raiyati_all_edt.setVisibility(View.VISIBLE);
                        }

                    }

                }
                else if(arg2==0)
                {
                    _var_aawedan_ghosit_rakwa_Id="";
                    //et_aawedan_ghosit_rakwa_2.setVisibility(View.GONE);
                    //et_aawedan_ghosit_rakwa.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sp_aawedak_one_family.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    Checklist wrd = ChecklistList.get(arg2 - 1);
                    _var_aawedak_one_family_Id =  wrd.getChecklist_Id();
                    _var_aawedak_one_family_Nm =  wrd.getChecklist_Name();

                }
                else if(arg2==0)
                {
                    _var_aawedak_one_family_Id="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        sp_swaghoshana_patra_sambandhit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub

                if (arg2 > 0) {
                    _var_swaghosana_sambandhit_Nm = swa_ghosana_sambandhit[arg2].toString();

                    if (_var_swaghosana_sambandhit_Nm.equals("स्व-घोषणा पत्र है एवं किसान सलाहकार के द्वारा हस्ताक्षरित है")) {

                        _var_swaghosana_sambandhit_Id = "K";
                        tv_swaghosana_signer_nm.setText("स्व घोषणा हस्ताक्षर करने वाले किसान सलाहकार का नाम (आधार के अनुसार) *");
                        ll_swaghosana_sambandhit_nm.setVisibility(View.VISIBLE);
                    } else if (_var_swaghosana_sambandhit_Nm.equals("स्व-घोषणा पत्र है एवं वार्ड सदस्य के द्वारा हस्ताक्षरित है")) {

                        _var_swaghosana_sambandhit_Id = "W";
                        tv_swaghosana_signer_nm.setText("स्व घोषणा हस्ताक्षर करने वाले वार्ड सदस्य का नाम (आधार के अनुसार) *");
                        ll_swaghosana_sambandhit_nm.setVisibility(View.VISIBLE);


                    } else if (_var_swaghosana_sambandhit_Nm.equals("स्व-घोषणा पत्र नहीं है")) {

                        _var_swaghosana_sambandhit_Id = "N";
                        ll_swaghosana_sambandhit_nm.setVisibility(View.GONE);
                    }
                    else {
                        ll_swaghosana_sambandhit_nm.setVisibility(View.GONE);
                    }

                }
                else if(arg2==0)
                {
                    _var_swaghosana_sambandhit_Nm="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        chk_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    // Do your coding
                    Chk_agree = "Y";
                } else {
                    Chk_agree = "N";
                }

            }
        });

        resedential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Boolean value = isNetworkConnected();


                //call method for synce record
                if (value.equals(true)) {

                    String imagesr=farmerDetails.getResidential();
                    doc_url=imagesr;
                    new GETDOCPATH().execute();

//                    Intent intent=new Intent(getApplicationContext(),WebViewActivity.class);
//                    String imagesr=farmerDetails.getResidential();
//                    intent.putExtra("resedential",imagesr);
//                    intent.putExtra("IDPROOF","N");
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "कृपया इंटरनेट कनेक्शन चालू करे!", Toast.LENGTH_LONG).show();
                }




//                String imagesr = farmerDetails.getResidential();
//                resedential.setTextColor(Color.parseColor("#FF4081"));
//                File file1 = new File("/storage/emulated/0/ParentFolder/Report/" + "resedential" + str_Reg_No + ".PDF");
//
//                if (!imagesr.equals("N")) {
//                    if (!file1.exists()) {
//                    getFileFromBase64AndSaveInSDCard(imagesr,"resedential"+ str_Reg_No, "PDF");
//                    }
//                    File file = new File("/storage/emulated/0/ParentFolder/Report/" +"resedential"+ str_Reg_No + ".PDF");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    Uri uri;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        uri = Uri.parse("content://com.econnect.team.PdfContentProvider/" + "/storage/emulated/0/ParentFolder/Report/" +"resedential"+ str_Reg_No + ".PDF");
//
//                        // uri = FileProvider.getUriForFile(EntryDetail.this,getApplicationContext().getPackageName() + ".fileProvider", file);
//
//                        // Add in case of if We get Uri from fileProvider.
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    } else {
//                        uri = Uri.fromFile(new File(String.valueOf(file)));
//                    }
//
//                    intent.setDataAndType(uri, "application/pdf");
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                    startActivity(intent);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show();
//                }

            }
        });
        bankpassbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean value = isNetworkConnected();


                //call method for synce record
                if (value.equals(true)) {

                    String imagesr=farmerDetails.getPassbook();
                    doc_url=imagesr;
                    new GETDOCPATH().execute();

//                    Intent intent=new Intent(getApplicationContext(),WebViewActivity.class);
//                    String imagesr=farmerDetails.getPassbook();
//                    intent.putExtra("bankpassbook",imagesr);
//                    intent.putExtra("resedential","N");
//                    intent.putExtra("IDPROOF","N");
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "कृपया इंटरनेट कनेक्शन चालू करे!", Toast.LENGTH_LONG).show();
                }








//                String imagesr = farmerDetails.getPassbook();
//                bankpassbook.setTextColor(Color.parseColor("#FF4081"));
//                File file1 = new File("/storage/emulated/0/ParentFolder/Report/" + "passbook" + str_Reg_No + ".PDF");
//
//                if (!imagesr.equals("N")) {
//                    if (!file1.exists()) {
//                        getFileFromBase64AndSaveInSDCard(imagesr, "passbook" + str_Reg_No, "PDF");
//                    }
//
//                File file = new File("/storage/emulated/0/ParentFolder/Report/"+"passbook"+str_Reg_No+".PDF");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    uri = Uri.parse("content://com.econnect.team.PdfContentProvider/"+"/storage/emulated/0/ParentFolder/Report/"+"passbook"+str_Reg_No+".PDF");
//
//                    // uri = FileProvider.getUriForFile(EntryDetail.this,getApplicationContext().getPackageName() + ".fileProvider", file);
//
//                    // Add in case of if We get Uri from fileProvider.
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }else {
//                    uri = Uri.fromFile(new File(String.valueOf(file)));
//                }
//
//                intent.setDataAndType(uri, "application/pdf");
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show();
//                }
            }
        });
        bhu_swamitwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean value = isNetworkConnected();


                //call method for synce record
                if (value.equals(true)) {

                    String imagesr=farmerDetails.getLPC();
                    doc_url=imagesr;
                    new GETDOCPATH().execute();

//                    Intent intent=new Intent(getApplicationContext(),WebViewActivity.class);
//                    String imagesr=farmerDetails.getLPC();
//                    intent.putExtra("bhu_swamitwa",imagesr);
//                    intent.putExtra("bankpassbook","N");
//                    intent.putExtra("resedential","N");
//                    intent.putExtra("IDPROOF","N");
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "कृपया इंटरनेट कनेक्शन चालू करे!", Toast.LENGTH_LONG).show();
                }





//                String imagesr = farmerDetails.getLPC();
//                Log.d("sbsn",""+imagesr);
//                bhu_swamitwa.setTextColor(Color.parseColor("#FF4081"));
//                File file1 = new File("/storage/emulated/0/ParentFolder/Report/" + "swamitwa" + str_Reg_No + ".PDF");
//                if (!imagesr.equals("N")) {
//                    if (!file1.exists()) {
//                        getFileFromBase64AndSaveInSDCard(imagesr, "swamitwa" + str_Reg_No, "PDF");
//                    }
//
//                File file = new File("/storage/emulated/0/ParentFolder/Report/"+"swamitwa"+str_Reg_No+".PDF");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    uri = Uri.parse("content://com.econnect.team.PdfContentProvider/"+"/storage/emulated/0/ParentFolder/Report/"+"swamitwa"+str_Reg_No+".PDF");
//
//                    // uri = FileProvider.getUriForFile(EntryDetail.this,getApplicationContext().getPackageName() + ".fileProvider", file);
//
//                    // Add in case of if We get Uri from fileProvider.
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }else {
//                    uri = Uri.fromFile(new File(String.valueOf(file)));
//                }
//
//                intent.setDataAndType(uri, "application/pdf");
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show();
//                }
            }
        });
        swa_gosna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean value = isNetworkConnected();

                if (value.equals(true)) {

                    //Intent intent=new Intent(getApplicationContext(),WebViewActivity.class);
                    String imagesr=farmerDetails.getSelfAttested();
                    doc_url=imagesr;
                    new GETDOCPATH().execute();

//                    intent.putExtra("swa_gosna",imagesr);
//                    intent.putExtra("bhu_swamitwa","N");
//                    intent.putExtra("bankpassbook","N");
//                    intent.putExtra("resedential","N");
//                    intent.putExtra("IDPROOF","N");
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "कृपया इंटरनेट कनेक्शन चालू करे!", Toast.LENGTH_LONG).show();
                }





//                swa_gosna.setTextColor(Color.parseColor("#FF4081"));
//                String imagesr =farmerDetails.getSelfAttested();
//
//                File file1 = new File("/storage/emulated/0/ParentFolder/Report/" + "swaGhosna" + str_Reg_No + ".PDF");
//
//                if (!imagesr.equals("N")) {
//                    if (!file1.exists()) {
//                        getFileFromBase64AndSaveInSDCard(imagesr, "swaGhosna" + str_Reg_No, "PDF");
//                    }
//
//                File file = new File("/storage/emulated/0/ParentFolder/Report/"+"swaGhosna"+str_Reg_No+".PDF");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    uri = Uri.parse("content://com.econnect.team.PdfContentProvider/"+"/storage/emulated/0/ParentFolder/Report/"+"swaGhosna"+str_Reg_No+".PDF");
//
//                    // uri = FileProvider.getUriForFile(EntryDetail.this,getApplicationContext().getPackageName() + ".fileProvider", file);
//
//                    // Add in case of if We get Uri from fileProvider.
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }else {
//                    uri = Uri.fromFile(new File(String.valueOf(file)));
//                }
//
//                intent.setDataAndType(uri, "application/pdf");
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show();
//                }
            }
        });
//        sp_remarks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if (arg2 > 0) {
//                    Remarks block = RemarksList.get(arg2 - 1);
//                    _varremarksID = block.getRemarksCode();
//                    _varremarksName = block.getRemarkName();
//                    if (_varremarksID.equals("4"))
//                    {
//                        et_summary.setHint("खाता खसरा संख्या  डाले");
//                        et_summary.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_CLASS_NUMBER);
//
//                    }
//                    if (_varremarksID.equals("12"))
//                    {
//                        et_summary.setHint("निबंधन संख्या  डाले");
//                        et_summary.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_CLASS_NUMBER);
//
//                    }
//
//                    if (_varremarksID.equals("1"))
//                    {
//                        btn_reject.setVisibility(View.GONE);
//                        et_summary.setVisibility(View.GONE);
//                        tv_remarks.setVisibility(View.GONE);
//
//                    }
//
//                    if (_varremarksID.equals("2")||_varremarksID.equals("3")||_varremarksID.equals("7")||_varremarksID.equals("11"))
//                    {
//
//                        et_summary.setVisibility(View.GONE);
//                        tv_remarks.setVisibility(View.GONE);
//
//                    }
//
//                   if (_varremarksID.equals("5")||_varremarksID.equals("6"))
//                    {
//                        tv_remarks.setVisibility(View.VISIBLE);
//                        et_summary.setVisibility(View.GONE);
//
//                    }
//                    if (_varremarksID.equals("4")||_varremarksID.equals("8")||_varremarksID.equals("9")||_varremarksID.equals("10")||_varremarksID.equals("12")||_varremarksID.equals("13"))
//                    {
//
//                        et_summary.setVisibility(View.VISIBLE);
//                        tv_remarks.setVisibility(View.GONE);
//                    }
//                    if (_varremarksID.equals("10")) {
//                        et_summary.setHint("आधार संख्या डाले");
//                        et_summary.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_CLASS_NUMBER);
//                        int maxLength = 12;
//                        InputFilter[] fArray = new InputFilter[1];
//                        fArray[0] = new InputFilter.LengthFilter(maxLength);
//                        et_summary.setFilters(fArray);
//
//                    }
//                    else  if (_varremarksID.equals("1")||_varremarksID.equals("2")||_varremarksID.equals("3")||_varremarksID.equals("5")||_varremarksID.equals("6")||_varremarksID.equals("7")||_varremarksID.equals("8")||_varremarksID.equals("11")||_varremarksID.equals("13")) {
//                        et_summary.setHint("अभियुक्ति डाले");
//                        et_summary.setInputType(InputType.TYPE_CLASS_TEXT |   InputType.TYPE_CLASS_TEXT);
//                        int maxLength = 70;
//                        InputFilter[] fArray1 = new InputFilter[1];
//                        fArray1[0] = new InputFilter.LengthFilter(maxLength);
//                        et_summary.setFilters(fArray1);
//                    }
//
//                    if (_varremarksID.equals("9")) {
//                        et_summary.setHint("बैंक अकाउंट संख्या डाले");
//                        et_summary.setInputType(InputType.TYPE_CLASS_NUMBER |
//                                InputType.TYPE_CLASS_NUMBER);
//                        int maxLength =25;
//                        InputFilter[] fArray = new InputFilter[1];
//                        fArray[0] = new InputFilter.LengthFilter(maxLength);
//                        et_summary.setFilters(fArray);
//
//                    }
//                    else  if (_varremarksID.equals("1")||_varremarksID.equals("2")||_varremarksID.equals("3")||_varremarksID.equals("5")||_varremarksID.equals("6")||_varremarksID.equals("7")||_varremarksID.equals("8")||_varremarksID.equals("11")||_varremarksID.equals("13")) {
//                        et_summary.setHint("अभियुक्ति डाले");
//                        et_summary.setInputType(InputType.TYPE_CLASS_TEXT |
//                                InputType.TYPE_CLASS_TEXT);
//                        int maxLength = 70;
//                        InputFilter[] fArray1 = new InputFilter[1];
//                        fArray1[0] = new InputFilter.LengthFilter(maxLength);
//                        et_summary.setFilters(fArray1);
//                    }
//
//
//
//
//                        btn_reject.setVisibility(View.VISIBLE);
//                } else {
//                    _varremarksID = "";
//                    _varremarksName = "";
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(_acceptOrReject.equalsIgnoreCase("A")) {
                _acceptOrReject = "A";
                //ll_remarks.setVisibility(View.VISIBLE);
                String isvalid = validateRecordBeforeSaving();
                if (isvalid.equalsIgnoreCase("yes")) {
                    //ll_remarks.set
                    sendLoginRegistrationData();
                }
            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _acceptOrReject="R";
                //  ll_remarks.setVisibility(View.VISIBLE);

                String isvalid = validateRecordBeforeSavingreject();
                if (isvalid.equalsIgnoreCase("yes")) {
                    if (applicationIds > 0) {
                        // ll_remarks.setVisibility(View.VISIBLE);
                        setSummary(String.valueOf(applicationIds));
                        sendLoginRegistrationData();
                    }else{
                        // ll_remarks.setVisibility(View.VISIBLE);
                        sendLoginRegistrationData();
                    }

                }


            }
        });

//        rg_ConsumwerIsPresent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton rb = (RadioButton) group.findViewById(checkedId);
//
//                if (null != rb && checkedId > -1) {
//                    if (rb.getText().equals("हाँ")) {
//                        ConsumwerIsPresent = "Y";
//                        Lin_consumerIsPresent.setVisibility(View.VISIBLE);
//                        txtconsumernumber.setVisibility(View.VISIBLE);
//                        et_electricconsumernumber.setVisibility(View.VISIBLE);
//                    } else if (rb.getText().equals("नहीं")) {
//                        ConsumwerIsPresent = "N";
//                        Lin_consumerIsPresent.setVisibility(View.GONE);
//                        txtconsumernumber.setVisibility(View.GONE);
//                        et_electricconsumernumber.setVisibility(View.GONE);
//                    }
//                }
//
//
//            }
//        });


    }
    public static GetFilePathAndStatus getFileFromBase64AndSaveInSDCard(String base64, String filename,String extension){
        GetFilePathAndStatus getFilePathAndStatus = new GetFilePathAndStatus();
        try {
            byte[] pdfAsBytes = Base64.decode(base64, 0);
            FileOutputStream os;
            os = new FileOutputStream(getReportPath(filename,extension), false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
            getFilePathAndStatus.filStatus = true;
            getFilePathAndStatus.filePath = getReportPath(filename,extension);
            Log.d("vewfhebfh",""+getFilePathAndStatus);
            return getFilePathAndStatus;

        } catch (IOException e) {
            e.printStackTrace();
            getFilePathAndStatus.filStatus = false;
            getFilePathAndStatus.filePath = getReportPath(filename,extension);
            return getFilePathAndStatus;
        }
    }

    public static String getReportPath(String filename,String extension) {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "ParentFolder/Report");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + filename + "."+extension);
        Log.d("dysggwugh",""+uriSting);
        return uriSting;

    }
    public static class GetFilePathAndStatus{
        public boolean filStatus;
        public String filePath;

    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "CameraAPIDemo");

    }


    private String validateRecordBeforeSaving() {
        String isvalid = "yes";

        if ((sp_aawedak_present != null && sp_aawedak_present.getSelectedItem() != null)) {
            if (!sp_aawedak_present.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                isvalid = "yes";
            } else {
                Toast.makeText(EntryDetail.this, "कृपया सत्यापन के दौरान आवेदक उपस्थित है का चयन करे ", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
        if(_var_aawedak_present_Id.equalsIgnoreCase("2")){
            if ((sp_aawedak_absent != null && sp_aawedak_absent.getSelectedItem() != null)) {
                if (!sp_aawedak_absent.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                    isvalid = "yes";
                } else {
                    Toast.makeText(EntryDetail.this, "कृपया आवेदक के अनुपस्थिति में किनके द्वारा सत्यापन कराया गया का चयन करे ", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }
//            if (sevikaabsent_Name.equals("")) {
//                Toast.makeText(EntryDetail.this, "कृपया आवेदक के अनुपस्थिति में किनके द्वारा सत्यापन कराया गया का चयन करे ", Toast.LENGTH_LONG).show();
//
//                return "no";
//            }
        }
        if(!sevikaabsent_Id.equalsIgnoreCase("7")) {


            if (farmerDetails.getTypeofFarmer().equals("1")) {

                if ((sp_lpc_related != null && sp_lpc_related.getSelectedItem() != null)) {
                    if (!sp_lpc_related.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                        isvalid = "yes";
                    } else {
                        Toast.makeText(EntryDetail.this, "कृपया LPC संबंधित अंचल कर्यालय से निर्गत है का चयन करे ", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                }
                if (_var_lpc_rltd_chk_Id.equalsIgnoreCase("1")) {
                    if ((sp_lpc_aawedan != null && sp_lpc_aawedan.getSelectedItem() != null)) {
                        if (!sp_lpc_aawedan.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                            isvalid = "yes";
                        } else {
                            Toast.makeText(EntryDetail.this, "कृपया आवेदन में बर्णित खाता खेसरा संख्या LPC के अनुरूप है का चयन करे ", Toast.LENGTH_LONG).show();
                            return "no";
                        }
                    }
                }
                if ((sp_ghosit_fasal_kheti != null && sp_ghosit_fasal_kheti.getSelectedItem() != null)) {
                    if (!sp_ghosit_fasal_kheti.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                        isvalid = "yes";
                    } else {
                        Toast.makeText(EntryDetail.this, "कृपया आवेदनकर्ता द्वारा घोषित फसल की खेती की गई है का चयन करे ", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                }
                if (_var_ghosit_fasal_khti_Id.equalsIgnoreCase("1")) {
                    if ((sp_aawedan_ghosit_rakwa != null && sp_aawedan_ghosit_rakwa.getSelectedItem() != null)) {
                        if (!sp_aawedan_ghosit_rakwa.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                            isvalid = "yes";
                        } else {
                            Toast.makeText(EntryDetail.this, "कृपया फसल का वास्तविक बुआई क्षेत्र आवेदन में घोषित बुआई क्षेत्र के अनुसार है  का चयन करे", Toast.LENGTH_LONG).show();
                            return "no";
                        }
                    }
                    if (_var_aawedan_ghosit_rakwa_Id.equalsIgnoreCase("2")) {

//                        if ((et_aawedan_ghosit_rakwa.getText().toString().trim().equals(""))) {
//                            Toast.makeText(EntryDetail.this, "रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
//                            return "no";
//                        } else if (!(et_aawedan_ghosit_rakwa.getText().toString().trim().equals(""))) {
//                            if (Double.valueOf(et_aawedan_ghosit_rakwa.getText().toString()) <= 0 || Double.valueOf(et_aawedan_ghosit_rakwa.getText().toString()) > 9999) {
//                                Toast.makeText(EntryDetail.this, "रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
//                                return "no";
//                            }
//
//                        }
                        if (!(farmerDetails.getGehu_raytti().equalsIgnoreCase("0.00"))|| (farmerDetails.getGehu_raytti().equalsIgnoreCase("0"))) {

                            if ((edt_gehu_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "गेहूँ (रैयती भूमि) का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_gehu_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_gehu_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "गेहूँ रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getMakka_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMakka_raytti().equalsIgnoreCase("0"))) {


                            if ((edt_makka_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "मक्का (रैयती भूमि) का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_makka_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_makka_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "मक्का रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getChana_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getChana_raytti().equalsIgnoreCase("0"))) {

                            if ((edt_chana_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "चना (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_chana_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_chana_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "चना रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getMasur_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMasur_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_masur_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "मसूर (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_masur_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_masur_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "मसूर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getArahar_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getArahar_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_arahar_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "अरहर (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_arahar_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_arahar_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "अरहर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
//                        if (!(farmerDetails.getRae_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getRae_raytti().equalsIgnoreCase("0"))) {
//                            if ((edt_rae_rayti.getText().toString().trim().equals(""))) {
//                                Toast.makeText(EntryDetail.this, "राई (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
//                                return "no";
//                            } else if (!(edt_rae_rayti.getText().toString().trim().equals(""))) {
//                                if (Double.valueOf(edt_rae_rayti.getText().toString()) <= 0 || Double.valueOf(edt_rae_rayti.getText().toString()) > 9999) {
//                                    Toast.makeText(EntryDetail.this, "राई रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
//                                    return "no";
//                                }
//
//                            }
//                        }
                        if (!(farmerDetails.getEkh_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getEkh_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_ekh_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "ईख (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_ekh_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_ekh_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "ईख रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getPyaj_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getPyaj_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_pyaj_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "प्याज (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_pyaj_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_pyaj_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "प्याज रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getAloo_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getAloo_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_aloo_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "आलू (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_aloo_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_aloo_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "आलू रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                    }
                }
            } else if (farmerDetails.getTypeofFarmer().equals("2")) {
                if ((sp_aawedak_one_family != null && sp_aawedak_one_family.getSelectedItem() != null)) {
                    if (!sp_aawedak_one_family.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                        isvalid = "yes";
                    } else {
                        Toast.makeText(EntryDetail.this, "कृपया आवेदक एक परिवार से एक ही व्यक्ति है का चयन करे", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                }
                if ((sp_ghosit_fasal_kheti != null && sp_ghosit_fasal_kheti.getSelectedItem() != null)) {
                    if (!sp_ghosit_fasal_kheti.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                        isvalid = "yes";
                    } else {
                        Toast.makeText(EntryDetail.this, "कृपया आवेदनकर्ता द्वारा घोषित फसल की खेती की गई है का चयन करे ", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                }
                if (_var_ghosit_fasal_khti_Id.equalsIgnoreCase("1")) {
                    if ((sp_aawedan_ghosit_rakwa != null && sp_aawedan_ghosit_rakwa.getSelectedItem() != null)) {
                        if (!sp_aawedan_ghosit_rakwa.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                            isvalid = "yes";
                        } else {
                            Toast.makeText(EntryDetail.this, "कृपया फसल का वास्तविक बुआई क्षेत्र आवेदन में घोषित बुआई क्षेत्र के अनुसार है  का चयन करे", Toast.LENGTH_LONG).show();
                            return "no";
                        }
                    }
                    if (_var_aawedan_ghosit_rakwa_Id.equalsIgnoreCase("2")) {

//                        if ((et_aawedan_ghosit_rakwa_2.getText().toString().trim().equals(""))) {
//                            Toast.makeText(EntryDetail.this, "गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
//                            return "no";
//                        } else if (!(et_aawedan_ghosit_rakwa_2.getText().toString().trim().equals(""))) {
//                            if (Double.valueOf(et_aawedan_ghosit_rakwa_2.getText().toString()) <= 0 || Double.valueOf(et_aawedan_ghosit_rakwa_2.getText().toString()) > 9999) {
//                                Toast.makeText(EntryDetail.this, "गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
//                                return "no";
//                            }
//
//                        }
                        if (!(farmerDetails.getGehu_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getGehu_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_gehu_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "गेहूँ (गैर रैयती भूमि) का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_gehu_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_gehu_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "गेहूँ गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getMakka_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMakka_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_makka_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "मक्का (गैर रैयती भूमि) का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_makka_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_makka_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "मक्का गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getChana_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getChana_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_chana_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "चना (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_chana_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_chana_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "चना गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getMasur_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMasur_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_masur_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "मसूर (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_masur_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_masur_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "मसूर गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getArahar_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getArahar_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_arahar_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "अरहर (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_arahar_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_arahar_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "अरहर गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getRae_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getRae_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_rae_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "राई (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_rae_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_rae_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "राई गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getEkh_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getEkh_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_ekh_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "ईख (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_ekh_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_ekh_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "ईख गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getPyaj_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getPyaj_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_pyaj_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "प्याज (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_pyaj_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_pyaj_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "प्याज गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getAloo_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getAloo_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_aloo_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "आलू (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_aloo_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_aloo_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "आलू गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                    }
                }

            } else if (farmerDetails.getTypeofFarmer().equals("3")) {
                if ((sp_aawedak_one_family != null && sp_aawedak_one_family.getSelectedItem() != null)) {
                    if (!sp_aawedak_one_family.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                        isvalid = "yes";
                    } else {
                        Toast.makeText(EntryDetail.this, "कृपया फसल का वास्तविक बुआई क्षेत्र आवेदन में घोषित बुआई क्षेत्र के अनुसार है  का चयन करे", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                }
                if ((sp_lpc_related != null && sp_lpc_related.getSelectedItem() != null)) {
                    if (!sp_lpc_related.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                        isvalid = "yes";
                    } else {
                        Toast.makeText(EntryDetail.this, "कृपया LPC संबंधित अंचल कर्यालय से निर्गत है का चयन करे ", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                }
                if (_var_lpc_rltd_chk_Id.equalsIgnoreCase("1")) {
                    if ((sp_lpc_aawedan != null && sp_lpc_aawedan.getSelectedItem() != null)) {
                        if (!sp_lpc_aawedan.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                            isvalid = "yes";
                        } else {
                            Toast.makeText(EntryDetail.this, "कृपया आवेदन में बर्णित खाता खेसरा संख्या LPC के अनुरूप है का चयन करे ", Toast.LENGTH_LONG).show();
                            return "no";
                        }
                    }
                }
                if ((sp_ghosit_fasal_kheti != null && sp_ghosit_fasal_kheti.getSelectedItem() != null)) {
                    if (!sp_ghosit_fasal_kheti.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                        isvalid = "yes";
                    } else {
                        Toast.makeText(EntryDetail.this, "कृपया आवेदनकर्ता द्वारा घोषित फसल की खेती की गई है का चयन करे ", Toast.LENGTH_LONG).show();
                        return "no";
                    }
                }
                if (_var_ghosit_fasal_khti_Id.equalsIgnoreCase("1")) {
                    if ((sp_aawedan_ghosit_rakwa != null && sp_aawedan_ghosit_rakwa.getSelectedItem() != null)) {
                        if (!sp_aawedan_ghosit_rakwa.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                            isvalid = "yes";
                        } else {
                            Toast.makeText(EntryDetail.this, "कृपया फसल का वास्तविक बुआई क्षेत्र आवेदन में घोषित बुआई क्षेत्र के अनुसार है  का चयन करे", Toast.LENGTH_LONG).show();
                            return "no";
                        }
                    }
                    if (_var_aawedan_ghosit_rakwa_Id.equalsIgnoreCase("2")) {

                        if (!(farmerDetails.getGehu_raytti().equalsIgnoreCase("0.00"))|| (farmerDetails.getGehu_raytti().equalsIgnoreCase("0"))) {

                            if ((edt_gehu_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "गेहूँ (रैयती भूमि) का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_gehu_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_gehu_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "गेहूँ रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getMakka_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMakka_raytti().equalsIgnoreCase("0"))) {


                            if ((edt_makka_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "मक्का (रैयती भूमि) का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_makka_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_makka_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "मक्का रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getChana_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getChana_raytti().equalsIgnoreCase("0"))) {

                            if ((edt_chana_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "चना (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_chana_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_chana_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "चना रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getMasur_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMasur_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_masur_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "मसूर (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_masur_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_masur_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "मसूर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getArahar_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getArahar_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_arahar_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "अरहर (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_arahar_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_arahar_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "अरहर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
//                        if (!(farmerDetails.getRae_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getRae_raytti().equalsIgnoreCase("0"))) {
//                            if ((edt_rae_rayti.getText().toString().trim().equals(""))) {
//                                Toast.makeText(EntryDetail.this, "राई (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
//                                return "no";
//                            } else if (!(edt_rae_rayti.getText().toString().trim().equals(""))) {
//                                if (Double.valueOf(edt_rae_rayti.getText().toString()) <= 0 || Double.valueOf(edt_rae_rayti.getText().toString()) > 9999) {
//                                    Toast.makeText(EntryDetail.this, "राई रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
//                                    return "no";
//                                }
//
//                            }
//                        }
                        if (!(farmerDetails.getEkh_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getEkh_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_ekh_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "ईख (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_ekh_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_ekh_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "ईख रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getPyaj_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getPyaj_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_pyaj_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "प्याज (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_pyaj_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_pyaj_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "प्याज रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getAloo_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getAloo_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_aloo_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "आलू (रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_aloo_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_aloo_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "आलू रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }

//                        if ((et_aawedan_ghosit_rakwa.getText().toString().trim().equals(""))) {
//                            Toast.makeText(EntryDetail.this, "रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
//                            return "no";
//                        } else if (!(et_aawedan_ghosit_rakwa.getText().toString().trim().equals(""))) {
//                            if (Double.valueOf(et_aawedan_ghosit_rakwa.getText().toString()) <= 0 || Double.valueOf(et_aawedan_ghosit_rakwa.getText().toString()) > 9999) {
//                                Toast.makeText(EntryDetail.this, "रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
//                                return "no";
//                            }
//
//                        }
//                        if ((et_aawedan_ghosit_rakwa_2.getText().toString().trim().equals(""))) {
//                            Toast.makeText(EntryDetail.this, "गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
//                            return "no";
//                        } else if (!(et_aawedan_ghosit_rakwa_2.getText().toString().trim().equals(""))) {
//                            if (Double.valueOf(et_aawedan_ghosit_rakwa_2.getText().toString()) <= 0 || Double.valueOf(et_aawedan_ghosit_rakwa_2.getText().toString()) > 9999) {
//                                Toast.makeText(EntryDetail.this, "गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
//                                return "no";
//                            }
//
//                        }
                        if (!(farmerDetails.getGehu_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getGehu_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_gehu_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "गेहूँ (गैर रैयती भूमि) का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_gehu_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_gehu_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "गेहूँ गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getMakka_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMakka_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_makka_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "मक्का (गैर रैयती भूमि) का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_makka_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_makka_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "मक्का गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getChana_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getChana_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_chana_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "चना (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_chana_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_chana_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "चना गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getMasur_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMasur_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_masur_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "मसूर (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_masur_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_masur_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "मसूर गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getArahar_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getArahar_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_arahar_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "अरहर (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_arahar_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_arahar_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "अरहर गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
//                        if (!(farmerDetails.getRae_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getRae_gair_raytti().equalsIgnoreCase("0"))) {
//                            if ((edt_rae_gair_rayti.getText().toString().trim().equals(""))) {
//                                Toast.makeText(EntryDetail.this, "राई (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
//                                return "no";
//                            } else if (!(edt_rae_gair_rayti.getText().toString().trim().equals(""))) {
//                                if (Double.valueOf(edt_rae_gair_rayti.getText().toString()) > 9999) {
//                                    Toast.makeText(EntryDetail.this, "राई गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
//                                    return "no";
//                                }
//
//                            }
//                        }
                        if (!(farmerDetails.getEkh_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getEkh_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_ekh_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "ईख (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_ekh_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_ekh_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "ईख गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getPyaj_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getPyaj_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_pyaj_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "प्याज (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_pyaj_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_pyaj_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "प्याज गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                        if (!(farmerDetails.getAloo_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getAloo_gair_raytti().equalsIgnoreCase("0"))) {
                            if ((edt_aloo_gair_rayti.getText().toString().trim().equals(""))) {
                                Toast.makeText(EntryDetail.this, "आलू (गैर रैयती भूमि) का भूमि का क्षेत्र(कुल रकवा डिसमिल में) में डाले ", Toast.LENGTH_LONG).show();
                                return "no";
                            } else if (!(edt_aloo_gair_rayti.getText().toString().trim().equals(""))) {
                                if (Double.valueOf(edt_aloo_gair_rayti.getText().toString()) > 9999) {
                                    Toast.makeText(EntryDetail.this, "आलू गैर रैयती भूमि का क्षेत्र(कुल रकवा डिसमिल में) 0 से बड़ा या 9999 से छोटा होना चाहिए", Toast.LENGTH_LONG).show();
                                    return "no";
                                }

                            }
                        }
                    }
                }

            }
        }

        if (farmerDetails.getTypeofFarmer().equals("2")||farmerDetails.getTypeofFarmer().equals("3")) {

            if ((sp_swaghoshana_patra_sambandhit != null && sp_swaghoshana_patra_sambandhit.getSelectedItem() != null)) {
                if (!sp_swaghoshana_patra_sambandhit.getSelectedItem().toString().equalsIgnoreCase("-चुनें-")) {
                    isvalid = "yes";
                } else {
                    Toast.makeText(EntryDetail.this, "कृपया स्व-घोषणा संबंधित का चयन करे ", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }
            if (_var_swaghosana_sambandhit_Id.equals("K")||_var_swaghosana_sambandhit_Id.equals("W"))
            {
                if ((et_swaghossna_signer_nm.getText().toString().trim().equals(""))) {
                    Toast.makeText(EntryDetail.this, "कृपया स्व-घोषणा पत्र पर हस्ताक्षर करने वाले का नाम डाले |", Toast.LENGTH_LONG).show();
                    return "no";
                }
            }

        }

        if (!(chk_agree.isChecked())) {
            Toast.makeText(getApplicationContext(), "कृपया प्रमाणित करे", Toast.LENGTH_LONG).show();
            return "no";

            //your validation here..
        }


//        if ((sp_remarks != null && sp_remarks.getSelectedItem() != null)) {
//            if (!sp_remarks.getSelectedItem().toString().equalsIgnoreCase("-Select-")) {
//                isvalid = "yes";
//            } else {
//                Toast.makeText(EntryDetail.this, "कृपया अभियुक्ति का चयन करे ", Toast.LENGTH_LONG).show();
//                return "no";
//            }
//        }
//
//        if (_varremarksID.equals("13")) {
//            if ((et_summary.getText().toString().trim().equals(""))) {
//                Toast.makeText(EntryDetail.this, "कृपया अभियुक्ति डाले", Toast.LENGTH_LONG).show();
//                return "no";
//            }
//        }
//        if(et_rashan_card_num.getText().toString().length()>0) {
//            //Toast.makeText(EntryDetail.this, "राशन कार्ड नंबर डाले", Toast.LENGTH_LONG).show();
//            //et_rashan_card_num.requestFocus();
//            //return "no";
//
//            if (et_rashan_card_num.getText().toString().trim().length() < 20) {
//                Toast.makeText(EntryDetail.this, "कृपया मान्य राशन कार्ड नंबर दर्ज करें.", Toast.LENGTH_LONG).show();
//                return "no";
//            }
//        }
//
//        if (_varremarksID.equals("10")) {
//            if(et_summary.getText().length()<12){
//                et_summary.setError("कृपया मान्य आधार संख्या डाले");
//                btn_update.setEnabled(false);
//                return "no";
//            }
//        }
//
//        if (_varremarksID.equals("9")) {
//
//            if(et_summary.getText().length()<7){
//                et_summary.setError("कृपया मान्य अकाउंट संख्या  डाले");
//                btn_update.setEnabled(false);
//                return "no";
//            }
//        }

//        if ((et_fieldwheat.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया गेहूँ की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_field_makka.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया मक्का  की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_masoor.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया मसूर  की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_arhar.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया अरहर  की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_sugarcane.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया ईंख की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_potato.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया आलू की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_raisarso.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया राई सरसो की  बुवाई का क्षेत्र  डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_onion.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया प्याज की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_chana.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया चना  की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if(ConsumwerIsPresent.equalsIgnoreCase("Y")){
//            if ((et_electricconsumernumber.getText().toString().trim().equals(""))) {
//                Toast.makeText(EntryDetail.this, "बिजली उपभोक्ता संख्या डाले", Toast.LENGTH_LONG).show();
//                return "no";
//            }
//        }

        return isvalid;
    }

    private String validateRecordBeforeSavingreject() {
        String isvalid = "yes";

        if ((sp_remarks != null && sp_remarks.getSelectedItem() != null)) {
            if (!sp_remarks.getSelectedItem().toString().equalsIgnoreCase("-Select-")) {
                isvalid = "yes";
            } else {
                Toast.makeText(EntryDetail.this, "कृपया अभियुक्ति का चयन करे ", Toast.LENGTH_LONG).show();
                return "no";
            }
        }

//        if (_varremarksID.equals("13")) {
//            if ((et_summary.getText().toString().trim().equals(""))) {
//                Toast.makeText(EntryDetail.this, "कृपया अभियुक्ति डाले", Toast.LENGTH_LONG).show();
//                return "no";
//            }
//        }
        if(et_rashan_card_num.getText().toString().length()>0) {
            //Toast.makeText(EntryDetail.this, "राशन कार्ड नंबर डाले", Toast.LENGTH_LONG).show();
            //et_rashan_card_num.requestFocus();
            //return "no";

            if (et_rashan_card_num.getText().toString().trim().length() < 20) {
                Toast.makeText(EntryDetail.this, "कृपया मान्य राशन कार्ड नंबर दर्ज करें.", Toast.LENGTH_LONG).show();
                return "no";
            }
        }
//        if ((et_fieldwheat.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया गेहूँ की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_field_makka.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया मक्का  की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_masoor.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया मसूर  की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_arhar.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया अरहर  की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_sugarcane.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया ईंख की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_potato.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया आलू की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_raisarso.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया राई सरसो की  बुवाई का क्षेत्र  डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_onion.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया प्याज की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }
//        if ((et_chana.getText().toString().trim().equals(""))) {
//            Toast.makeText(EntryDetail.this, "कृपया चना  की  बुवाई का क्षेत्र डाले", Toast.LENGTH_LONG).show();
//            return "no";
//        }

        return isvalid;
    }
    public void sendLoginRegistrationData() {

        if ((GlobalVariables.isOffline) || (!GlobalVariables.isOffline)) {
            long indexm = -1;
            try {
                DataBaseHelper placeData = new DataBaseHelper(EntryDetail.this);

                SQLiteDatabase db = placeData.getWritableDatabase();

                ContentValues values = new ContentValues();
//                if(_acceptOrReject.equalsIgnoreCase("A"))
//                {
//                    //values.put("Summary",et_summary.getText().toString());
//
//                }
//                else
//                {
//                    //values.put("Summary", et_summary.getText().toString());
//                }

                values.put("Status", _acceptOrReject);
                values.put("RegNo", str_Reg_No);
                values.put("RemarksCode", _varremarksID);
                values.put("RemarksName", _varremarksName);
                values.put("User_Id", farmerDetails.getUser_ID());
                //values.put("RashanCardNumber", et_rashan_card_num.getText().toString().trim());
                //values.put("wheatfield", et_fieldwheat.getText().toString().trim());
                //values.put("makkafield", et_field_makka.getText().toString().trim());
                //values.put("masoorfield", et_masoor.getText().toString().trim());
                // values.put("arharfield", et_arhar.getText().toString().trim());
                //values.put("sugarcanefield", et_sugarcane.getText().toString().trim());
                //values.put("potatofield", et_potato.getText().toString().trim());
                // values.put("raisarsofield", et_raisarso.getText().toString().trim());
                //values.put("onionfield", et_onion.getText().toString().trim());
                //values.put("chanafield", et_chana.getText().toString().trim());
                //values.put("electricity", et_electricconsumernumber.getText().toString().trim());
                values.put("ConsumerNumberIsPresent",ConsumwerIsPresent);

                values.put("aawedak_present",_var_aawedak_present_Id);
                values.put("aawedak_absent",sevikaabsent_Id);
                values.put("lpc_rltd_chk_Id",_var_lpc_rltd_chk_Id);
                values.put("lpc_awedn_chk_Id",_var_lpc_awedn_chk_Id);
                values.put("ghosit_fasal_khti_Id",_var_ghosit_fasal_khti_Id);
                values.put("aawedan_ghosit_rakwa_Id",_var_aawedan_ghosit_rakwa_Id);
                values.put("aawedak_one_family_Id",_var_aawedak_one_family_Id);
                //values.put("et_aawedan_ghosit_rakwa",et_aawedan_ghosit_rakwa.getText().toString());
                //values.put("et_aawedan_ghosit_rakwa_two",et_aawedan_ghosit_rakwa_2.getText().toString());
                values.put("Cheak",Chk_agree);

                values.put("gehu_rayti",edt_gehu_rayti.getText().toString());
                values.put("makka_rayti",edt_makka_rayti.getText().toString());
                values.put("chana_rayti",edt_chana_rayti.getText().toString());
                values.put("masur_rayti",edt_masur_rayti.getText().toString());
                values.put("arahar_rayti",edt_arahar_rayti.getText().toString());
                values.put("rae_rayti",edt_rae_rayti.getText().toString());
                values.put("ekh_rayti",edt_ekh_rayti.getText().toString());
                values.put("pyaj_rayti",edt_pyaj_rayti.getText().toString());
                values.put("aloo_rayti",edt_aloo_rayti.getText().toString());

                values.put("gehu_gair_rayti",edt_gehu_gair_rayti.getText().toString());
                values.put("makka_gair_rayti",edt_makka_gair_rayti.getText().toString());
                values.put("chana_gair_rayti",edt_chana_gair_rayti.getText().toString());
                values.put("masur_gair_rayti",edt_masur_gair_rayti.getText().toString());
                values.put("arahar_gair_rayti",edt_arahar_gair_rayti.getText().toString());
                values.put("rae_gair_rayti",edt_rae_gair_rayti.getText().toString());
                values.put("ekh_gair_rayti",edt_ekh_gair_rayti.getText().toString());
                values.put("pyaj_gair_rayti",edt_pyaj_gair_rayti.getText().toString());
                values.put("aloo_gair_rayti",edt_aloo_gair_rayti.getText().toString());
                values.put("swaghosana_sambandhit_id",_var_swaghosana_sambandhit_Id);
                values.put("swaghosana_sambandhit_nm",_var_swaghosana_sambandhit_Nm);
                values.put("swaghosana_signer_name",et_swaghossna_signer_nm.getText().toString());

                String[] whereArgs = new String[]{str_Reg_No};
                if(_acceptOrReject.equalsIgnoreCase("A")) {
                    if (applicationIds > 0) {
                        //et_summary.setText("");
                        indexm = db.update("InsertFarmer", values, "RegNo=?", whereArgs);
                        //Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EntryDetail.this, SinglePhotoActivity.class);
                        i.putExtra("index", str_Reg_No);
                        i.putExtra("edited", "yes");
                        startActivity(i);
                        //finish();
                    } else {
                        placeData = new DataBaseHelper(EntryDetail.this);
                        db = placeData.getWritableDatabase();
                        Cursor cur = db.rawQuery("SELECT RegNo from InsertFarmer where Photo1 IS NULL AND RegNo ='"+str_Reg_No+"'", null);
                        if(cur.getCount()>0) {

                            Log.e("NUll PH Count",String.valueOf(cur.getCount()));
                            //indexm = db.insert("InsertFarmer", null, values);
                            Intent i = new Intent(EntryDetail.this, SinglePhotoActivity.class);
                            i.putExtra("index", str_Reg_No);
                            i.putExtra("edited", "new");
                            startActivity(i);
                        }else {
                            Log.e("Else NUll PH Count",String.valueOf(cur.getCount()));
                            indexm = db.insert("InsertFarmer", null, values);
                            Intent i = new Intent(EntryDetail.this, SinglePhotoActivity.class);
                            i.putExtra("index", str_Reg_No);
                            i.putExtra("edited", "new");
                            startActivity(i);
                        }
                        // finish();
                    }
                }else{
                    if (applicationIds > 0) {
                        String thisph=null;
                        values.put("Photo1", thisph);
                        values.put("Photo2", thisph);
                        values.put("Photo3", thisph);
                        values.put("Photo4", thisph);
                        values.put("Photo5", thisph);
                        indexm = db.update("InsertFarmer", values, "RegNo=?", whereArgs);
                        Intent i = new Intent(EntryDetail.this, ExistingEntry.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        indexm = db.insert("InsertFarmer", null, values);
                        Intent i = new Intent(EntryDetail.this, ExistingEntry.class);
                        startActivity(i);
                        finish();

                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(EntryDetail.this, "कुछ गलत हो गया!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String getwardId(String userID)
    {
        try {
            long x = 0;
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            SQLiteDatabase db;
            db = dataBaseHelper.getReadableDatabase();
            Cursor cur = db.rawQuery("Select PanchayatCode from FarmerDetails where User_ID='" + userID + "'", null);
            x = cur.getCount();
            cur.moveToNext();
            str_panchayatId = cur.getString(0);
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str_panchayatId;
    }
    public long getRegNoId(String RegNo) {
        long x = 0;
        Cursor cur=null;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase db;
        db = dataBaseHelper.getReadableDatabase();
        try {

            dataBaseHelper = new DataBaseHelper(this);
            db = dataBaseHelper.getReadableDatabase();

            cur = db.rawQuery("Select * from InsertFarmer where RegNo='" + RegNo + "'", null);

            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void setLocaldata()
    {
        farmerDetails= dbhelper.getEditabeldata(str_Reg_No);

        // if (farmerDetails.getTypeofFarmer().equalsIgnoreCase("रैयत कृषक")) {
        if (farmerDetails.getTypeofFarmer().equals("1")) {

            house_hold_id.setVisibility(View.GONE);
            ll_khata_khesara.setVisibility(View.VISIBLE);
            ll_lpc_related.setVisibility(View.VISIBLE);
            ll_swaghoshana_sambandhit.setVisibility(View.GONE);
            // ll_lpc_aawedan.setVisibility(View.VISIBLE);
            ll_aawedak_one_family.setVisibility(View.GONE);
            tv_lpc_swagosna.setText("LPC के अनुसार जमीन का विवरण");
            bhu_swamitwa.setVisibility(View.VISIBLE);

        }
        //else if (farmerDetails.getTypeofFarmer().equalsIgnoreCase("गैर रैयत कृषक")) {
        else if (farmerDetails.getTypeofFarmer().equals("2")) {

            house_hold_id.setVisibility(View.VISIBLE);
            ll_aawedak_one_family.setVisibility(View.VISIBLE);
            ll_khata_khesara.setVisibility(View.GONE);
            ll_lpc_related.setVisibility(View.GONE);
            ll_lpc_aawedan.setVisibility(View.GONE);
            ll_swaghoshana_sambandhit.setVisibility(View.VISIBLE);
            tv_lpc_swagosna.setText("स्व-घोषणा के अनुसार जमीन का विवरण");
            bhu_swamitwa.setVisibility(View.GONE);


        }
        else if (farmerDetails.getTypeofFarmer().equals("3")) {

            house_hold_id.setVisibility(View.VISIBLE);
            ll_aawedak_one_family.setVisibility(View.VISIBLE);
            ll_khata_khesara.setVisibility(View.GONE);
            ll_lpc_related.setVisibility(View.VISIBLE);
            ll_lpc_aawedan.setVisibility(View.GONE);
            ll_swaghoshana_sambandhit.setVisibility(View.VISIBLE);
            tv_lpc_swagosna.setText("LPC एवं स्व-घोषणा के अनुसार जमीन का विवरण");
            bhu_swamitwa.setVisibility(View.VISIBLE);


        }
        if (!farmerDetails.getNibandhanSankhya().equalsIgnoreCase("") ) {
            spn_farmer_nibandhan.setText(farmerDetails.getNibandhanSankhya());
            spn_farmer_nibandhan.setEnabled(false);
        }
        if (!farmerDetails.getDistName().equalsIgnoreCase("") ) {
            spn_dist.setText(farmerDetails.getDistName());
            spn_dist.setEnabled(false);
        }

        if (!farmerDetails.getBlockName().equalsIgnoreCase("")) {
            spn_block.setText(farmerDetails.getBlockName());
            spn_block.setEnabled(false);
        }

        if (!farmerDetails.getPanchayatName().equalsIgnoreCase("") ) {
            spn_panchayat.setText(farmerDetails.getPanchayatName());
            spn_panchayat.setEnabled(false);
        }

        if (!farmerDetails.getWardNo().equalsIgnoreCase("") ) {
            spn_ward.setText(farmerDetails.getWardNo());
            spn_ward.setEnabled(false);
        }
        if (!farmerDetails.getVILLNAME().equalsIgnoreCase("") ) {
            spn_village.setText(farmerDetails.getVILLNAME());
            spn_village.setEnabled(false);
        }
        if (!farmerDetails.getTypeofFarmer().equalsIgnoreCase("")) {
            if(farmerDetails.getTypeofFarmer().equals("1")){
                farmerTypeNm="रैयत";
                ll_raiyati_all.setVisibility(View.VISIBLE);
                ll_gair_raiyati_all.setVisibility(View.GONE);
            }
            else if (farmerDetails.getTypeofFarmer().equals("2")){
                farmerTypeNm="गैर रैयत";
                ll_raiyati_all.setVisibility(View.GONE);
                ll_gair_raiyati_all.setVisibility(View.VISIBLE);
            }
            else if (farmerDetails.getTypeofFarmer().equals("3")){
                farmerTypeNm="रैयत एवं गैर रैयत";
                ll_raiyati_all.setVisibility(View.VISIBLE);
                ll_gair_raiyati_all.setVisibility(View.VISIBLE);
            }


            spn_type_farmer.setText(farmerTypeNm);
            spn_type_farmer.setEnabled(false);
        }
        if (!farmerDetails.getCropTypeKharifName().equalsIgnoreCase("")) {

            et_crop_Type.setText(farmerDetails.getCropTypeKharifName());
            et_crop_Type.setEnabled(false);
        }
//        if (!farmerDetails.getSoyabin().equalsIgnoreCase("0.00")) {
//            lin_soyabin.setVisibility(View.VISIBLE);
//                et_soyabin.setText(farmerDetails.getSoyabin());
//                et_soyabin.setEnabled(false);
//
//        }
//        else {
//            lin_soyabin.setVisibility(View.GONE);
//        }

//        if (!farmerDetails.getArea().equalsIgnoreCase("")) {
//            et_dhan_makka.setText(farmerDetails.getArea());
//            et_dhan_makka.setEnabled(false);
//        }
        if (!farmerDetails.getCropTypeKharif().equalsIgnoreCase("")) {
            if (farmerDetails.getCropTypeKharif().equalsIgnoreCase("1")) {



                // lin_wheat.setVisibility(View.GONE);
                // lin_makka.setVisibility(View.GONE);
                //lin_masoor.setVisibility(View.GONE);
                //lin_arhar.setVisibility(View.GONE);
                //lin_sugarcane.setVisibility(View.GONE);
                //lin_raisarso.setVisibility(View.GONE);
                //lin_potato.setVisibility(View.GONE);
                // lin_onion.setVisibility(View.GONE);
                // lin_chana.setVisibility(View.GONE);
                //tv_dhan_makka.setText("धान की बुवाई का क्षेत्र");
                //lin_soyabin.setVisibility(View.VISIBLE);
                //lin_dhan_makka.setVisibility(View.VISIBLE);

            } else if (farmerDetails.getCropTypeKharif().equalsIgnoreCase("2")) {

                //lin_wheat.setVisibility(View.VISIBLE);
                // lin_makka.setVisibility(View.VISIBLE);
                //lin_masoor.setVisibility(View.VISIBLE);
                //lin_arhar.setVisibility(View.VISIBLE);
                //lin_sugarcane.setVisibility(View.VISIBLE);
                //lin_raisarso.setVisibility(View.VISIBLE);
                //lin_potato.setVisibility(View.VISIBLE);
                //lin_onion.setVisibility(View.VISIBLE);
                //lin_chana.setVisibility(View.VISIBLE);
                // tv_dhan_makka.setText("मक्का की बुवाई का क्षेत्र");
                //lin_soyabin.setVisibility(View.GONE);
                //lin_dhan_makka.setVisibility(View.GONE);

            }
        }

        if (!farmerDetails.getFarmerName().equalsIgnoreCase("") ) {
            et_aawedak_name.setText(farmerDetails.getFarmerName());
            et_aawedak_name.setEnabled(false);
        }
        if (!farmerDetails.getHouseholdid().equalsIgnoreCase("") ) {
            et_house_id.setText(farmerDetails.getHouseholdid());
            et_house_id.setEnabled(false);
        }
        if (!farmerDetails.getFarmerFatherName().equalsIgnoreCase("") ) {
            et_father_husband_name.setText(farmerDetails.getFarmerFatherName());
            et_father_husband_name.setEnabled(false);
        }
        if (!farmerDetails.getMobileNumber().equalsIgnoreCase("") ) {
            et_mobile_number.setText(farmerDetails.getMobileNumber());
            et_mobile_number.setEnabled(false);
        }
        if (!farmerDetails.getCategory_Name().equalsIgnoreCase("") ) {
            spn_category.setText(farmerDetails.getCategory_Name());
            spn_category.setEnabled(false);
        }
        if (!farmerDetails.getGender_Name().equalsIgnoreCase("") ) {
            spn_gender.setText(farmerDetails.getGender_Name());
            spn_gender.setEnabled(false);
        }
        if (!farmerDetails.getAadharNo().equalsIgnoreCase("") ) {
            et_aadhar_number.setText(farmerDetails.getAadharNo());
            et_aadhar_number.setEnabled(false);
        }
        if (!farmerDetails.getAadharName().equalsIgnoreCase("") ) {
            et_aadhar_name.setText(farmerDetails.getAadharName());
            et_aadhar_name.setEnabled(false);
        }
        if (!farmerDetails.getPacsMemberStatus().equalsIgnoreCase("") ) {
            spn_isPacs.setText(farmerDetails.getPacsMemberStatus());
            spn_isPacs.setEnabled(false);
        }
        if (!farmerDetails.getBankName().equalsIgnoreCase("") ) {
            spn_bankname.setText(farmerDetails.getBankName());
            spn_bankname.setEnabled(false);
        }
        if (!farmerDetails.getBankBranchName().equalsIgnoreCase("") )
        {
            et_branch_name.setText(farmerDetails.getBankBranchName());
            et_branch_name.setEnabled(false);
        }
        if (!farmerDetails.getIFSCcode().equalsIgnoreCase("") )
        {
            et_ifsc_code.setText(farmerDetails.getIFSCcode());
            et_ifsc_code.setEnabled(false);
        }
        if (!farmerDetails.getBankAccountNo().equalsIgnoreCase("") )
        {
            et_account_number.setText(farmerDetails.getBankAccountNo());
            et_account_number.setEnabled(false);
        }

        if (applicationIds > 0)
        {
            String rcnum = getRashanCardNumIfExist(str_Reg_No);
            et_rashan_card_num.setText(rcnum);
        }


        if (!farmerDetails.getWheatherName().equalsIgnoreCase("") )
        {
            spn_wheather.setText(farmerDetails.getWheatherName());
            spn_wheather.setEnabled(false);
        }

        if (!farmerDetails.getCropName().equalsIgnoreCase("") )
        {
            spn_corps.setText(farmerDetails.getCropName());
            spn_corps.setEnabled(false);
        }
        if (!farmerDetails.getKhataNo().equalsIgnoreCase("") )
        {
            et_khata_number.setText(farmerDetails.getKhataNo());
            et_khata_number.setEnabled(false);
        }
        if (!farmerDetails.getKhasraNo().equalsIgnoreCase("") )
        {
            et_khesara_number.setText(farmerDetails.getKhasraNo());
            et_khesara_number.setEnabled(false);
        }
        if (!farmerDetails.getArea().equalsIgnoreCase("") )
        {
            et_khestra_rakwa.setText(farmerDetails.getArea());
            et_khestra_rakwa.setEnabled(false);
        }
        //new
        if ((farmerDetails.getGehu_raytti().equalsIgnoreCase("0.00"))|| (farmerDetails.getGehu_raytti().equalsIgnoreCase("0"))) {
            lin_gehu_rayti.setVisibility(View.GONE);
            lin_gehu_rayti_edt.setVisibility(View.GONE);

        }
        else {
            lin_gehu_rayti.setVisibility(View.VISIBLE);
            lin_gehu_rayti_edt.setVisibility(View.VISIBLE);
            et_gehu_rayti.setText(farmerDetails.getGehu_raytti());
            et_gehu_rayti.setEnabled(false);
        }
        if ((farmerDetails.getMakka_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMakka_raytti().equalsIgnoreCase("0"))) {
            lin_makka_rayti.setVisibility(View.GONE);
            lin_makka_rayti_edt.setVisibility(View.GONE);

        }
        else {
            lin_makka_rayti.setVisibility(View.VISIBLE);
            lin_makka_rayti_edt.setVisibility(View.VISIBLE);
            et_makka_rayti.setText(farmerDetails.getMakka_raytti());
            et_makka_rayti.setEnabled(false);
        }
        if ((farmerDetails.getChana_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getChana_raytti().equalsIgnoreCase("0"))) {
            lin_chana_rayti.setVisibility(View.GONE);
            lin_chana_rayti_edt.setVisibility(View.GONE);

        }
        else {
            lin_chana_rayti.setVisibility(View.VISIBLE);
            lin_chana_rayti_edt.setVisibility(View.VISIBLE);
            et_chana_rayti.setText(farmerDetails.getChana_raytti());
            et_chana_rayti.setEnabled(false);
        }
        if ((farmerDetails.getMasur_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMasur_raytti().equalsIgnoreCase("0"))) {
            lin_masur_rayti.setVisibility(View.GONE);
            lin_masur_rayti_edt.setVisibility(View.GONE);

        }
        else {
            lin_masur_rayti.setVisibility(View.VISIBLE);
            lin_masur_rayti_edt.setVisibility(View.VISIBLE);
            et_masur_rayti.setText(farmerDetails.getMasur_raytti());
            et_masur_rayti.setEnabled(false);
        }
        if ((farmerDetails.getArahar_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getArahar_raytti().equalsIgnoreCase("0")))
        {
            lin_arahar_rayti.setVisibility(View.GONE);
            lin_arahar_rayti_edt.setVisibility(View.GONE);

        }
        else {
            lin_arahar_rayti.setVisibility(View.VISIBLE);
            lin_arahar_rayti_edt.setVisibility(View.VISIBLE);
            et_arahar_rayti.setText(farmerDetails.getArahar_raytti());
            et_arahar_rayti.setEnabled(false);
        }
        if ((farmerDetails.getRae_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getRae_raytti().equalsIgnoreCase("0"))) {
            lin_Rae_raytti.setVisibility(View.GONE);
            lin_Rae_raytti_edt.setVisibility(View.GONE);

        }
        else {
            lin_Rae_raytti.setVisibility(View.VISIBLE);
            lin_Rae_raytti_edt.setVisibility(View.VISIBLE);
            et_rae_rayti.setText(farmerDetails.getRae_raytti());
            et_rae_rayti.setEnabled(false);
        }
        if ((farmerDetails.getEkh_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getEkh_raytti().equalsIgnoreCase("0"))) {
            lin_ekh_rayti.setVisibility(View.GONE);
            lin_ekh_rayti_edt.setVisibility(View.GONE);

        }
        else {
            lin_ekh_rayti.setVisibility(View.VISIBLE);
            lin_ekh_rayti_edt.setVisibility(View.VISIBLE);
            et_ekh_rayti.setText(farmerDetails.getEkh_raytti());
            et_ekh_rayti.setEnabled(false);
        }
        if ((farmerDetails.getPyaj_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getPyaj_raytti().equalsIgnoreCase("0")))
        {
            lin_pyaj_rayti.setVisibility(View.GONE);
            lin_pyaj_rayti_edt.setVisibility(View.GONE);

        }
        else
        {
            lin_pyaj_rayti.setVisibility(View.VISIBLE);
            lin_pyaj_rayti_edt.setVisibility(View.VISIBLE);
            et_pyaj_rayti.setText(farmerDetails.getPyaj_raytti());
            et_pyaj_rayti.setEnabled(false);
        }
        if ((farmerDetails.getAloo_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getAloo_raytti().equalsIgnoreCase("0")))
        {
            lin_aloo_rayti.setVisibility(View.GONE);
            lin_aloo_rayti_edt.setVisibility(View.GONE);

        }
        else
        {
            lin_aloo_rayti.setVisibility(View.VISIBLE);
            lin_aloo_rayti_edt.setVisibility(View.VISIBLE);
            et_aloo_rayti.setText(farmerDetails.getAloo_raytti());
            et_aloo_rayti.setEnabled(false);
        }
        //second

        if ((farmerDetails.getGehu_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getGehu_gair_raytti().equalsIgnoreCase("0"))) {
            lin_gair_gehu_rayti.setVisibility(View.GONE);
            lin_gair_gehu_rayti_edt.setVisibility(View.GONE);

        }
        else
        {
            lin_gair_gehu_rayti.setVisibility(View.VISIBLE);
            lin_gair_gehu_rayti_edt.setVisibility(View.VISIBLE);
            et_gehu_gair_rayti.setText(farmerDetails.getGehu_gair_raytti());
            et_gehu_gair_rayti.setEnabled(false);
        }

        if ((farmerDetails.getMakka_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMakka_gair_raytti().equalsIgnoreCase("0"))) {
            lin_gair_makka_rayti.setVisibility(View.GONE);
            lin_gair_makka_rayti_edt.setVisibility(View.GONE);

        }
        else
        {
            lin_gair_makka_rayti.setVisibility(View.VISIBLE);
            lin_gair_makka_rayti_edt.setVisibility(View.VISIBLE);
            et_makka_gair_rayti.setText(farmerDetails.getMakka_gair_raytti());
            et_makka_gair_rayti.setEnabled(false);
        }
        if ((farmerDetails.getChana_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getChana_gair_raytti().equalsIgnoreCase("0"))) {
            lin_gair_chana_rayti.setVisibility(View.GONE);
            lin_gair_chana_rayti_edt.setVisibility(View.GONE);

        }
        else
        {
            lin_gair_chana_rayti.setVisibility(View.VISIBLE);
            lin_gair_chana_rayti_edt.setVisibility(View.VISIBLE);
            et_chana_gair_rayti.setText(farmerDetails.getChana_gair_raytti());
            et_chana_gair_rayti.setEnabled(false);
        }
        if ((farmerDetails.getMasur_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getMasur_gair_raytti().equalsIgnoreCase("0")))
        {
            lin_gair_masur_rayti.setVisibility(View.GONE);
            lin_gair_masur_rayti_edt.setVisibility(View.GONE);


        }
        else {
            lin_gair_masur_rayti.setVisibility(View.VISIBLE);
            lin_gair_masur_rayti_edt.setVisibility(View.VISIBLE);
            et_masur_gair_rayti.setText(farmerDetails.getMasur_gair_raytti());
            et_masur_gair_rayti.setEnabled(false);
        }
        if ((farmerDetails.getArahar_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getArahar_gair_raytti().equalsIgnoreCase("0"))) {

            lin_gair_arahar_rayti.setVisibility(View.GONE);
            lin_gair_arahar_rayti_edt.setVisibility(View.GONE);


        }
        else {
            lin_gair_arahar_rayti.setVisibility(View.VISIBLE);
            lin_gair_arahar_rayti_edt.setVisibility(View.VISIBLE);
            et_arahar_gair_rayti.setText(farmerDetails.getArahar_gair_raytti());
            et_arahar_gair_rayti.setEnabled(false);
        }
        if ((farmerDetails.getRae_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getRae_gair_raytti().equalsIgnoreCase("0"))) {
            lin_gair_Rae_raytti.setVisibility(View.GONE);
            lin_gair_Rae_raytti_edt.setVisibility(View.GONE);

        }
        else {
            lin_gair_Rae_raytti.setVisibility(View.VISIBLE);
            lin_gair_Rae_raytti_edt.setVisibility(View.VISIBLE);
            et_rae_gair_rayti.setText(farmerDetails.getRae_gair_raytti());
            et_rae_gair_rayti.setEnabled(false);
        }
        if ((farmerDetails.getEkh_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getEkh_gair_raytti().equalsIgnoreCase("0"))) {
            lin_gair_ekh_rayti.setVisibility(View.GONE);
            lin_gair_ekh_rayti_edt.setVisibility(View.GONE);

        }
        else {
            lin_gair_ekh_rayti.setVisibility(View.VISIBLE);
            lin_gair_ekh_rayti_edt.setVisibility(View.VISIBLE);
            et_ekh_gair_rayti.setText(farmerDetails.getEkh_gair_raytti());
            et_ekh_gair_rayti.setEnabled(false);
        }
        if ((farmerDetails.getPyaj_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getPyaj_gair_raytti().equalsIgnoreCase("0"))) {
            lin_gair_pyaj_rayti.setVisibility(View.GONE);
            lin_gair_pyaj_rayti_edt.setVisibility(View.GONE);


        }
        else {
            lin_gair_pyaj_rayti.setVisibility(View.VISIBLE);
            lin_gair_pyaj_rayti_edt.setVisibility(View.VISIBLE);
            et_pyaj_gair_rayti.setText(farmerDetails.getPyaj_gair_raytti());
            et_pyaj_gair_rayti.setEnabled(false);
        }
        if ((farmerDetails.getAloo_gair_raytti().equalsIgnoreCase("0.00"))||(farmerDetails.getAloo_gair_raytti().equalsIgnoreCase("0"))) {
            lin_gair_aloo_rayti.setVisibility(View.GONE);
            lin_gair_aloo_rayti_edt.setVisibility(View.GONE);


        }
        else {
            lin_gair_aloo_rayti.setVisibility(View.VISIBLE);
            lin_gair_aloo_rayti_edt.setVisibility(View.VISIBLE);
            et_aloo_gair_rayti.setText(farmerDetails.getAloo_gair_raytti());
            et_aloo_gair_rayti.setEnabled(false);
        }




        // if (farmerDetails.getISCOnsumbernumberExist().equalsIgnoreCase("Y") ) {

//        if(farmerDetails.getISCOnsumbernumberExist().equalsIgnoreCase("Y"))
//        {
//            // rg_Videography_done.getChildAt(0).setSelected(true);
//
//            ((RadioButton)rg_ConsumwerIsPresent.getChildAt(0)).setChecked(true);
//            ((RadioButton)rg_ConsumwerIsPresent.getChildAt(1)).setChecked(false);
//            Lin_consumerIsPresent.setVisibility(View.VISIBLE);
//            txtconsumernumber.setVisibility(View.VISIBLE);
//        }
//
//        else  if(farmerDetails.getISCOnsumbernumberExist().equalsIgnoreCase("N"))
//        {
//            //rg_Videography_done.getChildAt(1).setSelected(true);
//            ((RadioButton)rg_ConsumwerIsPresent.getChildAt(1)).setChecked(true);
//            ((RadioButton)rg_ConsumwerIsPresent.getChildAt(0)).setChecked(false);
//            Lin_consumerIsPresent.setVisibility(View.GONE);
//            txtconsumernumber.setVisibility(View.GONE);
//        }
//        }
//        else {
//
//            Lin_consumerIsPresent.setVisibility(View.GONE);
//            txtconsumernumber.setVisibility(View.GONE);
//
//
//        }

        if (!farmerDetails.getCropArea().equalsIgnoreCase("") ) {
            et_khestra_makka.setText(farmerDetails.getCropArea());
            et_khestra_makka.setEnabled(false);
        }

        if(farmerDetails.getFarmerPhotoPath()!=null && !farmerDetails.getFarmerPhotoPath().equalsIgnoreCase("N"))
        {
            String imagesr=farmerDetails.getFarmerPhotoPath();
            byte[]  imgData = Base64.decode(imagesr, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            img_farmerphoto.setScaleType(ImageView.ScaleType.FIT_XY);
            img_farmerphoto.setImageBitmap(bmp);
        }
        else
        {
            img_farmerphoto.setImageResource(R.drawable.profile1_icon);
        }

        if (dbhelper.getUploadCommunityCount() > 0)
        {
            ISConsumerNumberExistRecord();
        }
        else
        {

        }

    }

    public void ISConsumerNumberExistRecord(){

        try {
            SQLiteDatabase db = dbhelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM InsertFarmer where RegNo=? ", new String[]{str_Reg_No});
            if (cursor.moveToNext()) {

                //String COnsumberpresent = cursor.getString(cursor.getColumnIndex("ConsumerNumberIsPresent")) == null ? "" : cursor.getString(cursor.getColumnIndex("ConsumerNumberIsPresent")).toString();
                ConsumwerIsPresent = cursor.getString(cursor.getColumnIndex("ConsumerNumberIsPresent")) == null ? "" : cursor.getString(cursor.getColumnIndex("ConsumerNumberIsPresent")).toString();
                String electricity = cursor.getString(cursor.getColumnIndex("electricity")) == null ? "" : cursor.getString(cursor.getColumnIndex("electricity")).toString();
                String wheatfield = cursor.getString(cursor.getColumnIndex("wheatfield")) == null ? "" : cursor.getString(cursor.getColumnIndex("wheatfield")).toString();
                String makkafield = cursor.getString(cursor.getColumnIndex("makkafield")) == null ? "" : cursor.getString(cursor.getColumnIndex("makkafield")).toString();
                String masoorfield = cursor.getString(cursor.getColumnIndex("masoorfield")) == null ? "" : cursor.getString(cursor.getColumnIndex("masoorfield")).toString();
                String arharfield = cursor.getString(cursor.getColumnIndex("arharfield")) == null ? "" : cursor.getString(cursor.getColumnIndex("arharfield")).toString();
                String sugarcanefield = cursor.getString(cursor.getColumnIndex("sugarcanefield")) == null ? "" : cursor.getString(cursor.getColumnIndex("sugarcanefield")).toString();
                String potatofield = cursor.getString(cursor.getColumnIndex("potatofield")) == null ? "" : cursor.getString(cursor.getColumnIndex("potatofield")).toString();
                String raisarsofield = cursor.getString(cursor.getColumnIndex("raisarsofield")) == null ? "" : cursor.getString(cursor.getColumnIndex("raisarsofield")).toString();
                String onionfield = cursor.getString(cursor.getColumnIndex("onionfield")) == null ? "" : cursor.getString(cursor.getColumnIndex("onionfield")).toString();
                String chanafield = cursor.getString(cursor.getColumnIndex("chanafield")) == null ? "" : cursor.getString(cursor.getColumnIndex("chanafield")).toString();

                String aawedak_present = cursor.getString(cursor.getColumnIndex("aawedak_present")) == null ? "" : cursor.getString(cursor.getColumnIndex("aawedak_present")).toString();
                String aawedak_present_id=dbhelper.getNameFor("CheckList","ChkList_Id","Chklist_Nm",aawedak_present);
                String aawedak_absent = cursor.getString(cursor.getColumnIndex("aawedak_absent")) == null ? "" : cursor.getString(cursor.getColumnIndex("aawedak_absent")).toString();
                String lpc_rltd_chk_Id = cursor.getString(cursor.getColumnIndex("lpc_rltd_chk_Id")) == null ? "" : cursor.getString(cursor.getColumnIndex("lpc_rltd_chk_Id")).toString();
                String lpc_rltd_chk=dbhelper.getNameFor("CheckList","ChkList_Id","Chklist_Nm",lpc_rltd_chk_Id);
                String lpc_awedn_chk_Id = cursor.getString(cursor.getColumnIndex("lpc_awedn_chk_Id")) == null ? "" : cursor.getString(cursor.getColumnIndex("lpc_awedn_chk_Id")).toString();
                String lpc_awedn_chk=dbhelper.getNameFor("CheckList","ChkList_Id","Chklist_Nm",lpc_awedn_chk_Id);
                String ghosit_fasal_khti_Id = cursor.getString(cursor.getColumnIndex("ghosit_fasal_khti_Id")) == null ? "" : cursor.getString(cursor.getColumnIndex("ghosit_fasal_khti_Id")).toString();
                String ghosit_fasal_khti=dbhelper.getNameFor("CheckList","ChkList_Id","Chklist_Nm",ghosit_fasal_khti_Id);
                String aawedan_ghosit_rakwa_Id = cursor.getString(cursor.getColumnIndex("aawedan_ghosit_rakwa_Id")) == null ? "" : cursor.getString(cursor.getColumnIndex("aawedan_ghosit_rakwa_Id")).toString();
                String aawedan_ghosit_rakwa=dbhelper.getNameFor("CheckList","ChkList_Id","Chklist_Nm",aawedan_ghosit_rakwa_Id);
                String aawedak_one_family_Id = cursor.getString(cursor.getColumnIndex("aawedak_one_family_Id")) == null ? "" : cursor.getString(cursor.getColumnIndex("aawedak_one_family_Id")).toString();
                String aawedak_one_family=dbhelper.getNameFor("CheckList","ChkList_Id","Chklist_Nm",aawedak_one_family_Id);
                String Str_aawedan_ghosit_rakwa = cursor.getString(cursor.getColumnIndex("et_aawedan_ghosit_rakwa")) == null ? "" : cursor.getString(cursor.getColumnIndex("et_aawedan_ghosit_rakwa")).toString();
                String et_aawedan_ghosit_rakwa_two = cursor.getString(cursor.getColumnIndex("et_aawedan_ghosit_rakwa_two")) == null ? "" : cursor.getString(cursor.getColumnIndex("et_aawedan_ghosit_rakwa_two")).toString();

                String gehu_rayti = cursor.getString(cursor.getColumnIndex("gehu_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("gehu_rayti")).toString();
                String makka_rayti = cursor.getString(cursor.getColumnIndex("makka_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("makka_rayti")).toString();
                String chana_rayti = cursor.getString(cursor.getColumnIndex("chana_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("chana_rayti")).toString();
                String masur_rayti = cursor.getString(cursor.getColumnIndex("masur_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("masur_rayti")).toString();
                String arahar_rayti = cursor.getString(cursor.getColumnIndex("arahar_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("arahar_rayti")).toString();
                String rae_rayti = cursor.getString(cursor.getColumnIndex("rae_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("rae_rayti")).toString();
                String ekh_rayti = cursor.getString(cursor.getColumnIndex("ekh_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("ekh_rayti")).toString();
                String pyaj_rayti = cursor.getString(cursor.getColumnIndex("pyaj_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("pyaj_rayti")).toString();
                String aloo_rayti = cursor.getString(cursor.getColumnIndex("aloo_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("aloo_rayti")).toString();

                String gehu_gair_rayti = cursor.getString(cursor.getColumnIndex("gehu_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("gehu_gair_rayti")).toString();
                String makka_gair_rayti = cursor.getString(cursor.getColumnIndex("makka_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("makka_gair_rayti")).toString();
                String chana_gair_rayti = cursor.getString(cursor.getColumnIndex("chana_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("chana_gair_rayti")).toString();
                String masur_gair_rayti = cursor.getString(cursor.getColumnIndex("masur_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("masur_gair_rayti")).toString();
                String arahar_gair_rayti = cursor.getString(cursor.getColumnIndex("arahar_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("arahar_gair_rayti")).toString();
                String rae_gair_rayti = cursor.getString(cursor.getColumnIndex("rae_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("rae_gair_rayti")).toString();
                String ekh_gair_rayti = cursor.getString(cursor.getColumnIndex("ekh_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("ekh_gair_rayti")).toString();
                String pyaj_gair_rayti = cursor.getString(cursor.getColumnIndex("pyaj_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("pyaj_gair_rayti")).toString();
                String aloo_gair_rayti = cursor.getString(cursor.getColumnIndex("aloo_gair_rayti")) == null ? "" : cursor.getString(cursor.getColumnIndex("aloo_gair_rayti")).toString();

                String cheak = cursor.getString(cursor.getColumnIndex("Cheak")) == null ? "" : cursor.getString(cursor.getColumnIndex("Cheak")).toString();

                String _swaghosana_sambandhit_name = cursor.getString(cursor.getColumnIndex("swaghosana_sambandhit_nm")) == null ? "" : cursor.getString(cursor.getColumnIndex("swaghosana_sambandhit_nm")).toString();
                String _swaghosana_sambandhit_ID = cursor.getString(cursor.getColumnIndex("swaghosana_sambandhit_id")) == null ? "" : cursor.getString(cursor.getColumnIndex("swaghosana_sambandhit_id")).toString();
                String _swaghosana_signer_nm = cursor.getString(cursor.getColumnIndex("swaghosana_signer_name")) == null ? "" : cursor.getString(cursor.getColumnIndex("swaghosana_signer_name")).toString();
                // String COnsumbernumber = cursor.getString(cursor.getColumnIndex("electricity")) == null ? "" : cursor.getString(cursor.getColumnIndex("electricity")).toString();

                //  if (COnsumberpresent.equalsIgnoreCase("Y")) {
//                if (ConsumwerIsPresent.equalsIgnoreCase("Y")) {
//                    // rg_Videography_done.getChildAt(0).setSelected(true);
//
//                    ((RadioButton) rg_ConsumwerIsPresent.getChildAt(0)).setChecked(true);
//                    ((RadioButton) rg_ConsumwerIsPresent.getChildAt(1)).setChecked(false);
//                    Lin_consumerIsPresent.setVisibility(View.VISIBLE);
//                    txtconsumernumber.setVisibility(View.VISIBLE);
//                    et_electricconsumernumber.setVisibility(View.VISIBLE);
//                    et_electricconsumernumber.setText(electricity);
//                }
//                // else if (COnsumberpresent.equalsIgnoreCase("N")) {
//                else if (ConsumwerIsPresent.equalsIgnoreCase("N")) {
//                    //rg_Videography_done.getChildAt(1).setSelected(true);
//                    ((RadioButton) rg_ConsumwerIsPresent.getChildAt(1)).setChecked(true);
//                    ((RadioButton) rg_ConsumwerIsPresent.getChildAt(0)).setChecked(false);
//                    Lin_consumerIsPresent.setVisibility(View.GONE);
//                    txtconsumernumber.setVisibility(View.GONE);
//                    et_electricconsumernumber.setVisibility(View.GONE);
//                }

                //et_electricconsumernumber.setText(COnsumbernumber);
//                et_fieldwheat.setText(wheatfield);
//                et_field_makka.setText(makkafield);
//                et_masoor.setText(masoorfield);
//                et_arhar.setText(arharfield);
//                et_sugarcane.setText(sugarcanefield);
//                et_potato.setText(potatofield);
//                et_raisarso.setText(raisarsofield);
//                et_onion.setText(onionfield);
//                et_chana.setText(chanafield);
                sp_aawedak_present.setSelection(((ArrayAdapter<String>) sp_aawedak_present.getAdapter()).getPosition(aawedak_present_id));
                if (farmerDetails.getTypeofFarmer().equals("2")||farmerDetails.getTypeofFarmer().equals("3"))
                {
                    sp_swaghoshana_patra_sambandhit.setSelection(((ArrayAdapter<String>) sp_swaghoshana_patra_sambandhit.getAdapter()).getPosition(_swaghosana_sambandhit_name));
                    if (_swaghosana_sambandhit_ID.equalsIgnoreCase("K")||_swaghosana_sambandhit_ID.equalsIgnoreCase("W")){
                        ll_swaghosana_sambandhit_nm.setVisibility(View.VISIBLE);
                        et_swaghossna_signer_nm.setText(_swaghosana_signer_nm);
                    }
                }

                if(aawedak_present.equalsIgnoreCase("2")){
                    ll_aawedak_absent.setVisibility(View.VISIBLE);
                    sp_aawedak_absent.setSelection(Integer.parseInt(aawedak_absent));
                    //sp_aawedak_absent.setSelection(((ArrayAdapter<String>) sp_aawedak_absent.getAdapter()).getPosition(aawedak_absent));
                }else{
                    ll_aawedak_absent.setVisibility(View.GONE);
                    sp_aawedak_absent.setSelection(0);
                }
                if(!aawedak_absent.equalsIgnoreCase("7")) {
                    ll_kinke_dwara_Satyapan.setVisibility(View.VISIBLE);
                    //  ll_kinke_dwara_Satyapan1.setVisibility(View.VISIBLE);
                    if (farmerDetails.getTypeofFarmer().equals("1")) {
                        sp_lpc_related.setSelection(((ArrayAdapter<String>) sp_lpc_related.getAdapter()).getPosition(lpc_rltd_chk));
                        if (lpc_rltd_chk_Id.equalsIgnoreCase("1")) {
                            sp_lpc_aawedan.setSelection(((ArrayAdapter<String>) sp_lpc_aawedan.getAdapter()).getPosition(lpc_awedn_chk));
                        } else {
                            sp_lpc_aawedan.setSelection(0);
                        }
                        sp_ghosit_fasal_kheti.setSelection(((ArrayAdapter<String>) sp_ghosit_fasal_kheti.getAdapter()).getPosition(ghosit_fasal_khti));
                        if (ghosit_fasal_khti_Id.equalsIgnoreCase("1")) {
                            sp_aawedan_ghosit_rakwa.setSelection(((ArrayAdapter<String>) sp_aawedan_ghosit_rakwa.getAdapter()).getPosition(aawedan_ghosit_rakwa));

                            if (aawedan_ghosit_rakwa_Id.equalsIgnoreCase("2")) {
                                //et_aawedan_ghosit_rakwa.setText(Str_aawedan_ghosit_rakwa);
                                edt_gehu_rayti.setText(gehu_rayti);
                                edt_makka_rayti.setText(makka_rayti);
                                edt_chana_rayti.setText(chana_rayti);
                                edt_masur_rayti.setText(masur_rayti);
                                edt_arahar_rayti.setText(arahar_rayti);
                                edt_rae_rayti.setText(rae_rayti);
                                edt_ekh_rayti.setText(ekh_rayti);
                                edt_pyaj_rayti.setText(pyaj_rayti);
                                edt_aloo_rayti.setText(aloo_rayti);
                            } else {
                                //et_aawedan_ghosit_rakwa.setText("");
                                edt_gehu_rayti.setText("");
                                edt_makka_rayti.setText("");
                                edt_chana_rayti.setText("");
                                edt_masur_rayti.setText("");
                                edt_arahar_rayti.setText("");
                                edt_rae_rayti.setText("");
                                edt_ekh_rayti.setText("");
                                edt_pyaj_rayti.setText("");
                                edt_aloo_rayti.setText("");
                            }
                        } else {
                            sp_aawedan_ghosit_rakwa.setSelection(0);
                        }

                    } else if (farmerDetails.getTypeofFarmer().equals("2")) {
                        sp_aawedak_one_family.setSelection(((ArrayAdapter<String>) sp_aawedak_one_family.getAdapter()).getPosition(aawedak_one_family));
                        sp_ghosit_fasal_kheti.setSelection(((ArrayAdapter<String>) sp_ghosit_fasal_kheti.getAdapter()).getPosition(ghosit_fasal_khti));
                        if (ghosit_fasal_khti_Id.equalsIgnoreCase("1")) {
                            sp_aawedan_ghosit_rakwa.setSelection(((ArrayAdapter<String>) sp_aawedan_ghosit_rakwa.getAdapter()).getPosition(aawedan_ghosit_rakwa));

                            if (aawedan_ghosit_rakwa_Id.equalsIgnoreCase("2")) {
                                //et_aawedan_ghosit_rakwa_2.setText(et_aawedan_ghosit_rakwa_two);
                                edt_gehu_gair_rayti.setText(gehu_gair_rayti);
                                edt_makka_gair_rayti.setText(makka_gair_rayti);
                                edt_chana_gair_rayti.setText(chana_gair_rayti);
                                edt_masur_gair_rayti.setText(masur_gair_rayti);
                                edt_arahar_gair_rayti.setText(arahar_gair_rayti);
                                edt_rae_gair_rayti.setText(rae_gair_rayti);
                                edt_ekh_gair_rayti.setText(ekh_gair_rayti);
                                edt_pyaj_gair_rayti.setText(pyaj_gair_rayti);
                                edt_aloo_gair_rayti.setText(aloo_gair_rayti);
                            } else {
                                //et_aawedan_ghosit_rakwa_2.setText("");
                                edt_gehu_gair_rayti.setText("");
                                edt_makka_gair_rayti.setText("");
                                edt_chana_gair_rayti.setText("");
                                edt_masur_gair_rayti.setText("");
                                edt_arahar_gair_rayti.setText("");
                                edt_rae_gair_rayti.setText("");
                                edt_ekh_gair_rayti.setText("");
                                edt_pyaj_gair_rayti.setText("");
                                edt_aloo_gair_rayti.setText("");
                            }
                        } else {
                            sp_aawedan_ghosit_rakwa.setSelection(0);
                        }

                    } else if (farmerDetails.getTypeofFarmer().equals("3"))
                    {
                        sp_aawedak_one_family.setSelection(((ArrayAdapter<String>) sp_aawedak_one_family.getAdapter()).getPosition(aawedak_one_family));
                        sp_lpc_related.setSelection(((ArrayAdapter<String>) sp_lpc_related.getAdapter()).getPosition(lpc_rltd_chk));
                        if (lpc_rltd_chk_Id.equalsIgnoreCase("1"))
                        {
                            sp_lpc_aawedan.setSelection(((ArrayAdapter<String>) sp_lpc_aawedan.getAdapter()).getPosition(lpc_awedn_chk));
                        }
                        else
                        {
                            sp_lpc_aawedan.setSelection(0);
                        }
                        sp_ghosit_fasal_kheti.setSelection(((ArrayAdapter<String>) sp_ghosit_fasal_kheti.getAdapter()).getPosition(ghosit_fasal_khti));
                        if (ghosit_fasal_khti_Id.equalsIgnoreCase("1"))
                        {
                            sp_aawedan_ghosit_rakwa.setSelection(((ArrayAdapter<String>) sp_aawedan_ghosit_rakwa.getAdapter()).getPosition(aawedan_ghosit_rakwa));

                            if (aawedan_ghosit_rakwa_Id.equalsIgnoreCase("2"))
                            {
                                edt_gehu_rayti.setText(gehu_rayti);
                                edt_makka_rayti.setText(makka_rayti);
                                edt_chana_rayti.setText(chana_rayti);
                                edt_masur_rayti.setText(masur_rayti);
                                edt_arahar_rayti.setText(arahar_rayti);
                                edt_rae_rayti.setText(rae_rayti);
                                edt_ekh_rayti.setText(ekh_rayti);
                                edt_pyaj_rayti.setText(pyaj_rayti);
                                edt_aloo_rayti.setText(aloo_rayti);

                                edt_gehu_gair_rayti.setText(gehu_gair_rayti);
                                edt_makka_gair_rayti.setText(makka_gair_rayti);
                                edt_chana_gair_rayti.setText(chana_gair_rayti);
                                edt_masur_gair_rayti.setText(masur_gair_rayti);
                                edt_arahar_gair_rayti.setText(arahar_gair_rayti);
                                edt_rae_gair_rayti.setText(rae_gair_rayti);
                                edt_ekh_gair_rayti.setText(ekh_gair_rayti);
                                edt_pyaj_gair_rayti.setText(pyaj_gair_rayti);
                                edt_aloo_gair_rayti.setText(aloo_gair_rayti);
                                //et_aawedan_ghosit_rakwa.setText(Str_aawedan_ghosit_rakwa);
                                //et_aawedan_ghosit_rakwa_2.setText(et_aawedan_ghosit_rakwa_two);
                            }
                            else
                            {
                                edt_gehu_rayti.setText("");
                                edt_makka_rayti.setText("");
                                edt_chana_rayti.setText("");
                                edt_masur_rayti.setText("");
                                edt_arahar_rayti.setText("");
                                edt_rae_rayti.setText("");
                                edt_ekh_rayti.setText("");
                                edt_pyaj_rayti.setText("");
                                edt_aloo_rayti.setText("");

                                edt_gehu_gair_rayti.setText("");
                                edt_makka_gair_rayti.setText("");
                                edt_chana_gair_rayti.setText("");
                                edt_masur_gair_rayti.setText("");
                                edt_arahar_gair_rayti.setText("");
                                edt_rae_gair_rayti.setText("");
                                edt_ekh_gair_rayti.setText("");
                                edt_pyaj_gair_rayti.setText("");
                                edt_aloo_gair_rayti.setText("");

                                //et_aawedan_ghosit_rakwa.setText("");
                                //et_aawedan_ghosit_rakwa_2.setText("");
                            }
                        }
                        else
                        {
                            sp_aawedan_ghosit_rakwa.setSelection(0);
                        }

                    }
                }
                else
                {
                    ll_kinke_dwara_Satyapan.setVisibility(View.GONE);
                    //  ll_kinke_dwara_Satyapan1.setVisibility(View.GONE);
                    sp_lpc_related.setSelection(0);
                    sp_lpc_aawedan.setSelection(0);
                    sp_aawedan_ghosit_rakwa.setSelection(0);
                    et_aawedan_ghosit_rakwa.setText("");
                    sp_aawedak_one_family.setSelection(0);
                    sp_ghosit_fasal_kheti.setSelection(0);
                    et_aawedan_ghosit_rakwa_2.setText("");
                }

                if (cheak.equalsIgnoreCase("Y")) {
                    chk_agree.setChecked(true);
                    Chk_agree = "Y";
                } else {
                    chk_agree.setChecked(false);
                    Chk_agree = "N";
                }

//                sp_lpc_related.setSelection(((ArrayAdapter<String>) sp_lpc_related.getAdapter()).getPosition(lpc_rltd_chk));
//                sp_lpc_aawedan.setSelection(((ArrayAdapter<String>) sp_lpc_aawedan.getAdapter()).getPosition(lpc_awedn_chk));
//                sp_ghosit_fasal_kheti.setSelection(((ArrayAdapter<String>) sp_ghosit_fasal_kheti.getAdapter()).getPosition(ghosit_fasal_khti));
//                sp_aawedan_ghosit_rakwa.setSelection(((ArrayAdapter<String>) sp_aawedan_ghosit_rakwa.getAdapter()).getPosition(aawedan_ghosit_rakwa));
//                sp_aawedak_one_family.setSelection(((ArrayAdapter<String>) sp_aawedak_one_family.getAdapter()).getPosition(aawedak_one_family));

//                et_aawedan_ghosit_rakwa.setText(Str_aawedan_ghosit_rakwa);
//                et_aawedan_ghosit_rakwa_2.setText(et_aawedan_ghosit_rakwa_two);

            }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public String getRashanCardNumIfExist(String thisID)
    {
        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();

        String rcnum="0";
        Cursor cursor = db
                .rawQuery("SELECT RashanCardNumber FROM InsertFarmer where RegNo =?", new String[]{String.valueOf(thisID)});


        if (cursor.moveToNext())
        {

            if (!cursor.isNull(0))
            {
                rcnum=cursor.getString(cursor.getColumnIndex("RashanCardNumber"));
            }
        }
        return rcnum;
    }
    private void setEditData(String slno, FarmerDetails thanaInfo3)
    {
        //et_summary.setVisibility(View.VISIBLE);

        //et_summary.setText("" + thanaInfo3.getSummary());
    }
    private void setSummary(String regno) {
        DataBaseHelper helper = new DataBaseHelper(EntryDetail.this);
        try {
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM InsertFarmer where RegNo ='" + regno + "'", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String Summary = cursor.getString(cursor.getColumnIndex("Summary"));
                        RemarksName= cursor.getString(cursor.getColumnIndex("RemarksName"));

                        //et_summary.setText(Summary);

//                        if (et_summary.getText().toString().length()>0) {
//                           // ll_remarks.setVisibility(View.VISIBLE);
//                            et_summary.setVisibility(View.VISIBLE);
//                        }
//                        else {
//                           // ll_remarks.setVisibility(View.VISIBLE);
//                        }
                    } while (cursor.moveToNext());
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }
    public void loadRemarksSpinnerdata()
    {

        RemarksList = dbhelper.getRemarks();
        String[] typeNameArray = new String[RemarksList.size() + 1];
        typeNameArray[0] = "-Select-";
        int i = 1;
        for (Remarks type : RemarksList)
        {
            typeNameArray[i] = type.getRemarkName();
            i++;
        }
        remarksadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, typeNameArray);
        remarksadapter.setDropDownViewResource(R.layout.dropdownlist);
        sp_remarks.setAdapter(remarksadapter);
        if(applicationIds>0)
        {
            sp_remarks.setSelection(((ArrayAdapter<String>) sp_remarks.getAdapter()).getPosition(RemarksName));
        }


    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void loadChecklistSpinnerdata()
    {

        ChecklistList = dbhelper.getCheckList();
        String[] divNameArray = new String[ChecklistList.size() + 1];
        divNameArray[0] = "-चुनें-";
        int i = 1;
        int setID = 0;
        for (Checklist gen : ChecklistList)
        {
            divNameArray[i] = gen.getChecklist_Name();
            if (checklist == gen.getChecklist_Id())
            {
                setID = i;
            }
            i++;

        }
        checklistadapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, divNameArray);
        checklistadapter.setDropDownViewResource(R.layout.dropdownlist);
        sp_aawedak_present.setAdapter(checklistadapter);
        sp_lpc_related.setAdapter(checklistadapter);
        sp_lpc_aawedan.setAdapter(checklistadapter);
        sp_ghosit_fasal_kheti.setAdapter(checklistadapter);
        sp_aawedan_ghosit_rakwa.setAdapter(checklistadapter);
        sp_aawedak_one_family.setAdapter(checklistadapter);

        if (applicationIds > 0)
        {

            if (farmerDetails.getTypeofFarmer().equals("1"))
            {

                house_hold_id.setVisibility(View.GONE);
                ll_khata_khesara.setVisibility(View.VISIBLE);
                ll_lpc_related.setVisibility(View.VISIBLE);
                // ll_lpc_aawedan.setVisibility(View.VISIBLE);
                ll_aawedak_one_family.setVisibility(View.GONE);
                ISConsumerNumberExistRecord();

            }
            //else if (farmerDetails.getTypeofFarmer().equalsIgnoreCase("गैर रैयत कृषक")) {
            else if (farmerDetails.getTypeofFarmer().equals("2"))
            {

                house_hold_id.setVisibility(View.VISIBLE);
                ll_aawedak_one_family.setVisibility(View.VISIBLE);
                ll_khata_khesara.setVisibility(View.GONE);
                ll_lpc_related.setVisibility(View.GONE);
                ll_lpc_aawedan.setVisibility(View.GONE);
                ISConsumerNumberExistRecord();

            }
            else if (farmerDetails.getTypeofFarmer().equals("3"))
            {

                house_hold_id.setVisibility(View.VISIBLE);
                ll_aawedak_one_family.setVisibility(View.VISIBLE);
                ll_khata_khesara.setVisibility(View.GONE);
                ll_lpc_related.setVisibility(View.VISIBLE);
                ll_lpc_aawedan.setVisibility(View.VISIBLE);

                ISConsumerNumberExistRecord();
            }

//        if(getIntent().hasExtra("KeyId"))
//        {
//            //spinchecklist1.setSelection(setID);
//            sp_lpc_related.setSelection(((ArrayAdapter<String>) sp_lpc_related.getAdapter()).getPosition(_spinchecklist1));
//
//        }

        }
    }

    private class GETDOCPATH extends AsyncTask<String, Void, String>
    {

        String rowid;
        private final ProgressDialog dialog = new ProgressDialog(EntryDetail.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(EntryDetail.this).create();


        @Override
        protected void onPreExecute()
        {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("UpLoading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param)
        {
            String res = WebServiceHelper.UploadLandDetailsPhase1(doc_url);
            return res;

        }

        @Override
        protected void onPostExecute(String result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);

            if (result != null)
            {

                storetoPdfandOpen(EntryDetail.this, result);
//                Intent i=new Intent(getApplicationContext(),WebViewActivity.class);
//            i.putExtra("base64",result);
//            startActivity(i);
                // storetoPdfandOpen(EntryDetail.this,result);
//               imageData = Base64.decode(result.trim(), Base64.DEFAULT);
//
//                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(EntryDetail.this);
//                LayoutInflater inflater = EntryDetail.this.getLayoutInflater();
//                final View dialogView = inflater.inflate(R.layout.viewimage, null);
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.setTitle("-DOCUMENT-");
//
//                Display display = getWindowManager().getDefaultDisplay();
//                Point size = new Point();
//                display.getSize(size);
//                int width = size.x;
//                int height = size.y;
//
//                ImageView imgview = (ImageView) dialogView.findViewById(R.id.imgview);
//                if (imageData != null) {
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//
//                    imgview.setImageBitmap(bmp);
//                }
//
//                dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                    }
//                });
//
//
//                AlertDialog b = dialogBuilder.create();
//                b.show();


//                String string = result;
//                String[] parts = string.split(",");
//                String part1 = parts[0]; // 004-
//                String part2 = parts[1];
//
//                if (part1.equals("1")) {
//
//
//
//                } else if (part1.equals("0")) {
//
//
//
//                }  else {
//                    Toast.makeText(EntryDetail.this, "Your data is not uploaded Successfully ! ", Toast.LENGTH_SHORT).show();
//                }
            }
            else
            {

                Toast.makeText(EntryDetail.this, "Result:null ...Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void storetoPdfandOpen(Context context, String base)
    {

        String root = Environment.getExternalStorageDirectory().toString();

        Log.e("ResponseEnv",root);

        File myDir = new File(root + "/Download");
        if (!myDir.exists())
        {
            myDir.mkdirs();
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);

        String fname = "Attachments-" + n + ".pdf";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            byte[] pdfAsBytes = Base64.decode(base, 0);
            out.write(pdfAsBytes);
            out.flush();
            out.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        File dir = new File(Environment.getExternalStorageDirectory(), "Download");
        File imgFile = new File(dir, fname);
        Intent sendIntent ;

        Uri uri = null;
        try{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {

                //  File file=new File(mFilePath);
                uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
                sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(uri);

                sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(sendIntent);
            }
            else
            {
                sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setDataAndType(Uri.parse(fname), "application/pdf");
                sendIntent = Intent.createChooser(sendIntent, "Open File");
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sendIntent);
            }
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
//            {
//                uri = FileProvider.getUriForFile(Objects.requireNonNull(EntryDetail.this), BuildConfig.APPLICATION_ID + ".fileprovider", file);
//                sendIntent.setDataAndType(uri, "application/pdf");
//                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                context.startActivity(sendIntent);
//
//
//            }
//            else
//            {
//                Toast.makeText(this, "Android Version lower than kitkat is not supported to open this pdf", Toast.LENGTH_SHORT).show();
//            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private TextWatcher inputTextWatcher1 = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_swaghossna_signer_nm.getText().length() >0)
            {
                checkForEnglish(et_swaghossna_signer_nm);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    };

    public void checkForEnglish(EditText etxt)
    {
        if (etxt.getText().length() > 0)
        {
            String s = etxt.getText().toString();
            if (isInputInEnglish(s))
            {
                //OK
            }
            else
            {
                Toast.makeText(this, "Please write in english and only text ,no numbers", Toast.LENGTH_SHORT).show();
                etxt.setText("");
            }
        }
    }

    public static boolean isInputInEnglish(String txtVal)
    {
        String regx = "^[A-Za-z_ ]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txtVal);
        return matcher.find();
    }


}
