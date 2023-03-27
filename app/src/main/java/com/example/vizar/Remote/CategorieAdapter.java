package com.example.vizar.Remote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizar.Model.CategorieCard;
import com.example.vizar.R;

import java.util.List;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.MyViewHolder2> {


    public List<CategorieCard> Categories;
    Context ctx;

    public CategorieAdapter(List<CategorieCard> categories, Context ctx) {
        Categories = categories;
        this.ctx = ctx;
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



            }


        }
    }

