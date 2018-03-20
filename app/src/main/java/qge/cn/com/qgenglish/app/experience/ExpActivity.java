package qge.cn.com.qgenglish.app.experience;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordActC;
import qge.cn.com.qgenglish.app.word.table.Word_four;
import qge.cn.com.qgenglish.app.word.table.Word_high_ty;
import qge.cn.com.qgenglish.app.word.table.Word_middle_ty;
import qge.cn.com.qgenglish.app.word.table.Word_small_ty;
import qge.cn.com.qgenglish.app.word.table.Word_tfys_ty;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * 超级记单词体验
 *  初中单词
 *  小学单词
 */

public class ExpActivity extends BaseActivity {
    @Bind(R.id.menu4)
    Button menu4;
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
        reqMenu();
    }


    @OnClick(R.id.menu1)
    void onclicMenu1() {  //初中单词 体验
        Intent intent = new Intent();
        //intent.setClass(activity, ExpChoseWordAct.class);
        if (!DBManager.getWordManager().isExist(TableName.word_middle_ty)) {
            DBManager.getWordManager().create(Word_middle_ty.class, DBManager.getWordManager().getReadableDatabase());
        }
        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
        intent.putExtra("tableName", TableName.word_middle_ty);
        intent.setClass(activity, WordActC.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.menu2)
    void onclicMenu2() {//高中单词
        Intent intent = new Intent();
        //intent.setClass(activity, ExpChoseWordAct.class);
        if (!DBManager.getWordManager().isExist(TableName.word_high_ty)) {
            DBManager.getWordManager().create(Word_high_ty.class, DBManager.getWordManager().getReadableDatabase());
        }
        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
        intent.putExtra("tableName", TableName.word_high_ty);
        intent.setClass(activity, WordActC.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.menu3)
    void onclicMenu3() {//托福雅思单词
        Intent intent = new Intent();
        //intent.setClass(activity, ExpChoseWordAct.class);
        if (!DBManager.getWordManager().isExist(TableName.word_tfys_ty)) {
            DBManager.getWordManager().create(Word_tfys_ty.class, DBManager.getWordManager().getReadableDatabase());
        }
        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
        intent.putExtra("tableName", TableName.word_tfys_ty);
        intent.setClass(activity, WordActC.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.menu4)
    void onclicMenu4() {// 小学
        Intent intent = new Intent();
        // intent.setClass(activity, ExpChoseWordAct.class);
        if (!DBManager.getWordManager().isExist(TableName.word_small_ty)) {
            DBManager.getWordManager().create(Word_small_ty.class, DBManager.getWordManager().getReadableDatabase());
        }
        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
        intent.putExtra("tableName", TableName.word_small_ty);
        intent.setClass(activity, WordActC.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        resultMenu(s);
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        switch (msg.what) {
            case 0:
                ToastHelper.toast(activity, msg.obj.toString());
                finish();
            case 1:
                Log.i("", "获取成功");
                break;
        }
    }
}
