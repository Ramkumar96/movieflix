package com.example.movieflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.room.Room;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        //toolbar back button
        ImageView backButton = findViewById(R.id.toolbar_back_icon);
        TextView toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setText("Edit Movie");

        uMovieTitle = findViewById(R.id.edit_movieTitle);
        uMovieStudio = findViewById(R.id.edit_movieStudio);
        uMovieRating = findViewById(R.id.edit_movieRating);
        edit_movie_btn = findViewById(R.id.edit_movie_btn);

        int uMovieID = Integer.parseInt(getIntent().getStringExtra("uMovieId"));
        uMovieTitle.setText(getIntent().getStringExtra("uMovieTitle"));
        uMovieStudio.setText(getIntent().getStringExtra("uMovieStudio"));
        uMovieRating.setText(getIntent().getStringExtra("uMovieRating"));


        edit_movie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDatabase db = Room.databaseBuilder(getApplicationContext(),
                        MovieDatabase.class, "movieDB").allowMainThreadQueries().build();
                MovieDao movieDao = db.movieDao();
                movieDao.updateById(uMovieID, uMovieTitle.getText().toString(), uMovieStudio.getText().toString(), Integer.parseInt(uMovieRating.getText().toString()));

                Toast.makeText(EditMovieActivity.this, "Movie Updated Successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMovieActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}