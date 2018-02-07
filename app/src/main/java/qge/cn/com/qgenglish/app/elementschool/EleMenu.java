package qge.cn.com.qgenglish.app.elementschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.phrase.PhraseAct;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordMenuFAct;
import qge.cn.com.qgenglish.app.word.table.Phrase_middle;
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
        title.setText("小学课程");
        wordMenuThAdapter = new EleMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();

                if (position == 0) {
                    ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
                    intent.putExtra("tableName", TableName.word_small);
                    intent.setClass(activity, WordAct.class);
                    activity.startActivity(intent);
                } else if (position == 1) {
                    ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.PHRASE;
                    if (!DBManager.getWordManager().isExist(TableName.phrase_small)) {
                        DBManager.getWordManager().create(Phrase_small.class, DBManager.getWordManager().getReadableDatabase());
                    }
                    intent.putExtra("tableName", TableName.phrase_small);
                    intent.setClass(activity, WordAct.class);
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

    private void initData() {


    }

}
