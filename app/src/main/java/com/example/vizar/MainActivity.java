package com.example.vizar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void signinpage(View view){

        Intent intent = new Intent(this,Sign_in.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }
    public void signuppage(View view)
    {
        Intent intent = new Intent(this,sign_Up1.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }




}