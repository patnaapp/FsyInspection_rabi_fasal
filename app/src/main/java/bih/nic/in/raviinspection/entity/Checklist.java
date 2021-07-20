package bih.nic.in.raviinspection.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class Checklist implements KvmSerializable,Serializable {

    private static final long serialVersionUID = 1L;

    public static Class<Checklist> Gender_CLASS = Checklist.class;

    private String Checklist_Id = "";
    private String Checklist_Name = "";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static Class<Checklist> getGender_CLASS() {
        return Gender_CLASS;
    }

    public static void setGender_CLASS(Class<Checklist> gender_CLASS) {
        Gender_CLASS = gender_CLASS;
    }

    public String getChecklist_Id() {
        return Checklist_Id;
    }

    public void setChecklist_Id(String checklist_Id) {
        Checklist_Id = checklist_Id;
    }

    public String getChecklist_Name() {
        return Checklist_Name;
    }

    public void setChecklist_Name(String checklist_Name) {
        Checklist_Name = checklist_Name;
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
