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
 * 外研版
 */

public class MiddleWordWyMenu extends BaseActivity {
    private String TAG = "MiddleWordWyMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private MiddleMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"初中7.1", "初中7.2", "初中8.1",
            "初中8.2", "初中9.1", "初中9.2"};

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

        title.setText("外研版");
        wordMenuThAdapter = new MiddleMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));
                switch (position) {
                    case 0:
                        ((FonyApplication) activity.getApplication()).tableDes = "外研版初中7.1";
                        intent.putExtra("tableName", TableName.word_waiyanshe_7_1);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).tableDes = "外研版初中7.2";
                        intent.putExtra("tableName", TableName.word_waiyanshe_7_2);
                        break;
                    case 2:
                        ((FonyApplication) activity.getApplication()).tableDes = "外研版初中8.1";
                        intent.putExtra("tableName", TableName.word_waiyanshe_8_1);
                        break;
                    case 3:
                        ((FonyApplication) activity.getApplication()).tableDes = "外研版初中8.2";
                        intent.putExtra("tableName", TableName.word_waiyanshe_8_2);
                        break;
                    case 4:
                        ((FonyApplication) activity.getApplication()).tableDes = "外研版初中9.1";
                        intent.putExtra("tableName", TableName.word_waiyanshe_9_1);
                        break;
                    case 5:
                        ((FonyApplication) activity.getApplication()).tableDes = "外研版初中9.2";
                        intent.putExtra("tableName", TableName.word_waiyanshe_9_2);
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
        }
    }


}
