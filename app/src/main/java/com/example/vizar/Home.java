package com.example.vizar;



import static com.example.vizar.R.color.Grey;
import static com.example.vizar.R.color.white;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

import com.example.vizar.Model.Horizantalrecyclerview;
import com.example.vizar.Model.product;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private RecyclerView parentRecyclerView;
    private RecyclerView.Adapter ParentAdapter;
    ArrayList<Horizantalrecyclerview> parentModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager parentLayoutManager;
    ImageButton home,saved,settings,search;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        home = findViewById(R.id.homebutton);
        saved= findViewById(R.id.savedbutton);
        settings= findViewById(R.id.settingbutton);
        search= findViewById(R.id.searchbutton);




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                FragmentManager fragmentManager = getSupportFragmentManager();
                if(!prefs.getBoolean("IsSeller", false))
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView3,homemain.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView3,HomeMainSellerMode.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();

                home.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),white)));
                settings.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                search.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                saved.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3,settingsFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                settings.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),white)));
                home.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                search.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                saved.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));


            }
        });
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3,savedFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                home.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                settings.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                search.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                saved.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),white)));

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3,searchFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                home.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                settings.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));
                search.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),white)));
                saved.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getBaseContext(),Grey)));

            }
        });

    }




}