package com.example.pixabay;

class WallpaperModel {
    String id,imgLink,user;
    String  likes,downloads;

    public WallpaperModel() {

    }

    public WallpaperModel(String id, String  imgLink ,String user,String likes,String downloads) {
        this.id = id;
        this.imgLink = imgLink;
        this.user=user;
        this.likes=likes;
        this.downloads=downloads;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }
}
