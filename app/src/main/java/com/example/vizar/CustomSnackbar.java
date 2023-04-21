package com.example.vizar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.vizar.Model.User;
import com.example.vizar.R;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Callback;

public class CustomSnackbar {
    Snackbar snackbar;

    public CustomSnackbar(Activity activity){
        snackbar = Snackbar
                .make(activity.findViewById(R.id.HomeSnackBar),"", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.BLACK);

    }

    public void Show(String text){
        snackbar.setText(text);
        snackbar.show();
    }
    public void Dismiss(){
        snackbar.dismiss();
    }
}
