package com.w1761940.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayMovies extends AppCompatActivity {

    private ListView listView;
    private Button saveBtn;
    private Database database;
    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        // Assigning XML Layout views via ids
        listView = findViewById(R.id.display_list_view);
        saveBtn = findViewById(R.id.add_to_fav_button);

        // Creating a fresh Database.class object to connect to the SQLite Database
        database = new Database(this);

        // Makes the list view to select multiple items
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        initListData();
    }

    private void initListData() {

        try {
            // The database class returns movie objects listed in alphabetical order
            movieList = database.getAllData();
        }catch (Exception e){
            runOnUiThread(new Runnable(){
                public void run() {
                    Toast.makeText(getApplicationContext(), "SQLite Error",Toast.LENGTH_LONG).show();
                }
            });
        }

        ArrayList<String> titles = new ArrayList<>();

        // Adds the movie titles which will be displayed.
        for (int k = 0; k<movieList.size();k++){
            titles.add(movieList.get(k).getTitle());
        }

        // Adapter which assigns titles to the XML list view layout with checkboxes.
        ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, titles);

        this.listView.setAdapter(arrayAdapter); // Setting the adapter

        // Loop which makes all checkboxes unchecked.
        for(int i=0;i< movieList.size(); i++ )  {
            this.listView.setItemChecked(i,false);
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeSelectedItems();
            }
        });
    }

    private void storeSelectedItems() {
        Boolean isChecked = false;

        // SparseBooleanArrays map integers to booleans
        SparseBooleanArray sp = listView.getCheckedItemPositions();

        // Add checked movies to favorites
        for (int i=0;i<sp.size();i++){
            if (sp.get(i)){
                movieList.get(i).setSelected(1);
                isChecked = true;
            }else {
                movieList.get(i).setSelected(0);
            }
        }

        if (isChecked) {
            // Storing the List into the SQLite database.
            database.setFavourite(movieList);
            finish();
        }else if(sp.size() == 0){
            Toast.makeText(getApplicationContext(),"Empty List",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"Select a Movie",Toast.LENGTH_SHORT).show();
        }

    }

}