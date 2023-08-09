package com.example.vizar;

import static com.example.vizar.R.color.good;
import static com.example.vizar.R.color.strong;
import static com.example.vizar.R.color.toweak;
import static com.example.vizar.R.color.weak;
import static com.example.vizar.R.color.white;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.vizar.Model.CreateUserDto;
import com.example.vizar.Model.User;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.paperdb.Paper;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sign_Up1 extends AppCompatActivity {
    TextInputEditText Name,Password,email;

    View bar1;
    View bar2;
    View bar3;
    View bar4;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APILink apiLink;
    int minlength,digits,lowercase,symobles,upercase,strength;
    private CustomSnackbar snackbar;
    private TextInputLayout  NameLayout;
    private TextInputLayout  passwordLayout;
    private TextInputLayout  emailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        snackbar = new CustomSnackbar(this);

        apiLink = RetrofitClient.getInstance().create(APILink.class);

        Name = (TextInputEditText)findViewById(R.id.username);
        Password = (TextInputEditText)findViewById(R.id.password);
        email = (TextInputEditText)findViewById(R.id.email);

        NameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailLayout = findViewById(R.id.emailLayout);




        bar1 = (View) findViewById(R.id.bar1);
        bar2 = (View) findViewById(R.id.bar2);
        bar3 = (View) findViewById(R.id.bar3);
        bar4 = (View) findViewById(R.id.bar4);
        minlength=8;
        digits=lowercase=symobles=upercase=0;
        inputchange();
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            //chekname();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                               // checkemail();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void chekname() {
        if(Name.getText().toString().length()<8) {
            NameLayout.setErrorTextAppearance(R.style.username);
            NameLayout.setErrorEnabled(true);
            NameLayout.setError("too short");
        }else {

            NameLayout.setErrorEnabled(false);
        }
    }

    private void checkemail() {
        emailvalidatoor validator = new emailvalidatoor();
        if (!validator.validate(email.getText().toString())) {
            emailLayout.setErrorTextAppearance(R.style.username);
            emailLayout.setErrorEnabled(true);
            email.setError("password too weak ");
        }else {

            emailLayout.setErrorEnabled(false);

        }
    }

    private void inputchange() {
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                PasswordValdiation();
                //checkpassword();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void checkpassword() {
        if(strength <2) {
            passwordLayout.setErrorTextAppearance(R.style.username);
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("password too weak");
        }else {
            passwordLayout.setErrorEnabled(false);

        }
    }

    @SuppressLint("ResourceAsColor")
    private void PasswordValdiation() {
        digits=lowercase=symobles=upercase=0;
            strength =0;
        if(Password.getText().toString().length()>0)
        {
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
                if(lowercase>0&&Password.length()>=8)
                    strength++;
                if (upercase > 0&&Password.length()>=8)
                    strength++;
                if (digits > 0&&Password.length()>=8)
                    strength++;
                if (symobles > 0&&Password.length()>=8)
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
                LoadingDialog signupLoading = new LoadingDialog(this);

                signupLoading.show();
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
                            Paper.book().write("User",response.body());
                            //homepage(view);
                            snackbar.Show("Verify your email");



                        }else{
                            if(response.code()==401)
                            {
                                snackbar.Show("Email Or Username is already Used");
                            }
                            else {
                                snackbar.Show("Error try again");

                            }

                        }

                        signupLoading.hide();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        signupLoading.hide();

                    }
                });
       }}
       private boolean InputsValid(){
        boolean valid = true;
        emailvalidatoor validator = new emailvalidatoor();
        if(Name.getText().toString().length()<6) {
            valid=false;

            snackbar.Show("Username too short");

        }
        if(strength <2) {
            valid=false;

            snackbar.Show("password too weak");

        }
        if (!validator.validate(email.getText().toString())) {
               valid = false;

            snackbar.Show("Email unvalid");

        }

           return valid ;

       }
}