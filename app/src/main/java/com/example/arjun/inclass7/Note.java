package com.example.arjun.inclass7;

/**
 * Created by arjun on 10/11/2015.
 */
public class Note {
    private long _id;
    private String appName,devName,Date,price,category,imgurl,isfav;

    public Note( String appName, String devName, String date, String price, String category, String imgurl,String isfav) {

        this.appName = appName;
        this.devName = devName;
        Date = date;
        this.price = price;
        this.category = category;
        this.imgurl = imgurl;
        this.isfav = isfav;
    }

    public Note() {

    }

    public String getIsfav() {
        return isfav;
    }

    public void setIsfav(String isfav) {
        this.isfav = isfav;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }



    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", appName='" + appName + '\'' +
                ", devName='" + devName + '\'' +
                ", Date='" + Date + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}
