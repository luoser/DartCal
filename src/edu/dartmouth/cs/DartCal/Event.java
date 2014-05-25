package edu.dartmouth.cs.DartCal;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	private String eventName;
	private String eventLocation;
	private String eventDescription;
	private long startTime;
	private long endTime;
	private int	isRepeating;
	private long id;
	private long date;
//	private String classPeriod;
	private int classPeriod;	// value will come from spinner

public Event() {
	this.eventName = "";
	this.eventLocation = "";
	this.eventDescription = "";
	this.classPeriod = -1;
	this.startTime = System.currentTimeMillis();
	this.endTime = System.currentTimeMillis();
	this.isRepeating = 3;
	this.date=System.currentTimeMillis();
}
public int getIsRepeating() {
	return isRepeating;
}
public void setIsRepeating(int isRepeating) {
	this.isRepeating = isRepeating;
}
public int getClassPeriod() {
	return classPeriod;
}
public void setClassPeriod(int classPeriod) {
	this.classPeriod = classPeriod;
}
public long getId(){
	return id;
}
public void setId(long id){
	this.id=id;
}
public String getEventName() {
	return eventName;
}

public void setEventName(String eventName) {
	this.eventName = eventName;
}

public String getEventLocation() {
	return eventLocation;
}

public void setEventLocation(String eventLocation) {
	this.eventLocation = eventLocation;
}

public String getEventDescription() {
	return eventDescription;
}

public void setEventDescription(String eventDescription) {
	this.eventDescription = eventDescription;
}

public long getStartTime() {
	return startTime;
}

public void setStartTime(long startTime) {
	this.startTime = startTime;
}

public long getEndTime() {
	return endTime;
}

public void setEndTime(long endTime) {
	this.endTime = endTime;
}

/*
public void setStartDateTime(long timeInMS){
	this.startDateTime = new Date(timeInMS);
}

public void setEndDateTime(long timeInMS){
	this.endDateTime = new Date(timeInMS);
}
*/
public long getDate(){
	return date;
}
public void setDate(long date){
	this.date=date;
}
public JSONObject toJSONObject(){
	JSONObject myObj=new JSONObject();
	try{
		
		long myId=getId();
		long myDate=getDate();
		String myEventName=getEventName();
		String myLocation=getEventLocation();
		long myStartTime=getStartTime();
		long myEndTime=getEndTime();
		String myDescription=getEventDescription();
		int myRepeating=getIsRepeating();
		Log.i("TAG","JSON START TIME"+getStartTime());
		myObj.put("id", myId);
		myObj.put("date", myDate);
		myObj.put("event name", myEventName);
		myObj.put("location", myLocation);
		myObj.put("start time", myStartTime);
		myObj.put("end time", myEndTime);
		myObj.put("description", myDescription);
		myObj.put("repeating", myRepeating);
		
	}
	catch (JSONException e){
		return null;
	}
	return myObj;
}
public JSONObject fromJSONObject(JSONObject obj){
	try{
		setId(obj.getLong("id"));
		setDate(obj.getLong("date"));
		setEventName(obj.getString("event name"));
		setEventLocation(obj.getString("location"));
		setStartTime(obj.getLong("start time"));
		setEndTime(obj.getLong("end time"));
		setEventDescription(obj.getString("description"));
		setIsRepeating(obj.getInt("repeating"));
		
	}
	catch(JSONException e){
		return null;
	}
	return obj;
}

}