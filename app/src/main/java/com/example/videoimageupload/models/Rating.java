package com.example.videoimageupload.models;

public class Rating {
    private double rating;
    private String userRated;

    public Rating(double rating, String userRated) {
        this.rating = rating;
        this.userRated = userRated;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUserRated() {
        return userRated;
    }

    public void setUserRated(String userRated) {
        this.userRated = userRated;
    }
}
