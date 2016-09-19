//Code donated from https://developer.android.com/training/animation/screen-slide.html#views
//and is NOT the original work of the author(s)

package com.example.cmsc355.hungr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import org.json.JSONArray;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 19;
    private JSONArray masterArray = new JSONArray();
    private int counter = 0;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            try {
                masterArray = new JSONArray(extras.getString("getData"));
                System.out.println(masterArray.toString(4));
            }catch(Exception e){}
        }
        else System.out.println("It's null!");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment newPane = new ScreenSlidePageFragment();
            try {
                String restaurantName = masterArray.getJSONObject(counter).get("name").toString();
                String url = masterArray.getJSONObject(counter).get("image_url").toString();
                newPane.setRestaurantName(restaurantName);
                newPane.setUrl(url);
            }catch(Exception e){ e.printStackTrace(); }
            finally{ increaseCounter(); }
            return newPane;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
        public void increaseCounter(){
            counter++;
        }
    }
}