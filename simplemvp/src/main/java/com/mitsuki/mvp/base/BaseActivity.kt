package com.mitsuki.mvp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.InflateException

abstract class BaseActivity<T : BasePresenter<*, *>> : AppCompatActivity(),
    IView,
    IBaseActivity {

    val TAG = this.javaClass.simpleName

    abstract val mPresenter: T?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val layoutResID = initView(savedInstanceState)
            if (layoutResID != 0) setContentView(layoutResID)
        } catch (e: Exception) {
            if (e is InflateException) throw e
            e.printStackTrace()
        }

        initData(savedInstanceState)
    }

    override fun killMyself() = finish()

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }
}
