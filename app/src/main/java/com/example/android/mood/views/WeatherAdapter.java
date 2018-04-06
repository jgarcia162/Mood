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

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherPoetry> dataSet;
    private int expandedPosition = -1;

    public WeatherAdapter(List<WeatherPoetry> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final WeatherViewHolder holder, final int position) {
        final boolean isExpanded = position==expandedPosition;
        holder.bind(dataSet.get(position));
        holder.linesTV.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded ? -1:position;
                if (position == expandedPosition) {
                    holder.linesTV.setVisibility(View.VISIBLE);
                } else {
                    holder.linesTV.setVisibility(View.GONE);
                }
                notifyItemChanged(expandedPosition);
            }
        });
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
        @BindView(R.id.poem_title_tv)
        public TextView poemTitleTV;
        @BindView(R.id.poem_lines_tv)
        public TextView linesTV;


        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(WeatherPoetry data) {
            AerisPeriod weatherData = (AerisPeriod) data.getWeather();
            Poem poemData = (Poem) data.getPoem();

            //TODO get day value of timestamp
            dateTV.setText(String.valueOf(weatherData.getTimestamp()));
            weatherTV.setText(itemView.getContext().getResources().getString(R.string.weather_title, weatherData.getWeatherPrimary(), weatherData.getMaxTempF(), weatherData.getMinTempF()));
            poemTitleTV.setText(poemData.getTitle());
            linesTV.setText(getPoem(poemData.getLines()));
        }

        private String getPoem(String[] lines) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}
