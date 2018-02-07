package qge.cn.com.qgenglish.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.baiyang.android.cache.CacheManager;
import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.http.common.AsyncHttp;
import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.bean.Error;
import qge.cn.com.qgenglish.app.bean.User;
import qge.cn.com.qgenglish.app.schoolinfo.SchoolInfo;
import qge.cn.com.qgenglish.app.schoolinfo.UserInfo;
import qge.cn.com.qgenglish.application.FonyApplication;

public class TimerService extends Service {
    private Context context;
    private AsyncHttp http;
    private String TAG = "TimerService";


    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (FonyApplication.exercise) {
                    try {
                        Thread.sleep(20 * 1000);//
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SchoolInfo schoolInfo = (SchoolInfo) CacheManager.readObject(context, "schoolinfo");
                    int id = schoolInfo.getSchoolInfo().getId();
                    http = new AsyncHttp(context);
                    http.setDebug(true);
                    http.setRequestCallback(requestCallback);
                    http.setDataType(AsyncBase.ResponseDataType.JSON);
                    http.addHead("authorization", schoolInfo.getToken());
                    Log.w(TAG, "-----------timmer start-----------");
                    http.get(String.format(RequestUrls.surplusTime, id).toString(), null, null, null);
                }
            }
        }).start();
    }


    AsyncBase.RequestCallback requestCallback = new AsyncBase.RequestCallback() {
        @Override
        public void onSuccess(String json) {
            Log.w("", json);
            Gson gson = new Gson();
            Type typeToken = new TypeToken<Result<SurplusTime>>() {
            }.getType();
            Result<SurplusTime> result = gson.fromJson(json, typeToken);
            SurplusTime surplusTime = result.getData();
            if (surplusTime.getTimeTotal() < 0) {
                System.out.println(" 时间到了 ");
                handler.obtainMessage(1, "").sendToTarget();

            }
        }

        private Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
//                    System.exit(0);
                    //  showDialog();
                }
            }
        };


        private void showDialog() {

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    System.exit(0);
                }
            });
            builder.setTitle("警告").setMessage("学习时间已不足,请购买时间。点击确定系统将自动关闭!!!");
            builder.create().getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            builder.show();
        }





        @Override
        public void onFailure(Throwable throwable, String s) {
            Log.w("", s);
            //       handler.obtainMessage(0,s).sendToTarget();
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
