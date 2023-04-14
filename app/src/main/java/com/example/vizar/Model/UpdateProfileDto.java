package com.example.vizar.Model;

public class UpdateProfileDto {
    public String UserName;
    public String FullName;
    public String Mobile;
    public String Adresse;

    public UpdateProfileDto(String userName, String fullName, String mobile, String adresse) {
        UserName = userName;
        FullName = fullName;
        Mobile = mobile;
        Adresse = adresse;
    }
}
