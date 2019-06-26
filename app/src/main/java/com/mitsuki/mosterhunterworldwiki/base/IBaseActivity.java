package com.mitsuki.mosterhunterworldwiki.base;

import android.os.Bundle;
import android.support.annotation.Nullable;


public interface IBaseActivity {
    int initView(@Nullable Bundle savedInstanceState);

    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 是否使用eventbus
     *
     * @return
     */
    boolean useEventBus();

}
