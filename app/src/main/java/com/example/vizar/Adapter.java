package com.example.vizar;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.vizar.Model.product;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private List<product> productsList;
    private int productlayout;
    private boolean showdate,edit;
    private APILink apiLink;
    private Activity Home;

    public Adapter(List<product> productsList, int productlayout, boolean showdate, boolean edit, Activity Home){this .productsList = productsList;this.productlayout = productlayout; this.showdate = showdate;this.edit=edit;this.Home=Home; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(productlayout,parent,false);

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

        private View deletebutton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.productimage);
            textname = itemView.findViewById(R.id.productname);
            textprice = itemView.findViewById(R.id.price);
            textsellername = itemView.findViewById(R.id.description);
            if(productlayout==R.layout.deleteproduct)
                    deletebutton = itemView.findViewById(R.id.DeleteButton);


            ProductView = itemView;
            apiLink = RetrofitClient.getInstance().create(APILink.class);

        }

        public void setData(product newproduct) {
            textname.setText(newproduct.name);

            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
            format.setCurrency(Currency.getInstance("usd"));
            format.setMinimumFractionDigits(0);
            String result = format.format(newproduct.price);
            textprice.setText(result);

            if(!showdate)
                textsellername.setText(newproduct.sellerName);
            else
                textsellername.setText("published since : " + newproduct.publishDate);

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .dontTransform()
                    .priority(Priority.IMMEDIATE);


            Glide.with(ProductView).load("http://zermatabdenour-001-site1.atempurl.com/images/" + newproduct.imageid).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageview);



                //product page
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

if(!edit)
{
                    //load view product activity
                    Intent i = new Intent(view.getContext(), ProductView.class);
                    i.putExtra("Product", newproduct);
                    view.getContext().startActivity(i);
}else {
    //animation navbar
    ObjectAnimator animation = ObjectAnimator.ofFloat(Home.findViewById(R.id.Navbar), "translationY", 500f);
    animation.setDuration(500);
    animation.start();
    //editpage
    ((Home)Home).product = newproduct;
    ((Home)Home).editproductfragment();

}


                }
            });

             //delete


            if(deletebutton!=null)
                deletebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Call<Void> deleteproduct = apiLink.deleteproduct(newproduct.id);
                            deleteproduct.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    productsList.remove(newproduct);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });




                    }
                });
        }
    }
}
