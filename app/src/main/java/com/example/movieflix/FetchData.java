package com.example.movieflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.List;

public class FetchData extends AppCompatActivity {

    RecyclerView movieRecView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);

        movieRecView = findViewById(R.id.movieRecyclerView);
        movieRecView.setLayoutManager(new LinearLayoutManager(this));

        getMovieData();
    }

    public void getMovieData() {
        MovieDatabase db = Room.databaseBuilder(getApplicationContext(),
                MovieDatabase.class, "movieDB").allowMainThreadQueries().build();
        MovieDao movieDao = db.movieDao();
        List<Movie> movies = movieDao.getAll();

        myadapter adapter = new myadapter(movies);
        movieRecView.setAdapter(adapter);
    }
}