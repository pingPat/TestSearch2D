package com.example.testaddloc;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewLocation extends Activity{
	DatabaseKMITLLocation mHelper;
	SQLiteDatabase mDb;
	Cursor mCursor;
	ListView listLocation;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);
        
        mHelper = new DatabaseKMITLLocation(this);
        mDb = mHelper.getReadableDatabase();
        
        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseKMITLLocation.TABLE_NAME, null);
        
        ArrayList<String> arr_list = new ArrayList<String>();
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast() ){
        	arr_list.add(mCursor.getString(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_NAME))
        			+ "\nพิกัด : " + mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LAT))
        			+ "\t," + mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LONG)));
        	mCursor.moveToNext();
        }
        
        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_listview, arr_list);
        
        listLocation = (ListView)findViewById(R.id.listLocation);
        listLocation.setAdapter(adapterDir);
        listLocation.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCursor.moveToPosition(arg2);

                String name = mCursor.getString(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_NAME));
                double slat = mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LAT));
                double slong = mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LONG));
                
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewLocation.this);
                builder.setTitle("ข้อมูลสถานที่");
                builder.setMessage(name + "\n\nLatitude : " + slat + "\n\nLongitude : " + slong);
                builder.setNeutralButton("OK", null);
                builder.show();
            }
        });
	}
	
	public void onStop() {
        super.onStop();
        mHelper.close();
        mDb.close();
    }	
}
