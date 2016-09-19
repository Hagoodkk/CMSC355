package com.example.cmsc355.hungr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity{

    //Master array that will be used to pass information through intents
    private JSONArray masterArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Execute background task to get information from API when button pressed
        Button getFoodButton = (Button) findViewById(R.id.getFoodButton);
        getFoodButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                pingYelpAPI apiResponse = new pingYelpAPI();
                apiResponse.execute();
            }
        });


        Button testSliderButton = (Button) findViewById(R.id.testButton);
        testSliderButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent newIntent = new Intent(getApplicationContext(), ScreenSlidePagerActivity.class);
                newIntent.putExtra("getData", masterArray.toString());
                startActivity(newIntent);
            }
        });

    }



    private class pingYelpAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String apiResponse;

            //Reference Yelp API sample code
            YelpAPI apiCall = new YelpAPI("4gMlTiNc9lXpqP9YIwmbJg",
                    "yKuJ3AV6pkw4ITB4WBfHuZq-JL0",
                    "Eeiax8f5iIYnh6IMWmHLVTKYWSGATfr1",
                    "FtZpcHv3NFy3gKd-XxvvSDmN3Gk");

            //Call Yelp's API ping method and save resulting String
            apiResponse = apiCall.searchForBusinessesByLocation("food", "Richmond, VA");
            try{

                //Parse important parts of JSON object into JSON array,
                //then save in masterArray
                JSONObject apiResponseJSON = new JSONObject(apiResponse);
                JSONArray businessJSONArray = apiResponseJSON.getJSONArray("businesses");
                masterArray = businessJSONArray;

            }

            //Fail 1 so we know which part failed if it occurs
            catch(Exception e){
                System.out.println("Fail 1");
            }

            //Print out JSON Array with code 4 toString for easy viewing of format used
            try {
                System.out.println(masterArray.toString(4));
            }catch(Exception e){}

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Ensure the masterArray is filled properly, for debugging purposes
            try {
                String printStr = masterArray.getJSONObject(0).get("name").toString();
                System.out.println(printStr);
            }catch(Exception e){}

            //Activate the main button after API has been pinged, and masterArray filled

            Intent restaurantSuggestionIntent = new Intent(getApplicationContext(), ScreenSlidePagerActivity.class);
            restaurantSuggestionIntent.putExtra("getData", masterArray.toString());
            startActivity(restaurantSuggestionIntent);
        }
    }
}



