package com.mitsuki.mosterhunterworldwiki

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.mitsuki.utilspack.utils.AppManager

class MyApplication : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //注册生命周期
        this.registerActivityLifecycleCallbacks(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    /***********************ActivityLifeCallbacks**********************************************************************/

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        AppManager.addActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        AppManager.currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        if (AppManager.currentActivity == activity) {
            AppManager.currentActivity = null
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        AppManager.removeActivity(activity)
    }

}
