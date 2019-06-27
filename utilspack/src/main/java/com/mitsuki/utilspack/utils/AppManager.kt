package com.mitsuki.utilspack.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import java.util.*
import kotlin.system.exitProcess

@SuppressLint("StaticFieldLeak")
object AppManager {
    var TAG = javaClass.simpleName
    private lateinit var mApplication: Application
    private var mActivityList: MutableList<Activity>? = null

    /**
     * 当前在前台的 Activity
     */
    var currentActivity: Activity? = null

    fun init(application: Application) {
        this.mApplication = application
    }

    fun getTopActivity(): Activity? = mActivityList?.let {
        if (it.size > 0) it.last() else null
    }

    fun getActivityList(): MutableList<Activity> {
        mActivityList?.let {
            return it
        }
        mActivityList = LinkedList()
        return mActivityList!!
    }

    fun addActivity(activity: Activity) {
        synchronized(AppManager::class) {
            val activities = getActivityList()
            if (!activities.contains(activity)) {
                activities.add(activity)
            }
        }
    }

    fun removeActivity(activity: Activity) {
        mActivityList?.let {
            synchronized(AppManager::class) {
                if (!it.contains(activity)) {
                    it.remove(activity)
                }
            }
        }
    }

    fun removeActivity(location: Int): Activity? {
        mActivityList?.let {
            synchronized(AppManager::class.java) {
                if (location > 0 && location < it.size) {
                    return it.removeAt(location)
                }
            }
        }
        return null
    }

    fun killActivity(activityClass: Class<*>) {
        mActivityList?.let {
            synchronized(AppManager::class) {
                val iterator = getActivityList().iterator()
                while (iterator.hasNext()) {
                    val next = iterator.next()
                    if (next.javaClass == activityClass) {
                        iterator.remove()
                        next.finish()
                    }
                }
            }
        }
    }

    fun activityInstanceIsLive(activity: Activity): Boolean {
        mActivityList?.let {
            return it.contains(activity)
        }
        return false
    }

    fun activityClassIsLive(activityClass: Class<*>): Boolean {
        mActivityList?.let {
            for (activity in it) {
                if (activity.javaClass == activityClass) {
                    return true
                }
            }
        }
        return false
    }

    fun findActivity(activityClass: Class<*>): Activity? {
        mActivityList?.let {
            for (activity in it) {
                if (activity.javaClass == activityClass) {
                    return activity
                }
            }
        }
        return null
    }

    fun killAll() {
        synchronized(AppManager::class) {
            val iterator = getActivityList().iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                iterator.remove()
                next.finish()
            }
        }
    }

    fun appExit() {
        try {
            killAll()
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startActivity(intent: Intent) {
        if (getTopActivity() == null) {
            //如果没有前台的activity就使用new_task模式启动activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mApplication.startActivity(intent)
            return
        }
        getTopActivity()?.startActivity(intent)
    }

    fun startActivity(activityClass: Class<*>) {
        startActivity(Intent(mApplication, activityClass))
    }
}