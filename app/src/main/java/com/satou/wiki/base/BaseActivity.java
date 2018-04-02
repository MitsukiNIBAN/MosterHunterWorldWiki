package com.satou.wiki.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.satou.wiki.R;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.entity.MessageEvent;
import com.satou.wiki.ui.SearchActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Mitsuki on 2018/3/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected EditText searchBar;
    protected LayoutInflater inflater;
    private RelativeLayout contentView;
    protected SwipeRefreshLayout swipeRefreshLayout;

    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getInstance().addActivity(this);
        super.setContentView(R.layout.activity_base);
        inflater = LayoutInflater.from(this);
        searchBar = findViewById(R.id.et_search_word);
        searchBar.setOnKeyListener((view, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                doSomething();
            }
            return false;
        });
        swipeRefreshLayout = findViewById(R.id.srl_load);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(() -> onRefresh());
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
        searchBar.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (registerEventBus())
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (registerEventBus())
            EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose();
    }

    @Override
    public void startActivity(Intent intent) {
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    protected abstract int getLayout();

    protected abstract boolean registerEventBus();

    protected void init() {

    }

    protected void doSomething(){

    };

    protected void onRefresh() {

    }
}
