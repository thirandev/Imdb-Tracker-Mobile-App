package com.w1761940.coursework2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MoviesDB";
    private static final String DATABASE_TABLE = "movies";
    private static final int DATABASE_VERSION = 1;

    private int id;
    private String title;
    private int year;
    private String director;
    private String cast;
    private int rating;
    private String review;
    private int fav;
    private Movie movie;

    private Context context;
    private SQLiteDatabase liteDatabase;

    // Database Construct which sets the context
    public Database(Context ct) {
        super(ct, DATABASE_NAME, null, DATABASE_VERSION);
        context = ct;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating a SQLite database table
        db.execSQL("create table " + DATABASE_TABLE + " (_id integer primary key autoincrement," +
                "mov_name text,mov_year integer,mov_dir text,mov_cast text,mov_rating integer," +
                "mov_review text,mov_fav integer);");
        Log.i("Database", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Dropping a table when creating if it exists.
        db.execSQL("drop table if exists " + DATABASE_TABLE);
        onCreate(liteDatabase);
    }

    public void insertData(Movie movie) {
        //Writing to the Database
        liteDatabase = getWritableDatabase();

        //Inserting data in the movie table using a sql query
        liteDatabase.execSQL("insert into " + DATABASE_TABLE + " (mov_name,mov_year,mov_dir," +
                "mov_cast,mov_rating,mov_review,mov_fav)" +
                " values('" + movie.getTitle() + "','" + movie.getYear() + "','" + movie.getDirector() +
                "','" + movie.getCast()
                + "','" + movie.getRating() + "','" + movie.getReview() + "','" + movie.getSelected() + "');");

    }

    public ArrayList<Movie> getAllData() {
        // Making the connection for getting data
        liteDatabase = getReadableDatabase();

        // Fetching all the data in alphabetical order
        Cursor cursor = liteDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " ORDER BY mov_name ASC", null);
        ArrayList<Movie> list = new ArrayList<>();

        // Creating a movie object with the fetched variables and adding to a Movie Object List
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            title = cursor.getString(1);
            year = cursor.getInt(2);
            director = cursor.getString(3);
            cast = cursor.getString(4);
            rating = cursor.getInt(5);
            review = cursor.getString(6);
            fav = cursor.getInt(7);
            movie = new Movie(title, director, cast, review, year, rating);
            movie.setId(id);
            movie.setSelected(fav);
            list.add(movie);
        }
        return list;
    }

    public void setFavourite(ArrayList<Movie> selectedList) {
        //Writing to the Database
        liteDatabase = getWritableDatabase();

        for (int i = 0; i < selectedList.size(); i++) {
            String idString = String.valueOf(selectedList.get(i).getId());
            String value = String.valueOf(selectedList.get(i).getSelected());
            liteDatabase.execSQL("UPDATE movies SET mov_fav = " + value + " WHERE _id =" + idString);
        }
    }

    public ArrayList<Movie> getFavourite() {
        // Making the connection for getting data
        liteDatabase = getReadableDatabase();

        // Fetching all stored data from database
        Cursor cursor = liteDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE mov_fav = 1", null);
        ArrayList<Movie> list = new ArrayList<>();

        //Getting only the Favorite Movies from the database
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            String title = cursor.getString(1);
            year = cursor.getInt(2);
            director = cursor.getString(3);
            cast = cursor.getString(4);
            rating = cursor.getInt(5);
            review = cursor.getString(6);
            fav = cursor.getInt(7);
            movie = new Movie(title, director, cast, review, year, rating);
            movie.setId(id);
            movie.setSelected(fav);
            list.add(movie);
        }
        return list;
    }

    public Movie getMovieData(int movieId) {

        // Making the connection for getting data
        liteDatabase = getReadableDatabase();

        //Fetching data by id from a data base
        Cursor cursor = liteDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE _id = " + movieId, null);
        Movie movie = null;

        //Assigning fetched data to the data which will be just a single movie object
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            int year = cursor.getInt(2);
            String director = cursor.getString(3);
            String cast = cursor.getString(4);
            int rating = cursor.getInt(5);
            String review = cursor.getString(6);
            int fav = cursor.getInt(7);
            movie = new Movie(title, director, cast, review, year, rating);
            movie.setId(id);
            movie.setSelected(fav);

        }
        return movie;
    }

    public void updateMovie(Movie movie) {
        //Writing to the Database
        liteDatabase = getWritableDatabase();
        id = movie.getId();
        title = movie.getTitle();
        fav = movie.getSelected();
        year = movie.getYear();
        director = movie.getDirector();
        cast = movie.getCast();
        review = movie.getReview();
        rating = movie.getRating();

        //Updating the database after the user edits the fields from the UI
        liteDatabase.execSQL("UPDATE movies SET mov_name = '" + title +
                "',mov_fav = " + fav + ",mov_year = " +
                year + ",mov_dir = '" + director + "',mov_cast = '" + cast + "',mov_review = '" + review +
                "',mov_rating = " + rating + " WHERE _id = " + id);
    }

    public ArrayList<Movie> getSearchData(String keyword) {
        // Making the connection for getting data
        liteDatabase = getReadableDatabase();
        Cursor cursor = liteDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " ORDER BY mov_name ASC", null);
        ArrayList<Movie> list = new ArrayList<>();
        Boolean contains;

        // Search for movies within the database to match the keyword the user entered.
        while (cursor.moveToNext()) {
            contains = cursor.getString(1).toLowerCase().contains(keyword) ||
                    cursor.getString(3).toLowerCase().contains(keyword) ||
                    cursor.getString(4).toLowerCase().contains(keyword);
            //if only the keyword is contains in the database it is added to the movie object list
            if (contains) {
                id = cursor.getInt(0);
                title = cursor.getString(1);
                year = cursor.getInt(2);
                director = cursor.getString(3);
                cast = cursor.getString(4);
                rating = cursor.getInt(5);
                review = cursor.getString(6);
                fav = cursor.getInt(7);
                movie = new Movie(title, director, cast, review, year, rating);
                movie.setId(id);
                movie.setSelected(fav);
                list.add(movie);
            }
        }
        return list;
    }
}
