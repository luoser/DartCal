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

public class EventDbHelper extends SQLiteOpenHelper {
	public static Friend mFriend;
	public static SQLiteDatabase database;
	public static int DATABASE_VERSION = 1;
	public static final String TABLE_NAME_ENTRIES = "ENTRIES";
	public static String DATABASE_NAME = "eventDB";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SCHEDULE = "schedule";
	private static String[] allColumns = {KEY_ROWID, KEY_NAME, KEY_SCHEDULE};

	private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_ENTRIES
			+ " ("
			+ KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_NAME
			+ " TEXT, "
			+ KEY_SCHEDULE + " BLOB " + ");";


	public EventDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mFriend = new Friend();
		database = getWritableDatabase();
	}

	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_ENTRIES);
	}


	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS ");
		onCreate(database);
	}

	public void open() throws SQLException {
		database = this.getWritableDatabase();
	}

	public void close() {
		this.close();
	}

	public static long insertEntry(Friend friend) throws IOException{
		mFriend = friend;
		ContentValues value = new ContentValues();
		value.put(KEY_ROWID, mFriend.getId());
		value.put(KEY_NAME, mFriend.getName());
		value.put(KEY_SCHEDULE, mFriend.getScheduleByteArray());
		long id = database.insert(EventDbHelper.TABLE_NAME_ENTRIES, null, value);

		return id;
	}
	
	public void removeEntry(long rowIndex){
		database = getWritableDatabase();
		database.delete(TABLE_NAME_ENTRIES, KEY_ROWID + "=" + rowIndex, null);
		database.close();
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
		friend.setScheduleFromByteArray(cursor.getBlob(cursor.getColumnIndex(KEY_SCHEDULE)));
		
		return friend;
	}
	

}
