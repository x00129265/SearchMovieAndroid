package com.example.moviesearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
    private List<? super Item> itemList;

    private Context mContext;
    private RecyclerView mRecyclerView;


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

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
        }
    }

    ItemAdapter(List<? super Item> itemList, RecyclerView recyclerView, Context mContext) {
        this.itemList = itemList;
        mRecyclerView = recyclerView;
        this.mContext = mContext;
    }

    public void onPressCategory(String name){
        getFilter().filter(name);
        Toast.makeText(mContext, "Category:" + name, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,
                parent, false);
        RecyclerView.ViewHolder holder = new CategoryViewHolder(v); // use CategoryViewHolder as default
        Log.i("Info", "View Type is: "+ viewType);

        if(itemList.get(0) instanceof CategoryItem) {
            // If item list is of type Category then use Category viewHolder
            v.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                    CategoryItem item = (CategoryItem)itemList.get(itemPosition);
                    onPressCategory(item.getTitle());

                }
            });
            return new CategoryViewHolder(v);
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

        if(itemList.get(0) instanceof CategoryItem) {
            // If item list is of type Category then use Category viewHolder
            CategoryViewHolder categoryHolder = (CategoryViewHolder) viewHolder;
            CategoryItem currentCategoryItem = (CategoryItem) itemList.get(i);

            categoryHolder.imageView.setImageResource(currentCategoryItem.getImageResource());
            categoryHolder.textView1.setText(currentCategoryItem.getTitle());
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
//            List<? super Item> filteredList = new ArrayList<>();
//
//            for (int i = 0; i < itemList.size(); i++){
//                filteredList.add(itemList.get(i));
//            }
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(itemList);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//
//                for ((? super Item) item : itemList) {
//                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
            List<? super Item> searchList = new ArrayList<>();
            searchList.add(new MovieItem(R.drawable.ic_search, "one", "two"));

            FilterResults results = new FilterResults();
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