package edu.dartmouth.cs.DartCal;

//import com.google.android.gcm.GCMBaseIntentService;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMIntentService extends IntentService {
	private static String SENDER_ID = "286158326826";
	private String MESSAGE = "message";
	private Event myEvent;
	private PersonalEventDbHelper db;
	private ArrayList<Event> myEventsArray;
	private int size;
	private int counter;
	
	public GCMIntentService() {
    super(SENDER_ID);
    myEventsArray=new ArrayList<Event>();
    counter=0;
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
        		/*
	        	if (messageSplit[0].equals("value")){
	        		Log.i("TAG","INSIDE OF VALUE");
	        		size=Integer.parseInt(messageSplit[1]);
	        	}
        		*/
	        	if (messageSplit[0].equals("add")){
	        			Log.i("TAG","COUNTER"+counter);
	        			size=Integer.parseInt(messageSplit[9]);
	        			counter++;
		        		Log.i("TAG","RECEIVING AN ADD MESSAGE event name "+messageSplit[1]);
		        		Log.i("TAG","RECEIVING AN ADD MESSAGE ate "+messageSplit[2]);
		        		Log.i("TAG","RECEIVING AN ADD MESSAGE location "+messageSplit[3]);
		        		Log.i("TAG","RECEIVING AN ADD MESSAGE start time "+messageSplit[4]);
		        		Log.i("TAG","RECEIVING AN ADD MESSAGE end time "+messageSplit[5]);
		        		Log.i("TAG","RECEIVING AN ADD MESSAGE description "+messageSplit[6]);
		        		Log.i("TAG","RECEIVING AN ADD MESSAGE repeating "+messageSplit[7]);
		        		Log.i("TAG","RECEIVING AN ADD MESSAGE regId "+messageSplit[8]);
		        		String myEventString=messageSplit[1].toString();
		        		
		        		String phoneRegId=messageSplit[8];
		        		Log.i("TAG","REGID BRAHHHHHH LIKE WATTTT "+MainActivity.regid);
		        		
		        		//if (!(phoneRegId.equals(MainActivity.regid))){
		        		
								Log.i("TAG", "INSIDE DIS BITCH LIKE WAHHHHAT BRAHHHHHH");
								String myEventName = messageSplit[1];
								long myEventDate = Long.parseLong(messageSplit[2]);
								String myEventLocation = messageSplit[3];
								long myEventStartTime = Long.parseLong(messageSplit[4]);
								long myEventEndTime = Long.parseLong(messageSplit[5]);
								String myEventDescription = messageSplit[6];
								int myIsRepeating = Integer.parseInt(messageSplit[7]);
			
								myEvent = new Event();
								db = new PersonalEventDbHelper(this);
			
								myEvent.setEventName(myEventName);
								myEvent.setDate(myEventDate);
								myEvent.setEventLocation(myEventLocation);
								myEvent.setStartTime(myEventStartTime);
								myEvent.setEndTime(myEventEndTime);
								myEvent.setEventDescription(myEventDescription);
								myEvent.setIsRepeating(myIsRepeating);
			
								myEventsArray.add(myEvent);
	
	        	}
	        	Log.i("TAG","COUNTER Before if "+Integer.toString(counter)+ "Size before if"+ Integer.toString(size));
	        	if (counter==size){
	        		Log.i("TAG","INSIDE THE COUNTER==SIZE");
	        		db.removeEntries();
	        		for (int i=0;i<myEventsArray.size();i++){
	        			Event theseEntries=myEventsArray.get(i);
	        			db.insertEntry(theseEntries);
	        		}
	        	
	        	}
	       }
      
    }    
    // Release the wake lock provided by the WakefulBroadcastReceiver.
    GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

}

	