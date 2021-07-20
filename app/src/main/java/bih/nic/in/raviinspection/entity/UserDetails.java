package bih.nic.in.raviinspection.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

/**
 * Created by NICSI on 3/15/2018.
 */

public class UserDetails implements KvmSerializable {

    public static Class<UserDetails> USER_CLASS = UserDetails.class;

    public boolean _isAuthenticated = false;
    private String id;
    private String UserID = "";
    //private String _isAuthenticated = "";
    private String UserName = "";
    private String Password = "";
    private String Role = "";
    private String DistCode = "";
    private String DistName = "";
    private String BlockCode = "";
    private String BlockName = "";
    private String IMEI = "";

    public UserDetails() {
    }

    public UserDetails(SoapObject obj) {

        this.set_isAuthenticated(Boolean.parseBoolean(obj.getProperty(
                "isAuthenticated").toString()));
        this.setUserID(obj.getProperty("UserID").toString());
        this.setUserName(obj.getProperty("UserName").toString());
        this.setPassword(obj.getProperty("Password").toString());
        this.setRole(obj.getProperty("Role").toString());
        if((obj.getProperty("DistCode").toString()).equalsIgnoreCase("anyType{}")){
            this.setDistCode("");
        }else{
            this.setDistCode(obj.getProperty("DistCode").toString());
        }
        if((obj.getProperty("DistName").toString()).equalsIgnoreCase("anyType{}")){
            this.setDistName("");
        }else{
            this.setDistName(obj.getProperty("DistName").toString());
        }
        if((obj.getProperty("BlockCode").toString()).equalsIgnoreCase("anyType{}")){
            this.setBlockCode("");
        }else{
            this.setBlockCode(obj.getProperty("BlockCode").toString());
        }
        if((obj.getProperty("BlockName").toString()).equalsIgnoreCase("anyType{}")){
            this.setBlockName("");
        }else{
            this.setBlockName(obj.getProperty("BlockName").toString());
        }
        this.setIMEI(obj.getProperty("IMEI").toString());
        //this.set_isAuthenticated(obj.getProperty("isAuthenticated").toString());

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

    public static Class<UserDetails> getUserClass() {
        return USER_CLASS;
    }

    public static void setUserClass(Class<UserDetails> userClass) {
        USER_CLASS = userClass;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
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

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean is_isAuthenticated() {
        return _isAuthenticated;
    }

    public void set_isAuthenticated(boolean _isAuthenticated) {
        this._isAuthenticated = _isAuthenticated;
    }

}
