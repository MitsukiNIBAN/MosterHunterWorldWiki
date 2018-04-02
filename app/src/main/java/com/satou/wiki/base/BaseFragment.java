package com.satou.wiki.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Mitsuki on 2018/3/29.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    protected LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, view);//绑定framgent
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (registerEventBus())
            EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (registerEventBus())
            EventBus.getDefault().unregister(this);
    }

    public abstract int getContentViewId();

    protected abstract boolean registerEventBus();
}
