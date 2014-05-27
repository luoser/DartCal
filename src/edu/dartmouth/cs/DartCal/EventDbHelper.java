package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventDbHelper extends SQLiteOpenHelper {
	public static Friend mFriend;
	public static int DATABASE_VERSION = 1;
	public static final String TABLE_NAME_ENTRIES = "ENTRIES";
	public static String DATABASE_NAME = "eventDB";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_CLASS = "class_year";
	public static final String	KEY_SIZE = "size";
	public static final String KEY_SCHEDULE = "schedule";
	public PersonalEventDbHelper user;
	private static String[] allColumns = {KEY_ROWID, KEY_NAME, KEY_CLASS, KEY_SIZE, KEY_SCHEDULE};

	private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_ENTRIES
			+ " ("
			+ KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_NAME
			+ " TEXT, "
			+ KEY_CLASS
			+ " TEXT, "
			+ KEY_SIZE
			+ " INTEGER, "
			+ KEY_SCHEDULE + " BLOB " + ");";


	public EventDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mFriend = new Friend();
		user = new PersonalEventDbHelper(context);
	}

	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_ENTRIES);
	}


	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS ");
		onCreate(database);
	}

	public long insertEntry(Friend friend) throws IOException {
		SQLiteDatabase dbObj;
		dbObj=getWritableDatabase();
		mFriend = friend;
		mFriend.scheduleSize = mFriend.schedule.size();
		
		ContentValues value = new ContentValues();
		value.put(KEY_NAME, mFriend.getName());
		value.put(KEY_CLASS, mFriend.getClassYear());
		value.put(KEY_SIZE, mFriend.scheduleSize);
		value.put(KEY_SCHEDULE, mFriend.getScheduleByteArray());
		
		long id = dbObj.insert(EventDbHelper.TABLE_NAME_ENTRIES, null, value);
		
		dbObj.close();
		
		return id;
	}
	
	public ArrayList<Friend> fetchEntries() throws StreamCorruptedException, ClassNotFoundException, IOException{
		SQLiteDatabase dbObj = getReadableDatabase();
		
		ArrayList<Friend> list = new ArrayList<Friend>();
		Cursor c = dbObj.query(EventDbHelper.TABLE_NAME_ENTRIES,
				allColumns, null, null, null, null, null);
		// Initialize the cursor
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Friend temp = cursorToEntry(c);
			list.add(temp);
			c.moveToNext();
		}
		c.close();
		dbObj.close();
		return list;
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
	
	public Friend fetchEntryByIndex(long rowId) throws SQLException, StreamCorruptedException, ClassNotFoundException, IOException {
		SQLiteDatabase dbObj = getReadableDatabase();
		
		Friend friend = null;

		Cursor cursor = dbObj.query(true, TABLE_NAME_ENTRIES, allColumns,
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			friend = cursorToEntry(cursor);
		}

		cursor.close();
		dbObj.close();

		return friend;
	}
	
	private Friend cursorToEntry(Cursor cursor) throws StreamCorruptedException, ClassNotFoundException, IOException {
		Friend friend = new Friend();
		
		friend.setId(cursor.getLong(cursor.getColumnIndex(KEY_ROWID)));
		friend.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
		friend.setClassYear(cursor.getString(cursor.getColumnIndex(KEY_CLASS)));
		friend.scheduleSize = cursor.getInt(cursor.getColumnIndex(KEY_SIZE));
		friend.setScheduleFromByteArray(cursor.getBlob(cursor.getColumnIndex(KEY_SCHEDULE)));
		
		return friend;
	}
	
	public void userConversion(){
		ArrayList<Event> list = user.fetchEntries();
		
	}
	
}
