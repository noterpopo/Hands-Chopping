package com.popo.module_search.mvp.mvp.model.entity;

public class GameSearchBean {
    Boolean bundle=false;
    String source;
    String id;
    String title;
    String imgUrl;
    String price;
    String sanko_id;

    public Boolean getBundle() {
        return bundle;
    }

    public void setBundle(Boolean bundle) {
        this.bundle = bundle;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSanko_id() {
        return sanko_id;
    }

    public void setSanko_id(String sanko_id) {
        this.sanko_id = sanko_id;
    }
}
