/*
 * @version 1.0
 * 分页类
*/

package qge.cn.com.qgenglish.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyang.android.adapter.ViewCreator;
import com.baiyang.android.http.common.RequestAction;
import com.baiyang.android.http.pagination.PageBean;
import com.baiyang.android.http.pagination.PagerResponse;

import java.util.List;

import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.word.SjWordAct;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.db.DBManager;

/**
 * @Title: 分页控件.
 */
public class PaginationWidget<T> {
    private static final String TAG = "PaginationWidget";
    /**
     */
    public static final String PAGESIZE_KEY = "pageSize";
    public static final String DATA_KEY = "pagination";
    /**
     * 分页控件所在Activity
     */
    protected Context context;
    protected View paginationView;
    /**
     * 分页按钮,首页,前一页,下一页,末页
     */
    protected Button btn_firstPage;
    protected ImageButton btn_prevPage, btn_nextPage, btn_lastPage;

    /**
     * 当前页,总页数,已查看记录数,总记录数
     */
    protected TextView txt_currentPage, txt_pageCount, txt_page_allCount;
    protected RequestAction requestAction = null;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private int current;
    private Handler handler;

    public void setCurrent(int current) {
        this.current = current;
    }




    //  分页显示指示器
    public void setPageIndicator(int allCount) {
        requestAction.pageBean.setAllCount(allCount);
        int pageCount = requestAction.pageBean.getPageCount();
        if (allCount < 1) {
            txt_currentPage.setText("0");
            txt_pageCount.setText("0");
        } else {
            txt_pageCount.setText("" + pageCount);
            txt_currentPage.setText(""
                    + requestAction.pageBean.getCurrentPage());
        }
        txt_page_allCount.setText("" + allCount);
        Log.d(TAG, "success," + requestAction);
        boolean enabled = true;
        if (allCount < 1) {
            enabled = false;
        }
        btn_firstPage.setEnabled(enabled);
        btn_prevPage.setEnabled(enabled);
        btn_nextPage.setEnabled(enabled);
        btn_lastPage.setEnabled(enabled);
    }

    public PaginationWidget() {
        if (requestAction == null) {
            requestAction = new RequestAction();
        }
    }

    public PaginationWidget<T> init(Context context, View container) {
        this.context = context;
        this.paginationView = container;
        setUpViews();
        bindEvents();
        return this;
    }

    private void setUpViews() {

        btn_firstPage = (Button) paginationView
                .findViewById(R.id.btn_firstPage);
        btn_prevPage = (ImageButton) paginationView.findViewById(R.id.btn_prevPage);
        btn_nextPage = (ImageButton) paginationView.findViewById(R.id.btn_nextPage);
        btn_lastPage = (ImageButton) paginationView.findViewById(R.id.btn_lastPage);
        txt_currentPage = (TextView) paginationView
                .findViewById(R.id.txt_currentPage);
        txt_pageCount = (TextView) paginationView
                .findViewById(R.id.txt_pageCount);
        txt_page_allCount = (TextView) paginationView
                .findViewById(R.id.txt_page_allCount);
    }

    private void bindEvents() {
        btn_firstPage.setOnClickListener(onClickListener);
        btn_prevPage.setOnClickListener(onClickListener);
        btn_nextPage.setOnClickListener(onClickListener);
        btn_lastPage.setOnClickListener(onClickListener);

    }


    private CompoundButton.OnClickListener onClickListener = new CompoundButton.OnClickListener() {

        @Override
        public void onClick(View v) {
            int vid = v.getId();
            if (vid == R.id.btn_firstPage) {
                loadFirstPage();
            } else if (vid == R.id.btn_prevPage) {
                loadPrevPage();
            } else if (vid == R.id.btn_nextPage) {
                loadNextPage();
            } else if (vid == R.id.btn_lastPage) {
                // loadLastPage();
                confusionWord(); // 单词混淆
            }
        }

    };

    // 首页
    private void loadFirstPage() {
        if (requestAction.pageBean.getCurrentPage() == 1) {
            showToast(R.string.isFirstPage);
            return;
        }


        requestAction.pageBean.setCurrentPage(1);

        handler.obtainMessage(100, requestAction.pageBean).sendToTarget();
    }

    // 上一页
    private void loadPrevPage() {
        if (requestAction.pageBean.getCurrentPage() == 1) {
            showToast(R.string.isFirstPage);
            return;
        }
        requestAction.pageBean.setCurrentPage(requestAction.pageBean
                .getCurrentPage() - 1);

        if (requestAction.pageBean.getCurrentPage() <= ((current - 1) * 2 + 3) && requestAction.pageBean.getCurrentPage() >= (current - 1) * 2 + 1) {
            handler.obtainMessage(100, requestAction.pageBean).sendToTarget();
        } else {
            requestAction.pageBean.setCurrentPage(requestAction.pageBean
                    .getCurrentPage() + 1);
        }


        handler.obtainMessage(100, requestAction.pageBean).sendToTarget();
    }

    // 下一页
    private void loadNextPage() {
        if (requestAction.pageBean.getCurrentPage() == requestAction.pageBean
                .getPageCount()) {
            showToast(R.string.isLastPage);
            handler.obtainMessage(102).sendToTarget();
            return;
        }

        requestAction.pageBean.setCurrentPage(requestAction.pageBean
                .getCurrentPage() + 1);

        if (requestAction.pageBean.getCurrentPage() <= ((current - 1) * 2 + 3) && requestAction.pageBean.getCurrentPage() >= (current - 1) * 2 + 1) {
            handler.obtainMessage(100, requestAction.pageBean).sendToTarget();
        } else if (requestAction.pageBean.getCurrentPage() == ((current - 1) * 2 + 4)) {
            requestAction.pageBean.setCurrentPage(requestAction.pageBean
                    .getCurrentPage() - 1);
            handler.obtainMessage(102).sendToTarget();
        } else {
            requestAction.pageBean.setCurrentPage(requestAction.pageBean
                    .getCurrentPage() - 1);
        }



    }

    // 最后一页
    private void loadLastPage() {
        if (requestAction.pageBean.getCurrentPage() == requestAction.pageBean
                .getPageCount()) {
            showToast(R.string.isLastPage);
            return;
        }
        requestAction.pageBean.setCurrentPage(requestAction.pageBean
                .getPageCount());
        handler.obtainMessage(100, requestAction.pageBean).sendToTarget();
    }

    // 混淆单词顺序
    private void confusionWord() {

        handler.obtainMessage(101).sendToTarget();
    }



    private void showToast(int message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public void setPageSize(int pageSize) {
        requestAction.pageBean.setPageSize(pageSize);
        requestAction.pageBean.setCurrentPage(1);
    }

    public PageBean getPageBean() {
        if (null == requestAction.pageBean) {
            requestAction.pageBean = new PageBean();
        }
        return requestAction.pageBean;
    }


    public RequestAction getRequestAction() {
        return requestAction;
    }


    protected String key;

    public void setKey(String key) {
        this.key = key;
    }


}
