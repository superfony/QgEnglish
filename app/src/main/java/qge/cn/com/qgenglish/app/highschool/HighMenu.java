package qge.cn.com.qgenglish.app.highschool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyang.android.http.basic.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.articel.ArticelMenuAct;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordActC;
import qge.cn.com.qgenglish.app.word.table.Phrase_high;
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
        initData();
        reqMenu();

    }

    private void initData() {
        title.setText("高中课程");
        wordMenuThAdapter = new HighMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));
                switch (position) {
                    case 0:
                        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
                        intent.setClass(activity, HighMenus.class);
                        activity.startActivity(intent);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).tableDes = "高中短语";
                        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.PHRASE;
                        if (!DBManager.getWordManager().isExist(TableName.phrase_high)) {
                            DBManager.getWordManager().create(Phrase_high.class, DBManager.getWordManager().getReadableDatabase());
                        }
                        intent.putExtra("tableName", TableName.phrase_high);
                        intent.setClass(activity, WordActC.class);
                        activity.startActivity(intent);
                        break;
                    case 2:
                        // 高中语法
                        break;
                    case 3:
                        // 重点句型
                        break;
                    case 4:
                        intent.removeExtra("menu");
                        Menu menu = new Menu();
                        menu.id = RequestUrls.high_article;
                        intent.putExtra("menu", menu);
                        intent.setClass(activity, ArticelMenuAct.class);
                        activity.startActivity(intent);
                        break;
                    case 5:
                        // 写作
                        break;
                    case 6:
                        // 高考听力训练
                        break;
                    case 7:
                        // 高考试卷
                        break;
                    case 8:
                        // 专项训练题
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
