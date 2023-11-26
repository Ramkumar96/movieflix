package com.example.movieflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;*/

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AddMovieActivity extends AppCompatActivity {


    EditText movieTitleText, movieStudioText, movieRatingText;

    TextView messageLabel, dataSet;
    Button addButton, fetchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);


        //toolbar back button
        ImageView backButton = findViewById(R.id.toolbar_back_icon);
        TextView toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setText("Add Movie");

        movieTitleText = findViewById(R.id.add_movieTitle);
        movieStudioText = findViewById(R.id.add_movieStudio);
        movieRatingText = findViewById(R.id.add_movieRating);
        messageLabel = findViewById(R.id.messagelabel);
        addButton = findViewById(R.id.add_movie_btn);
        fetchButton = findViewById(R.id.fetchDataBtn);
        dataSet = findViewById(R.id.dataholder);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new bgThread().start();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AddMovieActivity.this, MainActivity.class);
//                startActivity(intent);
                new bgThread().start();
            }
        });

    }

    class bgThread extends Thread {
        public void run() {
            super.run();
            MovieDatabase db = Room.databaseBuilder(getApplicationContext(),
                    MovieDatabase.class, "movieDB").allowMainThreadQueries().build();
            MovieDao movieDao = db.movieDao();
            movieDao.insertRecord(new Movie(movieTitleText.getText().toString(), movieStudioText.getText().toString(),
                    Integer.parseInt(movieRatingText.getText().toString())));
            movieTitleText.setText("");
            movieStudioText.setText("");
            movieRatingText.setText("");

//            Toast.makeText(AddMovieActivity.this, "Movie Inserted Successful", Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), "Inserted Successfully" ,Toast.LENGTH_SHORT).show();
            messageLabel.setText("Inserted Successfully");

        }
    }

}