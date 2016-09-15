package com.example.admin.androidproject2;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;

public class MyLocation extends Observable implements LocationListener {
    // set up location manager
    private LocationManager locationManager = null;

    //double lat;
    //double lon;
    //private static final String TAG = "MyLocation";

    public MyLocation(Activity activity){
        try {
            // GPS Provider does not work on my tablet, had to use Network Provider
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            // request location updates frequently from network provider
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, this);
            // catch the exception
        } catch(SecurityException e) {
            locationManager = null;
        }
    }

    public void getLastKnownLocation(){
        try {
            // Get the last known location from NetworkProvider
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            // notify observers and set changed
            setChanged();
            notifyObservers(lastKnownLocation);
            // catch exception
        } catch(SecurityException e) {
            locationManager = null;
        }
    }
    //lat = loc.getLatitude();
        //lon = loc.getLongitude();

    @Override
    public void onLocationChanged(Location location) {
        // notify observers of the new location and set changed
        setChanged();
        notifyObservers(location);
        //Log.d(TAG, "LAT: " + lat + ", LON: " + lon);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("Check your Provider");
    }
}
