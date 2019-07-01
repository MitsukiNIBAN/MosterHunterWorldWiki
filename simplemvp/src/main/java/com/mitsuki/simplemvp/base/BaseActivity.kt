package com.mitsuki.simplemvp.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.InflateException
import android.view.MotionEvent
import com.mitsuki.utilspack.utils.AppManager
import com.mitsuki.utilspack.utils.SoftInputHelper
import com.mitsuki.utilspack.utils.ToastUtils

abstract class BaseActivity<T : BasePresenter<*, *>> : AppCompatActivity(), IBaseActivity, IView {
    val TAG = this.javaClass.simpleName
    var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val layoutResID = initView(savedInstanceState)
            if (layoutResID != 0) {
                setContentView(layoutResID)
            }
        } catch (e: Exception) {
            if (e is InflateException) throw e
            e.printStackTrace()
        }

        mPresenter = myPresenter()
        initData(savedInstanceState)
    }

    /*****************************************************************************************************************/
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMessage(message: String) {
        ToastUtils.makeText(this, message)
    }

    override fun launchActivity(intent: Intent) {
        AppManager.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    /*****************************************************************************************************************/
    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        initTitleBar()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            SoftInputHelper.hideSoftInput(this)
            return super.dispatchTouchEvent(ev)
        }
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }

    /*****************************************************************************************************************/
    open fun myPresenter(): T? = null

    /*****************************************************************************************************************/


    private fun initTitleBar() {

        //        if (findViewById(R.id.toolbar) != null) {
        //            setSupportActionBar(findViewById(R.id.toolbar));
        //            getSupportActionBar().setDisplayShowTitleEnabled(false);
        //        }
        //
        //        if (findViewById(R.id.toolbar_title) != null && !(this instanceof WebPageActivity)) {
        //            ((TextView) findViewById(R.id.toolbar_title)).setText(getTitle());
        //        }
        //
        //        if (findViewById(R.id.toolbar_back) != null) {
        //            findViewById(R.id.toolbar_back).setOnClickListener(v -> {
        //                onBackPressed();
        //            });
        //        }
    }
}
