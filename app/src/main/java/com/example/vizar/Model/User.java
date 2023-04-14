package com.example.vizar.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("fullName")
    @Expose
    public String fullName;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("adresse")
    @Expose
    public String adresse;
    @SerializedName("userProducts")
    @Expose
    public String[] userProducts;
    @SerializedName("savedProducts")
    @Expose
    public String[] savedProducts;
    @SerializedName("imageID")
    @Expose
    public String imageID;
}
