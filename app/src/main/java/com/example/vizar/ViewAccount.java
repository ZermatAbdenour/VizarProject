package com.example.vizar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vizar.Model.User;
import com.example.vizar.Model.product;

import io.paperdb.Paper;

public class ViewAccount extends AppCompatActivity {

    TextView UserName;
    TextView Email;
    TextView FullName;
    TextView Address;
    TextView Mobile;
    ImageView ProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        //Load User Data
        Intent i = getIntent();
        boolean OverrideUser = (Boolean) i.getSerializableExtra("OverrideUser");

        //Get references
        UserName =  findViewById(R.id.ViewAccountUserName);
        Email = findViewById(R.id.ViewAccountEmail);
        FullName =  findViewById(R.id.ViewAccountFullName);
        Mobile =  findViewById(R.id.ViewAccountMobile);
        Address =  findViewById(R.id.ViewAccountAddress);
        ProfileImage = findViewById(R.id.ViewAccountImage);



        if(!OverrideUser){
            //Get User from paper
            User user = Paper.book().read("User",null);
            //Set data from user
            //Set Values
            UserName.setText(user.userName);
            Email.setText(user.email);
            FullName.setText(user.fullName);
            Mobile.setText(user.mobile);
            Address.setText(user.adresse);
            Glide.with(this).load("http://abdenourzermat-001-site1.htempurl.com/images/" + user.imageID).into(ProfileImage);
        }
        else{
            //Get User from Serelizable extra

        }
    }
}