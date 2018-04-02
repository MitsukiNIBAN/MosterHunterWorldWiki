package com.satou.wiki.base;

import android.app.Activity;

import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
        INSTANCE = this;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(5000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(5000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build());
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
