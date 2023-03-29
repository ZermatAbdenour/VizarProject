package com.example.monprojet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewProductActivity extends AppCompatActivity {

    private ImageView button;
    @Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_new_product);
   button =(ImageView) findViewById(R.id.button);

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent otherActivity = new Intent(AddNewProductActivity.this , Addproductpage.class);
            startActivity(otherActivity);

        }
    });




    }
}

