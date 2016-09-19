//Code donated from https://developer.android.com/training/animation/screen-slide.html#views
//and is NOT the original work of the author(s)

package com.example.cmsc355.hungr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class ScreenSlidePageFragment extends Fragment {

    private int position;
    private String restaurantName;
    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        TextView restaurantName = (TextView) rootView.findViewById(R.id.restaurantName);
        ImageView restaurantImage = (ImageView) rootView.findViewById(R.id.restaurantImage);
        restaurantName.setText(this.restaurantName);

        DownloadImageTask setImage = new DownloadImageTask(restaurantImage);
        setImage.execute(url);

        return rootView;
    }
    public void setRestaurantName(String restaurantName){
        this.restaurantName = restaurantName;
    }
    public void setUrl(String url){
        this.url = url;
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
