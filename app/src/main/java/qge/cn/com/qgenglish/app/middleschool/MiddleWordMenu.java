package qge.cn.com.qgenglish.app.middleschool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordMenuFAct;
import qge.cn.com.qgenglish.application.FonyApplication;

/**
 * 初中单词
 */

public class MiddleWordMenu extends BaseActivity {
    private String TAG = "ThreeMenuAct";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private MiddleMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"北师版", "牛津版", "人教版",
            "外研版", "全国版全部"};

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
        title.setText("初中单词");
        wordMenuThAdapter = new MiddleMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));
                switch (position) {
                    case 0:
                        intent.setClass(activity, MiddleWordRjMenu.class);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).tableDes = "初中短语";
                        intent.putExtra("tableName", TableName.word_junior);
                        intent.setClass(activity, WordAct.class);
                        break;
                    case 2:
                        intent.setClass(activity, MiddleWordBsMenu.class);
                        break;
                    case 3:
                        intent.setClass(activity, MiddleWordWyMenu.class);
                        break;
                    case 4:
                        intent.setClass(activity, MiddleWordNjMenu.class);
                        break;
                    default:
                        break;
                }

                activity.startActivity(intent);

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

        }
    }

}
