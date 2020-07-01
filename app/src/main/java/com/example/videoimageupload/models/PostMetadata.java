package com.example.videoimageupload.models;

public class PostMetadata {
    private String postId; //will be document name
    private String media_url;
    private String postedBy; //will be collections name
    private String mediaType;
    private String title;
    private String description;
    private String timeStamp;

    public static String POST_VIDEO = "Video",POST_IMAGES = "Image",POST_TEXT = "Text";

    public PostMetadata(String postId, String media_url, String postedBy, String mediaType, String title, String description, String timeStamp) {
        this.postId = postId;
        this.media_url = media_url;
        this.postedBy = postedBy;
        this.mediaType = mediaType;
        this.title = title;
        this.description = description;
        this.timeStamp = timeStamp;
    }


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
