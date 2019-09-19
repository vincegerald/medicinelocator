package com.example.myapplication.ui.medicine;

public class MedicineItem {

    private String pharmacyName;

    private String pharmacyId;

    private String genericName;

    private String brandName;

    private String medIndication;


    public MedicineItem(String pharmacyName, String pharmacyId, String genericName, String brandName, String medIndication){
        this.pharmacyName = pharmacyName;
        this.pharmacyId = pharmacyId;
        this.genericName = genericName;
        this.brandName = brandName;
        this.medIndication = medIndication;
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

    public String getMedIndication() {
        return medIndication;
    }

    public void setMedIndication(String medIndication) {
        this.medIndication = medIndication;
    }
}
