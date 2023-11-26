package com.example.movieflix;

import androidx.appcompat.app.AppCompatActivity;
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
    Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingactionbutton);
        fetchButton = findViewById(R.id.fetchDataBtn);
        dataSet = findViewById(R.id.dataholder);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddMovieActivity.class));
            }
        });

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), FetchData.class));
//                MovieDatabase db = Room.databaseBuilder(getApplicationContext(),
//                        MovieDatabase.class, "movieDB").allowMainThreadQueries().build();
//                MovieDao movieDao = db.movieDao();
//
//                List<Movie> movies = movieDao.getAll();
//                String moviesSting= "";
//
//                // Iterating through the movies and putting to string
//                for (Movie movie : movies)
//                    moviesSting = moviesSting +"\t " + movie.getMovieID() + " " + movie.getMovieTitle() + " " + movie.getStudio() + " " + movie.getCriticsRating() + "\n\n";
//
//                dataSet.setText(moviesSting);
            }
        });
    }
}