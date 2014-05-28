package edu.dartmouth.cs.DartCal;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WeeksActivity extends ListActivity {
	
	ArrayAdapter<String> mAdapter;
	
	static final String[] DATES = new String[] {"Week 1: March 22 - March 29","Week 2: March 30 - April 5",
		"Week 3: April 6 - April 12","Week 4: April 13 - April 19", "Week 5: April 20 - April 26", 
		"Week 6: April 27 - May 3", "Week 7: May 4 - May 10", "Week 8: May 11 - May 17", 
		"Week 9: May 18 - May 24", "Week 10: May 25 - May 31","Week 11: June 1 - June 3"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Define a new adapter
		mAdapter = new ArrayAdapter<String>(this,
				R.layout.activity_weeks, DATES);

		// Assign the adapter to ListView
		setListAdapter(mAdapter);

		// Define the listener interface
		OnItemClickListener mListener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Bundle myExtras=getIntent().getExtras();
				int myActivityType=myExtras.getInt("Activity Type");
				Log.i("TAG","MYACTIVITY TYPE "+Integer.toString(myActivityType));
				Intent intent = new Intent();
				//Bundle extras = new Bundle();
				//extras.putInt("Week", position);
				
//				int pos = mAdapter.getPosition();
				
				
				//intent.putExtras(extras);
				if (myActivityType==0){
					intent.setClass(WeeksActivity.this, PersonalWeeksCalendar.class);
					startActivity(intent);
				}
				else{
				intent.setClass(WeeksActivity.this, WeeksCalendar.class);
				startActivity(intent);
				}
			}
		};

		// Get the ListView and wired the listener
		ListView listView = getListView();
		listView.setOnItemClickListener(mListener);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weeks, menu);
		return true;
	}

}
