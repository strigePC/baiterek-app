package com.example.strig.baiterekapp;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;


public class LocationService extends Service {

    private LocationManager locationManager;
    private LocationListener listener;
    private final IBinder binder = new LocationServiceBinder ();
    public static final String TAG ="LOC_SERVICE_TAG";
    private Location loc;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocationServiceBinder extends Binder {

        LocationService getMyService () {
            return LocationService.this ;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createLocationListener();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
//        String provider = LocationManager.NETWORK_PROVIDER;
        Log.e(TAG, "onCreate: location provider - "+provider);
        int permissionCheckFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheckCoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheckFine == PackageManager.PERMISSION_GRANTED && permissionCheckCoarse == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "onCreate: permission granted");
            locationManager.requestLocationUpdates(provider, 1000, 1, listener);
            locationManager.getLastKnownLocation(provider);
        }
        Log.e(TAG, "onCreate: location"+loc);
    }


    public Location getInfo(){
        Log.e(TAG, "getInfo: loc: "+loc);
        return loc;
    }

    public void createLocationListener(){
        Log.e(TAG, "createLocationListener: ");
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e(TAG, "onLocationChanged: "+String.valueOf (location));
                loc = location;


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.e(TAG, "onStatusChanged: ");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.e(TAG, "onProviderEnabled: ");
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.e(TAG, "onProviderDisabled: ");
            }
        };
    }
}
