package com.example.android.mood.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoetry;
import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.poetry.Poem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/2/18.
 */

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.PoetryViewHolder> {

    private List<WeatherPoetry> dataSet;

    public PoetryAdapter(List<WeatherPoetry> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public PoetryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PoetryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item_view,parent,false));
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

        public void bind(WeatherPoetry data) {
            AerisPeriod weatherData = (AerisPeriod) data.getWeather();
            Poem poemData = (Poem) data.getPoem();
            weatherTitleTV.setText(weatherData.getWeatherPrimary());
            poemTitleTV.setText(poemData.getTitle());
            linesTV.setText(getPoem(poemData.getLines()));
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
