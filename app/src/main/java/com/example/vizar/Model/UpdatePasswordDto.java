package com.example.vizar.Model;

public class UpdatePasswordDto {
    public String OldPassword;
    public String NewPassword;

    public UpdatePasswordDto(String oldPassword, String newPassword) {
        OldPassword = oldPassword;
        NewPassword = newPassword;
    }
}
