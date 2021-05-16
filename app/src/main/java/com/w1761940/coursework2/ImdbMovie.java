package com.w1761940.coursework2;

public class ImdbMovie {

    private String movieId;
    private String movieName;
    private String movieDescription;
    private String movieImageSrc;
    private String rating;

    public ImdbMovie(String movieId, String movieName, String movieDescription, String movieImageSrc) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieImageSrc = movieImageSrc;
        this.rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieImageSrc() {
        return movieImageSrc;
    }

    public void setMovieImageSrc(String movieImageSrc) {
        this.movieImageSrc = movieImageSrc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ImdbMovie{" +
                "movieId='" + movieId + '\'' +
                ", movieName='" + movieName + '\'' +
                ", movieDescription='" + movieDescription + '\'' +
                ", movieImageSrc='" + movieImageSrc + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
