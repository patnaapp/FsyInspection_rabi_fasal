package bih.nic.in.raviinspection.database;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import bih.nic.in.raviinspection.entity.FarmerDetails;
import bih.nic.in.raviinspection.entity.Panchayat;
import bih.nic.in.raviinspection.entity.Remarks;
import bih.nic.in.raviinspection.entity.UserDetails;
import bih.nic.in.raviinspection.entity.Versioninfo;
import bih.nic.in.raviinspection.entity.Village;

public class WebServiceHelper {

    public static final String SERVICENAMESPACE = "http://epacs.bih.nic.in/";

    //public static final String SERVICEURL = "http://epacs.bih.nic.in/FsyInspectionWebServicesRavi.asmx";
    public static final String SERVICEURL = "http://epacs.bih.nic.in/BRFsyInspectionWebServicesRabi.asmx";

    //public static final String SERVICENAMESPACE = "http://10.133.17.37/";
    //public static final String SERVICEURL1 ="http://10.133.17.37/drishtiwebservice.asmx";


    public static final String APPVERSION_METHOD = "getAppLatest";
    public static final String AUTHENTICATE_METHOD = "Authenticate";
    private static final String Panchayat_LIST_METHOD = "GetPanchayatList1";
    private static final String Provision_LIST_METHOD = "GetProvisionalRemarks";
    private static final String Village_LIST_METHOD = "GetVillageLst";
    //private static final String getfarmerdetails = "GetFarmerDetails3";
    private static final String getfarmerdetails = "GetFarmerDetailsForRabi";
    private static final String INSERT_Farmer_details = "UploadDocNew";
    private static final String GET_PHOTO_PATH = "getPath";
    static String rest;




