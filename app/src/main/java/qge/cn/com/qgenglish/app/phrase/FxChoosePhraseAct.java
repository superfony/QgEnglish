package qge.cn.com.qgenglish.app.phrase;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.ChooseWordListion;
import qge.cn.com.qgenglish.app.PaginationWidget;
import qge.cn.com.qgenglish.app.experience.WordBeanOlds;
import qge.cn.com.qgenglish.app.word.review.FxSjWordAct;
import qge.cn.com.qgenglish.app.word.review.FxWordAdapter;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

/**
 * 短语复习
 */

public class FxChoosePhraseAct extends BaseActivity {
    @Bind(R.id.fx_lv)
    ListView fxLv;
    @Bind(R.id.fx_root)
    RelativeLayout fxRoot;
    @Bind(R.id.choosed_num)
    TextView choosedNum;
    @Bind(R.id.sure_btn)
    Button sureBtn;
    private PaginationWidget paginationWidget;
    private FxWordAdapter fxWordAdapter;
    private List<Word_niujinban_7_1> wordBeanOldList = new ArrayList<Word_niujinban_7_1>();
    private ArrayList<Word_niujinban_7_1> wordBeanOldListSj = new ArrayList<Word_niujinban_7_1>();
    private ArrayList<WordBeanOlds> wordBeanOldsArrayList = new ArrayList<WordBeanOlds>();
    private ArrayList<CpointBean> cpointBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fx_word_act);
        ButterKnife.bind(this);
        activity = this;
        initData();
    }

    private void initData() {
        // 测试数据
        Intent intent = activity.getIntent();
        cpointBeanList = (ArrayList<CpointBean>) intent.getSerializableExtra("cpointArray");
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
            List<Word_niujinban_7_1> subWordList = (List<Word_niujinban_7_1>) DBManager.getWordManager()
                    .get(cls, "_id", "asc", (cpointBean.code - 1) * 2 * 7, 14);
            wordBeanOldList.addAll(subWordList);
        }
        // 查询出来的单词列表变为两列的数据方式
        fxWordAdapter = new FxWordAdapter(activity, toWordBeanOlds(wordBeanOldList));
        fxWordAdapter.setChooseWordListion(chooseWordListion);
        fxLv.setAdapter(fxWordAdapter);
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

    @Override
    protected void onResume() {

        wordBeanOldListSj.clear();
        super.onResume();
    }

    private ChooseWordListion chooseWordListion = new ChooseWordListion() {
        @Override
        public void switchChose(int position, View view, boolean isShow) {
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
            Word_niujinban_7_1 wordBeanOld = wordBeanOldList.get(position);
            String word = wordBeanOld.english;
            icibaHttp(word, wordHandler);
        }

        @Override
        public void switchChose1(int position, View view, boolean isShow) {
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
            Word_niujinban_7_1 wordBeanOld = wordBeanOldList.get(position + 1);
            String word = wordBeanOld.english;
            icibaHttp(word, wordHandler);
        }

        @Override
        public void chooseCount() {
            //choosedNum.setText("已选择"+jcWordAdapter.chooseNum+"个单词");// 正则表达式
            // choosedNum.setText(String.format("已检查 %s /错误 %s /正确率 %s", jcWordAdapter.chooseNum, jcWordAdapter.chooseNum,jcWordAdapter.chooseNum).toString());
        }
    };


    private ArrayList<WordBeanOlds> toWordBeanOlds(List<Word_niujinban_7_1> wordBeanOldList) {


        for (int i = 0; i < wordBeanOldList.size(); i++) {
            WordBeanOlds wordBeanOlds = new WordBeanOlds();
            wordBeanOlds.wordBeanOld = wordBeanOldList.get(i);
            if (i > wordBeanOldList.size() - 1)
                return wordBeanOldsArrayList;
            wordBeanOlds.wordBeanOld1 = wordBeanOldList.get(++i);
            wordBeanOldsArrayList.add(wordBeanOlds);
        }
        return wordBeanOldsArrayList;
    }


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

            } else if (msg.what == 100) {
//                PageBean pageBean = (PageBean) msg.obj;
//                wordBeanOldList = DBManager.getWordManager()
//                        .get(WordBeanOld.class, (pageBean.getCurrentPage() - 1) * (pageBean.getPageSize()), pageBean.getPageSize());
//                expWordAdapter.updateListView(wordBeanOldList);
//                paginationWidget.setPageIndicator(pageBean.getAllCount());
            }
        }
    };


}
