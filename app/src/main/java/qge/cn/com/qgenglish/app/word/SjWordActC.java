package qge.cn.com.qgenglish.app.word;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.pagination.PageBean;
import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.PaginationWidget;
import qge.cn.com.qgenglish.app.PaginationWidgetC;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.schoolinfo.UserInfo;
import qge.cn.com.qgenglish.app.word.table.Tj;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.app.word.table.Word_unskilled;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.cache.CacheManager;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.SentBean;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

/**
 * 识记
 * 单词识记通用类  // 添加短语的
 */
public class SjWordActC extends BaseActivity {
    @Bind(R.id.sj_lv)
    ListView sjLv;
    @Bind(R.id.sj_root)
    RelativeLayout sjRoot;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.graspNum)
    TextView graspNum;
    @Bind(R.id.alreadyNum)
    TextView alreadyNum;
    private int curPage;// 当前页数
    private int allCount;// 总页数
    private PaginationWidgetC paginationWidget;
    private SjwordAdapter sjwordAdapter;
    private List<Word_niujinban_7_1> wordBeanOldList = new ArrayList<Word_niujinban_7_1>();
    private int count;
    private static int pageSize = 7;
    public Class<?> cls;
    private CpointBean cpointBean;
    private String clsName;
    private int current = 0;// 当前页
    protected EntryPopWindow entryPopWindow;
    protected EntryPopListener listener;
    private RelativeLayout paginationWidgetCtrl;
    private FonyApplication.QGTYPE qgtype;
    private Word_niujinban_7_1 wordBeanOld;
    private TextView phonetic_tv;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sj_word_act);
        ButterKnife.bind(this);
        activity = this;
        paginationWidgetCtrl = (RelativeLayout) sjRoot.findViewById(R.id.pagination_widget_ctrl);
        cpointBean = (CpointBean) activity.getIntent().getSerializableExtra("cpointBean");
        clsName = cpointBean.tablename;
        current = cpointBean.code; //关卡
        userInfo = (UserInfo) CacheManager.readObject(activity, "userinfo");
        title.setText(cpointBean.name);
        getAlreadyNum(cpointBean.tablename);
        switchCls(clsName);
        initPaginationWidget(cpointBean.wordcount);
        initAdpater();
        initData();
        initTTS();
        qgtype = ((FonyApplication) activity.getApplication()).qgtype;

    }

    private void switchCls(String clsName) {
        try {
            clsName = clsName.substring(0, 1).toUpperCase() + clsName.substring(1);
            cls = Class.forName("qge.cn.com.qgenglish.app.word.table." + clsName);
        } catch (Exception e) {

        }
    }

    // 获取已经掌握的单词的个数
    private void getAlreadyNum(String tableName) {
        long allCount = DBManager.getInstance().getCount(tableName);
        long count = DBManager.getInstance().getCount(CpointBean.class, "where tablename='" + tableName + "' and state=1");
        if (count >= allCount)
            alreadyNum.setText(allCount + "");
        else
            alreadyNum.setText(14 * count + "");
    }

    private void initData() {
        //long countlong = DBManager.getWordManager().getCount(cls); // 查询本地的
        wordBeanOldList = (ArrayList<Word_niujinban_7_1>) DBManager.getWordManager().getC(cls, "where pass=" + cpointBean.code, "_id", "asc", SjWordActC.pageSize, 0); // 第一页
        if (wordBeanOldList == null || wordBeanOldList.size() == 0) {
            String url = String.format(RequestUrls.CONTENTURL, cpointBean.id).toString();
            startHttpGet(url, null);
        } else {
            wordHandler.sendEmptyMessage(1);
        }
    }

    private void initPaginationWidget(int count) {
        paginationWidget = new PaginationWidgetC();
        paginationWidget.init(activity, sjRoot);
        paginationWidget.getPageBean().setAllCount((int) count);
        paginationWidget.setPageSize(SjWordActC.pageSize);
        paginationWidget.getPageBean().setCurrentPage((current - 1) * 2 + 1);
        paginationWidget.setCurrent(current);
        paginationWidget.setPageIndicator((int) count);
        paginationWidget.setHandler(wordHandler);
    }

    int postionCopy = 0;

    private void initAdpater() {
        sjwordAdapter = new SjwordAdapter(activity, wordBeanOldList);
        sjLv.setAdapter(sjwordAdapter);
        sjLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout interpretationLay = (LinearLayout) view.findViewById(R.id.interpretation_lay);
                phonetic_tv = (TextView) interpretationLay.findViewById(R.id.phonetic);

                wordBeanOld = wordBeanOldList.get(position);
                String queue = wordBeanOld.queue;
                // 这里需要修改
                //每个单词三遍发音
                if (position != postionCopy) {
                    wordBeanOldList.get(postionCopy).queue = "1";
                    postionCopy = position;
                }

                if (!TextUtils.isEmpty(queue)) {
                    if (queue.equals("1")) {
                        wordBeanOld.queue = "2";
                        interpretationLay.setVisibility(View.INVISIBLE);
                    } else if (queue.equals("2")) {
                        wordBeanOld.queue = "3";
                        interpretationLay.setVisibility(View.VISIBLE);
                    } else if (queue.equals("3")) {
                        wordBeanOld.queue = "1";
                        interpretationLay.setVisibility(View.INVISIBLE);
                    }
                } else {
                    wordBeanOld.queue = "2";
                    interpretationLay.setVisibility(View.INVISIBLE);
                }
                String word = wordBeanOld.english;
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
        });

        sjLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Word_niujinban_7_1 wordBeanOld = wordBeanOldList.get(position);

                // 对话框   添加生词  添加详情
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("请选择操作");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("生词", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 添加到生词本
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("是否加入生词本?");
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 添加生词本
                                Word_unskilled wordunskilled = new Word_unskilled();
                                wordunskilled.english = wordBeanOld.english;
                                wordunskilled.pass = wordBeanOld.pass;
                                wordunskilled.phonetic = wordBeanOld.phonetic;
                                wordunskilled.queue = wordBeanOld.queue;
                                wordunskilled.sen = wordBeanOld.sen;
                                wordunskilled.szh = wordBeanOld.szh;
                                wordunskilled.sense = wordBeanOld.sense;

                                wordunskilled.user_id = userInfo.getUserInfo().getId();
                                wordunskilled.belong = getWordType();
                                boolean sucess = DBManager.getWordManager().insert(wordunskilled, TableName.word_unskilled);
                                if (sucess) {
                                    ToastHelper.toast(activity, "添加成功!");
                                } else {
                                    ToastHelper.toast(activity, "添加失败!");
                                }

                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                        dialog.cancel();
                    }
                }).setNegativeButton("详情", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // 查询例句 例句
                        Dialog dialog;
                        LayoutInflater inflater = LayoutInflater.from(activity);
                        LinearLayout layout = (LinearLayout) inflater.inflate(
                                R.layout.sj_dialog, null);
                        dialog = new AlertDialog.Builder(activity).create();
                        dialog.setCancelable(true);
                        dialog.show();
                        dialog.getWindow().setContentView(layout);
                        TextView english_tv = (TextView) layout.findViewById(R.id.english);
                        TextView phonetic_tv = (TextView) layout.findViewById(R.id.phonetic);
                        TextView sense_tv = (TextView) layout.findViewById(R.id.sense);
                        TextView sen_tv = (TextView) layout.findViewById(R.id.sen);
                        TextView szh_tv = (TextView) layout.findViewById(R.id.szh);
                        english_tv.setText(wordBeanOld.english);
                        phonetic_tv.setText(wordBeanOld.phonetic);
                        sense_tv.setText(wordBeanOld.sense);
                        sen_tv.setText(wordBeanOld.sen);
                        szh_tv.setText(wordBeanOld.szh);
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    public Handler wordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {  //  单词发音的操作
                WordBean wordBean = (WordBean) msg.obj;
                String word = wordBean.getKey();
                String psE_value = wordBean.getPsE();
                String psA_value = wordBean.getPsA();
                String pronA = wordBean.getPronA();//  美式发音
                String acceptation_value = wordBean.getAcceptation();
                if (TextUtils.isEmpty(wordBeanOld.phonetic)) {
                    SentBean sentBean = new SentBean();
                    ArrayList<SentBean> sentBeanArrayList = wordBean.sentBeanArrayList;
                    if (sentBeanArrayList != null && sentBeanArrayList.size() > 0) {
                        sentBean = sentBeanArrayList.get(0);
                        wordBeanOld.sen = sentBean.orig;
                        wordBeanOld.szh = sentBean.trans;
                    }
                    phonetic_tv.setText("/" + psA_value + "/");
                    wordBeanOld.phonetic = psA_value;
                    String sql = String.format("update %s set phonetic='%s' , sen='%s' , szh='%s'  %s", cpointBean.tablename, "/" + psA_value + "/", sentBean.orig, sentBean.trans, " where english='" + word + "'").toString();
                    DBManager.getWordManager().update(cpointBean.tablename, sql);
                }
                if (!TextUtils.isEmpty(pronA)) {
                    mp3Play(word, Mp3Player.USA_ACCENT);
                } else {
                    textToSpeek(word);
                }
            } else if (msg.what == 1) {
                sjwordAdapter.updateListView(wordBeanOldList);
            } else if (msg.what == 100) {
                // 下一页的判断 这里针对分页按钮点击 根据current 来控制 offsert 参数值来进行查询当前关卡的单词记录。
                // 过关后自动加载第一页数据,手动点击下一页按钮 加载第二页数据 。
                PageBean pageBean = (PageBean) msg.obj;
                if (pageBean.getCurrentPage() <= ((current - 1) * 2 + 2) && pageBean.getCurrentPage() >= (current - 1) * 2 + 1) {
//                    wordBeanOldList = (List<Word_niujinban_7_1>) DBManager.getWordManager()
//                            .get(cls, "_id", "asc", (pageBean.getCurrentPage() - 1) * (pageBean.getPageSize()), pageBean.getPageSize());
                    wordBeanOldList = (ArrayList<Word_niujinban_7_1>) DBManager
                            .getWordManager().getC(cls, "where pass=" + cpointBean.code, "_id", "asc", SjWordActC.pageSize, (pageBean.getCurrentPage() - (current - 1) * 2 - 1) * 7); // 第一页
                    if (wordBeanOldList == null || wordBeanOldList.size() == 0) {
                        String url = String.format(RequestUrls.CONTENTURL, cpointBean.id).toString();
                        startHttpGet(url, null);
                        return;
                    }
                } else if (pageBean.getCurrentPage() == ((current - 1) * 2 + 3)) {  // 14 个单词全部加载的时候
//                    wordBeanOldList = (List<Word_niujinban_7_1>) DBManager.getWordManager()
//                            .get(cls, "_id", "asc", (current - 1) * 2 * SjWordActC.pageSize, pageBean.getPageSize() * 2);
                    wordBeanOldList = (ArrayList<Word_niujinban_7_1>) DBManager.getWordManager().getC(cls, "where pass=" + cpointBean.code, "_id", "asc", SjWordActC.pageSize * 2, 0); // 第一页
                } else {
                    return;
                }
                sjwordAdapter.updateListView(wordBeanOldList);
                paginationWidget.setPageIndicator(pageBean.getAllCount());
            } else if (msg.what == 101) {  // 混淆单词顺序
                Collections.shuffle(wordBeanOldList);
                sjwordAdapter.updateListView(wordBeanOldList);
            } else if (msg.what == 102) {  // 通关的操作
                // 通关的操作 pop
                // 隐藏 翻页按钮
                // 播放音乐
                // 获取路径
                if (current > 5 && current % 3 == 0 && current % 2 == 0) {
                    mp3Playend(R.raw.o);
                } else if (current > 2 && current % 3 == 0 && current % 2 == 1) {
                    mp3Playend(R.raw.j);
                } else {
                    mp3Playend(R.raw.el);
                }
                entryPopWindow = new EntryPopWindow(activity, listener, cpointBean);// TODO
                entryPopWindow.show(sjRoot);
                sjLv.setEnabled(false);
                paginationWidgetCtrl.setVisibility(View.INVISIBLE);
                // 跟新获取金币数量
                if (cpointBean.state == 1)
                    return;
                Tj tj = DBManager.getWordManager().getT(Tj.class, "where tablename='" + cpointBean.tablename + "' and userid='" + userInfo.getUserInfo().getId() + "'");
                tj.lcount = cpointBean.code * 14;
                if (tj.lcount > tj.allwordcount)
                    tj.lcount = tj.allwordcount;
                DBManager.getWordManager().update(TableName.tongj, "lcount", tj.lcount, "where tablename='" + cpointBean.tablename + "'  and userid='" + userInfo.getUserInfo().getId() + "'");
                tj.goldcoin = tj.goldcoin + 5;
                DBManager.getWordManager().update(TableName.tongj, "goldcoin", tj.goldcoin, "where tablename='" + cpointBean.tablename + "'  and userid='" + userInfo.getUserInfo().getId() + "'");

            }
        }
    };

    {
        listener = new EntryPopListener() {
            @Override
            public void doQuery(String req) {
                // 操作数据库 记录关卡状态
                stopMp3();
                sjLv.setEnabled(true);
                cpointBean.state = 1;
                DBManager.getInstance().update(TableName.cpointBean, "state", 1, "where tablename='" + cpointBean.tablename + "' and code=" + cpointBean.code + " and userid='" + userInfo.getUserInfo().getId() + "'");
                cpointBean = DBManager.getInstance().getT(CpointBean.class, "where tablename='" + cpointBean.tablename + "' and code=" + (cpointBean.code + 1) + " and userid='" + userInfo.getUserInfo().getId() + "'");
                if (cpointBean == null)
                    return;
                current = cpointBean.code;
                title.setText(cpointBean.name);
                getAlreadyNum(cpointBean.tablename);
                paginationWidget.setCurrent(current);
                paginationWidget.getPageBean().setCurrentPage((current - 1) * 2 + 1);
                wordHandler.obtainMessage(100, paginationWidget.getPageBean()).sendToTarget();
                paginationWidgetCtrl.setVisibility(View.VISIBLE);
                // 请求第二页数据
            }
        };
    }
// 今天掌握 28个/已掌握28个/ 共729个


    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void onSuccessBase(String s) {
        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<Word_niujinban_7_1>>>() {
        }.getType());
        ArrayList arrayList = (ArrayList) result.getData();
        // 这里插入数据并保存 关卡编号
        DBManager.getWordManager().insertListWithCoptionid(arrayList, cpointBean.tablename, cpointBean.code); //
        //加载第一页数据 根据offsert 进行控制分页数据
        wordBeanOldList = (ArrayList<Word_niujinban_7_1>) DBManager.getWordManager().getC(cls, "where pass=" + cpointBean.code, "_id", "asc", SjWordActC.pageSize, 0); // 第一页
        wordHandler.sendEmptyMessage(1);
        super.onSuccessBase(s);
    }
}
