package com.example.android.mood.views;

import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoetry;
import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.poetry.Poem;
import com.google.gson.Gson;
import com.qhutch.elevationimageview.ElevationImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by Joe on 4/2/18.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> implements View.OnClickListener {

    private List<WeatherPoetry> dataSet;
    private RecyclerView recyclerView;
    private Gson gson = new Gson();

    public WeatherAdapter(List<WeatherPoetry> dataSet, RecyclerView recyclerView) {
        this.dataSet = dataSet;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final WeatherViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
        holder.toggleIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean shouldExpand = holder.linesTV.getVisibility() == View.GONE;

                ChangeBounds transition = new ChangeBounds();
                transition.setDuration(300);

                if (shouldExpand) {
                    holder.linesTV.setVisibility(View.VISIBLE);
                } else {
                    holder.linesTV.setVisibility(View.GONE);
                }

                TransitionManager.beginDelayedTransition(recyclerView, transition);
                holder.itemView.setActivated(shouldExpand);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onClick(View view) {


    }


    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.weather_date_tv)
        public TextView dateTV;
        @BindView(R.id.weather_tv)
        public TextView weatherTV;
        @BindView(R.id.poem_title_tv)
        public TextView poemTitleTV;
        @BindView(R.id.poem_lines_tv)
        public TextView linesTV;
        @BindView(R.id.toggle_iv)
        public ImageView toggleIV;
        @BindView(R.id.weather_itemview_icon)
        public ElevationImageView elevationImageView;


        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(WeatherPoetry data) {
            AerisPeriod weatherData = gson.fromJson(data.getWeather(),AerisPeriod.class);
            Poem poemData = gson.fromJson(data.getPoem(),Poem.class);
            Log.d(TAG, "bind: timestamp = " + weatherData.getTimestamp() );

            dateTV.setText(weatherData.getFullDayOfTheWeekName());
            weatherTV.setText(itemView.getContext().getResources().getString(R.string.weather_title, weatherData.getWeatherPrimary(), weatherData.getMaxTempF(), weatherData.getMinTempF()));
            poemTitleTV.setText(poemData.getTitle());
            linesTV.setText(poemData.getFullPoem());
        }


    }
}
