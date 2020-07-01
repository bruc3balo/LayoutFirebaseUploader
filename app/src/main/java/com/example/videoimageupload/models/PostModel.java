package com.example.videoimageupload.models;

public class PostModel {

    private Comment comment;
    private Rating rating;
    private Shared shared;
    private Tags tags;
    private double average_rating;
    private Views views;
    private PostMetadata postMetadata;

    public static String POSTS = "Posts";

    public PostModel(PostMetadata postMetadata,Comment comment, Rating rating, Shared shared, Tags tags, Views views ,double average_rating) {
        this.comment = comment;
        this.rating = rating;
        this.shared = shared;
        this.tags = tags;
        this.average_rating = average_rating;
        this.views = views;
        this.postMetadata = postMetadata;
    }

    public PostMetadata getPostMetadata() {
        return postMetadata;
    }

    public void setPostMetadata(PostMetadata postMetadata) {
        this.postMetadata = postMetadata;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Shared getShared() {
        return shared;
    }

    public void setShared(Shared shared) {
        this.shared = shared;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(double average_rating) {
        this.average_rating = average_rating;
    }

    public Views getViews() {
        return views;
    }

    public void setViews(Views views) {
        this.views = views;
    }

}
