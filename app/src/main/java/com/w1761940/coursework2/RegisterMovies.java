package com.w1761940.coursework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterMovies extends AppCompatActivity {

    private EditText movieTitleText;
    private EditText yearText;
    private EditText directorText;
    private EditText castText;
    private EditText ratingText;
    private EditText reviewText;
    private Database sqlDatabase;

    private String title, director, cast, review;
    private int year, rating;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movies);

        // Assigning XML Layout views via ids
        movieTitleText = findViewById(R.id.title_edit_text);
        yearText = findViewById(R.id.year_edit_text);
        directorText = findViewById(R.id.director_edit_text);
        castText = findViewById(R.id.cast_edit_text);
        ratingText = findViewById(R.id.rating_edit_text);
        reviewText = findViewById(R.id.review_edit_text);

        // Creating a fresh Database.class object to connect to the SQLite Database
        sqlDatabase = new Database(this);

    }

    public void onClickRegister(View view) {

        Boolean inputError = true; // Assign false for year & rating errors
        String errorMsg = ""; // Store Error Message

        //Assigning the user entered values.
        title = movieTitleText.getText().toString();
        director = directorText.getText().toString();
        cast = castText.getText().toString();
        review = reviewText.getText().toString();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // To check if input fields are missing
        if (!title.isEmpty() && !yearText.getText().toString().isEmpty()
                && !director.isEmpty() && !cast.isEmpty() && !review.isEmpty()
                && !ratingText.getText().toString().isEmpty()) {

            year = Integer.parseInt(yearText.getText().toString());
            rating = Integer.parseInt(ratingText.getText().toString());

            //  Assigning a Movie object to reference variable
            movie = new Movie(title, director, cast, review, year, rating);

            if (!(year > 1895 && year <= currentYear)) {
                inputError = false;
                errorMsg = "Error - Enter Year between : 1895 to " + currentYear;

            } else if (!(rating >= 1 && rating <= 10)) {
                inputError = false;
                errorMsg = "Error - Enter Rating between : 1 to 10";
            }

            if (inputError) {

                // Storing user input data to the SQLite database
                try {
                    sqlDatabase.insertData(movie);
                    Toast.makeText(getApplicationContext(),"Data saved Successfully",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    runOnUiThread(new Runnable(){
                        public void run() {
                            Toast.makeText(getApplicationContext(), "SQLite Error",Toast.LENGTH_LONG).show();
                        }
                    });
                }

                // Clearing Input Text fields
                movieTitleText.setText("");
                yearText.setText("");
                directorText.setText("");
                castText.setText("");
                ratingText.setText("");
                reviewText.setText("");

                movieTitleText.clearFocus();
                yearText.clearFocus();
                directorText.clearFocus();
                castText.clearFocus();
                ratingText.clearFocus();
                reviewText.clearFocus();

            } else {
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Fields are Missing", Toast.LENGTH_SHORT).show();
        }
    }

    // Invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title" ,movieTitleText.getText().toString());
        outState.putString("director" ,directorText.getText().toString());
        outState.putString("cast" ,castText.getText().toString());
        outState.putString("review" ,reviewText.getText().toString());
    }

    // Restore values saved from the onSaveInstanceState method
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        title = savedInstanceState.getString("title");
        director = savedInstanceState.getString("director");
        cast = savedInstanceState.getString("cast");
        review = savedInstanceState.getString("review");

        movieTitleText.setText(title);
        directorText.setText(director);
        castText.setText(cast);
        reviewText.setText(review);
    }

}