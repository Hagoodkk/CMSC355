package com.example.cmsc355.hungr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by rizvish on 9/19/16.
 */
public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    Intent openStart = new Intent("com.example.cmsc355.hungr.MAINACTIVITY");
                    startActivity(openStart);

                }
            }
        };
        timer.start();
    }
}
