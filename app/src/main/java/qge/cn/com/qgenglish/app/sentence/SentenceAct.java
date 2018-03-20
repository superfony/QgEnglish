package qge.cn.com.qgenglish.app.sentence;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.articel.ArticelAct;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.hearing.HearAndLisAct;
import qge.cn.com.qgenglish.app.hearing.HearingAct;
import qge.cn.com.qgenglish.application.FonyApplication;

/**
 * Created by fony on 2018/2/28.
 * mp3 文件发音 列表
 * 接收一个menu
 */

public class SentenceAct extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private SentenceAdapter sentenceAdapter;
    private FonyApplication.ArticleOrListener articleOrListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sentence_act);
        ButterKnife.bind(this);
        activity = this;
        initData();
        reqMenu();
        articleOrListener = ((FonyApplication) activity.getApplication()).articleOrListener;
    }

    private void initData() {
        //title.setText("");//
        sentenceAdapter = new SentenceAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(sentenceAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Menu menu = menuArrayList.get(position);
                intent.putExtra("menu", menu); // 传递文章的url/ 或者下一级菜单的id
                if ("false".equals(menu.child)) {
                    intent.setClass(activity, SentenceAct.class);
                } else if ("true".equals(menu.child)) {

//                    switch (articleOrListener){
//                        case ARTICLE:
//                            intent.setClass(activity,RichTextAct.class);
//                            break;
//                        case LISTENER:
//                            intent.setClass(activity,HearingList.class);
//                            break;
//                        case ARTICLE_OR_LISTENER:
//                           // intent.setClass(activity,HearAndLisAct.class);
//                            break;
//                    }
                    if (TextUtils.isEmpty(menu.menuType))
                        return;
                    switch (menu.menuType) {
                        case "01":
                            break;
                        case "02":
                            break;
                        case "03":// 听力训练
                            intent.setClass(activity, HearingList.class);
                            break;
                        case "04": // 阅读理解
                            intent.setClass(activity, ArticelAct.class);
                            break;
                        case "05":
                            intent.setClass(activity, RichTextAct.class);
                            break;
                        case "06":
                            intent.setClass(activity, RichTextAct.class);
                            break;
                        case "07":
                            intent.setClass(activity, RichTextAct.class);
                            break;
                        case "08":
                            intent.setClass(activity, RichTextAct.class);
                            break;
                        case "09":
                            intent.setClass(activity, RichTextAct.class);
                            break;
                        case "10": //选择题 训练
                            intent.setClass(activity, SpecialList.class);
                            break;
                    }

                }
                activity.startActivity(intent);
            }
        });
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        resultMenu(s);
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }


    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        switch (msg.what) {
            case 0:
                break;
            case 1:
                sentenceAdapter.updateListView(menuArrayList);
                title.setText(menu.menuName);
                break;
            case 3:
                activity.finish();
                break;
        }
    }
}
