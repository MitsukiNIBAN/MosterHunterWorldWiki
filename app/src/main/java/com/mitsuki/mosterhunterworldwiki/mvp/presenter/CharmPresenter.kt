package com.mitsuki.mosterhunterworldwiki.mvp.presenter

import android.content.Context
import com.mitsuki.mosterhunterworldwiki.mvp.contract.CharmContract
import com.mitsuki.mosterhunterworldwiki.mvp.model.CharmModel
import com.mitsuki.simplemvp.base.BasePresenter

class CharmPresenter(context: Context, view: CharmContract.View) :
    BasePresenter<CharmContract.View, CharmContract.Model>(context, view) {
    override fun getModel(): CharmContract.Model = CharmModel()


    fun getCharmList(){

    }
}