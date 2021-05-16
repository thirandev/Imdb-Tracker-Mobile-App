package com.w1761940.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RatingsMovie extends AppCompatActivity {

    private ListView listView;
    private Database database;
    private ArrayList<Movie> movieList;
    private Button findBtn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        // Assigning XML Layout views via ids
        listView = findViewById(R.id.imdb_list_view);
        findBtn = findViewById(R.id.find_button);

        // Creating a fresh Database.class object to connect to the SQLite Database
        database = new Database(this);

        // Makes the list view to single selection
        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
        initListViewData();

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInImdb();
            }
        });

    }
    private void initListViewData() {

        // The database class returns movie objects listed
        movieList = database.getAllData();

        ArrayList<String> titles = new ArrayList<>();

        // Adds the movie titles which will be displayed.
        for (int k = 0; k<movieList.size();k++){
            titles.add(movieList.get(k).getTitle());
        }

        // Adapter which assigns titles to the XML list view layout with radio buttons.
        ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, titles);

        this.listView.setAdapter(arrayAdapter);

        for(int i=0;i< movieList.size(); i++ )  {
                this.listView.setItemChecked(i,false);
        }
    }
    private void searchInImdb() {
        // Getting the selected item
        int position = listView.getCheckedItemPosition();
        if (position != -1) {
            String title = movieList.get(position).getTitle();
            //Opens a new Activity to show IMDM search results
            intent = new Intent(this, Imdb.class);
            intent.putExtra("MovieTitle", title);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"Select a Movie",Toast.LENGTH_SHORT).show();
        }
    }
}
