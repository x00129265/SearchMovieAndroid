package com.example.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ItemAdapter adapter;
    private List<MovieItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillItemList();
        setUpRecyclerView();
    }

    private void fillItemList() {
        itemList = new ArrayList<>();
        itemList.add(new MovieItem(R.drawable.ic_search, "One", "Ten"));
        itemList.add(new MovieItem(R.drawable.ic_search, "Two", "Eleven"));
        itemList.add(new MovieItem(R.drawable.ic_search, "Three", "Twelve"));
        itemList.add(new MovieItem(R.drawable.ic_search, "Four", "Thirteen"));
        itemList.add(new MovieItem(R.drawable.ic_search, "Five", "Fourteen"));
        itemList.add(new MovieItem(R.drawable.ic_search, "Six", "Fifteen"));
        itemList.add(new MovieItem(R.drawable.ic_search, "Seven", "Sixteen"));
        itemList.add(new MovieItem(R.drawable.ic_search, "Eight", "Seventeen"));
        itemList.add(new MovieItem(R.drawable.ic_search, "Nine", "Eighteen"));
    }


    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ItemAdapter(itemList, recyclerView, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
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