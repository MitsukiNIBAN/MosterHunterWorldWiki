package com.mitsuki.simplemvp.base

import android.content.Context

abstract class BasePresenter<V : IView, M : IModel>(var context: Context, var view: V) {
    var mView: V = view
    var mModel: M? = getModel()

    abstract fun getModel(): M

    fun onDestroy() {
        mModel?.onDestroy()
    }
}
