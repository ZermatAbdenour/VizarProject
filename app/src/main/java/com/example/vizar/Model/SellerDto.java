package com.example.vizar.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SellerDto implements Serializable {

    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("fullName")
    @Expose
    public String fullName;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("adresse")
    @Expose
    public String adresse;

}
