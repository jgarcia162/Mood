package com.example.android.mood.views;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.weather.Weather;
import com.example.android.mood.model.weather.WeatherUtils;
import com.qhutch.elevationimageview.ElevationImageView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/2/18.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<Weather> dataSet;

    public WeatherAdapter(List<Weather> dataSet) {
        this.dataSet = dataSet;
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
        @BindView(R.id.weekday_tv)
        public TextView dateTV;
        @BindView(R.id.temp_tv)
        public TextView weatherTV;
        @BindView(R.id.weather_itemview_icon)
        public ElevationImageView elevationImageView;


        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Weather data) {
            String icon = data.getIcon();
            Drawable iconDrawable = getDrawable(icon);

            elevationImageView.setImageDrawable(iconDrawable);
            dateTV.setText(data.getFullDayOfTheWeekName());
            weatherTV.setText(String.valueOf(data.getMaxTempF()));
        }

        private Drawable getDrawable(String icon) {
            Resources resources = Objects.requireNonNull(itemView.getContext()).getResources();

            String uri = "@drawable/".concat(icon);
            uri = uri.replace(".png", "");

            int imageResource = resources.getIdentifier(uri, null, Objects.requireNonNull(itemView.getContext()).getPackageName());

            if (imageResource == 0) {
                imageResource = WeatherUtils.getAlternateResourceId(icon);
                return resources.getDrawable(imageResource, null);
            } else {
                return resources.getDrawable(imageResource, null);
            }
        }
    }
}
