package com.example.vizar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseConcatHolder<T> extends RecyclerView.ViewHolder {
    public BaseConcatHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void bind(T adapter);
}
