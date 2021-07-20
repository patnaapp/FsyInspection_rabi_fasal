package bih.nic.in.raviinspection.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bih.nic.in.raviinspection.R;
import bih.nic.in.raviinspection.activity.ExistingEntry;
import bih.nic.in.raviinspection.database.DataBaseHelper;
import bih.nic.in.raviinspection.database.WebServiceHelper;
import bih.nic.in.raviinspection.entity.FarmerDetails;
import bih.nic.in.raviinspection.utilities.Utiilties;



public class ListDataADapter extends ArrayAdapter<FarmerDetails> {
    Context context;
    int layoutResourceId;
    String str_flag;
    ArrayList<FarmerDetails> data = new ArrayList<FarmerDetails>();
    DataBaseHelper localDBHelper;
    private ArrayList<FarmerDetails> mEntries = new ArrayList<>();
    public ListDataADapter(Context context, int layoutResourceId, ArrayList<FarmerDetails> data)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        localDBHelper=new DataBaseHelper(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ImageHolder holder = null;
        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ImageHolder();
            holder.applicationd = (TextView) row.findViewById(R.id.appId);
            holder.Name = (TextView) row.findViewById(R.id.txt_name);
            holder.fathername = (TextView) row.findViewById(R.id.f_name);

            holder.imgcomp=(ImageView) row.findViewById(R.id.imgcompleted);
            holder.imgupp=(ImageView)row.findViewById(R.id.imguploadnew);
            row.setTag(holder);
        }
        else {
            holder = (ImageHolder) row.getTag();
        }
        final FarmerDetails dataprogress = data.get(position);
        holder.applicationd.setText(dataprogress.getMobileNumber());
        holder.Name.setText(dataprogress.getFarmerName());
        holder.fathername.setText(dataprogress.getFarmerFatherName());
        Log.d("jnjnvn",""+dataprogress.getRegistrationNO());
        if(isphototaken(dataprogress.getRegistrationNO()).contains("yes"))
        {
            holder.imgcomp.setVisibility(View.VISIBLE);
            holder.imgupp.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.imgcomp.setVisibility(View.GONE);
            holder.imgupp.setVisibility(View.GONE);
        }

        holder.imgupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (Utiilties.isOnline(context)) {

                        android.support.v7.app.AlertDialog.Builder ab = new android.support.v7.app.AlertDialog.Builder(
                                context);
                        ab.setTitle("अपलोड!");
                        ab.setMessage("क्या आप सर्वर पर डाटा अपलोड करना चाहते हैं ?");
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
                               localDBHelper=new DataBaseHelper(context);

                                List<FarmerDetails> locDatas = localDBHelper.getThisDataListCommunityData(dataprogress.getRegistrationNO().trim());
                                Log.d("Datainsecondtable",""+locDatas.size());
                                for (FarmerDetails cn : locDatas) {
                                    new uploadingLocData(cn).execute();
                                }
                                //new uploadingLocData(cn).execute();
                            }
                        });
                        ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                        ab.show();

                    } else

                    {
                        Toast.makeText(context, " कोई इंटरनेट कनेक्शन नहीं ! \n कृपया अपनी इंटरनेट कनेक्टिविटी की जांच करें.",
                                Toast.LENGTH_LONG).show();
                    }

                }
        });
        
