package com.example.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.gson.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ItemAdapter adapter;
    private List<? super Item> categoryList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillCategoryList();
        setUpRecyclerView();

    }

    private void fillCategoryList() {
        // Pre-populated category list
        categoryList = new ArrayList<>();
        categoryList.add(new GenreItem(R.drawable.ic_photo, "Action"));
        categoryList.add(new GenreItem(R.drawable.ic_photo, "Adventure"));
        categoryList.add(new GenreItem(R.drawable.ic_comedy, "Comedy"));
        categoryList.add(new GenreItem(R.drawable.ic_photo, "Crime"));
        categoryList.add(new GenreItem(R.drawable.ic_photo, "Drama"));
        categoryList.add(new GenreItem(R.drawable.ic_photo, "Fantasy"));
        categoryList.add(new GenreItem(R.drawable.ic_photo, "Thriller"));
        categoryList.add(new GenreItem(R.drawable.ic_photo, "Historical fiction"));
        categoryList.add(new GenreItem(R.drawable.ic_horror, "Horror"));
    }


    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ItemAdapter(categoryList, recyclerView, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.searchHint));
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { ;
                adapter.searchMovie(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Change display dynamically
                return false;
            }
        });
        return true;
    }

}