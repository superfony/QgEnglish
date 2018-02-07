package qge.cn.com.qgenglish.app.middleschool;

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
import qge.cn.com.qgenglish.app.articel.ArticelMenuAct;
import qge.cn.com.qgenglish.app.highschool.HighMenuAdapter;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.phrase.PhraseAct;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordMenuFAct;
import qge.cn.com.qgenglish.app.word.table.Phrase_middle;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * 初中
 */

public class MiddleMenu extends BaseActivity {
    private String TAG = "ThreeMenuAct";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private MiddleMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"初中单词", "初中短语", "初中写作",
            "初中语法", "阅读训练", "中考试卷", "重点句型", "专项训练题",
            "初中听力训练", "我的生词本"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        title.setText("初中课程");
        wordMenuThAdapter = new MiddleMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
                        intent.setClass(activity, MiddleWordMenu.class);
                        activity.startActivity(intent);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.PHRASE;
                        if (!DBManager.getWordManager().isExist(TableName.phrase_middle)) {
                            DBManager.getWordManager().create(Phrase_middle.class, DBManager.getWordManager().getReadableDatabase());
                        }
                        intent.putExtra("tableName", TableName.phrase_middle);
                        intent.setClass(activity, WordAct.class);
                        activity.startActivity(intent);
                        break;
                    case 2:

                        break;
                    case 3:
                        break;
                    case 4:
                        // 阅读训练
                        intent.putExtra("articleType", 1);
                        intent.setClass(activity, ArticelMenuAct.class);
                        activity.startActivity(intent);
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:

                        intent.setClass(activity, NewWordChoseAct.class);
                        activity.startActivity(intent);
                        break;
                    default:
                        break;

                }


            }
        });
    }

    private void initData() {


    }

}
