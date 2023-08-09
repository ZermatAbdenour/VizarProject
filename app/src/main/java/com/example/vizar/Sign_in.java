package com.example.vizar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vizar.Model.LoginDto;
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

public class Sign_in extends AppCompatActivity {
    TextInputEditText Password,email;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    APILink apiLink;
    CustomSnackbar snackbar;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private TextInputLayout passwordLayout;
    private TextInputLayout emailLayout;
    private CheckBox remembreme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        snackbar = new CustomSnackbar(this);
        apiLink = RetrofitClient.getInstance().create(APILink.class);

        Password = (TextInputEditText)findViewById(R.id.password1);
        email = (TextInputEditText)findViewById(R.id.Email1);

        passwordLayout = (TextInputLayout)findViewById(R.id.passwordLayout);
        emailLayout = (TextInputLayout)findViewById(R.id.emailLayout);

        remembreme = findViewById(R.id.checkBoxremembreme);

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
                    System.out.println("ffdsfdddddddddddddddddddddddddddd");
                    if(response.isSuccessful()){
                        if(remembreme.isChecked())
                        Paper.book().write("Authentified",true);
                        else
                        Paper.book().write("Authentified",false);

                        Paper.book().write("User",response.body());
                        homepage(view);
                    }
                    else {
                        if(response.code() == 404)
                                snackbar.Show("Email not found");
                        if(response.code() == 401)
                            snackbar.Show("Password is wrong");
                        if(response.code()==402)
                            snackbar.Show("Please Verify your Email");

                        System.out.println(response.code());

                    }
                    signinLoading.dismiss();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    signinLoading.dismiss();
                    System.out.println("ffdsfdddddddddddddddddddddddddddd : " +t);
                }
            });
        }
    }

    private boolean InputsValid(){
        boolean valid = true;
        emailvalidatoor validator = new emailvalidatoor();
        if(Password.getText().toString().length() < 8) {
            valid=false;
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("wrongpassword");
        }


        if (!validator.validate(email.getText().toString())) {
            valid = false;
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("email unvalid");
        }



        return valid ;

    }
}