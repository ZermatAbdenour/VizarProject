package com.example.vizar.Model;

public class productDto {
    public String Name;
    public float Price;
    public String Description;
    public String Categorie;
    public String SellerName;
    public String SellerID;
    public String WebLink;
    public float Width;
    public float Height;
    public float Depth;
    public float Weight;
    public float Volume;
    public String ModelExtension;


    public productDto(String name, float price, String description, String categorie,String sellerID,String sellerName, String webLink, float width, float height, float depth, float weight, float volume,String modelExtension) {
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
        ModelExtension = modelExtension;
    }
}
