package com.satou.wiki.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.satou.wiki.R;

/**
 * Created by Mitsuki on 2018/3/29.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(() ->
                startActivity(new Intent(this, MainActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                , 2000);
    }
}
