package com.example.testaddloc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseKMITLLocation {

	public static final String TAG = "InDatabase";
	private static final String DB_NAME = "KMITL_Location";
	private static final int DB_VERSION = 1;

	// private static final String TAG = "ItemsDbAdapter";

	public static final String TABLE_NAME = "Location";

	public static final String COL_NAME = "name";
	public static final String COL_LAT = "latitude";
	public static final String COL_LONG = "longitude";

	// private SQLiteDatabase mDb;

	public class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + TABLE_NAME
					+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME
					+ " TEXT, " + COL_LAT + " DOUBLE, " + COL_LONG
					+ " DOUBLE);");
			Log.e(TAG, "Create");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TALBE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Activity mActivity;

	public DatabaseKMITLLocation(Activity activity) {
        this.mActivity = activity;
        mDbHelper = this.new DatabaseHelper(activity);
        mDb = mDbHelper.getWritableDatabase();
    }
	
	public void close() {
        mDbHelper.close();
    }
	
	public Cursor getMatchingLocate(String constraint) throws SQLException {

		String queryString = "SELECT * FROM " + TABLE_NAME;

		if (constraint != null) {
			// Query for any rows where the state name begins with the
			// string specified in constraint.
			//
			// NOTE:
			// If wildcards are to be used in a rawQuery, they must appear
			// in the query parameters, and not in the query string proper.
			// See http://code.google.com/p/android/issues/detail?id=3153
			//constraint = constraint.trim() + "%'";
			//queryString += " WHERE name LIKE '% ?";
			queryString += " WHERE " + COL_NAME + " LIKE '%" + constraint + "%'";
			queryString += " ORDER BY " + COL_NAME + " ASC";
			Log.e(TAG, "Call like");
		}
		//String params[] = { constraint };
		Log.e(TAG, "Call getMatching");

		/*if (constraint == null) {
			// If no parameters are used in the query,
			// the params arg must be null.
			params = null;
		}*/
		try {
			//Cursor cursor = mDb.rawQuery(queryString, params);
			Cursor cursor = mDb.rawQuery(queryString, null);
			if (cursor != null) {
				Log.e(TAG, "Cursor moveToFirst");
				cursor.moveToFirst();
				Log.e(TAG, "Return Cursor");
				return cursor;
			}
		} catch (SQLException e) {
			Log.e(TAG, "ERROR");
			Log.e("AutoCompleteDbAdapter", e.toString());
			throw e;
		}

		return null;
	}

	public DatabaseHelper getmDbHelper() {
		return mDbHelper;
	}

	public void setmDbHelper(DatabaseHelper mDbHelper) {
		this.mDbHelper = mDbHelper;
	}

}
