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
        title.setText("初中单词");
        wordMenuThAdapter = new MiddleMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(activity, MiddleWordBsMenu.class);
                        break;
                    case 1:
                        intent.setClass(activity, MiddleWordNjMenu.class);
                        break;
                    case 2:
                        intent.setClass(activity, MiddleWordRjMenu.class);
                        break;
                    case 3:
                        intent.setClass(activity, MiddleWordWyMenu.class);
                        break;
                    case 4:
                        intent.putExtra("tableName", TableName.word_junior);
                        intent.setClass(activity, WordAct.class);
                        break;
                    case 5:
                        break;
                    default:
                        break;

                }

                activity.startActivity(intent);

            }
        });
    }

    private void initData() {


    }

}
