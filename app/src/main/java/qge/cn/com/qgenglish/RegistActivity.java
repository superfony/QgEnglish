package qge.cn.com.qgenglish;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.word.WordMenuSecAct;
import qge.cn.com.qgenglish.application.AppContext;

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
    void regist() {
        String name = usernameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String pwd = passwordEt.getText().toString();
        String pwd_sure = surePwdEt.getText().toString();
        Pattern pattern = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])[0-9]{7}[1-9]");

        if (TextUtils.isEmpty(name)) {
            AppContext.showToast("请填写姓名");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            AppContext.showToast("请填写电话");
            return;
        }

        if (!pattern.matcher(phone).find()) {
            AppContext.showToast("请填写正确的手机号码");
            return;
        }



        Intent intent = new Intent();
        intent.setClass(activity, WordMenuSecAct.class);
        activity.startActivity(intent);
    }
}
