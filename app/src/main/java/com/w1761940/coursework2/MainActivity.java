package com.w1761940.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchRegisterMovie(View view) {
        intent = new Intent(this,RegisterMovies.class);
        startActivity(intent);
    }

    public void launchDisplayMovies(View view) {
        intent = new Intent(this,DisplayMovies.class);
        startActivity(intent);
    }

    public void launchFavorites(View view) {
        intent = new Intent(this, FavouriteMovies.class);
        startActivity(intent);
    }

    public void launchEditMovies(View view) {
        intent = new Intent(this, ListEditMovies.class);
        startActivity(intent);
    }

    public void launchSearch(View view) {
        intent = new Intent(this, SearchMovies.class);
        startActivity(intent);
    }

    public void launchRatings(View view) {
        intent = new Intent(this, RatingsMovie.class);
        startActivity(intent);
    }
}