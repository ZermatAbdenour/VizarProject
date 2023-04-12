package com.example.vizar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vizar.Model.CreateUserDto;
import com.example.vizar.Model.LoginDto;
import com.example.vizar.Model.User;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;

import java.io.Console;

import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_in extends AppCompatActivity {
    EditText Password,email;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APILink apiLink;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        apiLink = RetrofitClient.getInstance().create(APILink.class);

        Password = (EditText)findViewById(R.id.password1);
        email = (EditText)findViewById(R.id.Email1);

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


    public void signin(View view){


        if(InputsValid())
        {

            LoginDto loginDto = new LoginDto(email.getText().toString(),Password.getText().toString());
            /*
            compositeDisposable.add(apiLink.loginuser(loginDto)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            Toast.makeText(Sign_in.this,s,Toast.LENGTH_SHORT).show();
                            homepage(view);
                            System.out.println(s);

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //dialog.dis
                            System.out.println(throwable.toString());
                            Toast.makeText(Sign_in.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
            );
            */

            Call<User> call = apiLink.loginuser(loginDto);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(Sign_in.this,response.body().id,Toast.LENGTH_SHORT).show();
                        Paper.book().write("Authentified",true);
                        Paper.book().write("User",response.body());
                        homepage(view);
                    }
                    else {
                        System.out.println("Not successfull");
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        }
    }

    private boolean InputsValid(){
        boolean valid = true;

        if(Password.getText().toString().length() < 8) {
            valid=false;
            Toast.makeText(Sign_in.this, "password too short", Toast.LENGTH_SHORT).show();
        }
        if(email.getText().toString().length() < 11) {
            valid=false;
            Toast.makeText(Sign_in.this, "Email unvalid", Toast.LENGTH_SHORT).show();
        }

        return valid ;

    }
}