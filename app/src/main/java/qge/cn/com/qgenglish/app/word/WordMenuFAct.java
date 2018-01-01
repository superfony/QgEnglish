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
 * 年级选择菜单
 */

public class WordMenuFAct extends BaseActivity {
    private String TAG = "WordMenuFAct";
    @Bind(R.id.ckxz_lv)
     ListView ckxzLv;
    private WordMenuThAdapter wordMenuThAdapter;
    private  String [] menuArr={"初中七年级(全)词汇","初中八年级上册词汇","初中八年级下册词汇",
            "初中九年级(全)词汇","高中必修(一)词汇"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordmenu);
        ButterKnife.bind(this);
         activity=this;
         wordMenuThAdapter =new WordMenuThAdapter(activity,menuArr);
         ckxzLv.setAdapter(wordMenuThAdapter);
         ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(activity,WordAct.class);
                activity.startActivity(intent);
            }
        });
    }
}
