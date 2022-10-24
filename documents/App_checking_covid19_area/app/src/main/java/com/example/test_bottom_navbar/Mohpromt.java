package com.example.test_bottom_navbar;
import java.io.Serializable;

public class Mohpromt implements Serializable {
    private String mohpromtPlace;
    private String mohpromtType;
    private String mohpromtStartDate;
    private String mohpromtEndDate;
    private String mohpromtStartTime;
    private String mohpromtEndTime;
    private String mohpromtLat;
    private String mohpromtLng;
    private String mohpromtDetail;
    private String mohpromtAddress;

    public Mohpromt(){ }

    public Mohpromt(String mohpromtPlace, String mohpromtType, String mohpromtStartDate, String mohpromtEndDate, String mohpromtStartTime, String mohpromtEndTime, String mohpromtLat, String mohpromtLng, String mohpromtDetail, String mohpromtAddress) {
        this.mohpromtPlace = mohpromtPlace;
        this.mohpromtType = mohpromtType;
        this.mohpromtStartDate = mohpromtStartDate;
        this.mohpromtEndDate = mohpromtEndDate;
        this.mohpromtStartTime = mohpromtStartTime;
        this.mohpromtEndTime = mohpromtEndTime;
        this.mohpromtLat = mohpromtLat;
        this.mohpromtLng = mohpromtLng;
        this.mohpromtDetail = mohpromtDetail;
        this.mohpromtAddress = mohpromtAddress;
    }

    public String getMohpromtPlace() {
        return mohpromtPlace;
    }

    public void setMohpromtPlace(String mohpromtPlace) {
        this.mohpromtPlace = mohpromtPlace;
    }

    public String getMohpromtType() {
        return mohpromtType;
    }

    public void setMohpromtType(String mohpromtType) {
        this.mohpromtType = mohpromtType;
    }

    public String getMohpromtStartDate() {
        return mohpromtStartDate;
    }

    public void setMohpromtStartDate(String mohpromtStartDate) {
        this.mohpromtStartDate = mohpromtStartDate;
    }

    public String getMohpromtEndDate() {
        return mohpromtEndDate;
    }

    public void setMohpromtEndDate(String mohpromtEndDate) {
        this.mohpromtEndDate = mohpromtEndDate;
    }

    public String getMohpromtStartTime() {
        return mohpromtStartTime;
    }

    public void setMohpromtStartTime(String mohpromtStartTime) {
        this.mohpromtStartTime = mohpromtStartTime;
    }

    public String getMohpromtEndTime() {
        return mohpromtEndTime;
    }

    public void setMohpromtEndTime(String mohpromtEndTime) {
        this.mohpromtEndTime = mohpromtEndTime;
    }

    public String getMohpromtLat() {
        return mohpromtLat;
    }

    public void setMohpromtLat(String mohpromtLat) {
        this.mohpromtLat = mohpromtLat;
    }

    public String getMohpromtLng() {
        return mohpromtLng;
    }

    public void setMohpromtLng(String mohpromtLng) {
        this.mohpromtLng = mohpromtLng;
    }

    public String getMohpromtDetail() {
        return mohpromtDetail;
    }

    public void setMohpromtDetail(String mohpromtDetail) {
        this.mohpromtDetail = mohpromtDetail;
    }

    public String getMohpromtAddress() {
        return mohpromtAddress;
    }

    public void setMohpromtAddress(String mohpromtAddress) {
        this.mohpromtAddress = mohpromtAddress;
    }
}
