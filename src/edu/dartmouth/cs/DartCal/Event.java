package edu.dartmouth.cs.DartCal;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String eventName;
	private String eventLocation;
	private String eventDescription;
	private long startTime;
	private long endTime;
	private int	isRepeating;
	private long date;
	private int color;
	private int classPeriod;
	private String RegId;
	private String OwnerName;

public Event() {
	this.eventName = "";
	this.eventLocation = "";
	this.eventDescription = "";
	this.startTime = System.currentTimeMillis();
	this.endTime = System.currentTimeMillis();
	this.isRepeating = 3;
	this.classPeriod = -1;
	this.date=System.currentTimeMillis();
	this.RegId="";
	this.OwnerName="";
}
public int getClassPeriod() {
	return classPeriod;
}
public void setClassPeriod(int classPeriod) {
	this.classPeriod = classPeriod;
}
public int getIsRepeating() {
	return isRepeating;
}
public void setIsRepeating(int isRepeating) {
	this.isRepeating = isRepeating;
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

public String getOwnerName() {
	return OwnerName;
}
public void setOwnerName(String ownerName) {
	OwnerName = ownerName;
}
public String getRegId(){
	return RegId;
}
public void setRegId(String regId) {
	RegId = regId;
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
public int getColor() {
	return color;
}
public void setColor(int color) {
	this.color = color;
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
		myObj.put("owner name",myOwnerName);
		
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