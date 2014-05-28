package edu.dartmouth.cs.DartCal;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Event")

public class Event extends ParseObject {
//	private static final long serialVersionUID = 1L;
//	private long id;
//	private String eventName;
//	private String eventLocation;
//	private String eventDescription;
//	private long startTime;
//	private long endTime;
//	private int	isRepeating;
//	private long date;
//	private int color;
//	private int classPeriod;
//	private String RegId;
//	private String OwnerName;

public Event() {
//	this.eventName = "";
//	this.eventLocation = "";
//	this.eventDescription = "";
//	this.startTime = System.currentTimeMillis();
//	this.endTime = System.currentTimeMillis();
//	this.isRepeating = 3;
	setClassPeriod(-1);
//	this.date=System.currentTimeMillis();
//	this.RegId= MainActivity.regid;
	setOwnerName(Globals.USER);
}
public int getClassPeriod() {
	return getInt("classPeriod");
}
public void setClassPeriod(int classPeriod) {
	put("classPeriod", classPeriod);
}
public int getIsRepeating() {
	
	return getInt("isRepeating");
}
public void setIsRepeating(int isRepeating) {
	put("isRepeating", isRepeating);
}
public long getId(){
	return getLong("id");
}
public void setId(long id){
	put("id", id);
}
public String getEventName() {
	return getString("eventName");
}

public void setEventName(String eventName) {
	put("eventName", eventName);
}

public String getEventLocation() {
	return getString("eventLocation");
}

public void setEventLocation(String eventLocation) {
	put("eventLocation", eventLocation);
}

public String getEventDescription() {
	return getString("eventDescription");
}

public void setEventDescription(String eventDescription) {
	put("eventDescription", eventDescription);
}

public long getStartTime() {
	return getLong("startTime");
}

public void setStartTime(long startTime) {
	put("startTime", startTime);
}

public long getEndTime() {
	return getLong("endTime");
}

public void setEndTime(long endTime) {
	put("endTime", endTime);
}

public String getOwnerName() {
	return getString("ownerName");
}
public void setOwnerName(String ownerName) {
	put("ownerName", ownerName);
}
public String getRegId(){
	return getString("regId");
}
public void setRegId(String regId) {
	put("regId", regId);
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
	return getLong("date");
}
public void setDate(long date){
	put("date", date);
}
public int getColor() {
	return getInt("color");
}
public void setColor(int color) {
	put("color", color);
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
		int myColor = getColor();
		String myRegId=getRegId();
		String myOwnerName=getOwnerName();
		int myClassPeriod = getClassPeriod();
		
		myObj.put("id", myId);
		myObj.put("date", myDate);
		myObj.put("event name", myEventName);
		myObj.put("location", myLocation);
		myObj.put("start time", myStartTime);
		myObj.put("end time", myEndTime);
		myObj.put("description", myDescription);
		myObj.put("repeating", myRepeating);
		myObj.put("classPeriod", myClassPeriod);
		myObj.put("color", myColor);
		myObj.put("regId", myRegId);
		myObj.put("owner name", myOwnerName);
		
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
		setClassPeriod(obj.getInt("classPeriod"));
		setColor(obj.getInt("color"));
		setRegId(obj.getString("regId"));
		setOwnerName(obj.getString("owner name"));
		
	}
	catch(JSONException e){
		return null;
	}
	return obj;
}

}