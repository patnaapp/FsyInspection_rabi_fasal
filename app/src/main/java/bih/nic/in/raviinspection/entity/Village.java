package bih.nic.in.raviinspection.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Village implements KvmSerializable {

    public static Class<Village> Village_CLASS = Village.class;
    String  PanchayatCode, VillageCode,VillageName;


    // Empty constructor
    public Village() {
    }

    // constructor
    public Village(SoapObject obj) {

        this.PanchayatCode = obj.getProperty("PanchayatCode").toString();
        this.VillageCode = obj.getProperty("VILLCODE").toString();
        this.VillageName = obj.getProperty("VILLNAME").toString();


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

    public String getPanchayatCode() {
        return PanchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        PanchayatCode = panchayatCode;
    }

    public String getVillageCode() {
        return VillageCode;
    }

    public void setVillageCode(String villageCode) {
        VillageCode = villageCode;
    }

    public String getVillageName() {
        return VillageName;
    }

    public void setVillageName(String villageName) {
        VillageName = villageName;
    }
}
