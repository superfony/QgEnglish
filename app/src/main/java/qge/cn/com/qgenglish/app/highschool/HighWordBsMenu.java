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
 * 北师版
 */

public class HighWordBsMenu extends BaseActivity {
    private String TAG = "HighWordBsMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private HighMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"高中必修(一)", "高中必修(二)", "高中必修(三)",
            "高中必修(四)", "高中必修(五)", "高中选修(六)", "高中选修(七)", "高中选修(八)"};

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

        title.setText("北师版");
        wordMenuThAdapter = new HighMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));
                switch (position) {
                    case 0:
                        ((FonyApplication) activity.getApplication()).tableDes = "北师版高中1";
                        intent.putExtra("tableName", TableName.word_bnu_compulsory_1);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).tableDes = "北师版高中2";
                        intent.putExtra("tableName", TableName.word_bnu_compulsory_2);
                        break;
                    case 2:
                        ((FonyApplication) activity.getApplication()).tableDes = "北师版高中3";
                        intent.putExtra("tableName", TableName.word_bnu_compulsory_3);
                        break;
                    case 3:
                        ((FonyApplication) activity.getApplication()).tableDes = "北师版高中4";
                        intent.putExtra("tableName", TableName.word_bnu_compulsory_4);
                        break;
                    case 4:
                        ((FonyApplication) activity.getApplication()).tableDes = "北师版高中5";
                        intent.putExtra("tableName", TableName.word_bnu_compulsory_5);
                        break;
                    case 5:
                        ((FonyApplication) activity.getApplication()).tableDes = "北师版高中6";
                        intent.putExtra("tableName", TableName.word_bnu_selective_6);
                        break;
                    case 6:
                        ((FonyApplication) activity.getApplication()).tableDes = "北师版高中7";
                        intent.putExtra("tableName", TableName.word_bnu_selective_7);
                        break;
                    case 7:
                        ((FonyApplication) activity.getApplication()).tableDes = "北师版高中8";
                        intent.putExtra("tableName", TableName.word_bnu_selective_8);
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
