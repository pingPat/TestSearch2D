package com.example.testaddloc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchLocation extends Activity {
	
	public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    
    final static int[] to = new int[] { android.R.id.text1 };
    final static String[] from = new String[] { "name" };

    private AutoCompleteTextView mLNameView;
    private DatabaseKMITLLocation mDbHelper;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);
        mDbHelper = new DatabaseKMITLLocation(this);       
        
        mLNameView = (AutoCompleteTextView) findViewById(R.id.l_name);
        
        SimpleCursorAdapter adapter = 
                new SimpleCursorAdapter(this, 
                        android.R.layout.simple_dropdown_item_1line, null,
                        from, to);
            mLNameView.setAdapter(adapter);

         // Set the CursorToStringConverter, to provide the labels for the
            // choices to be displayed in the AutoCompleteTextView.
            adapter.setCursorToStringConverter(new CursorToStringConverter() {
                public String convertToString(android.database.Cursor cursor) {
                    // Get the label for this row out of the "state" column
                    final int columnIndex = cursor.getColumnIndexOrThrow("name");
                    final String str = cursor.getString(columnIndex);
                    return str;
                }
            });
            
         // Set the FilterQueryProvider, to run queries for choices
            // that match the specified input.
            adapter.setFilterQueryProvider(new FilterQueryProvider() {
                public Cursor runQuery(CharSequence constraint) {
                    // Search for states whose names begin with the specified letters.
                    Cursor cursor = mDbHelper.getMatchingLocate(
                            (constraint != null ? constraint.toString() : null));
                    return cursor;
                }
            });
            
            /*myAutoComplete.setOnItemClickListener(new OnItemClickListener() {
            	
            	@Override
                public void onItemClick(AdapterView<?> listView, View arg1, int pos, long id) {
            		
            		//try{
            		Log.e(TAG, "Index: " + pos + " Length : " + listView.getCount());	
            		String t_name =  (String) myAutoComplete.getAdapter().getItem(pos);
            		
            		//Toast.makeText(getApplicationContext(), ""+pos, Toast.LENGTH_LONG).show();
            		/*Cursor cur = mDb.rawQuery("SELECT * FROM " + mHelper.TABLE_NAME + " WHERE " + mHelper.COL_NAME 
            				+ " = '" + t_name + "'" + " LIMIT 1" , null);
            		
            		cur.moveToFirst();
            		String tt_name = cur.getString(cur.getColumnIndex("name"));
                    double slat = cur.getDouble(cur.getColumnIndex("latitude"));
                    double slong = cur.getDouble(cur.getColumnIndex("longitude"));
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("mydName", t_name);
					//i.putExtra("mydLat", slat);
					//i.putExtra("mydLong", slong);
					Toast.makeText(getApplicationContext(), tt_name+"\n"+slat+","+slong, Toast.LENGTH_LONG).show();
					
					//mNameView.setText(t_name+"\n"+String.format("%.7f", slat)+" , "+String.format("%.7f", slong));
            		//cur.moveToFirst();
            		setResult(RESULT_OK, i);
            		finish();//
            		//}catch(Exception e){
            			//Toast.makeText(getApplicationContext(), ""+pos, Toast.LENGTH_LONG).show();
            		//}
            		//}catch(Exception e){
            		//	Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            		//};
					//startActivity(i);
            		//String t_name = cur.getString(cur.getColumnIndexOrThrow("name"));
            		//mNameView.setText(t_name);
                    
                }
            	
			});*/

    }
	
}
