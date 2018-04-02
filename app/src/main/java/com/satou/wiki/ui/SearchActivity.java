package com.satou.wiki.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.satou.wiki.R;
import com.satou.wiki.adapter.ModuleListAdapter;
import com.satou.wiki.base.BaseActivity;
import com.satou.wiki.constant.Address;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.MainPageDataAnalysis;
import com.satou.wiki.data.SearchPageDataAnalysis;
import com.satou.wiki.data.entity.MessageEvent;
import com.satou.wiki.http.RequestHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mitsuki on 2018/4/2.
 */

public class SearchActivity extends BaseActivity {

    @BindView(R.id.lv_data)
    ListView listView;
    @BindView(R.id.tv_empty)
    TextView emptyView;

    private String keyword;

    private ModuleListAdapter adapter;

    @Override
    protected void init() {
        adapter = new ModuleListAdapter(this);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @OnClick(R.id.tv_back)
    public void close() {
        finishAfterTransition();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void loadData(MessageEvent messageEvent) {
        if (messageEvent != null) {
            if (messageEvent.getId() == TypeCode.SEARCHKEYWORD) {
                String kw = (String) messageEvent.getContent();
                searchBar.setText(kw);
                search(kw);
                EventBus.getDefault().removeAllStickyEvents();
            }
        }
    }

    @Override
    protected void doSomething() {
        String str = searchBar.getText().toString() + "";
        if (str.length() > 0) {
            keyword = str;
            search(str);
        }
    }

    private void search(String str) {
        RequestHelper.getInstance().get(Address.URL + "search?q=" + str)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> swipeRefreshLayout.setRefreshing(true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("MainActivity", "onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<String> stringResponse) {
                        Log.e("MainActivity", "onNext");
                        adapter.refreshData(SearchPageDataAnalysis
                                .getSearchData(stringResponse.body()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "onError");
                        swipeRefreshLayout.setRefreshing(false);
                        emptyView.setText("请求失败，下拉刷新重试");
                        swipeRefreshLayout.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        Log.e("MainActivity", "onComplete");
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(false);
                        emptyView.setText("没有任何结果");
                    }
                });
    }

    @Override
    protected void onRefresh() {
        search(keyword);
    }
}
