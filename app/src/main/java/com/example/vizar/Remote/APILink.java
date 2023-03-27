package com.example.vizar.Remote;

import com.example.vizar.Model.CreateUserDto;
import com.example.vizar.Model.LoginDto;
import com.example.vizar.Model.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APILink {


    @POST("users/register")
    public Call<User> registeruser(@Body CreateUserDto user);

    @POST("users/login")
    public Call<User> loginuser(@Body LoginDto user);

}
