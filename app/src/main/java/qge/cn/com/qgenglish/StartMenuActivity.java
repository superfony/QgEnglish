package qge.cn.com.qgenglish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.CheckAct;
import qge.cn.com.qgenglish.app.experience.ExpActivity;
import qge.cn.com.qgenglish.app.update.UpdateManager;

/**
 * 首页菜单
 * 超级记单词
 * 词汇量检测
 * 超级记单词体验
 */

public class StartMenuActivity extends BaseActivity {
    private String TAG = "StartMenuActivity";
    @Bind(R.id.menu1)
    Button menu1;
    @Bind(R.id.menu2)
    Button menu2;
    @Bind(R.id.menu3)
    Button menu3;
    @Bind(R.id.menu4)
    Button menu4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmenu);
        activity = this;
        ButterKnife.bind(this);
        // UpdateManager.getUpdateManager().checkAppUpdate(this, false);// 检查是否更新
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this);
//        PgyUpdateManager.register(this,
//                new UpdateManagerListener() {
//
//                    @Override
//                    public void onUpdateAvailable(final String result) {
//
//                        // 将新版本信息封装到AppBean中
//                        final AppBean appBean = getAppBeanFromString(result);
//                        new AlertDialog.Builder(activity)
//                                .setTitle("更新")
//                                .setMessage("")
//                                .setNegativeButton(
//                                        "确定",
//                                        new DialogInterface.OnClickListener() {
//
//                                            @Override
//                                            public void onClick(
//                                                    DialogInterface dialog,
//                                                    int which) {
//                                                startDownloadTask(
//                                                        activity,
//                                                        appBean.getDownloadURL());
//                                            }
//                                        }).show();
//                    }
//
//                    @Override
//                    public void onNoUpdateAvailable() {
//                    }
//                });


    }

    @OnClick(R.id.menu1)
    void onclicMenu1() {
        Intent intent = new Intent();
        intent.setClass(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    //词汇量检测  收集学生信息
    @OnClick(R.id.menu2)
    void onclicMenu2() {
        Intent intent = new Intent();
        intent.setClass(activity, CheckAct.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.menu3)
    void onclicMenu3() {
        Intent intent = new Intent();
        intent.setClass(activity, ExpActivity.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.menu4)
    void onclicMenu4() {
//        Intent intent = new Intent();
//        intent.setClass(activity, StartMenuActivity.class);
//        activity.startActivity(intent);
    }
}
