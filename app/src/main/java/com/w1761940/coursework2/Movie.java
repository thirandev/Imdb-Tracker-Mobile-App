package com.w1761940.coursework2;

public class Movie {

    private int id;
    private String title;
    private int selected ;
    private String director;
    private String cast;
    private String review;
    private int year;
    private int rating;

    public Movie(String title,String director, String cast, String review, int year, int rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.selected = 0;
        this.cast = cast;
        this.review = review;
        this.year = year;
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", selected=" + selected +
                ", director='" + director + '\'' +
                ", cast='" + cast + '\'' +
                ", review='" + review + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                '}';
    }
}
