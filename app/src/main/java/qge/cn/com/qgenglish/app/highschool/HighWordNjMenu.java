package qge.cn.com.qgenglish.app.highschool;

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
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.middleschool.MiddleMenuAdapter;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.application.FonyApplication;

/**
 * 牛津版
 */

public class HighWordNjMenu extends BaseActivity {
    private String TAG = "MiddleWordNjMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private HighMenuAdapter wordMenuThAdapter;
    private String[] menuArr = {"高中选修(一)", "高中选修(二)", "高中选修(三)",
            "高中选修(四)", "高中选修(五)", "高中选修(六)", "高中选修(七)", "高中选修(八)",
            "高中选修(九)", "高中选修(十)", "高中选修(十一)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        title.setText("牛津版");
        initData();
        reqMenu();
    }

    private void initData() {
        wordMenuThAdapter = new HighMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));
                switch (position) {
                    case 0:
                        intent.putExtra("tableName", TableName.word_compulsory_1);
                        break;
                    case 1:
                        intent.putExtra("tableName", TableName.word_compulsory_2);

                        break;
                    case 2:
                        intent.putExtra("tableName", TableName.word_compulsory_3);

                        break;
                    case 3:
                        intent.putExtra("tableName", TableName.word_compulsory_4);

                        break;
                    case 4:
                        intent.putExtra("tableName", TableName.word_compulsory_5);

                        break;
                    case 5:
                        intent.putExtra("tableName", TableName.word_compulsory_6);
                        break;
                    case 6:
                        intent.putExtra("tableName", TableName.word_compulsory_7);
                        break;
                    case 7:

                        intent.putExtra("tableName", TableName.word_compulsory_8);
                        break;
                    case 8:

                        intent.putExtra("tableName", TableName.word_compulsory_9);
                        break;
                    case 9:
                        intent.putExtra("tableName", TableName.word_compulsory_10);
                        break;
                    case 10:
                        intent.putExtra("tableName", TableName.word_compulsory_11);
                        break;
                    default:
                        break;
                }
                ((FonyApplication) activity.getApplication()).tableDes = "牛津版" + menuArrayList.get(position).menuName;
                intent.setClass(activity, WordAct.class);
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
