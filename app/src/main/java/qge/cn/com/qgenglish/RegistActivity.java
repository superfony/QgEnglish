package qge.cn.com.qgenglish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baiyang.android.cache.CacheManager;
import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.bean.Error;
import qge.cn.com.qgenglish.app.bean.User;
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

//        if (TextUtils.isEmpty(name)) {
//            AppContext.showToast("请填写姓名");
//            return;
//        }
//
//        if (TextUtils.isEmpty(phone)) {
//            AppContext.showToast("请填写电话");
//            return;
//        }
//
//        if (!pattern.matcher(phone).find()) {
//            AppContext.showToast("请填写正确的手机号码");
//            return;
//        }
//
//        if (TextUtils.isEmpty(pwd)) {
//            AppContext.showToast("密码不能为空");
//            return;
//        }
//
//        if (TextUtils.isEmpty(pwd_sure)) {
//            AppContext.showToast("确认密码不能为空");
//            return;
//        }
//
//        if(!pwd.equals(pwd_sure)){
//            AppContext.showToast("两次输入密码不一致");
//            return;
//        }
        showPD();
        RequestParams requestParams = new RequestParams();
        requestParams.put("name", name);
        requestParams.put("phone", phone);
        requestParams.put("pwd", pwd);
        requestParams.put("pwd_sure", pwd_sure);
        http.setRequestCallback(requestCallbackBase);
        http.setDataType(AsyncBase.ResponseDataType.JSON);
        http.post(RequestUrls.regist, requestParams, null, null);
    }

    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        hidePD();
        switch (msg.what) {
            case 1:
                Intent intent = new Intent();
                intent.setClass(activity, LoginActivity.class);  // 注册成功后的跳转
                activity.startActivity(intent);
                break;
            case 0:
                AppContext.showToast(msg.toString());
                break;
        }
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
        handlerBase.obtainMessage(0, s).sendToTarget();
    }

    // 有数据返回的 注册接口的特殊
    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        //Gson gson=new Gson();
        handlerBase.obtainMessage(1, s).sendToTarget();
    }
}
