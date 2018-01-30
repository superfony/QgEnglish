package qge.cn.com.qgenglish.app.receiving;

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
 * 托福
 */

public class ReceivingWordMenu extends BaseActivity {
    private String TAG = "ReceivingWordMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private ReceivingMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"托福初级", "托福中级", "托福高级"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        title.setText("托福词汇");
        wordMenuThAdapter = new ReceivingMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:

                        intent.putExtra("tableName", TableName.word_toefl1);
                        break;
                    case 1:
                        intent.putExtra("tableName", TableName.word_toefl2);
                        break;
                    case 2:
                        intent.putExtra("tableName", TableName.word_toefl3);
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
