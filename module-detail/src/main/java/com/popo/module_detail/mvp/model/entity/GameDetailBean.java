package com.popo.module_detail.mvp.model.entity;

import java.util.ArrayList;

public class GameDetailBean {
    String price=null;
    String title=null;
    String id=null;
    ArrayList<String> bannnerImages=new ArrayList<>();
    String img=null;
    String gameInfo=null;
    String gameDescription=null;
    String syaRequest=null;
    ArrayList<String> reviews=new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getBannnerImages() {
        return bannnerImages;
    }

    public void setBannnerImages(ArrayList<String> bannnerImages) {
        this.bannnerImages = bannnerImages;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(String gameInfo) {
        this.gameInfo = gameInfo;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getSyaRequest() {
        return syaRequest;
    }

    public void setSyaRequest(String syaRequest) {
        this.syaRequest = syaRequest;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
