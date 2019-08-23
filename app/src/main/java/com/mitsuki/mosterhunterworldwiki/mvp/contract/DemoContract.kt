package com.mitsuki.mosterhunterworldwiki.mvp.contract

import com.mitsuki.simplemvp.base.IModel
import com.mitsuki.simplemvp.base.IView

interface DemoContract {
    interface View : IView {
        fun testShow(str: String)
    }

    interface Model : IModel {
        fun getStr(): String
    }
}