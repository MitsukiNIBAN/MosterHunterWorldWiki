package com.mitsuki.utilspack.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager

import android.content.Context.INPUT_METHOD_SERVICE

/**
 * 软键盘帮助类
 */
object SoftInputHelper {
    /**
     * 显示软键盘
     *
     * @param activity
     */
    fun showSoftInput(activity: Activity) {
        activity.currentFocus?.let {
            val imm: InputMethodManager = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(it, 0)
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    fun hideSoftInput(activity: Activity) {
        activity.currentFocus?.let {
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}
