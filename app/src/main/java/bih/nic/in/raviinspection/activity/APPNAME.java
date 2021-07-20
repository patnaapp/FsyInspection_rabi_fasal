package bih.nic.in.raviinspection.activity;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;
public class APPNAME implements KvmSerializable {
    public static Class<APPNAME> SCHEME_CLASS = APPNAME.class;

    private String _MobileAppId = "";
    private String _AppName = "";

    public APPNAME() {
        super();
    }

    public APPNAME(SoapObject obj) {
        this._MobileAppId  = obj.getProperty("MobileAppId").toString();
        this._AppName = obj.getProperty("AppName").toString();
//        this.BlockName = obj.getProperty("BlockName").toString();
//        this.BlockCode_PFMS = obj.getProperty("PACSBankID").toString();
    }


    public String get_MobileAppId() {
        return _MobileAppId;
    }

    public void set_MobileAppId(String _MobileAppId) {
        this._MobileAppId = _MobileAppId;
    }

    public String get_AppName() {
        return _AppName;
    }

    public void set_AppName(String _AppName) {
        this._AppName = _AppName;
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
