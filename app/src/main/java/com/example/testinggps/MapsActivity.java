package com.example.testinggps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.testinggps.Adapter.Adapter;
import com.example.testinggps.Model.ModelData;
import com.example.testinggps.Util.AppControler;
import com.example.testinggps.Util.ServerAPI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    double lattt,lnggg, latq, lngq;
    TextView norek,nama;
    int a;
    List<ModelData> mItems;
    ProgressDialog progressDialog;
    private Context mContext;
    String dataNama,dataNorek;
    String item[][];
    int panjangArray, helo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        support = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        progressDialog = new ProgressDialog(MapsActivity.this);
        mItems = new ArrayList<>();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        item=new String[4][panjangArray];


        support.getMapAsync(this);

    }

    public double jarak(double lat1, double lat2, double lng1, double lng2){
        lat1=lattt;
        lng1=lnggg;
        lat2=latq;
        lng2=lngq;

        double selisihlat=(lat1-lat2)*30.416;
        double selisihlng=(lng1-lng2)*30.416;

        double jaraknya=Math.sqrt((Math.pow(selisihlat,2))+(Math.pow(selisihlng,2)));
        System.out.println("hitung di methodnya = "+jaraknya);


        return jaraknya;


    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        RequestQueue queue = Volley.newRequestQueue(this);
        progressDialog.setMessage("Ngambil data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonArrayRequest reqData=new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DATA,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray response) {
                        System.out.println("DARI MAPS YA RESPONSENYA: ");
                        System.out.println(response);
                        progressDialog.cancel();
                        Log.d("volley ", "response = " + response.toString());
                        System.out.println("Ini panjang response= "+response.length());
                        panjangArray=response.length();
                        for (int i=0;i<response.length();i++){
                            item= new String [4][panjangArray];
                            try {
                                JSONObject data= response.getJSONObject(i);

                                item[0][i]=data.getString("nama");
                                item[1][i]=Double.toString(data.getDouble("lat"));
                                item[2][i]=Double.toString(data.getDouble("lng"));
                                item[3][i]=data.getString("norek");
                                System.out.println("Norek yg masuk ke array= "+ item[3][i]);

                                LatLng kk = new LatLng(lattt, lnggg);
                                double ini=jarak(latq,lngq,lattt,lnggg);
                                System.out.println("jarakku dengan kamu adalah "+ini);

                                mMap.addMarker(new MarkerOptions().position(kk).title(item[0][i]));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(kk));

                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        norek=(TextView) findViewById(R.id.tvnorek);
                                        nama=(TextView) findViewById(R.id.tvnama);
                                        String namama=marker.getTitle();
                                        nama.setText(namama);
                                        System.out.println(response.length());

                                        return false;
                                    }
                                });
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
                        Log.d("volley","error="+ error.getMessage());
                    }
                }
        );

        System.out.println(reqData);
        queue.add(reqData);
        System.out.println("INI PANJANG ARRAY "+panjangArray);
        System.out.println(latq+" == "+lngq);

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
            System.out.println("NYOOOOOOOOOOO");
            System.out.println(mLastLocation);

            if (mLastLocation != null) {
                double lat = mLastLocation.getLatitude();
                double lng = mLastLocation.getLongitude();
                System.out.println("Latitude= " + lat + ", longitude= " + lng);
                support.getMapAsync(this);

                LatLng bb = new LatLng(lat, lng);
                System.out.println(bb + "ini BBBBBBBBBBBB");
                latq=lat;
                lngq=lng;
                mMap.addMarker(new MarkerOptions()
                        .position(bb)
                        .title("u're here")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(bb));

            }
        }
    }
}



