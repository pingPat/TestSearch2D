package com.example.testaddloc;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteLocation extends Activity {
	DatabaseKMITLLocation mHelper;
	SQLiteDatabase mDb;
	Cursor mCursor;
	ListView listLocation;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
    }
    
    public void onResume() {
        super.onResume();
        
        mHelper = new DatabaseKMITLLocation(this);
        mDb = mHelper.getmDbHelper().getWritableDatabase();
        
        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseKMITLLocation.TABLE_NAME, null);
        
        listLocation = (ListView)findViewById(R.id.listLocation);
        listLocation.setAdapter(updateListView());
        listLocation.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCursor.moveToPosition(arg2);
            }
        });
        
        listLocation.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCursor.moveToPosition(arg2);
                
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteLocation.this);
                builder.setTitle("ลบข้อมูลสถานที่");
                builder.setMessage("ลบข้อมูลนี้ใช่หรือไม่?");
                builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	String name = mCursor.getString(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_NAME));
                        double slat = mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LAT));
                        double slong = mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LONG));
                            
                        mDb.execSQL("DELETE FROM " + DatabaseKMITLLocation.TABLE_NAME 
                                + " WHERE " + DatabaseKMITLLocation.COL_NAME + "='" + name + "'"
                                + " AND " + DatabaseKMITLLocation.COL_LAT + "='" + slat + "'"
                                + " AND " + DatabaseKMITLLocation.COL_LONG + "='" + slong + "';"); 
                            
                        mCursor.requery();

                        listLocation.setAdapter(updateListView());
                        
                        Toast.makeText(getApplicationContext(),"ลบข้อมูลเรียบร้อย"
                                , Toast.LENGTH_SHORT).show();
                    }
                });
                    
                builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                
                return true;
            }
        });
    }
    
    public void onStop() {
        super.onStop();
        mHelper.close();
        mDb.close();
    }
    
    public ArrayAdapter<String> updateListView() {
        ArrayList<String> arr_list = new ArrayList<String>();
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast()){
        	arr_list.add(mCursor.getString(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_NAME))
        			+ "\nพิกัด : " + mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LAT))
        			+ "\t," + mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LONG)));
        	mCursor.moveToNext();    
        }

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getApplicationContext() 
                , R.layout.my_listview, arr_list);
        return adapterDir;
    }

}
