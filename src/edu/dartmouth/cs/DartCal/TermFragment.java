package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.io.StreamCorruptedException;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TermFragment extends Fragment {
	private EventUploader mEventUploader;
	private Intent intent;
	private Bundle extras;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.term_fragment, container, false);
		Button personalButton;
		personalButton = (Button) v.findViewById(R.id.btnPersonalCalendar);
		Log.i("TAG", personalButton.toString());
		personalButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onPersonalBtClicked(arg0);
			}
		});
		Button dartmouthButton;
		dartmouthButton = (Button) v.findViewById(R.id.btnDartmouthCalendar);
		dartmouthButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onDartmouthBtClicked(arg0);
			}
		});

		// Inflate the layout for this fragment
		return v;
	}

	public void onPersonalBtClicked(View v) {
		intent = new Intent();
		extras = new Bundle();
		extras.putInt("Activity Type", 0);
		intent.putExtras(extras);
		intent.setClass(getActivity(), WeeksActivity.class);
		startActivity(intent);
	}

	public void onDartmouthBtClicked(View v) {

		intent = new Intent();
		extras = new Bundle();
		extras.putInt("Activity Type", 1);
		intent.putExtras(extras);
		intent.setClass(getActivity(), WeeksActivity.class);
		startActivity(intent);
	}

}
