package com.droiddigger.rssfeedparsing;

/**
 * Created by mufad on 10/28/2016.
 */

public class NewsItems {

    private String title;
    private String description;
    private String url;
    private String media;

    public NewsItems(String title, String description, String url, String media) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.media = media;
    }

    public NewsItems() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "Title: " +title +"\nURL: "+url+"\nDescription: "+description+"\nMedia: "+media;
    }
}
