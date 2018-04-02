package com.example.android.mood.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mood.R;
import com.example.android.mood.model.AerisPoetry;

import java.util.List;

/**
 * Created by Joe on 4/2/18.
 */

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.PoetryViewHolder> {

    private List<AerisPoetry> dataSet;

    @Override
    public PoetryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
    }

    @Override
    public void onBindViewHolder(PoetryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class PoetryViewHolder extends RecyclerView.ViewHolder {

        public PoetryViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(AerisPoetry data) {

        }
    }
}
