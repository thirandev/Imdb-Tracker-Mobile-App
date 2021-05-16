package com.w1761940.coursework2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Imdb extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ImdbMovie> movies = new ArrayList<>();
    private ImdbAdapter imdbAdapter;
    private TextView keywordText;
    private Thread thread;
    private ImdbMovie imdbMovie;
    private String keyword;
    JSONObject object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdb);

        //Getting the keyword from the intent
        keyword = getIntent().getExtras().getString("MovieTitle");

        // Assigning XML Layout views via ids
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        keywordText = (TextView) findViewById(R.id.imdb_search_text_view);
        keywordText.setText("Keyword : " + keyword);

        // Running a new thread to fetch data from the api
        thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    getData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        imdbAdapter = new ImdbAdapter(movies, Imdb.this);
        recyclerView.setAdapter(imdbAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getData() {
        try {
            // Api Keys :{k_g3ck28sk,k_3jyhq68y,k_2bi02w2n,k_vhko37ti}
            // Uniform Resource Locator, a pointer to a "api" on www.
            URL url = new URL("https://imdb-api.com/en/API/SearchTitle/k_vhko37ti/" + keyword);
            //Request a URI
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                object = new JSONObject(inputLine);
            }
            in.close();
            // Getting data as a json array
            JSONArray jsonArray = object.getJSONArray("results");
            JSONObject jsonObject;
            movies = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                // Converting json array data into json objects
                jsonObject = new JSONObject(jsonArray.get(i).toString());
                imdbMovie = new ImdbMovie(jsonObject.getString("id"),
                        jsonObject.getString("title"),
                        jsonObject.getString("description"),
                        jsonObject.getString("image"));
                imdbMovie.setRating(getRating(jsonObject.getString("id")));
                movies.add(imdbMovie);
            }
            // Runs the ui thread and update the list view items
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    imdbAdapter = new ImdbAdapter(movies, Imdb.this);
                    recyclerView.setAdapter(imdbAdapter);
                }
            });


        } catch (IOException | JSONException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable(){
                public void run() {
                    Toast.makeText(getApplicationContext(), "Connection Error",
                            Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    public String getRating(String id) {
        String totalRating = "";
        try {
            // Api Keys :{k_g3ck28sk,k_3jyhq68y,k_2bi02w2n,k_vhko37ti}
            URL url = new URL("https://imdb-api.com/en/API/UserRatings/k_vhko37ti/" + id);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                object = new JSONObject(inputLine);
            }
            in.close();
            totalRating = object.getString("totalRating");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable(){
                public void run() {
                    Toast.makeText(getApplicationContext(), "Connection Error",
                            Toast.LENGTH_LONG).show();
                }
            });

        }
        return totalRating;
    }
}