package com.example.vizar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizar.Model.product;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.BaseGridConcatAdapter;
import com.example.vizar.Remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homemain#newInstance} factory method to
 * create an instance of this fragment.
 */

    public class homemain extends Fragment {
        APILink apiLink;

        //Scrolling
        public static int ProductsCountPerCall = 10;
        private int Offset;
        private int LastOffset;
        private boolean FirstCall;

        public RecyclerView parentRecyclerView;
        private RecyclerView.Adapter ParentAdapter;
        ConcatAdapter concatAdapter;
        Adapter listadapter;
        Adapter_1 outeradapter;
        footeradapter footer;

        ArrayList<Horizantalrecyclerview> parentModelArrayList = new ArrayList<>();
        List<product> productslist = new ArrayList<>();
        private RecyclerView.LayoutManager parentLayoutManager;

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        private String mParam1;
        private String mParam2;
            ArrayList<Horizantalrecyclerview> horizantalrecyclerviewList =  new ArrayList<>();
    private RecyclerView recyclerview;

    public homemain() {
            // Required empty public constructor
        }

        public static homemain newInstance(String param1, String param2) {
            homemain fragment = new homemain();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
            apiLink = RetrofitClient.getInstance().create(APILink.class);
            FirstCall = false;

            horizantalrecyclerviewList.add(new Horizantalrecyclerview("Featured categories"));

            listadapter = new Adapter(productslist,R.layout.product,false,false,getActivity());
            outeradapter = new Adapter_1(horizantalrecyclerviewList,getContext(),getActivity());
            footer = new footeradapter(R.layout.footer);
            concatAdapter = new ConcatAdapter(outeradapter,new BaseGridConcatAdapter(getContext(),listadapter,2,"Recommendations"),footer);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //Set ButtomNav
            Home.ButtomNavHome(this);

            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_homemain, container, false);
        }


        private boolean isLoading;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);


                recyclerview = getActivity().findViewById(R.id.Parent_recyclerView);
                recyclerview.setLayoutManager(new GridLayoutManager(getContext(),1));

                recyclerview.setAdapter(concatAdapter);

            if(productslist.size()==0){
                GetFirstProducts();


                recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (!recyclerView.canScrollVertically(1)&& !isLoading && FirstCall && Offset!=LastOffset) {
                            isLoading =true;

                            //concatAdapter.notifyDataSetChanged();
                            listadapter.notifyItemRangeInserted(Offset,ProductsCountPerCall);

                            System.out.println(concatAdapter.getItemCount());
                            LastOffset = Offset;
                            GetProducts();

                        }
                        else
                            isLoading = false;

                    }
                });
            }
        }

        public void GetFirstProducts(){
            Call<List<product>> getproducts = apiLink.getproducts(Offset,ProductsCountPerCall);
            getproducts.enqueue(new Callback<List<product>>() {
                @Override
                public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                    if(response.body() != null){
                        productslist.addAll(response.body());

                        listadapter.notifyItemRangeInserted(1,10);

                        Offset=response.body().size();
                        LastOffset=response.body().size();
                        FirstCall = true;
                        GetProducts();
                    }
                }

                @Override
                public void onFailure(Call<List<product>> call, Throwable t) {

                }
            });
        }

        public void GetProducts(){
            Call<List<product>> getproducts = apiLink.getproducts(Offset,ProductsCountPerCall);
            getproducts.enqueue(new Callback<List<product>>() {
                @Override
                public void onResponse(Call<List<product>> call, Response<List<product>> response) {

                    if(response.body().size()>0){
                    productslist.addAll(response.body());
                    Offset+=response.body().size();

                }else {

                    System.out.println("response size : "+response.body().size()+"       655555555555555555555555555555555555555555555555555555555555555555");
                    concatAdapter.removeAdapter(footer);
                    footer = new footeradapter(R.layout.savedfooter);
                    concatAdapter.addAdapter(footer);
                    //      concatAdapter.notifyDataSetChanged();

                }}

                @Override
                public void onFailure(Call<List<product>> call, Throwable t) {

                }
            });
        }

    }
