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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Common;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.schoolinfo.UserInfo;
import qge.cn.com.qgenglish.app.word.check.JcChoseWordAct;
import qge.cn.com.qgenglish.app.word.check.JcCpointAdapter;
import qge.cn.com.qgenglish.app.word.review.FxChoseWordAct;
import qge.cn.com.qgenglish.app.word.review.FxCpointAdapter;
import qge.cn.com.qgenglish.app.word.table.Tj;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointAdapter;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.cache.CacheManager;
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
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_mobile)
    TextView tvMobile;
    @Bind(R.id.tv_school)
    TextView tvSchool;
    @Bind(R.id.tv_grade)
    TextView tvGrade;
    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    @Bind(R.id.tv_already)
    TextView tvAlready;
    @Bind(R.id.tv_timeused)
    TextView tvTimeused;
    @Bind(R.id.tv_average)
    TextView tvAverage;
    @Bind(R.id.tv_rest)
    TextView tvRest;
    @Bind(R.id.tv_timeneed)
    TextView tvTimeneed;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.tv_coin)
    TextView tvCoin;
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
    // private ArrayList<Menu> arrayList = new ArrayList<Menu>(); // 网络菜单
    private int allCount = 0;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_act);
        ButterKnife.bind(this);
        activity = this;
        userInfo = (UserInfo) CacheManager.readObject(activity, "userinfo");
        initPageScroll();
        initData();

    }

    private void initData() {
        Intent intent = activity.getIntent();
        tableName = intent.getStringExtra("tableName");
        if (tableName == null)
            return;
        // 查询关卡表 没有表关卡则第一次需要插入
        // 根据表名查询关卡选项  有则取数据库数据
        boolean isHave = DBManager.getInstance().isCpoinHave(tableName, userInfo.getUserInfo().getId() + "");
        if (isHave) {
            cpointBeanList = DBManager.getInstance().get(CpointBean.class, " where tablename='" + tableName + "' and userid='" + userInfo.getUserInfo().getId() + "'");
            cpointBeanListJc = cpointBeanList;
            cpointBeanListFx = cpointBeanList;
        } else {
            // 四六级单词进行网络请求  其它的读取本地数据库
            if (TableName.word_six.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.word_six).toString(); //
                startHttpGet(url, null);

            } else if (TableName.word_four.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.word_four).toString();
                startHttpGet(url, null);
            } else if (TableName.phrase_small.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.phrase_small).toString(); //
                startHttpGet(url, null);

            } else if (TableName.phrase_middle.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.phrase_middle).toString();
                startHttpGet(url, null);

            } else if (TableName.phrase_high.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.phrase_high).toString();
                startHttpGet(url, null);

            } else if (TableName.word_small_ty.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.small_ty).toString();
                startHttpGet(url, null);
            } else if (TableName.word_high_ty.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.high_ty).toString();
                startHttpGet(url, null);
            } else if (TableName.word_middle_ty.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.middle_ty).toString();
                startHttpGet(url, null);
            } else if (TableName.word_tfys_ty.equals(tableName)) {
                String url = String.format(RequestUrls.COMMONURL, RequestUrls.tfys_ty).toString();
                startHttpGet(url, null);
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
                    cpointBean.wordcount = (int) wordSize;
                    cpointBean.userid = userInfo.getUserInfo().getId() + "";
                    cpointBeanList.add(cpointBean); // 识记
                    cpointBeanListJc.add(cpointBean); // 检查
                    cpointBeanListFx.add(cpointBean); // 复习
                    DBManager.getInstance().insert(cpointBean, TableName.cpointBean);
                }
                initTjdata((int) wordSize);

            }
        }

        cpointAdapter = new CpointAdapter(activity, cpointBeanList);
        word_cp_lv.setAdapter(cpointAdapter);
        word_cp_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                CpointBean cpointBean = (CpointBean) cpointAdapter.getItem(position);
                if (position > 0) {
                    CpointBean cpointBean1 = (CpointBean) cpointAdapter.getItem(position - 1);
                    if (cpointBean1.state == 0) {
                        return;
                    }
                }
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

    // 第一次初始化才会执行
    private void initTjdata(int wordSize) {
        Tj tj = new Tj();
        tj.tablename = tableName;
        tj.tabledes = ((FonyApplication) activity.getApplication()).tableDes;
        tj.allwordcount = (int) wordSize;
        tj.goldcoin = 0;
        tj.jccount = 0;
        tj.jcwrong = 0;
        tj.lcount = 0;
        tj.resdual = 0;
        tj.redudate = (new DecimalFormat("0.0").format(wordSize / 300)) + "天";// 语句剩余时间
        tj.userid = userInfo.getUserInfo().getId();
        DBManager.getWordManager().insert(tj, TableName.tongj);
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
        intent.putExtra("tableName", tableName);
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
                            case 3: // 统计
                                getTJdata();
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
    protected void onRestart() {
        super.onRestart();
        cpointBeanList = DBManager.getInstance().get(CpointBean.class, " where tablename='" + tableName + "' and userid='" + userInfo.getUserInfo().getId() + "'");
        cpointBeanListJc = cpointBeanList;
        cpointBeanListFx = cpointBeanList;
        cpointAdapter.updateListView(cpointBeanList);
        cpointAdapterJc.updateListView(cpointBeanListJc);
        cpointAdapterFx.updateListView(cpointBeanListFx);
    }

    // 处理成功返回的json
    @Override
    protected void onSuccessBase(String s) {
        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<Menu>>>() {
        }.getType());
        String wordNum = result.getMessage();
        allCount = Integer.parseInt(wordNum);
        ArrayList arrayList = (ArrayList) result.getData();
        int copintSize = arrayList.size();
        for (int i = 1; i <= copintSize; i++) {
            Menu menu = (Menu) arrayList.get(i - 1);
            CpointBean cpointBean = new CpointBean();
            cpointBean.name = "第" + i + "关";
            cpointBean.tablename = tableName;
            cpointBean.code = i;
            cpointBean.isPromiss = 1;
            cpointBean.ischecked = 0;
            cpointBean.state = 0;
            cpointBean.id = menu.id;
            cpointBean.menuName = menu.menuName;
            cpointBean.pmenuId = menu.pmenuId;
            cpointBean.menuType = menu.menuType;
            cpointBean.sortIndex = menu.sortIndex;
            cpointBean.child = menu.child;
            cpointBean.wordcount = allCount;
            cpointBean.userid = userInfo.getUserInfo().getId() + "";
            cpointBeanList.add(cpointBean);   //识记
            cpointBeanListJc.add(cpointBean); //检查
            cpointBeanListFx.add(cpointBean); // 复习
            DBManager.getInstance().insert(cpointBean, TableName.cpointBean);
        }
        cpointAdapter.updateListView(cpointBeanList);
        cpointAdapterJc.updateListView(cpointBeanListJc);
        cpointAdapterFx.updateListView(cpointBeanListFx);
        initTjdata(allCount);
        super.onSuccessBase(s);
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    // 统计信息
    private void getTJdata() {

        UserInfo.UserInfoBean user = userInfo.getUserInfo();
        tvName.setText(user.getUserName());
        tvArea.setText(user.getCity() + user.getArea());
        tvGrade.setText(user.getGrade());
        tvMobile.setText(user.getPhone());
        tvSchool.setText(user.getSchoolName());

        Tj tj = DBManager.getWordManager().getT(Tj.class, "where tablename='" + tableName + "' and userid=" + user.getId());
        if (tj == null)
            return;
        tvLevel.setText(tj.tabledes);  // 当前词库
        tvAlready.setText(tj.lcount + "");// 已识记单词数量
        tvRest.setText((tj.allwordcount - tj.lcount) + "");// 剩余单词数量
        tvTimeneed.setText(new DecimalFormat("0.0").format((float) (tj.allwordcount - tj.lcount) / 300) + "天");// 预计所需时间

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        String result = numberFormat.format((float) (tj.jccount - tj.jcwrong) / (float) tj.jccount * 100);
        tvResult.setText(String.format("已检查 %s /错误 %s /正确率 %s", tj.jccount, tj.jcwrong, result + "%").toString());
        tvCoin.setText(tj.goldcoin + "");// 金币
        //
    }
}
