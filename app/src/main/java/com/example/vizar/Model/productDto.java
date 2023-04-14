package com.example.vizar.Model;

public class productDto {
    public String Name;
    public float Price;
    public String Description;
    public String Categorie;
    public String SellerName;
    public String SellerID;
    public String SellerName;
    public String WebLink;

    public productDto(String name, float price, String description, String categorie,String sellerID,String sellerName, String webLink, float width, float height, float depth, float weight, float volume, String imageID, String modelID) {
        Name = name;
        Price = price;
        Description = description;
        Categorie = categorie;
        SellerID = sellerID;
        SellerName = sellerName;
        WebLink = webLink;
        Width = width;
        Height = height;
        Depth = depth;
        Weight = weight;
        Volume = volume;
        ImageID = imageID;
        ModelID = modelID;
    }

    public float Width;
    public float Height;
    public float Depth;
    public float Weight;
    public float Volume;
    public String ImageID;
    public String ModelID;
}
