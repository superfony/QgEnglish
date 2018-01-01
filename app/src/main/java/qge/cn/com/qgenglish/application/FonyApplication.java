package qge.cn.com.qgenglish.application;


import com.baiyang.android.crashexception.AppCrashHandler;

import qge.cn.com.qgenglish.BuildConfig;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * Created by qiujuer
 * on 2016/10/27.
 */
public class FonyApplication extends AppContext {
    private static final String CONFIG_READ_STATE_PRE = "CONFIG_READ_STATE_PRE_";

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化操作
        //DetailCache.init(getApplicationContext()); // fony
        init();
    }

    public static void reInit() {
        ((FonyApplication) FonyApplication.getInstance()).init();
    }

    private void init() {
        // 初始化异常捕获类
        AppCrashHandler.getInstance().init(this);
        DBManager.init(this);
        AppSharedPreference.init(this, "app_sp");

        if (AppSharedPreference.getInstance().hasShowUpdate()) {//如果已经更新过
            //如果版本大于更新过的版本，就设置弹出更新
            if (BuildConfig.VERSION_CODE > AppSharedPreference.getInstance().getUpdateVersion()) {
                AppSharedPreference.getInstance().putShowUpdate(true);
            }
        }
    }
}