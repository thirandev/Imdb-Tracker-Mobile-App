package com.w1761940.coursework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchMovies extends AppCompatActivity {

    private ListView listView;
    private Database database;
    private ArrayList<Movie> movieList;
    private Button lookupBtn;
    private EditText searchText;

    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Assigning XML Layout views via ids
        searchText = findViewById(R.id.search_edit_text);
        lookupBtn = findViewById(R.id.lookup_button);
        listView = findViewById(R.id.search_list_view);

        // Creating a fresh Database.class object to connect to the SQLite Database
        database = new Database(this);

        lookupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initListViewData();
            }
        });
    }
    private void initListViewData() {
        // Assign the keyword
        keyword = searchText.getText().toString().toLowerCase();

        if (!keyword.isEmpty()) {
            movieList = database.getSearchData(keyword);

            ArrayList<String> titles = new ArrayList<>();

            //Makes the content to be displayed after search
            for (int k = 0; k < movieList.size(); k++) {
                titles.add("TITLE          -  " + movieList.get(k).getTitle() + "\nDIRECTOR -  " + movieList.get(k).getDirector() +
                        "\nCAST          -  " + movieList.get(k).getCast());
            }

            // Adapter which assigns titles to the XML list view layout
            ArrayAdapter<String> arrayAdapter
                    = new ArrayAdapter<String>(this, R.layout.activity_listview2, R.id.list_view_two_text_view, titles);

            this.listView.setAdapter(arrayAdapter);

            for (int i = 0; i < movieList.size(); i++) {
                this.listView.setItemChecked(i, false);
            }
            if (movieList.size() == 0) {
                Toast.makeText(this, "Empty Search Result", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please Enter Keyword", Toast.LENGTH_SHORT).show();
        }
    }

    // Invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("keyword" ,searchText.getText().toString());
    }

    // Restore values saved from the onSaveInstanceState method
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        keyword = savedInstanceState.getString("keyword");
        searchText.setText(keyword);
        initListViewData();
    }
}