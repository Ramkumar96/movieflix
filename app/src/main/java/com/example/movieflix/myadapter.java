package com.example.movieflix;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>{

    List<Movie> movies;

    public myadapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdessign,parent,false);
       return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.myviewholder holder, int position) {

        holder.recMovieID.setText(String.valueOf(movies.get(position).getMovieID()));
        holder.recMovieTitle.setText(movies.get(position).getMovieTitle());
        holder.recMovieStudio.setText(movies.get(position).getStudio());
        holder.recMovieRatings.setText(String.valueOf(movies.get(position).getCriticsRating()));

        // Delete Btn
        holder.recDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDatabase db = Room.databaseBuilder(holder.recMovieID.getContext(),
                        MovieDatabase.class, "movieDB").allowMainThreadQueries().build();
                MovieDao movieDao = db.movieDao();

                // delete from db
                movieDao.deleteById(movies.get(position).getMovieID());

                // delete from array list
                movies.remove(position);
                notifyDataSetChanged();
            }
        });

        // Edit Btn
        holder.recEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(holder.recEditBtn.getContext(), EditMovieActivity.class));
                intent.putExtra("uMovieId", String.valueOf(movies.get(position).getMovieID()));
                intent.putExtra("uMovieTitle", movies.get(position).getMovieTitle());
                intent.putExtra("uMovieStudio", movies.get(position).getStudio());
                intent.putExtra("uMovieRating", String.valueOf(movies.get(position).getCriticsRating()));
                holder.recEditBtn.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView recMovieID, recMovieTitle, recMovieStudio, recMovieRatings;
        Button recEditBtn, recDeleteBtn;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            recMovieID = itemView.findViewById(R.id.recMovieID);
            recMovieTitle = itemView.findViewById(R.id.recMovieTitle);
            recMovieStudio = itemView.findViewById(R.id.recMovieStudio);
            recMovieRatings = itemView.findViewById(R.id.recMovieRatings);
            recDeleteBtn = itemView.findViewById(R.id.recDeleteBtn);
            recEditBtn = itemView.findViewById(R.id.recEditBtn);


        }
    }
}
