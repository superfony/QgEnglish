package qge.cn.com.qgenglish.app.receiving;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.util.basic.ToastHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.highschool.HighMenuAdapter;
import qge.cn.com.qgenglish.app.newword.NewWordChoseAct;
import qge.cn.com.qgenglish.app.word.WordAct;
import qge.cn.com.qgenglish.app.word.WordMenuFAct;
import qge.cn.com.qgenglish.application.FonyApplication;

/**
 * 托福
 */

public class ReceivingMenu extends BaseActivity {
    private String TAG = "ReceivingMenu";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ckxz_lv)
    ListView ckxzLv;
    private ReceivingMenuAdapter wordMenuThAdapter;

    private String[] menuArr = {"托福词汇", "我的生词本"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ele_menu);
        ButterKnife.bind(this);
        activity = this;
        initData();
        reqMenu();

    }

    private void initData() {

        title.setText("托福课程");
        wordMenuThAdapter = new ReceivingMenuAdapter(activity, menuArrayList);
        ckxzLv.setAdapter(wordMenuThAdapter);
        ckxzLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("menu", menuArrayList.get(position));
                if (position == 0) {
                    ((FonyApplication) activity.getApplication()).qgtype = FonyApplication.QGTYPE.WORD;
                    intent.setClass(activity, ReceivingWordMenu.class);
                } else if (position == 1) {
                    intent.setClass(activity, NewWordChoseAct.class);
                }
                activity.startActivity(intent);

            }
        });
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        resultMenu(s);
    }

    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        switch (msg.what) {
            case 0:
                ToastHelper.toast(activity, msg.obj.toString());
                break;
            case 1:
                wordMenuThAdapter.updateListView(menuArrayList);
                break;
            case 3:
                activity.finish();
                break;


        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            RequestParams requestParams = new RequestParams();
            stulogoutdialog(requestParams);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
