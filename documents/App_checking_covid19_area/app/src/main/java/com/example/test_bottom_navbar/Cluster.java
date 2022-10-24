package com.example.test_bottom_navbar;
import java.io.Serializable;

public class Cluster implements Serializable {
    private String clusterDate;
    private String clusterPlace;
    private String clusterSubdistrict;
    private String clusterDistrict;

    private String cluster_getwell_patient;
    private String cluster_news_patient;
    private String cluster_All_patient;

    private String cluster_Allpatient_district;
    private String cluster_Allgetwell_district;
    private String cluster_Allhealing_district;

    private String clusterLat;
    private String clusterLng;

    public Cluster(){ }

    public Cluster(String clusterDate, String clusterPlace, String clusterSubdistrict, String clusterDistrict, String cluster_getwell_patient, String cluster_news_patient, String cluster_All_patient, String cluster_Allpatient_district, String cluster_Allgetwell_district, String cluster_Allhealing_district, String clusterLat, String clusterLng) {
        this.clusterDate = clusterDate;
        this.clusterPlace = clusterPlace;
        this.clusterSubdistrict = clusterSubdistrict;
        this.clusterDistrict = clusterDistrict;
        this.cluster_getwell_patient = cluster_getwell_patient;
        this.cluster_news_patient = cluster_news_patient;
        this.cluster_All_patient = cluster_All_patient;
        this.cluster_Allpatient_district = cluster_Allpatient_district;
        this.cluster_Allgetwell_district = cluster_Allgetwell_district;
        this.cluster_Allhealing_district = cluster_Allhealing_district;
        this.clusterLat = clusterLat;
        this.clusterLng = clusterLng;
    }

    public String getClusterDate() {
        return clusterDate;
    }

    public void setClusterDate(String clusterDate) {
        this.clusterDate = clusterDate;
    }

    public String getClusterPlace() {
        return clusterPlace;
    }

    public void setClusterPlace(String clusterPlace) {
        this.clusterPlace = clusterPlace;
    }

    public String getClusterSubdistrict() {
        return clusterSubdistrict;
    }

    public void setClusterSubdistrict(String clusterSubdistrict) {
        this.clusterSubdistrict = clusterSubdistrict;
    }

    public String getClusterDistrict() {
        return clusterDistrict;
    }

    public void setClusterDistrict(String clusterDistrict) {
        this.clusterDistrict = clusterDistrict;
    }

    public String getClusterLat() {
        return clusterLat;
    }

    public void setClusterLat(String clusterLat) {
        this.clusterLat = clusterLat;
    }

    public String getClusterLng() {
        return clusterLng;
    }

    public void setClusterLng(String clusterLng) {
        this.clusterLng = clusterLng;
    }

    public String getCluster_getwell_patient() {
        return cluster_getwell_patient;
    }

    public void setCluster_getwell_patient(String cluster_getwell_patient) {
        this.cluster_getwell_patient = cluster_getwell_patient;
    }

    public String getCluster_news_patient() {
        return cluster_news_patient;
    }

    public void setCluster_news_patient(String cluster_news_patient) {
        this.cluster_news_patient = cluster_news_patient;
    }

    public String getCluster_All_patient() {
        return cluster_All_patient;
    }

    public void setCluster_All_patient(String cluster_All_patient) {
        this.cluster_All_patient = cluster_All_patient;
    }

    public String getCluster_Allpatient_district() {
        return cluster_Allpatient_district;
    }

    public void setCluster_Allpatient_district(String cluster_Allpatient_district) {
        this.cluster_Allpatient_district = cluster_Allpatient_district;
    }

    public String getCluster_Allgetwell_district() {
        return cluster_Allgetwell_district;
    }

    public void setCluster_Allgetwell_district(String cluster_Allgetwell_district) {
        this.cluster_Allgetwell_district = cluster_Allgetwell_district;
    }

    public String getCluster_Allhealing_district() {
        return cluster_Allhealing_district;
    }

    public void setCluster_Allhealing_district(String cluster_Allhealing_district) {
        this.cluster_Allhealing_district = cluster_Allhealing_district;
    }
}
