package edu.dartmouth.cs.DartCal;

import java.text.DecimalFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;


public class DisplayEntryActivity extends Activity {
	public Context mContext;
	private EditText eventName;
	private EditText location;
	private EditText startTime;
	private EditText endTime;
	private EditText eventDate;

	
	private static final String KEY_ROWID= "row_id";
	private static final String KEY_EVENT_NAME= "event_name";
	private static final String KEY_LOCATION= "location";
	private static final String KEY_DESCRIPTION= "description";
	private static final String KEY_START_TIME= "start_time";
	private static final String KEY_END_TIME= "end_time";
	private static final String KEY_EVENT_DATE="event_date";
	private static final String TAG = "TAG";
	private long myId;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_entry);
		mContext=this;
		
		Bundle extras=getIntent().getExtras();
		myId=extras.getLong(KEY_ROWID,-1);
		String myEvent=extras.getString(KEY_EVENT_NAME);
		String myLocation=extras.getString(KEY_LOCATION);
		String myDescription=extras.getString(KEY_DESCRIPTION);
		float myStartTime=extras.getFloat(KEY_START_TIME,-1);
		float myEndTime=extras.getFloat(KEY_END_TIME,-1);
		
				
		eventName= (EditText) findViewById(R.id.editText1);
		eventName.setText(myEvent);
		/*
		dateTime= (EditText) findViewById(R.id.editText3);
		dateTime.setText(text);
		
		duration= (EditText) findViewById(R.id.editText2);
		duration.setText(Integer.toString(myDuration/60)+"mins 0secs");
		
		distance= (EditText) findViewById(R.id.editText4);
		SharedPreferences settings=PreferenceManager.getDefaultSharedPreferences(this);
		String distancePreference=settings.getString("unit_preference","0");
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		if (distancePreference.equals("0")){
			distance.setText(decimalFormat.format(myDistance*0.000621371)+" Miles");
		}
		if (distancePreference.equals("1")){
			distance.setText(decimalFormat.format(myDistance/1000)+" Kilometers");
		}

		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_entry, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem_delete:
			PersonalEventDbHelper dbHelper= new PersonalEventDbHelper(this.getBaseContext());
			dbHelper.removeEntry(myId);
			Log.i(TAG, "Line"+Long.toString(myId));
			finish();
			return true;
	}
		return true;

}

}
