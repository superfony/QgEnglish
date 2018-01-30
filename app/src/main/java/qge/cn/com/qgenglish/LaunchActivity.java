package qge.cn.com.qgenglish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import qge.cn.com.qgenglish.app.AppOperator;

/**
 * 应用启动界面
 */
public class LaunchActivity extends Activity {

    protected String TAG = "LaunchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_start);
        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;
        Log.w(TAG, "xdpi=" + xdpi + ",ydpi=" + ydpi);//xdpi=210.0,ydpi=210.0

        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                doMerge();
            }
        });
    }

    private void doMerge() {
        // 判断是否是新版本
//        if (Setting.checkIsNewVersion(this)) {
//            // Cookie迁移
//            String cookie = OSCApplication.getInstance().getProperty("cookie");
//            if (!TextUtils.isEmpty(cookie)) {
//                OSCApplication.getInstance().removeProperty("cookie");
//                User user = AccountHelper.getUser();
//                user.setCookie(cookie);
//                AccountHelper.updateUserCache(user);
//                OSCApplication.reInit();
//            }
//        }
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redirectTo();
    }

    private void redirectTo() {
        Intent intent = new Intent(this, StartMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
