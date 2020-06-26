package com.mitsuki.mvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BasePresenter<*, *>> : Fragment(),
    IView,
    IBaseFragment {

    val TAG = this.javaClass.simpleName

    abstract val mPresenter: T?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = initView(inflater, container, savedInstanceState)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
    override fun killMyself() {
        activity?.finish()
    }
}
