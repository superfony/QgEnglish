package qge.cn.com.qgenglish.app.articel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

/**
 * 阅读训练列表
 */

public class ArticelMenuAct extends BaseActivity {
    private String TAG = "ArticelMenuAct";
    @Bind(R.id.articel_menu_lv)
    ListView articel_menu_lv;
    private ArticelMenuAdapter articelMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articelmenu_act);
        ButterKnife.bind(this);
        activity = this;
        initData();
        reqMenu();
    }
    private void initData() {

        articelMenuAdapter = new ArticelMenuAdapter(activity, menuArrayList);
        articel_menu_lv.setAdapter(articelMenuAdapter);
        articel_menu_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));

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
                articelMenuAdapter.updateListView(menuArrayList);
                break;
        }
    }
}
