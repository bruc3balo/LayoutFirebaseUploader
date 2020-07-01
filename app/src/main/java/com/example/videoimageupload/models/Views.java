package com.example.videoimageupload.models;

public class Views {
    private String userViewed;
    private String postId;

    public Views(String userViewed, String postId) {
        this.userViewed = userViewed;
        this.postId = postId;
    }

    public String getUserViewed() {
        return userViewed;
    }

    public void setUserViewed(String userViewed) {
        this.userViewed = userViewed;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
