package qge.cn.com.qgenglish.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.word.table.Userinfo;

/**
 * Created by fony on 2018/1/28.
 */

public class CheckChooseAct extends BaseActivity {
    @Bind(R.id.word_value)
    TextView wordValue;
    @Bind(R.id.count_down)
    TextView countDown;
    @Bind(R.id.radio_1)
    RadioButton radio1;
    @Bind(R.id.radio_2)
    RadioButton radio2;
    @Bind(R.id.radio_3)
    RadioButton radio3;
    @Bind(R.id.radio_4)
    RadioButton radio4;
    @Bind(R.id.radio_5)
    RadioButton radio5;
    @Bind(R.id.radio_6)
    RadioButton radio6;
    @Bind(R.id.radio_7)
    RadioButton radio7;
    @Bind(R.id.check_word_rg)
    RadioGroup checkWordRg;
    @Bind(R.id.check_sure)
    Button checkSure;
    @Bind(R.id.word_num)
    Button wordNum;
    @Bind(R.id.check_rlay)
    RelativeLayout checkRlay;
    @Bind(R.id.check_result_rlay)
    RelativeLayout checkResultRlay;
    @Bind(R.id.result_name)
    TextView resultName;
    @Bind(R.id.s_sj)
    TextView sSj;
    @Bind(R.id.result_school)
    TextView resultSchool;
    @Bind(R.id.result_score)
    TextView resultScore;
    @Bind(R.id.result_ans)
    TextView resultAns;
    @Bind(R.id.check_back)
    Button checkBack;
    private CheckChooseJson checkChooseJson;
    private List<CheckChooseJson.DataBean> list = null;
    private int countTime = 20;
    private int countWords = 50;
    private int correntCount = 0;
    private int score = 0;
    private String answer = null;
    private String chl = null;
    private CheckChooseJson.DataBean dataBean;
    private Userinfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_choose_act);
        ButterKnife.bind(this);
        userinfo = (Userinfo) this.getIntent().getSerializableExtra("userinfo");
        Gson gson = new Gson();
        String checkStr = getFileToStr("check_word.txt");
        checkChooseJson = gson.fromJson(checkStr, CheckChooseJson.class);
        list = checkChooseJson.getData();
        dataBean = list.get(correntCount);
        initData(dataBean);
        timer.schedule(task, 1000, 1000);
        checkWordRg.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private void initData(CheckChooseJson.DataBean dataBean) {
        wordNum.setText(dataBean.getQuestionID() + "");
        wordValue.setText(dataBean.getQuestionBody());
        countDown.setText(countTime + "");
        List<CheckChooseJson.DataBean.QuestionItemBean> questionItemBeanList = dataBean.getQuestionItem();
        radio1.setText(questionItemBeanList.get(0).getItemContent());
        radio2.setText(questionItemBeanList.get(1).getItemContent());
        radio3.setText(questionItemBeanList.get(2).getItemContent());
        radio4.setText(questionItemBeanList.get(3).getItemContent());
        radio5.setText(questionItemBeanList.get(4).getItemContent());
        radio6.setText(questionItemBeanList.get(5).getItemContent());
        radio7.setText(questionItemBeanList.get(6).getItemContent());
    }

    Timer timer = new Timer();

    TimerTask task = new TimerTask() {
        public void run() {
            if (countTime > 1) {
                countTime--;
                osHandler.sendEmptyMessage(0);
            } else if (countTime == 1) {
                score(answer, dataBean);
                answer = null;
                correntCount++;
                osHandler.sendEmptyMessage(1);
            }
        }
    };


    private void score(String answer, CheckChooseJson.DataBean dataBean) {
        if (!TextUtils.isEmpty(answer)) {
            if (answer.equals(dataBean.getAnswer())) {
                score = score + 2;
            }
        }
    }


    @OnClick(R.id.check_sure)
    public void checkSure() {
        score(answer, dataBean);
        countTime = 20;
        correntCount++;
        checkWordRg.clearCheck();
        answer = null;
        if (correntCount >= 50) {
            osHandler.sendEmptyMessage(2);
            return;
        }
        dataBean = list.get(correntCount);
        initData(dataBean);
    }

    @OnClick(R.id.check_back)
    public void checkBack() {

        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    Handler osHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                countDown.setText(countTime + "");
            } else if (msg.what == 1) {
                if (correntCount >= 50) {
                    osHandler.sendEmptyMessage(2);
                    return;
                }
                countTime = 20;
                checkWordRg.clearCheck();
                initData(list.get(correntCount));
            } else if (msg.what == 2) { // 退出
                //   跳转显示统计结果
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }

                if (score >= 0 && score <= 10) {
                    chl = "100-200";
                } else if (score >= 11 && score <= 20) {
                    chl = "200-300";
                } else if (score >= 21 && score <= 30) {
                    chl = "300-500";
                } else if (score >= 31 && score <= 40) {
                    chl = "500-800";
                } else if (score >= 41 && score <= 50) {
                    chl = "800-1000";
                } else if (score >= 51 && score <= 60) {
                    chl = "1000-1500";
                } else if (score >= 61 && score <= 70) {
                    chl = "1600-1800";
                } else if (score >= 71 && score <= 80) {
                    chl = "1800-2000";
                } else if (score >= 81 && score <= 90) {
                    chl = "2000-2500";
                } else if (score >= 91 && score <= 100) {
                    chl = "2600-3000";
                }
                resultName.setText(userinfo.username);
                sSj.setText(userinfo.city + userinfo.area);
                resultSchool.setText(userinfo.school);
                resultScore.setText(String.format("您的词汇量检测成绩是 %s 分", score));
                resultAns.setText(String.format("您的词汇量约 %s ,相当于小学三四年级水平,只能认识部分生活中简单的名词,无法进行交流/阅读/x写作,建议背诵小学词汇和初中词汇", chl));
                checkRlay.setVisibility(View.GONE);
                checkResultRlay.setVisibility(View.VISIBLE);

            }
        }
    };
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {
                case R.id.radio_1:
                    answer = "A";
                    break;
                case R.id.radio_2:
                    answer = "B";
                    break;
                case R.id.radio_3:
                    answer = "C";
                    break;
                case R.id.radio_4:
                    answer = "D";
                    break;
                case R.id.radio_5:
                    answer = "E";
                    break;
                case R.id.radio_6:
                    answer = "F";
                    break;
                case R.id.radio_7:
                    answer = "G";
                    break;
            }
        }
    };

    // 您的词汇量检测成绩是12分
    //您的词汇量约200-300,相当于小学三四年级水平,只能认识部分生活中简单的名词,无法进行交流/阅读/x写作,建议背诵小学词汇和初中词汇
}
