package qge.cn.com.qgenglish.app.phrase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyang.android.http.pagination.PageBean;
import com.baiyang.android.util.basic.ToastHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.PaginationWidget;
import qge.cn.com.qgenglish.app.TableName;
import qge.cn.com.qgenglish.app.word.EntryPopListener;
import qge.cn.com.qgenglish.app.word.EntryPopWindow;
import qge.cn.com.qgenglish.app.word.SjwordAdapter;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.app.word.table.Word_unskilled;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;
import qge.cn.com.qgenglish.db.DBManager;
import qge.cn.com.qgenglish.iciba.WordBean;
import qge.cn.com.qgenglish.iciba.icibautil.Mp3Player;

/**
 * 识记
 * 短语识记通用类
 */
public class SjPhraseAct extends BaseActivity {
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
    private PaginationWidget paginationWidget;
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
    RelativeLayout paginationWidgetCtrl;
    private TextToSpeech textToSpeech = null;

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
        title.setText(cpointBean.name);
        getAlreadyNum(cpointBean.tablename);
        switchCls(clsName);
        initTextToSpeek();
        initData();
        initPaginationWidget(count);
        initAdpater();

    }

    public void initTextToSpeek() {
        textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                } else {
                    Toast.makeText(activity, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
        long countlong = DBManager.getInstance().getCount(cls);
        count = (int) countlong;
        wordBeanOldList = (ArrayList<Word_niujinban_7_1>) DBManager.getInstance().get(cls, "_id", "asc", (current - 1) * 2 * SjPhraseAct.pageSize, SjPhraseAct.pageSize); // 第一页
    }

    private void initPaginationWidget(int count) {
        paginationWidget = new PaginationWidget();
        paginationWidget.init(activity, sjRoot);
        paginationWidget.getPageBean().setAllCount((int) count);
        paginationWidget.setPageSize(SjPhraseAct.pageSize);
        paginationWidget.getPageBean().setCurrentPage((current - 1) * 2 + 1);
        paginationWidget.setCurrent(current);
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
                //  icibaHttp(word, wordHandler);// 读取发音
                textToSpeech.setLanguage(Locale.ENGLISH); // 设置语言
                textToSpeech.setPitch(0.0f); //
                textToSpeech.setSpeechRate(0.8f); // 控制语速
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak("send for", TextToSpeech.QUEUE_FLUSH, null, null);
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
//                                User user=(User) CacheManager.readObject(activity, "user");
//                                wordunskilled.user_id=user.getUserinfo().getUserid();
                                wordunskilled.user_id = "101";
                                DBManager.getWordManager().insert(wordunskilled, "word_unskilled");
                                Toast.makeText(activity, "添加成功", Toast.LENGTH_SHORT).show();
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
            if (msg.what == 0) {
                WordBean wordBean = (WordBean) msg.obj;
                String word = wordBean.getKey();
                String psE_value = wordBean.getPsE();
                String psA_value = wordBean.getPsA();
                String acceptation_value = wordBean.getAcceptation();
                mp3Play(word, Mp3Player.USA_ACCENT);
            } else if (msg.what == 1) {


            } else if (msg.what == 100) {
                // 这里来判断 页数不能
                //   下一页
                PageBean pageBean = (PageBean) msg.obj;
                if (pageBean.getCurrentPage() <= ((current - 1) * 2 + 2) && pageBean.getCurrentPage() >= (current - 1) * 2 + 1) {
                    wordBeanOldList = (List<Word_niujinban_7_1>) DBManager.getWordManager()
                            .get(cls, "_id", "asc", (pageBean.getCurrentPage() - 1) * (pageBean.getPageSize()), pageBean.getPageSize());
                } else if (pageBean.getCurrentPage() == ((current - 1) * 2 + 3)) {
                    wordBeanOldList = (List<Word_niujinban_7_1>) DBManager.getWordManager()
                            .get(cls, "_id", "asc", (current - 1) * 2 * SjPhraseAct.pageSize, pageBean.getPageSize() * 2);
                } else {
                    //
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

                ToastHelper.toast(activity, "通关了");
                entryPopWindow = new EntryPopWindow(activity, listener, cpointBean);// TODO
                entryPopWindow.show(sjRoot);
                paginationWidgetCtrl.setVisibility(View.INVISIBLE);

            }
        }
    };

    {
        listener = new EntryPopListener() {
            @Override
            public void doQuery(String req) {
                // 操作数据库 记录关卡状态
                ToastHelper.toast(activity, "操作数据库 记录关卡状态");
                cpointBean.state = 1;
                DBManager.getInstance().update(TableName.cpointBean, "state", 1, "where tablename='" + cpointBean.tablename + "' and code=" + cpointBean.code);
                cpointBean = DBManager.getInstance().getT(CpointBean.class, "where tablename='" + cpointBean.tablename + "' and code=" + (cpointBean.code + 1));
                if (cpointBean == null)
                    return;
                current = cpointBean.code;
                title.setText(cpointBean.name);
                getAlreadyNum(cpointBean.tablename);
                paginationWidget.setCurrent(current);
                paginationWidget.getPageBean().setCurrentPage((current - 1) * 2 + 1);
                wordHandler.obtainMessage(100, paginationWidget.getPageBean()).sendToTarget();
                paginationWidgetCtrl.setVisibility(View.VISIBLE);
            }
        };
    }
// 今天掌握 28个/已掌握28个/ 共729个


    //
}
