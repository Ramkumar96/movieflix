package com.example.movieflix;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditMovieActivity extends AppCompatActivity {

    EditText uMovieTitle, uMovieStudio, uMovieRating;
    AppCompatButton edit_movie_btn;
    ImageView edit_movie_image;

    private static final int PICK_IMAGE_REQUEST = 1;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        // Initialize views
        uMovieTitle = findViewById(R.id.edit_movieTitle);
        uMovieStudio = findViewById(R.id.edit_movieStudio);
        uMovieRating = findViewById(R.id.edit_movieRating);
        edit_movie_btn = findViewById(R.id.edit_movie_btn);
        edit_movie_image = findViewById(R.id.edit_movieImage);

        // Retrieve data from the intent
        int uMovieID = Integer.parseInt(getIntent().getStringExtra("uMovieId"));
        uMovieTitle.setText(getIntent().getStringExtra("uMovieTitle"));
        uMovieStudio.setText(getIntent().getStringExtra("uMovieStudio"));
        uMovieRating.setText(getIntent().getStringExtra("uMovieRating"));

        // Image selection onClick listener
        edit_movie_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        // Update button onClick listener
        edit_movie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMovie(uMovieID);
            }
        });

        ImageView backButton = findViewById(R.id.toolbar_back_icon);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMovieActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to launch image picker intent
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handling the result after the image is picked
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageUrl = selectedImageUri.toString();
            edit_movie_image.setImageURI(selectedImageUri); // Display the selected image in ImageView
        }
    }

    // Method to update movie details
    private void updateMovie(int uMovieID) {
        // Handle other movie details
        String title = uMovieTitle.getText().toString();
        String studio = uMovieStudio.getText().toString();
        int rating = Integer.parseInt(uMovieRating.getText().toString());

        MovieDatabase db = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "movieDB").allowMainThreadQueries().build();
        MovieDao movieDao = db.movieDao();
        movieDao.updateById(uMovieID, title, studio, rating, imageUrl);

        Toast.makeText(EditMovieActivity.this, "Movie Updated Successfully", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}