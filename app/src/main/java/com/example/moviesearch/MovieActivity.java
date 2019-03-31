package com.example.moviesearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieActivity extends MainActivity {

    ImageView img;
    TextView title;
    TextView year;
    TextView description;
    TextView genre;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        img = findViewById(R.id.image_viewMovie);
        title = findViewById(R.id.movieTitle);
        year = findViewById(R.id.movieYear);
        description = findViewById(R.id.movieDescription);
        genre = findViewById(R.id.movieGenre);
        backBtn = findViewById(R.id.buttonBack);

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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
