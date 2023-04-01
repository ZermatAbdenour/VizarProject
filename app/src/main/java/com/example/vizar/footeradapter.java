package com.example.vizar;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;

public class footeradapter extends RecyclerView.Adapter<footeradapter.ViewHolder>{

    int Footerlayout;

    public footeradapter(int footerlayout) {
        Footerlayout = footerlayout;
    }

    @NonNull
    @Override
    public footeradapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(Footerlayout,parent,false);
        return new footeradapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{


        public ViewHolder(@NonNull View itemView) {
            super(itemView);




        }
    }
}
