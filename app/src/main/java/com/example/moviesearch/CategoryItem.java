package com.example.moviesearch;

public class CategoryItem {
    private int imageResource;
    private String title;

    public CategoryItem(int imageResource, String title) {
        this.imageResource = imageResource;
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }
    
}
