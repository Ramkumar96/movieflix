package com.example.movieflix;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    public int movieID;

    @ColumnInfo(name = "movieTitle")
    public String movieTitle;

    @ColumnInfo(name = "studio")
    public String studio;

    @ColumnInfo(name = "criticsRating")
    public int criticsRating;

    @ColumnInfo(name = "movieImage")
    public byte[] movieImage;

    // Constructor

    public Movie(String movieTitle, String studio, int criticsRating, byte[] movieImage) {
        this.movieTitle = movieTitle;
        this.studio = studio;
        this.criticsRating = criticsRating;
        this.movieImage = movieImage;
    }


    // Getters and Setters

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getCriticsRating() {
        return criticsRating;
    }

    public void setCriticsRating(int criticsRating) {
        this.criticsRating = criticsRating;
    }

    public byte[] getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(byte[] movieImage) {
        this.movieImage = movieImage;
    }
}

