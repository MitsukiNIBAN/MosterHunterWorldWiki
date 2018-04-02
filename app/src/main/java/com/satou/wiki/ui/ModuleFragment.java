package com.satou.wiki.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.satou.wiki.R;
import com.satou.wiki.adapter.ModuleListAdapter;
import com.satou.wiki.base.BaseFragment;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.entity.MessageEvent;
import com.satou.wiki.data.entity.Unit;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mitsuki on 2018/3/29.
 */

public class ModuleFragment extends BaseFragment {

    private ModuleListAdapter moduleListAdapter;

    @BindView(R.id.lv_data)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        moduleListAdapter = new ModuleListAdapter(getActivity());
        listView.setAdapter(moduleListAdapter);
        return view;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_module;
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setData(MessageEvent messageEvent){
        if (messageEvent.getId() == TypeCode.WIKILOG){
            List<Unit> wikiLog = (List<Unit>) messageEvent.getContent();
            moduleListAdapter.refreshData(wikiLog);
        }else if (messageEvent.getId() == TypeCode.GAMEUPDATE){
            Unit gameUpdate = (Unit) messageEvent.getContent();
            moduleListAdapter.refreshData(gameUpdate);
        }
    }


    @OnClick(R.id.tv_back)
    public void back() {
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()){
            if (fragment.getTag().equals("menuFragment")){
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.hide(this);
                fragmentTransaction.show(fragment);
                fragmentTransaction.commit();
            }
        }
    }

}
