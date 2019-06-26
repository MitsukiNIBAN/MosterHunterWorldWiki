package com.mitsuki.mosterhunterworldwiki.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.zcitc.cloudhousehelper.constants.Constants;
import com.zcitc.cloudhousehelper.helper.AppManager;
import com.zcitc.cloudhousehelper.helper.database.GreenDaoHelper;
import com.zcitc.cloudhousehelper.helper.glidehelper.GlideImageLoader;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import okhttp3.OkHttpClient;
import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

import java.util.HashMap;

public class FLApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static Application mApplication;

    public static Application getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        //okgo初始化
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build());

        initUtils();
        initX5();
        initUmeng();
        initImagePicker();

        //注册生命周期
        this.registerActivityLifecycleCallbacks(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initUtils() {
        //初始化activity管理器
        AppManager.getAppManager().init(this);

        //屏幕适配
        AutoSizeConfig.getInstance()
                .getUnitsManager()
                .setSupportDP(true)
                .setSupportSubunits(Subunits.PT);

        //加载数据库工具
        GreenDaoHelper.initDatabase(this);
    }

    private void initX5() {
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_PRIVATE_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);

        //x5 callback
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                if (arg0) {
//                    Toast.makeText(getApplicationContext(), "内核加载成功", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getApplicationContext(), "内核加载失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initUmeng() {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的AppKey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, Constants.UMENG_APPKEY, "Android", UMConfigure.DEVICE_TYPE_PHONE, Constants.UMENG_MSG_SECRET);
        UMConfigure.setLogEnabled(true);
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("FLApplication", "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("FLApplication", "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        MiPushRegistar.register(this, Constants.XIAOMI_ID, Constants.XIAOMI_KEY);
        HuaWeiRegister.register(this);

        //微博分享初始化
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        //
        PlatformConfig.setAlipay(Constants.ALIPAY_APP_ID);
        PlatformConfig.setWeixin(Constants.WECHAT_APP_ID, Constants.WECHAT_APP_SECRET);

    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
    }

    /***********************ActivityLifeCallbacks**************************************************/

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //如果 intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理
        boolean isNotAdd = false;
        if (activity.getIntent() != null)
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);

        if (!isNotAdd)
            AppManager.getAppManager().addActivity(activity);
        //应用数据统计接口
        PushAgent.getInstance(activity).onAppStart();

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        AppManager.getAppManager().setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (AppManager.getAppManager().getCurrentActivity() == activity) {
            AppManager.getAppManager().setCurrentActivity(null);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppManager.getAppManager().removeActivity(activity);
        OkGo.getInstance().cancelTag(this);
    }
}
