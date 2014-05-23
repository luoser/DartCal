package edu.dartmouth.cs.DartCal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class WeeklyFragment extends Fragment {

	private int XHOURS_SELECTED = 0;
	private int OFFICE_HOURS_SELECTED = 1;
	private int EDIT_PROFILE_SELECTED = 2;

	MenuItem xHours;
	MenuItem officeHours;
	MenuItem editProfile;

	DrawView drawView;
	Context mContext = getActivity();

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
		case (0):
			Toast.makeText(getActivity(), "X-Hours on", Toast.LENGTH_SHORT)
					.show();
			break;
		case (1):
			// will display the official weekly diagram...?
//			Intent intent = new Intent(getActivity(), displayDiagram.class);
//			startActivity(intent);
			break;

		// edit profile selected
		case (2):
			Intent intent = new Intent(getActivity(), EditProfileActivity.class);
			startActivity(intent);
			break;
		}

		return false;
	}

	private class MyDrawable extends Drawable {

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			canvas.drawLine(0, 0, 50, 50, paint);
			canvas.drawLine(50, 0, 50, 50, paint);
		}

		@Override
		public int getOpacity() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setAlpha(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setColorFilter(ColorFilter arg0) {
			// TODO Auto-generated method stub
		}
	}

}
