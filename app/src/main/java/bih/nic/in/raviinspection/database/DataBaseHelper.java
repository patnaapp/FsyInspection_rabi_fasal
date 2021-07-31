package bih.nic.in.raviinspection.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import bih.nic.in.raviinspection.entity.Block;
import bih.nic.in.raviinspection.entity.Checklist;
import bih.nic.in.raviinspection.entity.District;
import bih.nic.in.raviinspection.entity.FarmerDetails;
import bih.nic.in.raviinspection.entity.Panchayat;
import bih.nic.in.raviinspection.entity.Remarks;
import bih.nic.in.raviinspection.entity.UserDetails;
import bih.nic.in.raviinspection.entity.Village;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static String DB_PATH = "";// "/data/data/com.bih.nic.app.biharmunicipalcorporation/databases/";
    private static String DB_NAME = "FsyInspection.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 4);
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {


            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist


        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            //this.getReadableDatabase();

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS
                            | SQLiteDatabase.OPEN_READWRITE);


        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;

    }

    public boolean databaseExist() {


        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        this.getReadableDatabase().close();

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }

    public void openDataBase() throws SQLException {

        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateNewTables(db);

    }

    public void CreateNewTables(SQLiteDatabase db)

    {



       db.execSQL("CREATE TABLE IF NOT EXISTS CheckList( ChkList_Id TEXT, Chklist_Nm TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e("oldVersion",""+oldVersion);
        Log.e("newVersion",""+newVersion);
        try {
            switch (oldVersion) {
                case 3:
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN RemarksCode TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN RemarksName TEXT");
                    db.execSQL("ALTER TABLE FarmerDetails ADD COLUMN Nibandhan_Sankhya TEXT");
                    db.execSQL("ALTER TABLE FarmerDetails ADD COLUMN Household_Id TEXT");

                   // db.execSQL("CREATE TABLE Districts( DistCode TEXT, DistName TEXT );");
                    //db.execSQL("CREATE TABLE Blocks( DistCode TEXT, BlockCode TEXT, BlockName TEXT );");

                    db.execSQL("ALTER TABLE Panchayat ADD COLUMN BlockCode TEXT");

                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN lpc_rltd_chk_Id TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN lpc_awedn_chk_Id TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN ghosit_fasal_khti_Id TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN aawedan_ghosit_rakwa_Id TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN aawedak_one_family_Id TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN et_aawedan_ghosit_rakwa TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN et_aawedan_ghosit_rakwa_two TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN Cheak TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN aawedak_present TEXT");
                    db.execSQL("ALTER TABLE InsertFarmer ADD COLUMN aawedak_absent TEXT");


                    //db.execSQL("ALTER TABLE FarmerDetails ADD COLUMN Nibandhan_Sankhya TEXT,Household_Id TEXT");

                    //db.execSQL("CREATE TABLE Remarks( RemarkCode TEXT, RemarkName TEXT );");

                    break;


                case 4:

                    db.execSQL("CREATE TABLE IF NOT EXISTS CheckList( ChkList_Id TEXT, Chklist_Nm TEXT );");
                    db.execSQL("CREATE TABLE IF NOT EXISTS Districts( DistCode TEXT, DistName TEXT );");
                    db.execSQL("CREATE TABLE IF NOT EXISTS Blocks( DistCode TEXT, BlockCode TEXT, BlockName TEXT );");

                    //db.execSQL("ALTER TABLE Panchayat ADD COLUMN BlockCode TEXT");
                    break;
            }
        }
        catch(Exception e)
            {
                Log.e("EXCEPTION", e.getLocalizedMessage());
                Log.e("EXCEPTION", e.getMessage());
            }




    }

    public UserDetails getUserDetails(String userId, String pass) {

        UserDetails userInfo = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{userId.trim(), pass};

            Cursor cur = db.rawQuery(
                    "Select * from UserDetail WHERE UserID=? and Password=?",params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                userInfo = new UserDetails();
                userInfo.setUserID(cur.getString(cur.getColumnIndex("UserID")));
                userInfo.setUserName(cur.getString(cur.getColumnIndex("UserName")));
                userInfo.setPassword(cur.getString(cur.getColumnIndex("Password")));
                userInfo.setIMEI(cur.getString(cur.getColumnIndex("IMEI")));
                userInfo.setRole(cur.getString(cur.getColumnIndex("Role")));
                userInfo.setDistCode(cur.getString(cur.getColumnIndex("DistCode")));
                userInfo.set_isAuthenticated(true);
                userInfo.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                userInfo.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
                userInfo.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));

            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            userInfo = null;
        }
        return userInfo;
    }

    public long insertUserDetails(UserDetails result) {

        long c = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("Delete from UserDetail");

            ContentValues values = new ContentValues();

            values.put("UserID", result.getUserID());
            values.put("UserName", result.getUserName());
            values.put("Password", result.getPassword());
            values.put("IMEI", result.getIMEI());
            values.put("Role", result.getRole());
            values.put("DistCode", result.getDistCode());
            values.put("DistName", result.getDistName());
            values.put("BlockCode", result.getBlockCode());
            values.put("BlockName", result.getBlockName());



//            String[] whereArgs = new String[]{result.getUserId()};
//
//           c = db.update("UserLogin", values, "UserId=? ", whereArgs);
//
//            if (!(c > 0)) {

            c = db.insert("UserDetail", null, values);
            //}

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;

    }
    public long getUserCount() {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from UserDetail", null);

            x = cur.getCount();

            cur.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return x;
    }

    public void insertPanchayat(ArrayList<Panchayat> result) {

        long c = -1;
        try {
            // CREATE TABLE "BankList" ( `BankId` TEXT, `BankName` TEXT, `BankType` TEXT )

            SQLiteDatabase db = this.getWritableDatabase();
           // db.delete("Panchayat",null,null);
            for (Panchayat panchayat : result) {

                ContentValues values = new ContentValues();
                values.put("UserId", panchayat.getUserid().toLowerCase());
                values.put("PanchayatCode", panchayat.getPanchayatCode());
                values.put("PanchayatName", panchayat.getPanchayatName());
                values.put("BlockCode", panchayat.getBlockCode());


                String[] whereArgs = new String[]{panchayat.getPanchayatCode()};
                c = db.update("Panchayat", values, "PanchayatCode=?", whereArgs);
                if (!(c > 0)) {

                    c = db.insert("Panchayat", null, values);
                }

            }
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        // return plantationList;
    }

    public void insertProvisional(ArrayList<Remarks> result) {

        long c = -1;
        try {
            // CREATE TABLE "BankList" ( `BankId` TEXT, `BankName` TEXT, `BankType` TEXT )

            SQLiteDatabase db = this.getWritableDatabase();
            // db.execSQL("Delete from RANGE");
            for (Remarks remarks : result) {

                ContentValues values = new ContentValues();
                values.put("RemarkCode", remarks.getRemarksCode());
                values.put("RemarkName", remarks.getRemarkName());

                String[] whereArgs = new String[]{remarks.getRemarksCode()};
                c = db.update("Remarks", values, "RemarkCode=?", whereArgs);
                if (!(c > 0)) {

                    c = db.insert("Remarks", null, values);
                }

            }
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        // return plantationList;
    }

//    public ArrayList<Panchayat> getPanchayatList(String userId) {
//
//        //String UserId;
//        //UserId= CommonPref.getUserDetails(myContext).getUserID();
//        ArrayList<Panchayat> datawardList = new ArrayList<Panchayat>();
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
////            Cursor cur = db
////                    .rawQuery(
////                            "SELECT PanchayatCode,PanchayatName from Panchayat WHERE UserId="+userId,null);
//            Cursor cur = db.rawQuery("SELECT * FROM  Panchayat where UserId='" + userId + "' order by PanchayatName", null);
//            int x = cur.getCount();
//            while (cur.moveToNext()) {
//                Panchayat ward = new Panchayat();
//                ward.setPanchayatCode(cur.getString(cur
//                        .getColumnIndex("PanchayatCode")));
//                ward.setPanchayatName(cur.getString(cur
//                        .getColumnIndex("PanchayatName")));
//                datawardList.add(ward);
//            }
//            cur.close();
//            db.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO: handle exception
//
//        }
//        return datawardList;
//
//    }
    public ArrayList<Panchayat> getPanchayatList(String userid,String BlockCode) {
        ArrayList<Panchayat> bdetail = new ArrayList<Panchayat>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT PanchayatCode,PanchayatName,UserId,BlockCode FROM  Panchayat where UserId='" + userid.trim().toLowerCase() + "' AND BlockCode='" + BlockCode + "'", null);
//            String[] params = new String[]{userid,BlockCode};
//            Cursor cur = db
//                    .rawQuery(
//                            "SELECT * from Panchayat WHERE UserId=? AND BlockCode=?", params);

            int x = cur.getCount();
            while (cur.moveToNext()) {
                Panchayat ps = new Panchayat();
                ps.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                ps.setPanchayatName((cur.getString(cur.getColumnIndex("PanchayatName"))));
                ps.setUserid((cur.getString(cur.getColumnIndex("UserId"))));
                ps.setBlockCode((cur.getString(cur.getColumnIndex("BlockCode"))));
                bdetail.add(ps);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bdetail;
    }
    public ArrayList<Panchayat> getPanchayatListwithblockid(String userid,String blockid) {
        ArrayList<Panchayat> bdetail = new ArrayList<Panchayat>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT PanchayatCode,PanchayatName,UserId,BlockCode FROM  Panchayat where UserId='" + userid.trim().toLowerCase() + "' AND BlockCode='" + blockid + "'", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Panchayat ps = new Panchayat();
                ps.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                ps.setPanchayatName((cur.getString(cur.getColumnIndex("PanchayatName"))));
                ps.setUserid((cur.getString(cur.getColumnIndex("UserId"))));
                ps.setBlockCode((cur.getString(cur.getColumnIndex("BlockCode"))));
                bdetail.add(ps);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }

    public long getpanchayatcount(String panchayatID,String UserId) {

        long x = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur=null;

                String[] whereArgs = new String[] {panchayatID,UserId };
                cur = db.rawQuery("Select * from FarmerDetails where PanchayatCode= '"+panchayatID.trim()+"' and User_ID= '"+UserId.trim().toLowerCase()+"'", null);

            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e)
        {

        }

        return x;
    }

    public long getVillagecount(String VillCode,String UserId,String PanCode) {

        long x = 0;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur=null;

            String[] whereArgs = new String[] {VillCode,UserId,PanCode };
            cur = db.rawQuery("Select * from FarmerDetails where PanchayatCode= '"+PanCode.trim()+"' and User_ID= '"+UserId.trim().toLowerCase()+"' AND VillageCode='"+VillCode.trim()+"'", null);

            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e)
        {

        }

        return x;
    }
    public void Datainsert(ArrayList<FarmerDetails> result,String panchayatcode,String userid) {
        long c = -1;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //db.delete("tablename","id=? and name=?",new String[]{"1","jack"});
          // long d= db.delete("FarmerDetails", "User_ID=? and PanchayatCode=?", new String[]{userid.trim(),panchayatcode.trim()});

            for (FarmerDetails state : result) {

                ContentValues values = new ContentValues();
                values.put("User_ID", userid.toLowerCase());
                values.put("Nibandhan_Sankhya", state.getNibandhanSankhya());
                values.put("Household_Id", state.getHouseholdid());
                values.put("DistCode", state.getDistCode());
                values.put("DistName", state.getDistName());
                values.put("BlockCode", state.getBlockCode());
                values.put("BlockName", state.getBlockName());
                values.put("PanchayatCode", panchayatcode.trim());
                values.put("PanchayatName", state.getPanchayatName());
                values.put("wardNo", state.getWardNo().trim());
                values.put("VillageCode", state.getVillageCode().trim());
                values.put("VILLNAME", state.getVILLNAME());
                values.put("FarmerName", state.getFarmerName());
                values.put("FarmerFatherName", state.getFarmerFatherName());
                values.put("Gender_Code", state.getGender_Code());
                values.put("Gender_Name", state.getGender_Name());
                values.put("Category_Code", state.getCategory_Code());
                values.put("Category_Name", state.getCategory_Name());
                values.put("MobileNumber", state.getMobileNumber());
                values.put("RegistrationNO", state.getRegistrationNO());
                values.put("TypeofFarmer", state.getTypeofFarmer());
                values.put("pacsMemberStatus", state.getPacsMemberStatus());
                values.put("AadharNo", state.getAadharNo());
                values.put("AadharName", state.getAadharName());
                values.put("BankName", state.getBankName());
                values.put("BankBranchName", state.getBankBranchName());
                values.put("IFSCcode", state.getIFSCcode());
                values.put("BankAccountNo", state.getBankAccountNo());
                values.put("CropName", state.getCropName());
                values.put("WheatherName", state.getWheatherName());
                values.put("IDProof", state.getIDProof());
                values.put("Residential", state.getResidential());
                values.put("Passbook", state.getPassbook());
                values.put("LPC", state.getLPC());
                values.put("SelfAttested", state.getSelfAttested());
                values.put("khataNo", state.getKhataNo());
                values.put("khasraNo", state.getKhasraNo());
                values.put("area", state.getArea());
                values.put("cropArea", state.getCropArea());
                values.put("FarmerPhotoPath", state.getFarmerPhotoPath());
                values.put("CropTypeKharif", state.getCropTypeKharif());
                values.put("CropTypeKharifName", state.getCropTypeKharifName());

                values.put("Gehu_raytti", state.getGehu_raytti());
                values.put("Gehu_gair_raytti", state.getGehu_gair_raytti());
                values.put("Makka_raytti", state.getMakka_raytti());
                values.put("Makka_gair_raytti", state.getMakka_gair_raytti());
                values.put("Chana_raytti", state.getChana_raytti());
                values.put("Chana_gair_raytti", state.getChana_gair_raytti());
                values.put("Masur_raytti", state.getMasur_raytti());
                values.put("Masur_gair_raytti", state.getMasur_gair_raytti());
                values.put("Arahar_raytti", state.getArahar_raytti());
                values.put("Arahar_gair_raytti", state.getArahar_gair_raytti());
                values.put("Rae_raytti", state.getRae_raytti());
                values.put("Rae_gair_raytti", state.getRae_gair_raytti());
                values.put("Ekh_raytti", state.getEkh_raytti());
                values.put("Ekh_gair_raytti", state.getEkh_gair_raytti());
                values.put("Pyaj_raytti", state.getPyaj_raytti());
                values.put("Pyaj_gair_raytti", state.getPyaj_gair_raytti());
                values.put("Aloo_raytti", state.getAloo_raytti());
                values.put("Aloo_gair_raytti", state.getAloo_gair_raytti());




                String[] whereArgs = new String[] { state.getRegistrationNO() };
                c = db.update("FarmerDetails", values, "RegistrationNO=?", whereArgs);
                if (c<=0) {

                    c = db.insert("FarmerDetails", null, values);

                }
                if (c>0) {

                    Log.d("PanchayatCode",state.getPanchayatCode());//success
                    Log.d("PanchayatName",state.getPanchayatName());//success
                    Log.d("VillageCode",state.getVillageCode());//success
                    Log.d("VILLNAME",state.getVILLNAME());//success

                }

                //boolean d=deleteReciihl(state.get_ApplicationId());
//                boolean d=deleteReciihl();
//                if(d)
//                {
//                    Log.d("True","d");//success
//                }
//                else
//                {
//                    Log.d("False","d");
//                    //failed
//                }

            }
            db.close();

        } catch (Exception e) {
            e.getMessage();
        }

    }

    public Panchayat getPanchayat() {
        Panchayat progress = new Panchayat();
        try {
            Cursor cur=null;
            SQLiteDatabase db = this.getReadableDatabase();

                cur = db.rawQuery(
                        "Select * from Panchayat", null);
            if (cur.moveToNext()) {
                progress = setProgressData(cur);
            }
            cur.close();
            db.close();

        }


        catch (Exception e) {
            // TODO: handle exception

        }

        return progress;

    }

    public Panchayat setProgressData(Cursor cur) {
        Panchayat progress = new Panchayat();
        progress.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
        progress.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
        progress.setUserid(cur.getString(cur.getColumnIndex("UserId")));
        return progress;
    }

    public ArrayList<FarmerDetails> getProgressList(String panchayatid,String userid) {
        ArrayList<FarmerDetails> progressList = new ArrayList<FarmerDetails>();
        try {
            Cursor cur=null;
            SQLiteDatabase db = this.getReadableDatabase();

            if(!panchayatid.equalsIgnoreCase("")){

                cur = db.rawQuery("Select FarmerName,FarmerFatherName,MobileNumber,PanchayatCode,RegistrationNO,User_ID from FarmerDetails where PanchayatCode= '" + panchayatid + "' and User_ID = '"+ userid.toLowerCase() +"' ", null);

            }
            else{
                cur = db.rawQuery("Select FarmerName,FarmerFatherName,MobileNumber,PanchayatCode,RegistrationNO,User_ID from FarmerDetails where User_ID = '"+ userid.toLowerCase() +"'",null);
            //cur = db
                   // .rawQuery("Select * from FarmerDetails where User_Id = 'bin755'",null);
           }

            int i =cur.getCount();
            while (cur.moveToNext()) {
                FarmerDetails progress = new FarmerDetails();
                progress.setFarmerName(cur.getString(cur.getColumnIndex("FarmerName")));
                progress.setFarmerFatherName(cur.getString(cur.getColumnIndex("FarmerFatherName")));
                progress.setMobileNumber(cur.getString(cur.getColumnIndex("MobileNumber")));
                progress.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                progress.setRegistrationNO(cur.getString(cur.getColumnIndex("RegistrationNO")));
                progress.setUser_ID(cur.isNull(cur.getColumnIndex("User_ID")) == false ? cur.getString(cur.getColumnIndex("User_ID")): "");
                progressList.add(progress);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return progressList;

    }
    public ArrayList<FarmerDetails> getProgressListPanVillWise(String panchayatID,String userID,String villID) {
        ArrayList<FarmerDetails> progressList = new ArrayList<FarmerDetails>();
        try {
            Cursor cur=null;
            SQLiteDatabase db = this.getReadableDatabase();

            if(!villID.equalsIgnoreCase("")){

                cur = db.rawQuery("Select FarmerName,FarmerFatherName,MobileNumber,PanchayatCode,RegistrationNO,User_ID from FarmerDetails where PanchayatCode= '" + panchayatID + "' and User_ID = '"+ userID.toLowerCase() +"' AND VillageCode='"+villID +"'", null);

            }
            else{
                cur = db.rawQuery("Select FarmerName,FarmerFatherName,MobileNumber,PanchayatCode,RegistrationNO,User_ID from FarmerDetails where User_ID = '"+ userID.toLowerCase() +"' AND PanchayatCode= '" + panchayatID + "'",null);
                //cur = db
                // .rawQuery("Select * from FarmerDetails where User_Id = 'bin755'",null);
            }

            int i =cur.getCount();
            Log.e("cur.getCount()",""+cur.getCount());
            while (cur.moveToNext()) {
                FarmerDetails progress = new FarmerDetails();
                progress.setFarmerName(cur.getString(cur.getColumnIndex("FarmerName")));
                progress.setFarmerFatherName(cur.getString(cur.getColumnIndex("FarmerFatherName")));
                progress.setMobileNumber(cur.getString(cur.getColumnIndex("MobileNumber")));
                progress.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                progress.setRegistrationNO(cur.getString(cur.getColumnIndex("RegistrationNO")));
                progress.setUser_ID(cur.isNull(cur.getColumnIndex("User_ID")) == false ? cur.getString(cur.getColumnIndex("User_ID")): "");
                progressList.add(progress);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return progressList;

    }
    public ArrayList<Panchayat> getpanchayatListOfUlbCode(String userID) {

       // String UlbCode = PreferenceManager.getDefaultSharedPreferences(myContext).getString("UlbCode", "");
        //UlbCode= CommonPref.getUserDetails(myContext).get_ULBCode();
        ArrayList<Panchayat> datawardList = new ArrayList<Panchayat>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db
                    .rawQuery(
                            "SELECT PanchayatCode,PanchayatName from Panchayat WHERE UserId="+userID, null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Panchayat ward = new Panchayat();
                ward.setPanchayatCode(cur.getString(cur
                        .getColumnIndex("PanchayatCode")));
                ward.setPanchayatName(cur.getString(cur
                        .getColumnIndex("PanchayatName")));
                ward.setUserid(cur.getString(cur
                        .getColumnIndex("UserId")));

                datawardList.add(ward);
            }
            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return datawardList;

    }

    public ArrayList<FarmerDetails> getSearcheDataList(String searchstr,String panchayatId) {
        ArrayList<FarmerDetails> progressList = new ArrayList<FarmerDetails>();
        try {
            Cursor cur=null;
            SQLiteDatabase db = this.getReadableDatabase();
//CREATE TABLE "RecordList" ( `UlbId` TEXT, `wardId` TEXT, `ApplicantId` TEXT, `AopplicationId` TEXT, `FatherName` TEXT, `Address` TEXT,
// `ContactNo` TEXT, `currentstatus` TEXT, `Name` TEXT, `SchemeId` TEXT, `Field11` INTEGER, PRIMARY KEY(`AopplicationId`) )
            if(!panchayatId.equalsIgnoreCase("0")){

//                cur = db.rawQuery("Select * from RecordList where ApplicantId= '%" + searchstr + "%' and CurrentStatus = 'N'", null);
                cur = db.rawQuery("Select MobileNumber,FarmerName,FarmerFatherName,PanchayatCode,RegistrationNO,User_ID from FarmerDetails where PanchayatCode = '" + panchayatId+"' AND (MobileNumber LIKE '%" + searchstr + "%' OR FarmerName LIKE '%"+ searchstr+"%')", null);
                // cur = db.rawQuery("Select * from RecordList where wardId = '" + wardId+"' AND Name LIKE '%"+ searchstr+"%' and CurrentStatus = 'N'", null);

            }
            else  if(panchayatId.equalsIgnoreCase("0")){

//                cur = db.rawQuery("Select * from RecordList where ApplicantId= '%" + searchstr + "%' and CurrentStatus = 'N'", null);
                cur = db.rawQuery("Select MobileNumber,FarmerName,FarmerFatherName,PanchayatCode,RegistrationNO,User_ID from FarmerDetails where(MobileNumber LIKE '%" + searchstr + "%' OR FarmerName LIKE '%"+ searchstr+"%')", null);

            }
            else{
                cur = db
                        .rawQuery("Select MobileNumber,FarmerName,FarmerFatherName,PanchayatCode,RegistrationNO,User_ID from FarmerDetails",null);
            }

            int i =cur.getCount();
            while (cur.moveToNext()) {
                FarmerDetails progress = new FarmerDetails();
                progress.setMobileNumber(cur.getString(cur.getColumnIndex("MobileNumber")));
                progress.setFarmerName(cur.getString(cur.getColumnIndex("FarmerName")));
                progress.setFarmerFatherName(cur.getString(cur.getColumnIndex("FarmerFatherName")));
                progress.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                progress.setRegistrationNO(cur.getString(cur.getColumnIndex("RegistrationNO")));
                progress.setUser_ID(cur.isNull(cur.getColumnIndex("User_ID")) == false ? cur.getString(cur.getColumnIndex("User_ID")): "");
                progressList.add(progress);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception

        }
        return progressList;

    }
    public FarmerDetails getEditabeldata(String userid) {
        FarmerDetails progress = new FarmerDetails();
        try {
            Cursor cur=null;
            SQLiteDatabase db = this.getReadableDatabase();

                cur = db.rawQuery(
                        "Select * from FarmerDetails where RegistrationNO='" + userid + "'", null);
            if (cur.moveToNext()) {
                progress = setProgressData_all(cur);
            }
            cur.close();
            db.close();

        }


        catch (Exception e) {
            // TODO: handle exception

        }

        return progress;

    }

    public FarmerDetails setProgressData_all(Cursor cur) {
        FarmerDetails progress = new FarmerDetails();
        progress.setRegistrationNO(cur.getString(cur.getColumnIndex("RegistrationNO")));
        progress.setNibandhanSankhya(cur.getString(cur.getColumnIndex("Nibandhan_Sankhya")));
        progress.setHouseholdid(cur.getString(cur.getColumnIndex("Household_Id")));
        progress.setUser_ID(cur.getString(cur.getColumnIndex("User_ID")));
        progress.setDistCode(cur.getString(cur.getColumnIndex("DistCode")));
        progress.setDistName(cur.getString(cur.getColumnIndex("DistName")));
        progress.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
        progress.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
        progress.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
        progress.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
        progress.setWardNo(cur.getString(cur.getColumnIndex("wardNo")));
        progress.setVillageCode(cur.getString(cur.getColumnIndex("VillageCode")));
        progress.setVILLNAME(cur.getString(cur.getColumnIndex("VILLNAME")));
        progress.setTypeofFarmer(cur.getString(cur.getColumnIndex("TypeofFarmer")));
        progress.setFarmerName(cur.getString(cur.getColumnIndex("FarmerName")));
        progress.setFarmerFatherName(cur.getString(cur.getColumnIndex("FarmerFatherName")));
        progress.setMobileNumber(cur.getString(cur.getColumnIndex("MobileNumber")));
        progress.setGender_Code(cur.getString(cur.getColumnIndex("Gender_Code")));
        progress.setGender_Name(cur.getString(cur.getColumnIndex("Gender_Name")));
        progress.setCategory_Code(cur.getString(cur.getColumnIndex("Category_Code")));
        progress.setCategory_Name(cur.getString(cur.getColumnIndex("Category_Name")));
        progress.setAadharNo(cur.getString(cur.getColumnIndex("AadharNo")));
        progress.setAadharName(cur.getString(cur.getColumnIndex("AadharName")));
        progress.setPacsMemberStatus(cur.getString(cur.getColumnIndex("pacsMemberStatus")));
        progress.setBankName(cur.getString(cur.getColumnIndex("BankName")));
        progress.setBankBranchName(cur.getString(cur.getColumnIndex("BankBranchName")));
        progress.setIFSCcode(cur.getString(cur.getColumnIndex("IFSCcode")));
        progress.setBankAccountNo(cur.getString(cur.getColumnIndex("BankAccountNo")));
        progress.setWheatherName(cur.getString(cur.getColumnIndex("WheatherName")));
        progress.setCropName(cur.getString(cur.getColumnIndex("CropName")));
        progress.setKhataNo(cur.getString(cur.getColumnIndex("khataNo")));
        progress.setKhasraNo(cur.getString(cur.getColumnIndex("khasraNo")));
        progress.setArea(cur.getString(cur.getColumnIndex("area")));
        progress.setCropArea(cur.getString(cur.getColumnIndex("cropArea")));
        progress.setIDProof(cur.getString(cur.getColumnIndex("IDProof")));
        progress.setResidential(cur.getString(cur.getColumnIndex("Residential")));
        progress.setPassbook(cur.getString(cur.getColumnIndex("Passbook")));
        progress.setLPC(cur.getString(cur.getColumnIndex("LPC")));
        progress.setSelfAttested(cur.getString(cur.getColumnIndex("SelfAttested")));
        progress.setFarmerPhotoPath(cur.getString(cur.getColumnIndex("FarmerPhotoPath")));
        progress.setISCOnsumbernumberExist(cur.getString(cur.getColumnIndex("ConsumberNumberPresent")));
        progress.setCropTypeKharif(cur.getString(cur.getColumnIndex("CropTypeKharif")));
        progress.setCropTypeKharifName(cur.getString(cur.getColumnIndex("CropTypeKharifName")));

        progress.setGehu_raytti(cur.getString(cur.getColumnIndex("Gehu_raytti")));
        progress.setGehu_gair_raytti(cur.getString(cur.getColumnIndex("Gehu_gair_raytti")));
        progress.setMakka_raytti(cur.getString(cur.getColumnIndex("Makka_raytti")));
        progress.setMakka_gair_raytti(cur.getString(cur.getColumnIndex("Makka_gair_raytti")));
        progress.setChana_raytti(cur.getString(cur.getColumnIndex("Chana_raytti")));
        progress.setChana_gair_raytti(cur.getString(cur.getColumnIndex("Chana_gair_raytti")));
        progress.setMasur_raytti(cur.getString(cur.getColumnIndex("Masur_raytti")));
        progress.setMasur_gair_raytti(cur.getString(cur.getColumnIndex("Masur_gair_raytti")));
        progress.setArahar_raytti(cur.getString(cur.getColumnIndex("Arahar_raytti")));
        progress.setArahar_gair_raytti(cur.getString(cur.getColumnIndex("Arahar_gair_raytti")));
        progress.setRae_raytti(cur.getString(cur.getColumnIndex("Rae_raytti")));
        progress.setRae_gair_raytti(cur.getString(cur.getColumnIndex("Rae_gair_raytti")));
        progress.setEkh_raytti(cur.getString(cur.getColumnIndex("Ekh_raytti")));
        progress.setEkh_gair_raytti(cur.getString(cur.getColumnIndex("Ekh_gair_raytti")));
        progress.setPyaj_raytti(cur.getString(cur.getColumnIndex("Pyaj_raytti")));
        progress.setPyaj_gair_raytti(cur.getString(cur.getColumnIndex("Pyaj_gair_raytti")));
        progress.setAloo_raytti(cur.getString(cur.getColumnIndex("Aloo_raytti")));
        progress.setAloo_gair_raytti(cur.getString(cur.getColumnIndex("Aloo_gair_raytti")));


        return progress;
    }
    public long getUploadCommunityCount() {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from InsertFarmer", null);
            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e)
        {
            e.getMessage();
        }

        return x;
    }
    public List<FarmerDetails> getAllDataListCommunityData() {
        List<FarmerDetails> dataList = new ArrayList<FarmerDetails>();
        try{
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select RegNo,Status,Summary,RemarksCode,RemarksName,User_Id,Latitude,Longitude,Pic1landLat,Pic1landLong,Pic2landLat,Pic2landLong,aawedak_present,aawedak_absent,lpc_rltd_chk_Id,lpc_awedn_chk_Id,ghosit_fasal_khti_Id,aawedan_ghosit_rakwa_Id,aawedak_one_family_Id,gehu_rayti,makka_rayti,chana_rayti,masur_rayti,arahar_rayti,rae_rayti,ekh_rayti,pyaj_rayti,aloo_rayti,gehu_gair_rayti,makka_gair_rayti,chana_gair_rayti,masur_gair_rayti,arahar_gair_rayti,rae_gair_rayti,ekh_gair_rayti,pyaj_gair_rayti,aloo_gair_rayti,swaghosana_sambandhit_id,swaghosana_sambandhit_nm,swaghosana_signer_name from InsertFarmer ", null);

        if (cursor.moveToFirst()) {
           // do {
                FarmerDetails data = new FarmerDetails();
            data.setTypeofFarmer(cursor.getString(cursor.getColumnIndex("TypeofFarmer")));
                data.setRegistrationNO(cursor.getString(cursor.getColumnIndex("RegNo")));
                data.setStatus(cursor.getString(cursor.getColumnIndex("Status")));
                data.setSummary(cursor.getString(cursor.getColumnIndex("Summary")));
                data.setProvisionId(cursor.getString(cursor.getColumnIndex("RemarksCode")));
                data.setProvisionName(cursor.getString(cursor.getColumnIndex("RemarksName")));
                data.setUser_ID(cursor.getString(cursor.getColumnIndex("User_Id")));
                data.setLatitude(cursor.getString(cursor.getColumnIndex("Latitude")));
                data.setLongitude(cursor.getString(cursor.getColumnIndex("Longitude")));
                data.setPic1landLat(cursor.getString(cursor.getColumnIndex("Pic1landLat")));
                data.setPic1landLong(cursor.getString(cursor.getColumnIndex("Pic1landLong")));
                data.setPic2landLat(cursor.getString(cursor.getColumnIndex("Pic2landLat")));
                data.setPic2landLong(cursor.getString(cursor.getColumnIndex("Pic2landLong")));

                //data.setRashanCardNum(cursor.getString(cursor.getColumnIndex("RashanCardNumber")));


//                data.setEt_fieldwheat(cursor.getString(cursor.getColumnIndex("wheatfield")));
//                data.setEt_field_makka(cursor.getString(cursor.getColumnIndex("makkafield")));
//                data.setEt_masoor(cursor.getString(cursor.getColumnIndex("masoorfield")));
//                data.setEt_arhar(cursor.getString(cursor.getColumnIndex("arharfield")));
//                data.setEt_sugarcane(cursor.getString(cursor.getColumnIndex("sugarcanefield")));
//                data.setEt_potato(cursor.getString(cursor.getColumnIndex("potatofield")));
//                data.setEt_raisarso(cursor.getString(cursor.getColumnIndex("raisarsofield")));
//                data.setEt_onion(cursor.getString(cursor.getColumnIndex("onionfield")));
//                data.setEt_chana(cursor.getString(cursor.getColumnIndex("chanafield")));
//                data.setElectricity(cursor.getString(cursor.getColumnIndex("electricity")));

            data.setElectricity(cursor.getString(cursor.getColumnIndex("electricity")));
            data.setElectric_avail_id(cursor.getString(cursor.getColumnIndex("electric_avail_Id")));
            data.setElectric_avail_nm(cursor.getString(cursor.getColumnIndex("electric_avail_Nm")));
            data.setElectric_id(cursor.getString(cursor.getColumnIndex("electric_Id")));
            data.setElectric_nm(cursor.getString(cursor.getColumnIndex("electric_Nm")));

            data.setSwaghona_upload(cursor.getString(cursor.getColumnIndex("swaghoshana_upload_Id")));//swaghona upload hai
            data.setSwaghona_patra_aawedakrta(cursor.getString(cursor.getColumnIndex("swaghoshana_aawedek_name_Id")));//swaghona patra aawedakrta ke naam se hai

                data.setAawedak_accept(cursor.getString(cursor.getColumnIndex("aawedak_present")));
                data.setAawedak_reject(cursor.getString(cursor.getColumnIndex("aawedak_absent")));
                data.setLpc_rltd_chk_Id(cursor.getString(cursor.getColumnIndex("lpc_rltd_chk_Id")));
                data.setLpc_awedn_chk_Id(cursor.getString(cursor.getColumnIndex("lpc_awedn_chk_Id")));
                data.setGhosit_fasal_khti_Id(cursor.getString(cursor.getColumnIndex("ghosit_fasal_khti_Id")));
                data.setAawedan_ghosit_rakwa_Id(cursor.getString(cursor.getColumnIndex("aawedan_ghosit_rakwa_Id")));
                data.setAawedak_one_family_Id(cursor.getString(cursor.getColumnIndex("aawedak_one_family_Id")));
                //data.setEt_aawedan_ghosit_rakwa(cursor.getString(cursor.getColumnIndex("et_aawedan_ghosit_rakwa")));
                //data.setEt_aawedan_ghosit_rakwa_two(cursor.getString(cursor.getColumnIndex("et_aawedan_ghosit_rakwa_two")));

                data.setGehu_raytti(cursor.getString(cursor.getColumnIndex("gehu_rayti")));
                data.setMakka_raytti(cursor.getString(cursor.getColumnIndex("makka_rayti")));
                data.setChana_raytti(cursor.getString(cursor.getColumnIndex("chana_rayti")));
                data.setMasur_raytti(cursor.getString(cursor.getColumnIndex("masur_rayti")));
                data.setArahar_raytti(cursor.getString(cursor.getColumnIndex("arahar_rayti")));
                data.setRae_raytti(cursor.getString(cursor.getColumnIndex("rae_rayti")));
                data.setEkh_raytti(cursor.getString(cursor.getColumnIndex("ekh_rayti")));
                data.setPyaj_raytti(cursor.getString(cursor.getColumnIndex("pyaj_rayti")));
                data.setAloo_raytti(cursor.getString(cursor.getColumnIndex("aloo_rayti")));

                data.setGehu_gair_raytti(cursor.getString(cursor.getColumnIndex("gehu_gair_rayti")));
                data.setMakka_gair_raytti(cursor.getString(cursor.getColumnIndex("makka_gair_rayti")));
                data.setChana_gair_raytti(cursor.getString(cursor.getColumnIndex("chana_gair_rayti")));
                data.setMasur_gair_raytti(cursor.getString(cursor.getColumnIndex("masur_gair_rayti")));
                data.setArahar_gair_raytti(cursor.getString(cursor.getColumnIndex("arahar_gair_rayti")));
                data.setRae_gair_raytti(cursor.getString(cursor.getColumnIndex("rae_gair_rayti")));
                data.setEkh_gair_raytti(cursor.getString(cursor.getColumnIndex("ekh_gair_rayti")));
                data.setPyaj_gair_raytti(cursor.getString(cursor.getColumnIndex("pyaj_gair_rayti")));
                data.setAloo_gair_raytti(cursor.getString(cursor.getColumnIndex("aloo_gair_rayti")));

                data.setSwaghosana_sambandhit_id(cursor.getString(cursor.getColumnIndex("swaghosana_sambandhit_id")));
                data.setSwaghosana_sambandhit_Nm(cursor.getString(cursor.getColumnIndex("swaghosana_sambandhit_nm")));
                data.setSwaghosana_sambandhit_signer_nm(cursor.getString(cursor.getColumnIndex("swaghosana_signer_name")));
                data.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                data.setAawedan_karta_Id(cursor.getString(cursor.getColumnIndex("aawedan_karta_Id")));
                data.setAawedan_karta_Nm(cursor.getString(cursor.getColumnIndex("aawedan_karta_Nm")));

                data.setIs_gehu(cursor.getString(cursor.getColumnIndex("Is_genhu")));
                data.setIs_chana(cursor.getString(cursor.getColumnIndex("Is_chana")));
                data.setIs_makka(cursor.getString(cursor.getColumnIndex("Is_makka")));
                data.setIs_arahar(cursor.getString(cursor.getColumnIndex("Is_arhar")));
                data.setIs_masur(cursor.getString(cursor.getColumnIndex("Is_masur")));
                data.setIs_rai(cursor.getString(cursor.getColumnIndex("Is_rai")));
                data.setIs_ikha(cursor.getString(cursor.getColumnIndex("Is_ikha")));
                data.setIs_onion(cursor.getString(cursor.getColumnIndex("Is_onion")));
                data.setIs_potato(cursor.getString(cursor.getColumnIndex("Is_potato")));

               // data.setPhoto1(cursor.isNull(cursor.getColumnIndex("Photo1")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo1")), Base64.NO_WRAP) : "");
                //data.setPhoto2(cursor.isNull(cursor.getColumnIndex("Photo2")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo2")), Base64.NO_WRAP) : "");
                //data.setPhoto3(cursor.isNull(cursor.getColumnIndex("Photo3")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo3")), Base64.NO_WRAP) : "");
                //data.setPhoto4(cursor.isNull(cursor.getColumnIndex("Photo4")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo4")), Base64.NO_WRAP) : "");
               // data.setPhoto5(cursor.isNull(cursor.getColumnIndex("Photo5")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo5")), Base64.NO_WRAP) : "");

                //String[] args2 = {rowID};
                //String selectSQL = "select Photo1,Photo2 From InsertFarmer ORDER BY Id  DESC";
                //Cursor cursor1 = db.rawQuery();
                Cursor cursor1 = db.rawQuery("Select Photo1,Photo2 from InsertFarmer ", null);
                while (cursor1.moveToNext()) {
                    data.setPhoto1(cursor1.isNull(cursor1.getColumnIndex("Photo1")) == false ? Base64.encodeToString(cursor1.getBlob(cursor1.getColumnIndex("Photo1")), Base64.NO_WRAP) : "");
                    data.setPhoto2(cursor1.isNull(cursor1.getColumnIndex("Photo2")) == false ? Base64.encodeToString(cursor1.getBlob(cursor1.getColumnIndex("Photo2")), Base64.NO_WRAP) : "");

                }

                //basicdetail.add(basicInfo);
                cursor1.close();

                Cursor cursor2 = db.rawQuery("Select Photo3,Photo4 from InsertFarmer ", null);
                while (cursor2.moveToNext()) {

                    data.setPhoto3(cursor2.isNull(cursor2.getColumnIndex("Photo3")) == false ? Base64.encodeToString(cursor2.getBlob(cursor2.getColumnIndex("Photo3")), Base64.NO_WRAP) : "");
                    data.setPhoto4(cursor2.isNull(cursor2.getColumnIndex("Photo4")) == false ? Base64.encodeToString(cursor2.getBlob(cursor2.getColumnIndex("Photo4")), Base64.NO_WRAP) : "");
                }

                cursor2.close();

                Cursor cursor3 = db.rawQuery("Select Photo5 from InsertFarmer ", null);
                while (cursor3.moveToNext()) {

                    data.setPhoto5(cursor3.isNull(cursor3.getColumnIndex("Photo5")) == false ? Base64.encodeToString(cursor3.getBlob(cursor3.getColumnIndex("Photo5")), Base64.NO_WRAP) : "");

                }

                dataList.add(data);
                cursor3.close();


                //dataList.add(data);
//            }
//            while (cursor.moveToNext());
        }
        cursor.close();
        this.getReadableDatabase().close();
        db.close();
        } catch (Exception e) {
            e.printStackTrace();
            dataList = null;
            // TODO: handle exception

        }
        return dataList;
    }
    public List<FarmerDetails> getThisDataListCommunityData(String regnum) {
        List<FarmerDetails> dataList = new ArrayList<FarmerDetails>();
        try{
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery("Select * from InsertFarmer WHERE RegNo='"+regnum +"'", null);
            //Cursor cursor = db.rawQuery("Select RegNo,Status,Summary,RemarksCode,RemarksName,User_Id,Latitude,Longitude,Pic1landLat,Pic1landLong,Pic2landLat,Pic2landLong,aawedak_present,aawedak_absent,lpc_rltd_chk_Id,lpc_awedn_chk_Id,ghosit_fasal_khti_Id,aawedan_ghosit_rakwa_Id,aawedak_one_family_Id,gehu_rayti,makka_rayti,chana_rayti,masur_rayti,arahar_rayti,rae_rayti,ekh_rayti,pyaj_rayti,aloo_rayti,gehu_gair_rayti,makka_gair_rayti,chana_gair_rayti,masur_gair_rayti,arahar_gair_rayti,rae_gair_rayti,ekh_gair_rayti,pyaj_gair_rayti,aloo_gair_rayti,swaghosana_sambandhit_id,swaghosana_sambandhit_nm,swaghosana_signer_name from InsertFarmer WHERE RegNo='"+regnum +"'", null);
            Cursor cursor = db.rawQuery("Select * from InsertFarmer WHERE RegNo='"+regnum +"'", null);

        if (cursor.moveToFirst()) {
          //  do {
                FarmerDetails data = new FarmerDetails();
                data.setTypeofFarmer(cursor.getString(cursor.getColumnIndex("TypeofFarmer")));
                data.setRegistrationNO(cursor.getString(cursor.getColumnIndex("RegNo")));
                data.setStatus(cursor.getString(cursor.getColumnIndex("Status")));
                data.setSummary(cursor.getString(cursor.getColumnIndex("Summary")));
                data.setProvisionId(cursor.getString(cursor.getColumnIndex("RemarksCode")));
                data.setProvisionName(cursor.getString(cursor.getColumnIndex("RemarksName")));
                data.setUser_ID(cursor.getString(cursor.getColumnIndex("User_Id")));
                data.setLatitude(cursor.getString(cursor.getColumnIndex("Latitude")));
                data.setLongitude(cursor.getString(cursor.getColumnIndex("Longitude")));
                data.setPic1landLat(cursor.getString(cursor.getColumnIndex("Pic1landLat")));
                data.setPic1landLong(cursor.getString(cursor.getColumnIndex("Pic1landLong")));
                data.setPic2landLat(cursor.getString(cursor.getColumnIndex("Pic2landLat")));
                data.setPic2landLong(cursor.getString(cursor.getColumnIndex("Pic2landLong")));

                //data.setRashanCardNum(cursor.getString(cursor.getColumnIndex("RashanCardNumber")));


//                data.setEt_fieldwheat(cursor.getString(cursor.getColumnIndex("wheatfield")));
//                data.setEt_field_makka(cursor.getString(cursor.getColumnIndex("makkafield")));
//                data.setEt_masoor(cursor.getString(cursor.getColumnIndex("masoorfield")));
//                data.setEt_arhar(cursor.getString(cursor.getColumnIndex("arharfield")));
//                data.setEt_sugarcane(cursor.getString(cursor.getColumnIndex("sugarcanefield")));
//                data.setEt_potato(cursor.getString(cursor.getColumnIndex("potatofield")));
//                data.setEt_raisarso(cursor.getString(cursor.getColumnIndex("raisarsofield")));
//                data.setEt_onion(cursor.getString(cursor.getColumnIndex("onionfield")));
//                data.setEt_chana(cursor.getString(cursor.getColumnIndex("chanafield")));

                data.setElectricity(cursor.getString(cursor.getColumnIndex("electricity")));
                data.setElectric_avail_id(cursor.getString(cursor.getColumnIndex("electric_avail_Id")));
                data.setElectric_avail_nm(cursor.getString(cursor.getColumnIndex("electric_avail_Nm")));
                data.setElectric_id(cursor.getString(cursor.getColumnIndex("electric_Id")));
                data.setElectric_nm(cursor.getString(cursor.getColumnIndex("electric_Nm")));

                data.setSwaghona_upload(cursor.getString(cursor.getColumnIndex("swaghoshana_upload_Id")));//swaghona upload hai
                data.setSwaghona_patra_aawedakrta(cursor.getString(cursor.getColumnIndex("swaghoshana_aawedek_name_Id")));//swaghona patra aawedakrta ke naam se hai



                data.setAawedak_accept(cursor.getString(cursor.getColumnIndex("aawedak_present")));
                data.setAawedak_reject(cursor.getString(cursor.getColumnIndex("aawedak_absent")));
                data.setLpc_rltd_chk_Id(cursor.getString(cursor.getColumnIndex("lpc_rltd_chk_Id")));
                data.setLpc_awedn_chk_Id(cursor.getString(cursor.getColumnIndex("lpc_awedn_chk_Id")));
                data.setGhosit_fasal_khti_Id(cursor.getString(cursor.getColumnIndex("ghosit_fasal_khti_Id")));
                data.setAawedan_ghosit_rakwa_Id(cursor.getString(cursor.getColumnIndex("aawedan_ghosit_rakwa_Id")));
                data.setAawedak_one_family_Id(cursor.getString(cursor.getColumnIndex("aawedak_one_family_Id")));

                data.setGehu_raytti(cursor.getString(cursor.getColumnIndex("gehu_rayti")));
                data.setMakka_raytti(cursor.getString(cursor.getColumnIndex("makka_rayti")));
                data.setChana_raytti(cursor.getString(cursor.getColumnIndex("chana_rayti")));
                data.setMasur_raytti(cursor.getString(cursor.getColumnIndex("masur_rayti")));
                data.setArahar_raytti(cursor.getString(cursor.getColumnIndex("arahar_rayti")));
                data.setRae_raytti(cursor.getString(cursor.getColumnIndex("rae_rayti")));
                data.setEkh_raytti(cursor.getString(cursor.getColumnIndex("ekh_rayti")));
                data.setPyaj_raytti(cursor.getString(cursor.getColumnIndex("pyaj_rayti")));
                data.setAloo_raytti(cursor.getString(cursor.getColumnIndex("aloo_rayti")));

                data.setGehu_gair_raytti(cursor.getString(cursor.getColumnIndex("gehu_gair_rayti")));
                data.setMakka_gair_raytti(cursor.getString(cursor.getColumnIndex("makka_gair_rayti")));
                data.setChana_gair_raytti(cursor.getString(cursor.getColumnIndex("chana_gair_rayti")));
                data.setMasur_gair_raytti(cursor.getString(cursor.getColumnIndex("masur_gair_rayti")));
                data.setArahar_gair_raytti(cursor.getString(cursor.getColumnIndex("arahar_gair_rayti")));
                data.setRae_gair_raytti(cursor.getString(cursor.getColumnIndex("rae_gair_rayti")));
                data.setEkh_gair_raytti(cursor.getString(cursor.getColumnIndex("ekh_gair_rayti")));
                data.setPyaj_gair_raytti(cursor.getString(cursor.getColumnIndex("pyaj_gair_rayti")));
                data.setAloo_gair_raytti(cursor.getString(cursor.getColumnIndex("aloo_gair_rayti")));

                data.setSwaghosana_sambandhit_id(cursor.getString(cursor.getColumnIndex("swaghosana_sambandhit_id")));
                data.setSwaghosana_sambandhit_Nm(cursor.getString(cursor.getColumnIndex("swaghosana_sambandhit_nm")));
                data.setSwaghosana_sambandhit_signer_nm(cursor.getString(cursor.getColumnIndex("swaghosana_signer_name")));
            data.setDate(cursor.getString(cursor.getColumnIndex("Date")));
            data.setAawedan_karta_Id(cursor.getString(cursor.getColumnIndex("aawedan_karta_Id")));
            data.setAawedan_karta_Nm(cursor.getString(cursor.getColumnIndex("aawedan_karta_Nm")));


            data.setIs_gehu(cursor.getString(cursor.getColumnIndex("Is_genhu")));
            data.setIs_chana(cursor.getString(cursor.getColumnIndex("Is_chana")));
            data.setIs_makka(cursor.getString(cursor.getColumnIndex("Is_makka")));
            data.setIs_arahar(cursor.getString(cursor.getColumnIndex("Is_arhar")));
            data.setIs_masur(cursor.getString(cursor.getColumnIndex("Is_masur")));
            data.setIs_rai(cursor.getString(cursor.getColumnIndex("Is_rai")));
            data.setIs_ikha(cursor.getString(cursor.getColumnIndex("Is_ikha")));
            data.setIs_onion(cursor.getString(cursor.getColumnIndex("Is_onion")));
            data.setIs_potato(cursor.getString(cursor.getColumnIndex("Is_potato")));

                //data.setEt_aawedan_ghosit_rakwa(cursor.getString(cursor.getColumnIndex("et_aawedan_ghosit_rakwa")));
                //data.setEt_aawedan_ghosit_rakwa_two(cursor.getString(cursor.getColumnIndex("et_aawedan_ghosit_rakwa_two")));


                //data.setPhoto1(cursor.isNull(cursor.getColumnIndex("Photo1")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo1")), Base64.NO_WRAP) : "");
                //data.setPhoto2(cursor.isNull(cursor.getColumnIndex("Photo2")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo2")), Base64.NO_WRAP) : "");
                //data.setPhoto3(cursor.isNull(cursor.getColumnIndex("Photo3")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo3")), Base64.NO_WRAP) : "");
                //data.setPhoto4(cursor.isNull(cursor.getColumnIndex("Photo4")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo4")), Base64.NO_WRAP) : "");
                //data.setPhoto5(cursor.isNull(cursor.getColumnIndex("Photo5")) == false ? Base64.encodeToString(cursor.getBlob(cursor.getColumnIndex("Photo5")), Base64.NO_WRAP) : "");

                String[] args2 = {regnum};
                String selectSQL = "select Photo1,Photo2 From InsertFarmer where RegNo=?";
                Cursor cursor1 = db.rawQuery(selectSQL, args2);

                while (cursor1.moveToNext()) {
                    data.setPhoto1(cursor1.isNull(cursor1.getColumnIndex("Photo1")) == false ? Base64.encodeToString(cursor1.getBlob(cursor1.getColumnIndex("Photo1")), Base64.NO_WRAP) : "");
                    data.setPhoto2(cursor1.isNull(cursor1.getColumnIndex("Photo2")) == false ? Base64.encodeToString(cursor1.getBlob(cursor1.getColumnIndex("Photo2")), Base64.NO_WRAP) : "");

                }

                //basicdetail.add(basicInfo);
                cursor1.close();

                String[] args3 = {regnum};
                String selectSQL1 = "select Photo3,Photo4 From InsertFarmer where RegNo=?";
                Cursor cursor2 = db.rawQuery(selectSQL1, args3);
                while (cursor2.moveToNext()) {


                    data.setPhoto3(cursor2.isNull(cursor2.getColumnIndex("Photo3")) == false ? Base64.encodeToString(cursor2.getBlob(cursor2.getColumnIndex("Photo3")), Base64.NO_WRAP) : "");
                    data.setPhoto4(cursor2.isNull(cursor2.getColumnIndex("Photo4")) == false ? Base64.encodeToString(cursor2.getBlob(cursor2.getColumnIndex("Photo4")), Base64.NO_WRAP) : "");
                }

                cursor2.close();

                String[] args4 = {regnum};
                String selectSQL2 = "select Photo5 From InsertFarmer where RegNo=?";
                Cursor cursor3 = db.rawQuery(selectSQL2, args4);
                while (cursor3.moveToNext()) {

                    data.setPhoto5(cursor3.isNull(cursor3.getColumnIndex("Photo5")) == false ? Base64.encodeToString(cursor3.getBlob(cursor3.getColumnIndex("Photo5")), Base64.NO_WRAP) : "");


                }

                dataList.add(data);
                cursor3.close();



                //dataList.add(data);
           // } while (cursor.moveToNext());
        }
        cursor.close();
        this.getReadableDatabase().close();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
        dataList = null;
        // TODO: handle exception

    }

        return dataList;
    }
    public boolean deleterowCommunity1(String id) {
        //commensed phase
        String[] whereArgs = new String[] {id };
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("InsertFarmer", "RegNo=?", whereArgs) > 0;
    }
    public long getPendingUploadCommunityCount() {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from InsertFarmer", null);
            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e)
        {

        }

        return x;
    }
    public boolean deleterowPhaseCommunity1Actual(String id) {
        //commensed phase  actual data
        String[] whereArgs = new String[] {id };
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("FarmerDetails", "RegistrationNO=?", whereArgs) > 0;
    }

    public long getPendingUploadCount(String userid) {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM InsertFarmer WHERE User_Id=? AND (Photo1 IS NOT NULL AND Status='A') OR (Photo1 IS NULL AND Status='R')", new String[]{userid.toLowerCase()});
            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return x;
    }
    public void updateEditedData(String pid, FarmerDetails thanaInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            values.put("Summary", thanaInfo.getSummary());
            values.put("Status", thanaInfo.getStatus());
            values.put("RegNo", thanaInfo.getRegistrationNO());
            String[] whereArgs = {String.valueOf(pid)};
            db.update("InsertFarmer", values, "RegNo=?", whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public void insertVillageList(ArrayList<Village> result) {

        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            // "SHG" ( `SHG_ID` TEXT, `SHG_NAME` TEXT, `PG_ID` TEXT, `PG_NAME` TEXT, `ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `Village_Code` TEXT, `Village_Name` TEXT )
            for (Village SHGType : result) {


                ContentValues values = new ContentValues();
                values.put("VillageCode", SHGType.getVillageCode().trim());
                values.put("VillageName", SHGType.getVillageName().trim());
                values.put("PanchayatCode", SHGType.getPanchayatCode().trim());

                String[] whereArgs = new String[]{SHGType.getVillageCode()};
                c = db.update("Village", values, "VillageCode=?", whereArgs);
                if (!(c > 0)) {
                    c = db.insert("Village", null, values);
                }

                Log.e("VillageCode",SHGType.getVillageCode().trim());
                Log.e("VillageName",SHGType.getVillageName().trim());
                Log.e("PanchayatCode", SHGType.getPanchayatCode().trim());
            }
            db.close();
            getWritableDatabase().close();

        } catch (Exception e) {
            // TODO: handle exception
        }
        // return plantationList;
    }
    public ArrayList<Village> getVillageLocal(String distId) {
        ArrayList<Village> bdetail = new ArrayList<Village>();
        try {
            String[] param = {distId};
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from Village where PanchayatCode = ? order by VillageName", param);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Village block = new Village();
                block.setVillageCode(cur.getString(cur.getColumnIndex("VillageCode")));
                block.setVillageName((cur.getString(cur.getColumnIndex("VillageName"))));
                block.setPanchayatCode((cur.getString(cur.getColumnIndex("PanchayatCode"))));
                bdetail.add(block);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }
    public ArrayList<Remarks> getRemarks() {


        ArrayList<Remarks> typeList = new ArrayList<Remarks>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
                            "SELECT RemarkCode,RemarkName from Remarks",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Remarks gen = new Remarks();
                gen.setRemarksCode(cur.getString(cur.getColumnIndex("RemarkCode")));
                gen.setRemarkName(cur.getString(cur.getColumnIndex("RemarkName")));
                typeList.add(gen);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeList;
    }
    public ArrayList<District> getDistLocal() {
        ArrayList<District> bdetail = new ArrayList<District>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT DistCode,DistName FROM Districts order by DistName", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                District ps = new District();
                ps.setDist_Code(cur.getString(cur.getColumnIndex("DistCode")));
                ps.setDist_Name(cur.getString(cur.getColumnIndex("DistName")));
                bdetail.add(ps);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }
    public ArrayList<Block> getblockLocal(String distid) {
        ArrayList<Block> bdetail = new ArrayList<Block>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM  Blocks where DistCode='" + distid.trim() + "' order by BlockName", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                Block ps = new Block();
                ps.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
                ps.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                ps.setDistCode((cur.getString(cur.getColumnIndex("DistCode"))));
                bdetail.add(ps);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
        }
        return bdetail;
    }


    public ArrayList<Checklist> getCheckList() {

        ArrayList<Checklist> checkList = new ArrayList<Checklist>();

        try {
//CREATE TABLE `Gender` ( `GenderID` TEXT, `GenderName` TEXT )
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
                            "SELECT ChkList_Id,Chklist_Nm from CheckList ",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Checklist gen = new Checklist();
                gen.setChecklist_Id(cur.getString(cur
                        .getColumnIndex("ChkList_Id")));
                gen.setChecklist_Name(cur.getString(cur
                        .getColumnIndex("Chklist_Nm")));

                checkList.add(gen);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception

        }
        return checkList;
    }
    public String getNameFor(String tblName, String whereColumnName, String returnColumnValue, String thisID) {
        String thisValue = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from " + tblName + " WHERE " + whereColumnName + "='" + thisID.trim() + "'", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                thisValue = cur.getString(cur.getColumnIndex(returnColumnValue));
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisValue.trim();
    }


}
