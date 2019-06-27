package com.mitsuki.mosterhunterworldwiki.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun makeText(context: Context, str: String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    fun makeTextLong(context: Context, str: String) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show()
    }
}
