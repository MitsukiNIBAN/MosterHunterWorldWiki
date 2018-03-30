package com.satou.wiki.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.satou.wiki.R;
import com.satou.wiki.base.Application;
import com.satou.wiki.base.BaseActivity;
import com.satou.wiki.constant.Address;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.entity.MessageEvent;
import com.satou.wiki.http.RequestHelper;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private long exitTime = 0;

    private MenuFragment menuFragment;
    private ModuleFragment moduleFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        menuFragment = new MenuFragment();
        moduleFragment = new ModuleFragment();
//        fragmentManager.getFragment()

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!menuFragment.isAdded()) {
            transaction.add(R.id.rl_fragment_container, menuFragment, "menuFragment");
            transaction.hide(menuFragment);
        }
        if (!moduleFragment.isAdded()) {
            transaction.add(R.id.rl_fragment_container, moduleFragment, "moduleFragment");
            transaction.hide(moduleFragment);
        }
        transaction.commit();
        refresh();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean registerEventBus() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!menuFragment.isHidden()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再次点击返回退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Application.getInstance().exit();
            }
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.hide(moduleFragment);
            if (!menuFragment.isAdded())
                transaction.add(R.id.rl_fragment_container, menuFragment);
            transaction.show(menuFragment).commit();
        }
    }

    @Override
    protected void onRefresh() {
        refresh();
    }

    private void refresh() {
        RequestHelper.getInstance().get(Address.URL)
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
                        MessageEvent total = new MessageEvent<String>();
                        total.setId(TypeCode.TOTAL);
                        total.setContent(stringResponse.body());
                        EventBus.getDefault().postSticky(total);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "onError");
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "请求失败，下拉刷新重试", Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        Log.e("MainActivity", "onComplete");
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(false);

                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.hide(moduleFragment);
                        transaction.show(menuFragment).commit();
                    }
                });
    }

}
