package com.mitsuki.mosterhunterworldwiki.mvp.module

import com.mitsuki.mosterhunterworldwiki.mvp.contract.DemoContract
import com.mitsuki.mosterhunterworldwiki.mvp.contract.InnerContract
import com.mitsuki.mosterhunterworldwiki.mvp.model.DemoModel
import com.mitsuki.mosterhunterworldwiki.mvp.model.InnerModel
import com.mitsuki.mosterhunterworldwiki.mvp.presenter.DemoPresenter
import com.mitsuki.mosterhunterworldwiki.mvp.presenter.InnerPresenter
import com.mitsuki.mosterhunterworldwiki.mvp.ui.activity.DemoActivity
import com.mitsuki.mosterhunterworldwiki.mvp.ui.fragment.InnerFragment
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton


const val INNER_MODULE_TAG = "INNER_MODULE_TAG"

val innerKodeinModule = Kodein.Module(INNER_MODULE_TAG) {
    bind<InnerContract.Model>() with scoped<InnerFragment>(AndroidLifecycleScope).singleton { InnerModel() }
    bind<InnerContract.View>() with scoped<InnerFragment>(AndroidLifecycleScope).singleton { context }
    bind<InnerPresenter>() with scoped<InnerFragment>(AndroidLifecycleScope).singleton { InnerPresenter(kodein) }
}