package qge.cn.com.qgenglish.app.middleschool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyang.android.http.basic.RequestParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.articel.ArticelMenuAct;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.sentence.SentenceAct;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordActC;
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
        initDate();
        reqMenu();
    }

    private void initDate() {
        title.setText("初中课程");
        wordMenuThAdapter = new MiddleMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));

                switch (position) {
                    case 0:

                        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
                        intent.setClass(activity, MiddleWordMenu.class);
                        activity.startActivity(intent);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).tableDes = "初中短语";
                        ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.PHRASE;
                        if (!DBManager.getWordManager().isExist(TableName.phrase_middle)) {
                            DBManager.getWordManager().create(Phrase_middle.class, DBManager.getWordManager().getReadableDatabase());
                        }
                        intent.putExtra("tableName", TableName.phrase_middle);
                        intent.setClass(activity, WordActC.class);
                        activity.startActivity(intent);
                        break;
                    case 2:
                        // 初中语法
                        ((FonyApplication) activity.getApplication()).articleOrListener = FonyApplication.ArticleOrListener.ARTICLE;
                        intent.setClass(activity, SentenceAct.class);
                        activity.startActivity(intent);
                        break;
                    case 3:
                        // 重点句型
                        ((FonyApplication) activity.getApplication()).articleOrListener = FonyApplication.ArticleOrListener.ARTICLE;
                        intent.setClass(activity, SentenceAct.class);
                        activity.startActivity(intent);
                        break;
                    case 4:
                        //初中写作
                        ((FonyApplication) activity.getApplication()).articleOrListener = FonyApplication.ArticleOrListener.ARTICLE;
                        intent.setClass(activity, SentenceAct.class);
                        activity.startActivity(intent);
                        break;
                    case 5:
                        // 阅读训练
                        intent.removeExtra("menu");
                        Menu menus = new Menu();
                        menus.id = RequestUrls.middle_article;
                        intent.putExtra("menu", menus);
                        intent.setClass(activity, ArticelMenuAct.class);
                        activity.startActivity(intent);
                        break;
                    case 6:
                        // 专项训练题
                        ((FonyApplication) activity.getApplication()).articleOrListener = FonyApplication.ArticleOrListener.ARTICLE;
                        intent.setClass(activity, SentenceAct.class);
                        activity.startActivity(intent);
                        break;
                    case 7:
                        // 初中听力训练
                        ((FonyApplication) activity.getApplication()).articleOrListener = FonyApplication.ArticleOrListener.LISTENER;
                        intent.setClass(activity, SentenceAct.class);
                        activity.startActivity(intent);
                        break;
                    case 8:
                        // 中考试卷
                        ((FonyApplication) activity.getApplication()).articleOrListener = FonyApplication.ArticleOrListener.LISTENER;
                        intent.setClass(activity, SentenceAct.class);
                        activity.startActivity(intent);
                        break;
                    case 9:
                        // 生词本
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
        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<Menu>>>() {
        }.getType());
        String menus = result.getMessage();
        menuArrayList = (ArrayList) result.getData();
        handlerBase.obtainMessage(1, "").sendToTarget();
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
