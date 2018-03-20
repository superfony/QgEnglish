package qge.cn.com.qgenglish.app.sentence;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

/**
 * Created by fony on 2018/3/12.
 */

public class SpecialList extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    @Bind(R.id.score)
    TextView score;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    private SpecialAdapter specialAdapter;
    private ArrayList<SpecialBean> specialBeanArrayList = new ArrayList<SpecialBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_list);
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
        specialAdapter = new SpecialAdapter(activity, specialBeanArrayList);
        ckxzLv.setAdapter(specialAdapter);
        // 提交判断
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerBase.obtainMessage(2, "").sendToTarget();
            }
        });

    }

    // 处理返回的结果
    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        Gson gson = new Gson();
        Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<SpecialBean>>>() {
        }.getType());
        specialBeanArrayList = (ArrayList) result.getData();
        List<String> list = specialBeanArrayList.get(0).getItems();
        specialBeanArrayList.clear();
        for (int i = 0; i < 20; i++) {  // 测试
            SpecialBean specialBean = new SpecialBean();
            specialBean.setAnswer("A");
            specialBean.setItems(list);
            specialBean.setContent("sfwnfewjfowjefjwjfoijwjfj" + i);
            specialBeanArrayList.add(specialBean);
        }
        handlerBase.obtainMessage(1, "").sendToTarget();
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
                specialAdapter.updateListView(specialBeanArrayList);
                title.setText(menu.menuName);
                break;
            case 2:
                specialAdapter.setShow(true);

                specialAdapter.updateListView(specialBeanArrayList);
                score.setText(getScoreStr(specialBeanArrayList));
                break;
            case 3:
                activity.finish();
                break;
        }
    }

    private String getScoreStr(ArrayList<SpecialBean> specialBeanArrayList) {
        int m = 0, n = 0;
        for (int i = 0; i < specialBeanArrayList.size(); i++) {
            SpecialBean specialBean = specialBeanArrayList.get(i);
            if (specialBean.getIsornot() == 1) {
                m++;
            } else if (specialBean.getIsornot() == 2) {
                n++;
            }
        }
        return String.format("答对%s题,答错%s题", m, n).toString();

    }


}
