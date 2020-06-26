package com.mitsuki.mvvm.base

import android.os.Bundle
import android.view.InflateException
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<M : BaseViewModel<*>> : AppCompatActivity() {

    abstract val mViewModel: M

    abstract fun initView(savedInstanceState: Bundle?): Int
    abstract fun initData(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initView(savedInstanceState).apply { if (this != 0) setContentView(this) }
        } catch (e: Exception) {
            if (e is InflateException) throw e
            e.printStackTrace()
        }
        initData(savedInstanceState)
    }

}