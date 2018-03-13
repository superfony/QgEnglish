package qge.cn.com.qgenglish.app.sentence;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.articel.ArticelPopListener;
import qge.cn.com.qgenglish.app.articel.ArticelPopWindow;
import qge.cn.com.qgenglish.app.articel.question.ArticelBean;
import qge.cn.com.qgenglish.app.articel.question.QuestionText;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.cache.CacheManager;
import qge.cn.com.qgenglish.view.CountdownTextView;

/**
 * 专项训练
 */

public class SpecialAct extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.time_count)
    CountdownTextView timeCount;
    @Bind(R.id.articel_content)
    TextView articelContent;
    @Bind(R.id.question1)
    TextView question1_tv;
    @Bind(R.id.radio_btn_A1)
    RadioButton radioBtnA1;
    @Bind(R.id.radio_btn_B1)
    RadioButton radioBtnB1;
    @Bind(R.id.radio_btn_C1)
    RadioButton radioBtnC1;
    @Bind(R.id.radio_btn_D1)
    RadioButton radioBtnD1;
    @Bind(R.id.question2)
    TextView question2_tv;
    @Bind(R.id.radio_btn_A2)
    RadioButton radioBtnA2;
    @Bind(R.id.radio_btn_B2)
    RadioButton radioBtnB2;
    @Bind(R.id.radio_btn_C2)
    RadioButton radioBtnC2;
    @Bind(R.id.radio_btn_D2)
    RadioButton radioBtnD2;
    @Bind(R.id.question3)
    TextView question3_tv;
    @Bind(R.id.radio_btn_A3)
    RadioButton radioBtnA3;
    @Bind(R.id.radio_btn_B3)
    RadioButton radioBtnB3;
    @Bind(R.id.radio_btn_C3)
    RadioButton radioBtnC3;
    @Bind(R.id.radio_btn_D3)
    RadioButton radioBtnD3;
    @Bind(R.id.question4)
    TextView question4_tv;
    @Bind(R.id.radio_btn_A4)
    RadioButton radioBtnA4;
    @Bind(R.id.radio_btn_B4)
    RadioButton radioBtnB4;
    @Bind(R.id.radio_btn_C4)
    RadioButton radioBtnC4;
    @Bind(R.id.radio_btn_D4)
    RadioButton radioBtnD4;
    @Bind(R.id.articel_sl)
    ScrollView articelSl;
    @Bind(R.id.look_btn)
    ImageButton lookBtn;
    @Bind(R.id.submit_btn)
    ImageButton submitBtn;
    @Bind(R.id.articel_lay)
    RelativeLayout articelLay;
    @Bind(R.id.articel_webview)
    WebView articel_webview;
    @Bind(R.id.next_t)
    ImageButton nextT;
    @Bind(R.id.radioGroup1)
    RadioGroup radioGroup1;
    @Bind(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @Bind(R.id.radioGroup3)
    RadioGroup radioGroup3;
    @Bind(R.id.radioGroup4)
    RadioGroup radioGroup4;
    @Bind(R.id.question1_result)
    ImageView question1Result;
    @Bind(R.id.question2_result)
    ImageView question2Result;
    @Bind(R.id.question3_result)
    ImageView question3Result;
    @Bind(R.id.question4_result)
    ImageView question4Result;
    @Bind(R.id.title_num)
    TextView titleNum;
    @Bind(R.id.articel_root)
    RelativeLayout articelRoot;
    @Bind(R.id.question5)
    TextView question5_tv;
    @Bind(R.id.question5_result)
    ImageView question5Result;
    @Bind(R.id.radio_btn_A5)
    RadioButton radioBtnA5;
    @Bind(R.id.radio_btn_B5)
    RadioButton radioBtnB5;
    @Bind(R.id.radio_btn_C5)
    RadioButton radioBtnC5;
    @Bind(R.id.radio_btn_D5)
    RadioButton radioBtnD5;
    @Bind(R.id.radioGroup5)
    RadioGroup radioGroup5;
    @Bind(R.id.question5_lay)
    LinearLayout question5Lay;
    private String TAG = "ArticelAct";
    private QuestionText questionText;
    private String answer_value1, answer_value2, answer_value3, answer_value4, answer_value5;
    private String answer1, answer2, answer3, answer4, answer5;
    private List<ArticelBean.QuestionsBean> questionsBeanList;
    private int count = 0;//
    protected ArticelPopWindow articelPopWindow;
    protected ArticelPopListener articelPopListener;
    private int rightCount = 0;
    private List<ArticelBean> articelBeanList = new ArrayList<ArticelBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_act);
        ButterKnife.bind(this);
        activity = this;
        Intent intent = getIntent();
        menu = (Menu) intent.getSerializableExtra("menu");
        title.setText(menu.menuName);

        initLis();
        Result result = (Result) CacheManager.readObject(activity, menu.id);
