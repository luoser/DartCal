package edu.dartmouth.cs.DartCal;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class WeeklyFragment extends Fragment {

	private int XHOURS_SELECTED = 0;
	private int OFFICE_HOURS_SELECTED = 1;
	private int EDIT_PROFILE_SELECTED = 2;

	MenuItem xHours;
	MenuItem officeHours;
	MenuItem editProfile;

	// CheckBox xHours;
	// CheckBox officeHours;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.weekly_fragment, container, false);
	}

	// @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		// add the menu items and set the ids for click listeners
		xHours = menu.add(0, 0, 0, "X-Hours");
		officeHours = menu.add(0, 1, 1, "Office Hours");
		editProfile = menu.add(0, 2, 2, "Edit Profile");

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		System.out.println(item.getItemId());

		int itemId = item.getItemId();

		switch (itemId) {
		case (0):
			Toast.makeText(getActivity(), "X-Hours on", Toast.LENGTH_SHORT)
					.show();
			break;
		case (1):
			Toast.makeText(getActivity(), "Office Hours on", Toast.LENGTH_SHORT)
					.show();
			break;
		case (2):
			Intent intent = new Intent(getActivity(), EditProfileActivity.class);
			startActivity(intent);
			break;
		}

		return false;
	}

	// this function will get the data input by the user from the edit profile
	// activity
	public void retrieveCourseData() {

	}

	// this will draw the specific block for the period onto the calendar
	public void drawPeriod(String period) {

	}

	// this function will draw the blocks onto the schedule.
	public void drawCourses(String course1, String course2, String course3,
			String course4) {

		if (!course1.isEmpty())
			drawPeriod(course1);

		if (!course2.isEmpty())
			drawPeriod(course2);

		if (!course3.isEmpty())
			drawPeriod(course3);

		if (!course4.isEmpty())
			drawPeriod(course4);

	}

}

// class to help draw things
abstract class DrawView extends View {
	Paint paint = new Paint();

	public DrawView(Context context) {
		super(context);
	}

}
