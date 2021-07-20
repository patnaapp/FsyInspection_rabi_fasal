package bih.nic.in.raviinspection.utilities;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import bih.nic.in.raviinspection.entity.UserDetails;


public class CommonPref {

	static Context context;

	CommonPref() {

	}

	CommonPref(Context context) {
		CommonPref.context = context;
	}



	public static void setUserDetails(Context context, UserDetails userInfo) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = prefs.edit();

		editor.putString("UserID", userInfo.getUserID());
		editor.putString("UserName", userInfo.getUserName());
		editor.putString("Password", userInfo.getPassword());
		editor.putString("Role", userInfo.getRole());
		editor.putString("DistCode", userInfo.getDistCode());
		editor.putString("DistName", userInfo.getDistName());
		editor.putString("BlockCode", userInfo.getBlockCode());
		editor.putString("BlockName", userInfo.getBlockName());
		editor.putString("IMEI", userInfo.getIMEI());



		editor.commit();

	}

	public static UserDetails getUserDetails(Context context) {

		String key = "_USER_DETAILS";
		UserDetails userInfo = new UserDetails();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		userInfo.setUserID(prefs.getString("UserID", ""));
		userInfo.setUserName(prefs.getString("UserName", ""));
		userInfo.setPassword(prefs.getString("Password", ""));
		userInfo.setRole(prefs.getString("Role", ""));
		userInfo.setDistCode(prefs.getString("DistCode", ""));
		userInfo.setDistName(prefs.getString("DistName", ""));
		userInfo.setBlockCode(prefs.getString("BlockCode", ""));
		userInfo.setBlockName(prefs.getString("BlockName", ""));
		userInfo.setIMEI(prefs.getString("IMEI", ""));


		return userInfo;
	}

	

	public static void setCheckUpdate(Context context, long dateTime) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		
		dateTime=dateTime+1*3600000;
		editor.putLong("LastVisitedDate", dateTime);

		editor.commit();

	}

	public static int getCheckUpdate(Context context) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		long a = prefs.getLong("LastVisitedDate", 0);

		
		if(System.currentTimeMillis()>a)
			return 1;
		else
			return 0;
	}

	public static void setAwcId(Activity activity, String awcid){
		String key = "_Awcid";
		SharedPreferences prefs = activity.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("code2", awcid);
		editor.commit();
	}


}
