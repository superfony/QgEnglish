package qge.cn.com.qgenglish.app.articel;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.word.EntryPopListener;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;

/**
 * 通关的pop
 */

public class ArticelPopWindow {
    private static final String TAG = ArticelPopWindow.class.getName();
    private Activity mActivity;
    private LayoutInflater layoutInflater;
    private PopupWindow popupWindow = null;
    private View popView;
    private ArticelPopListener listener;
    private final static int DIALOG = 1;

    public ArticelPopWindow(final Activity activity, final ArticelPopListener listener, int guanqi, int rightCount) {
        this.mActivity = activity;
        this.listener = listener;
        layoutInflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = LayoutInflater.from(mActivity).inflate(R.layout.articelpop, null);
        popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        ColorDrawable dw = new ColorDrawable(-00000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.UpInAndOutAnimation);
        popupWindow.update();
        TextView cpoincode_tv = (TextView) popView.findViewById(R.id.cpoincode);
        TextView goldcoin_tv = (TextView) popView.findViewById(R.id.goldcoin);
        cpoincode_tv.setText(rightCount + "");
        goldcoin_tv.setText("5");
        View query = popView.findViewById(R.id.query);
        query.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.doQuery("");
                popupWindow.dismiss();
            }

        });
    }


    public void show(View anchor) {// TODO 设置显示
        popupWindow.setHeight(dipToPixels(400));
        Rect frame = new Rect();
        mActivity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(frame);

        popupWindow.showAtLocation(anchor, Gravity.CENTER_HORIZONTAL
                | Gravity.TOP, 100, frame.top);

    }

    public int dipToPixels(int dip) {
        Resources r = mActivity.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics());
        return (int) px;
    }


}
