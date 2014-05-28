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
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class WeeksCalendar extends Activity {
	public PersonalEventDbHelper datasource;
	public Context mContext;
	private EventUploader mEventUploader;
	//private ActivityEntriesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weeks_calendar);
		mContext=this;
		datasource = new PersonalEventDbHelper(this);
		String myServerURL= mContext.getString(R.string.server_addr)+"/post_data";
		mEventUploader=new EventUploader(mContext, myServerURL);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		datasource = new PersonalEventDbHelper(this);
		/*
		try {
			Log.i("TAG","MY ID BE ALL LIKE "+Long.toString(datasource.fetchEntryByIndex(0).getId())+"MY NAME IS "+
					datasource.fetchEntryByIndex(0).getSchedule().get(0).getEventName());
		}
		catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		//NEED TO PASS IN THE VALUES
		//ArrayList<ExerciseEntry> values = datasource.fetchEntries();
		/*	
		adapter=new ActivityEntriesAdapter(mContext);
		ArrayList<ExerciseEntry> values = datasource.fetchEntries();
		
		setListAdapter(adapter);
		adapter.addAll(values);
		adapter.notifyDataSetChanged();
		*/
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
				Log.i("TAG","IN DOINBACKGROUND");
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
		case R.id.menuitem_sync:
			Log.i("TAG","Sync button has been clicked");
			AsyncTask();
			/*
			new AsyncTask<Void,Void,String>(){
				@Override
				protected String doInBackground(Void... arg0){
					Log.i("TAG","IN DOINBACKGROUND");
					Log.i("TAG","IN DA BACKGROUND"+datasource.fetchEntries().toString());
					ArrayList<Event> entryList = datasource.fetchEntries();
					
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
	*/
		}
		return true;


	}
}
