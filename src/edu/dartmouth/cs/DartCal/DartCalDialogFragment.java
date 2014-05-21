/* CS 65 Smartphone Programming
 * Professor Andrew Campbell
 * MyRuns_2: UI
 * 
 * Created by Lisa Luo
 * Modified 4/12/2014
 * */

package edu.dartmouth.cs.DartCal;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class DartCalDialogFragment extends DialogFragment {

	// App dialog ids
	public static final int DIALOG_ID_ERROR = -1;
	public static final int DIALOG_ID_PHOTO_PICKER = 9;
	public static final int DIALOG_DATE = 0;
	public static final int DIALOG_TIME = 1;
	public static final int DIALOG_DURATION = 2;
	public static final int DIALOG_DISTANCE = 3;
	public static final int DIALOG_CALORIES = 4;
	public static final int DIALOG_HEARTRATE = 5;
	public static final int DIALOG_COMMENT = 6;

	// For photo picker selection:
	public static final int ID_PHOTO_PICKER_FROM_CAMERA = 0;
	public static final int ID_PHOTO_PICKER_FROM_GALLERY = 1;

	private static final String DIALOG_ID_KEY = "dialog_id";
	EditText editComment;

	public static DartCalDialogFragment newInstance(int dialog_id) {
		DartCalDialogFragment frag = new DartCalDialogFragment();
		Bundle args = new Bundle();
		args.putInt(DIALOG_ID_KEY, dialog_id);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int dialog_id = getArguments().getInt(DIALOG_ID_KEY);

		final Activity myActivity = getActivity();

		// Setup dialog appearance and onClick Listeners
		switch (dialog_id) {
		case DIALOG_ID_PHOTO_PICKER:

			// Build picture picker dialog for choosing from camera or gallery
			AlertDialog.Builder photo_builder = new AlertDialog.Builder(
					myActivity);
			photo_builder.setTitle(R.string.ui_profile_photo_picker_title);
			// Set up click listener, firing intents open camera
			DialogInterface.OnClickListener dlistener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					// Item is ID_PHOTO_PICKER_FROM_CAMERA
					// Call the onPhotoPickerItemSelected in the parent
					// activity, i.e., CameraControlActivity in this case
					((EditProfileActivity) myActivity)
							.onPhotoPickerItemSelected(item);
				}
			};

			// Set the item/s to display and create the dialog
			photo_builder.setItems(R.array.ui_profile_photo_picker_items,
					dlistener);
			return photo_builder.create();

		default:
			return null;
		}
	}
}
