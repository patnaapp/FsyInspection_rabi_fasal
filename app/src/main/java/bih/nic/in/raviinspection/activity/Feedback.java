package bih.nic.in.raviinspection.activity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Feedback implements KvmSerializable {
    public static Class<Feedback> USER_CLASS = Feedback.class;
    private String _MobileAppname = "";
    private String _messagetype = "";
    private String message = "";
    private String mobile = "";
    private String userid = "";

    public String get_MobileAppname() {
        return _MobileAppname;
    }

    public void set_MobileAppname(String _MobileAppname) {
        this._MobileAppname = _MobileAppname;
    }

    public String get_messagetype() {
        return _messagetype;
    }

    public void set_messagetype(String _messagetype) {
        this._messagetype = _messagetype;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
}
