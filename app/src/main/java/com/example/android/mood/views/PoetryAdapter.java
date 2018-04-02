package com.example.android.mood.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mood.model.AerisPoetry;

/**
 * Created by Joe on 4/2/18.
 */

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.PoetryViewHolder> {


    @Override
    public PoetryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PoetryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class PoetryViewHolder extends RecyclerView.ViewHolder {

        public PoetryViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(AerisPoetry data) {

        }
    }
}
