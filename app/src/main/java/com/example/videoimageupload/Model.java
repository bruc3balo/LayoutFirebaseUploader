package com.example.videoimageupload;

public class Model {
    private String image_urk;
    private String title;
    private String description;

    public Model(String image_urk, String title, String description) {
        this.image_urk = image_urk;
        this.title = title;
        this.description = description;
    }

    public String getImage_urk() {
        return image_urk;
    }

    public void setImage_urk(String image_urk) {
        this.image_urk = image_urk;
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
}
