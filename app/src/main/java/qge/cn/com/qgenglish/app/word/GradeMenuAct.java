package qge.cn.com.qgenglish.app.word;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.LoginActivity;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.StuLoginActivity;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.articel.ArticelMenuAct;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.schoolinfo.SchoolInfo;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.cache.CacheManager;

/**
 * 小学课程
 * 初中课程
 * 高中课程
 * 四级单词
 * 六级单词
 * 托福单词
 * 雅思课程
 */

public class GradeMenuAct extends BaseActivity {
    @Bind(R.id.menu1)
    Button menu1;
    @Bind(R.id.menu2)
    Button menu2;
    @Bind(R.id.menu3)
    Button menu3;
    @Bind(R.id.menu4)
    Button menu4;
    @Bind(R.id.menu5)
    Button menu5;
    @Bind(R.id.menu6)
    Button menu6;
    @Bind(R.id.menu7)
    Button menu7;
    private String TAG = "GradeMenuAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_menu_act);
        ButterKnife.bind(this);
        startHttpGet(String.format(RequestUrls.COMMONURL, RequestUrls.rootid), null);
    }

    //小学
    @OnClick(R.id.menu1)
    void onclicMenu1() {
        Intent intent = new Intent();
        intent.putExtra("grade", 1);
        intent.putExtra("menu", menuArrayList.get(0));
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 初中
    @OnClick(R.id.menu2)
    void onclicMenu2() {
        Intent intent = new Intent();
        intent.putExtra("grade", 2);
        intent.putExtra("menu", menuArrayList.get(1));
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 高中
    @OnClick(R.id.menu3)
    void onclicMenu3() {

        Intent intent = new Intent();
        intent.putExtra("grade", 3);
        intent.putExtra("menu", menuArrayList.get(2));
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 四级
    @OnClick(R.id.menu4)
    void onclicMenu4() {
        Intent intent = new Intent();
        intent.putExtra("grade", 4);
        intent.putExtra("menu", menuArrayList.get(3));
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 六级
    @OnClick(R.id.menu5)
    void onclicMenu5() {
        Intent intent = new Intent();
        intent.putExtra("grade", 5);
        intent.putExtra("menu", menuArrayList.get(4));
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 托福
    @OnClick(R.id.menu6)
    void onclicMenu6() {
        Intent intent = new Intent();
        intent.putExtra("grade", 6);
        intent.putExtra("menu", menuArrayList.get(5));
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 雅思
    @OnClick(R.id.menu7)
    void onclicMenu7() {
        Intent intent = new Intent();
        intent.putExtra("grade", 7);
        intent.putExtra("menu", menuArrayList.get(6));
        intent.setClass(activity, StuLoginActivity.class);
        activity.startActivity(intent);
    }

    // 点击判断当前是否登录
    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<Menu>>>() {
        }.getType());
        String menus = result.getMessage();
        menuArrayList = (ArrayList) result.getData();
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        switch (msg.what) {
            case 0:
                ToastHelper.toast(activity, msg.obj.toString());
                finish();
            case 1:
                Log.i("", "获取成功");
                break;
            case 3:  // 登出成功
                ToastHelper.toast(activity, msg.obj.toString());
                activity.finish();
                break;
            case 4:
                ToastHelper.toast(activity, msg.obj.toString());
                break;// 登出失败

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 学生退出同步当前用户相关操作信息
//            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // 收集本地用户信息并上传
//
//                }
//            });
//            builder.setTitle("是否确认退出当前学校账号");
//            builder.create().show();
            schoolloginout();
        }

        return true;

    }


    AsyncBase.RequestCallback requestCallback = new AsyncBase.RequestCallback() {
        @Override
        public void onSuccess(String s) {
            handlerBase.obtainMessage(3, s).sendToTarget();// 登出的操作
        }

        @Override
        public void onFailure(Throwable throwable, String s) {
            handlerBase.obtainMessage(4, s).sendToTarget();//
        }
    };

    private void schoolloginout() {
        RequestParams requestParams = new RequestParams();
        SchoolInfo schoolInfo = (SchoolInfo) CacheManager.readObject(activity, "schoolinfo");
        ((FonyApplication) activity.getApplication()).tocken = schoolInfo.getToken();
        logout(RequestUrls.schoollogout, requestParams, requestCallback);
    }




}
