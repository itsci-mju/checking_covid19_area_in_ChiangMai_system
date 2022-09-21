package com.example.test_bottom_navbar;

public class News {
    private String newsTitle;
    private String newsImg;
    private String newsDate;
    private String newsDetail;

    public News(){ }

    public News(String newsTitle, String newsImg, String newsDate, String detail) {
        this.newsTitle = newsTitle;
        this.newsImg = newsImg;
        this.newsDate = newsDate;
        newsDetail = detail;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getDetail() {
        return newsDetail;
    }

    public void setDetail(String detail) {
        newsDetail = detail;
    }
}
