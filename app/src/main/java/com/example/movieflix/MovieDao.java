package com.example.movieflix;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("DELETE FROM movie WHERE movieID = :movieId")
    void deleteById(int movieId);

    @Query("SELECT * FROM movie WHERE movieID IN (:movieIds)")
    List<Movie> loadAllByIds(int[] movieIds);

    @Query("SELECT * FROM movie WHERE movieTitle LIKE :title AND " +
            "studio LIKE :studio_name LIMIT 1")
    Movie findByName(String title, String studio_name);

//    @Query("SELECT EXISTS (SELECT * FROM movie WHERE movieID = :movieIds)")
//    Boolean is_exists(int movieIds);

    @Insert
    void insertAll(List<Movie> movies);

    @Insert
    void insertRecord(Movie movies);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
