package com.example.strig.baiterekapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private ServiceConnection serviceConnection;
    private LocationService locService;
    private boolean serviceBound;
    private Location myLocation, baiLocation;
    private LatLng user, baiterek;
    private String TAG = "MAPS_ACTIVITY_TAG";
    private int distance;
    private TextView distanceTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar()!=null){
            Log.e(TAG, "onCreate: action bar not null");
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_maps);
        distanceTextView = findViewById(R.id.distance_tv);
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
                    if (myLocation!=null) {
                        user = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(user).title("Marker at your location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(user));
                        distance = (int) myLocation.distanceTo(baiLocation);
                        distanceTextView.setText(String.format("Distance from Baiterek to your location is approximately %s meters", distance));
                        Log.e(TAG, "run: my location" + myLocation);
                    }
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
        Log.e(TAG, "onMyLocationButtonClick: ");
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Log.e(TAG, "onMyLocationClick: "+location);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
