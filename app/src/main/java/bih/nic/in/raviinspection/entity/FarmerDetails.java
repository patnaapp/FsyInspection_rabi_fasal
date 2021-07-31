package bih.nic.in.raviinspection.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class FarmerDetails implements KvmSerializable, Serializable {

    public static Class<FarmerDetails> FARMER_CLASS = FarmerDetails.class;


    private int id;
    private String User_ID = "";
    private String NibandhanSankhya = "";
    private String RegistrationNO = "";
    //private String _isAuthenticated = "";
    private String DistCode = "";
    private String DistName = "";
    private String BlockCode = "";
    private String BlockName = "";
    private String PanchayatCode = "";
    private String PanchayatName = "";
    private String wardNo = "";
    private String VillageCode = "";
    private String VILLNAME = "";
    private String TypeofFarmer = "";
    private String FarmerName = "";
    private String FarmerFatherName = "";
    private String Gender_Code = "";
    private String Gender_Name = "";
    private String Category_Code = "";
    private String Category_Name = "";
    private String MobileNumber = "";
    private String pacsMemberStatus = "";
    private String AadharNo = "";
    private String AadharName = "";
    private String BankName = "";
    private String BankBranchName = "";
    private String IFSCcode = "";
    private String BankAccountNo = "";
    private String CropName = "";
    private String WheatherName = "";
    private String IDProof = "";
    private String Residential = "";
    private String Passbook = "";
    private String LPC = "";
    private String SelfAttested = "";
    private String FarmerPhotoPath = "";
    private String khataNo = "";
    private String khasraNo = "";
    private String area = "";
    private String cropArea = "";
    private String Householdid = "";
    private String status = "";
    private String summary = "";
    private String ProvisionId = "";
    private String ProvisionName = "";
    private String latitude = "";
    private String longitude = "";
    private String Photo1 = "";
    private String Photo2 = "";
    private String Photo3 = "";
    private String Photo4 = "";
    private String Photo5 = "";
    private String Pic1landLat = "";
    private String Pic1landLong = "";
    private String Pic2landLat = "";
    private String Pic2landLong = "";
    private String RashanCardNum = "";

    private String CropTypeKharif = "";
    private String CropTypeKharifName = "";




    private String et_fieldwheat = "";
    private String et_field_makka = "";
    private String et_masoor = "";
    private String et_arhar = "";
    private String et_sugarcane = "";
    private String et_potato = "";
    private String et_raisarso = "";
    private String et_onion = "";
    private String et_chana = "";
    private String electricity = "";
    private String soyabin = "";
    private String ISCOnsumbernumberExist = "";

    private String aawedak_accept = "";
    private String aawedak_reject = "";
    private String lpc_rltd_chk_Id = "";
    private String lpc_awedn_chk_Id = "";
    private String ghosit_fasal_khti_Id = "";
    private String aawedan_ghosit_rakwa_Id = "";
    private String aawedak_one_family_Id = "";
    private String et_aawedan_ghosit_rakwa = "";
    private String et_aawedan_ghosit_rakwa_two = "";


    private String gehu_raytti = "";
    private String makka_raytti = "";
    private String chana_raytti = "";
    private String masur_raytti = "";
    private String arahar_raytti = "";
    private String rae_raytti = "";
    private String ekh_raytti = "";
    private String pyaj_raytti = "";
    private String aloo_raytti = "";
    private String gehu_gair_raytti = "";
    private String makka_gair_raytti = "";
    private String chana_gair_raytti = "";
    private String masur_gair_raytti = "";
    private String arahar_gair_raytti = "";
    private String rae_gair_raytti = "";
    private String ekh_gair_raytti = "";
    private String pyaj_gair_raytti = "";
    private String aloo_gair_raytti = "";

    private String swaghosana_sambandhit_id = "";
    private String swaghosana_sambandhit_Nm = "";
    private String swaghosana_sambandhit_signer_nm = "";

    private String electric_avail_id = "";
    private String electric_avail_nm = "";
    private String electric_id = "";
    private String electric_nm = "";
    private String swaghosana_sambandhit_nm = "";
    private String swaghosana_signer_name = "";
    private String Date = "";
    private String aawedan_karta_Id = "";
    private String aawedan_karta_Nm = "";
    private String swaghona_upload = "";
    private String swaghona_patra_aawedakrta = "";

    private String is_gehu = "";
    private String is_makka = "";
    private String is_chana = "";
    private String is_masur = "";
    private String is_arahar = "";
    private String is_rai = "";
    private String is_ikha = "";
    private String is_onion = "";
    private String is_potato = "";

    public FarmerDetails() {
    }

    public FarmerDetails(SoapObject obj) {


//        this.setNibandhanSankhya(obj.getProperty("AGRIUNIQNO").toString());
//        this.setUser_ID(obj.getProperty("User_ID").toString());
//        this.setRegistrationNO(obj.getProperty("RegistrationNO").toString());
//        this.setDistCode(obj.getProperty("DistCode").toString());
//        this.setDistName(obj.getProperty("DistName").toString());
//        this.setBlockCode(obj.getProperty("BlockCode").toString());
//        this.setBlockName(obj.getProperty("BlockName").toString());
//        this.setPanchayatCode(obj.getProperty("PanchayatCode").toString());
//        this.setPanchayatName(obj.getProperty("PanchayatName").toString());
//        this.setWardNo(obj.getProperty("wardname").toString());
//        this.setVillageCode(obj.getProperty("VillageCode").toString());
//        this.setVILLNAME(obj.getProperty("VILLNAME").toString());
//        this.setTypeofFarmer(obj.getProperty("TypeofFarmer").toString());
//        this.setFarmerName(obj.getProperty("FarmerName").toString());
//        this.setFarmerFatherName(obj.getProperty("FarmerFatherName").toString());
//        this.setGender_Code(obj.getProperty("Gender_Code").toString());
//        this.setGender_Name(obj.getProperty("Gender_Name").toString());
//        this.setCategory_Code(obj.getProperty("Category_Code").toString());
//        this.setCategory_Name(obj.getProperty("Category_Name").toString());
//        this.setMobileNumber(obj.getProperty("MobileNumber").toString());
//
//
//        this.setPacsMemberStatus(obj.getProperty("pacsMemberStatus").toString());
//        this.setAadharNo(obj.getProperty("AadharNo").toString());
//        this.setAadharName(obj.getProperty("AadharName").toString());
        //this.setBankName(obj.getProperty("BankName").toString());
        //this.setBankBranchName(obj.getProperty("BankBranchName").toString());
        //this.setIFSCcode(obj.getProperty("IFSCcode").toString());
//        this.setBankAccountNo(obj.getProperty("BankAccountNo").toString());
//        this.setHouseholdid(obj.getProperty("HouseholdNo").toString());
//        this.setCropName(obj.getProperty("CropName").toString());
//        this.setWheatherName(obj.getProperty("WheatherName").toString());
//        this.setIDProof(obj.getProperty("IDProof").toString());
//        this.setResidential(obj.getProperty("Residential").toString());
//        this.setLPC(obj.getProperty("LPC").toString());
//        this.setSelfAttested(obj.getProperty("SelfAttested").toString());
//        this.setFarmerPhotoPath(obj.getProperty("FarmerPhotoPath").toString());

        //this.setPassbook(obj.getProperty("Passbook").toString());



//        this.setKhataNo(obj.getProperty("khataNo").toString());
//        this.setKhasraNo(obj.getProperty("khasraNo").toString());
//        this.setArea(obj.getProperty("area").toString());
       // this.setCropArea(obj.getProperty("cropArea").toString());

        //this.setRashanCardNum(obj.getProperty("isAuthenticated").toString());


//        this.setEt_fieldwheat(obj.getProperty("cropAreaWheat").toString().trim());
//        this.setEt_field_makka(obj.getProperty("cropAreaMazee").toString().trim());
//        this.setEt_chana(obj.getProperty("cropAreaChana").toString().trim());
//        this.setEt_masoor(obj.getProperty("cropAreaMasoor").toString().trim());
//        this.setEt_arhar(obj.getProperty("cropAreaArhar").toString().trim());
//        this.setEt_raisarso(obj.getProperty("cropAreaRai").toString().trim());
//        this.setEt_sugarcane(obj.getProperty("cropAreaIkha").toString().trim());
//        this.setEt_onion(obj.getProperty("cropAreaOnion").toString().trim());
//        this.setEt_potato(obj.getProperty("cropAreaPotato").toString().trim());
//        this.setISCOnsumbernumberExist(obj.getProperty("IsElectricity").toString().trim());



        this.setCropTypeKharif(obj.getProperty("CropType").toString().trim());
        this.setCropTypeKharifName(obj.getProperty("CropName").toString().trim());
        this.setUser_ID(obj.getProperty("User_ID").toString().trim());
        this.setRegistrationNO(obj.getProperty("RegistrationNO").toString().trim());
        this.setDistCode(obj.getProperty("DistCode").toString().trim());
        this.setDistName(obj.getProperty("DistName").toString().trim());
        this.setBlockCode(obj.getProperty("BlockCode").toString().trim());
        this.setBlockName(obj.getProperty("BlockName").toString().trim());
        this.setPanchayatCode(obj.getProperty("PanchayatCode").toString().trim());
        this.setPanchayatName(obj.getProperty("PanchayatName").toString().trim());
        this.setWardNo(obj.getProperty("wardname").toString().trim());
        this.setVillageCode(obj.getProperty("VillageCode").toString().trim());
        this.setVILLNAME(obj.getProperty("VILLNAME").toString().trim());
        this.setTypeofFarmer(obj.getProperty("TypeofFarmer").toString().trim());
        this.setFarmerName(obj.getProperty("FarmerName").toString().trim());
        //this.setISCOnsumbernumberExist(obj.getProperty("FarmerNameHN").toString().trim());
        this.setFarmerFatherName(obj.getProperty("FarmerFatherName").toString().trim());
        //this.setISCOnsumbernumberExist(obj.getProperty("FarmerFatherNameHN").toString().trim());
        this.setGender_Code(obj.getProperty("Gender_Code").toString().trim());
        this.setGender_Name(obj.getProperty("Gender_Name").toString().trim());
        this.setCategory_Code(obj.getProperty("Category_Code").toString().trim());
        this.setCategory_Name(obj.getProperty("Category_Name").toString().trim());
        this.setMobileNumber(obj.getProperty("MobileNumber").toString().trim());
        this.setPacsMemberStatus(obj.getProperty("pacsMemberStatus").toString().trim());
        this.setAadharNo(obj.getProperty("AadharNo").toString().trim());
        this.setAadharName(obj.getProperty("AadharName").toString().trim());
        this.setBankAccountNo(obj.getProperty("BankAccountNo").toString().trim());
        this.setHouseholdid(obj.getProperty("HouseholdNo").toString().trim());
        this.setCropName(obj.getProperty("CropName").toString().trim());
        this.setWheatherName(obj.getProperty("WheatherName").toString().trim());
        this.setIDProof(obj.getProperty("IDProof").toString().trim());
        this.setResidential(obj.getProperty("Residential").toString().trim());
        this.setLPC(obj.getProperty("LPC").toString().trim());
        this.setSelfAttested(obj.getProperty("SelfAttested").toString().trim());
        this.setFarmerPhotoPath(obj.getProperty("FarmerPhotoPath").toString().trim());
        this.setKhataNo(obj.getProperty("khataNo").toString().trim());
        this.setKhasraNo(obj.getProperty("khasraNo").toString().trim());

        if((obj.getProperty("area").toString().equalsIgnoreCase("anyType{}"))){
            this.setArea("");
        }
        else {
            this.setArea(obj.getProperty("area").toString().trim());
        }

        if((obj.getProperty("cropAreaWheatFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setGehu_raytti("");
        }else{
            this.setGehu_raytti(obj.getProperty("cropAreaWheatFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaWheatShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setGehu_gair_raytti("");
        }else{
            this.setGehu_gair_raytti(obj.getProperty("cropAreaWheatShare").toString().trim());
        }
        if((obj.getProperty("cropAreaMazeeFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setMakka_raytti("");
        }else{
            this.setMakka_raytti(obj.getProperty("cropAreaMazeeFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaMazeeShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setMakka_gair_raytti("");
        }else{
            this.setMakka_gair_raytti(obj.getProperty("cropAreaMazeeShare").toString().trim());
        }
        if((obj.getProperty("cropAreaChanaFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setChana_raytti("");
        }else{
            this.setChana_raytti(obj.getProperty("cropAreaChanaFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaChanaShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setChana_gair_raytti("");
        }else{
            this.setChana_gair_raytti(obj.getProperty("cropAreaChanaShare").toString().trim());
        }
        if((obj.getProperty("cropAreaMasoorFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setMasur_raytti("");
        }else{
            this.setMasur_raytti(obj.getProperty("cropAreaMasoorFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaMasoorShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setMasur_gair_raytti("");
        }else{
            this.setMasur_gair_raytti(obj.getProperty("cropAreaMasoorShare").toString().trim());
        }

        if((obj.getProperty("cropAreaArharFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setArahar_raytti("");
        }else{
            this.setArahar_raytti(obj.getProperty("cropAreaArharFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaArharShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setArahar_gair_raytti("");
        }else{
            this.setArahar_gair_raytti(obj.getProperty("cropAreaArharShare").toString().trim());
        }
        if((obj.getProperty("cropAreaRaiFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setRae_raytti("");
        }else{
            this.setRae_raytti(obj.getProperty("cropAreaRaiFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaRaiShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setRae_gair_raytti("");
        }else{
            this.setRae_gair_raytti(obj.getProperty("cropAreaRaiShare").toString().trim());
        }
        if((obj.getProperty("cropAreaIkhaFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setEkh_raytti("");
        }else{
            this.setEkh_raytti(obj.getProperty("cropAreaIkhaFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaIkhaShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setEkh_gair_raytti("");
        }else{
            this.setEkh_gair_raytti(obj.getProperty("cropAreaIkhaShare").toString().trim());
        }
        if((obj.getProperty("cropAreaOnionFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setPyaj_raytti("");
        }else{
            this.setPyaj_raytti(obj.getProperty("cropAreaOnionFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaOnionShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setPyaj_gair_raytti("");
        }else{
            this.setPyaj_gair_raytti(obj.getProperty("cropAreaOnionShare").toString().trim());
        }
        if((obj.getProperty("cropAreaPotatoFarmer").toString().equalsIgnoreCase("anyType{}"))){
            this.setAloo_raytti("");
        }else{
            this.setAloo_raytti(obj.getProperty("cropAreaPotatoFarmer").toString().trim());
        }
        if((obj.getProperty("cropAreaPotatoShare").toString().equalsIgnoreCase("anyType{}"))){
            this.setAloo_gair_raytti("");
        }else{
            this.setAloo_gair_raytti(obj.getProperty("cropAreaPotatoShare").toString().trim());
        }

    }


    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }

    public String getRashanCardNum() {
        return RashanCardNum;
    }

    public void setRashanCardNum(String rashanCardNum) {
        RashanCardNum = rashanCardNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getPanchayatCode() {
        return PanchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        PanchayatCode = panchayatCode;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public String getVillageCode() {
        return VillageCode;
    }

    public void setVillageCode(String villageCode) {
        VillageCode = villageCode;
    }

    public String getVILLNAME() {
        return VILLNAME;
    }

    public void setVILLNAME(String VILLNAME) {
        this.VILLNAME = VILLNAME;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getFarmerName() {
        return FarmerName;
    }

    public void setFarmerName(String farmerName) {
        FarmerName = farmerName;
    }

    public String getFarmerFatherName() {
        return FarmerFatherName;
    }

    public void setFarmerFatherName(String farmerFatherName) {
        FarmerFatherName = farmerFatherName;
    }

    public String getGender_Code() {
        return Gender_Code;
    }

    public void setGender_Code(String gender_Code) {
        Gender_Code = gender_Code;
    }

    public String getGender_Name() {
        return Gender_Name;
    }

    public void setGender_Name(String gender_Name) {
        Gender_Name = gender_Name;
    }

    public String getCategory_Code() {
        return Category_Code;
    }

    public void setCategory_Code(String category_Code) {
        Category_Code = category_Code;
    }

    public String getCategory_Name() {
        return Category_Name;
    }

    public void setCategory_Name(String category_Name) {
        Category_Name = category_Name;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getRegistrationNO() {
        return RegistrationNO;
    }

    public void setRegistrationNO(String registrationNO) {
        RegistrationNO = registrationNO;
    }

    public String getTypeofFarmer() {
        return TypeofFarmer;
    }

    public void setTypeofFarmer(String typeofFarmer) {
        TypeofFarmer = typeofFarmer;
    }

    public String getPacsMemberStatus() {
        return pacsMemberStatus;
    }

    public void setPacsMemberStatus(String pacsMemberStatus) {
        this.pacsMemberStatus = pacsMemberStatus;
    }

    public String getAadharNo() {
        return AadharNo;
    }

    public void setAadharNo(String aadharNo) {
        AadharNo = aadharNo;
    }

    public String getAadharName() {
        return AadharName;
    }

    public void setAadharName(String aadharName) {
        AadharName = aadharName;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankBranchName() {
        return BankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        BankBranchName = bankBranchName;
    }

    public String getIFSCcode() {
        return IFSCcode;
    }

    public void setIFSCcode(String IFSCcode) {
        this.IFSCcode = IFSCcode;
    }

    public String getBankAccountNo() {
        return BankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        BankAccountNo = bankAccountNo;
    }

    public String getCropName() {
        return CropName;
    }

    public void setCropName(String cropName) {
        CropName = cropName;
    }

    public String getWheatherName() {
        return WheatherName;
    }

    public void setWheatherName(String wheatherName) {
        WheatherName = wheatherName;
    }

    public String getIDProof() {
        return IDProof;
    }

    public void setIDProof(String IDProof) {
        this.IDProof = IDProof;
    }

    public String getResidential() {
        return Residential;
    }

    public void setResidential(String residential) {
        Residential = residential;
    }

    public String getPassbook() {
        return Passbook;
    }

    public void setPassbook(String passbook) {
        Passbook = passbook;
    }

    public String getLPC() {
        return LPC;
    }

    public void setLPC(String LPC) {
        this.LPC = LPC;
    }

    public String getSelfAttested() {
        return SelfAttested;
    }

    public void setSelfAttested(String selfAttested) {
        SelfAttested = selfAttested;
    }

    public String getFarmerPhotoPath() {
        return FarmerPhotoPath;
    }

    public void setFarmerPhotoPath(String farmerPhotoPath) {
        FarmerPhotoPath = farmerPhotoPath;
    }

    public String getKhataNo() {
        return khataNo;
    }

    public void setKhataNo(String khataNo) {
        this.khataNo = khataNo;
    }

    public String getKhasraNo() {
        return khasraNo;
    }

    public void setKhasraNo(String khasraNo) {
        this.khasraNo = khasraNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCropArea() {
        return cropArea;
    }

    public void setCropArea(String cropArea) {
        this.cropArea = cropArea;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhoto1() {
        return Photo1;
    }

    public void setPhoto1(String photo1) {
        Photo1 = photo1;
    }

    public String getPhoto2() {
        return Photo2;
    }

    public void setPhoto2(String photo2) {
        Photo2 = photo2;
    }

    public String getPhoto3() {
        return Photo3;
    }

    public void setPhoto3(String photo3) {
        Photo3 = photo3;
    }

    public String getPhoto4() {
        return Photo4;
    }

    public void setPhoto4(String photo4) {
        Photo4 = photo4;
    }

    public String getPhoto5() {
        return Photo5;
    }

    public void setPhoto5(String photo5) {
        Photo5 = photo5;
    }

    public String getPic1landLat() {
        return Pic1landLat;
    }

    public void setPic1landLat(String pic1landLat) {
        Pic1landLat = pic1landLat;
    }

    public String getPic1landLong() {
        return Pic1landLong;
    }

    public void setPic1landLong(String pic1landLong) {
        Pic1landLong = pic1landLong;
    }

    public String getPic2landLat() {
        return Pic2landLat;
    }

    public void setPic2landLat(String pic2landLat) {
        Pic2landLat = pic2landLat;
    }

    public String getPic2landLong() {
        return Pic2landLong;
    }

    public void setPic2landLong(String pic2landLong) {
        Pic2landLong = pic2landLong;
    }

    public String getProvisionId() {
        return ProvisionId;
    }

    public void setProvisionId(String provisionId) {
        ProvisionId = provisionId;
    }

    public String getProvisionName() {
        return ProvisionName;
    }

    public void setProvisionName(String provisionName) {
        ProvisionName = provisionName;
    }

    public String getNibandhanSankhya() {
        return NibandhanSankhya;
    }

    public void setNibandhanSankhya(String nibandhanSankhya) {
        NibandhanSankhya = nibandhanSankhya;
    }

    public String getHouseholdid() {
        return Householdid;
    }

    public void setHouseholdid(String householdid) {
        Householdid = householdid;
    }



    public String getEt_fieldwheat() {
        return et_fieldwheat;
    }

    public void setEt_fieldwheat(String et_fieldwheat) {
        this.et_fieldwheat = et_fieldwheat;
    }

    public String getEt_field_makka() {
        return et_field_makka;
    }

    public void setEt_field_makka(String et_field_makka) {
        this.et_field_makka = et_field_makka;
    }

    public String getEt_masoor() {
        return et_masoor;
    }

    public void setEt_masoor(String et_masoor) {
        this.et_masoor = et_masoor;
    }

    public String getEt_arhar() {
        return et_arhar;
    }

    public void setEt_arhar(String et_arhar) {
        this.et_arhar = et_arhar;
    }

    public String getEt_sugarcane() {
        return et_sugarcane;
    }

    public void setEt_sugarcane(String et_sugarcane) {
        this.et_sugarcane = et_sugarcane;
    }

    public String getEt_potato() {
        return et_potato;
    }

    public void setEt_potato(String et_potato) {
        this.et_potato = et_potato;
    }

    public String getEt_raisarso() {
        return et_raisarso;
    }

    public void setEt_raisarso(String et_raisarso) {
        this.et_raisarso = et_raisarso;
    }

    public String getEt_onion() {
        return et_onion;
    }

    public void setEt_onion(String et_onion) {
        this.et_onion = et_onion;
    }

    public String getEt_chana() {
        return et_chana;
    }

    public void setEt_chana(String et_chana) {
        this.et_chana = et_chana;
    }


    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getISCOnsumbernumberExist() {
        return ISCOnsumbernumberExist;
    }

    public void setISCOnsumbernumberExist(String ISCOnsumbernumberExist) {
        this.ISCOnsumbernumberExist = ISCOnsumbernumberExist;
    }

    public String getCropTypeKharif() {
        return CropTypeKharif;
    }

    public void setCropTypeKharif(String cropTypeKharif) {
        CropTypeKharif = cropTypeKharif;
    }

    public String getSoyabin() {
        return soyabin;
    }

    public void setSoyabin(String soyabin) {
        this.soyabin = soyabin;
    }

    public String getCropTypeKharifName() {
        return CropTypeKharifName;
    }

    public void setCropTypeKharifName(String cropTypeKharifName) {
        CropTypeKharifName = cropTypeKharifName;
    }

    public String getLpc_rltd_chk_Id() {
        return lpc_rltd_chk_Id;
    }

    public void setLpc_rltd_chk_Id(String lpc_rltd_chk_Id) {
        this.lpc_rltd_chk_Id = lpc_rltd_chk_Id;
    }

    public String getLpc_awedn_chk_Id() {
        return lpc_awedn_chk_Id;
    }

    public void setLpc_awedn_chk_Id(String lpc_awedn_chk_Id) {
        this.lpc_awedn_chk_Id = lpc_awedn_chk_Id;
    }

    public String getGhosit_fasal_khti_Id() {
        return ghosit_fasal_khti_Id;
    }

    public void setGhosit_fasal_khti_Id(String ghosit_fasal_khti_Id) {
        this.ghosit_fasal_khti_Id = ghosit_fasal_khti_Id;
    }

    public String getAawedan_ghosit_rakwa_Id() {
        return aawedan_ghosit_rakwa_Id;
    }

    public void setAawedan_ghosit_rakwa_Id(String aawedan_ghosit_rakwa_Id) {
        this.aawedan_ghosit_rakwa_Id = aawedan_ghosit_rakwa_Id;
    }

    public String getAawedak_one_family_Id() {
        return aawedak_one_family_Id;
    }

    public void setAawedak_one_family_Id(String aawedak_one_family_Id) {
        this.aawedak_one_family_Id = aawedak_one_family_Id;
    }

    public String getEt_aawedan_ghosit_rakwa() {
        return et_aawedan_ghosit_rakwa;
    }

    public void setEt_aawedan_ghosit_rakwa(String et_aawedan_ghosit_rakwa) {
        this.et_aawedan_ghosit_rakwa = et_aawedan_ghosit_rakwa;
    }

    public String getEt_aawedan_ghosit_rakwa_two() {
        return et_aawedan_ghosit_rakwa_two;
    }

    public void setEt_aawedan_ghosit_rakwa_two(String et_aawedan_ghosit_rakwa_two) {
        this.et_aawedan_ghosit_rakwa_two = et_aawedan_ghosit_rakwa_two;
    }

    public String getAawedak_accept() {
        return aawedak_accept;
    }

    public void setAawedak_accept(String aawedak_accept) {
        this.aawedak_accept = aawedak_accept;
    }

    public String getAawedak_reject() {
        return aawedak_reject;
    }

    public void setAawedak_reject(String aawedak_reject) {
        this.aawedak_reject = aawedak_reject;
    }

    public String getGehu_raytti() {
        return gehu_raytti;
    }

    public void setGehu_raytti(String gehu_raytti) {
        this.gehu_raytti = gehu_raytti;
    }

    public String getMakka_raytti() {
        return makka_raytti;
    }

    public void setMakka_raytti(String makka_raytti) {
        this.makka_raytti = makka_raytti;
    }

    public String getChana_raytti() {
        return chana_raytti;
    }

    public void setChana_raytti(String chana_raytti) {
        this.chana_raytti = chana_raytti;
    }

    public String getMasur_raytti() {
        return masur_raytti;
    }

    public void setMasur_raytti(String masur_raytti) {
        this.masur_raytti = masur_raytti;
    }

    public String getArahar_raytti() {
        return arahar_raytti;
    }

    public void setArahar_raytti(String arahar_raytti) {
        this.arahar_raytti = arahar_raytti;
    }

    public String getRae_raytti() {
        return rae_raytti;
    }

    public void setRae_raytti(String rae_raytti) {
        this.rae_raytti = rae_raytti;
    }

    public String getEkh_raytti() {
        return ekh_raytti;
    }

    public void setEkh_raytti(String ekh_raytti) {
        this.ekh_raytti = ekh_raytti;
    }

    public String getPyaj_raytti() {
        return pyaj_raytti;
    }

    public void setPyaj_raytti(String pyaj_raytti) {
        this.pyaj_raytti = pyaj_raytti;
    }

    public String getAloo_raytti() {
        return aloo_raytti;
    }

    public void setAloo_raytti(String aloo_raytti) {
        this.aloo_raytti = aloo_raytti;
    }

    public String getGehu_gair_raytti() {
        return gehu_gair_raytti;
    }

    public void setGehu_gair_raytti(String gehu_gair_raytti) {
        this.gehu_gair_raytti = gehu_gair_raytti;
    }

    public String getMakka_gair_raytti() {
        return makka_gair_raytti;
    }

    public void setMakka_gair_raytti(String makka_gair_raytti) {
        this.makka_gair_raytti = makka_gair_raytti;
    }

    public String getChana_gair_raytti() {
        return chana_gair_raytti;
    }

    public void setChana_gair_raytti(String chana_gair_raytti) {
        this.chana_gair_raytti = chana_gair_raytti;
    }

    public String getMasur_gair_raytti() {
        return masur_gair_raytti;
    }

    public void setMasur_gair_raytti(String masur_gair_raytti) {
        this.masur_gair_raytti = masur_gair_raytti;
    }

    public String getArahar_gair_raytti() {
        return arahar_gair_raytti;
    }

    public void setArahar_gair_raytti(String arahar_gair_raytti) {
        this.arahar_gair_raytti = arahar_gair_raytti;
    }

    public String getRae_gair_raytti() {
        return rae_gair_raytti;
    }

    public void setRae_gair_raytti(String rae_gair_raytti) {
        this.rae_gair_raytti = rae_gair_raytti;
    }

    public String getEkh_gair_raytti() {
        return ekh_gair_raytti;
    }

    public void setEkh_gair_raytti(String ekh_gair_raytti) {
        this.ekh_gair_raytti = ekh_gair_raytti;
    }

    public String getPyaj_gair_raytti() {
        return pyaj_gair_raytti;
    }

    public void setPyaj_gair_raytti(String pyaj_gair_raytti) {
        this.pyaj_gair_raytti = pyaj_gair_raytti;
    }

    public String getAloo_gair_raytti() {
        return aloo_gair_raytti;
    }

    public void setAloo_gair_raytti(String aloo_gair_raytti) {
        this.aloo_gair_raytti = aloo_gair_raytti;
    }

    public String getSwaghosana_sambandhit_id() {
        return swaghosana_sambandhit_id;
    }

    public void setSwaghosana_sambandhit_id(String swaghosana_sambandhit_id) {
        this.swaghosana_sambandhit_id = swaghosana_sambandhit_id;
    }

    public String getSwaghosana_sambandhit_Nm() {
        return swaghosana_sambandhit_Nm;
    }

    public void setSwaghosana_sambandhit_Nm(String swaghosana_sambandhit_Nm) {
        this.swaghosana_sambandhit_Nm = swaghosana_sambandhit_Nm;
    }

    public String getSwaghosana_sambandhit_signer_nm() {
        return swaghosana_sambandhit_signer_nm;
    }

    public void setSwaghosana_sambandhit_signer_nm(String swaghosana_sambandhit_signer_nm) {
        this.swaghosana_sambandhit_signer_nm = swaghosana_sambandhit_signer_nm;
    }

    public String getElectric_avail_id() {
        return electric_avail_id;
    }

    public void setElectric_avail_id(String electric_avail_id) {
        this.electric_avail_id = electric_avail_id;
    }

    public String getElectric_avail_nm() {
        return electric_avail_nm;
    }

    public void setElectric_avail_nm(String electric_avail_nm) {
        this.electric_avail_nm = electric_avail_nm;
    }

    public String getElectric_id() {
        return electric_id;
    }

    public void setElectric_id(String electric_id) {
        this.electric_id = electric_id;
    }

    public String getElectric_nm() {
        return electric_nm;
    }

    public void setElectric_nm(String electric_nm) {
        this.electric_nm = electric_nm;
    }

    public String getSwaghosana_sambandhit_nm() {
        return swaghosana_sambandhit_nm;
    }

    public void setSwaghosana_sambandhit_nm(String swaghosana_sambandhit_nm) {
        this.swaghosana_sambandhit_nm = swaghosana_sambandhit_nm;
    }

    public String getSwaghosana_signer_name() {
        return swaghosana_signer_name;
    }

    public void setSwaghosana_signer_name(String swaghosana_signer_name) {
        this.swaghosana_signer_name = swaghosana_signer_name;
    }

    public String getSwaghona_upload() {
        return swaghona_upload;
    }

    public void setSwaghona_upload(String swaghona_upload) {
        this.swaghona_upload = swaghona_upload;
    }

    public String getSwaghona_patra_aawedakrta() {
        return swaghona_patra_aawedakrta;
    }

    public void setSwaghona_patra_aawedakrta(String swaghona_patra_aawedakrta) {
        this.swaghona_patra_aawedakrta = swaghona_patra_aawedakrta;
    }

    public String getIs_gehu() {
        return is_gehu;
    }

    public void setIs_gehu(String is_gehu) {
        this.is_gehu = is_gehu;
    }

    public String getIs_makka() {
        return is_makka;
    }

    public void setIs_makka(String is_makka) {
        this.is_makka = is_makka;
    }

    public String getIs_chana() {
        return is_chana;
    }

    public void setIs_chana(String is_chana) {
        this.is_chana = is_chana;
    }

    public String getIs_masur() {
        return is_masur;
    }

    public void setIs_masur(String is_masur) {
        this.is_masur = is_masur;
    }

    public String getIs_arahar() {
        return is_arahar;
    }

    public void setIs_arahar(String is_arahar) {
        this.is_arahar = is_arahar;
    }

    public String getIs_rai() {
        return is_rai;
    }

    public void setIs_rai(String is_rai) {
        this.is_rai = is_rai;
    }

    public String getIs_ikha() {
        return is_ikha;
    }

    public void setIs_ikha(String is_ikha) {
        this.is_ikha = is_ikha;
    }

    public String getIs_onion() {
        return is_onion;
    }

    public void setIs_onion(String is_onion) {
        this.is_onion = is_onion;
    }

    public String getIs_potato() {
        return is_potato;
    }

    public void setIs_potato(String is_potato) {
        this.is_potato = is_potato;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAawedan_karta_Id() {
        return aawedan_karta_Id;
    }

    public void setAawedan_karta_Id(String aawedan_karta_Id) {
        this.aawedan_karta_Id = aawedan_karta_Id;
    }

    public String getAawedan_karta_Nm() {
        return aawedan_karta_Nm;
    }

    public void setAawedan_karta_Nm(String aawedan_karta_Nm) {
        this.aawedan_karta_Nm = aawedan_karta_Nm;
    }
}
