package org1.appanalysis;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.net.Uri;
import android.database.Cursor;
import android.database.ContentObserver;
import dalvik.system.*;
import android.telephony.TelephonyManager;
import android.telephony.*;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStreamReader;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.*;
import android.widget.Toast;


public class SampleApp extends Activity {
    private static final String TAG = SampleApp.class.getSimpleName();

    private static Button NIMEIButton;
    private static Button NGPSButton;
    private static Button GPSButton;
    private static Button IMEIButton;

    public Activity a = null;
    public LocationManager mloc = null;
   public LocationListener locationListener;
	public double latitude, longitude;
	public String STRINGCOPY;	
	private void ToNetwork(String s){
				STRINGCOPY = s;
				new Thread(new Runnable() {
				public void run() {
					try {
						Socket nSocket = null;
						
						nSocket = new Socket();
						Log.e("run","Thread");
						InetAddress addr = InetAddress.getByName("www.cs.stonybrook.edu");
						
						SocketAddress sockaddr = new InetSocketAddress(addr, 80);
						nSocket.connect(sockaddr, 10000);
						DataOutputStream dos = new DataOutputStream(nSocket.getOutputStream());
						dos.writeUTF(STRINGCOPY);
						dos.close();
						nSocket.close();
				}catch (UnknownHostException e) {
					e.printStackTrace();
                                 }catch (IOException e) {
					e.printStackTrace();		
                                					
				}
			}
		}).start();
		}
		
    private OnClickListener onClickNIMEIButton = new OnClickListener() {
        public void onClick(View v) {
		Taint.log(" before device ID");
		String s = ((TelephonyManager)a.getSystemService("phone")).getDeviceId();
		Taint.log("device is is equal to "+s );
		ToNetwork(s);
		Taint.log("after sending");
        }
    };

	
    private OnClickListener onClickNGPSButton = new OnClickListener() {
        public void onClick(View v) {
		
		Criteria criteria = new Criteria();
	      	criteria.setAccuracy(Criteria.ACCURACY_FINE);  
	          
	        criteria.setAltitudeRequired(false);  
	        criteria.setBearingRequired(false);  
	        criteria.setCostAllowed(true);   
	        criteria.setPowerRequirement(Criteria.POWER_LOW);  
	        String provider = mloc.getBestProvider(criteria, true);
	    
		mloc.requestLocationUpdates(provider, 2000, 0, locationListener); 
	       Location loc=mloc.getLastKnownLocation(provider);
		
		
		if(loc != null){
			latitude = loc.getLatitude();
			longitude = loc.getLongitude();
		
			Log.d(TAG,"Geo_location is "+ latitude + " " + longitude);
			Log.d(TAG, "after removing the tag");	
			sendDataOverNetwork(Double.toString(latitude));	
	
        }
	}
    };
	
	private OnClickListener onClickIMEIButton = new OnClickListener(){
		public void onClick(View v){
		Taint.log("pallavi before device ID");
		String s = ((TelephonyManager)a.getSystemService("phone")).getDeviceId();
		Taint.log("pallavi after String s");
		ReferenceMonitor.RemoveTaint(s,Taint.TAINT_IMEI,Taint.TAINT_POSIX_IO,Taint.TAINT_FOREVER);
		Taint.log("after printing s");
		ToNetwork(s);
		Taint.log("after sending");
		}
	};

    private OnClickListener onClickGPSButton = new OnClickListener() {
	    public void onClick(View v){

 
		Criteria criteria = new Criteria();
	      	criteria.setAccuracy(Criteria.ACCURACY_FINE);  
	          
	        criteria.setAltitudeRequired(false);  
	        criteria.setBearingRequired(false);  
	        criteria.setCostAllowed(true);   
	        criteria.setPowerRequirement(Criteria.POWER_LOW);  
	        String provider = mloc.getBestProvider(criteria, true);
	    
		mloc.requestLocationUpdates(provider, 2000, 0, locationListener); 
	       Location loc=mloc.getLastKnownLocation(provider);
		
		
		if(loc != null){
			latitude = loc.getLatitude();
			Log.d(TAG,"Geo_location is "+ latitude );

			ReferenceMonitor.RemoveTaint(latitude,Taint.TAINT_LOCATION|Taint.TAINT_LOCATION_GPS, Taint.TAINT_POSIX_IO, Taint.TAINT_FOREVER);
		
			Log.d(TAG, "after removing the tag");	
			sendDataOverNetwork(Double.toString(latitude));	
	
	}

	    }
    };   


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);
        


	a = this;

        NIMEIButton = (Button) findViewById(R.id.NIMEIButton);
        NIMEIButton.setOnClickListener(onClickNIMEIButton);
        
        NGPSButton = (Button) findViewById(R.id.NGPSButton);
        NGPSButton.setOnClickListener(onClickNGPSButton);
    	
    	GPSButton = (Button) findViewById(R.id.GPSButton);
	GPSButton.setOnClickListener(onClickGPSButton);
	
	IMEIButton = (Button) findViewById(R.id.IMEIButton);
	IMEIButton.setOnClickListener(onClickIMEIButton);
		
		mloc = (LocationManager)a.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new LocationListener() 
	    {     
	        public void onLocationChanged(Location loc) 
	        {      
	            // Called when a new location is found by the network location provider.       
	            Log.i("loc","location changed");
	            if (loc!=null)
	            {
	                loc.getLatitude(); 
	                loc.getLongitude(); 
	                String Text = "My current location is: " + 
	                "Latitud =" + loc.getLatitude() + 
	                "Longitud = " + loc.getLongitude(); 
	            }
	            
	          
	            
	        }      
	        public void onStatusChanged(String provider, int status, Bundle extras) 
	        {}      
	        public void onProviderEnabled(String provider) 
	        {}      
	        public void onProviderDisabled(String provider) 
	        {}   
	    };

    }
}
