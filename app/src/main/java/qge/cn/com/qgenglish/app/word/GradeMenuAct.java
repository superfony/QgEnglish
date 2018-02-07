package qge.cn.com.qgenglish.app.word;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.LoginActivity;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.StuLoginActivity;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.articel.ArticelMenuAct;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;

/**
 * 小学课程
 * 初中课程
 * 高中课程
 * 四级单词
 * 六级单词
 * 托福单词
 * 雅思课程
 */

public class GradeMenuAct extends BaseActivity {
    @Bind(R.id.menu1)
    Button menu1;
    @Bind(R.id.menu2)
    Button menu2;
    @Bind(R.id.menu3)
    Button menu3;
    @Bind(R.id.menu4)
    Button menu4;
    @Bind(R.id.menu5)
    Button menu5;
    @Bind(R.id.menu6)
    Button menu6;
    @Bind(R.id.menu7)
    Button menu7;
    private String TAG = "GradeMenuAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_menu_act);
        ButterKnife.bind(this);


    }

    //小学
    @OnClick(R.id.menu1)
    void onclicMenu1() {
        Intent intent = new Intent();
        intent.putExtra("grade", 1);
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 初中
    @OnClick(R.id.menu2)
    void onclicMenu2() {
        Intent intent = new Intent();
        intent.putExtra("grade", 2);
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 高中
    @OnClick(R.id.menu3)
    void onclicMenu3() {

        Intent intent = new Intent();
        intent.putExtra("grade", 3);
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 四级
    @OnClick(R.id.menu4)
    void onclicMenu4() {
        Intent intent = new Intent();
        intent.putExtra("grade", 4);
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 六级
    @OnClick(R.id.menu5)
    void onclicMenu5() {
        Intent intent = new Intent();
        intent.putExtra("grade", 5);
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 托福
    @OnClick(R.id.menu6)
    void onclicMenu6() {
        Intent intent = new Intent();
        intent.putExtra("grade", 6);
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 雅思
    @OnClick(R.id.menu7)
    void onclicMenu7() {
        Intent intent = new Intent();
        intent.putExtra("grade", 7);
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }


    // 点击判断当前是否登录
}
