package com.mitsuki.mvp.base

import android.os.Bundle


interface IBaseActivity {
    fun initView(savedInstanceState: Bundle?): Int

    fun initData(savedInstanceState: Bundle?)
}
