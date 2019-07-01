package com.mitsuki.mosterhunterworldwiki.mvp.contract

import com.mitsuki.mosterhunterworldwiki.mvp.model.entity.CharmBean
import com.mitsuki.simplemvp.base.IModel
import com.mitsuki.simplemvp.base.IView

interface CharmContract {
    interface View : IView {
        fun showCharmData(data: List<CharmBean>)
    }

    interface Model : IModel {

    }
}