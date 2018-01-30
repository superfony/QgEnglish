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
import qge.cn.com.qgenglish.app.middleschool.MiddleMenuAdapter;
import qge.cn.com.qgenglish.app.word.WordAct;

/**
 * 外研版
 */

public class HighWordWyMenu extends BaseActivity {
    private String TAG = "HighWordWyMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private HighMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"高中必修(一)", "高中必修(二)", "高中必修(三)",
            "高中必修(四)", "高中必修(五)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        title.setText("外研版");
        wordMenuThAdapter = new HighMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.putExtra("tableName", TableName.word_waiyanshe_compulsory_1);
                        break;
                    case 1:
                        intent.putExtra("tableName", TableName.word_waiyanshe_compulsory_2);
                        break;
                    case 2:
                        intent.putExtra("tableName", TableName.word_waiyanshe_compulsory_3);
                        break;
                    case 3:
                        intent.putExtra("tableName", TableName.word_waiyanshe_compulsory_4);
                        break;
                    case 4:
                        intent.putExtra("tableName", TableName.word_waiyanshe_compulsory_5);
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
