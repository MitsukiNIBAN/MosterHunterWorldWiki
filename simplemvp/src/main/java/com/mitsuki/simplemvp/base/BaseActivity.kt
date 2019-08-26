package com.mitsuki.simplemvp.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.InflateException
import android.view.MotionEvent
import com.mitsuki.utilspack.utils.AppManager
import com.mitsuki.utilspack.utils.SoftInputHelper
import com.mitsuki.utilspack.utils.toastShort
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

abstract class BaseActivity<T : BasePresenter<*, *>> : AppCompatActivity(), KodeinAware, IView,
        IBaseActivity {
    val TAG = this.javaClass.simpleName

    abstract val mPresenter: T?
    abstract val kodeinModule: Kodein.Module

    private val parentKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(kodeinModule)
    }

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

    /*****************************************************************************************************************/
    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showMessage(message: String) = toastShort { message }

    override fun launchActivity(intent: Intent) = AppManager.startActivity(intent)

    override fun killMyself() = finish()

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
