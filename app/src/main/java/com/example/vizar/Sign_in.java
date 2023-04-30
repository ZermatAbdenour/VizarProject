package com.example.vizar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vizar.Model.LoginDto;
import com.example.vizar.Model.User;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;

import io.paperdb.Paper;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_in extends AppCompatActivity {
    EditText Password,email;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APILink apiLink;
    CustomSnackbar snackbar;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        snackbar = new CustomSnackbar(this);

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
            LoadingDialog signinLoading = new LoadingDialog(this);


            signinLoading.show();

            LoginDto loginDto = new LoginDto(email.getText().toString(),Password.getText().toString());

            Call<User> call = apiLink.loginuser(loginDto);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        Paper.book().write("Authentified",true);
                        Paper.book().write("User",response.body());
                        homepage(view);
                    }
                    else {
                        if(response.code() == 404)
                                snackbar.Show("Email not found");
                        if(response.code() == 401)
                            snackbar.Show("Password is wrong");

                        System.out.println(response.code());

                    }
                    signinLoading.dismiss();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    signinLoading.dismiss();
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