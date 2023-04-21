package com.example.vizar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.vizar.R;

public class LoadingDialog extends Dialog {

    private Activity CurrentActivity;
    private AlertDialog Dialog;


    public LoadingDialog(@NonNull Context context){
        super(context);
        WindowManager.LayoutParams parms = new WindowManager.LayoutParams();
        parms.gravity = Gravity.CENTER;
        getWindow().setAttributes(parms);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        setOnCancelListener(null);

        View view = LayoutInflater.from(context).inflate(R.layout.loadingdialog,null);
        setContentView(view);
    }
}
