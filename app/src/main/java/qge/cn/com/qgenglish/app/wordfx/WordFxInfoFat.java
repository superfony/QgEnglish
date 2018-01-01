package qge.cn.com.qgenglish.app.wordfx;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baiyang.android.http.pagination.PageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseFragment;
import qge.cn.com.qgenglish.app.PaginationWidget;
import qge.cn.com.qgenglish.app.word.SjwordAdapter;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

/**
 * 复习单词详情页面
 */
public class WordFxInfoFat extends BaseFragment {

    @Bind(R.id.title_lay)
    LinearLayout titleLay;
    @Bind(R.id.sj_lv)
    ListView sjLv;
//    @Bind(R.id.btn_firstPage)
//    Button btnFirstPage;
//    @Bind(R.id.btn_prevPage)
//    ImageButton btnPrevPage;
//    @Bind(R.id.txt_currentPage)
//    TextView txtCurrentPage;
//    @Bind(R.id.txt_pageCount)
//    TextView txtPageCount;
//    @Bind(R.id.pageRecourdLayout)
//    LinearLayout pageRecourdLayout;
//    @Bind(R.id.txt_page_allCount)
//    TextView txtPageAllCount;
//    @Bind(R.id.pageSizeLayout)
//    LinearLayout pageSizeLayout;
//    @Bind(R.id.pageRecordMainLayout)
//    RelativeLayout pageRecordMainLayout;
//    @Bind(R.id.btn_nextPage)
//    ImageButton btnNextPage;
//    @Bind(R.id.btn_lastPage)
//    Button btnLastPage;
//    @Bind(R.id.pagination_widget_ctrl)
//    RelativeLayout paginationWidgetCtrl;

    @Bind(R.id.sj_root)
    RelativeLayout sjRoot;
    private View view;
    private Activity activity;

    private PaginationWidget paginationWidget;
    private SjwordAdapter sjwordAdapter;
    private List<Word_niujinban_7_1> wordBeanOldList = new ArrayList<Word_niujinban_7_1>();

    public WordFxInfoFat() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fx_word_info_list, container,
                    false);
        } catch (Exception e) {
        }
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = this.getActivity();
        initUI();
        initData();
    }

    private void initUI() {
    }

    private void initData() {
        long count = DBManager.getWordManager().getCount(Word_niujinban_7_1.class);
        paginationWidget = new PaginationWidget();
        paginationWidget.init(activity, sjRoot);
        paginationWidget.getPageBean().setAllCount((int) count);
        paginationWidget.setPageSize(13);
        paginationWidget.setPageIndicator((int) count);
        paginationWidget.setHandler(wordHandler);

        wordBeanOldList = DBManager.getWordManager().get(Word_niujinban_7_1.class, 0, paginationWidget.getPageBean().getPageSize());
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

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
