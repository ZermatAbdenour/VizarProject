package com.example.vizar.Model;

public class product {
    private String name;
    private String price;
    private String description;

    private String publishdate;
    private int imageview;

    public product(String name, String price, String description, int imageview) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageview = imageview;
    }

    public product(String name, String price, String description, String publishdate, int imageview) {
        this.name = name;
        this.price = price;
        this.publishdate = publishdate;
        this.imageview = imageview;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }
}
