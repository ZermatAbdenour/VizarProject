package com.example.vizar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.vizar.Model.SellerDto;
import com.example.vizar.Model.product;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //load Image From database


        holder.setData(productsList.get(position));
    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private ImageView imageview;
        private TextView textname;
        private TextView textprice;
        private TextView textsellername;
        private View ProductView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.productimage);
            textname = itemView.findViewById(R.id.productname);
            textprice = itemView.findViewById(R.id.price);
            textsellername = itemView.findViewById(R.id.description);

            ProductView = itemView;

        }

        public void setData(product newproduct) {
            textname.setText(newproduct.name);
            textprice.setText(String.valueOf(newproduct.price));
            textsellername.setText(newproduct.sellerName);

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .dontTransform()
                    .priority(Priority.IMMEDIATE);

            RequestBuilder<Drawable> requestBuilder= Glide.with(ProductView)
                    .asDrawable().sizeMultiplier(0.05f);

            Glide.with(ProductView).load("http://abdenourzermat-001-site1.htempurl.com/images/" + newproduct.imageid).error(Glide.with(ProductView).load("http://abdenourzermat-001-site1.htempurl.com/images/" + newproduct.imageid).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageview)).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageview);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //load view product activity
                    Intent i = new Intent(view.getContext(), ProductView.class);
                    i.putExtra("Product", newproduct);
                    view.getContext().startActivity(i);


                }
            });
        }
    }
}
