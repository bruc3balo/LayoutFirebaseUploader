package com.example.videoimageupload.models;

import java.util.LinkedList;

public class Tags {
    private LinkedList<String> tags;

    public Tags(LinkedList<String> tags) {
        this.tags = tags;
    }

    public LinkedList<String> getTags() {
        return tags;
    }

    public void setTags(LinkedList<String> tags) {
        this.tags = tags;
    }

    public void addTags (String tag) {
        tags.add(tag);
    }
}
