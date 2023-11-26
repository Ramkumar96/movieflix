package com.example.movieflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

    ImageView movieImage;

    private static final int PICK_IMAGE_REQUEST = 1;
    private String imagePath;


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
//        fetchButton = findViewById(R.id.fetchDataBtn);
        dataSet = findViewById(R.id.dataholder);
        movieImage = findViewById(R.id.add_movieImage);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertMovie();
            }
        });

        movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMovieActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            movieImage.setImageURI(imageUri);
            imagePath = imageUri.toString(); // Store the image path
        }
    }

    private void insertMovie() {
        String title = movieTitleText.getText().toString();
        String studio = movieStudioText.getText().toString();
        int rating = Integer.parseInt(movieRatingText.getText().toString());

        // Ensure imagePath (movieImage) is not empty
        if (title.isEmpty() || studio.isEmpty() || imagePath.isEmpty() || movieRatingText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert movie into Room database
        MovieDatabase db = Room.databaseBuilder(getApplicationContext(),
                MovieDatabase.class, "movieDB").allowMainThreadQueries().build();
        MovieDao movieDao = db.movieDao();

        Movie newMovie = new Movie(title, studio, rating, imagePath);
        newMovie.movieImage = imagePath; // Set the image path
        movieDao.insertRecord(newMovie);

        // Clear input fields after insertion
        movieTitleText.setText("");
        movieStudioText.setText("");
        movieRatingText.setText("");
        movieImage.setImageResource(R.drawable.ic_launcher_foreground);

        Toast.makeText(this, "Movie Inserted Successfully", Toast.LENGTH_SHORT).show();
    }

}