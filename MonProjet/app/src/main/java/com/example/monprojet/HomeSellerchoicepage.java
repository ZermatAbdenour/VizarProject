package com.example.monprojet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.monprojet.databinding.ActivityHomesellerchoicepageBinding;

public class HomeSellerchoicepage extends AppCompatActivity {


    private CardView addprod;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homesellerchoicepage);
        this.addprod=(CardView) findViewById(R.id.addprod);

        addprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otheractivity = new Intent(getApplicationContext() , AddNewProductActivity.class);
                startActivity(otheractivity);
                finish();

            }
        });




    }






    }


