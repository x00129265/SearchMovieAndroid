package com.example.moviesearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MovieViewHolder> implements Filterable {
    private List<MovieItem> movieList;
    private List<MovieItem> movieListFull;
    private Context mContext;
    private RecyclerView mRecyclerView;


    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        MovieViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
        }
    }

    ItemAdapter(List<MovieItem> movieList, RecyclerView recyclerView, Context mContext) {
        this.movieList = movieList;
        movieListFull = new ArrayList<>(movieList);
        mRecyclerView = recyclerView;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,
                parent, false);
        v.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                MovieItem item = movieList.get(itemPosition);
                Toast.makeText(mContext, item.getText1(),Toast.LENGTH_SHORT).show();
            }
        });
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieItem currentItem = movieList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getText1());
        holder.textView2.setText(currentItem.getText2());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MovieItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(movieListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MovieItem item : movieListFull) {
                    if (item.getText1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    if (item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movieList.clear();
            movieList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}