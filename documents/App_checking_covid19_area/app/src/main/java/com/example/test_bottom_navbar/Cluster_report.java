package com.example.test_bottom_navbar;
import java.io.Serializable;

public class Cluster_report {
    private String cluster_newpatinet_today;
    private String cluster_getwellpatinet_today;
    private String cluster_Allpatient_district;
    private String cluster_Allgetwell_district;
    private String cluster_Allhealing_district;


    public Cluster_report(){}

    public Cluster_report(String cluster_newpatinet_today, String cluster_getwellpatinet_today, String cluster_Allpatient_district, String cluster_Allgetwell_district, String cluster_Allhealing_district) {
        this.cluster_newpatinet_today = cluster_newpatinet_today;
        this.cluster_getwellpatinet_today = cluster_getwellpatinet_today;
        this.cluster_Allpatient_district = cluster_Allpatient_district;
        this.cluster_Allgetwell_district = cluster_Allgetwell_district;
        this.cluster_Allhealing_district = cluster_Allhealing_district;
    }

    public String getCluster_newpatinet_today() {
        return cluster_newpatinet_today;
    }

    public void setCluster_newpatinet_today(String cluster_newpatinet_today) {
        this.cluster_newpatinet_today = cluster_newpatinet_today;
    }

    public String getCluster_getwellpatinet_today() {
        return cluster_getwellpatinet_today;
    }

    public void setCluster_getwellpatinet_today(String cluster_getwellpatinet_today) {
        this.cluster_getwellpatinet_today = cluster_getwellpatinet_today;
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
