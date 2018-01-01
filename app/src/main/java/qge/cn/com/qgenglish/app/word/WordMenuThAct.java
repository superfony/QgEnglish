package qge.cn.com.qgenglish.app.word;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;

/**
 * 词库选择
 */

public class WordMenuThAct extends BaseActivity {
    private String TAG = "ThreeMenuAct";
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private WordMenuThAdapter wordMenuThAdapter;

    private String[] menuArr = {"课本词汇 (人教版)", "课本词汇 (北师版)", "课本词汇 (牛津版)",
            "课本词汇 (外研版)", "小学词汇", "初中词汇", "高中初级词汇", "高中中级词汇",
            "高中高级词汇", "托福初级词汇", "托福中级词汇", "托福高级词汇",
            "雅思初级词汇", "雅思中级词汇", "雅思高级词汇"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordmenu);
        ButterKnife.bind(this);
        activity = this;
        wordMenuThAdapter = new WordMenuThAdapter(activity, menuArr);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(activity, WordMenuFAct.class);
                activity.startActivity(intent);

            }
        });
    }

    private void initData() {


    }

}
