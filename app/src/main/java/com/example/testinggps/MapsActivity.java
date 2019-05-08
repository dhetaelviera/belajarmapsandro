package com.example.testinggps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.testinggps.Adapter.Adapter;
import com.example.testinggps.Model.ModelData;
import com.example.testinggps.Util.ServerAPI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private SupportMapFragment support;
    private Adapter mAdapter;
    double lat, lng;
    int a;
    List<ModelData> mItems;
    ProgressDialog progressDialog;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        support = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        progressDialog = new ProgressDialog(MapsActivity.this);
        mItems = new ArrayList<>();
        mAdapter = new Adapter(MapsActivity.this, mItems);

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        onMapReady(mMap);
        support.getMapAsync(this);

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        System.out.println(lat + "=== "+lng);

        System.out.println("LAAAAAAAAAAAAAAAA");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DATA, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println("ini responsenya: ");
                        System.out.println(response);
                        progressDialog.cancel();
                        Log.d("volley ", "response = " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setLng(data.getDouble("lng"));
                                md.setLat(data.getDouble("lat"));
                                md.setUsername(data.getString("username"));
                                String user = data.getString("username");
                                double lattt = data.getDouble("lat");
                                double lnggg = data.getDouble("lng");
                                System.out.println(user + " adalah user ke- " + i);
                                System.out.println(lattt + " --- " + lnggg);
                                mItems.add(md);
                                a = mItems.size();

                                LatLng kk = new LatLng(lattt, lnggg);
                                System.out.println(kk);

                                mMap.addMarker(new MarkerOptions().position(kk).title(user));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(kk));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Log.d("volley", "error=" + error.getMessage());
                    }
                }
        );

        System.out.println(a);
        queue.add(reqData);

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

    public void onLocationChanged(Location location) {

    }

    public void onConnectionSuspended(int arg0) {

    }

    public void onStatusChange(String provider, int status, Bundle extras) {

    }

    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void onConnected(Bundle bundle) {
        System.out.println("MASUK GAAAAAA");
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lng = mLastLocation.getLongitude();
                System.out.println("Latitude= " + lat + ", longitude= " + lng);
                support.getMapAsync(this);

                LatLng bb = new LatLng(lat, lng);
                System.out.println(bb + "ini BBBBBBBBBBBB");

                mMap.addMarker(new MarkerOptions().position(bb).title("u're here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(bb));

            }
        }
    }
}



