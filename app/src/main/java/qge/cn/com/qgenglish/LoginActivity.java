package qge.cn.com.qgenglish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.baiyang.android.cache.CacheManager;
import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.http.common.AsyncHttp;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.bean.Error;
import qge.cn.com.qgenglish.app.bean.User;
import qge.cn.com.qgenglish.app.word.WordMenuSecAct;

/**
 * 登录页
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
    }

    private void init() {
    }

    @OnClick(R.id.login_btn)
    void login() {
        String pwd = passwordEt.getText().toString();
        String username = usernameEt.getText().toString();
        RequestParams requestParams = new RequestParams();
//        http = new AsyncHttp(this);
//        pd = new ProgressDialog(this);
//        pd.show();
//        http.setDebug(true);
//        http.setRequestCallback(requestCallback);
//        http.setDataType(AsyncBase.ResponseDataType.JSON);
//        http.post(RequestUrls.Login, requestParams, null, null);
        Intent intent = new Intent();
        intent.setClass(activity, WordMenuSecAct.class);
        activity.startActivity(intent);
    }

    AsyncBase.RequestCallback requestCallback = new AsyncBase.RequestCallback() {
        @Override
        public void onSuccess(String json) {
            Log.i(TAG, json);
            JSONObject jsonObj = null;
            String request = null;
            Gson gson = new Gson();
            try {
                jsonObj = new JSONObject(json);
                request = jsonObj.getString("request");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, request);
            if ("1".equals(request)) {
                User user = gson.fromJson(json, User.class);
                Log.i(TAG, user.toString());
                CacheManager.saveObject(activity,user, "user");
                user=(User)CacheManager.readObject(activity,"user");
                Log.i(TAG, user.toString());
                handler.obtainMessage(1, user).sendToTarget();
            } else if ("0".equals(request)) {
                Error error = gson.fromJson(json, Error.class);
                handler.obtainMessage(0, error).sendToTarget();
            }
        }

        @Override
        public void onFailure(Throwable throwable, String s) {
            handler.obtainMessage(0).sendToTarget();
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.cancel();
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    Intent intent = new Intent();
                    intent.setClass(activity, WordMenuSecAct.class);
                    activity.startActivity(intent);
                    break;
            }
        }
    };

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
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)

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
}
