package com.example.vizar.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class product implements Serializable {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("publishDate")
    @Expose
    public String publishDate;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("price")
    @Expose
    public float price;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("categorie")
    @Expose
    public String categorie;
    @SerializedName("sellerName")
    @Expose
    public String sellerName;
    @SerializedName("sellerID")
    @Expose
    public String sellerid;
    @SerializedName("webLink")
    @Expose
    public String weblink;
    @SerializedName("width")
    @Expose
    public float width;
    @SerializedName("height")
    @Expose
    public float height;
    @SerializedName("depth")
    @Expose
    public float depth;
    @SerializedName("weight")
    @Expose
    public float weight;
    @SerializedName("volume")
    @Expose
    public float volume;
    @SerializedName("imageID")
    @Expose
    public String imageid;
    @SerializedName("modelID")
    @Expose
    public String modelid;
    @SerializedName("modelExtension")
    @Expose
    public String modelExtension;

    public product(String name, float price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }


    public String getPublishdate() {
        return publishDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
