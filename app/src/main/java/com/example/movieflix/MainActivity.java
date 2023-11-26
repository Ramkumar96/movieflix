package com.example.movieflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    TextView dataSet;

    // Fetching Data
    RecyclerView movieRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingactionbutton);
//        fetchButton = findViewById(R.id.fetchDataBtn);
        dataSet = findViewById(R.id.dataholder);

        movieRecView = findViewById(R.id.movieRecyclerView);
        movieRecView.setLayoutManager(new LinearLayoutManager(this));
        getMovieData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddMovieActivity.class));
            }
        });

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