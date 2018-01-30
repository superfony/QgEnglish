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
 * 北师版
 */

public class MiddleWordBsMenu extends BaseActivity {
    private String TAG = "MiddleWordBsMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private MiddleMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"初中7.1", "初中7.2", "初中8.1",
            "初中8.2", "初中9.1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        title.setText("北师版");
        wordMenuThAdapter = new MiddleMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.putExtra("tableName", TableName.word_bnu_7_1);
                        break;
                    case 1:
                        intent.putExtra("tableName", TableName.word_bnu_7_2);

                        break;
                    case 2:
                        intent.putExtra("tableName", TableName.word_bnu_8_1);

                        break;
                    case 3:
                        intent.putExtra("tableName", TableName.word_bnu_8_2);

                        break;
                    case 4:
                        intent.putExtra("tableName", TableName.word_bnu_9_0);

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
