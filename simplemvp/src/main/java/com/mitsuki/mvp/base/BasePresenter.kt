package com.mitsuki.mvp.base


abstract class BasePresenter<V : IView, M : IModel>() {
    abstract val mView: V
    open val mModel: M? = null

    fun onDestroy() {
        mModel?.onDestroy()
    }
}
