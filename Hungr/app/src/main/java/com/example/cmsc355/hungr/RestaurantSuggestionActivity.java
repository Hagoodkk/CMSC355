package com.example.cmsc355.hungr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.InputStream;

/*
    Class is only partially complete. Completed class will load all 20 suggestions into ListView
    panes, then support swiping to load new pre-loaded suggestion.
 */
public class RestaurantSuggestionActivity extends AppCompatActivity {

    private JSONArray masterArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_suggestion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get masterArray sent from MainActivity via putExtra command
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            try {
                masterArray = new JSONArray(extras.getString("getData"));
                System.out.println(masterArray.toString(4));
                System.out.println(masterArray.getJSONObject(1).toString(4));
            }catch(Exception e){}
        }

        //Create object references for values to be filled
        ImageView restaurantImage = (ImageView) findViewById(R.id.restaurantImage);
        TextView restaurantNameText = (TextView) findViewById(R.id.restaurantName);

        //Retrieve parameters from masterArray
        String url = null;
        String restaurantName = null;
        try{
            url = masterArray.getJSONObject(19).get("image_url").toString();
            restaurantName = masterArray.getJSONObject(19).get("name").toString();
        }catch(Exception e){}

        //Set the values using parameters from masterArray
        DownloadImageTask restaurantImageGenerator = new DownloadImageTask(restaurantImage);
        restaurantImageGenerator.execute(url);
        restaurantNameText.setText(restaurantName);

    }

    //Class DownloadImageTask donated from http://stackoverflow.com/questions/5776851/load-image-from-url
    //and is NOT the original work of the author(s)
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
