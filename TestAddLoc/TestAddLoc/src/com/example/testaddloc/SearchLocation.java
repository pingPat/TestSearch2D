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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchLocation extends Activity {
	
	CustomAutoCompleteView myAutoComplete;
	public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
	// adapter for auto-complete
    ArrayAdapter<String> myAdapter;
     
    // for database operations
    DatabaseKMITLLocation databaseH;
    private TextView mNameView;
    
    
    /*
    Cursor mCursor;
    SQLiteDatabase mDb;
    DatabaseKMITLLocation mHelper;
    */
    SQLiteDatabase mDb;
    DatabaseKMITLLocation mHelper;
    
    // just to add some initial value
    String[] item = new String[] {"Please search..."};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);
        
        
        
        try{
            
            // instantiate database handler
            databaseH = new DatabaseKMITLLocation(SearchLocation.this);
             
            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);
            mNameView = (TextView) findViewById(R.id.mName);
            /*
            mHelper = new DatabaseKMITLLocation(this);
            mDb = mHelper.getWritableDatabase();
            */
            mHelper = new DatabaseKMITLLocation(this);
            mDb = mHelper.getWritableDatabase();
             
            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
             
            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);
            
            myAutoComplete.setOnItemClickListener(new OnItemClickListener() {
            	
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
            		finish();*/
            		//}catch(Exception e){
            			//Toast.makeText(getApplicationContext(), ""+pos, Toast.LENGTH_LONG).show();
            		//}
            		//}catch(Exception e){
            		//	Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            		//};
					//startActivity(i);
            		//String t_name = cur.getString(cur.getColumnIndexOrThrow("name"));
            		//mNameView.setText(t_name);
            		/*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("lname", parent.getItemAtPosition(pos).toString());
                    bundle.putLong("_id", id);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
            		/*mCursor.moveToPosition(pos);

                    String name = mCursor.getString(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_NAME));
                    double slat = mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LAT));
                    double slong = mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LONG));
                    
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchLocation.this);
                    builder.setTitle("������ʶҹ���");
                    builder.setMessage(name + "\n\nLatitude : " + slat + "\n\nLongitude : " + slong);
                    builder.setNeutralButton("OK", null);
                    builder.show();
            		mCursor.moveToPosition(pos);
            		String name = mCursor.getString(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_NAME));
            		double slat = mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LAT));
                    double slong = mCursor.getDouble(mCursor.getColumnIndex(DatabaseKMITLLocation.COL_LONG));
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("mydName", name);
					i.putExtra("mydLat", slat);
					i.putExtra("mydLong", slong);
					startActivityForResult(i, 999);*/
                    
                }
            	
			});
         
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 // this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String searchTerm){
         
        // add items on the array dynamically
        List<MyObject> products = databaseH.read(searchTerm);
        int rowCount = products.size();
         
        String[] item = new String[rowCount];
        int x = 0;
         
        for (MyObject record : products) {
             
            item[x] = record.objectName;
            x++;
        }
         
        return item;
    }
	
}
