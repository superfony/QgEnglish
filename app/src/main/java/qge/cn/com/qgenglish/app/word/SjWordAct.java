package qge.cn.com.qgenglish.app.word;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baiyang.android.http.pagination.PageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.PaginationWidget;
import qge.cn.com.qgenglish.app.word.table.WordBase;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.app.word.table.Word_renjiao_7_0;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

/**
 * 识记
 * 单词识记通用类
 */
public class SjWordAct extends BaseActivity {
    @Bind(R.id.sj_lv)
    ListView sjLv;
    @Bind(R.id.sj_root)
    RelativeLayout sjRoot;
    private int curPage;// 当前页数
    private int allCount;// 总页数
    private PaginationWidget paginationWidget;
    private SjwordAdapter sjwordAdapter;
    private List<Word_niujinban_7_1> wordBeanOldList = new ArrayList<Word_niujinban_7_1>();
    private int count;
    private static int pageSize = 13;
    public Class<?> cls;
    private CpointBean cpointBean;
    private String clsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sj_word_act);
        ButterKnife.bind(this);
        activity = this;
        cpointBean = (CpointBean) activity.getIntent().getSerializableExtra("cpointBean");
        clsName = cpointBean.tableName;
        switchCls(clsName);
        initData();
        initPaginationWidget(count);
        initAdpater();

    }

    private void switchCls(String clsName) {

        if (TextUtils.isEmpty(clsName))
            clsName = "word_niujinban_7_1";
        if ("word_niujinban_7_1".equals(clsName)) {
            cls = Word_niujinban_7_1.class;
        } else if ("word_renjiao_7_0".equals(clsName)) {
            cls = Word_renjiao_7_0.class;

        }
    }

    private void initData() {
        Intent intent = this.getIntent();
        wordBeanOldList = (ArrayList<Word_niujinban_7_1>) intent.getSerializableExtra("sjArray");
        if (wordBeanOldList != null) {
            count = wordBeanOldList.size();
        } else {
            long countlong = DBManager.getWordManager().getCount(cls);
            count = (int) countlong;
            wordBeanOldList = (ArrayList<Word_niujinban_7_1>) DBManager.getWordManager().get(cls, 0, SjWordAct.pageSize);
        }
    }

    private void initPaginationWidget(int count) {
        paginationWidget = new PaginationWidget();
        paginationWidget.init(activity, sjRoot);
        paginationWidget.getPageBean().setAllCount((int) count);
        paginationWidget.setPageSize(SjWordAct.pageSize);
        paginationWidget.setPageIndicator((int) count);
        paginationWidget.setHandler(wordHandler);
    }

    private void initAdpater() {
        sjwordAdapter = new SjwordAdapter(activity, wordBeanOldList);
        sjLv.setAdapter(sjwordAdapter);
        sjLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout interpretationLay = (LinearLayout) view.findViewById(R.id.interpretation_lay);
                interpretationLay.setVisibility(View.VISIBLE);
                Word_niujinban_7_1 wordBeanOld = wordBeanOldList.get(position);
                String word = wordBeanOld.english;
                icibaHttp(word, wordHandler);
            }
        });
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
                PageBean pageBean = (PageBean) msg.obj;
                wordBeanOldList = DBManager.getWordManager()
                        .get(Word_niujinban_7_1.class, (pageBean.getCurrentPage() - 1) * (pageBean.getPageSize()), pageBean.getPageSize());
                sjwordAdapter.updateListView(wordBeanOldList);
                paginationWidget.setPageIndicator(pageBean.getAllCount());
            }
        }
    };
}
