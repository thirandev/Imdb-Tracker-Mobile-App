package com.w1761940.coursework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.Calendar;

public class MovieEditor extends AppCompatActivity {

    private EditText movieTitleText;
    private EditText yearText;
    private EditText directorText;
    private EditText castText;
    private RatingBar ratingBar;
    private EditText reviewText;
    private Button saveBtn;
    private RadioGroup radioGroup;
    private RadioButton favRadioButton;

    private Database sqlDatabase;
    private String title,director,cast,review;
    private int year,rating;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Assigning XML Layout views via ids
        movieTitleText = findViewById(R.id.title_editor_text);
        yearText = findViewById(R.id.year_editor_text);
        directorText = findViewById(R.id.director_editor_text);
        castText = findViewById(R.id.cast_editor_text);
        ratingBar = findViewById(R.id.rating_bar);
        reviewText = findViewById(R.id.review_editor_text);
        saveBtn = findViewById(R.id.save_edit_button);
        radioGroup = findViewById(R.id.editor_radio);
        favRadioButton = findViewById(R.id.fav_radio);

        // Creating a fresh Database.class object to connect to the SQLite Database
        sqlDatabase =new Database(this);

        getEditorData();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMovieData();
            }
        });

    }

    private void saveMovieData() {

        Boolean inputError = true; // Assign false for year & rating errors


        // Setting the user input to the specific variables
        title = movieTitleText.getText().toString();
        director = directorText.getText().toString();
        cast = castText.getText().toString();
        review = reviewText.getText().toString();
        rating = (int) ratingBar.getRating();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // To check if input fields are missing
        if (!title.isEmpty() && !yearText.getText().toString().isEmpty()
                && !director.isEmpty() && !cast.isEmpty() && !review.isEmpty()
                && rating != 0) {
            year = Integer.parseInt(yearText.getText().toString());

            //  Assigning a Movie object to reference variable
            Movie m = new Movie(title, director, cast, review, year, rating);

            if (!(year > 1895 && year <= currentYear)) {
                inputError = false;
            }
            if (favRadioButton.isChecked()) {
                m.setSelected(1);
            }
            if (inputError) {
                m.setId(movie.getId());
                try {
                    sqlDatabase.updateMovie(m);
                }catch (Exception e){
                    runOnUiThread(new Runnable(){
                        public void run() {
                            Toast.makeText(getApplicationContext(), "SQLite Error",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                Toast.makeText(getApplicationContext(),"Data saved Succesully",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                String errorMsg = "Error - Enter Year between : 1895 to " + currentYear;
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Fields are Missing", Toast.LENGTH_SHORT).show();
        }

    }

    public void getEditorData(){
        // Storing the movie id selected from ListEditMovies activity
        int movieId = getIntent().getExtras().getInt("itemID");

        // The database class returns movie object of the specified ID
        movie = sqlDatabase.getMovieData(movieId);

        // Assign the values accordingly to the saved data.
        movieTitleText.setText(movie.getTitle());
        yearText.setText(String.valueOf(movie.getYear()));
        directorText.setText(movie.getDirector());
        castText.setText(movie.getCast());
        ratingBar.setRating(movie.getRating());
        reviewText.setText(movie.getReview());

        // Checks if it is favourite movie
        if (movie.getSelected() == 1){
            radioGroup.check(R.id.fav_radio);
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