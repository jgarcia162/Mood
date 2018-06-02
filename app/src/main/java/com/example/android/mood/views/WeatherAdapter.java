package com.example.android.mood.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.weather.Weather;
import com.qhutch.elevationimageview.ElevationImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/2/18.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<Weather> dataSet;
    private RecyclerView recyclerView;

    public WeatherAdapter(List<Weather> dataSet, RecyclerView recyclerView) {
        this.dataSet = dataSet;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.weather_date_tv)
        public TextView dateTV;
        @BindView(R.id.weather_tv)
        public TextView weatherTV;
        @BindView(R.id.weather_itemview_icon)
        public ElevationImageView elevationImageView;


        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Weather data) {
            elevationImageView.setImageDrawable(itemView.getContext().getDrawable(R.drawable.clear));
            dateTV.setText(data.getFullDayOfTheWeekName());
            weatherTV.setText(itemView.getContext().getResources().getString(R.string.weather_title, data.getWeatherPrimary(), data.getMaxTempF(), data.getMinTempF()));
        }
    }
}
