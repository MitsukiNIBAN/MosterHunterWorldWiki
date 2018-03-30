package com.satou.wiki.base;

import android.app.Activity;

import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitsuki on 2018/3/30.
 */

public class Application extends android.app.Application {
    public List<Activity> activityList = new ArrayList<Activity>();
    private static Application INSTANCE;

    //    private Application(){}
    public synchronized static Application getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        INSTANCE = this;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityList) {
            if (activity != null) {
                if (activity.getClass().equals(cls))
                    activity.finish();
            }
        }
    }

    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityList) {
            if (activity != null) {
                if (activity.getClass().equals(cls))
                    return activity;
            }
        }
        return null;
    }
}
