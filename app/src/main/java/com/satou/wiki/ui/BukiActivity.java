package com.satou.wiki.ui;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.satou.wiki.R;
import com.satou.wiki.adapter.BukiAdapter;
import com.satou.wiki.adapter.ModuleListAdapter;
import com.satou.wiki.base.BaseActivity;
import com.satou.wiki.constant.Address;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.DetailPageDataAnalysis;
import com.satou.wiki.data.entity.Buki;
import com.satou.wiki.data.entity.MessageEvent;
import com.satou.wiki.data.entity.Unit;
import com.satou.wiki.http.RequestHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mitsuki on 2018/4/18.
 */

public class BukiActivity extends BaseActivity {

    private Unit unit;
    @BindView(R.id.lv_data)
    ListView dataView;

    private BukiAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_list_data;
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Override
    protected void init() {
        adapter = new BukiAdapter(this);
        dataView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void loadData(MessageEvent messageEvent) {
        if (messageEvent != null) {
            if (messageEvent.getId() == TypeCode.BUKI) {
                unit = (Unit) messageEvent.getContent();
                searchBar.setText("");
                loadInfo(unit.getUrl());
                EventBus.getDefault().removeAllStickyEvents();
            }
        }
    }

    private void loadInfo(String str) {
        RequestHelper.getInstance().get(Address.URL + str)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> swipeRefreshLayout.setRefreshing(true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("AitemuActivity", "onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<String> stringResponse) {
                        Log.e("AitemuActivity", "onNext");
                        //此处往webview中加入内容
                        showData(DetailPageDataAnalysis.getBuki(stringResponse.body()));
                        setContentVisibility(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("AitemuActivity", "onError");
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(BukiActivity.this, "请求失败，下拉刷新重试", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        Log.e("DetailActivity", "onComplete");
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(false);
                    }
                });
    }

    private void showData(Buki buki) {
        if (buki != null) {
            adapter.setBuki(buki);
        }
    }

    @Override
    protected boolean setSearchVisibility() {
        return false;
    }

}
