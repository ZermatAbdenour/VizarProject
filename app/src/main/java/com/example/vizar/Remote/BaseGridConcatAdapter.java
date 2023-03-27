package com.example.vizar.Remote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizar.Adapter;
import com.example.vizar.BaseConcatHolder;
import com.example.vizar.R;

public class BaseGridConcatAdapter extends RecyclerView.Adapter<BaseConcatHolder<?>> {
    private Context context;
    private Adapter productsAdapter;
    private int spanCount;

    public BaseGridConcatAdapter(Context context, Adapter productsAdapter, int spanCount) {
        this.context = context;
        this.productsAdapter = productsAdapter;
        this.spanCount = spanCount;
    }

    @NonNull
    @Override
    public BaseConcatHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.concat_row, parent, false);
        ((RecyclerView)view.findViewById(R.id.rv_concat)).setLayoutManager(new GridLayoutManager(context, spanCount));
        return new ConcatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseConcatHolder<?> holder, int position) {
        if (holder instanceof ConcatViewHolder) {
            ((ConcatViewHolder) holder).bind(productsAdapter);
        } else {
            throw new IllegalArgumentException("No viewholder to show this data, did you forgot to add it to the onBindViewHolder?");
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ConcatViewHolder extends BaseConcatHolder<Adapter> {
        public ConcatViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Adapter adapter) {
            ((RecyclerView) itemView.findViewById(R.id.rv_concat)).setAdapter(adapter);
        }
    }
}
