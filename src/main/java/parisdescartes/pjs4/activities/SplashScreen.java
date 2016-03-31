package parisdescartes.pjs4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import parisdescartes.pjs4.R;

/**
 * Created by Kévin on 31/03/2016.
 */
public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, ConnectActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME);

    }
}
