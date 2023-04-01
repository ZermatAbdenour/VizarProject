package com.example.vizar;

import android.icu.lang.UCharacter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vizar.Model.CategorieCard;
import com.example.vizar.Model.Horizantalrecyclerview;
import com.example.vizar.Model.product;
import com.example.vizar.Remote.BaseGridConcatAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homemain#newInstance} factory method to
 * create an instance of this fragment.
 */

    public class homemain extends Fragment {

        public RecyclerView parentRecyclerView;
        private RecyclerView.Adapter ParentAdapter;
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

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            horizantalrecyclerviewList.add(new Horizantalrecyclerview("Featured categories"));
            productslist.add(new product("table","$200","good table",R.drawable.cat_armchairs));
            productslist.add(new product("table","$200","good tale",R.drawable.cat_tvstands));
            productslist.add(new product("table","$200","good tale",R.drawable.cat_cat1));
            productslist.add(new product("table","$200","good tale",R.drawable.cat_dressers));
            productslist.add(new product("table","$200","good",R.drawable.background));
            productslist.add(new product("table","$200","good tale",R.drawable.cat_diningtable));
            productslist.add(new product("table","$200","good table",R.drawable.cat_armchairs));
            productslist.add(new product("table","$200","good tale",R.drawable.cat_tvstands));
            productslist.add(new product("table","$200","good tale",R.drawable.cat_cat1));
            productslist.add(new product("table","$200","good tale",R.drawable.cat_dressers));
            productslist.add(new product("table","$200","good",R.drawable.background));
            productslist.add(new product("table","$200","good tale",R.drawable.cat_diningtable));
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_homemain, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);




            recyclerview = view.findViewById(R.id.Parent_recyclerView);
            recyclerview.setLayoutManager(new GridLayoutManager(getContext(),1));
            recyclerview.setHasFixedSize(true);
            Adapter_1 outeradapter = new Adapter_1(horizantalrecyclerviewList,getContext());
            Adapter listadapter = new Adapter(productslist);
            footeradapter footer = new footeradapter(R.layout.footer);

            ConcatAdapter concatAdapter = new ConcatAdapter(outeradapter,new BaseGridConcatAdapter(getContext(),listadapter,2,"Top sales"),new BaseGridConcatAdapter(getContext(),listadapter,2,"Recommendations"),footer);

            recyclerview.setAdapter(concatAdapter);
            concatAdapter.notifyDataSetChanged();
        }

    }
