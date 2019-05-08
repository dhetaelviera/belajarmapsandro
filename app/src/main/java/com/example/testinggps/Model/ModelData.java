package com.example.testinggps.Model;


import java.util.ArrayList;

public class ModelData extends ArrayList<String> {
    String username, nama;
    Double lat, lng;

    public ModelData(){}

    public ModelData(String username, String nama, Double lat, Double lng){
        this.lat=lat;
        this.username=username;
        this.nama=nama;
        this.lng=lng;
    }

    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        this.username = username;
        return username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Object getAsJsonObject( String nama) {
        this.nama=nama;
    return nama;
    }
}
