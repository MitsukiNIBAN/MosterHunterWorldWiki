package com.mitsuki.simplemvp.base

import org.kodein.di.Kodein

abstract class BasePresenter<V : IView, M : IModel>(kodein: Kodein) {
    abstract val mView: V
    open val mModel: M? = null

    fun onDestroy() {
        mModel?.onDestroy()
    }
}
