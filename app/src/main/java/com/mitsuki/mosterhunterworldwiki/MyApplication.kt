package com.mitsuki.mosterhunterworldwiki

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.mitsuki.utilspack.utils.AppManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class MyApplication : Application(), Application.ActivityLifecycleCallbacks, KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton { this@MyApplication }
        import(androidCoreModule(this@MyApplication))
        import(androidXModule(this@MyApplication))
    }

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }


    /***********************ActivityLifeCallbacks**********************************************************************/

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}
