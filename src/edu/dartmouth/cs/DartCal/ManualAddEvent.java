package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class ManualAddEvent extends Activity {
	private EventDbHelper datasource;
	private Friend mFriend;
	private Event mEvent;
	ArrayList<Event> mySchedule;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_add_event);
		mySchedule=new ArrayList<Event>();
		datasource = new EventDbHelper(this);
		mEvent=new Event();
		mFriend=new Friend();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manual_add_event, menu);
		return true;
	}
	public void onSaveBtClicked(View v) throws IOException {
		//Event Name
		EditText mEdit1;
		mEdit1   = (EditText)findViewById(R.id.editText1);
		String eventName=mEdit1.getText().toString();
		Log.i("TAG",eventName);
		//Location
		EditText mEdit2;
		mEdit2   = (EditText)findViewById(R.id.editText2);
		String location=mEdit2.getText().toString();
		//Description
		EditText mEdit3;
		mEdit3   = (EditText)findViewById(R.id.editText3);
		String description=mEdit3.getText().toString();
		//Start Time
		TimePicker mTimePicker1;
		mTimePicker1   = (TimePicker)findViewById(R.id.timePicker1);
		Time startTime = new Time();
		startTime.hour=mTimePicker1.getCurrentHour();
		startTime.minute=mTimePicker1.getCurrentMinute();
		long myStartTime = startTime.toMillis(false);
		Log.i("TAG",Long.toString(myStartTime));
		//End Time
		TimePicker mTimePicker2;
		mTimePicker2 = (TimePicker) findViewById(R.id.timePicker2);
		Time endTime = new Time();
		endTime.hour = mTimePicker2.getCurrentHour();
		endTime.minute = mTimePicker2.getCurrentMinute();
		long myEndTime = startTime.toMillis(false);
		//Date
		DatePicker mDatePicker1;
		mDatePicker1= (DatePicker) findViewById(R.id.datePicker1);
		Date date=new Date();
		long myDate=date.getTime();
		//Repeating
		int repeating=3;
		SharedPreferences settings =PreferenceManager.getDefaultSharedPreferences(this);
		String repeatingPreference=settings.getString("repeating_event","0");
		if (repeatingPreference.equals("0")){
			repeating=0;
		}
		if (repeatingPreference.equals("1")) {
			repeating=1;
		}
		if (repeatingPreference.equals("2")) {
			repeating=2;
		}
		mEvent.setEventName(eventName);
		mEvent.setDate(myDate);
		mEvent.setEventLocation(location);
		mEvent.setStartTime(myStartTime);
		mEvent.setEndTime(myEndTime);
		mEvent.setEventDescription(description);
		mEvent.setRepeating(repeating);
		
		ArrayList<Event> mySchedule=new ArrayList<Event>();
		mySchedule.add(mEvent);
		mFriend.setSchedule(mySchedule);
		datasource.insertEntry(mFriend);
		finish();
	}

	public void onCancelBtClicked(View v) {

		// Close the activity
		finish();
	}

	public static class SettingsFragment extends PreferenceFragment {
    public static final String KEY_PREF_SYNC_CONN = "pref_syncConnectionType";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
    /*
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("switch_preference")) {
            Toast.makeText(getActivity(), "Switch", Toast.LENGTH_LONG).show();
        }
    }
    */
}

}