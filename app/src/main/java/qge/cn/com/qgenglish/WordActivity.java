package qge.cn.com.qgenglish;


/**
 * Created by fony on 2017/11/5.
 * 金山词霸实现的单词发音的操作
 * 金山接口测试用例
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncHttp;
import com.baiyang.android.http.common.ResponseProcessor;
import java.util.ArrayList;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.WorldHandler;
import qge.cn.com.qgenglish.iciba.WorldResp;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

public class WordActivity extends Activity {

    public TextView word_tv = null;
    public TextView pse_tv = null;
    public TextView psa_tv = null;
    public TextView acceptation_tv = null;
    public ImageButton psE_btn = null;
    public ImageButton psA_btn = null;
    public ImageButton search_ib = null;
    public EditText search_et = null;
    public WordBean wordBean = null;
    public Mp3Player mp3Box = null;
    public Handler uiHandler = null;
    protected AsyncHttp http;
    private ProgressDialog pd;
    public final static String iciba_key = "41DFE6A573E4538D2AB79EA55479D466";
    public final static String icibaurl = "http://dict-co.iciba.com/api/dictionary.php";
    public final static String iCiBaURL1 = "http://dict-co.iciba.com/api/dictionary.php?w=";
    public final static String iCiBaURL2 = "&key=" + iciba_key;
    private Activity activity;

    private String TAG = "WordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);
        activity = this;
        initial();

    }

    public void initial() {
        word_tv = (TextView) findViewById(R.id.text_dict_word);
        acceptation_tv = (TextView) findViewById(R.id.text_dict_interpret);
        pse_tv = (TextView) findViewById(R.id.text_dict_phosymbol_eng);
        psa_tv = (TextView) findViewById(R.id.text_dict_phosymbol_usa);

        psE_btn = (ImageButton) findViewById(R.id.image_btn_dict_horn_accent_eng);
        psA_btn = (ImageButton) findViewById(R.id.image_btn_dict_horn_accent_usa);
        search_ib = (ImageButton) findViewById(R.id.image_btn_dict_search);
        search_et = (EditText) findViewById(R.id.edit_text_dict);
        search_et.setOnEditorActionListener(new EditTextDictEditActionLis());
        mp3Box = new Mp3Player(WordActivity.this);
        uiHandler = new Handler(Looper.getMainLooper());
        search_ib.setOnClickListener(new IBDictSearchClickLis());
        psE_btn.setOnClickListener(psE_onClickListener);
        psA_btn.setOnClickListener(psA_onClickListener);
    }

    // 金山词霸需要用get方式进行请求
    private void testIcibaHttp(String w) {
        if (DBManager.getInstance().isWordExist(w)) {
            wordBean = DBManager.getInstance().getWordFromDict(w);
            handler.obtainMessage(0, wordBean).sendToTarget();
        } else {
            RequestParams params = new RequestParams();
            params.put("w", w);
            params.put("key", iciba_key);
            http = new AsyncHttp(this);
            http.setDebug(true);
            pd = new ProgressDialog(this);
            pd.show();
            http.get(icibaurl, params, new WorldHandler(),
                    processor);
        }

    }

    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                WordBean wordBean = (WordBean) msg.obj;
                String word = wordBean.getKey();
                String psE_value = wordBean.getPsE();
                String psA_value = wordBean.getPsA();
                String acceptation_value = wordBean.getAcceptation();
                uiHandler.post(new UiRunnable(word, psE_value, psA_value, acceptation_value, null, null));

            } else if (msg.what == 1) {


            }
        }
    };

    ResponseProcessor processor = new ResponseProcessor() {
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


    public void showAddDialog() {

        AlertDialog dialog = new AlertDialog.Builder(WordActivity.this).create();
        dialog.show();
        Window window = dialog.getWindow();
//        window.setContentView(R.layout.dialog_if_layout);
//        buttonDictDialogConfirm = (Button) window.findViewById(R.id.dialog_confirm);
//        buttonDictDialogCancel = (Button) window.findViewById(R.id.dialog_cancel);
//        buttonDictDialogConfirm.setOnClickListener(new BDictDialogConfirmClickLis(dialog));
//        buttonDictDialogCancel.setOnClickListener(new BDictDialogCancelClickLis(dialog));
//        TextView dialogText = (TextView) window.findViewById(R.id.dialog_text);
//        dialogText.setText("把" + searchedWord + "添加到单词本?");

    }


    @Override
    protected void onStart() {
        super.onStart();
        mp3Box.isMusicPermitted = true;
    }

    @Override
    protected void onPause() {
        mp3Box.isMusicPermitted = false;
        super.onPause();
    }


    public class UiRunnable implements Runnable {
        String key = null;
        String pse_value = null;
        String psa_value = null;
        String interpret = null;


        public UiRunnable(String searchStr, String phoSymEng,
                          String phoSymUSA, String interpret, ArrayList<String> sentList,
                          ArrayList<String> sentInChineseList) {
            super();
            this.key = searchStr;
            this.pse_value = "英[" + phoSymEng + "]";
            this.psa_value = "美[" + phoSymUSA + "]";
            this.interpret = interpret;
        }


        @Override
        public void run() {
            word_tv.setText(key);
            pse_tv.setText(pse_value);
            psa_tv.setText(psa_value);
            acceptation_tv.setText(interpret);
        }

    }


    OnClickListener psE_onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mp3Box.playMusicByWord(wordBean.getKey(), Mp3Player.ENGLISH_ACCENT);
                }
            }).start();

        }
    };

    OnClickListener psA_onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mp3Box.playMusicByWord(wordBean.getKey(), Mp3Player.USA_ACCENT);
                }
            }).start();

        }
    };


    class IBDictAddWordToGlossaryClickLis implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            showAddDialog();
        }

    }


    class EditTextDictEditActionLis implements OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
            if (arg1 == EditorInfo.IME_ACTION_SEARCH || arg2 != null && arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                startSearch();
                return true;
            }
            return false;
        }
    }


    class IBDictSearchClickLis implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            startSearch();
        }
    }

    // 查找单词
    public void startSearch() {
        search_et.setText("you");
        String str = search_et.getText().toString();
        if (TextUtils.isEmpty(str))
            return;
        testIcibaHttp(str);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search_et.getWindowToken(), 0);
        search_et.setText("");
    }
}
