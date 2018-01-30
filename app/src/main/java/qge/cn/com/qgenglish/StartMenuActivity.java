package qge.cn.com.qgenglish;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.CheckAct;
import qge.cn.com.qgenglish.app.experience.ExpActivity;

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
        ButterKnife.bind(this);
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
