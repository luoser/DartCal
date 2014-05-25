package edu.dartmouth.cs.DartCal;

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
	private final static int OFFICE_HOURS_SELECTED = 1;
	private final static int EDIT_PROFILE_SELECTED = 2;

	MenuItem xHours;
	MenuItem officeHours;
	MenuItem editProfile;

	DrawView drawView;
	Context mContext = getActivity();
	public static boolean xHoursOn = false;
	
	int course1Time, course2Time, course3Time, course4Time;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.weekly_fragment, container, false);
		drawView = (DrawView) rootView.findViewById(R.id.drawView);
		drawView.postInvalidate();
		
		
		return rootView;
		// Inflate the layout for this fragment
//		return inflater.inflate(R.layout.weekly_fragment, container, false);
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
//		drawView = (DrawView) getActivity().findViewById(R.id.drawView); // !!!!!!???!
//		drawView.postInvalidate();
		
		SharedPreferences prefs = getActivity().getSharedPreferences("edu.dartmouth.cs.DartCal", Context.MODE_PRIVATE);
		course1Time = prefs.getInt(Globals.COURSE1_TIME_KEY, -1);
		course2Time = prefs.getInt(Globals.COURSE2_TIME_KEY, -1);
		course3Time = prefs.getInt(Globals.COURSE3_TIME_KEY, -1);
		course4Time = prefs.getInt(Globals.COURSE4_TIME_KEY, -1);
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		// redraw...
		drawView.postInvalidate();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		// add the menu items and set the ids for click listeners
		xHours = menu.add(0, 0, 0, "X-Hours");
		officeHours = menu.add(0, 1, 1, "View Schedule Diagram");
		editProfile = menu.add(0, 2, 2, "Edit Profile");

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		System.out.println(item.getItemId());

		int itemId = item.getItemId();

		switch (itemId) {
		// xHours selected
		case (XHOURS_SELECTED):
			xHoursOn = true;
			Toast.makeText(getActivity(), "X-Hours on", Toast.LENGTH_SHORT)
					.show();

			break;

		case (OFFICE_HOURS_SELECTED):
			// will display the official weekly diagram...?
			// Intent intent = new Intent(getActivity(), displayDiagram.class);
			// startActivity(intent);

			break;

		// edit profile selected
		case (EDIT_PROFILE_SELECTED):
			Intent intent = new Intent(getActivity(), EditProfileActivity.class);
			startActivity(intent);
			
			break;
		}

		return false;
	}

}
