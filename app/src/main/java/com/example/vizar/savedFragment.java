package com.example.vizar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vizar.Model.product;
import com.example.vizar.Remote.BaseGridConcatAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link savedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class savedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerview;
    List<product> savedproductslist = new ArrayList<>();

    public savedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment savedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static savedFragment newInstance(String param1, String param2) {
        savedFragment fragment = new savedFragment();
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
        savedproductslist.add(new product("table","$200","good table",R.drawable.cat_armchairs));
        savedproductslist.add(new product("table","$200","good tale",R.drawable.cat_tvstands));
        savedproductslist.add(new product("table","$200","good tale",R.drawable.cat_cat1));
        savedproductslist.add(new product("table","$200","good tale",R.drawable.cat_dressers));
        savedproductslist.add(new product("table","$200","good",R.drawable.background));
        savedproductslist.add(new product("table","$200","good tale",R.drawable.cat_diningtable));
        savedproductslist.add(new product("table","$200","good table",R.drawable.cat_armchairs));
        savedproductslist.add(new product("table","$200","good tale",R.drawable.cat_tvstands));
        savedproductslist.add(new product("table","$200","good tale",R.drawable.cat_cat1));
        savedproductslist.add(new product("table","$200","good tale",R.drawable.cat_dressers));


        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.saved_recyclerView);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerview.setHasFixedSize(true);
        Adapter adapter = new Adapter(savedproductslist);
        footeradapter footer = new footeradapter(R.layout.savedfooter);
        ConcatAdapter concatAdapter = new ConcatAdapter(new BaseGridConcatAdapter(getContext(),adapter,2,"Saved Products"),footer);
        recyclerview.setAdapter(concatAdapter);
        adapter.notifyDataSetChanged();

    }
}