package com.example.moviesearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,
                parent, false);
        RecyclerView.ViewHolder holder = new CategoryViewHolder(v); // use CategoryViewHolder as default
        Log.i("Info", "View Type is: "+ viewType);

        if(itemList.get(0) instanceof CategoryItem) {
            v.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                    CategoryItem item = (CategoryItem)itemList.get(itemPosition);
                    Toast.makeText(mContext, "Category:" + item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            return new CategoryViewHolder(v);
        } else {

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
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.i("Info", "Item view type (2): " + viewHolder.getItemViewType());
        if(itemList.get(0) instanceof CategoryItem) {
            CategoryViewHolder categoryHolder = (CategoryViewHolder) viewHolder;
            CategoryItem currentCategoryItem = (CategoryItem) itemList.get(i);

            categoryHolder.imageView.setImageResource(currentCategoryItem.getImageResource());
            categoryHolder.textView1.setText(currentCategoryItem.getTitle());

        } else {
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
            List<? super Item> filteredList = new ArrayList<>();

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

            FilterResults results = new FilterResults();
            results.values = itemList;

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