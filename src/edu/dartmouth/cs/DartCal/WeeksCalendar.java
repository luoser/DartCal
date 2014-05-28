package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class WeeksCalendar extends Activity {
	public PersonalEventDbHelper datasource;
	public Context mContext;
	private EventUploader mEventUploader;
	private DrawViewPersonal drawView;
	View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_weeks_calendar,
				container, false);
		drawView = (DrawViewPersonal) rootView
				.findViewById(R.id.drawViewPersonal);
		drawView.postInvalidate();

		return rootView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weeks_calendar);
		mContext = this;
		Globals.drawFriendsEventsOn = true;

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weeks_calendar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem_add:
			Intent intent = new Intent();
			intent.setClass(this, ManualAddEvent.class);
			startActivity(intent);
			return true;

		}
		return true;

	}
}
