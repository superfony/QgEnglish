package qge.cn.com.qgenglish.app.word;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Common;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.word.check.JcChoseWordAct;
import qge.cn.com.qgenglish.app.word.check.JcCpointAdapter;
import qge.cn.com.qgenglish.app.word.review.FxChoseWordAct;
import qge.cn.com.qgenglish.app.word.review.FxCpointAdapter;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointAdapter;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.view.ScrollLayout;

/**
 * 单词选关界面    识记  复习  检查  统计
 */
public class WordAct extends BaseActivity {
    @Bind(R.id.word_title)
    TextView wordTitle;
    @Bind(R.id.word_title_header_lay)
    RelativeLayout wordTitleHeaderLay;
    @Bind(R.id.main_footbar_sj)
    RadioButton mainFootbarSj;
    @Bind(R.id.main_footbar_fx)
    RadioButton mainFootbarFx;
    @Bind(R.id.main_footbar_jc)
    RadioButton mainFootbarJc;
    @Bind(R.id.main_footbar_tj)
    RadioButton mainFootbarTj;
    @Bind(R.id.main_linearlayout_footer)
    LinearLayout mainLinearlayoutFooter;
    @Bind(R.id.word_cp_lv)
    ListView word_cp_lv;
    @Bind(R.id.word_scrolllayout)
    ScrollLayout wordScrolllayout;
    //    @Bind(R.id.info_container)
//    LinearLayout info_container;
    @Bind(R.id.word_jc_lv)
    ListView word_jc_lv;
    @Bind(R.id.jc_btn)
    Button jc_btn;
    @Bind(R.id.fx_word_lv)
    ListView fx_word_lv;
    @Bind(R.id.fx_btn)
    Button fx_btn;
    private int mViewCount;
    private int mCurSel;// 设置底部焦点索引
    private RadioButton[] mButtons;
    private String[] wordTitles;
    private String TAG = "WordAct";
    private List<CpointBean> cpointBeanList = new ArrayList<CpointBean>();
    private CpointAdapter cpointAdapter;
    private List<CpointBean> cpointBeanListJc = new ArrayList<CpointBean>();
    private JcCpointAdapter cpointAdapterJc;
    private List<CpointBean> cpointBeanListFx = new ArrayList<CpointBean>();
    private FxCpointAdapter cpointAdapterFx;
    private String tableName;// 操作的那张表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_act);
        ButterKnife.bind(this);
        activity = this;
        initPageScroll();
        initData();
    }

    private void initData() {
        Intent intent = activity.getIntent();
        tableName = intent.getStringExtra("tableName");
        if (tableName == null)
            return;
        // 查询关卡表 没有表关卡则第一次需要插入
        //有则取数据库数据
        boolean isHave = DBManager.getInstance().isCpoinHave(tableName);
        if (isHave) {
            cpointBeanList = DBManager.getInstance().get(CpointBean.class, " where tablename='" + tableName + "'");
            cpointBeanListJc = cpointBeanList;
            cpointBeanListFx = cpointBeanList;
        } else {
            long wordSize = DBManager.getWordManager().getWordCount(tableName);
            int copintSize = (int) Math.ceil((double) ((float) wordSize / (float) 14));


            for (int i = 1; i <= copintSize; i++) {
                CpointBean cpointBean = new CpointBean();
                cpointBean.name = "第" + i + "关";
                cpointBean.tablename = tableName;
                cpointBean.code = i;
                cpointBean.isPromiss = 1;
                cpointBean.ischecked = 0;
                cpointBean.state = 0;
                cpointBeanList.add(cpointBean); // 识记
                cpointBeanListJc.add(cpointBean); // 检查
                cpointBeanListFx.add(cpointBean); // 复习
                DBManager.getInstance().insert(cpointBean, TableName.cpointBean);
            }
        }

        cpointAdapter = new CpointAdapter(activity, cpointBeanList);
        word_cp_lv.setAdapter(cpointAdapter);
        word_cp_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                CpointBean cpointBean = (CpointBean) cpointAdapter.getItem(position);
                intent.putExtra("cpointBean", cpointBean);
                intent.setClass(activity, SjWordAct.class);  //  单词识记的
                activity.startActivity(intent);
            }
        });


        //复习相关
        cpointAdapterFx = new FxCpointAdapter(activity, cpointBeanListFx);
        fx_word_lv.setAdapter(cpointAdapterFx);
        fx_word_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //检查相关
        cpointAdapterJc = new JcCpointAdapter(activity, cpointBeanListJc);
        word_jc_lv.setAdapter(cpointAdapterJc);
        word_jc_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    // 检查相关的
    @OnClick(R.id.jc_btn)
    public void jc_btn_onClick() {
        Intent intent = new Intent();
        ArrayList<CpointBean> cpointBeenArray = new ArrayList<CpointBean>();
        for (int i = 0; i < cpointBeanListFx.size(); i++) {
            CpointBean cpointBean = cpointBeanListFx.get(i);
            if (cpointBean.ischecked == 1)
                cpointBeenArray.add(cpointBean);
        }
        if (cpointBeenArray.size() <= 0)
            return;
        intent.putExtra("cpointArray", cpointBeenArray);

        intent.setClass(activity, JcChoseWordAct.class);
        activity.startActivity(intent);


    }


    // 复习跳转的 这里需要手机选择的关卡
    @OnClick(R.id.fx_btn)
    public void fx_btn_onClick() {
        Intent intent = new Intent();
        ArrayList<CpointBean> cpointBeenArray = new ArrayList<CpointBean>();
        for (int i = 0; i < cpointBeanListFx.size(); i++) {
            CpointBean cpointBean = cpointBeanListFx.get(i);
            if (cpointBean.ischecked == 1)
                cpointBeenArray.add(cpointBean);
        }
        if (cpointBeenArray.size() <= 0)
            return;
        intent.putExtra("cpointArray", cpointBeenArray);
        intent.setClass(activity, FxChoseWordAct.class);
        activity.startActivity(intent);
        // 需要进行还原设置
    }

    /**
     * 初始化水平滚动翻页
     */
    private void initPageScroll() {
        wordTitles = getResources().getStringArray(R.array.word_titles);
        mViewCount = wordScrolllayout.getChildCount();
        mButtons = new RadioButton[mViewCount];

        for (int i = 0; i < mViewCount; i++) {
            mButtons[i] = (RadioButton) mainLinearlayoutFooter.getChildAt(i * 2);
            mButtons[i].setTag(i);// 设置隐含的tag值 从0开始
            mButtons[i].setChecked(false);
            mButtons[i].setOnClickListener(new View.OnClickListener() {// 底部菜单点击事件
                public void onClick(View v) {
                    int pos = (Integer) (v.getTag());
                    if (mCurSel == pos) {
                        switch (pos) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                    }
                    wordScrolllayout.snapToScreen(pos); // 设置页面跳转
                }
            });
        }
        // 设置第一显示屏 滑屏事件的具体实现
        mCurSel = 0;
        mButtons[mCurSel].setChecked(true);
        wordScrolllayout
                .SetOnViewChangeListener(new ScrollLayout.OnViewChangeListener() {
                    public void OnViewChange(int viewIndex) {
                        switch (viewIndex) {
                            case 0:
                                break;
                            case 1:
//                              Common.replaceRightFragment(activity, new WordFxFat(),
//                                        false, R.id.info_container, "key");
                                // fat方式
//                                Common.clear(activity);
//                                Common.replaceRightFragment(activity, new WordFxFat(),
//                                        false, R.id.info_container);
                                break;
                            case 2:
                                // 检查
                                break;
                            case 3: //  统计
                                break;
                        }
                        setCurPoint(viewIndex);
                    }
                });
    }

    /**
     * 设置底部栏当前焦点
     *
     * @param index
     */
    private void setCurPoint(int index) {
        if (index < 0 || index > mViewCount - 1 || mCurSel == index)
            return;
        mButtons[mCurSel].setChecked(false);
        mButtons[index].setChecked(true);
        wordTitle.setText(wordTitles[index]);
        mCurSel = index;// TODO 底部菜单选中位置
        if (index == 0) {
            wordTitleHeaderLay.setVisibility(View.VISIBLE);
        } else if (index == 1) {
            wordTitleHeaderLay.setVisibility(View.VISIBLE);
        } else if (index == 2) {
            wordTitleHeaderLay.setVisibility(View.VISIBLE);
        } else if (index == 3) {
            wordTitleHeaderLay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Common.map.clear();
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    } //Phrase

    @Override
    protected void onResume() {
        super.onResume();
        cpointBeanList = DBManager.getInstance().get(CpointBean.class, " where tablename='" + tableName + "'");
        cpointBeanListJc = cpointBeanList;
        cpointBeanListFx = cpointBeanList;
        cpointAdapter.updateListView(cpointBeanList);
        cpointAdapterJc.updateListView(cpointBeanListJc);
        cpointAdapterFx.updateListView(cpointBeanListFx);
    }
}
