package edu.dartmouth.cs.DartCal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class EditProfileActivity extends Activity {
	
	public static final int REQUEST_CODE_TAKE_FROM_CAMERA = 0;
	public static final int REQUEST_CODE_CHOOSE_FROM_GALLERY = 1;
	public static final int REQUEST_CODE_CROP_PHOTO = 2;
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private static final String URI_INSTANCE_STATE_KEY = "saved_uri";
	private static final String TMP_IMAGE_KEY = "temp";

	private Uri mImageCaptureUri;
	private ImageView mImageView;
	private boolean isTakenFromCamera;

	// Temporary buffer for storing profile image.
	private byte[] bytePhoto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		mImageView = (ImageView) findViewById(R.id.imageProfile);

		// If not loading up for the first time, get the image from the saved
		// Instance State.
		if (savedInstanceState != null) {
			// Handle the screen rotation here.
			// Restore the image uri.
			mImageCaptureUri = savedInstanceState
					.getParcelable(URI_INSTANCE_STATE_KEY);

			// Retrieve the temporarily store byte array.
			bytePhoto = savedInstanceState.getByteArray(TMP_IMAGE_KEY);
			loadImage();
		}

		// If loading up for the first time, there is no saved Instance State
		// so just load the user data.
		else {
			loadUserData();
		}
	}
	
	// avoid popping up right away.........???
	public void supressKeyboard() {
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
	}

	@Override
	// This function is called before onCreate(); together they handle the data
	// loaded when the function is called or recalled.
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save the image capture uri before the activity goes into background
		outState.putParcelable(URI_INSTANCE_STATE_KEY, mImageCaptureUri);

		// Save profile image to internal storage.
		if (bytePhoto != null) {
			// Save byte array to bundle. This will keep the photo intact as you
			// rotate.
			outState.putByteArray(TMP_IMAGE_KEY, bytePhoto);
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	// BUTTON CLICKS
	// Save user data, including picture, and close the application.
	public void onSaveClicked(View v) {
		
		
		
		saveUserData();
		// Make a Toast informing the user their information is saved.
		Toast.makeText(getApplicationContext(),
				"Saved", Toast.LENGTH_SHORT).show();
		finish();
	}

	// Display a Toast and cancel application.
	public void onCancelClicked(View v) {
		Toast.makeText(getApplicationContext(),
				"Cancelled", Toast.LENGTH_SHORT).show();
		// Close the activity.
		finish();
	}

	// Display a dialog box to allow user to change profile image.
	public void onChangePhotoClicked(View v) {
		displayDialog(DartCalDialogFragment.DIALOG_ID_PHOTO_PICKER);
	}

	// Handle image data after activity returns, i.e. after photo is either
	// taken or selected.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			Log.d("CS65", "Camera error: result not ok.");
			return;
		}

		switch (requestCode) {
		case REQUEST_CODE_CHOOSE_FROM_GALLERY:
			// Allow user to choose image from internal gallery.
			Log.d("CS65", "entered choose from gallery request");
			mImageCaptureUri = data.getData();
			cropImage();
			break;

		case REQUEST_CODE_TAKE_FROM_CAMERA:
			// Allow user to take a photo from the camera.
			// Send image taken from camera for cropping
			cropImage();
			break;

		case REQUEST_CODE_CROP_PHOTO:
			// Update image view after image crop.
			Bundle extras = data.getExtras();
			// Set the picture image in UI
			if (extras != null) {
				// Convert bitmap to a byte array and load.
				Bitmap photo = (Bitmap) extras.getParcelable("data");
				bitToByte(photo);
				loadImage();
			}

			// Delete temporary image taken by camera after crop.
			if (isTakenFromCamera) {
				File f = new File(mImageCaptureUri.getPath());
				if (f.exists())
					f.delete();
			}
			break;
		}
	}

	// PHOTO PICKER DIALOG
	public void displayDialog(int id) {
		DialogFragment fragment = DartCalDialogFragment.newInstance(id);
		fragment.show(getFragmentManager(),
				getString(R.string.dialog_fragment_tag_photo_picker));
	}

	public void onPhotoPickerItemSelected(int item) {
		Intent intent;

		switch (item) {
		case DartCalDialogFragment.ID_PHOTO_PICKER_FROM_GALLERY:
			// Select a photo from the camera gallery.
			Intent pickPhoto = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(pickPhoto, REQUEST_CODE_CHOOSE_FROM_GALLERY);
			isTakenFromCamera = false;
			break;

		case DartCalDialogFragment.ID_PHOTO_PICKER_FROM_CAMERA:
			// Take photo from camera.
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// Construct temporary image path and name to save the taken
			// photo
			mImageCaptureUri = Uri.fromFile(new File(Environment
					.getExternalStorageDirectory(), "tmp_"
					+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
					mImageCaptureUri);
			intent.putExtra("return-data", true);
			try {
				// Start a camera capturing activity.
				// REQUEST_CODE_TAKE_FROM_CAMERA is an integer tag you
				// defined to identify the activity in onActivityResult()
				// when it returns
				startActivityForResult(intent, REQUEST_CODE_TAKE_FROM_CAMERA);
			} catch (ActivityNotFoundException e) {
				e.printStackTrace();
			}
			isTakenFromCamera = true;
			break;

		default:
			return;
		}
	}

	// PRIVATE HELPER FUNCTIONS
	private void loadImage() {
		// Load byte array photo to view.
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytePhoto);
			Bitmap bmap = BitmapFactory.decodeStream(bis);
			mImageView.setImageBitmap(bmap);
			bis.close();
		} catch (Exception ex) {
			// Default profile photo if no photo saved before.
			mImageView.setImageResource(R.drawable.ic_launcher);
		}
	}

	// Convert the bitmap image to the byte array
	private void bitToByte(Bitmap bmap) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bytePhoto = bos.toByteArray();
			bos.close();
		} catch (IOException ioe) {

		}
	}

	// Crop and resize the image for profile
	private void cropImage() {
		// Use existing crop activity.
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(mImageCaptureUri, IMAGE_UNSPECIFIED);

		// Specify image size
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);

		// Specify aspect ratio, 1:1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);

		// REQUEST_CODE_CROP_PHOTO is an integer tag you defined to
		// identify the activity in onActivityResult() when it returns
		startActivityForResult(intent, REQUEST_CODE_CROP_PHOTO);
	}

	// load the user data from shared preferences if there is no data make sure
	// that we set it to something reasonable
	private void loadUserData() {
		// Load and update all profile views

		// Load profile photo from internal storage
		try {
			// open the file using a file input stream
			FileInputStream fis = openFileInput(getString(R.string.profile_photo_file_name));
			// the file's data will be read into a bytearray output stream
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// inputstream -> buffer -> outputstream
			byte[] buffer = new byte[5 * 1024];
			int n;
			// read data in a while loop
			while ((n = fis.read(buffer)) > -1) {
				bos.write(buffer, 0, n); // Don't allow any extra bytes to creep
											// in, final write
			}
			fis.close();
			// get the byte array from the ByteArrayOutputStream
			bytePhoto = bos.toByteArray();
			bos.close();
		} catch (IOException e) {
		}

		// load the byte array to the image view
		loadImage();

		// Get the shared preferences - create or retrieve the activity
		// preference object.
		String mKey = getString(R.string.preference_name);
		SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);

		// Load name
		mKey = getString(R.string.name_field);
		String mValue = mPrefs.getString(mKey, " ");
		((EditText) findViewById(R.id.editName)).setText(mValue);

		// Load class info
		mKey = getString(R.string.class_field);
		mValue = mPrefs.getString(mKey, " ");
		((EditText) findViewById(R.id.editClass)).setText(mValue);

		// Load Major
		mKey = getString(R.string.major_field);
		mValue = mPrefs.getString(mKey, " ");
		((EditText) findViewById(R.id.editMajor)).setText(mValue);
	}

	private void saveUserData() {

		// Save profile image into internal storage.
		try {
			// if the user did not change default profile
			// picture, mProfilePictureArray will be null.
			if (bytePhoto != null) {
				FileOutputStream fos = openFileOutput(
						getString(R.string.profile_photo_file_name),
						MODE_PRIVATE);
				fos.write(bytePhoto);
				fos.flush();
				fos.close();
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

		// Getting the shared preferences editor
		String mKey = getString(R.string.preference_name);
		SharedPreferences mPrefs = getSharedPreferences(mKey, MODE_PRIVATE);

		SharedPreferences.Editor mEditor = mPrefs.edit();
		mEditor.clear();

		// Save name information
		mKey = getString(R.string.name_field);
		String mValue = (String) ((EditText) findViewById(R.id.editName))
				.getText().toString();
		mEditor.putString(mKey, mValue);

		// Read which index the radio is checked.
		mKey = getString(R.string.gender_label);

		// Save class information
		mKey = getString(R.string.class_field);
		mValue = (String) ((EditText) findViewById(R.id.editClass)).getText()
				.toString();
		mEditor.putString(mKey, mValue);

		// Save major information
		mKey = getString(R.string.major_field);
		mValue = (String) ((EditText) findViewById(R.id.editMajor)).getText()
				.toString();
		mEditor.putString(mKey, mValue);

		// Commit all the changes into the shared preference
		mEditor.commit();

	}

}
