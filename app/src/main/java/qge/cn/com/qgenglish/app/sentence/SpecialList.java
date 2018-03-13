package qge.cn.com.qgenglish.app.sentence;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.articel.question.ArticelBean;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.hearing.HearAndLisAct;
import qge.cn.com.qgenglish.application.FonyApplication;
import qge.cn.com.qgenglish.cache.CacheManager;

/**
 * Created by fony on 2018/3/12.
 */

public class SpecialList extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private SpecialAdapter specialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.special_list);
        ButterKnife.bind(this);
        activity = this;
        initData();
        // reqMenu();
    }

    private void initData() {
        specialAdapter = new SpecialAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(specialAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    // 处理返回的结果
    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<ArticelBean>>>() {
        }.getType());
        // articelBeanList = (ArrayList) result.getData();
        handlerBase.obtainMessage(1, "").sendToTarget();
        CacheManager.saveObject(activity, result, menu.id);
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
                specialAdapter.updateListView(menuArrayList);
                title.setText(menu.menuName);
                break;
            case 3:
                activity.finish();
                break;
        }
    }
}
