package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class EventUploader {
	private Context mContext;
	private String mServerUrl;
	private String REG_ID="regId";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private String PROPERTY_REG_ID="reg_id";
	
	
	public EventUploader(Context context, String myServerURL) {
		mContext=context;
		mServerUrl=myServerURL;
	}

	public boolean upload(ArrayList<Event> entryList) throws IOException{
		Log.i("TAG","GETTING INSIDE UPLOAD?");
		JSONArray jsonArray = new JSONArray();
		for (int i=0;i<entryList.size();i++){
			Event entry = entryList.get(i);
			jsonArray.put(entry.toJSONObject());
		}
		String myJSONArrayString=jsonArray.toString();
		SharedPreferences GCMPreferences= mContext.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		final String regId = GCMPreferences.getString(MainActivity.PROPERTY_REG_ID,"");
		Log.i("TAG","MY REGISTRATION ID"+ regId);
		Map<String, String> params = new HashMap<String, String>();
		params.put(REG_ID, regId);
		params.put("data",myJSONArrayString);
		ServerUtilities.post(mServerUrl, params);
		return true;
	}
}
