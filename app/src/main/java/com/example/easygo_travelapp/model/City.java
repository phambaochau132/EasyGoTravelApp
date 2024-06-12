package com.example.easygo_travelapp.model;

import java.io.Serializable;

public class City implements Serializable {
    private int idCity;
    private String imageCity;
    private String nameCity;
    private String idLocation;

    private int price;

    private int rating;

    private double latitude;
    private double longitude;

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getImageCity() {
        return imageCity;
    }

    public void setImageCity(String imageCity) {
        this.imageCity = imageCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public City() {
    }

    public City(int idCity, String imageCity, String nameCity, String idLocation, double latitude, double longitude) {
        this.idCity = idCity;
        this.imageCity = imageCity;
        this.nameCity = nameCity;
        this.idLocation = idLocation;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
