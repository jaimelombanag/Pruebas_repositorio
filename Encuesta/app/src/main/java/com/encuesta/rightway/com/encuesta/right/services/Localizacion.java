package com.encuesta.rightway.com.encuesta.right.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.encuesta.rightway.encuesta.Constants;

import java.util.Date;

/**
 * Created by jalombanag on 19/04/2016.
 */
public class Localizacion extends Service implements Runnable {

    private static final String TAG = "Seguimiento";
    private static final int LOCATION_INTERVAL = 0;
    private static final float LOCATION_DISTANCE = 0;


    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location oldLocation = null;
    private long lastReport = 0;


    private boolean initialized = false;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, ": START " + startId + ": " + intent);
        if(!initialized){
            initialized = true;
            run();
        }
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return(null);
    }




    private void changeReportParameters(Intent intent) {
        locationManager.removeUpdates(locationListener);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,locationListener);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,locationListener);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        InitGPS();
        Log.d(TAG,  ": LOCATION UPDATES - DISTANCE: "+LOCATION_DISTANCE+" INTERVAL: "+LOCATION_INTERVAL);
    }


    @Override
    public void run() {
        try {
            initializeLocationListener();
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            InitGPS();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);
            Log.d(TAG, ": LOCATION UPDATES - DISTANCE: " + LOCATION_DISTANCE + " INTERVAL: " + LOCATION_INTERVAL);
        } catch (Exception e) {
            Log.d(TAG, ": GRAVE " + e.getMessage());
            e.printStackTrace();
        }

    }


    public void InitGPS(){
        // exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {

            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception ex) {
        }

        // don't start listeners if no provider is enabled
        if (!gps_enabled && !network_enabled) {

            Toast.makeText(getApplicationContext(), "Sin GPS, Por favor verifique que tenga habilitada TODAS las funciones de ubicacion", Toast.LENGTH_LONG).show();
        }

        if (gps_enabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        if (network_enabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    private void initializeLocationListener(){
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                Date d = new Date();
                CharSequence fechaHora  = DateFormat.format("yyyy-MM-dd hh:mm:ss", d.getTime());

                //String TramaGPS =  location.getTime() + "|" + location.getLatitude()+ "|" + location.getLongitude() + "|" + location.getAccuracy() + "|" + location.getSpeed();
                String TramaGPS = fechaHora+ ";" + location.getSpeed() + ";" + location.getAccuracy()+ ";" + location.getAltitude() + ";" + location.getLatitude() + ";" + location.getLongitude();
                //Log.i(TAG, "CAMBIA LA SEÑAL DE GPS!!!!!!!!!!  " +  TramaGPS );


                String lat =  String.valueOf(location.getLatitude());
                String lon =  String.valueOf(location.getLongitude());

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.Latitud,lat );
                editor.putString(Constants.Longitud,lon );
                editor.commit();

            }

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                if(arg0.equals(LocationManager.GPS_PROVIDER)||arg0.equals(LocationManager.NETWORK_PROVIDER)){
                    notifyNoLocation();
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.w(TAG, ": LOCATION PROVIDER DISABLED");
                //if(provider.equals(LocationManager.GPS_PROVIDER)){
                if(provider.equals(LocationManager.GPS_PROVIDER)||provider.equals(LocationManager.NETWORK_PROVIDER)){
                    notifyNoLocation();
                }
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.w(TAG, ": LOCATION PROVIDER ENABLED");
//				if(provider.equals(LocationManager.GPS_PROVIDER)){
                if(provider.equals(LocationManager.GPS_PROVIDER)||provider.equals(LocationManager.NETWORK_PROVIDER)){
                    notifyNoLocation();
                }
            }

            private void notifyNoLocation(){
                //appState.setLocation(null);
            }

        };
    }


    @Override
    public void onDestroy() {
        try {
            Log.i(TAG, "onDestroy");
            locationManager.removeUpdates(locationListener);
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
