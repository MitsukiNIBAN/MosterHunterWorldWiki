package com.mitsuki.mvp.base

import android.content.Intent

interface IView {

    fun showLoading()

    fun hideLoading()

    fun showMessage(message: String)

    fun launchActivity(intent: Intent)

    fun killMyself()
}
