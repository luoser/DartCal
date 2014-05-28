package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PersonalWeeksCalendar extends Activity {
	public PersonalEventDbHelper datasource;
	public Context mContext;
	private EventUploader mEventUploader;
	//private ActivityEntriesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		setContentView(R.layout.activity_personal_weeks_calendar);
		datasource = new PersonalEventDbHelper(this);


	}
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_weeks_calendar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem_add:
			Intent intent = new Intent();
			intent.setClass(this,ManualAddEvent.class);
			startActivity(intent);
			return true;
	}
		return true;
}
	
}

