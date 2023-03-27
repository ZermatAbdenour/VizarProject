package com.example.vizar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizar.Model.CategorieCard;
import com.example.vizar.Model.Horizantalrecyclerview;
import com.example.vizar.Remote.CategorieAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter_1 extends RecyclerView.Adapter<Adapter_1.MyViewHolder>{
    private ArrayList<Horizantalrecyclerview> parentModelArrayList;
    public Context cxt;

    public Adapter_1(ArrayList<Horizantalrecyclerview> parentModelArrayList, Context cxt) {
        this.parentModelArrayList = parentModelArrayList;
        this.cxt = cxt;

    }

    @NonNull
    @Override
    public Adapter_1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_recyclerview_items, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_1.MyViewHolder holder, int position) {
        Horizantalrecyclerview currentItem = parentModelArrayList.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false);
        holder.childRecyclerView.setLayoutManager(layoutManager);

        holder.Title.setText(currentItem.getTitle());
        List<CategorieCard> List = new ArrayList<>();


            List.add(new CategorieCard(R.drawable.cat_cat1,"Wardrobes"));
            List.add(new CategorieCard(R.drawable.cat_armchairs,"Armchairs"));
            List.add(new CategorieCard( R.drawable.cat_diningtable,"DiningTable"));
            List.add(new CategorieCard( R.drawable.cat_dressers,"Dressers"));
            List.add(new CategorieCard( R.drawable.cat_sofas,"Sofas"));
            List.add(new CategorieCard( R.drawable.cat_tvstands,"TV Stands"));

            CategorieAdapter childRecyclerViewAdapter = new CategorieAdapter(List,holder.childRecyclerView.getContext());
            holder.childRecyclerView.setAdapter(childRecyclerViewAdapter);


    }
    @Override
    public int getItemCount() {
        if (parentModelArrayList != null)
            return parentModelArrayList.size();
        else
            return 0;


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public RecyclerView childRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.Title);
            childRecyclerView = itemView.findViewById(R.id.Child_RV);
        }
    }
}
