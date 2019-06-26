package com.mitsuki.mosterhunterworldwiki.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.InflateException;
import android.view.MotionEvent;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.zcitc.cloudhousehelper.R;
import com.zcitc.cloudhousehelper.helper.tool.SoftInputHelper;
import com.zcitc.cloudhousehelper.mvp.ui.activity.WebPageActivity;
import org.simple.eventbus.EventBus;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseActivity {
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (useEventBus()) {
                EventBus.getDefault().register(this);
            }

            int layoutResID = initView(savedInstanceState);
            if (layoutResID != 0) {
                setContentView(layoutResID);
                mUnbinder = ButterKnife.bind(this);
            }

        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        mPresenter = getPresenter();
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null)
            mPresenter.onDestroy();

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initTitleBar();
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    public T getPresenter() {
        return null;
    }

    /**********************************************************************************************/
    //隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            SoftInputHelper.hideSoftInput(this);
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private void initTitleBar() {

        if (findViewById(R.id.toolbar) != null) {
            setSupportActionBar(findViewById(R.id.toolbar));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (findViewById(R.id.toolbar_title) != null && !(this instanceof WebPageActivity)) {
            ((TextView) findViewById(R.id.toolbar_title)).setText(getTitle());
        }

        if (findViewById(R.id.toolbar_back) != null) {
            findViewById(R.id.toolbar_back).setOnClickListener(v -> {
                onBackPressed();
            });
        }
    }
}
