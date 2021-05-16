package com.w1761940.coursework2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;

public class ImdbAdapter extends RecyclerView.Adapter<ImdbAdapter.ViewHolder>{

    private ArrayList<ImdbMovie> movies;
    Context context;
    ImageView image;
    Dialog dialog;

    //Constructor getting IMDB Object List and context
    public ImdbAdapter(ArrayList<ImdbMovie> movies,Context context) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater instance that is already hooked up to the current context and correctly
        // configured for the device you are running on.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_list,parent,false);
        //Add fields to catch IDs
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Getting the position
        final ImdbMovie movieList = movies.get(position);
        //Setting the Views according to its position
        holder.titleText.setText(movieList.getMovieName());
        holder.descriptionText.setText(movieList.getMovieDescription());
        if (movieList.getRating().equals("null")) {
            holder.rating.setText(" ");
        }else {
            holder.rating.setText(movieList.getRating());
        }

        // On click a dialog window appears with the image
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup);
                    ImageView close = dialog.findViewById(R.id.close);
                    TextView title = dialog.findViewById(R.id.dialog_title);
                    TextView description = dialog.findViewById(R.id.dialog_description);
                    description.setText(movieList.getMovieDescription());
                    image = dialog.findViewById(R.id.dialog_image);
                    title.setText(movieList.getMovieName());
                    //Using async class to get the image from url
                    new LoadImage(image)
                            .execute(movieList.getMovieImageSrc());
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        TextView descriptionText;
        TextView rating;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            titleText = itemView.findViewById(R.id.imdb_title_text);
            descriptionText = itemView.findViewById(R.id.imdb_des_text);
            rating = itemView.findViewById(R.id.imdb_rating_text);
        }

    }
    // AsynTask which loads the image from the uri of a imae.
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView bmImage) {
            this.imageView = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(imageUrl).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
