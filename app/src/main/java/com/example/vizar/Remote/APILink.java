package com.example.vizar.Remote;

import android.icu.text.CaseMap;

import com.example.vizar.Model.CreateUserDto;
import com.example.vizar.Model.GUIDDto;
import com.example.vizar.Model.LoginDto;
import com.example.vizar.Model.SellerDto;
import com.example.vizar.Model.User;
import com.example.vizar.Model.product;
import com.example.vizar.Model.productDto;

import java.lang.reflect.Array;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APILink {

    //User Functions
    @POST("users/register")
    public Call<User> registeruser(@Body CreateUserDto user);

    @POST("users/login")
    public Call<User> loginuser(@Body LoginDto user);


    //Products Functions

    //add new Product
    @Multipart
    @POST("images")
    public Call<GUIDDto> uploadimage(@Part MultipartBody.Part file);
    @Multipart
    @POST("models")
    public Call<GUIDDto> uploadmodel(@Part MultipartBody.Part file);

    @POST("products")
    public Call<product> uploadproduct(@Body productDto product);

    //Home
    @GET("products/scroll")
    public Call<List<product>> getproducts(@Query("Offset") int offset,@Query("productsCount") int productsCount);

    @GET("users/seller/{id}")
    public Call<SellerDto> getsellerbyid(@Path("id")String id );

}
