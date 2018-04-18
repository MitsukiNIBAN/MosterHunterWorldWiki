package com.satou.wiki.ui;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.satou.wiki.R;
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

public class BukiActivity extends BaseActivity{

    private Unit unit;

    @BindView(R.id.tv_name)
    TextView nameView;
    @BindView(R.id.tv_rare)
    TextView rareView;
    @BindView(R.id.tv_set)
    TextView setView;
    @BindView(R.id.tv_attack)
    TextView attackView;
    @BindView(R.id.tv_crit)
    TextView critView;
    @BindView(R.id.tv_defense)
    TextView defenseView;
    @BindView(R.id.tv_attr)
    TextView attrView;
    @BindView(R.id.tv_sharpness)
    TextView sharpnessView;
    @BindView(R.id.tv_timbre)
    TextView timbreView;
    @BindView(R.id.tv_bullet)
    TextView bulletView;
    @BindView(R.id.tv_bug)
    TextView bugView;
    @BindView(R.id.tv_bottle)
    TextView bottleView;
    @BindView(R.id.tv_shelling)
    TextView shellingView;
    @BindView(R.id.listview)
    ListView listView;

    @Override
    protected int getLayout() {
        return R.layout.activity_buki;
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void loadData(MessageEvent messageEvent) {
        if (messageEvent != null) {
            if (messageEvent.getId() == TypeCode.BUKI) {
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

    private void showData(Buki buki){

    }

    @Override
    protected boolean setSearchVisibility() {
        return false;
    }

}
