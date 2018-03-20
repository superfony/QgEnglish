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
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.fourlevel.Menu;
import qge.cn.com.qgenglish.app.hearing.HearingAct;
import qge.cn.com.qgenglish.application.FonyApplication;

/**
 * Created by fony on 2018/2/28.
 * mp3 文件发音 列表
 * 接收一个menu
 */

public class HearingList extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private HearAdapter hearAdapter;
    protected ArrayList<HearBean> hearBeanArrayList = new ArrayList<HearBean>(); //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sentence_act);
        ButterKnife.bind(this);
        activity = this;
        initData();
        reqData();
    }


    private void reqData() {
        Intent intent = activity.getIntent();
        menu = (Menu) intent.getSerializableExtra("menu");
        title.setText(menu.menuName);
        String url = String.format(RequestUrls.CONTENTURL, menu.id).toString();
        startHttpGet(url, null);
    }

    private void initData() {
        //title.setText("");//
        hearAdapter = new HearAdapter(activity, hearBeanArrayList);
        ckxzLv.setAdapter(hearAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                HearBean hearBean = hearBeanArrayList.get(position);
                intent.putExtra("hearBean", hearBean);
                intent.setClass(activity, HearingAct.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<HearBean>>>() {
        }.getType());
        hearBeanArrayList = (ArrayList) result.getData();
        handlerBase.sendEmptyMessage(1);

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
                hearAdapter.updateListView(hearBeanArrayList);
                title.setText(menu.menuName);
                break;
            case 3:
                activity.finish();
                break;
        }
    }
}
