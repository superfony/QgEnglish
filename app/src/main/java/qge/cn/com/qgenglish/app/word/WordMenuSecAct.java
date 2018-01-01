package qge.cn.com.qgenglish.app.word;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.articel.ArticelMenuAct;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;

/**
 * 超级记单词
 * 于阅读训练
 * 我的生词本
 */

public class WordMenuSecAct extends BaseActivity {
    @Bind(R.id.menu1)
    Button menu1;
    @Bind(R.id.menu2)
    Button menu2;
    @Bind(R.id.menu3)
    Button menu3;
    private String TAG = "SecondMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_word);
        ButterKnife.bind(this);
    }

    //超级记单词
    @OnClick(R.id.menu1)
    void onclicMenu1() {
        Intent intent = new Intent();
        intent.setClass(activity, WordMenuThAct.class);
        activity.startActivity(intent);
    }

    // 阅读训练
    @OnClick(R.id.menu2)
    void onclicMenu2() {
        Intent intent = new Intent();
        intent.setClass(activity, ArticelMenuAct.class);
        activity.startActivity(intent);
    }

    // 我的生词本
    @OnClick(R.id.menu3)
    void onclicMenu3() {
        Intent intent = new Intent();
        intent.setClass(activity, NewWordChoseAct.class);
        activity.startActivity(intent);
    }
}