//        if (result != null) {
//            articelBeanList = (ArrayList) result.getData();
//            handlerBase.obtainMessage(1, "").sendToTarget();
//        } else {
//            startHttpGet(String.format(RequestUrls.CONTENTURL, menu.id).toString(), null);
//        }
        startHttpGet(String.format(RequestUrls.CONTENTURL, menu.id).toString(), null);

        timeCount.init("%s", 3600, bachHandler);
        timeCount.start(1);
    }

    private void initLis() {
        radioGroup1.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup2.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup3.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup4.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup5.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    //
    private void initData(ArticelBean articelBean) {
        titleNum.setText("(" + (count + 1) + ")");

        articelContent.setText(articelBean.getContent());
        questionsBeanList = articelBean.getQuestions();
        ArticelBean.QuestionsBean questionsBean;
        List<String> itemContent;

        for (int i = 0; i < questionsBeanList.size(); i++) {
            questionsBean = questionsBeanList.get(i);
            switch (i) {
                case 0:
                    question1_tv.setText(questionsBean.getName());
                    answer_value1 = questionsBean.getAnswer();
                    itemContent = questionsBean.getItems();
                    for (int n = 0; n < itemContent.size(); n++) {
                        switch (n) {
                            case 0:
                                radioBtnA1.setText("A、" + itemContent.get(0));
                                break;
                            case 1:
                                radioBtnB1.setText("B、" + itemContent.get(1));
                                break;
                            case 2:
                                radioBtnC1.setText("C、" + itemContent.get(2));
                                break;
                            case 3:
                                radioBtnD1.setText("D、" + itemContent.get(3));
                                break;
                        }
                    }
                    break;
                case 1:
                    question2_tv.setText(questionsBean.getName());
                    answer_value2 = questionsBean.getAnswer();
                    itemContent = questionsBean.getItems();
                    for (int n = 0; n < itemContent.size(); n++) {
                        switch (n) {
                            case 0:
                                radioBtnA2.setText("A、" + itemContent.get(0));
                                break;
                            case 1:
                                radioBtnB2.setText("B、" + itemContent.get(1));
                                break;
                            case 2:
                                radioBtnC2.setText("C、" + itemContent.get(2));
                                break;
                            case 3:
                                radioBtnD2.setText("D、" + itemContent.get(3));
                                break;
                        }
                    }

                    break;
                case 2:
                    question3_tv.setText(questionsBean.getName());
                    answer_value3 = questionsBean.getAnswer();
                    itemContent = questionsBean.getItems();
                    for (int n = 0; n < itemContent.size(); n++) {
                        switch (n) {
                            case 0:
                                radioBtnA3.setText("A、" + itemContent.get(0));
                                break;
                            case 1:
                                radioBtnB3.setText("B、" + itemContent.get(1));
                                break;
                            case 2:
                                radioBtnC3.setText("C、" + itemContent.get(2));
                                break;
                            case 3:
                                radioBtnD3.setText("D、" + itemContent.get(3));
                                break;
                        }
                    }

                    break;
                case 3:
                    question4_tv.setText(questionsBean.getName());
                    answer_value4 = questionsBean.getAnswer();
                    itemContent = questionsBean.getItems();
                    for (int n = 0; n < itemContent.size(); n++) {
                        switch (n) {
                            case 0:
                                radioBtnA4.setText("A、" + itemContent.get(0));
                                break;
                            case 1:
                                radioBtnB4.setText("B、" + itemContent.get(1));
                                break;
                            case 2:
                                radioBtnC4.setText("C、" + itemContent.get(2));
                                break;
                            case 3:
                                radioBtnD4.setText("D、" + itemContent.get(3));
                                break;
                        }
                    }

                    break;
                case 4:
                    question5Lay.setVisibility(View.VISIBLE);
                    question5_tv.setText(questionsBean.getName());
                    answer_value5 = questionsBean.getAnswer();
                    itemContent = questionsBean.getItems();
                    for (int n = 0; n < itemContent.size(); n++) {
                        switch (n) {
                            case 0:
                                radioBtnA5.setText("A、" + itemContent.get(0));
                                break;
                            case 1:
                                radioBtnB5.setText("B、" + itemContent.get(1));
                                break;
                            case 2:
                                radioBtnC5.setText("C、" + itemContent.get(2));
                                break;
                            case 3:
                                radioBtnD5.setText("D、" + itemContent.get(3));
                                break;
                        }
                    }
                    break;
            }
        }
        // 根据问题获取对应的答案选项
    }

    // 答案提交
    @OnClick(R.id.submit_btn)
    void submitOnClick() {
        int count = questionsBeanList.size();
        if (count == 5) {
            if (TextUtils.isEmpty(answer1))
                return;
            if (TextUtils.isEmpty(answer2))
                return;
            if (TextUtils.isEmpty(answer3))
                return;
            if (TextUtils.isEmpty(answer4))
                return;
            if (TextUtils.isEmpty(answer5))
                return;

        } else if (count == 4) {
            if (TextUtils.isEmpty(answer1))
                return;
            if (TextUtils.isEmpty(answer2))
                return;
            if (TextUtils.isEmpty(answer3))
                return;
            if (TextUtils.isEmpty(answer4))
                return;

        } else if (count == 3) {
            if (TextUtils.isEmpty(answer1))
                return;
            if (TextUtils.isEmpty(answer2))
                return;
            if (TextUtils.isEmpty(answer3))
                return;

        } else if (count == 2) {
            if (TextUtils.isEmpty(answer1))
                return;
            if (TextUtils.isEmpty(answer2))
                return;
        } else if (count == 1) {
            if (TextUtils.isEmpty(answer1))
                return;
        }

        for (int i = 0; i < count; i++) {
            ArticelBean.QuestionsBean questionsBean = questionsBeanList.get(i);
            switch (i) {
                case 0:
                    if (TextUtils.isEmpty(answer1))
                        break;
                    if (answer1.equals(answer_value1)) {
                        rightCount++;
                        submit_right(radioGroup1, question1Result);
                    } else {
                        submit_answer_wrong(questionsBean, radioGroup1, question1Result);
                    }
                    break;
                case 1:
                    if (TextUtils.isEmpty(answer2))
                        break;
                    if (answer2.equals(answer_value2)) {
                        rightCount++;
                        submit_right(radioGroup2, question2Result);
                    } else {
                        submit_answer_wrong(questionsBean, radioGroup2, question2Result);
                    }

                    break;
                case 2:
                    if (TextUtils.isEmpty(answer3))
                        break;
                    if (answer3.equals(answer_value3)) {
                        rightCount++;
                        submit_right(radioGroup3, question3Result);
                    } else {
                        submit_answer_wrong(questionsBean, radioGroup3, question3Result);
                    }
                    break;
                case 3:
                    if (TextUtils.isEmpty(answer4))
                        break;
                    if (answer4.equals(answer_value4)) {
                        rightCount++;
                        submit_right(radioGroup4, question4Result);
                    } else {
                        submit_answer_wrong(questionsBean, radioGroup4, question4Result);
                    }

                    break;
                case 4:
                    if (TextUtils.isEmpty(answer5))
                        break;
                    if (answer5.equals(answer_value5)) {
                        rightCount++;
                        submit_right(radioGroup5, question5Result);
                    } else {
                        submit_answer_wrong(questionsBean, radioGroup5, question5Result);
                    }
                    break;

            }
        }
        submitBtn.setVisibility(View.GONE);
        lookBtn.setVisibility(View.GONE);
        nextT.setVisibility(View.VISIBLE);
        if (count == 3) {
            popShow();
        }
    }

    // 下一题
    @OnClick(R.id.next_t)
    void nextT() {
        count++;
        if (count < articelBeanList.size()) {
            clearRadioGroup();
            initData(articelBeanList.get(count));

        } else {
            popShow(); // 最后一题
        }
    }

    private void popShow() {
        submitBtn.setVisibility(View.GONE);
        lookBtn.setVisibility(View.GONE);
        nextT.setVisibility(View.GONE);
        mp3Playend(R.raw.el);
        articelPopWindow = new ArticelPopWindow(activity, articelPopListener, menu, rightCount);// TODO
        articelPopWindow.show(articelRoot);
    }

    {
        articelPopListener = new ArticelPopListener() {
            @Override
            public void doQuery(String req) {
                stopMp3();
                activity.finish();

            }
        };

    }


    private void submit_right(RadioGroup radioGroup, ImageView imageView) {
        ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).setButtonDrawable(R.mipmap.right);
        imageView.setBackgroundResource(R.mipmap.right);
        imageView.setVisibility(View.VISIBLE);
        disableRadioGroup(radioGroup);
    }

    private void submit_answer_wrong(ArticelBean.QuestionsBean question, RadioGroup radioGroup, ImageView imageView) {
        switch (question.getAnswer()) {
            case "A":
                ((RadioButton) radioGroup.getChildAt(0)).setButtonDrawable(R.mipmap.right);
                break;
            case "B":
                ((RadioButton) radioGroup.getChildAt(1)).setButtonDrawable(R.mipmap.right);
                break;
            case "C":
                ((RadioButton) radioGroup.getChildAt(2)).setButtonDrawable(R.mipmap.right);
                break;
            case "D":
                ((RadioButton) radioGroup.getChildAt(3)).setButtonDrawable(R.mipmap.right);
                break;
        }
        ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).setButtonDrawable(R.mipmap.wrong);
        imageView.setBackgroundResource(R.mipmap.wrong);
        imageView.setVisibility(View.VISIBLE);
        disableRadioGroup(radioGroup);
    }

    // 查看答案
    @OnClick(R.id.look_btn)
    void lookOnClick() {
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            answerValue(checkedId);
        }
    };

    private void answerValue(int checkedId) {
        switch (checkedId) {
            case R.id.radio_btn_A1:
                answer1 = "A";
                break;
            case R.id.radio_btn_B1:
                answer1 = "B";
                break;
            case R.id.radio_btn_C1:
                answer1 = "C";
                break;
            case R.id.radio_btn_D1:
                answer1 = "D";
                break;
            case R.id.radio_btn_A2:
                answer2 = "A";
                break;
            case R.id.radio_btn_B2:
                answer2 = "B";
                break;
            case R.id.radio_btn_C2:
                answer2 = "C";
                break;
            case R.id.radio_btn_D2:
                answer2 = "D";
                break;
            case R.id.radio_btn_A3:
                answer3 = "A";
                break;
            case R.id.radio_btn_B3:
                answer3 = "B";
                break;
            case R.id.radio_btn_C3:
                answer3 = "C";
                break;
            case R.id.radio_btn_D3:
                answer3 = "D";
                break;
            case R.id.radio_btn_A4:
                answer4 = "A";
                break;
            case R.id.radio_btn_B4:
                answer4 = "B";
                break;
            case R.id.radio_btn_C4:
                answer4 = "C";
                break;
            case R.id.radio_btn_D4:
                answer4 = "D";
                break;
            case R.id.radio_btn_A5:
                answer5 = "A";
                break;
            case R.id.radio_btn_B5:
                answer5 = "B";
                break;
            case R.id.radio_btn_C5:
                answer5 = "C";
                break;
            case R.id.radio_btn_D5:
                answer5 = "D";
                break;
        }
    }

    public void disableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setClickable(false);
        }
    }

    public void enableRadioGroup(RadioGroup radioGroup) {

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setClickable(true);
            ((RadioButton) radioGroup.getChildAt(i)).setButtonDrawable(R.drawable.rediobtn_check);
        }
    }

    /**
     * 状态还原
     */
    private void clearRadioGroup() {
        enableRadioGroup(radioGroup1);
        enableRadioGroup(radioGroup2);
        enableRadioGroup(radioGroup3);
        enableRadioGroup(radioGroup4);
        enableRadioGroup(radioGroup5);
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();
        radioGroup4.clearCheck();
        radioGroup5.clearCheck();
        question1Result.setVisibility(View.INVISIBLE);
        question2Result.setVisibility(View.INVISIBLE);
        question3Result.setVisibility(View.INVISIBLE);
        question4Result.setVisibility(View.INVISIBLE);
        question5Result.setVisibility(View.INVISIBLE);
        answer1 = null;
        answer2 = null;
        answer3 = null;
        answer4 = null;
        answer5 = null;
        submitBtn.setVisibility(View.VISIBLE);
        lookBtn.setVisibility(View.VISIBLE);
        nextT.setVisibility(View.GONE);
        question5Lay.setVisibility(View.GONE);
    }

    Handler bachHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                popShow();
            }
        }
    };


    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        Log.w("", s);
        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<ArticelBean>>>() {
        }.getType());
        articelBeanList = (ArrayList) result.getData();
        handlerBase.obtainMessage(1, "").sendToTarget();
        CacheManager.saveObject(activity, result, menu.id);
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        if (msg.what == 1) {
            initData(articelBeanList.get(0));
        } else if (msg.what == 0) {
            ToastHelper.toast(activity, msg.obj.toString());
        }
    }
}
