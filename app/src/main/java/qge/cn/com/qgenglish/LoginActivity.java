package qge.cn.com.qgenglish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.http.common.AsyncHttp;
import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Pub_method;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.bean.User;
import qge.cn.com.qgenglish.app.schoolinfo.SchoolInfo;
import qge.cn.com.qgenglish.app.word.GradeMenuAct;
import qge.cn.com.qgenglish.app.word.WordMenuSecAct;
import qge.cn.com.qgenglish.application.AppContext;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.cache.CacheManager;

/**
 * 学校账号登录页
 */
public class LoginActivity extends BaseActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SQLiteStudioService.instance().start(this);
    }

    private void init() {
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
        startHttpPost(RequestUrls.schoollogin, requestParams);


    }


    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        switch (msg.what) {
            case 0:
                ToastHelper.toast(activity, msg.obj.toString());
                break;
            case 1:
                startIService();//开启服务
                Intent intent = new Intent();
                intent.setClass(activity, GradeMenuAct.class);
                activity.startActivity(intent);
                ;
                break;

        }
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        Gson gson = new Gson();
        Type typeToken = new TypeToken<Result<SchoolInfo>>() {
        }.getType();
        Result<SchoolInfo> result = gson.fromJson(s, typeToken);
        SchoolInfo schoolInfo = result.getData();
        ((FonyApplication) activity.getApplication()).tocken = schoolInfo.getToken();
        CacheManager.saveObject(activity, schoolInfo, "schoolinfo");
        handlerBase.obtainMessage(1, "").sendToTarget();


    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }


    @OnClick(R.id.regist_btn)
    void regist() {
        Intent intent = new Intent();
        intent.setClass(activity, RegistActivity.class);
        activity.startActivity(intent);
    }

    // 获取设备屏幕信息
    private void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);   // 屏幕宽度(dp)
        int screenHeight = (int) (height / density); // 屏幕高度(dp)

        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
    }

    // 隐藏虚拟按键
    private void hiddNAVIGATION() {
        Window _window;
        _window = getWindow();
        WindowManager.LayoutParams params = _window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        _window.setAttributes(params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SQLiteStudioService.instance().start(this);
        stopIService();
    }
}
