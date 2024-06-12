package com.example.easygo_travelapp.model;

import java.io.Serializable;
import java.util.List;

public class DetailScenic extends ItemScenic implements Serializable {
    private int rating;
    private int timeTour;
    private String description;
    private String photos;
    private List<Review> review;

    private List<City> cities;

    private int price;

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public int getTimeTour() {
        return timeTour;
    }

    public void setTimeTour(int timeTour) {
        this.timeTour = timeTour;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DetailScenic() {
    }

    public DetailScenic(int idScenic, String imageScenic, String nameScenic, String location, int rating, int timeTour, String description, String photos, List<Review> review, List<City> cities, int price) {
        super(idScenic, imageScenic, nameScenic, location);
        this.rating = rating;
        this.timeTour = timeTour;
        this.description = description;
        this.photos = photos;
        this.review = review;
        this.cities = cities;
        this.price = price;
    }
}
