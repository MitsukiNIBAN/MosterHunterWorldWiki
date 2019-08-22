package com.mitsuki.mosterhunterworldwiki.mvp.ui.activity

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitsuki.mosterhunterworldwiki.R
import com.mitsuki.mosterhunterworldwiki.mvp.contract.CharmContract
import com.mitsuki.mosterhunterworldwiki.mvp.model.entity.CharmBean
import com.mitsuki.mosterhunterworldwiki.mvp.presenter.CharmPresenter
import com.mitsuki.mosterhunterworldwiki.mvp.ui.adapter.CharmAdapter
import com.mitsuki.simplemvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list.*

class CharmActivity : BaseActivity<CharmPresenter>(), CharmContract.View, androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {

    lateinit var mAdapter: CharmAdapter

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_list

    override fun myPresenter() = CharmPresenter(this, this)

    override fun initData(savedInstanceState: Bundle?) {

        initRecyclerView()
    }

    /**********************************************************************************************/
    override fun onRefresh() {
        mPresenter?.getCharmList()
    }

    override fun hideLoading() {
        refreshLayout.isRefreshing = false
    }


    /**********************************************************************************************/
    override fun showCharmData(data: List<CharmBean>) {
        mAdapter.addAll(data)
        mAdapter.notifyDataSetChanged()
    }

    /**********************************************************************************************/
    private fun initRecyclerView() {
        refreshLayout.setOnRefreshListener(this)

        mAdapter = CharmAdapter(this)
        dataLayout.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        dataLayout.adapter = mAdapter
    }


}
