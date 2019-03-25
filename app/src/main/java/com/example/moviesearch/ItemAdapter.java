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

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<MovieItem> movieList;
    private List<CategoryItem> categoryList;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public class ItemViewHolder {
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

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
        }

    }

    ItemAdapter(List<CategoryItem> categoryList, RecyclerView recyclerView, Context mContext) {
        this.categoryList = categoryList;
        this.movieList = new ArrayList<>();
        mRecyclerView = recyclerView;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,
                parent, false);
        switch (viewType) {
            case 0:
//                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,
//                        parent, false);
                v.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                        MovieItem item = movieList.get(itemPosition);
                        Toast.makeText(mContext, item.getText1(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,
                        parent, false);
                v.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                        MovieItem item = movieList.get(itemPosition);
                        Toast.makeText(mContext, item.getText1(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return new MovieViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                CategoryViewHolder categoryHolder = (CategoryViewHolder)viewHolder;
                CategoryItem currentCategiryItem = categoryList.get(i);

                categoryHolder.imageView.setImageResource(currentCategiryItem.getImageResource());
                categoryHolder.textView1.setText(CategoryItem.getTitle());

                break;

            case 2:
                MovieViewHolder movieHolder = (MovieViewHolder)viewHolder;
                MovieItem currentMovieItem = movieList.get(i);

                movieHolder.imageView.setImageResource(currentMovieItem.getImageResource());
                movieHolder.textView1.setText(movieHolder.getText1());
                movieHolder.textView2.setText(movieHolder.getText2());
                break;
        }

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
                filteredList.addAll(movieList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MovieItem item : movieList) {
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