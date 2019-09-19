package com.example.myapplication.ui.home;

public class PharmacyModel {

    private String pharmacyName;

    private String pharmacyId;

    private String location;

    private String longitude;

    private String latitude;

    private String genericName;

    private String brandName;



    public PharmacyModel(String pharmacyName, String pharmacyId, String location, String longitude, String latitude, String genericName,String brandName){
        this.pharmacyName = pharmacyName;
        this.pharmacyId = pharmacyId;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.genericName = genericName;
        this.brandName = brandName;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
