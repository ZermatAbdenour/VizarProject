package com.example.vizar.Remote;

import com.example.vizar.Model.CreateUserDto;
import com.example.vizar.Model.GUIDDto;
import com.example.vizar.Model.LoginDto;
import com.example.vizar.Model.SellerDto;
import com.example.vizar.Model.UpdatePasswordDto;
import com.example.vizar.Model.UpdateProfileDto;
import com.example.vizar.Model.User;
import com.example.vizar.Model.product;
import com.example.vizar.Model.productDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APILink {

    //User Functions
    @POST("users/register")
    public Call<User> registeruser(@Body CreateUserDto user);

    @POST("users/login")
    public Call<User> loginuser(@Body LoginDto user);

    @Multipart
    @PUT("users/update/image/{id}")
    public Call<User> setProfileImage(@Path("id")String id,@Part MultipartBody.Part file);

    @PUT("users/update/profile/{id}")
    public Call<User> UpdateProfile(@Path("id")String id, @Body UpdateProfileDto updateDto);

    @PUT("users/update/password/{id}")
    public Call<User> UpdatePassword(@Path("id") String id, @Body UpdatePasswordDto updateDto);

    //Products Functions

    //add new Product
    /*
    @Multipart
    @POST("images")
    public Call<GUIDDto> uploadimage(@Part MultipartBody.Part file);
    @Multipart
    @POST("models")
    public Call<GUIDDto> uploadmodel(@Part MultipartBody.Part file);

    @POST("products")
    public Call<product> uploadproduct(@Body productDto product);
    */
    @Multipart
    @POST("products")
    Call<Void> uploadProduct(
            @Part MultipartBody.Part imageFile,
            @Part MultipartBody.Part modelFile,
            @Part("Name") RequestBody name,
            @Part("Price") RequestBody price,
            @Part("Description") RequestBody description,
            @Part("Categorie") RequestBody category,
            @Part("SellerID") RequestBody sellerID,
            @Part("SellerName") RequestBody sellerName,
            @Part("WebLink") RequestBody webLink,
            @Part("Width") RequestBody width,
            @Part("Height") RequestBody height,
            @Part("Depth") RequestBody depth,
            @Part("Weight") RequestBody weight,
            @Part("Volume") RequestBody volume,
            @Part("ModelExtension") RequestBody modelExtension
    );


    //Home
    @GET("products/scroll")
    public Call<List<product>> getproducts(@Query("Offset") int offset,@Query("productsCount") int productsCount);

    @GET("users/seller/{id}")
    public Call<SellerDto> getsellerbyid(@Path("id")String id );

    @GET("users/savedproducts/{id}")
    public Call<List<product>> getsavedproducts(@Path("id")String id);

    @GET("users/userproducts/{id}")
    public Call<List<product>> getuserproducts(@Path("id")String id);

    @DELETE("products/{id}")
    public Call<Void> deleteproduct(@Path("id")String id);


    @GET("users/savedproducts/stat/{id}")
    public Call<Boolean> getproductsavestate(@Path("id")String id, @Query("productid")String productid);

    @POST("users/savedproducts/stat/{id}")
    public Call<Void> setproductsavestate(@Path("id")String id,@Query("productid")String productid,@Query("savestat")Boolean savestat);


    @GET("products/search/autocomplete")
    public Call<List<product>> autocomplete(@Query("query")String text);

    @POST("products/search/FullSearch")
    public Call<List<product>> Search(@Query("query")String text,@Query("Offset")int offset,@Query("productscount")int productscount,@Query("categories")String categories);
}
