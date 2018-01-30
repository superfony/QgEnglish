package qge.cn.com.qgenglish;

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
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Pub_method;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.bean.User;
import qge.cn.com.qgenglish.app.elementschool.EleMenu;
import qge.cn.com.qgenglish.app.fourlevel.FourLevelMenu;
import qge.cn.com.qgenglish.app.highschool.HighMenu;
import qge.cn.com.qgenglish.app.middleschool.MiddleMenu;
import qge.cn.com.qgenglish.app.receiving.ReceivingMenu;
import qge.cn.com.qgenglish.app.sixlevel.SixLevelMenu;
import qge.cn.com.qgenglish.app.thinkselegantly.ThinkSelegMenu;
import qge.cn.com.qgenglish.app.word.GradeMenuAct;
import qge.cn.com.qgenglish.app.word.WordMenuSecAct;
import qge.cn.com.qgenglish.application.AppContext;

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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        registBtn.setVisibility(View.VISIBLE);

        Intent intent = this.getIntent();
        grade = intent.getIntExtra("grade", 0);
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
        requestParams.put("userName", "school");
        requestParams.put("password", "test");
        requestParams.put("padId", Pub_method.getDeviceID(activity));//
//        http = new AsyncHttp(this);
//        pd = new ProgressDialog(this);
//        pd.show();
//        http.setDebug(true);
//        http.setRequestCallback(requestCallback);
//        http.setDataType(AsyncBase.ResponseDataType.JSON);
//        http.post(RequestUrls.login, requestParams, null, null);
//        startIService();//开启服务

// 学生登录成功后进行分离
        /**
         * 传递点击标示
         */

        Intent intent = new Intent();

        switch (grade) {
            case 1:
                intent.setClass(activity, EleMenu.class);
                break;
            case 2:
                intent.setClass(activity, MiddleMenu.class);
                break;
            case 3:
                intent.setClass(activity, HighMenu.class);
                break;
            case 4:
                intent.setClass(activity, FourLevelMenu.class);
                break;
            case 5:
                intent.setClass(activity, SixLevelMenu.class);
                break;
            case 6:
                intent.setClass(activity, ReceivingMenu.class);
                break;
            case 7:
                intent.setClass(activity, ThinkSelegMenu.class);
                break;
            default:
                break;
        }

        activity.startActivity(intent);

    }

    AsyncBase.RequestCallback requestCallback = new AsyncBase.RequestCallback() {
        @Override
        public void onSuccess(String json) {
            Log.i(TAG, json);
//            JSONObject jsonObj = null;
//            try {
//                jsonObj = new JSONObject(json);
//                code = jsonObj.getInt("code");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            Gson gson = new Gson();
//            Type typeToken= new TypeToken<Result<User>>() {}.getType();
//            Result<User> result = gson.fromJson(json, typeToken);
            Result result = gson.fromJson(json, Result.class);
            int code = result.getCode();
            String message = result.getMessage();
            Log.i(TAG, "" + code + "message=" + message);
            if (code == 200) {  // 200返回正确的结果
//                User user = gson.fromJson(json, User.class);
//                User user= result.getData();
//                Log.i(TAG, user.toString());
//                CacheManager.saveObject(activity, user, "user");
//                user = (User) CacheManager.readObject(activity, "user");
                User user = new User();
                Log.i(TAG, user.toString());
                handler.obtainMessage(1, user).sendToTarget();
            } else {
                handler.obtainMessage(0, result.getMessage()).sendToTarget();
            }
        }

        @Override
        public void onFailure(Throwable throwable, String s) {

            handler.obtainMessage(0, s).sendToTarget();
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.cancel();
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    AppContext.showToast(msg.obj.toString());
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
//          stopIService();
    }
}
