package com.example.vizar.Model;

public class CreateUserDto {
    private String Name;
    private String Email;
    private String Password;


    public CreateUserDto(String name, String email, String password) {
        Name = name;
        Email = email;
        Password = password;
    }


}
