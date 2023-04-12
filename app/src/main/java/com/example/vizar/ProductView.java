package com.example.vizar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.vizar.Model.SellerDto;
import com.example.vizar.Model.product;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductView extends AppCompatActivity {
    APILink apiLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiLink = RetrofitClient.getInstance().create(APILink.class);
        setContentView(R.layout.activity_product_view);

        ToggleButton ReadMoreButton = (ToggleButton) findViewById(R.id.ReadMoreButton);
        TextView DescriptionText = (TextView) findViewById(R.id.ProductViewDescription);


        DescriptionText.setMaxLines(2);//Inisialize with read more
        ReadMoreButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    DescriptionText.setMaxLines(100);
                else
                    DescriptionText.setMaxLines(2);

            }
        });


        //Load Product Data
        Intent i = getIntent();
        product Product = (product)i.getSerializableExtra("Product");

        //Get Seller Data

        final SellerDto[] sellerdto = {new SellerDto()};

        Call<SellerDto> seller = apiLink.getsellerbyid(Product.sellerid);
        seller.enqueue(new Callback<SellerDto>() {
            @Override
            public void onResponse(Call<SellerDto> call, Response<SellerDto> response) {
                sellerdto[0] = response.body();
            }

            @Override
            public void onFailure(Call<SellerDto> call, Throwable t) {
                System.out.println(t);
            }
        });



        //image
        ImageView Image = (ImageView) findViewById(R.id.ProductViewImage);
        Glide.with(this).load("http://abdenourzermat-001-site1.htempurl.com/images/" + Product.imageid).transition(DrawableTransitionOptions.withCrossFade()).into(Image);

        //name
        TextView Name = (TextView) findViewById(R.id.ProductViewName);
        Name.setText(Product.name);
        //sellernqme

        TextView Sellername = (TextView)findViewById(R.id.SellerName) ;
        Sellername.setText(Product.sellerName);
        //Description
        TextView Description = (TextView) findViewById(R.id.ProductViewDescription);
        Description.setText(Product.description);

        //Sales Page
        RelativeLayout WebSite = (RelativeLayout)findViewById(R.id.ProductViewWebSite);

        WebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(Product.weblink); // missing 'http://' will cause crashed
                //System.out.println(Product.weblink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //Set data
        TextView Height = (TextView) findViewById(R.id.ProductViewHeight);
        Height.setText(Float.toString(Product.height));
        TextView Depth = (TextView) findViewById(R.id.ProductViewDepth);
        Depth.setText(Float.toString(Product.depth));
        TextView Volume = (TextView) findViewById(R.id.ProductViewVolume);
        Volume.setText(Float.toString(Product.volume));
        TextView Weight = (TextView) findViewById(R.id.ProductViewWeight);
        Weight.setText(Float.toString(Product.weight));
        TextView Width = (TextView) findViewById(R.id.ProductViewWidth);
        Width.setText(Float.toString(Product.width));
    }
}