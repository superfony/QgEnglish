package qge.cn.com.qgenglish.app.articel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baiyang.android.util.basic.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;

/**
 * 阅读训练列表
 */

public class ArticelMenuActCopy extends BaseActivity {
    private String TAG = "ArticelMenuAct";
    @Bind(R.id.articel_menu_lv)
    ListView articel_menu_lv;
    private ArticelMenuAdapterCopy articelMenuAdapter;
    private List<ArticelMenuBean> articelMenuBeanList = new ArrayList<ArticelMenuBean>();
    private int articleTyp = 0; // 1 初中 2  高中


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articelmenu_act);
        ButterKnife.bind(this);
        activity = this;
        articleTyp = activity.getIntent().getIntExtra("articleType", 0);
        initData();
//        reqMenu();
    }

    private void initData() {
        if (articleTyp == 1) {
            for (int i = 1; i < 20; i++) {
                ArticelMenuBean articelMenuBean = new ArticelMenuBean();
                articelMenuBean.name = "初中阅读训练第" + i + "关";
                articelMenuBeanList.add(articelMenuBean);
            }
        } else if (articleTyp == 2) {
            for (int i = 1; i < 20; i++) {
                ArticelMenuBean articelMenuBean = new ArticelMenuBean();
                articelMenuBean.name = "高中阅读训练第" + i + "关";
                articelMenuBeanList.add(articelMenuBean);
            }
        }


        articelMenuAdapter = new ArticelMenuAdapterCopy(activity, articelMenuBeanList);
        articel_menu_lv.setAdapter(articelMenuAdapter);
        articel_menu_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("guanqi", position);// 默认从0开始  // 作为数据库中的起始主键进行查询
                intent.setClass(activity, ArticelAct.class);
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
                ToastHelper.toast(activity, msg.obj.toString());
                break;
            case 1:
                handlerBase.obtainMessage(1, "").sendToTarget();
                break;


        }
    }
}
