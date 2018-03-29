package com.satou.wiki.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.satou.wiki.R;

import butterknife.ButterKnife;

/**
 * Created by Mitsuki on 2018/3/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected EditText searchBar;
    protected LayoutInflater inflater;
    private RelativeLayout contentView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        inflater = LayoutInflater.from(this);
        searchBar = findViewById(R.id.et_search_word);
        swipeRefreshLayout = findViewById(R.id.srl_load);
        swipeRefreshLayout.setEnabled(false);
        contentView = findViewById(R.id.rl_content);
        contentView.removeAllViews();
        contentView.addView(inflater.inflate(getLayout(), null), new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startActivity(Intent intent) {
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    protected abstract int getLayout();

    protected void init() {

    }
}
