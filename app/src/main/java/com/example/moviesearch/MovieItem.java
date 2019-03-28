package com.example.moviesearch;

public class MovieItem extends Item{
    private String description;

    public MovieItem(int imageResource, String title, String description) {
        super(imageResource, title);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
