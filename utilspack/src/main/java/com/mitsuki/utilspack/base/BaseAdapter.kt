package com.mitsuki.utilspack.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

abstract class BaseAdapter<B, T : RecyclerView.ViewHolder>(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_EMPTY = Integer.MIN_VALUE

    private var mData: MutableList<B> = ArrayList()
    protected var mContext = context
    private var emptyLayout: Int = 0
    private var useEmpty = false

    final override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return if (getItemViewType(i) == VIEW_TYPE_EMPTY && emptyLayout != 0 && useEmpty) {
            onCreateEmptyViewHolder(viewGroup, i)
        } else onCreateMyViewHolder(viewGroup, i)
    }

    final override fun onBindViewHolder(t: RecyclerView.ViewHolder, i: Int) {
        if (getItemViewType(i) == VIEW_TYPE_EMPTY) {
            onBindEmptyViewHolder(t, i)
        } else {
            onBindMyViewHolder(t as T, i)
        }
    }

    abstract fun onCreateMyViewHolder(viewGroup: ViewGroup, i: Int): T

    abstract fun onBindMyViewHolder(t: T, i: Int)

    fun onCreateEmptyViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val emptyView = LayoutInflater.from(mContext).inflate(emptyLayout, viewGroup, false)
        return object : RecyclerView.ViewHolder(emptyView) {

        }
    }

    fun onBindEmptyViewHolder(t: RecyclerView.ViewHolder, i: Int) {

    }


    override fun getItemCount(): Int {
        return if (mData.size <= 0 && useEmpty && emptyLayout != 0) 1 else mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mData.size <= 0 && useEmpty && emptyLayout != 0) VIEW_TYPE_EMPTY else super.getItemViewType(
            position
        )
    }

    fun setEmptyLayout(emptyLayout: Int) {
        this.emptyLayout = emptyLayout
    }

    fun addAll(data: List<B>) {
        mData.clear()
        mData.addAll(data)
    }

    fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): B {
        return mData[position]
    }

    fun setUseEmpty(useEmpty: Boolean) {
        this.useEmpty = useEmpty
    }
}
