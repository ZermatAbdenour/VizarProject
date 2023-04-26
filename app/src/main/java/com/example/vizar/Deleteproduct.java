package com.example.vizar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizar.Model.User;
import com.example.vizar.Model.product;
import com.example.vizar.Remote.APILink;
import com.example.vizar.Remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Deleteproduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Deleteproduct extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private APILink apiLink;
    private User user;

    public Deleteproduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Deleteproduct.
     */
    // TODO: Rename and change types and number of parameters


    private RecyclerView recyclerview;
    List<product> warehouseproductslist = new ArrayList<>();
    public static Deleteproduct newInstance(String param1, String param2) {
        Deleteproduct fragment = new Deleteproduct();
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
        // Inflate the layout for this fragment
        apiLink = RetrofitClient.getInstance().create(APILink.class);
        user = Paper.book().read("User");
/*
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        warehouseproductslist.add(new product("table",200,"Published since: 14/03/2022"));
        */
        Call<List<product>> getuserproducts = apiLink.getuserproducts(user.id);

        getuserproducts.enqueue(new Callback<List<product>>() {
            @Override
            public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                warehouseproductslist.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<product>> call, Throwable t) {

            }
        });
        return inflater.inflate(R.layout.fragment_deleteproduct, container, false);
    }
    Adapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.delete_recyclerView);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerview.setHasFixedSize(true);
         adapter = new Adapter(warehouseproductslist,R.layout.deleteproduct,true,false,getActivity());
        footeradapter footer = new footeradapter(R.layout.savedfooter);
        ConcatAdapter concatAdapter = new ConcatAdapter(adapter,footer);
        recyclerview.setAdapter(concatAdapter);


    }
}