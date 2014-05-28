package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersonalEventDbHelper extends SQLiteOpenHelper {
	public static Event mEvent;
	//public static SQLiteDatabase database;
	public static int DATABASE_VERSION = 1;
	public static final String TABLE_NAME_ENTRIES = "ENTRIES";
	public static String DATABASE_NAME = "personalEventDB";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SCHEDULE = "schedule";
	public static final String KEY_EVENT_NAME = "event_name";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_CLASS_PERIOD = "classPeriod";
	public static final String KEY_REGID = "regid";
	public static final String KEY_COLOR = "color";
	public static final String KEY_OWNER_NAME = "ownername";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_EVENT_DATE= "date";
	public static final String KEY_EVENT_START_TIME= "start";
	public static final String KEY_EVENT_END_TIME= "end";
	public static final String KEY_IS_REPEATING = "is_repeating";
	private static String[] allColumns = {KEY_ROWID,KEY_EVENT_NAME,KEY_LOCATION,
		KEY_EVENT_DATE, KEY_CLASS_PERIOD, KEY_REGID, KEY_OWNER_NAME, KEY_COLOR, KEY_EVENT_START_TIME,KEY_EVENT_END_TIME,KEY_DESCRIPTION,KEY_IS_REPEATING};

	private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_ENTRIES
			+ " ("
			+ KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_EVENT_NAME
			+ " TEXT, "
			+ KEY_LOCATION
			+ " TEXT, "
			+ KEY_EVENT_DATE
			+ " INTEGER NOT NULL, "
			+ KEY_CLASS_PERIOD
			+ " INTEGER NOT NULL, "
			+ KEY_REGID
			+ " TEXT, "
			+ KEY_OWNER_NAME
			+ " TEXT, "
			+ KEY_COLOR
			+ " INTEGER NOT NULL, "
			+ KEY_EVENT_START_TIME
			+ " INTEGER NOT NULL, "
			+ KEY_EVENT_END_TIME
			+ " INTEGER NOT NULL, "
			+ KEY_DESCRIPTION
			+ " TEXT, " + KEY_IS_REPEATING + " INTEGER NOT NULL " + ");";


	public PersonalEventDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mEvent = new Event();
		//database = getWritableDatabase();
	}

	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_ENTRIES);
	}


	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		/*
		database.execSQL("DROP TABLE IF EXISTS ");
		onCreate(database);
		 */
	}
	/*
	public void open() throws SQLException {
		database = this.getWritableDatabase();
	}

	public void close() {
		this.close();
	}
	 */
	public long insertEntry(Event event) {
		SQLiteDatabase dbObj;

		ContentValues value = new ContentValues();
		//value.put(KEY_ROWID, event.getId());
		//value.put(KEY_NAME, event.getName());
		value.put(KEY_EVENT_DATE, event.getDate());
		value.put(KEY_EVENT_NAME, event.getEventName());
		value.put(KEY_LOCATION, event.getEventLocation());
		value.put(KEY_EVENT_START_TIME, event.getStartTime());
		value.put(KEY_REGID, event.getRegId());
		value.put(KEY_CLASS_PERIOD, event.getClassPeriod());
		value.put(KEY_OWNER_NAME, event.getOwnerName());
		value.put(KEY_COLOR, event.getColor());
		Log.i("TAG","INSERT ENTRY"+event.getStartTime());
		Log.i("TAG","INSERT ENTRY VALUE"+value.get(KEY_EVENT_START_TIME));
		value.put(KEY_EVENT_END_TIME, event.getEndTime());
		value.put(KEY_DESCRIPTION, event.getEventDescription());
		value.put(KEY_IS_REPEATING, event.getIsRepeating());


		dbObj=getWritableDatabase();
		long id = dbObj.insert(TABLE_NAME_ENTRIES, null, value);

		dbObj.close();
		return id;
	}

	public void removeEntry(long rowIndex){
		SQLiteDatabase dbObj=getWritableDatabase();
		dbObj.delete(TABLE_NAME_ENTRIES, KEY_ROWID + "=" + rowIndex, null);
		dbObj.close();
	}

	public int removeEntries(){

		SQLiteDatabase dbObj = getReadableDatabase();

		int rows = dbObj.delete(TABLE_NAME_ENTRIES, "1", null);

		dbObj.close();
		return rows;

	}

	public Event fetchEntryByIndex(long rowId) throws SQLException {
		SQLiteDatabase dbObj = getReadableDatabase();
		Event event = null;
		Cursor cursor = dbObj.query(true, TABLE_NAME_ENTRIES, allColumns,
				KEY_ROWID + "=" + rowId, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			event = cursorToEntry(cursor);
		}

		cursor.close();
		dbObj.close();

		return event;
	}

	private Event cursorToEntry(Cursor cursor){
		Event event = new Event();

		event.setId(cursor.getLong(cursor.getColumnIndex(KEY_ROWID)));
		event.setEventName(cursor.getString(cursor.getColumnIndex(KEY_EVENT_NAME)));
		event.setDate(cursor.getLong(cursor.getColumnIndex(KEY_EVENT_DATE)));
		event.setEventLocation(cursor.getString(cursor.getColumnIndex(KEY_LOCATION)));
		event.setStartTime(cursor.getLong(cursor.getColumnIndex(KEY_EVENT_START_TIME)));
		event.setEndTime(cursor.getLong(cursor.getColumnIndex(KEY_EVENT_END_TIME)));
		event.setEventDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
		event.setIsRepeating(cursor.getInt(cursor.getColumnIndex(KEY_IS_REPEATING)));
		event.setRegId(cursor.getString(cursor.getColumnIndex(KEY_REGID)));
		event.setOwnerName(cursor.getString(cursor.getColumnIndex(KEY_OWNER_NAME)));
		event.setClassPeriod(cursor.getInt(cursor.getColumnIndex(KEY_CLASS_PERIOD)));
		event.setColor(cursor.getInt(cursor.getColumnIndex(KEY_COLOR)));

		return event;
	}
	public ArrayList<Event> fetchEntries(){
		SQLiteDatabase database=getWritableDatabase();

		ArrayList<Event> entries = new ArrayList<Event>();

		Cursor cursor = database.query(TABLE_NAME_ENTRIES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Event entry = cursorToEntry(cursor);
			Log.i("TAG","INSIDE FETCH"+entry.getEventName());
			entries.add(entry);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();

		database.close();
		return entries;
	}
}
