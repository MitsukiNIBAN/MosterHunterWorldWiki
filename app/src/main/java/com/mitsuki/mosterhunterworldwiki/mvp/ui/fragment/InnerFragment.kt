package com.mitsuki.mosterhunterworldwiki.mvp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mitsuki.mosterhunterworldwiki.R
import com.mitsuki.mosterhunterworldwiki.mvp.contract.InnerContract
import com.mitsuki.mosterhunterworldwiki.mvp.module.innerKodeinModule
import com.mitsuki.mosterhunterworldwiki.mvp.presenter.InnerPresenter
import com.mitsuki.simplemvp.base.BaseFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_demo.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class InnerFragment : BaseFragment<InnerPresenter>(), InnerContract.View {
    override val kodeinModule: Kodein.Module = innerKodeinModule
    override val mPresenter: InnerPresenter by instance()

    val subject by kodein.instance<PublishSubject<Int>>()

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_demo, container, false)

    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
//        subject.filter { it == 0 }.subscribe { showMessage("这里是fragment，收到了来自activity的消息") }

        fragmentBtn.setOnClickListener { mPresenter.sendStr() }
    }

    override fun setData(data: Any?) {
    }

}