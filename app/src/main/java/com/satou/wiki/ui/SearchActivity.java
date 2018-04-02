package com.satou.wiki.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.satou.wiki.R;
import com.satou.wiki.adapter.ModuleListAdapter;
import com.satou.wiki.base.BaseActivity;
import com.satou.wiki.constant.Address;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.entity.MessageEvent;
import com.satou.wiki.http.RequestHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
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

    private ModuleListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ModuleListAdapter(this);
        listView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void startSearch(MessageEvent messageEvent) {
        MessageEvent stickyEvent = EventBus.getDefault().removeStickyEvent(MessageEvent.class);
        if (stickyEvent != null) {
            if (stickyEvent.getId() == TypeCode.SEARCHKEYWORD) {
                String kw = stickyEvent.getContent() + "";
                if (kw.length() <= 0)
                    return;
                RequestHelper.getInstance().get(Address.URL + "search?q=" +kw)
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

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("MainActivity", "onError");
                                Toast.makeText(getApplicationContext(), "请求失败，请重试", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onComplete() {
                                Log.e("MainActivity", "onComplete");
                                swipeRefreshLayout.setRefreshing(false);
                                swipeRefreshLayout.setEnabled(false);

                            }
                        });
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }
}
