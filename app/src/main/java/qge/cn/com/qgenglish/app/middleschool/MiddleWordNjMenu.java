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
 * 牛津版
 */

public class MiddleWordNjMenu extends BaseActivity {
    private String TAG = "MiddleWordNjMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private MiddleMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"初中7A", "初中7B", "初中8A",
            "初中8B", "初中9A"};

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
        title.setText("牛津版");
        wordMenuThAdapter = new MiddleMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));
                switch (position) {
                    case 0:
                        ((FonyApplication) activity.getApplication()).tableDes = "牛津版初中7";
                        intent.putExtra("tableName", TableName.word_niujinban_7_1);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).tableDes = "牛津版初中7B";

                        intent.putExtra("tableName", TableName.word_niujinban_7_2);

                        break;
                    case 2:
                        ((FonyApplication) activity.getApplication()).tableDes = "牛津版初中8A";

                        intent.putExtra("tableName", TableName.word_niujinban_8_1);

                        break;
                    case 3:
                        ((FonyApplication) activity.getApplication()).tableDes = "牛津版初中8B";
                        intent.putExtra("tableName", TableName.word_niujinban_8_2);

                        break;
                    case 4:
                        ((FonyApplication) activity.getApplication()).tableDes = "牛津版初中9A";
                        intent.putExtra("tableName", TableName.word_niujinban_9_1);

                        break;
                    case 5:
                        ((FonyApplication) activity.getApplication()).tableDes = "牛津版初中9B";
                        intent.putExtra("tableName", TableName.word_niujinban_9_2);
                        break;
                    default:
                        break;
                }
                intent.setClass(activity, WordAct.class);
                activity.startActivity(intent);

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

        wordMenuThAdapter.updateListView(menuArrayList);
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
    }

}
