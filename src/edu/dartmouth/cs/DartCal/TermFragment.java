package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TermFragment extends Fragment {
	private EventUploader mEventUploader;

	@Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {	
		View v=inflater.inflate(R.layout.term_fragment, container, false);
		Button personalButton;
		personalButton= (Button) v.findViewById(R.id.btnPersonalCalendar);
		Log.i("TAG",personalButton.toString());
		personalButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onPersonalBtClicked(arg0);			
			}
		});
		Button dartmouthButton;
		dartmouthButton= (Button) v.findViewById(R.id.btnDartmouthCalendar);
		dartmouthButton.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				onPersonalBtClicked(arg0);			
			}
		});
		
      // Inflate the layout for this fragment
      return v;
  }
	public void onPersonalBtClicked(View v){
		Log.i("TAG","ON SYNC BUTTON IS GETTING CALLED");
		/*
		new AsyncTask<Void,Void,String>(){
			@Override
			protected String doInBackground(Void... arg0){
				Log.i("TAG","IN DOINBACKGROUND");
				EventDbHelper datasource=new EventDbHelper(getActivity());
				ArrayList<Event> entryList = datasource.fetchEntryByIndex();
				String stateOfUpload="";
				try{
					mEventUploader.upload(entryList);
					Log.i("TAG","GETTING INSIDE TRY ONSYNC");
				}
				catch(IOException e){
				
				}
				return stateOfUpload;
			}
		}.execute();
		*/
		Intent intent=new Intent();
		Bundle extras= new Bundle();
		extras.putInt("Activity Type", 0);
		intent.putExtras(extras);
		intent.setClass(getActivity(), WeeksActivity.class);
		startActivity(intent);
	}

	public void onDartmouthBtClicked(View v){
		/*
		Log.i("TAG","ON SYNC BUTTON IS GETTING CALLED");
		new AsyncTask<Void,Void,String>(){
			@Override
			protected String doInBackground(Void... arg0){
				Log.i("TAG","IN DOINBACKGROUND");
				EventDbHelper datasource=new EventDbHelper(getActivity());
				ArrayList<Event> entryList = datasource.fetchEntryByIndex();
				String stateOfUpload="";
				try{
					mEventUploader.upload(entryList);
					Log.i("TAG","GETTING INSIDE TRY ONSYNC");
				}
				catch(IOException e){
					
				}
				return stateOfUpload;
			}
		}.execute();
	*/

		Intent intent=new Intent();
		Bundle extras= new Bundle();
		extras.putInt("Activity Type", 1);
		intent.putExtras(extras);
		intent.setClass(getActivity(), WeeksActivity.class);
		startActivity(intent);
	}

}
