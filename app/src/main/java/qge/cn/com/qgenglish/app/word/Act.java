package qge.cn.com.qgenglish.app.word;

import android.os.Bundle;

import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;

public class Act extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordmenu);
        ButterKnife.bind(this);
        activity = this;
    }
}
