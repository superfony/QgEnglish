package qge.cn.com.qgenglish;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.word.WordMenuSecAct;

/**
 * 登录页
 */

public class RegistActivity extends BaseActivity {
    protected String TAG = "RegistActivity";
    @Bind(R.id.username_et)
    EditText usernameEt;
    @Bind(R.id.phone_et)
    EditText phoneEt;
    @Bind(R.id.password_et)
    EditText passwordEt;
    @Bind(R.id.sure_pwd_et)
    EditText surePwdEt;
    @Bind(R.id.regist_btn)
    ImageView registBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgit);
        ButterKnife.bind(this);

    }

    private void init() {
    }

    @OnClick(R.id.regist_btn)
    void login() {
        Intent intent = new Intent();
        intent.setClass(activity, WordMenuSecAct.class);
        activity.startActivity(intent);
    }
}
