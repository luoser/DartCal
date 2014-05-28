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
	private DrawView drawView;
	View rootView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		rootView = inflater.inflate(R.layout.activity_weeks, container, false);
		drawView = (DrawView) rootView.findViewById(R.id.drawViewTerm);
		drawView.postInvalidate();
		Globals.drawFriendsEventsOn = true;
		
		return rootView;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weeks_calendar);
		mContext=this;
		datasource = new PersonalEventDbHelper(this);
		String myServerURL= mContext.getString(R.string.server_addr)+"/post_data";
		mEventUploader=new EventUploader(mContext, myServerURL);
		Globals.drawEventsOn = true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		datasource = new PersonalEventDbHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weeks_calendar, menu);
		return true;
	}
	public boolean AsyncTask(){
		new AsyncTask<Void,Void,String>(){
			@Override
			protected String doInBackground(Void... arg0){
				Log.i("TAG","IN DA BACKGROUND"+datasource.fetchEntries().toString());
				ArrayList<Event> entryList = datasource.fetchEntries();
				for (int i=0;i<entryList.size();i++){
					Event myEvent=entryList.get(i);
					Log.i("TAG","MY DATABASE"+myEvent.getEventName());
				}
				String stateOfUpload="";
				try{
					mEventUploader.upload(entryList);
				}
				catch(IOException e){
				
				}
				//catch(NullPointerException e){}
				return stateOfUpload;
			}
		}.execute();
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
