package com.w1761940.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListEditMovies extends AppCompatActivity {

    private ListView listView;
    private Database database;
    private ArrayList<Movie> movieList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);

        // Assigning XML Layout views via ids
        listView = findViewById(R.id.edit_list_view);

        // Creating a fresh Database.class object to connect to the SQLite Database
        database = new Database(this);

        // The database class returns movie objects listed in alphabetical order
        movieList = database.getAllData();

        // Adds the movie titles which will be displayed.
        ArrayList<String> titles = new ArrayList<>();

        // Adds the movie titles which will be displayed.
        for (int k = 0; k<movieList.size();k++){
            titles.add(movieList.get(k).getTitle());
        }

        // Adapter which assigns titles to the XML list view layout with checkboxes.
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview,R.id.list_view_text_view, titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Open a new activity to edit
                intent = new Intent(ListEditMovies.this, MovieEditor.class);
                intent.putExtra("itemID", movieList.get(position).getId());
                startActivity(intent);
            }
        });
    }
}