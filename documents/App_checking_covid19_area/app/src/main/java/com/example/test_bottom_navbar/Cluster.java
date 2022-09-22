package com.example.test_bottom_navbar;
import java.io.Serializable;

public class Cluster implements Serializable {
    private String clusterDate;
    private String clusterPlace;
    private String clusterSubdistrict;
    private String clusterDistrict;
    private String cluster_news_patient;

    public Cluster(String clusterDate, String clusterPlace, String clusterSubdistrict, String clusterDistrict, String cluster_news_patient) {
        this.clusterDate = clusterDate;
        this.clusterPlace = clusterPlace;
        this.clusterSubdistrict = clusterSubdistrict;
        this.clusterDistrict = clusterDistrict;
        this.cluster_news_patient = cluster_news_patient;
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

    public String getCluster_news_patient() {
        return cluster_news_patient;
    }

    public void setCluster_news_patient(String cluster_news_patient) {
        this.cluster_news_patient = cluster_news_patient;
    }
}
