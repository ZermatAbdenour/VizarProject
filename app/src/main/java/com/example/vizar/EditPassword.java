package com.example.vizar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        TextInputEditText PasswordInputText = (TextInputEditText) findViewById(R.id.Password);
        PasswordInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckSimilarity();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TextInputEditText RepeatPasswordInputText = (TextInputEditText) findViewById(R.id.RepeatPassword);
        RepeatPasswordInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CheckSimilarity();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void CheckSimilarity(){
        TextInputEditText PasswordInputText = (TextInputEditText) findViewById(R.id.Password);
        TextInputEditText RepeatPasswordInputText = (TextInputEditText) findViewById(R.id.RepeatPassword);
        TextInputLayout RepeatPasswordLayout = (TextInputLayout)findViewById(R.id.RepeatPasswordLayout);
        if(!PasswordInputText.getText().toString().matches(RepeatPasswordInputText.getText().toString())){
            RepeatPasswordLayout.setErrorEnabled(true);
            RepeatPasswordLayout.setError("Not match");
        }
        else
            RepeatPasswordLayout.setErrorEnabled(false);
    }
}