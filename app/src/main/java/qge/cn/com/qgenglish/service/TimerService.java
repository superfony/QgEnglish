package qge.cn.com.qgenglish.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.baiyang.android.cache.CacheManager;
import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.http.common.AsyncHttp;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.bean.Error;
import qge.cn.com.qgenglish.app.bean.User;
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
                        Thread.sleep(10 * 1000);//
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RequestParams requestParams = new RequestParams();
                    // User user = (User) CacheManager.readObject(context, "user");
                    requestParams.put("userName", "school");//user.getUserid()
                    requestParams.put("password", "test");
                    requestParams.put("padId", "test");
                    http = new AsyncHttp(context);
                    http.setDebug(true);
                    http.setRequestCallback(requestCallback);
                    http.setDataType(AsyncBase.ResponseDataType.JSON);
                    Log.w(TAG, "-----------timmer start-----------");
                    http.post(RequestUrls.login, requestParams, null, null);
                }
            }
        }).start();
    }


    AsyncBase.RequestCallback requestCallback = new AsyncBase.RequestCallback() {
        @Override
        public void onSuccess(String json) {
            Log.w(TAG, json);
            JSONObject jsonObj = null;
//            String request = null;
            Gson gson = new Gson();

        }

        @Override
        public void onFailure(Throwable throwable, String s) {
//            handler.obtainMessage(0,s).sendToTarget();
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
