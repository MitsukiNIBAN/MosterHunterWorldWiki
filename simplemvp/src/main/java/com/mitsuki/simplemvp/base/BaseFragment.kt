package com.mitsuki.simplemvp.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mitsuki.utilspack.utils.AppManager
import com.mitsuki.utilspack.utils.ToastUtils


abstract class BaseFragment<T : BasePresenter<*, *>> : Fragment(), IBaseFragment, IView {
    val TAG = this.javaClass.simpleName
    var mPresenter: T? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter = myPresenter()
        initData(savedInstanceState)
    }

    fun myPresenter(): T? = null

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }
    /*****************************************************************************************************************/
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMessage(message: String) {
        context?.let {
            ToastUtils.makeText(it, message)
        }
    }

    override fun launchActivity(intent: Intent) {
        AppManager.startActivity(intent)
    }

    override fun killMyself() {

    }
}
