package edu.dartmouth.cs.DartCal;

//import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


public class GCMIntentService extends IntentService {
	private static String SENDER_ID="544332492675";
	private String MESSAGE="message";
	
	public GCMIntentService() {
    super(SENDER_ID);
}

	@Override
	protected void onHandleIntent(Intent intent) {
    Bundle extras = intent.getExtras();
    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
    // The getMessageType() intent parameter must be the intent you received
    // in your BroadcastReceiver.
    String messageType = gcm.getMessageType(intent);
    Log.i("TAG","CALLING HANDLE INTENT");
    if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
        /*
         * Filter messages based on message type. Since it is likely that GCM
         * will be extended in the future with new message types, just ignore
         * any message types you're not interested in, or that you don't
         * recognize.
         */
    		Log.i("TAG","RECEIVING A MESSAGE");
        if (GoogleCloudMessaging.
                MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            //handle send error in here
        } else if (GoogleCloudMessaging.
                MESSAGE_TYPE_DELETED.equals(messageType)) {
        	//handle delete message on server in here
        } else if (GoogleCloudMessaging.
                MESSAGE_TYPE_MESSAGE.equals(messageType)) {
        	// If it's a regular GCM message, do some work.
        	String message = (String) extras.get("messages");
        	String[] messageSplit=message.split(";");
        	if (messageSplit[0].equals("delete")){
        		Log.i("TAG","RECEIVING A DELETE MESSAGE");
        		new EventDbHelper(getApplicationContext()).removeEntry(Long.parseLong(messageSplit[1]));        		
        	}
        }
      
    }    
    // Release the wake lock provided by the WakefulBroadcastReceiver.
    GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

/*
	@Override
	protected void onRegistered(Context arg0, String arg1) {
		Log.i("TAG","ONREGISTERED in GCMINTENTSERVICE");
		ServerUtilities.register(arg0,arg1);
		
	}


	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		if (GCMRegistrar.isRegisteredOnServer(arg0))
			ServerUtilities.unregister(arg0,arg1);
		
	}
	*/
}

