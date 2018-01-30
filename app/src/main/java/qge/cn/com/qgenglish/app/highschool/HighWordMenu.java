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
import qge.cn.com.qgenglish.app.middleschool.MiddleWordBsMenu;
import qge.cn.com.qgenglish.app.middleschool.MiddleWordNjMenu;
import qge.cn.com.qgenglish.app.middleschool.MiddleWordRjMenu;
import qge.cn.com.qgenglish.app.middleschool.MiddleWordWyMenu;
import qge.cn.com.qgenglish.app.word.WordAct;

/**
 * 高中单词
 */

public class HighWordMenu extends BaseActivity {
    private String TAG = "ThreeMenuAct";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private HighMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"北师版", "牛津版", "人教版",
            "外研版", "高中词汇"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        title.setText("高中单词");
        wordMenuThAdapter = new HighMenuAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(activity, HighWordBsMenu.class);
                        break;
                    case 1:
                        intent.setClass(activity, HighWordNjMenu.class);
                        break;
                    case 2:
                        intent.setClass(activity, HighWordRjMenu.class);
                        break;
                    case 3:
                        intent.setClass(activity, HighWordWyMenu.class);
                        break;
                    case 4:
                        intent.setClass(activity, HighWordHighMenu.class);
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
