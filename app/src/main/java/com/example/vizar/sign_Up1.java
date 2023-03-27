package com.example.vizar;

import static com.example.vizar.R.color.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.vizar.Model.CreateUserDto;
import com.example.vizar.Model.User;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sign_Up1 extends AppCompatActivity {
    EditText Name,Password,email;

    View bar1;
    View bar2;
    View bar3;
    View bar4;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APILink apiLink;
    int minlength,digits,lowercase,symobles,upercase,strength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        apiLink = RetrofitClient.getInstance().create(APILink.class);

        Name = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.Email);
        bar1 = (View) findViewById(R.id.bar1);
        bar2 = (View) findViewById(R.id.bar2);
        bar3 = (View) findViewById(R.id.bar3);
        bar4 = (View) findViewById(R.id.bar4);
        minlength=8;
        digits=lowercase=symobles=upercase=0;
        inputchange();
    }
    private void inputchange() {
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                PasswordValdiation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void PasswordValdiation() {
        digits=lowercase=symobles=upercase=0;
            strength =0;
        if(Password.getText().toString().length()>0)
        {
            strength++;
            if(Password.getText().toString().length()>=minlength) {


                for (int i = 0; i < Password.getText().toString().length(); i++) {
                    if (Character.isUpperCase(Password.getText().toString().charAt(i)))
                        upercase += 1;
                    else if (Character.isDigit(Password.getText().toString().charAt(i)))
                        digits += 1;
                    else if(Character.isLowerCase(Password.getText().toString().charAt(i)))
                        lowercase += 1;
                    else
                        symobles += 1;
                }
                if(lowercase>0)
                    strength++;
                if (upercase > 0)
                    strength++;
                if (digits > 0)
                    strength++;
            }
        }else
        {
            strength = 0;
        }











        System.out.println(strength);
        switch (strength){

            case 0 :  bar1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      bar2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      bar3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      bar4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      break;
            case 1 :  bar1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,toweak)));
                      bar2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      bar3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      bar4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      break;
            case 2 :  bar1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,weak)));
                      bar2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,weak)));
                      bar3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      bar4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      break;
            case 3:   bar1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,good)));
                      bar2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,good)));
                      bar3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,good)));
                      bar4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,white)));
                      break;
            case 4:   bar1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,strong)));
                      bar2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,strong)));
                      bar3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,strong)));
                      bar4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,strong)));
                      break;
        }


    }
       public void signinpage(View view){

              Intent intent = new Intent(this,Sign_in.class);
              startActivity(intent);
           overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

          }
    public void homepage(View view){

        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
          public void signuppage(View view)
          {
              Intent intent = new Intent(this,sign_Up1.class);
              startActivity(intent);
              overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
          }
    
       public void register(View view){

            if(InputsValid())
            {


           CreateUserDto createUserDto = new CreateUserDto(Name.getText().toString(),email.getText().toString(),Password.getText().toString());
            /*
            compositeDisposable.add(apiLink.registeruser(createUserDto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            Toast.makeText(sign_Up1.this,s,Toast.LENGTH_SHORT).show();
                            System.out.println(s);
                            homepage(view);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //dialog.dis
                            System.out.println(throwable.toString());
                            Toast.makeText(sign_Up1.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })

            );
            */
                Call<User> registerCall = apiLink.registeruser(createUserDto);
                registerCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(sign_Up1.this,response.message(),Toast.LENGTH_SHORT).show();
                            homepage(view);
                        }else{
                            Toast.makeText(sign_Up1.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
       }}
       private boolean InputsValid(){
                    boolean valid = true;
                    if(Name.getText().toString().length()<8) {
                        valid=false;
                        Toast.makeText(sign_Up1.this, "username too short", Toast.LENGTH_SHORT).show();
                    }
                    if(strength <2) {
                          valid=false;
                          Toast.makeText(sign_Up1.this, "password too short", Toast.LENGTH_SHORT).show();
                    }
                      if(email.getText().toString().length() < 11) {
                          valid=false;
                          Toast.makeText(sign_Up1.this, "Email unvalid", Toast.LENGTH_SHORT).show();
                      }

                    return valid ;

       }
}