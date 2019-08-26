package com.mitsuki.mosterhunterworldwiki.mvp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mitsuki.mosterhunterworldwiki.R
import com.mitsuki.mosterhunterworldwiki.mvp.module.demoKodeinModule
import com.mitsuki.mosterhunterworldwiki.mvp.contract.DemoContract
import com.mitsuki.mosterhunterworldwiki.mvp.presenter.DemoPresenter
import com.mitsuki.mosterhunterworldwiki.mvp.ui.fragment.InnerFragment
import com.mitsuki.simplemvp.base.BaseActivity
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_demo.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class DemoActivity : BaseActivity<DemoPresenter>(), DemoContract.View {

    override val kodeinModule: Kodein.Module = demoKodeinModule
    override val mPresenter: DemoPresenter by instance()

    val subject: PublishSubject<Int> by kodein.instance()

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_demo

    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        btn.setOnClickListener { mPresenter.test() }

        val fra = InnerFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentCre, fra).commitAllowingStateLoss()

        subject.filter { it == 1 }.subscribe { showMessage("这里是activity，收到了来自fragment 的消息") }
    }


    override fun testShow(str: String) {
        showMessage(str)
    }
}