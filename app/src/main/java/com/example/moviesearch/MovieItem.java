package com.example.moviesearch;

public class MovieItem extends Item{
    private String description;
    private String year;
    private String genre;

    public MovieItem(int imageResource, String title,String year, String description, String genre) {
        super(imageResource, title);
        this.year = year;
        this.description = description;
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }
}
