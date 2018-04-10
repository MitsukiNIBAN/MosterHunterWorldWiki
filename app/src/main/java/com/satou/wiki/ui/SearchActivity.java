package com.satou.wiki.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.satou.wiki.R;
import com.satou.wiki.adapter.ModuleListAdapter;
import com.satou.wiki.base.Application;
import com.satou.wiki.base.BaseActivity;
import com.satou.wiki.constant.Address;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.MainPageDataAnalysis;
import com.satou.wiki.data.SearchPageDataAnalysis;
import com.satou.wiki.data.entity.MessageEvent;
import com.satou.wiki.data.entity.Unit;
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
    private long exitTime = 0;

    @Override
    protected void init() {
        adapter = new ModuleListAdapter(this);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Unit unit = (Unit) adapter.getItem(i);
            if (unit.getUrl().equals("null")) return;
            MessageEvent uM = new MessageEvent<String>();
            uM.setId(TypeCode.DETAIL);
            uM.setContent(unit);
            EventBus.getDefault().postSticky(uM);

            Intent intent = new Intent();
            intent.setClass(this, DetailActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean registerEventBus() {
        return false;
    }

//    @OnClick(R.id.tv_back)
//    public void close() {
//        finishAfterTransition();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void loadData(MessageEvent messageEvent) {
//        if (messageEvent != null) {
//            if (messageEvent.getId() == TypeCode.SEARCHKEYWORD) {
//                String kw = (String) messageEvent.getContent();
//                searchBar.setText(kw);
//                search(kw);
//                EventBus.getDefault().removeAllStickyEvents();
//            }
//        }
//    }

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
                        Log.i("SearchActivity", "onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<String> stringResponse) {
                        Log.i("SearchActivity", "onNext");
                        adapter.refreshData(SearchPageDataAnalysis
                                .getSearchData(stringResponse.body()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("SearchActivity", "onError");
                        swipeRefreshLayout.setRefreshing(false);
                        emptyView.setText("请求失败，下拉刷新重试");
                        swipeRefreshLayout.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("SearchActivity", "onComplete");
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

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再次点击退出应用", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            Application.getInstance().exit();
        }
    }
}
