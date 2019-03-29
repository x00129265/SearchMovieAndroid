package com.example.moviesearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<? super Item> itemList;
    private List<MovieItem> searchList;;

    private Context mContext;
    private RecyclerView mRecyclerView;
    private RestfulClient client;


    ItemAdapter(List<? super Item> itemList, RecyclerView recyclerView, Context mContext) {
        this.itemList = itemList;
        mRecyclerView = recyclerView;
        this.mContext = mContext;
        client = new RestfulClient(mContext);
        searchList = new ArrayList<>();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
        }
    }

    class GenreViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
        }
    }


    public void onPressGenre(String name){
        getFilter().filter(name);
        Toast.makeText(mContext, "Genre:" + name, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item,
                parent, false);
        RecyclerView.ViewHolder holder = new GenreViewHolder(v); // use GenreViewHolder as default
        Log.i("Info", "View Type is: "+ viewType);

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
                    Toast.makeText(mContext,"Movie:" + item.getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
            return new MovieViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
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
            movieHolder.textView2.setText(currentMovieItem.getDescription());
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            //Toast.makeText(mContext, client.callService(mRecyclerView).get(0).getTitle(), Toast.LENGTH_SHORT).show();
            //Log.i("RestfulClient, adapter", "YO: " + client.callService(mRecyclerView).get(0).getTitle());

            client.callService(new ServerCallback() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("RestfulClient, OnSucc", response.toString());
                            Type listType = new TypeToken<ArrayList<MovieItem>>(){}.getType();
                            searchList = new Gson().fromJson(response, listType);
                        }
                    }
            );


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