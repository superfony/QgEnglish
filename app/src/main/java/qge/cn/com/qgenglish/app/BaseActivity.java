package qge.cn.com.qgenglish.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baiyang.android.cache.CacheManager;
import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.http.common.AsyncHttp;
import com.baiyang.android.http.common.ResponseProcessor;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import butterknife.ButterKnife;
import qge.cn.com.qgenglish.WordActivity;
import qge.cn.com.qgenglish.app.bean.Error;
import qge.cn.com.qgenglish.app.bean.User;
import qge.cn.com.qgenglish.app.phrase.FxChoosePhraseAct;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.WorldHandler;
import qge.cn.com.qgenglish.iciba.WorldResp;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;
import qge.cn.com.qgenglish.service.IActivitySupport;
import qge.cn.com.qgenglish.service.ReConnectService;
import qge.cn.com.qgenglish.service.TimerService;

/**
 * Created by fony
 * on 16/6/20.
 */

public class BaseActivity extends Activity implements IActivitySupport {

    protected String TAG = "BaseActivity";
    protected Activity activity;
    protected AsyncHttp http;
    protected ProgressDialog pd;
    private WordBean wordBean = null;
    private Mp3Player mp3Box = null;
    protected Handler handler;
    protected final static String iciba_key = "41DFE6A573E4538D2AB79EA55479D466";
    protected final static String icibaurl = "http://dict-co.iciba.com/api/dictionary.php";
    protected ChooseWordListion chooseWordListion;
    //protected Handler handlerAct;
    private Intent timerService, reConnectService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initNet();
    }


    protected void initNet() {
        if (http == null)
            http = new AsyncHttp(this);
        http.setDebug(true);
    }

    /**
     * 调用金山词霸接口 获取单词信息*
     *
     * @param w
     */
    protected void icibaHttp(String w, Handler handler) {
        this.handler = handler;
        mp3Box = new Mp3Player(activity);
        if (DBManager.getInstance().isWordExist(w)) {
            wordBean = DBManager.getInstance().getWordFromDict(w);
            handler.obtainMessage(0, wordBean).sendToTarget();
        } else {
            RequestParams params = new RequestParams();
            params.put("w", w);
            params.put("key", iciba_key);
            pd = new ProgressDialog(this);
            pd.show();
            http.get(icibaurl, params, new WorldHandler(),
                    baseProcessor);
        }
    }

    protected void initTextToSpeek() {
        textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                } else {
                    Toast.makeText(activity, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private TextToSpeech textToSpeech = null;

    protected void textToSpeek(String w) {
        textToSpeech.setLanguage(Locale.ENGLISH); // 设置语言
        textToSpeech.setPitch(1.0f); //
        textToSpeech.setSpeechRate(1.0f); // 控制语速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(w, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(w, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    protected void distoryTextToSpeech() {
        if (textToSpeech != null)
            textToSpeech.shutdown();
    }

    ResponseProcessor baseProcessor = new ResponseProcessor() {
        @Override
        public void onFailure(Throwable e, String reason) {
            pd.cancel();
            if (TextUtils.isEmpty(reason)) {
                reason = "请求失败";
            }
            Toast.makeText(activity, reason, Toast.LENGTH_LONG).show();
        }

        @Override
        public void processResponse(String response, Object data) {
            Log.i(TAG, "WorldResp=" + response);
            WorldResp resp = (WorldResp) data;
            pd.cancel();
            if ("1".equals(resp.requestcode)) {
                wordBean = new WordBean(resp);
                DBManager.getInstance().insert(wordBean);  // 插入数据库
                handler.obtainMessage(0, wordBean).sendToTarget();
                return;
            } else if ("0".equals(resp.requestcode)) {

            }
        }
    };

    /**
     * @param type 美式 或 英式 发音
     */
    protected synchronized void mp3Play(final String word, final int type) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mp3Box == null)
                    return;
                if (TextUtils.isEmpty(word))
                    return;
                mp3Box.playMusicByWord(word, type);
            }
        }).start();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mp3Box != null)
            mp3Box.isMusicPermitted = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp3Box != null)
            mp3Box.isMusicPermitted = false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    // 用于ACT之间的跳转
    protected Handler handlerBase = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handMessage(msg);
        }
    };

    protected void handMessage(Message msg) {

    }

    protected void showPD() {
        if (pd == null)
            pd = new ProgressDialog(activity);
        pd.show();

    }

    protected void hidePD() {
        if (pd != null)
            pd.cancel();

    }

    //   数据回调的统一的实现

    protected AsyncBase.RequestCallback requestCallbackBase = new AsyncBase.RequestCallback() {

        @Override
        public void onFailure(Throwable throwable, String s) {
            onFailureBase(throwable, s);
        }

        @Override
        public void onSuccess(String s) {
            JSONObject jsonObj = null;
            String request = null;
            try {
                jsonObj = new JSONObject(s);
                request = jsonObj.getString("request");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, request);
            if ("1".equals(request)) {
                onSuccess(s);
            } else if ("0".equals(request)) {
                Gson gson = new Gson();
                Error error = gson.fromJson(s, Error.class);
                handlerBase.obtainMessage(0, error).sendToTarget();
            }


        }
    };

    // 请求失败
    protected void onFailureBase(Throwable throwable, String s) {


    }

    // 有结果
    protected void onSuccessBase(String s) {

    }

    @Override
    public void stopIService() {
        Log.i(TAG, "....stopService....");
        FonyApplication.exercise = false;
        if (timerService != null)
            activity.stopService(timerService);
        timerService = null;


        if (reConnectService != null)
            activity.stopService(reConnectService);
        reConnectService = null;

    }

    @Override
    public void startIService() {
        Log.i(TAG, "....startService....");
        // 定时服务
        timerService = new Intent(activity, TimerService.class);
        activity.startService(timerService);
        // 网络服务
        reConnectService = new Intent(activity, ReConnectService.class);
        activity.startService(reConnectService);
    }

    @Override
    public boolean validateInternet() {
        return false;
    }

    @Override
    public boolean hasInternetConnected() {
        return false;
    }


    protected String getFileToStr(String fileName) {
        StringBuffer temp = new StringBuffer();
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(fileName);
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String tempLine = rd.readLine();
            while (tempLine != null) {
                temp.append(tempLine);
                tempLine = rd.readLine();
            }
        } catch (Exception e) {
        }
        String result = temp.toString();
        Log.w("AsyncHttpRequset", "项目文件内容=" + result);
        return result;
    }
}
