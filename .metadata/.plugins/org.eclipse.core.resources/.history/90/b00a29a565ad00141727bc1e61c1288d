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

public class DatabaseKMITLLocation extends SQLiteOpenHelper {

	private static final String DB_NAME = "KMITL_Location";
	private static final int DB_VERSION = 1;

	// private static final String TAG = "ItemsDbAdapter";

	public static final String TABLE_NAME = "Location";

	public static final String COL_NAME = "name";
	public static final String COL_LAT = "latitude";
	public static final String COL_LONG = "longitude";

	// private SQLiteDatabase mDb;

		public DatabaseKMITLLocation(Context context) {
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
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TALBE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	

	/*
	 * public Cursor fetchItemsByDesc(String inputText) throws SQLException {
	 * Log.w(TAG, inputText); Cursor mCursor = mDb.query(true, TABLE_NAME, new
	 * String[] {COL_NAME, COL_LAT, COL_LONG}, COL_NAME + " like '%" + inputText
	 * + "%'", null, null, null, null, null); if (mCursor != null) {
	 * mCursor.moveToFirst(); } return mCursor;
	 * 
	 * }
	 */

	  // Read records related to the search term public List<MyObject>
	public List<MyObject> read(String searchTerm) {
	  
	  List<MyObject> recordsList = new ArrayList<MyObject>();
	  
	  // select query 
	  String sql = ""; 
	  sql += "SELECT * FROM " + TABLE_NAME;
	  sql += " WHERE " + COL_NAME + " LIKE '%" + searchTerm + "%'";
	  sql += " ORDER BY " + COL_NAME + " ASC"; 
	  //sql += " LIMIT 0,5";
	  
	  SQLiteDatabase db = this.getWritableDatabase();
	  
	  // execute the query 
	  Cursor cursor = db.rawQuery(sql, null);
	 
	  // looping through all rows and adding to list 
	  if (cursor.moveToFirst()){
		  do {
	  
	  // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId))); 
	  String objectName = cursor.getString(cursor.getColumnIndex(COL_NAME));
	  double objectLat = cursor.getDouble(cursor.getColumnIndex(COL_LAT)); 
	  double objectLng = cursor.getDouble(cursor.getColumnIndex(COL_LONG)); 
	  MyObject myObjectN = new MyObject(objectName); 
	  MyObject myObjectLat = new MyObject(objectLat); 
	  MyObject myObjectLng = new MyObject(objectLng);
	  
	  // add to list
	  recordsList.add(myObjectN);
	  
	  } while (cursor.moveToNext()); }
	  
	  cursor.close();
	  db.close();
	  
	  // return the list of records 
	  return recordsList; 
	  }  

}
