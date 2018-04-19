package com.satou.wiki.ui;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.satou.wiki.R;
import com.satou.wiki.base.BaseActivity;
import com.satou.wiki.constant.Address;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.DetailPageDataAnalysis;
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
 * Created by Mitsuki on 2018/4/3.
 */

public class DetailActivity extends BaseActivity {

    private Unit unit;
    @BindView(R.id.tv_content)
    TextView content;

    @Override
    protected void init() {
        searchBar.setVisibility(View.GONE);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void loadData(MessageEvent messageEvent) {
        if (messageEvent != null) {
            if (messageEvent.getId() == TypeCode.DETAIL) {
                unit = (Unit) messageEvent.getContent();
                searchBar.setText("");
//                Log.e("unit", unit.toString());
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
                        Log.e("DetailActivity", "onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<String> stringResponse) {
                        Log.e("DetailActivity", "onNext");
                        //此处往webview中加入内容
//                        Log.e("sadfa", DetailPageDataAnalysis.getDetail(stringResponse.body()));stringResponse
//                        webView.loadData(DetailPageDataAnalysis.getDetail(stringResponse.body()),
//                                "text/html", "utf-8");
                        content.setText(Html.fromHtml(DetailPageDataAnalysis.getDetail(stringResponse.body())));

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("DetailActivity", "onError");
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(DetailActivity.this, "请求失败，下拉刷新重试", Toast.LENGTH_SHORT).show();
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
}