//        holder = (ImageHolder) row.getTag();
        this.notifyDataSetChanged();
        return row;
    }
    public class uploadingLocData extends AsyncTask<String, Void, String> {
        String result11;
        ProgressDialog pd1;
        FarmerDetails farmerDetails;
        String _regNo;
        public uploadingLocData(FarmerDetails getdatalist){
            this.farmerDetails = getdatalist;
        }



        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd1 = new ProgressDialog(context);
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
                Toast.makeText(context, "डेटा सफलतापूर्वक अपलोड किया गया है !", Toast.LENGTH_SHORT).show();

                boolean isDel=localDBHelper.deleterowCommunity1(farmerDetails.getRegistrationNO());
                if (isDel) {
                    //count = dataBaseHelper.getPendingUploadCount(CommonPref.getUserDetails(context).getUserID());
                    //  pendingTask.setText("" + count);
                    boolean is_del=localDBHelper.deleterowPhaseCommunity1Actual(farmerDetails.getRegistrationNO());

//                    final ArrayList<FarmerDetails> entries = getLocalPendingView(farmerDetails.getPanchayatCode(),farmerDetails.getUser_ID());
//
//                    upDateEntries(entries);

                    if(is_del)
                    {
                        reg_num=farmerDetails.getRegistrationNO().toString().trim();
                        Toast.makeText(context, "वास्तविक डेटा सूची आईडी से हटाया गया:" + reg_num, Toast.LENGTH_SHORT).show();
//                        upDateEntries(entries);
                        ((ExistingEntry)context).BindListData();
                    }
                    else
                    {
                        Toast.makeText(context, "वास्तविक डेटा सूची आईडी से निकालने में विफल रहा:" + reg_num, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.e("message", "data is uploaded but not deleted !!");
                }

            } else {
                Toast.makeText(context, "अपलोडिंग विफल !\n" + result.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }
    static class ImageHolder {
        TextView applicationd,Name,fathername;
        ImageView imgcomp,imgupp;

    }

    public String isphototaken(String regNo)
    {
        Cursor cur=null;
        String istaken="no";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db;
        //db = dataBaseHelper.getReadableDatabase();
        try {

            dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getReadableDatabase();
            cur = db.rawQuery("Select Status from InsertFarmer where RegNo='" + regNo + "' AND ((Photo1 IS NOT NULL AND Status='A') OR (Photo1 IS NULL AND Status='R'))", null);

            if (cur.moveToNext()) {


                if (!cur.isNull(0)) {
                    istaken="yes";

                    Log.e("isphototakenIF",istaken);
                }
                else
                {
                    Log.e("isphototakenELSE",istaken);
                }
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istaken;
    }
    public String isphototakenA(String regNo)
    {
        Cursor cur=null;
        String istaken="no";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db;
        //db = dataBaseHelper.getReadableDatabase();
        try {

            dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getReadableDatabase();
                cur = db.rawQuery("Select Status from InsertFarmer where RegNo='" + regNo + "' AND (Photo1 IS NOT NULL AND Status='A')", null);

            if (cur.moveToNext()) {


                if (!cur.isNull(0)) {
                    istaken="yes";

                }
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istaken;
    }
    public String isphototakenR(String regNo)
    {
        Cursor cur=null;
        String istaken="no";
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        SQLiteDatabase db;
        //db = dataBaseHelper.getReadableDatabase();
        try {

            dataBaseHelper = new DataBaseHelper(context);
            db = dataBaseHelper.getReadableDatabase();
            cur = db.rawQuery("Select Status from InsertFarmer where RegNo='" + regNo + "' AND (Photo1 IS NULL AND Status='R')", null);

            if (cur.moveToNext()) {


                if (!cur.isNull(0)) {
                    istaken="yes";

                }
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return istaken;
    }


    private ArrayList<FarmerDetails> getLocalPendingView(String panchayatid,String userid) {

        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<FarmerDetails> WorkList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT id,isBoringStarted,isStaggingDone,ProjectCompleteionDate FROM FarmerDetails WHERE CreatedBy='"+userid +"'  ORDER BY id DESC", null);

            if(!panchayatid.equalsIgnoreCase("")){

                cursor = db.rawQuery("Select FarmerName,FarmerFatherName,MobileNumber,PanchayatCode,RegistrationNO,User_ID from FarmerDetails where PanchayatCode= '" + panchayatid + "' and User_ID = '"+ userid.toLowerCase() +"' ", null);

            }
            else{
                cursor = db.rawQuery("Select FarmerName,FarmerFatherName,MobileNumber,PanchayatCode,RegistrationNO,User_ID from FarmerDetails where User_ID = '"+ userid.toLowerCase() +"'",null);
                //cur = db
                // .rawQuery("Select * from FarmerDetails where User_Id = 'bin755'",null);
            }


            while (cursor.moveToNext()) {
                FarmerDetails wi = new FarmerDetails();

                wi.setMobileNumber(cursor.getString(2));
                wi.setFarmerName(cursor.getString(0));
                wi.setFarmerFatherName(cursor.getString(1));
                wi.setRegistrationNO(cursor.getString(4));
                wi.setPanchayatCode(cursor.getString(3));
                wi.setUser_ID(cursor.getString(5));
                WorkList.add(wi);
            }
            Log.e("C COunt",""+cursor.getCount());
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return WorkList;
    }
    public void upDateEntries(ArrayList<FarmerDetails> entries) {

        mEntries = entries;
        notifyDataSetChanged();
    }

}
