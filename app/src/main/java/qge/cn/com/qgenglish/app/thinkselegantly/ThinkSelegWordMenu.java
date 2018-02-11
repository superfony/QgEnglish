package qge.cn.com.qgenglish.app.thinkselegantly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.receiving.ReceivingMenuAdapter;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.application.FonyApplication;

/**
 * 雅思
 */

public class ThinkSelegWordMenu extends BaseActivity {
    private String TAG = "ThinkSelegWordMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private ReceivingMenuAdapter wordMenuThAdapter;
    private String[] menuArr = {"雅思初级", "雅思中级", "雅思高级"};

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

        title.setText("雅思词汇");
        wordMenuThAdapter = new ReceivingMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));
                switch (position) {
                    case 0:
                        ((FonyApplication) activity.getApplication()).tableDes = "雅思初级";
                        intent.putExtra("tableName", TableName.word_ielts1);
                        break;
                    case 1:
                        ((FonyApplication) activity.getApplication()).tableDes = "雅思中级";
                        intent.putExtra("tableName", TableName.word_ielts2);
                        break;
                    case 2:
                        ((FonyApplication) activity.getApplication()).tableDes = "雅思高级";
                        intent.putExtra("tableName", TableName.word_ielts3);
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
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        resultMenu(s);
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
