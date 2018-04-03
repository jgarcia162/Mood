package com.example.android.mood.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.AerisPoetry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/2/18.
 */

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.PoetryViewHolder> {

    private List<AerisPoetry> dataSet;

    public PoetryAdapter(List<AerisPoetry> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public PoetryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PoetryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(PoetryViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class PoetryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.weather_title_tv)
        public TextView weatherTitleTV;
        @BindView(R.id.poem_title_tv)
        public TextView poemTitleTV;
        @BindView(R.id.lines_tv)
        public TextView linesTV;

        public PoetryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(AerisPoetry data) {
            weatherTitleTV.setText(data.getAerisPeriod().getWeatherPrimary());
            poemTitleTV.setText(data.getPoem().getTitle());
            linesTV.setText(getPoem(data.getPoem().getLines()));
        }

        private String getPoem(String[] lines){
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}
