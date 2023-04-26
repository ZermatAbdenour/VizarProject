package com.example.vizar.Remote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizar.CategorieCard;
import com.example.vizar.Home;
import com.example.vizar.R;

import java.util.List;

import io.paperdb.Paper;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.MyViewHolder2> {


    public List<CategorieCard> Categories;
    Context ctx;
    Activity home;

    public CategorieAdapter(List<CategorieCard> categories, Context ctx, Activity home) {
        Categories = categories;
        this.ctx = ctx;
        this.home = home;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_recyclerview_items, parent, false);
       MyViewHolder2 viewHolder2 = new MyViewHolder2(view);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        CategorieCard currentItem = Categories.get(position);
        holder.Categorieimage.setImageResource(currentItem.getImage());
        holder.Categoriename.setText(currentItem.getName());

    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {


            private ImageView Categorieimage;
            private TextView Categoriename;
            public MyViewHolder2(@NonNull View itemView) {
                super(itemView);
                Categorieimage = itemView.findViewById(R.id.Categorie_Image);
                Categoriename = itemView.findViewById(R.id.Category_Name);
                itemView.findViewById(R.id.Categorie_Card).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        ((Home)home).searchfragment();
                        Paper.book().write("category",Categoriename.getText().toString());

                    }
                });


            }


        }
    }

