package qge.cn.com.qgenglish.app.experience;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.word.SjWordAct;

/**
 * 超级记单词体验
 * 初中单词
 * 高中单词
 * 雅思单词
 */

public class ExpActivity extends BaseActivity {
    private String TAG = "ExpActivity";
    @Bind(R.id.menu1)
    Button menu1;
    @Bind(R.id.menu2)
    Button menu2;
    @Bind(R.id.menu3)
    Button menu3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cjjdcty_menu_act);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.menu1)
    void onclicMenu1() {  //初中单词
        Intent intent = new Intent();
        intent.setClass(activity, ExpChoseWordAct.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.menu2)
    void onclicMenu2() {//高中单词
        Intent intent = new Intent();
        intent.setClass(activity, ExpChoseWordAct.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.menu3)
    void onclicMenu3() {//托福雅思单词
        Intent intent = new Intent();
        intent.setClass(activity, ExpChoseWordAct.class);
        activity.startActivity(intent);
    }
}
