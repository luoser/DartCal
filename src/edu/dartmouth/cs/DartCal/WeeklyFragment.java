package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class WeeklyFragment extends Fragment {

	private final static int XHOURS_SELECTED = 0;
	private final static int DISPLAY_DIAGRAM_SELECTED = 1;
	private final static int EDIT_PROFILE_SELECTED = 2;

	private MenuItem xHoursMenuItem;
	private MenuItem diagramMenuItem;
	private MenuItem editProfileMenuItem;

	private DrawView drawView;
	public static Context mContext;

	private boolean xHoursOn;

	int course1Time, course2Time, course3Time, course4Time;

	EventDbHelper dbHelper;
	ArrayList<Friend> events;
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.weekly_fragment, container, false);
		drawView = (DrawView) rootView.findViewById(R.id.drawViewWeekly);
		drawView.postInvalidate();

		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mContext = getActivity();

		// TEST PARSING TIME
//		long epoch = System.currentTimeMillis();
//		String timetest = CalendarUtils.parseTime(epoch);
//		int dayOfweek = CalendarUtils.parseDayOfWeek(epoch);
//		System.out.println("timetest " + timetest);
//		System.out.println("datetest " + dayOfweek);
		
		SharedPreferences prefs = mContext.getSharedPreferences(
				"edu.dartmouth.cs.DartCal", Context.MODE_PRIVATE);
		System.out.println(prefs.getAll());

		dbHelper = new EventDbHelper(mContext);

		try {
			events = dbHelper.fetchEntries();

			if (events != null) {
				// retreive course times from the database
				Globals.callOnDraw = true;
				// Globals.xHoursOn = true;
			}

		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onResume() {
		super.onResume();

		// redraw...
		drawView.postInvalidate();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		// add the menu items and set the ids for click listeners
		xHoursMenuItem = menu.add(0, 0, 0, "Toggle X-Hours");
		diagramMenuItem = menu.add(0, 1, 1, "View Schedule Diagram");
		editProfileMenuItem = menu.add(0, 2, 2, "Edit Profile");

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int itemId = item.getItemId();
		Intent intent;

		switch (itemId) {

		// xHours selected
		case (XHOURS_SELECTED):

			if (!xHoursOn) {
				xHoursOn = true;
				Globals.xHoursOn = true;
				Toast.makeText(getActivity(), "X-Hours on", Toast.LENGTH_SHORT)
						.show();
				drawView.postInvalidate();
			}
		
			// TOGGLE x-hours
			if (xHoursOn) {
				Globals.xHoursOn = false;
				xHoursOn = false;
				Toast.makeText(getActivity(), "X-Hours off", Toast.LENGTH_SHORT)
						.show();
				 drawView.postInvalidate();
			}

			break;

		case (DISPLAY_DIAGRAM_SELECTED):
			// will display the official weekly diagram...?
			intent = new Intent(getActivity(), DisplayDiagramActivity.class);
			startActivity(intent);

			break;

		// edit profile selected
		case (EDIT_PROFILE_SELECTED):
			intent = new Intent(getActivity(), EditProfileActivity.class);
			startActivity(intent);

			break;
		}

		return false;
	}

}
