package com.example.vizar.Model;

import android.graphics.Bitmap;

public class CategorieCard {

    private int image;
    private String name;

    public CategorieCard(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
