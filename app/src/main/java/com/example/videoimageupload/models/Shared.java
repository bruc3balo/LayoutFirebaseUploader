package com.example.videoimageupload.models;

public class Shared {
    private String userShared;
    private String userReceived;
    private String postId;

    public Shared(String userShared, String userReceived, String postId) {
        this.userShared = userShared;
        this.userReceived = userReceived;
        this.postId = postId;
    }

    public String getUserShared() {
        return userShared;
    }

    public void setUserShared(String userShared) {
        this.userShared = userShared;
    }

    public String getUserReceived() {
        return userReceived;
    }

    public void setUserReceived(String userReceived) {
        this.userReceived = userReceived;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
