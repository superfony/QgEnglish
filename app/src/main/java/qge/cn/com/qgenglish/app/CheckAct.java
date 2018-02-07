package qge.cn.com.qgenglish.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.word.table.Userinfo;

/**
 * Created by fony on 2018/1/28.
 */

public class CheckAct extends BaseActivity {
    @Bind(R.id.check_name)
    EditText checkName;
    @Bind(R.id.check_grade)
    EditText checkGrade;
    @Bind(R.id.check_school)
    EditText checkSchool;
    @Bind(R.id.check_phone)
    EditText checkPhone;
    @Bind(R.id.check_zxs)
    EditText checkZxs;
    @Bind(R.id.check_djs)
    EditText checkDjs;
    @Bind(R.id.check_start)
    Button checkStart;
    private CheckJson checkJson;
    private boolean isZXS = false;
    private static int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_act);
        activity = this;
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String checkStr = getFileToStr("provincecity.txt");
        checkJson = gson.fromJson(checkStr, CheckJson.class);
        System.out.print(checkJson);
    }


    // 年级的
    @OnClick(R.id.check_grade)
    public void checkGrade() {
        List<String> arrayList;
        String[] strings = activity.getResources().getStringArray(R.array.grade);
        arrayList = Arrays.asList(strings);
        dialog(arrayList, 0);
    }

    @OnClick(R.id.check_zxs)
    public void checkZxs() {
        List<String> arrayList = new ArrayList<>();
        List<CheckJson.DataBean> dataBeanList = checkJson.getData();
        for (int i = 0; i < dataBeanList.size(); i++) {
            CheckJson.DataBean dataBean = dataBeanList.get(i);
            arrayList.add(dataBean.getProvinceName());
        }
        dialog(arrayList, 1);
    }

    @OnClick(R.id.check_djs)
    public void checkDjs() {
        if (!isZXS) {
            ToastHelper.toast(activity, "请先选择直辖市");
            return;
        }

        List<String> arrayList = new ArrayList<>();
        List<CheckJson.DataBean> dataBeanList = checkJson.getData();
        CheckJson.DataBean dataBean = dataBeanList.get(position);
        List<CheckJson.DataBean.CityBean> cityBeanList = dataBean.getCity();
        for (int i = 0; i < cityBeanList.size(); i++) {
            CheckJson.DataBean.CityBean cityBean = cityBeanList.get(i);
            arrayList.add(cityBean.getCityName());
        }
        dialog(arrayList, 2);

    }


    // 数据提交
    @OnClick(R.id.check_start)
    public void checkStart() {


        String name = checkName.getText().toString();
        String grade = checkGrade.getText().toString();
        String school = checkSchool.getText().toString();
        String phone = checkPhone.getText().toString();
        String zxs = checkZxs.getText().toString();
        String djs = checkDjs.getText().toString();


        Userinfo userinfo = new Userinfo();
        userinfo.city = zxs;
        userinfo.grade = grade;
        userinfo.area = djs;
        userinfo.user_mobile = phone;
        userinfo.username = name;
        userinfo.school = school;


        if (TextUtils.isEmpty(name)) {
            ToastHelper.toast(activity, "姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(grade)) {
            ToastHelper.toast(activity, "年级不能为空");
            return;
        }
        if (TextUtils.isEmpty(school)) {
            ToastHelper.toast(activity, "学校不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastHelper.toast(activity, "手机号不能为空");
            return;
        }


        Intent intent = new Intent();
        intent.putExtra("userinfo", userinfo);
        intent.setClass(activity, CheckChooseAct.class);
        activity.startActivity(intent);
        finish();
    }


    Dialog dialog;
    private ListView checkList;
    private CheckAdapter checkAdapter;

    private void dialog(List<String> arrayList, final int type) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.check_dialog, null);
        dialog = new AlertDialog.Builder(activity).create();
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        checkList = (ListView) layout.findViewById(R.id.check_list);
        checkAdapter = new CheckAdapter(activity, arrayList);
        checkList.setAdapter(checkAdapter);
        checkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) checkAdapter.getItem(position);
                CheckAct.position = position;

                osHandler.obtainMessage(type, value).sendToTarget();
                dialog.cancel();
            }
        });
    }

    Handler osHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String value = msg.obj.toString();
                checkGrade.setText(value);
            } else if (msg.what == 1) {
                String value = msg.obj.toString();
                checkZxs.setText(value);
                isZXS = true;

            } else if (msg.what == 2) {
                String value = msg.obj.toString();
                checkDjs.setText(value);
            }
        }
    };


}
