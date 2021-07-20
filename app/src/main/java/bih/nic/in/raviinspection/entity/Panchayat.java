package bih.nic.in.raviinspection.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Panchayat implements KvmSerializable {

    public static Class<Panchayat> Panchayat_CLASS = Panchayat.class;
    int _id;
    String Userid, PanchayatCode, PanchayatName,BlockCode;
    public boolean _isAuthenticated = false;

    // Empty constructor
    public Panchayat() {
    }

    // constructor
    public Panchayat(SoapObject obj) {

        this.set_isAuthenticated(Boolean.parseBoolean(obj.getProperty(
                "isAuthenticated").toString()));


        this.Userid = obj.getProperty("userid").toString();
        this.PanchayatCode = obj.getProperty("Panchayat").toString();
        this.PanchayatName = obj.getProperty("PanchayatName").toString();
        this.BlockCode = obj.getProperty("blockcode").toString();


    }


    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public Object getProperty(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
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

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }

    public boolean is_isAuthenticated() {
        return _isAuthenticated;
    }

    public void set_isAuthenticated(boolean _isAuthenticated) {
        this._isAuthenticated = _isAuthenticated;
    }
}
