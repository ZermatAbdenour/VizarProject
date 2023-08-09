package com.example.vizar;


import static com.example.vizar.R.color.Grey;
import static com.example.vizar.R.color.white;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vizar.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.paperdb.Paper;

public class Home extends AppCompatActivity {
    private RecyclerView parentRecyclerView;
    private RecyclerView.Adapter ParentAdapter;
    ArrayList<Horizantalrecyclerview> parentModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager parentLayoutManager;
    static ImageButton home,saved,settings,search;

    public  com.example.vizar.Model.product product;

    static Fragment LatestFragment;

         ViewFlipper headercontainer;
    private searchFragment searchFragment;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home = findViewById(R.id.homebutton);
        saved= findViewById(R.id.savedbutton);
        settings= findViewById(R.id.settingbutton);
        search= findViewById(R.id.searchbutton);
       // headercontainer = findViewById(R.id.Header_container);

        // flipper anim

      /*  headercontainer.setInAnimation(AnimationUtils.loadAnimation(
                Home.this, R.anim.upslide));
        headercontainer.setOutAnimation(AnimationUtils.loadAnimation(
                Home.this, R.anim.downslide));*/
        FloatingActionButton ArButton = findViewById(R.id.ARButton);
        ArButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenUnity(view,"fdsfsfsdfds");

            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        if((boolean)Paper.book().read("IsSeller",false))
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView3,HomeMainSellerMode.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                if(!(boolean)Paper.book().read("IsSeller",false))
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView3,homemain.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
                else
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView3,HomeMainSellerMode.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();



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

            }
        });

        //Update the Top Profile Image
        ImageView UserImage = findViewById(R.id.UserTopImage);
        User user = (User) Paper.book().read("User");
        Glide.with(this).load("http://zermatabdenour-001-site1.atempurl.com/images/" + user.imageID).into(UserImage);
        UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //load view account activity
                Intent i = new Intent(view.getContext(), ViewAccount.class);
                i.putExtra("OverrideUser", false);
                view.getContext().startActivity(i);
            }
        });
    }

    public static void ButtomNavHome(Fragment fragment){

        home.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),white)));
        settings.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        search.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        saved.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        LatestFragment = fragment;
    }
    public static void ButtomNavSearch(Fragment fragment){

        home.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        settings.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        search.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),white)));
        saved.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        LatestFragment = fragment;
    }
    public static void ButtomNavSaved(Fragment fragment){

        home.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        settings.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        search.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        saved.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),white)));
        LatestFragment = fragment;
    }
    public static void ButtomNavSettings(Fragment fragment){

        settings.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),white)));
        home.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        search.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        saved.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getActivity(),Grey)));
        LatestFragment = fragment;
    }
    public void searchfragment()
    {


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, com.example.vizar.searchFragment.class,null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

    }
    public void editproductfragment()
    {


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, com.example.vizar.editproduct.class,null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();




    }

    public void OpenUnity(View view,String ProductID){

    }
}