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
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordMenuFAct;

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
        title.setText("外研版");
        wordMenuThAdapter = new MiddleMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.putExtra("tableName", TableName.word_waiyanshe_7_1);
                        break;
                    case 1:
                        intent.putExtra("tableName", TableName.word_waiyanshe_7_2);
                        break;
                    case 2:
                        intent.putExtra("tableName", TableName.word_waiyanshe_8_1);
                        break;
                    case 3:
                        intent.putExtra("tableName", TableName.word_waiyanshe_8_2);
                        break;
                    case 4:
                        intent.putExtra("tableName", TableName.word_waiyanshe_9_1);
                        break;
                    case 5:
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

    private void initData() {


    }

}
