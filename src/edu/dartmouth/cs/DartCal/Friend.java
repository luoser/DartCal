package edu.dartmouth.cs.DartCal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Friend {
	private long id;
	private String name;
	private ArrayList<Event> schedule;
	
	public Friend(){
		this.id = -1;
		this.name = "";
		this.schedule = new ArrayList<Event>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Event> getSchedule() {
		return schedule;
	}

	public void setSchedule(ArrayList<Event> schedule) {
		this.schedule = schedule;
	}
	
	public byte[] getScheduleByteArray() throws IOException {
		
	ByteArrayOutputStream b = new ByteArrayOutputStream();
	ObjectOutputStream o = new ObjectOutputStream(b);
	
	for(int i = 0; i < schedule.size(); i++){
		o.writeObject(schedule.get(i));
	}
	
	return b.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	public void setScheduleFromByteArray(byte[] array) throws StreamCorruptedException, IOException, ClassNotFoundException{
		
		ByteArrayInputStream b = new ByteArrayInputStream(array);
		
		ObjectInputStream o = new ObjectInputStream(b);
		
		
		for(int i = 0; i < array.length; i++){
			schedule.add((Event) o.readObject());
		}
	}
}
