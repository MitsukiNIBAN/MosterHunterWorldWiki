package com.mitsuki.mosterhunterworldwiki.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public interface IBaseFragment {
    View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 是否使用eventbus
     *
     * @return
     */
    boolean useEventBus();

    void setData(@Nullable Object data);

}
