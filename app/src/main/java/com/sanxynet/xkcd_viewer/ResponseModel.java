package com.sanxynet.xkcd_viewer;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("title")
    public String comicTitle;

    @SerializedName("img")
    public String imageUrl;

    public ResponseModel(String comicTitle, String imageUrl) {
        this.comicTitle = comicTitle;
        this.imageUrl = imageUrl;
    }

    public String getComicTitle() {
        return comicTitle;
    }

    public void setComicTitle(String comicTitle) {
        this.comicTitle = comicTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
