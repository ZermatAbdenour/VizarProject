package com.example.vizar;

import android.app.Application;
import android.content.Intent;

import com.example.vizar.Model.User;

import io.paperdb.Paper;

public class VizarApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //Inisialize Local DataBase
        Paper.init(this);

        User user = Paper.book().read("User",null);

        boolean Authentified = Paper.book().read("Authentified",false);

        if(Authentified && user != null){
            Intent intent = new Intent(this,Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


}
