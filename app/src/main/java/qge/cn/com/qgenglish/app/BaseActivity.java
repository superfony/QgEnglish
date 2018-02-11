package qge.cn.com.qgenglish.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.http.common.AsyncHttp;
import com.baiyang.android.http.common.ResponseProcessor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
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
    private Intent timerService, reConnectService;
    protected TextToSpeech textToSpeech;
    protected ArrayList<Menu> menuArrayList = new ArrayList<Menu>(); // 网络菜单
    protected Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initNet();

    }

    protected void initTTS() {
        textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Log.w("base", "000000000");
                } else {
                    Toast.makeText(activity, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
//            http=new AsyncHttp(activity);
            http.setRequestCallback(null);
            http.setDataType(AsyncBase.ResponseDataType.XML);
            http.get(icibaurl, params, new WorldHandler(),
                    baseProcessor);
        }
    }


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


    protected synchronized void mp3Playend(final int r) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mp3Box == null)
                    mp3Box = new Mp3Player(activity);
                mp3Box.soundMp3(r);
            }
        }).start();
    }

    protected void stopMp3() {
        if (mp3Box != null) {
            mp3Box.stopSoundMp3();
        }
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

    protected void addHead(String key, String value) {
        http.addHead(key, value);
    }

    protected void startHttpPost(String url, RequestParams requestParams) {
        showPD();
        http.setRequestCallback(requestCallbackBase);
        http.setDataType(AsyncBase.ResponseDataType.JSON);
        http.addHead("authorization", getTocken());
        http.setDebug(true);
        http.post(url, requestParams, null, null);

    }

    // 用于学生 学校登出的操作
    protected void logout(String url, RequestParams requestParams, AsyncBase.RequestCallback requestCallback) {
        showPD();
        http.setRequestCallback(requestCallback);
        http.setDataType(AsyncBase.ResponseDataType.JSON);
        http.addHead("authorization", getTocken());
        http.setDebug(true);
        http.post(url, requestParams, null, null);

    }


    private String getTocken() {
        return ((FonyApplication) activity.getApplication()).tocken;

    }

    //
    protected void startHttpGet(String url, RequestParams requestParams) {
        showPD();
        http.setRequestCallback(requestCallbackBase);
        http.setDataType(AsyncBase.ResponseDataType.JSON);
        http.setDebug(true);
        http.get(url, requestParams, null, null);
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
                System.out.println("request=" + s);
                if (TextUtils.isEmpty(s)) {
                    handlerBase.obtainMessage(0, "没有此用户信息!").sendToTarget(); //
                    return;
                }
                jsonObj = new JSONObject(s);
                request = jsonObj.getString("code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ("200".equals(request)) {
                onSuccessBase(s);
            } else {
                onFailureBase(null, s);
            }
        }
    };

    // 请求失败
    protected void onFailureBase(Throwable throwable, String s) {
        hidePD();
        handlerBase.obtainMessage(0, s).sendToTarget();
    }

    // 有结果
    protected void onSuccessBase(String s) {
        hidePD();
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

    protected String getWordType() {
        String type = null;
        FonyApplication.WordType wordType = ((FonyApplication) activity.getApplication()).wordType;

        switch (wordType) {
            case Small:
                type = TableName.word_small;
                break;
            case Middle:
                type = TableName.word_junior;
                break;
            case High:
                type = TableName.word_high1;
                break;
            case FourLeve:
                type = TableName.word_four;
                break;
            case SixLeve:
                type = TableName.word_six;
                break;
            case ThinkSeleg:
                type = TableName.word_toefl1;
                break;
            case Receiving:
                type = TableName.word_ielts1;
                break;
        }
        return type;
    }

    /**
     * 获取菜单的 方法仅请求菜单的地方调用
     */
    protected void reqMenu() {
        menu = (Menu) activity.getIntent().getSerializableExtra("menu");
        startHttpGet(String.format(RequestUrls.COMMONURL, menu.id), null);// 请求菜单
    }

    protected void resultMenu(String s) {

        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<Menu>>>() {
        }.getType());
        String menus = result.getMessage();
        menuArrayList = (ArrayList) result.getData();
        handlerBase.obtainMessage(1, "").sendToTarget();
    }

    // 实现一个计数器
    //

    //学生登出的操作

    protected void stulogoutdialog(final RequestParams requestParams) {
//          AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//          builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//              @Override
//              public void onClick(DialogInterface dialog, int which) {
//                  dialog.cancel();
//              }
//          }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//              @Override
//              public void onClick(DialogInterface dialog, int which) {
//
//                  // 收集本地用户信息并上传
//
//              }
//          });
//          builder.setTitle("是否确认退出当前学生账号");
//          builder.create().show();

        stuloginout(requestParams);


    }


    AsyncBase.RequestCallback requestCallback = new AsyncBase.RequestCallback() {
        @Override
        public void onSuccess(String s) {
            handlerBase.obtainMessage(3, s).sendToTarget();// 登出的操作
        }

        @Override
        public void onFailure(Throwable throwable, String s) {
            handlerBase.obtainMessage(0, s).sendToTarget();//
        }
    };

    private void stuloginout(RequestParams requestParams) {
        logout(RequestUrls.studentlogout, requestParams, requestCallback);

    }

}
