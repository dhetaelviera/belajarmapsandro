package com.example.testinggps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.security.Permission;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private SupportMapFragment support;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
          support= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mGoogleApiClient=new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng lokasi=new LatLng(lat,lng);
        System.out.println("Latitude= " + lat +", longitude= "+lng);
        mMap.addMarker(new MarkerOptions().position(lokasi).title("Kamu di sini"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasi));

    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    public void onPause() {
        mGoogleApiClient.disconnect();
        super.onPause();
    }
    public void onResume() {
        mGoogleApiClient.connect();
        super.onResume();
    }

    public void onLocationChanged(Location location){

    }

    public void onConnectionSuspended(int arg0){

    }

    public void onStatusChange (String provider, int status, Bundle extras){

    }


    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mLocation= LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation!=null){
                lat=mLocation.getLatitude();
                lng=mLocation.getLongitude();
                System.out.println("Latitude= " + lat +", longitude= "+lng);
                support.getMapAsync(this);

            }
        }
    }

    public void onConnectionFailed( ConnectionResult connectionResult) {

    }
}
