package com.mitsuki.mosterhunterworldwiki.mvp.presenter

import com.mitsuki.mosterhunterworldwiki.mvp.contract.DemoContract
import com.mitsuki.simplemvp.base.BasePresenter
import io.reactivex.subjects.PublishSubject
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class DemoPresenter(kodein: Kodein) : BasePresenter<DemoContract.View, DemoContract.Model>(kodein) {
    override val mView: DemoContract.View by kodein.instance()
    override val mModel: DemoContract.Model by kodein.instance()

    val subject by kodein.instance<PublishSubject<Int>>()

    fun test() {
        subject.onNext(0)
    }

}