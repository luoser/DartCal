package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {

	private static final String TAB_KEY_INDEX = "tab_key";

	private String SENDER_ID = "72048596630";
	CheckBox xHourCheckBox;
	CheckBox officeHoursCheckBox;
	private GoogleCloudMessaging gcm;
	private Context context;
	private String regid;
	public static String PROPERTY_REG_ID = "reg_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	protected static final String TAG = null;

	// final OnClickListener mClickListener = new OnClickListener(){
	// public void onClick(View v){
	// updateFragmentView();
	// }
	// };

	// update fragment visibility based on current check box state

	// CheckBox xHourCheckBox;
	// CheckBox officeHoursCheckBox;

	// final OnClickListener mClickListener = new OnClickListener(){
	// public void onClick(View v){
	// updateFragmentView();
	// }
	// };

	// update fragment visibility based on current check box state
	// this will control the calendar colors...
	// void updateFragmentView(){
	// FragmentTransaction ft = getFragmentManager().beginTransaction();
	//
	// }

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		// ActionBar
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// create new tabs and and set up the titles of the tabs
		ActionBar.Tab mWeeklyTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_weekly));
		ActionBar.Tab mFriendsTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_friends));
		ActionBar.Tab mTermTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_term));

		// create the fragments
		Fragment mWeeklyFragment = new WeeklyFragment();
		Fragment mFriendsFragment = new FriendsFragment();
		Fragment mTermFragment = new TermFragment();

		// bind the fragments to the tabs - set up tabListeners for each tab
		mWeeklyTab.setTabListener(new MyTabsListener(mWeeklyFragment,
				getApplicationContext()));
		mFriendsTab.setTabListener(new MyTabsListener(mFriendsFragment,
				getApplicationContext()));
		mTermTab.setTabListener(new MyTabsListener(mTermFragment,
				getApplicationContext()));

		// add the tabs to the action bar
		actionbar.addTab(mWeeklyTab);
		actionbar.addTab(mFriendsTab);
		actionbar.addTab(mTermTab);

		// Watch check box clicks
		// xHourCheckBox = (CheckBox) findViewById(R.id.xHourCheckBox);
		// xHourCheckBox.setOnClickListener(mClickListener);
		// officeHoursCheckBox = (CheckBox) findViewById(R.id.xHourCheckBox);
		// officeHoursCheckBox.setOnClickListener(mClickListener);

		// restore to navigation
		if (savedInstanceState != null) {
			Toast.makeText(getApplicationContext(),
					"tab is " + savedInstanceState.getInt(TAB_KEY_INDEX, 0),
					Toast.LENGTH_SHORT).show();

			actionbar.setSelectedNavigationItem(savedInstanceState.getInt(
					TAB_KEY_INDEX, 0));
		}

		// Check device for Play Services APK. If check succeeds, proceed with
		// GCM registration.
		/*
		 * if (checkPlayServices()) { gcm =
		 * GoogleCloudMessaging.getInstance(this); regid =
		 * getRegistrationId(context);
		 * 
		 * if (regid.isEmpty()) { registerInBackground(); }
		 * 
		 * }
		 */
	}

	public String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			return "";
		}
		return registrationId;
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				finish();
			}
			return false;
		}
		return true;
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.
					// The request to your server should be authenticated if
					// your app
					// is using accounts.
					ServerUtilities.sendRegistrationIdToBackend(context, regid);

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the
					// message using the 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Log.i(TAG, "gcm register msg: " + msg);
			}
		}.execute(null, null, null);
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.main, menu);
	// return true;
	// }

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.main, menu);
	// return true;
	// }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Toast.makeText(
				this,
				"onSaveInstanceState: tab is"
						+ getActionBar().getSelectedNavigationIndex(),
				Toast.LENGTH_SHORT).show();
		outState.putInt(TAB_KEY_INDEX, getActionBar()
				.getSelectedNavigationIndex());

	}

}

// TabListenr class for managing user interaction with the ActionBar tabs. The
// application context is passed in pass it in constructor, needed for the
// toast.

class MyTabsListener implements ActionBar.TabListener {
	public Fragment fragment;
	public Context context;

	public MyTabsListener(Fragment fragment, Context context) {
		this.fragment = fragment;
		this.context = context;

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}
}
