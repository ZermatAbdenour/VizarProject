package com.example.vizar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizar.Model.product;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private List<product> productsList;

    public Adapter(List<product> productsList){this .productsList = productsList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource = productsList.get(position).getImageview();
        String name = productsList.get(position).getName();
        String price = productsList.get(position).getPrice();
        String description = productsList.get(position).getDescription();

        holder.setData(resource,name,price,description);
    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private ImageView imageview;
        private TextView textname;
        private TextView textprice;
        private TextView textdiscription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.productimage);
            textname = itemView.findViewById(R.id.productname);
            textprice = itemView.findViewById(R.id.price);
            textdiscription = itemView.findViewById(R.id.description);



        }

        public void setData(int resource, String name, String price, String description) {
            imageview.setImageResource(resource);
            textname.setText(name);
            textprice.setText(price);
            textdiscription.setText(description);

        }
    }
}
