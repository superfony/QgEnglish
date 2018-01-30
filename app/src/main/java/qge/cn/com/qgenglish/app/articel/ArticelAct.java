package qge.cn.com.qgenglish.app.articel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.articel.question.Question;
import qge.cn.com.qgenglish.app.articel.question.QuestionItem;
import qge.cn.com.qgenglish.app.articel.question.QuestionText;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.view.CountdownTextView;

/**
 * 阅读界面
 * 文章阅读 在数据库表里面按排序 读取 4条一关
 */

public class ArticelAct extends BaseActivity {
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


    private String TAG = "ArticelAct";
    private QuestionText questionText;
    private String answer_value1, answer_value2, answer_value3, answer_value4;
    private String answer1, answer2, answer3, answer4;
    private List<Question> questionList;
    private Question question1, question2, question3, question4;
    private List<QuestionText> questionTextList;
    private int guanqi;// 关卡计数
    private int count;//当前关卡文章计数
    private String titleStr;


    public final static String linkCss = "<script type=\"text/javascript\" " +
            "src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/detail_page" +
            ".js\"></script>"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window" +
            ".location.url= url;}</script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" " +
            "href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore" +
            ".css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common" +
            ".css\">";
    public final static String WEB_STYLE = linkCss;

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var " +
            "allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    public static String getWebViewBodyString() {
        if (false) {
            return "<body class='night'><div class='contentstyle' id='article_body'>";
        } else {
            return "<body style='background-color: #FFF'><div class='contentstyle' id='article_body' >";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articel_act);
        ButterKnife.bind(this);
        activity = this;
        initUI();
        Intent intent = getIntent();
        guanqi = intent.getIntExtra("guanqi", 0);
        count = intent.getIntExtra("count", 0);
        timeCount.init("%s", 3600);
        timeCount.start(1);
        questionTextList = getQuestionTextList(guanqi * 4, 4, 0);
        initData(count);
        titleNum.setText("(" + (count + 1) + ")");
    }

    private void initUI() {
        radioGroup1.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup2.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup3.setOnCheckedChangeListener(onCheckedChangeListener);
        radioGroup4.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private void initData(int i) {

        QuestionText questionText = getQuestionText(i);
        articelContent.setText(Html.fromHtml(questionText.questionBody));


        StringBuffer body = new StringBuffer();
        body.append(WEB_STYLE).append(WEB_LOAD_IMAGES);
        body.append(getWebViewBodyString());
        body.append(questionText.questionBody);
        // 封尾
        body.append("</div></body>");
//        articel_webview.loadDataWithBaseURL(null,
//                WEB_STYLE + body.toString(), "text/html",
//                "utf-8", null);
//       articel_webview.loadData(questionText.questionBody,"text/html", "utf-8");
        // 获取问题列表
        questionList = getQuestionList(questionText);
        question1 = questionList.get(0);//第一个问题
        question1_tv.setText(question1.questionBody);
        // 根据问题获取对应的答案选项
        List<QuestionItem> questionItemList = getQuestionItemList(question1);
        for (int n = 0; n < questionItemList.size(); n++) {
            switch (n) {
                case 0:
                    radioBtnA1.setText("A、" + questionItemList.get(0).itemContent);
                    break;
                case 1:
                    radioBtnB1.setText("B、" + questionItemList.get(1).itemContent);
                    break;
                case 2:
                    radioBtnC1.setText("C、" + questionItemList.get(2).itemContent);
                    break;
                case 3:
                    radioBtnD1.setText("D、" + questionItemList.get(3).itemContent);
                    break;
            }
        }
        answer_value1 = question1.answer;


        question2 = questionList.get(1);//第二个问题
        question2_tv.setText(question2.questionBody);
        questionItemList = getQuestionItemList(question2);

        for (int n = 0; n < questionItemList.size(); n++) {
            switch (n) {
                case 0:
                    radioBtnA2.setText("A、" + questionItemList.get(0).itemContent);
                    break;
                case 1:
                    radioBtnB2.setText("B、" + questionItemList.get(1).itemContent);
                    break;
                case 2:
                    radioBtnC2.setText("C、" + questionItemList.get(2).itemContent);
                    break;
                case 3:
                    radioBtnD2.setText("D、" + questionItemList.get(3).itemContent);
                    break;
            }
        }
        answer_value2 = question2.answer;


        question3 = questionList.get(2);//第三个问题
        question3_tv.setText(question3.questionBody);
        questionItemList = getQuestionItemList(question3);

        for (int n = 0; n < questionItemList.size(); n++) {
            switch (n) {
                case 0:
                    radioBtnA3.setText("A、" + questionItemList.get(0).itemContent);
                    break;
                case 1:
                    radioBtnB3.setText("B、" + questionItemList.get(1).itemContent);
                    break;
                case 2:
                    radioBtnC3.setText("C、" + questionItemList.get(2).itemContent);
                    break;
                case 3:
                    radioBtnD3.setText("D、" + questionItemList.get(3).itemContent);
                    break;
            }
        }
        answer_value3 = question3.answer;

        question4 = questionList.get(3);//第四个问题
        question4_tv.setText(question4.questionBody);
        questionItemList = getQuestionItemList(question4);

        for (int n = 0; n < questionItemList.size(); n++) {
            switch (n) {
                case 0:
                    radioBtnA4.setText("A、" + questionItemList.get(0).itemContent);
                    break;
                case 1:
                    radioBtnB4.setText("B、" + questionItemList.get(1).itemContent);
                    break;
                case 2:
                    radioBtnC4.setText("C、" + questionItemList.get(2).itemContent);
                    break;
                case 3:
                    radioBtnD4.setText("D、" + questionItemList.get(3).itemContent);
                    break;
            }
        }
        answer_value4 = question4.answer;
    }

    // 答案提交
    @OnClick(R.id.submit_btn)
    void submitOnClick() {
        if (TextUtils.isEmpty(answer1))
            return;
        if (TextUtils.isEmpty(answer2))
            return;
        if (TextUtils.isEmpty(answer3))
            return;
        if (TextUtils.isEmpty(answer4))
            return;
        DBManager.getWordManager().update("question", "userAnswer", answer1, "where questionID=" + question1.questionID);
        DBManager.getWordManager().update("question", "userAnswer", answer2, "where questionID=" + question2.questionID);
        DBManager.getWordManager().update("question", "userAnswer", answer3, "where questionID=" + question3.questionID);
        DBManager.getWordManager().update("question", "userAnswer", answer4, "where questionID=" + question4.questionID);
        if (answer1.equals(question1.answer)) {
            submit_right(radioGroup1, question1Result);
        } else {
            submit_answer_wrong(question1, radioGroup1, question1Result);
        }

        if (answer2.equals(question2.answer)) {
            submit_right(radioGroup2, question2Result);
        } else {
            submit_answer_wrong(question2, radioGroup2, question2Result);
        }

        if (answer3.equals(question3.answer)) {
            submit_right(radioGroup3, question3Result);
        } else {
            submit_answer_wrong(question3, radioGroup3, question3Result);
        }

        if (answer4.equals(question4.answer)) {
            submit_right(radioGroup4, question4Result);
        } else {
            submit_answer_wrong(question4, radioGroup4, question4Result);
        }

        submitBtn.setVisibility(View.GONE);
        lookBtn.setVisibility(View.GONE);
        nextT.setVisibility(View.VISIBLE);


    }


    @OnClick(R.id.next_t)
    void nextT() {
        count++;
        if (count < 4) {
            Intent intent = new Intent();
            intent.putExtra("guanqi", guanqi);
            intent.putExtra("count", count);
            intent.setClass(activity, ArticelAct.class);
            activity.startActivity(intent);
            activity.finish();
        } else {
            //  下一关的操作  统计查看答题分数
//            Intent intent = new Intent();
//            intent.putExtra("guanqi", ++guanqi);
//            intent.putExtra("count", 0);
//            intent.setClass(activity, ArticelAct.class);
//            activity.startActivity(intent);
//            activity.finish();
        }
    }

    private void submit_right(RadioGroup radioGroup, ImageView imageView) {
        ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).setButtonDrawable(R.mipmap.right);
        imageView.setBackgroundResource(R.mipmap.right);
        imageView.setVisibility(View.VISIBLE);
        disableRadioGroup(radioGroup);
    }

    private void submit_answer_wrong(Question question, RadioGroup radioGroup, ImageView imageView) {
        switch (question.answer) {
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


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private QuestionText getQuestionText(int i) {
        QuestionText questionText = null;
        if (questionTextList != null)
            questionText = questionTextList.get(i);
        return questionText;
    }


    private List<QuestionText> getQuestionTextList(int limit, int limit2, int offset) {
        return DBManager.getWordManager().get(QuestionText.class, limit, limit2, offset);
    }

    private List<Question> getQuestionList(QuestionText questionText) {
        List<Question> questionList = null;
        questionList = DBManager.getWordManager().get(Question.class, "where parentID=" + questionText.textID);
        return questionList;
    }

    private List<QuestionItem> getQuestionItemList(Question question) {
        List<QuestionItem> questionItemList = null;
        questionItemList = DBManager.getWordManager().get(QuestionItem.class, "where questionID=" + question.questionID);
        return questionItemList;
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
        }
    }

    public void disableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            // radioGroup.getChildAt(i).setEnabled(false); // 隐藏
            radioGroup.getChildAt(i).setClickable(false);
        }
    }

    public void enableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setClickable(true);
        }
    }
}
