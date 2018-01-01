package qge.cn.com.qgenglish.app.articel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;

/**
 * 阅读训练列表
 */

public class ArticelMenuAct extends BaseActivity {
    private String TAG = "ArticelMenuAct";
    @Bind(R.id.articel_menu_lv)
    ListView articel_menu_lv;
    private ArticelMenuAdapter articelMenuAdapter;
    private List<ArticelMenuBean> articelMenuBeanList = new ArrayList<ArticelMenuBean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articelmenu_act);
        ButterKnife.bind(this);
        activity = this;
        initData();
        articelMenuAdapter = new ArticelMenuAdapter(activity, articelMenuBeanList);
        articel_menu_lv.setAdapter(articelMenuAdapter);
        articel_menu_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("guanqi", position);// 默认从0开始
                intent.putExtra("count", 0);
                intent.setClass(activity, ArticelAct.class);
                activity.startActivity(intent);
            }
        });
    }

    private void initData() {
        for (int i = 1; i < 31; i++) {
            ArticelMenuBean articelMenuBean = new ArticelMenuBean();
            articelMenuBean.name = "初中阅读训练第" + i + "关";
            articelMenuBeanList.add(articelMenuBean);
        }

        for (int i = 1; i < 21; i++) {
            ArticelMenuBean articelMenuBean = new ArticelMenuBean();
            articelMenuBean.name = "高中阅读训练第" + i + "关";
            articelMenuBeanList.add(articelMenuBean);
        }
    }
}
