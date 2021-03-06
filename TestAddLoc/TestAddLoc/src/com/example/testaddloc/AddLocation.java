package com.example.testaddloc;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocation extends Activity{

	DatabaseKMITLLocation mHelper;
    SQLiteDatabase mDb;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        mHelper = new DatabaseKMITLLocation(this);
        mDb = mHelper.getWritableDatabase();
        
        final EditText editName = (EditText)findViewById(R.id.editName);
        
        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = editName.getText().toString();
                double alat = getIntent().getExtras().getDouble("myLat");
                double along = getIntent().getExtras().getDouble("myLong");
                
                if(name.length() != 0) {
                    
                    Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseKMITLLocation.TABLE_NAME 
                            + " WHERE " + DatabaseKMITLLocation.COL_NAME + "='" + name + "'" 
                            + " AND " + DatabaseKMITLLocation.COL_LAT + "='" + alat + "'" 
                            + " AND " + DatabaseKMITLLocation.COL_LONG + "='" + along + "'", null);
                    
                    if(mCursor.getCount() == 0) {
                        mDb.execSQL("INSERT INTO " + DatabaseKMITLLocation.TABLE_NAME + " (" 
                                + DatabaseKMITLLocation.COL_NAME + ", " + DatabaseKMITLLocation.COL_LAT 
                                + ", " + DatabaseKMITLLocation.COL_LONG + ") VALUES ('" + name 
                                + "', '" + alat + "', '" + along + "');"); 

                        editName.setText("");
                        
                        Toast.makeText(getApplicationContext(), "����������ʶҹ������º��������"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "�բ�����ʶҹ�������������"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    
    public void onStop() {
        super.onStop();
        mHelper.close();
        mDb.close();
    }
}
