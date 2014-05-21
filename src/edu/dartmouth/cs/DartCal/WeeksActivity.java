package edu.dartmouth.cs.DartCal;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WeeksActivity extends ListActivity {
	
	static final String[] DATES = new String[] {"Week 1: September 15- September 21","Week 2: September 22- September 28",
		"Week 3: September 19- October 5","Week 4: October 6- October 12", "Week 5: October 13- October 19", 
		"Week 6: October 20- October 26", "Week 7: October 27- November 2", "Week 8: November 3- November 9", 
		"Week 9: November 10- November 16", "Week 10: November 17- November 23","Week 11: November 24- November 30"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Define a new adapter
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
				R.layout.activity_weeks, DATES);

		// Assign the adapter to ListView
		setListAdapter(mAdapter);

		// Define the listener interface
		OnItemClickListener mListener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent();
				Bundle extras = new Bundle();
				extras.putInt("Week", position);
				intent.putExtras(extras);
				intent.setClass(WeeksActivity.this, WeeksCalendar.class);
				startActivity(intent);
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
