package com.example.monprojet;

import android.content.ClipData;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Addproductpage extends AppCompatActivity {
    private ImageView goingback;

    String[] items ={"wardrobes","Sofas","Dressers","Armchairs","Dining table","TV Stands"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;


   @Override
protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.addproductpage);
       autoCompleteTextView=findViewById(R.id.autocompletetxt);
       adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
       autoCompleteTextView.setAdapter(adapterItems);


       autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
               String item = parent.getItemAtPosition(i).toString();
               Toast.makeText(getApplicationContext(),"Item"+item,Toast.LENGTH_SHORT).show();
           }
           });
       this.goingback=(ImageView) findViewById(R.id.goingback);

       goingback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent otheractivity = new Intent(getApplicationContext() , AddNewProductActivity.class);
               startActivity(otheractivity);
               finish();

           }
       });

           }


       }