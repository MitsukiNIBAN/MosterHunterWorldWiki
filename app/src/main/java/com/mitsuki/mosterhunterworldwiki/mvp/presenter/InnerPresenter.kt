package com.mitsuki.mosterhunterworldwiki.mvp.presenter

import com.mitsuki.mosterhunterworldwiki.mvp.contract.InnerContract
import com.mitsuki.simplemvp.base.BasePresenter
import io.reactivex.subjects.PublishSubject
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class InnerPresenter(kodein: Kodein) : BasePresenter<InnerContract.View, InnerContract.Model>(kodein) {
    override val mView: InnerContract.View by kodein.instance()
    override val mModel: InnerContract.Model? by kodein.instance()

    val subject by kodein.instance<PublishSubject<Int>>()


    fun sendStr() {
        subject.onNext(1)
    }

}