package com.example.android.mood.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoetry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/8/18.
 */

public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.PoemViewHolder> {
    private List<WeatherPoetry> data;

    public PoemAdapter(List<WeatherPoetry> dataSet) {
        data = dataSet;
    }

    @NonNull
    @Override
    public PoemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PoemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.poem_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PoemViewHolder holder, int position) {
        holder.bind(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class PoemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.poem_title)
        public TextView title;
        @BindView(R.id.author)
        public TextView author;
        @BindView(R.id.poem)
        public TextView poemText;
        @BindView(R.id.mood_tv)
        public TextView moodTextView;

        public PoemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(WeatherPoetry data) {
            //TODO remove gson serialization
//            Poem poem = new Gson().fromJson(data.getPoem(), Poem.class);
//            title.setText(poem.getTitle());
//            author.setText(poem.getAuthor());
//            poemText.setText(poem.getFullPoem());
//            WatsonHelper.getInstance().getTones(data.getPoem(),moodTextView);
        }

    }
}
