package qge.cn.com.qgenglish.app.word.check;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.PaginationWidget;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.experience.WordBeanOlds;
import qge.cn.com.qgenglish.app.schoolinfo.UserInfo;
import qge.cn.com.qgenglish.app.word.SjWordAct;
import qge.cn.com.qgenglish.app.word.review.FxSjWordAct;
import qge.cn.com.qgenglish.app.word.table.Tj;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.cache.CacheManager;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

/**
 * 检查
 * 选择关卡
 */

public class JcChoseWordAct extends BaseActivity {
    @Bind(R.id.sj_lv)
    ListView sjLv;
    @Bind(R.id.sj_root)
    RelativeLayout sjRoot;
    @Bind(R.id.choosed_num)
    TextView choosedNum;
    @Bind(R.id.sure_btn)
    Button sureBtn;
    private int curPage;// 当前页数
    private int allCount;// 总页数
    private PaginationWidget paginationWidget;
    private JcWordAdapter jcWordAdapter;
    private List<CpointBean> cpointBeanList;
    private List<Word_niujinban_7_1> wordBeanOldList = new ArrayList<Word_niujinban_7_1>();
    private ArrayList<Word_niujinban_7_1> wordBeanOldListSj = new ArrayList<Word_niujinban_7_1>();
    private ArrayList<WordBeanOlds> wordBeanOldsArrayList = new ArrayList<WordBeanOlds>();
    private int allWordsNum;
    private FonyApplication.QGTYPE qgtype;
    private UserInfo userInfo;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jc_word_act);
        ButterKnife.bind(this);
        activity = this;
        userInfo = (UserInfo) CacheManager.readObject(activity, "userinfo");
        initData();
        initTTS();
        qgtype = ((FonyApplication) activity.getApplication()).qgtype;
    }

    private void initData() {

        Intent intent = activity.getIntent();
        cpointBeanList = (ArrayList<CpointBean>) intent.getSerializableExtra("cpointArray");
        tableName = intent.getStringExtra("tableName");
        if (cpointBeanList == null)
            return;
        int n = cpointBeanList.size();
        for (int i = 0; i < n; i++) {
            CpointBean cpointBean = cpointBeanList.get(i);
            cpointBean.tablename = cpointBean.tablename.substring(0, 1).toUpperCase() + cpointBean.tablename.substring(1);
            Class<?> cls = null;
            try {
                cls = Class.forName("qge.cn.com.qgenglish.app.word.table." + cpointBean.tablename);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // 需要注意最后一页数据
            List<Word_niujinban_7_1> subWordList;
//                    = (List<Word_niujinban_7_1>) DBManager.getWordManager()
//                    .get(cls, "_id", "asc", (cpointBean.code - 1) * 2 * 7, 14);

            if (tableName.equals(TableName.word_four) || tableName.equals(TableName.word_six) || tableName.equals(TableName.phrase_small) || tableName.equals(TableName.phrase_high) || tableName.equals(TableName.phrase_middle)) {
                subWordList = (List<Word_niujinban_7_1>) DBManager.getWordManager().get(cls, "where pass=" + cpointBean.code);

            } else {
                subWordList = (List<Word_niujinban_7_1>) DBManager.getWordManager()
                        .get(cls, "_id", "asc", (cpointBean.code - 1) * 2 * 7, 14); // 查询当前选中关卡的数据
            }


            wordBeanOldList.addAll(subWordList);
        }

        // 查询出来的单词列表变为两列的数据方式
        jcWordAdapter = new JcWordAdapter(activity, toWordBeanOlds(wordBeanOldList));
        jcWordAdapter.setChooseWordListion(chooseWordListion);
        sjLv.setAdapter(jcWordAdapter);
//        sjLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                LinearLayout interpretationLay = (LinearLayout) view.findViewById(R.id.interpretation_lay);
//                interpretationLay.setVisibility(View.VISIBLE);
//                WordBeanOld wordBeanOld = wordBeanOldList.get(position);
//                String word = wordBeanOld.english;
//                icibaHttp(word, wordHandler);
//            }
//        });

        /**
         * 重新识记
         */
//
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < wordBeanOldsArrayList.size(); i++) {
                    WordBeanOlds wordBeanOlds = wordBeanOldsArrayList.get(i);
                    if (wordBeanOlds.state)
                        wordBeanOldListSj.add(wordBeanOlds.wordBeanOld);
                    if (wordBeanOlds.state1)
                        wordBeanOldListSj.add(wordBeanOlds.wordBeanOld1);
                }

                Intent intent = new Intent();
                intent.setClass(activity, FxSjWordAct.class);
                intent.putExtra("sjArray", wordBeanOldListSj);
                activity.startActivity(intent);
            }
        });
    }

    private ChooseWordListion chooseWordListion = new ChooseWordListion() {
        // 记录学习的单词的个数
        @Override
        public void switchChose(int position, View view, boolean isShow, boolean isClick) {
            LinearLayout interpretationLay = (LinearLayout) view.findViewById(R.id.interpretation_lay);
            TextView textView = (TextView) view.findViewById(R.id.word_value);
            TextView phonetic = (TextView) view.findViewById(R.id.phonetic);
            if (isShow) {
                interpretationLay.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                phonetic.setVisibility(View.GONE);
            } else {
                interpretationLay.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                phonetic.setVisibility(View.GONE);
            }
            Word_niujinban_7_1 wordBeanOld = wordBeanOldList.get(2 * position);
            String word = wordBeanOld.english;
            wordHandler.obtainMessage(2).sendToTarget();


            //icibaHttp(word, wordHandler);

            switch (qgtype) {
                case WORD:
                    icibaHttp(word, wordHandler);// 读取发音  这里区分单词还是短语 发音
                    break;
                case PHRASE:
                    if (word.contains("sth")) {
                        word = word.replace("sth", "something");
                    }
                    if (word.contains("sb")) {
                        word = word.replace("sb", "somebody");
                    }
                    textToSpeek(word);
                    break;

            }

        }

        @Override
        public void switchChose1(int position, View view, boolean isShow, boolean isClick) {

            LinearLayout interpretationLay = (LinearLayout) view.findViewById(R.id.interpretation_lay1);
            TextView textView = (TextView) view.findViewById(R.id.word_value1);
            TextView phonetic1 = (TextView) view.findViewById(R.id.phonetic1);
            if (isShow) {
                interpretationLay.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                phonetic1.setVisibility(View.GONE);
            } else {
                interpretationLay.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                phonetic1.setVisibility(View.GONE);
            }
            Word_niujinban_7_1 wordBeanOld = wordBeanOldList.get(2 * position + 1);
            String word = wordBeanOld.english;
            wordHandler.obtainMessage(2).sendToTarget();
            //icibaHttp(word, wordHandler);
            switch (qgtype) {
                case WORD:
                    icibaHttp(word, wordHandler);// 读取发音  这里区分单词还是短语 发音
                    break;
                case PHRASE:
                    if (word.contains("sth")) {
                        word = word.replace("sth", "something");
                    }
                    if (word.contains("sb")) {
                        word = word.replace("sb", "somebody");
                    }
                    textToSpeek(word);
                    break;

            }

        }

        @Override
        public void chooseCount(int chooseNum) {

            // 记录错误单词的个数
            //choosedNum.setText("已选择"+jcWordAdapter.chooseNum+"个单词");// 正则表达式
            // choosedNum.setText(String.format("已检查 %s /错误 %s /正确率 %s", jcWordAdapter.chooseNum, chooseNum, jcWordAdapter.chooseNum).toString());
            wordHandler.obtainMessage(2).sendToTarget();
        }
    };

    interface ChooseWordListion {
        void switchChose(int position, View view, boolean isShow, boolean isClick);

        void switchChose1(int position, View view, boolean isShow, boolean isClick);

        void chooseCount(int chooseNum);
    }


    private ArrayList<WordBeanOlds> toWordBeanOlds(List<Word_niujinban_7_1> wordBeanOldList) {
        allWordsNum = wordBeanOldList.size();

        for (int i = 0; i < allWordsNum; i++) {
            WordBeanOlds wordBeanOlds = new WordBeanOlds();
            wordBeanOlds.wordBeanOld = wordBeanOldList.get(i);
            if (i > wordBeanOldList.size() - 1)
                return wordBeanOldsArrayList;
            wordBeanOlds.wordBeanOld1 = wordBeanOldList.get(++i);
            wordBeanOldsArrayList.add(wordBeanOlds);
        }
        return wordBeanOldsArrayList;
    }

    int checkedNumCop = 0;
    int chooseNumCopy = 0;

    public Handler wordHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                WordBean wordBean = (WordBean) msg.obj;
                String word = wordBean.getKey();
                String psE_value = wordBean.getPsE();
                String psA_value = wordBean.getPsA();
                String acceptation_value = wordBean.getAcceptation();
                mp3Play(word, Mp3Player.USA_ACCENT);
            } else if (msg.what == 1) {

            } else if (msg.what == 2) {
                if (jcWordAdapter.checkedNum == 0)
                    return;
                //

                Tj tj = DBManager.getWordManager().getT(Tj.class, "where tablename='" + tableName + "' and userid='" + userInfo.getUserInfo().getId() + "'");

                tj.jccount = jcWordAdapter.checkedNum + tj.jccount - checkedNumCop;
                tj.jcwrong = jcWordAdapter.chooseNum + tj.jcwrong - chooseNumCopy;
                checkedNumCop = jcWordAdapter.checkedNum;
                chooseNumCopy = jcWordAdapter.chooseNum;
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);
                String result = numberFormat.format((float) (tj.jccount - tj.jcwrong) / (float) tj.jccount * 100);
                choosedNum.setText(String.format("已检查 %s /错误 %s /正确率 %s", tj.jccount, tj.jcwrong, result + "%").toString());
                DBManager.getWordManager().update(TableName.tongj, "jccount", tj.jccount, "where tablename='" + tableName + "'  and userid='" + userInfo.getUserInfo().getId() + "'");
                DBManager.getWordManager().update(TableName.tongj, "jcwrong", tj.jcwrong, "where tablename='" + tableName + "'  and userid='" + userInfo.getUserInfo().getId() + "'");

            }
        }
    };

    @Override
    protected void onResume() {
        wordBeanOldListSj.clear();
        super.onResume();
    }

}
