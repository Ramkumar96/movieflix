package com.example.movieflix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class AddMovieActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageFilepath;
    private Bitmap imageToStore;
    private byte[] imageData;



    EditText movieTitleText, movieStudioText, movieRatingText;

    TextView messageLabel, dataSet;
    Button addButton, fetchButton , imagePickButton;
    ImageView movieImage;


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

//        imagePickButton = findViewById(R.id.pickImageButton);
        movieImage = findViewById(R.id.add_movieImage);


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

            byte[] imageData = getImageDataFromPathOrURL();

            movieDao.insertRecord(new Movie(movieTitleText.getText().toString(), movieStudioText.getText().toString(),
                    Integer.parseInt(movieRatingText.getText().toString()), imageData));
            movieTitleText.setText("");
            movieStudioText.setText("");
            movieRatingText.setText("");

//            Toast.makeText(AddMovieActivity.this, "Movie Inserted Successful", Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), "Inserted Successfully" ,Toast.LENGTH_SHORT).show();
            messageLabel.setText("Inserted Successfully");

        }
    }


    public void chooseImage(View objectView){
        try {
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null && data.getData()!=null) {
              imageFilepath = data.getData();
              imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilepath);
              movieImage.setImageBitmap(imageToStore);

                // Convert the selected image to a byte array
                InputStream inputStream = getContentResolver().openInputStream(imageFilepath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                imageData = byteArrayOutputStream.toByteArray();
            }
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

//    private byte[] getImageDataFromImageView() {
//        // Get the image path or URL from the ImageView
//        String imagePath = movieImage.getText().toString();
//
//        // Convert the image path or URL to byte array
//        // (you can implement this based on your requirements)
//        // For simplicity, I'm assuming you already have the byte array.
//        return getImageDataFromPathOrURL(imagePath);
//    }


    private byte[] getImageDataFromPathOrURL() {
        try {
            // Use Glide to load the image and convert it into a byte array
            byte[] imageData = Glide.with(this)
                    .as(byte[].class)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .load(imageFilepath)
                    .submit()
                    .get();

            return imageData;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return an empty byte array if conversion fails
        return new byte[0];
    }

}