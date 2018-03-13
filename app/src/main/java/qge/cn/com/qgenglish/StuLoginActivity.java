package qge.cn.com.qgenglish;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Pub_method;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.bean.User;
import qge.cn.com.qgenglish.app.elementschool.EleMenu;
import qge.cn.com.qgenglish.app.fourlevel.FourLevelMenu;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.highschool.HighMenu;
import qge.cn.com.qgenglish.app.middleschool.MiddleMenu;
import qge.cn.com.qgenglish.app.receiving.ReceivingMenu;
import qge.cn.com.qgenglish.app.schoolinfo.SchoolInfo;
import qge.cn.com.qgenglish.app.schoolinfo.UserInfo;
import qge.cn.com.qgenglish.app.sixlevel.SixLevelMenu;
import qge.cn.com.qgenglish.app.thinkselegantly.ThinkSelegMenu;
import qge.cn.com.qgenglish.app.word.GradeMenuAct;
import qge.cn.com.qgenglish.app.word.WordMenuSecAct;
import qge.cn.com.qgenglish.app.word.table.Tj;
import qge.cn.com.qgenglish.application.AppContext;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.cache.CacheManager;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * 学生登录页
 */
public class StuLoginActivity extends BaseActivity {
    protected String TAG = "LoginActivity";
    @Bind(R.id.username_et)
    EditText usernameEt;
    @Bind(R.id.password_et)
    EditText passwordEt;
    @Bind(R.id.login_btn)
    ImageView loginBtn;
    @Bind(R.id.remember)
    CheckBox remember;
    @Bind(R.id.autologin)
    CheckBox autologin;
    @Bind(R.id.login_rel_lay)
    RelativeLayout loginRelLay;
    @Bind(R.id.activity_login)
    RelativeLayout activityLogin;
    @Bind(R.id.regist_btn)
    ImageView registBtn;

    private int grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_stu);
        ButterKnife.bind(this);
        registBtn.setVisibility(View.VISIBLE);
        Intent intent = this.getIntent();
        grade = intent.getIntExtra("grade", 0);
        menu = (Menu) intent.getSerializableExtra("menu");
        initTTS();

        UserInfo userInfo = (UserInfo) CacheManager.readObject(activity, "userinfo");
        if (userInfo != null) {
            usernameEt.setText(userInfo.getUserInfo().getUserName());
            passwordEt.setText(userInfo.getUserInfo().getPassword());
        }

    }


    @OnClick(R.id.login_btn)
    void login() {
        String username = usernameEt.getText().toString();
        String pwd = passwordEt.getText().toString();
        if (TextUtils.isEmpty(username)) {
            AppContext.showToast("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            AppContext.showToast("请输入密码");
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", username);
        requestParams.put("password", pwd);
        requestParams.put("padId", Pub_method.getDeviceID(activity));//
        startHttpPost(RequestUrls.studentlogin, requestParams);


    }

    private void reactAct() {
        Intent intent = new Intent();
        switch (grade) {
            case 1:
                ((FonyApplication) activity.getApplication()).wordType = FonyApplication.WordType.Small;
                intent.setClass(activity, EleMenu.class);
                break;
            case 2:
                ((FonyApplication) activity.getApplication()).wordType = FonyApplication.WordType.Middle;
                intent.setClass(activity, MiddleMenu.class);
                break;
            case 3:
                ((FonyApplication) activity.getApplication()).wordType = FonyApplication.WordType.High;
                intent.setClass(activity, HighMenu.class);
                break;
            case 4:
                ((FonyApplication) activity.getApplication()).wordType = FonyApplication.WordType.FourLeve;
                intent.setClass(activity, FourLevelMenu.class);
                break;
            case 5:
                ((FonyApplication) activity.getApplication()).wordType = FonyApplication.WordType.SixLeve;
                intent.setClass(activity, SixLevelMenu.class);
                break;
            case 6:
                ((FonyApplication) activity.getApplication()).wordType = FonyApplication.WordType.Receiving;
                intent.setClass(activity, ReceivingMenu.class);
                break;
            case 7:
                ((FonyApplication) activity.getApplication()).wordType = FonyApplication.WordType.ThinkSeleg;
                intent.setClass(activity, ThinkSelegMenu.class);
                break;
            default:
                break;
        }
        intent.putExtra("menu", menu);
        activity.startActivity(intent);
    }


    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        switch (msg.what) {
            case 0:
                ToastHelper.toast(activity, msg.obj.toString());
                break;
            case 1:
                textToSpeek("");
                reactAct();
                break;

        }
    }


    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        Gson gson = new Gson();
        Type typeToken = new TypeToken<Result<UserInfo>>() {
        }.getType();
        Result<UserInfo> result = gson.fromJson(s, typeToken);
        UserInfo userInfo = result.getData();
        CacheManager.saveObject(activity, userInfo, "userinfo");
        ((FonyApplication) activity.getApplication()).tocken = userInfo.getToken();
        ((FonyApplication) activity.getApplication()).userinfo = userInfo;
        //
        if (!DBManager.getWordManager().isExist(TableName.tongj)) {
            DBManager.getWordManager().create(Tj.class, DBManager.getWordManager().getReadableDatabase());
        }
        handlerBase.obtainMessage(1, "").sendToTarget();
    }

    @OnClick(R.id.regist_btn)
    void regist() {
        Intent intent = new Intent();
        intent.setClass(activity, RegistActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
