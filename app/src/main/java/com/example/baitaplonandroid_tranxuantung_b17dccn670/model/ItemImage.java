package com.example.baitaplonandroid_tranxuantung_b17dccn670.model;

import java.io.Serializable;

public class ItemImage implements Serializable {

    public static final long serialVersionUID = 2L;

    private long id;

    private String url;

    public ItemImage(long id, String url) {
        this.id = id;
        this.url = url;
    }

    public ItemImage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ItemImage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
