package qge.cn.com.qgenglish.app.highschool;

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
import qge.cn.com.qgenglish.app.middleschool.MiddleWordMenu;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.phrase.PhraseAct;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordMenuFAct;
import qge.cn.com.qgenglish.app.word.WordMenuThAdapter;
import qge.cn.com.qgenglish.app.word.table.Phrase_high;
import qge.cn.com.qgenglish.app.word.table.Phrase_middle;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * 高中
 */

public class HighMenu extends BaseActivity {
    private String TAG = "HighMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private HighMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"高中单词", "高中短语", "高中写作",
            "高中语法", "重要句型", "阅读训练", "重要句型", "专项训练题",
            "高考试卷", "高考英语听力训练", "我的生词本"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        title.setText("高中课程");
        wordMenuThAdapter = new HighMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
                        intent.setClass(activity, HighMenus.class);
                        activity.startActivity(intent);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.PHRASE;
                        if (!DBManager.getWordManager().isExist(TableName.phrase_high)) {
                            DBManager.getWordManager().create(Phrase_high.class, DBManager.getWordManager().getReadableDatabase());
                        }
                        intent.putExtra("tableName", TableName.phrase_high);
                        intent.setClass(activity, WordAct.class);
                        activity.startActivity(intent);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        intent.putExtra("articleType", 2);
                        intent.setClass(activity, ArticelMenuAct.class);
                        activity.startActivity(intent);
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;

                    case 10:
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
