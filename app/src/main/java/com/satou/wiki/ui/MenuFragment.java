package com.satou.wiki.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.satou.wiki.R;
import com.satou.wiki.base.BaseFragment;
import com.satou.wiki.constant.TypeCode;
import com.satou.wiki.data.MainPageDataAnalysis;
import com.satou.wiki.data.entity.GameUpdate;
import com.satou.wiki.data.entity.MessageEvent;
import com.satou.wiki.data.entity.WikiLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mitsuki on 2018/3/29.
 */

public class MenuFragment extends BaseFragment {

    private WikiLog wikiLog;
    private GameUpdate gameUpdate;


    @BindView(R.id.tv_wiki_msg)
    TextView wikiMsgTextView;
    @BindView(R.id.tv_wiki_msg_time)
    TextView wikiTimeTextView;
    @BindView(R.id.tv_game_msg)
    TextView gameMsgTextView;
    @BindView(R.id.tv_game_msg_time)
    TextView gameTimeTextView;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void setData(MessageEvent messageEvent) {
        MessageEvent stickyEvent = EventBus.getDefault().removeStickyEvent(MessageEvent.class);
        if (stickyEvent != null) {
            if (stickyEvent.getId() == TypeCode.TOTAL) {
                gameUpdate = MainPageDataAnalysis.getGameUpdate((String) stickyEvent.getContent());
                wikiLog = MainPageDataAnalysis.getWikiMsg((String) stickyEvent.getContent());
                wikiMsgTextView.setText(wikiLog.getMessageLsit().get(0).getMsgContent());
                wikiTimeTextView.setText(wikiLog.getMessageLsit().get(0).getTime());
                gameMsgTextView.setText(gameUpdate.getDate());
                Log.e("===================", wikiLog.getMessageLsit().size() + "");
            }
        }
    }

    @OnClick(R.id.ll_wiki_msg)
    public void gotoWikiMsg() {
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
            if (fragment.getTag().equals("moduleFragment")) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.hide(this);
                fragmentTransaction.show(fragment);
                fragmentTransaction.commit();

                MessageEvent messageEvent = new MessageEvent<WikiLog>();
                messageEvent.setContent(wikiLog);
                messageEvent.setId(TypeCode.WIKILOG);
                EventBus.getDefault().post(messageEvent);
            }
        }
    }

    @OnClick(R.id.ll_game_msg)
    public void gotoGameMsg() {
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
            if (fragment.getTag().equals("moduleFragment")) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.hide(this);
                fragmentTransaction.show(fragment);
                fragmentTransaction.commit();

                MessageEvent messageEvent = new MessageEvent<WikiLog>();
                messageEvent.setContent(gameUpdate);
                messageEvent.setId(TypeCode.GAMEUPDATE);
                EventBus.getDefault().post(messageEvent);
            }
        }
    }


}
