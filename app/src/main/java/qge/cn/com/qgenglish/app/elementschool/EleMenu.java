package qge.cn.com.qgenglish.app.elementschool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.util.basic.ToastHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordActC;
import qge.cn.com.qgenglish.app.word.table.Phrase_small;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * 小学的
 */

public class EleMenu extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    private String TAG = "ThreeMenuAct";
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private EleMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"小学单词", "小学短语", "小学写作",
            "小学语法", "重点句型", "我的生词本"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        initData();
        reqMenu();

    }

    private void initData() {
        title.setText("小学课程");
        wordMenuThAdapter = new EleMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();

                if (position == 0) {
                    ((FonyApplication) activity.getApplication()).tableDes = "小学单词";
                    ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
                    intent.putExtra("tableName", TableName.word_small);
                    intent.setClass(activity, WordAct.class);
                    activity.startActivity(intent);
                } else if (position == 1) {
                    ((FonyApplication) activity.getApplication()).tableDes = "小学短语";
                    ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.PHRASE;
                    if (!DBManager.getWordManager().isExist(TableName.phrase_small)) {
                        DBManager.getWordManager().create(Phrase_small.class, DBManager.getWordManager().getReadableDatabase());
                    }
                    intent.putExtra("tableName", TableName.phrase_small);
                    intent.setClass(activity, WordActC.class);
                    activity.startActivity(intent);
                } else if (position == 2) {
                    return;

                } else if (position == 3) {
                    return;

                } else if (position == 4) {
                    return;

                } else if (position == 5) { // 我的生词本
                    intent.setClass(activity, NewWordChoseAct.class);
                    activity.startActivity(intent);
                    return;

                }


            }
        });
    }


    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        resultMenu(s);
    }

    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);


        switch (msg.what) {
            case 0:
                ToastHelper.toast(activity, msg.obj.toString());
                break;
            case 1:
                wordMenuThAdapter.updateListView(menuArrayList);
                break;
            case 3:
                activity.finish();
                break;


        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            RequestParams requestParams = new RequestParams();
            stulogoutdialog(requestParams);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