    public static Versioninfo CheckVersion(String imei, String version) {
        Versioninfo versioninfo;
        SoapObject res1;
        try {

            //res1=getServerData(APPVERSION_METHOD,Versioninfo.Versioninfo_CLASS,"IMEI","Ver",imei,version);
            SoapObject request = new SoapObject(SERVICENAMESPACE,APPVERSION_METHOD);
            request.addProperty("IMEI",imei);
            request.addProperty("Ver",version);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,Versioninfo.Versioninfo_CLASS.getSimpleName(),Versioninfo.Versioninfo_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + APPVERSION_METHOD,envelope);
            res1 = (SoapObject) envelope.getResponse();
            SoapObject final_object = (SoapObject) res1.getProperty(0);

            versioninfo = new Versioninfo(final_object);

        } catch (Exception e) {

            return null;
        }
        return versioninfo;

    }

    public static UserDetails Authenticate(String UserName, String Password) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, AUTHENTICATE_METHOD);

        request.addProperty("UserID", UserName);
        request.addProperty("Password", Password);
        UserDetails userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,UserDetails.USER_CLASS.getSimpleName(), UserDetails.USER_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + AUTHENTICATE_METHOD, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new UserDetails(res1);

        } catch (Exception e) {

            return null;
        }
        return userDetails;

    }

    public static ArrayList<Panchayat> loadPanchayatList(String user_id,String blockCode) {



        SoapObject request = new SoapObject(SERVICENAMESPACE, Panchayat_LIST_METHOD);

        request.addProperty("userid", user_id);
        request.addProperty("Blockcode", blockCode);


        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE,Panchayat.Panchayat_CLASS.getSimpleName(), Panchayat.Panchayat_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + Panchayat_LIST_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<Panchayat> pvmArrayList = new ArrayList<Panchayat>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Panchayat district = new Panchayat(final_object);
                    pvmArrayList.add(district);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }
    public static ArrayList<Remarks> loadProvisionList() {



        SoapObject request = new SoapObject(SERVICENAMESPACE, Provision_LIST_METHOD);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, Remarks.Remarks_CLASS.getSimpleName(), Remarks.Remarks_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + Provision_LIST_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<Remarks> pvmArrayList = new ArrayList<Remarks>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Remarks district = new Remarks(final_object);
                    pvmArrayList.add(district);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }
    public static ArrayList<Village> loadVillageList(String Panchayat_id) {



        SoapObject request = new SoapObject(SERVICENAMESPACE, Village_LIST_METHOD);

        request.addProperty("PanchayatCode", Panchayat_id);


        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, Village.Village_CLASS.getSimpleName(), Village.Village_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
           // HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL,600000);

            androidHttpTransport.call(SERVICENAMESPACE + Village_LIST_METHOD, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<Village> pvmArrayList = new ArrayList<Village>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Village district = new Village(final_object);
                    pvmArrayList.add(district);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }

    public static ArrayList<FarmerDetails> GetDataLIst1(String panchayatcode,String crop_id,String userid) {
        SoapObject res1;

        SoapObject request = new SoapObject(SERVICENAMESPACE,getfarmerdetails);
        Log.d("yttusydi",""+panchayatcode);

        request.addProperty("PanchayatCode",panchayatcode);
        request.addProperty("userid",userid);
        request.addProperty("Weather",crop_id);
       // request.addProperty("userid",Role);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(SERVICENAMESPACE,FarmerDetails.FARMER_CLASS.getSimpleName(),FarmerDetails.FARMER_CLASS);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);

        ArrayList<FarmerDetails> dataList = new ArrayList<FarmerDetails>();

        try {
            androidHttpTransport.call(SERVICENAMESPACE + getfarmerdetails, envelope);
            res1 = (SoapObject) envelope.getResponse();

            //res1=getServerData(SYNCHRONIZE_METHOD,GetDataListClass.GetData_CLASS,"_ULBID","_WardNo", UlbCode,wardid);

            int TotalProperty=0;
            if(res1!=null)
                TotalProperty= res1.getPropertyCount();


            for (int i = 0; i < TotalProperty; i++) {
                if (res1.getProperty(i) != null) {
                    Object property = res1.getProperty(i);
                    if (property instanceof SoapObject) {
                        SoapObject final_object = (SoapObject) property;
                        FarmerDetails sm = new FarmerDetails(final_object);
                        dataList.add(sm);
                    }
                } else
                    return dataList;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return dataList;
    }

    public static String sendLocaldata(FarmerDetails data) {
        SoapObject request = new SoapObject(SERVICENAMESPACE, INSERT_Farmer_details);

        request.addProperty("TypeOfFarmer", data.getTypeofFarmer());
        request.addProperty("ACNO", data.getRegistrationNO());
        request.addProperty("lat", data.getLatitude());
        request.addProperty("long_", data.getLongitude());
        request.addProperty("PictOfInspector", data.getPhoto1());
        request.addProperty("PictOfAawedak", data.getPhoto2());
        request.addProperty("PictOfLand", data.getPhoto3());
        request.addProperty("PictOfLand2", data.getPhoto4());
        request.addProperty("lat2", data.getPic1landLat());
        request.addProperty("long2", data.getPic1landLong());
        request.addProperty("PictOfLand3", data.getPhoto5());
        request.addProperty("lat3", data.getPic2landLat());
        request.addProperty("long3", data.getPic2landLong());
        request.addProperty("EntryBy", data.getUser_ID());
        request.addProperty("SelfDecOnlyOne", data.getAawedak_one_family_Id());
        request.addProperty("LPCiSIssuedByOFFICE", data.getLpc_rltd_chk_Id());
        request.addProperty("KhataAsPerLPC", data.getLpc_awedn_chk_Id());
        request.addProperty("LPCDate", data.getDate());
        request.addProperty("LPCNameAsPerApplicant", data.getAawedan_karta_Id());
        request.addProperty("IsCrop", data.getGhosit_fasal_khti_Id());
        request.addProperty("isCropAccordingToDec", data.getAawedan_ghosit_rakwa_Id());
//        request.addProperty("verifySatus", data.getStatus());
//        request.addProperty("Remarks", data.getSummary());
//        request.addProperty("ProvisionalID", data.getProvisionId());
//        request.addProperty("RashanCardNo", data.getRashanCardNum());
//        request.addProperty("cropAreaWheat", data.getEt_fieldwheat());
//        request.addProperty("cropAreaMazee", data.getEt_field_makka());
//        request.addProperty("cropAreaMasoor", data.getEt_masoor());
//        request.addProperty("cropAreaArhar", data.getEt_arhar());
//        request.addProperty("cropAreaIkha", data.getEt_sugarcane());
//        request.addProperty("cropAreaPotato", data.getEt_potato());
//        request.addProperty("cropAreaRai", data.getEt_raisarso());
//        request.addProperty("cropAreaOnion", data.getEt_onion());
//        request.addProperty("cropAreaChana", data.getEt_chana());
//        request.addProperty("ElectricityConsumerNo", data.getElectricity());

        request.addProperty("cropAreaWheatFarmer", data.getGehu_raytti());
        request.addProperty("cropAreaWheatShare", data.getGehu_gair_raytti());
        request.addProperty("cropAreaMazeeFarmer", data.getMakka_raytti());
        request.addProperty("cropAreaMazeeShare", data.getMakka_gair_raytti());
        request.addProperty("cropAreaChanaFarmer", data.getChana_raytti());
        request.addProperty("cropAreaChanaShare", data.getChana_gair_raytti());
        request.addProperty("cropAreaMasoorFarmer", data.getMasur_raytti());
        request.addProperty("cropAreaMasoorShare", data.getMasur_gair_raytti());
        request.addProperty("cropAreaArharFarmer", data.getArahar_raytti());
        request.addProperty("cropAreaArharShare", data.getArahar_gair_raytti());
        request.addProperty("cropAreaRaiFarmer", data.getRae_raytti());
        request.addProperty("cropAreaRaiShare", data.getRae_gair_raytti());
        request.addProperty("cropAreaIkhaFarmer", data.getEkh_raytti());
        request.addProperty("cropAreaIkhaShare", data.getEkh_gair_raytti());
        request.addProperty("cropAreaOnionFarmer", data.getPyaj_raytti());
        request.addProperty("cropAreaOnionShare", data.getPyaj_gair_raytti());
        request.addProperty("cropAreaPotatoFarmer", data.getAloo_raytti());
        request.addProperty("cropAreaPotatoShare", data.getAloo_gair_raytti());

        request.addProperty("IsApplicantPer", data.getAawedak_accept());
        request.addProperty("IsRelation", data.getAawedak_reject());
        request.addProperty("IsSelfDecUploded", data.getSwaghona_upload());
        request.addProperty("IsSelfDecAsPerName", data.getSwaghona_patra_aawedakrta());
        request.addProperty("SelfDecStatus", data.getSwaghosana_sambandhit_id());
        request.addProperty("NameOfKSOrWardMember", data.getSwaghosana_sambandhit_signer_nm());

        if(!data.getElectric_avail_id().equalsIgnoreCase("")){
            if(data.getElectric_avail_id().equalsIgnoreCase("1")){
                request.addProperty("IsElectricity", "Y");
                if(data.getElectric_id().equals("1")) {
                    request.addProperty("IsElectricityNumber","Y");
                    request.addProperty("ElectricityNumber", data.getElectricity());
                }else if(data.getElectric_id().equals("2")){
                    request.addProperty("IsElectricityNumber","N");
                    request.addProperty("ElectricityNumber", "");
                }else{
                    request.addProperty("IsElectricityNumber","");
                    request.addProperty("ElectricityNumber", "");
                }
            }else if(data.getElectric_avail_id().equalsIgnoreCase("2")){
                request.addProperty("IsElectricity", "N");
                request.addProperty("IsElectricityNumber", "");
                request.addProperty("ElectricityNumber", "");
            }else{
                request.addProperty("IsElectricity", "");
                request.addProperty("IsElectricityNumber", "");
                request.addProperty("ElectricityNumber", "");
            }

        }else{
            request.addProperty("IsElectricity", "");
            request.addProperty("IsElectricityNumber", "");
            request.addProperty("ElectricityNumber", "");
        }

        request.addProperty("IsWheat", data.getIs_gehu());
        request.addProperty("IsMazee", data.getIs_makka());
        request.addProperty("IsChana", data.getIs_chana());
        request.addProperty("IsMasoor", data.getIs_masur());
        request.addProperty("IsArhar", data.getIs_arahar());
        request.addProperty("IsRai", data.getIs_rai());
        request.addProperty("isOnion", data.getIs_onion());
        request.addProperty("isPotato", data.getIs_potato());
        request.addProperty("isIkha", data.getIs_ikha());



       // request.addProperty("Swaghosana_sambandhit_Nm", data.getSwaghosana_sambandhit_Nm());

        //request.addProperty("CropAreaFarmer", data.getEt_aawedan_ghosit_rakwa());
        //request.addProperty("CropAreaShare", data.getEt_aawedan_ghosit_rakwa_two());
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + INSERT_Farmer_details, envelope);
            rest = envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return "0";
        }
        return rest;
    }


    public static String UploadLandDetailsPhase1( String filepath) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GET_PHOTO_PATH);
        request.addProperty("FilePath", filepath);

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_PHOTO_PATH, envelope);

            rest = envelope.getResponse().toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            //return "0";
            return null;
        }
        return rest;

    }
}
