package com.satou.wiki.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import com.satou.wiki.R;
import com.satou.wiki.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
//        Transition fade = TransitionInflater.from(this).inflateTransition(android.R.transition.fade);
//        getWindow().setEnterTransition(fade);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

}
