package com.example.testaddloc;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	LocationClient mLocationClient;
	GoogleMap mMap;
	Marker mMarker, mDMarker;
	TextView textView1,textInf;
	GMapV2Direction md;
	String ApiKey = "AIzaSyB78GfzFRl3ZY3pqg3qHiMU-BSY2Y6CtSI";
	//String dName = " ";
	double lat, lng;
	//double dlat = 0.0;
	//double dlng = 0.0;
	int i = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DatabaseKMITLLocation mHelper = new DatabaseKMITLLocation(this);
		SQLiteDatabase mDb = mHelper.getWritableDatabase();
		mHelper.close();
		mDb.close();
		//String alname = getIntent().getExtras().getString("lname");
		
		Button btnAdd = (Button) findViewById(R.id.btn_add);
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						AddLocation.class);
				intent.putExtra("myLat", lat);
				intent.putExtra("myLong", lng);
				startActivity(intent);
			}
		});

		Button btnView = (Button) findViewById(R.id.btn_view);
		btnView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ViewLocation.class);
				startActivity(intent);
			}
		});

		Button btnSearch = (Button) findViewById(R.id.b_search);
		btnSearch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SearchLocation.class);
				//startActivity(intent);
				startActivityForResult(intent, 999);
			}
		});

		Button btnEdit = (Button) findViewById(R.id.bedit);
		btnEdit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						DeleteLocation.class);
				startActivity(intent);
			}
		});

		textView1 = (TextView) findViewById(R.id.textView1);
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		boolean result = isServicesAvailable();
		if (result) {
			// �����ͧ�� Google Play Services
			mLocationClient = new LocationClient(this, mCallback, mListener);
		} else {
			// �����ͧ����� Google Play Services �Դ�������
			finish();
		}
	}

	protected void onStart() {
		super.onStart();
		mLocationClient.connect();
		//i++;
		//TextView testi = (TextView) findViewById(R.id.texti);
		//testi.setText(String.format("%d", i));
	}

	protected void onStop() {
		super.onStop();
		mLocationClient.disconnect();
	}
	
	/*protected void onResume(){
		super.onResume();
		dName = getIntent().getExtras().getString("mydName");
		dlat = getIntent().getExtras().getDouble("mydLat");
		dlng = getIntent().getExtras().getDouble("mydLong");
	}*/
	
	//
	protected void onActivityResult ( int requestCode, int resultCode, Intent data )
	{
		//super.onActivityResult ( requestCode, resultCode, intent );
	
		if(requestCode == 999)
		{
			if(resultCode == RESULT_OK){
				String dName = data.getStringExtra("mydName");
				//Double dlat = data.getDoubleExtra("mydLat", lat);
				//Double dlng = data.getDoubleExtra("mydLong", lng);
				
				//TextView testi = (TextView) findViewById(R.id.texti);
				//testi.setText(dName+"\n"+String.format("%.7f", dlat)+","+String.format("%.7f", dlng));
				
				
				//dName = getIntent().getExtras().getString("mydName");
				//dlat = getIntent().getExtras().getDouble("mydLat");
				//dlng = getIntent().getExtras().getDouble("mydLong");
	
				textInf = (TextView) findViewById(R.id.textInfo);

				md = new GMapV2Direction();
				
				/*if (mDMarker != null)
					mDMarker.remove();

				mDMarker = mMap.addMarker(new MarkerOptions()
							.position(new LatLng(dlat, dlng))
							.title(dName)
							.snippet(dlat + "," + dlng)
							.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
				
				LatLng startPosition = new LatLng(lat, lng);
				LatLng endPosition = new LatLng(dlat,dlng);*/

				/*Document doc = md.getDocument(startPosition
		                , endPosition, GMapV2Direction.MODE_DRIVING);
		        int duration = md.getDurationValue(doc);
		        String distance = md.getDistanceText(doc);
		        String strDuration = md.getDurationText(doc);

		        ArrayList<LatLng> directionPoint = md.getDirection(doc);
		        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
		        
		        for(int i = 0 ; i < directionPoint.size() ; i++) {            
		            rectLine.add(directionPoint.get(i));
		            mMap.addMarker(new MarkerOptions().position(directionPoint.get(i)));
		        }
		        
		        mMap.addPolyline(rectLine);
		        
		        textInf.setText("Distance : " + distance + "\n"
		        				+"Duration : " + strDuration + "\n");*/

			}
		}
	}
	//

	private ConnectionCallbacks mCallback = new ConnectionCallbacks() {
		public void onConnected(Bundle bundle) {
			// �������͡Ѻ Google Play Services ��
			Toast.makeText(MainActivity.this, "Services connected",
					Toast.LENGTH_SHORT).show();

			LocationRequest mRequest = new LocationRequest()
					.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
					.setInterval(5000).setFastestInterval(1000);

			mLocationClient.requestLocationUpdates(mRequest, locationListener);
		}

		public void onDisconnected() {
			// ��ش�������͡Ѻ Google Play Services
			Toast.makeText(MainActivity.this, "Services disconnected",
					Toast.LENGTH_SHORT).show();
		}
	};

	private OnConnectionFailedListener mListener = new OnConnectionFailedListener() {
		public void onConnectionFailed(ConnectionResult result) {
			// ������Դ�ѭ���������͡Ѻ Google Play Services �����
			Toast.makeText(MainActivity.this, "Services connection failed",
					Toast.LENGTH_SHORT).show();
		}
	};

	private boolean isServicesAvailable() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		return (resultCode == ConnectionResult.SUCCESS);
	}

	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			LatLng coordinate = new LatLng(location.getLatitude(),
					location.getLongitude());
			lat = location.getLatitude();
			lng = location.getLongitude();

			if (mMarker != null)
				mMarker.remove();

			mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(
					lat, lng)));
			mMap.animateCamera(CameraUpdateFactory
					.newLatLngZoom(coordinate, 16));

			/*
			CircleOptions co = new CircleOptions(); co.center(coordinate);
			co.radius(location.getAccuracy()); co.strokeColor(Color.BLUE);
			co.strokeWidth(4); mMap.addCircle(co);*/

			textView1.setText("Provider : " + location.getProvider() + "\n"
					+ "Latitude : " + location.getLatitude() + "\n"
					+ "Longitude : " + location.getLongitude() + "\n"
					+ "Accuracy : " + location.getAccuracy() + "m\n"
					+ "Speed : " + location.getSpeed() + "m/s\n" + "Bearing : "
					+ location.getBearing() + "\n");
			// + "Satellite : " + location.getExtras().getInt("satellites"));

		}
	};

}
