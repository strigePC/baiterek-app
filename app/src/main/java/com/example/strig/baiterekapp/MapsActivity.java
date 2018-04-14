package com.example.strig.baiterekapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private ServiceConnection serviceConnection;
    private LocationService locService;
    private boolean serviceBound;
    private Location myLocation, baiLocation;
    private LatLng user, baiterek;
    private String TAG = "MAPS_ACTIVITY_TAG";
    private double distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e(TAG, "onServiceConnected: ");
                LocationService.LocationServiceBinder locationServiceBinder = (LocationService.LocationServiceBinder) iBinder;
                locService = locationServiceBinder.getMyService();
                serviceBound = true;


            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.e(TAG, "onServiceDisconnected: ");
                serviceBound = false;
            }
        };

        interactWithService();

    }

    private void interactWithService() {
        Log.e(TAG, "interactWithService: ");
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (serviceBound) {
                    Log.e(TAG, "run: service bound");
                    myLocation = locService.getInfo();
                    if (myLocation == null) {
                        user = new LatLng(51.0905, 71.3982);
                        myLocation = new Location("");
                        myLocation.setLatitude(user.latitude);
                        myLocation.setLongitude(user.longitude);


                    }
                    mMap.addMarker(new MarkerOptions().position(user).title("Marker at your location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(user));
                    distance = myLocation.distanceTo(baiLocation);
                    Toast.makeText(locService, "Distance from Baiterek to you is "+distance+" meters", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "run: my location" + myLocation);

                } else{
                    Log.e(TAG, "run: NOT BOUND");
                }
                handler.postDelayed(this, 1000);
            }

        });
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(TAG, "onMapReady: ");
        mMap = googleMap;

        // Add a marker at Baiterek and move the camera
        baiterek = new LatLng(51.1283, 71.4305);
        baiLocation = new Location("");
        baiLocation.setLatitude(baiterek.latitude);
        baiLocation.setLongitude(baiterek.longitude);
        mMap.addMarker(new MarkerOptions().position(baiterek).title("Marker at Baiterek"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(baiterek, 13);
        mMap.animateCamera(cameraUpdate);
        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "onMapReady: ");
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceBound){
            unbindService(serviceConnection);
            serviceBound=false;
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
//        LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Log.e(TAG, "onMyLocationClick: ");
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }

}