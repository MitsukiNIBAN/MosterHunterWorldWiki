package com.mitsuki.mosterhunterworldwiki.mvp.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mitsuki.mosterhunterworldwiki.R
import com.mitsuki.mosterhunterworldwiki.mvp.model.entity.CharmBean
import com.mitsuki.utilspack.base.BaseAdapter


class CharmAdapter(context: Context) : BaseAdapter<CharmBean, CharmAdapter.ViewHolder>(context) {
    override fun onCreateMyViewHolder(viewGroup: ViewGroup, i: Int): CharmAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_charm, viewGroup, false)
        return ViewHolder(view)
    }


    override fun onBindMyViewHolder(t: CharmAdapter.ViewHolder, i: Int) {

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}