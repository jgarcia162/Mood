package com.example.android.mood.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoem;
import com.example.android.mood.room.MoodDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/3/18.
 */

public class PoemFragment extends Fragment {
    @BindView(R.id.poems_rv)
    public RecyclerView poemsRecyclerView;
    private MoodDatabase database;
    private Context context;
    private List<WeatherPoem> poemsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        database = MoodDatabase.getInstance(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poetry, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPoemsFromDB();
    }

    @SuppressLint("StaticFieldLeak")
    private void getPoemsFromDB() {

        new AsyncTask<Void,Void,List<WeatherPoem>>(){
            @Override
            protected List<WeatherPoem> doInBackground(Void... voids) {
                return database.weatherPoetryDao().getAll();
            }

            @Override
            protected void onPostExecute(List<WeatherPoem> weatherPoemList) {
                setUpRecyclerView(weatherPoemList);
            }
        }.execute();

    }

    private void setUpRecyclerView(List<WeatherPoem> dataSet) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        poemsRecyclerView.setLayoutManager(manager);
        poemsRecyclerView.setAdapter(new PoemAdapter(dataSet));
    }
}
