package com.example.testinggps.Model;

public class ModelData  {
    String norek, nama;
    Double lat, lng;

    public ModelData(){}

    public ModelData(String norek, String nama, Double lat, Double lng){
        this.lat=lat;
        this.norek=norek;
        this.nama=nama;
        this.lng=lng;
    }

    public String getNorek() {
        return norek;
    }

    public void setNorek(String norek) {
        this.norek = norek;
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

}
