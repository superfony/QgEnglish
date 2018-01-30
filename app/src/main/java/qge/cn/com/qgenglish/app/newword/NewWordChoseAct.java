package qge.cn.com.qgenglish.app.newword;

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
import qge.cn.com.qgenglish.app.PaginationWidget;
import qge.cn.com.qgenglish.app.word.SjWordAct;
import qge.cn.com.qgenglish.app.word.table.Word_unskilled;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

/**
 * 我的生词本界面
 * 选择关卡
 */

public class NewWordChoseAct extends BaseActivity {
    @Bind(R.id.new_lv)
    ListView newLv;
    @Bind(R.id.new_root)
    RelativeLayout newRoot;
    @Bind(R.id.choosed_num)
    TextView choosedNum;
    @Bind(R.id.sure_btn)
    Button sureBtn;
    private int curPage;// 当前页数
    private int allCount;// 总页数
    private PaginationWidget paginationWidget;
    private NewWordAdapter newWordAdapter;
    private List<Word_unskilled> word_unskilledList = new ArrayList<Word_unskilled>();
    private ArrayList<Word_unskilled> wordBeanOldListSj = new ArrayList<Word_unskilled>();
    ArrayList<NewWordbeanS> wordBeanOldsArrayList = new ArrayList<NewWordbeanS>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_word_act);
        ButterKnife.bind(this);
        activity = this;
        initData();
    }

    private void initData() {
        long count = DBManager.getWordManager().getCount(Word_unskilled.class);
        paginationWidget = new PaginationWidget();
        paginationWidget.init(activity, newRoot);
        paginationWidget.getPageBean().setAllCount((int) count);
        paginationWidget.setPageSize(22);
        paginationWidget.setPageIndicator((int) count);
        paginationWidget.setHandler(wordHandler);
        word_unskilledList = DBManager.getWordManager().get(Word_unskilled.class, 0, paginationWidget.getPageBean().getPageSize());
        // 查询出来的单词列表变为两列的数据方式
        newWordAdapter = new NewWordAdapter(activity, toWordBeanOlds(word_unskilledList));
        newWordAdapter.setChooseWordListion(chooseWordListion);
        newLv.setAdapter(newWordAdapter);
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
                    NewWordbeanS wordBeanOlds = wordBeanOldsArrayList.get(i);
                    if (wordBeanOlds.state)
                        wordBeanOldListSj.add(wordBeanOlds.wordBeanOld);
                    if (wordBeanOlds.state1)
                        wordBeanOldListSj.add(wordBeanOlds.wordBeanOld1);
                }

                Intent intent = new Intent();
                intent.setClass(activity, SjWordAct.class);
                // intent.putParcelableArrayListExtra("sjArray",wordBeanOldListSj);
                intent.putExtra("sjArray", wordBeanOldListSj);
                activity.startActivity(intent);
            }
        });
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
            Word_unskilled wordBeanOld = word_unskilledList.get(position);
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
            Word_unskilled wordBeanOld = word_unskilledList.get(position + 1);
            String word = wordBeanOld.english;
            icibaHttp(word, wordHandler);
        }

        @Override
        public void chooseCount() {
            //choosedNum.setText("已选择"+jcWordAdapter.chooseNum+"个单词");// 正则表达式
            // choosedNum.setText(String.format("已检查 %s /错误 %s /正确率 %s", jcWordAdapter.chooseNum, jcWordAdapter.chooseNum,jcWordAdapter.chooseNum).toString());
        }
    };

    interface ChooseWordListion {
        void switchChose(int position, View view, boolean isShow);

        void switchChose1(int position, View view, boolean isShow);

        void chooseCount();
    }


    private ArrayList<NewWordbeanS> toWordBeanOlds(List<Word_unskilled> word_unskilledList) {


        for (int i = 0; i < word_unskilledList.size(); i++) {
            NewWordbeanS newWordbeanS = new NewWordbeanS();
            newWordbeanS.wordBeanOld = word_unskilledList.get(i);
            if (i >= word_unskilledList.size() - 1) {
                wordBeanOldsArrayList.add(newWordbeanS);
                return wordBeanOldsArrayList;
            }
            newWordbeanS.wordBeanOld1 = word_unskilledList.get(++i);
            wordBeanOldsArrayList.add(newWordbeanS);
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
