package qge.cn.com.qgenglish.app.sentence;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.RequestUrls;
import qge.cn.com.qgenglish.app.BaseActivity;
import qge.cn.com.qgenglish.app.Result;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

/**
 * Created by fony on 2018/3/1.
 * webview 形式显示富文本
 * 第一步,根据menu 先获取当前文本的url ;
 * 第二步,根据返回的url加载显示富文本内容
 */

public class RichTextAct extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rich_text_wb)
    WebView richTextWb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rich_text_act);
        ButterKnife.bind(this);
        activity = this;
        //  initData();
        initWebViewSettings(richTextWb);

        richTextWb.loadData("1234567890sdfghjkl;sdfghjkl;wertyuiopsdfghjkl" +
                "wsdfghjkasdfghjklasdfghjklfghj" +
                "asdfghjklsdfghjkdfghj" +
                "dfghjkfghjkfghj" +
                "nihao你哈 覅我佛IE发奇偶微积分 佛文件附件饿哦我发" +
                "金佛文件of就王府井wjfoiwj ojwfojoefjowj fjwojfowj我owjfojw分接哦我就否" +
                "-;'qdfghjk", "text/html; charset=UTF-8", null);
    }

    //
    private void initData() {
        Intent intent = activity.getIntent();
        menu = (Menu) intent.getSerializableExtra("menu");
        title.setText(menu.menuName);

        String url = String.format(RequestUrls.COMMONURL, menu.id).toString();
        startHttpGet(url, null);
    }

    @Override
    protected void onFailureBase(Throwable throwable, String s) {
        super.onFailureBase(throwable, s);
    }

    @Override
    protected void onSuccessBase(String s) {
        super.onSuccessBase(s);
        // 获取 富文本url
        Gson gson = new Gson();
        // Result result = gson.fromJson(s, new TypeToken<Result<ArrayList<Word_niujinban_7_1>>>() {
        // Word_niujinban_7_1}.getType());
        // ArrayList arrayList = (ArrayList) result.getData();
        String url = "";
        richHandler.obtainMessage(1, url).sendToTarget();
    }

    Handler richHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 0:
                    // web显示数据

                    String url = msg.obj.toString();//
                    richTextWb.loadUrl(url);
                    //richTextWb.loadData();
                    break;
                case 1:
                    break;

            }
        }
    };


    public static void initWebViewSettings(WebView mWebView) {
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        // 触觉反馈，暂时没发现用处在哪里
        mWebView.setHapticFeedbackEnabled(false);
        WebSettings settings = mWebView.getSettings();
        // 支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        // 允许js交互
        settings.setJavaScriptEnabled(true);
        // 设置WebView是否可以由 JavaScript 自动打开窗口，默认为 false
        // 通常与 JavaScript 的 window.open() 配合使用。
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 允许中文编码
        settings.setDefaultTextEncodingName("UTF-8");
        // 使用大视图，设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 支持多窗口
        settings.setSupportMultipleWindows(true);
        // 隐藏自带缩放按钮
        settings.setBuiltInZoomControls(false);
        // 支持缩放
        settings.setSupportZoom(true);
        // 设置可访问文件
        settings.setAllowFileAccess(true);
        // 当WebView调用requestFocus时为WebView设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 指定WebView的页面布局显示形式，调用该方法会引起页面重绘。
        // NORMAL,SINGLE_COLUMN 过时, NARROW_COLUMNS 过时 ,TEXT_AUTOSIZING
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 从Lollipop(5.0)开始WebView默认不允许混合模式，https当中不能加载http资源，需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
}
