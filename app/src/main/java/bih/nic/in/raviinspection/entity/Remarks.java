package bih.nic.in.raviinspection.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Remarks implements KvmSerializable {
    public static Class<Remarks> Remarks_CLASS = Remarks.class;
    String  RemarksCode,RemarkName;


    // Empty constructor
    public Remarks() {
    }

    // constructor
    public Remarks(SoapObject obj) {

        this.RemarksCode = obj.getProperty("ProvisionalId").toString();
        this.RemarkName = obj.getProperty("description").toString();

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

    public String getRemarksCode() {
        return RemarksCode;
    }

    public void setRemarksCode(String remarksCode) {
        RemarksCode = remarksCode;
    }

    public String getRemarkName() {
        return RemarkName;
    }

    public void setRemarkName(String remarkName) {
        RemarkName = remarkName;
    }
}
