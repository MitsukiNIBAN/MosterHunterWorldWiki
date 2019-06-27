package com.mitsuki.utilspack.utils.resultmanager

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.SparseArray

class ResultFragment : Fragment() {

    private var callbacks = SparseArray<(requestCode: Int, resultCode: Int, data: Intent?) -> Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startActivityForResult(
        intent: Intent,
        requestCode: Int,
        callback: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit
    ) {
        callbacks.put(requestCode, callback)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbacks.get(requestCode).invoke(requestCode, resultCode, data)
    }
}