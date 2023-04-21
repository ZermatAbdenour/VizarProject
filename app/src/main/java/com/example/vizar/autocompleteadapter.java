package com.example.vizar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class autocompleteadapter extends RecyclerView.Adapter<autocompleteadapter.ViewHolder> {

    private List<String> elementlist;
    TextInputEditText searchtext;
    private searchFragment searchFragment;

    public autocompleteadapter(List<String> elementlist, TextInputEditText searchtext, com.example.vizar.searchFragment searchFragment) {
        this.elementlist = elementlist;
        this.searchtext = searchtext;
        this.searchFragment = searchFragment;
    }



    @NonNull
    @Override
    public autocompleteadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchelement,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull autocompleteadapter.ViewHolder holder, int position) {


        holder.setData(elementlist.get(position));
    }

    @Override
    public int getItemCount() {
        return elementlist.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        private TextView textview;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            textview = itemview.findViewById(R.id.text);
        }

        public void setData(String newstring) {



            textview.setText(newstring);
            itemView.findViewById(R.id.arrow_vecto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            searchtext.setText(textview.getText().toString());
                    searchtext.setSelection(searchtext.getText().length());
                }
            });
            itemView.findViewById(R.id.iconsax_lin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchtext.setText(textview.getText().toString());
                    searchtext.setSelection(searchtext.getText().length());
                    searchFragment.Search();

                }
            });
            itemView.findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchtext.setText(textview.getText().toString());
                    searchtext.setSelection(searchtext.getText().length());
                    searchFragment.Search();

                }
            });

        }
    }}
