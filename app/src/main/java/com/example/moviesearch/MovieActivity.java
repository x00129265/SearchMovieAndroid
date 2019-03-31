package com.example.moviesearch;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieActivity extends MainActivity {

    ImageView img;
    TextView title;
    TextView year;
    TextView description;
    TextView genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        img = findViewById(R.id.image_viewMovie);
        title = findViewById(R.id.movieTitle);
        year = findViewById(R.id.movieYear);
        description = findViewById(R.id.movieDescription);
        genre = findViewById(R.id.movieGenre);
        Bundle b = getIntent().getExtras();
        onOpenMovie(b.getInt("img"), b.getString("title"), b.getString("year"), b.getString("description"), b.getString("genre"));

    }

    public void onOpenMovie(int image, String title, String year, String description, String genre){
        img.setImageResource(image);
        this.title.setText(title);
        this.year.setText(year);
        this.description.setText(description);
        this.genre.setText(genre);
    }

}
