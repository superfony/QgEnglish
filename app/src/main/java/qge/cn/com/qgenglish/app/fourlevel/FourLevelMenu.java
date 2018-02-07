package qge.cn.com.qgenglish.app.fourlevel;

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
import qge.cn.com.qgenglish.app.highschool.HighMenuAdapter;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.newword.NewWordbeanS;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordMenuFAct;
import qge.cn.com.qgenglish.app.word.table.Word_four;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * 英语四级
 */

public class FourLevelMenu extends BaseActivity {
    private String TAG = "FourLevelMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private HighMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"四级词汇", "我的生词本"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        title.setText("英语四级");
        wordMenuThAdapter = new HighMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                if (position == 0) {
                    if (!DBManager.getWordManager().isExist(TableName.word_four)) {
                        DBManager.getWordManager().create(Word_four.class, DBManager.getWordManager().getReadableDatabase());
                    }
                    ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
                    intent.putExtra("tableName", TableName.word_four);
                    intent.setClass(activity, WordAct.class);
                } else if (position == 1) {

                    intent.setClass(activity, NewWordChoseAct.class);
                }

                activity.startActivity(intent);

            }
        });
    }

    private void initData() {


    }

}
