package qge.cn.com.qgenglish;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baiyang.android.http.basic.RequestParams;
import com.baiyang.android.http.common.AsyncBase;
import com.baiyang.android.util.basic.ToastHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.CheckAct;
import qge.cn.com.qgenglish.app.CheckAdapter;
import qge.cn.com.qgenglish.app.CheckJson;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.schoolinfo.SchoolInfo;
import qge.cn.com.qgenglish.app.word.table.Userinfo;
import qge.cn.com.qgenglish.application.AppContext;
import qge.cn.com.qgenglish.cache.CacheManager;

/**
 * 学生注册界面
 */

public class RegistActivity extends BaseActivity {
    protected String TAG = "RegistActivity";
    @Bind(R.id.username_et)
    EditText usernameEt;
    @Bind(R.id.phone_et)
    EditText phoneEt;
    @Bind(R.id.password_et)
    EditText passwordEt;
    @Bind(R.id.sure_pwd_et)
    EditText surePwdEt;
    @Bind(R.id.regist_btn)
    ImageView registBtn;
    @Bind(R.id.school_info_et)
    EditText schoolInfoEt;
    @Bind(R.id.grade_info_et)
    EditText gradeInfoEt;
    @Bind(R.id.zxs_et)
    EditText zxsEt;
    @Bind(R.id.area_et)
    EditText areaEt;
    private CheckJson checkJson;
    private boolean isZXS = false;
    private static int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgit);
        ButterKnife.bind(this);

        Gson gson = new Gson();
        String checkStr = getFileToStr("provincecity.txt");
        checkJson = gson.fromJson(checkStr, CheckJson.class);
    }

    private void init() {

    }

    @OnClick(R.id.zxs_et)
    public void zxs() {

        List<String> arrayList = new ArrayList<>();
        List<CheckJson.DataBean> dataBeanList = checkJson.getData();
        for (int i = 0; i < dataBeanList.size(); i++) {
            CheckJson.DataBean dataBean = dataBeanList.get(i);
            arrayList.add(dataBean.getProvinceName());
        }
        dialog(arrayList, 1);
    }

    @OnClick(R.id.area_et)
    public void setAreaEt() {
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

    @OnClick(R.id.grade_info_et)
    public void gradeInfo() {
        List<String> arrayList;
        String[] strings = activity.getResources().getStringArray(R.array.grade);
        arrayList = Arrays.asList(strings);
        dialog(arrayList, 0);

    }


    @OnClick(R.id.regist_btn)
    void regist() {
        String name = usernameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String pwd = passwordEt.getText().toString();
        String pwd_sure = surePwdEt.getText().toString();

        String school_name = schoolInfoEt.getText().toString();
        String city = zxsEt.getText().toString();
        String area = areaEt.getText().toString();
        String grade = gradeInfoEt.getText().toString();


        Pattern pattern = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])[0-9]{7}[1-9]");
        if (TextUtils.isEmpty(name)) {
            AppContext.showToast("请填写姓名");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            AppContext.showToast("请填写电话");
            return;
        }

        if (!pattern.matcher(phone).find()) {
            AppContext.showToast("请填写正确的手机号码");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            AppContext.showToast("密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(pwd_sure)) {
            AppContext.showToast("确认密码不能为空");
            return;
        }

        if (!pwd.equals(pwd_sure)) {
            AppContext.showToast("两次输入密码不一致");
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", name); // 姓名
        requestParams.put("password", pwd);// 密码
        requestParams.put("phoneNum", phone);// 手机号码
        requestParams.put("sname", name);
        requestParams.put("phone", phone); //手机号码
        requestParams.put("grade", grade);// 年级
        requestParams.put("parentPhone", phone); //手机号码
        requestParams.put("city", city); //城市
        requestParams.put("area", area);// 区域
        requestParams.put("address", area);// 地址
        requestParams.put("userImg", "");// 头像路径
        requestParams.put("school_name", school_name);//学校名字

        SchoolInfo schoolInfo = (SchoolInfo) CacheManager.readObject(activity, "schoolinfo");
        addHead("authorization", schoolInfo.getToken());
        startHttpPost(RequestUrls.studentRegist, requestParams);

    }


    @Override
    protected void handMessage(Message msg) {
        super.handMessage(msg);
        hidePD();
        switch (msg.what) {
            case 1:
                Intent intent = new Intent();
                intent.setClass(activity, StuLoginActivity.class);  // 注册成功后的跳转
                activity.startActivity(intent);
                break;
            case 0:
                AppContext.showToast(msg.toString());
                break;
        }
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
        handlerBase.obtainMessage(0, s).sendToTarget();
    }

    // 有数据返回的 注册接口的特殊
    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        // 解析注册成功的
        // 用于文件存储方式  方便获取用户信息

//        Gson gson = new Gson();
//        Result result = gson.fromJson(s, new TypeToken<Userinfo>() {
//        }.getType());
//        Userinfo userinfo = (Userinfo) result.getData();

        handlerBase.obtainMessage(1, "注册成功").sendToTarget();
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
                RegistActivity.position = position;

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
                gradeInfoEt.setText(value);
            } else if (msg.what == 1) {
                String value = msg.obj.toString();
                zxsEt.setText(value);
                isZXS = true;

            } else if (msg.what == 2) {
                String value = msg.obj.toString();
                areaEt.setText(value);
            }
        }
    };
}
