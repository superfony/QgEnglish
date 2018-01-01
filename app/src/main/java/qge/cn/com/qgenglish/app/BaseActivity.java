package qge.cn.com.qgenglish.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncHttp;
import com.baiyang.android.http.common.ResponseProcessor;

import butterknife.ButterKnife;
import qge.cn.com.qgenglish.WordActivity;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.WorldHandler;
import qge.cn.com.qgenglish.iciba.WorldResp;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

/**
 * Created by fony
 * on 16/6/20.
 */

public class BaseActivity extends Activity {
    protected String TAG = "BaseActivity";


    protected Activity activity;
    protected AsyncHttp http;
    protected ProgressDialog pd;
    private WordBean wordBean = null;
    private Mp3Player mp3Box = null;
    protected Handler handler;
    protected final static String iciba_key = "41DFE6A573E4538D2AB79EA55479D466";
    protected final static String icibaurl = "http://dict-co.iciba.com/api/dictionary.php";

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
     * 调用金山词霸接口 获取单词信息
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
    protected synchronized void mp3Play(final  String word,final int type) {

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


}
