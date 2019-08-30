package com.mitsuki.mosterhunterworldwiki.mvp.module

import com.mitsuki.mosterhunterworldwiki.mvp.contract.DemoContract
import com.mitsuki.mosterhunterworldwiki.mvp.model.DemoModel
import com.mitsuki.mosterhunterworldwiki.mvp.presenter.DemoPresenter
import com.mitsuki.mosterhunterworldwiki.mvp.ui.activity.DemoActivity
import com.uber.autodispose.autoDisposable
import io.reactivex.subjects.PublishSubject
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val DEMO_MODULE_TAG = "DEMO_MODULE_TAG"

val demoKodeinModule = Kodein.Module(DEMO_MODULE_TAG) {
    bind<DemoContract.Model>() with scoped<DemoActivity>(AndroidLifecycleScope).singleton { DemoModel() }
    bind<DemoContract.View>() with scoped<DemoActivity>(AndroidLifecycleScope).singleton { context }
    bind<DemoPresenter>() with scoped<DemoActivity>(AndroidLifecycleScope).singleton { DemoPresenter(kodein) }

    bind<PublishSubject<Int>>() with singleton { PublishSubject.create<Int>() }
}