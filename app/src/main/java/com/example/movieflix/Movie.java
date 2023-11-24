package com.example.movieflix;

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

    // Constructor

    public Movie(int movieID, String movieTitle, String studio, int criticsRating) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.studio = studio;
        this.criticsRating = criticsRating;
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
}

