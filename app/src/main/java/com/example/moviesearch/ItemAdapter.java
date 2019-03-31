package com.example.moviesearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<? super Item> itemList;
    private List<MovieItem> searchList;;

    private Context mContext;
    private RecyclerView mRecyclerView;
    private RestfulClient client;
    private Intent intent;
    private Bundle bundle;


    ItemAdapter(List<? super Item> itemList, RecyclerView recyclerView, Context mContext) {
        this.itemList = itemList;
        mRecyclerView = recyclerView;
        this.mContext = mContext;
        client = new RestfulClient(mContext);
        searchList = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_viewMovie);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
            intent = new Intent(mContext, MovieActivity.class);
            bundle = new Bundle();
        }
    }

    class GenreViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_viewMovie);
            textView1 = itemView.findViewById(R.id.text_view1);
        }
    }


    public void onPressGenre(String name){
        searchMovie(name);
        Toast.makeText(mContext, "Searching for genre:" + name, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item,
                parent, false);
        RecyclerView.ViewHolder holder = new GenreViewHolder(v); // use GenreViewHolder as default

        if(itemList.get(0) instanceof GenreItem) {
            // If item list is of type Genre then use Genre viewHolder
            v.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                    GenreItem item = (GenreItem)itemList.get(itemPosition);
                    onPressGenre(item.getTitle());

                }
            });
            return new GenreViewHolder(v);
        } else {
            // Else use movie viewHolder
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,
                    parent, false);
            v.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                    MovieItem item = (MovieItem)itemList.get(itemPosition);
                    bundle.putInt("img",item.getImageResource());
                    bundle.putString("title",item.getTitle());
                    bundle.putString("genre",item.getGenre());
                    bundle.putString("year",item.getYear());
                    bundle.putString("description",item.getDescription());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);


                    Toast.makeText(mContext,"Opening Movie:" + item.getTitle(),Toast.LENGTH_SHORT).show();


                }
            });
            return new MovieViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        // Bind view holders depending on what list item is used. I.e. is itemList of type Movie or Genre
        viewHolder.setIsRecyclable(false); // allows viewHolders to be updated.

        if(itemList.get(0) instanceof GenreItem) {
            // If item list is of type Genre then use Genre viewHolder
            GenreViewHolder genreHolder = (GenreViewHolder) viewHolder;
            GenreItem currentGenreItem = (GenreItem) itemList.get(i);

            genreHolder.imageView.setImageResource(currentGenreItem.getImageResource());
            genreHolder.textView1.setText(currentGenreItem.getTitle());
        } else {
            // Else use movie viewHolder
            MovieViewHolder movieHolder = (MovieViewHolder)viewHolder;
            MovieItem currentMovieItem = (MovieItem)itemList.get(i);

            movieHolder.imageView.setImageResource(currentMovieItem.getImageResource());
            movieHolder.textView1.setText(currentMovieItem.getTitle());
            String text2 = currentMovieItem.getGenre() + ", " +currentMovieItem.getYear();
            movieHolder.textView2.setText(text2);
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public void searchMovie(String query){
        client.callService(query, new ServerCallback() {
               @Override
               public void onSuccess(String response) {
                   // On response OK
                   Type listType = new TypeToken<ArrayList<MovieItem>>(){}.getType();
                   searchList = new Gson().fromJson(response, listType);

                   movieFilter.filter("");
               }

               @Override
               public void onError() {
                   // On response Error
                   searchList = new ArrayList<>();

                   movieFilter.filter("");
               }
        });
    }

    public void setImages(){
        for(int i = 0; i< searchList.size(); i++){
            MovieItem item = searchList.get(i);
            item.setImageResource(findGenreImage(item.getGenre().toLowerCase()));
        }
    }

    public int findGenreImage(String genre){
        int icon;
        if(genre.contains("comedy")){
            icon = R.drawable.ic_comedy;
        } else if(genre.contains("horror")){
            icon = R.drawable.ic_horror;
            //etc...
        } else {
            icon = R.drawable.ic_photo;
        }
        return icon;
    }

    private Filter movieFilter = new Filter() {
        // First executes Filtering and then publishing results
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            setImages();
            results.values = searchList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}