package qge.cn.com.qgenglish.app.sentence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

/**
 *
 */

public class SpecialAdapter extends BaseAdapter {
    private ArrayList<Menu> menuArrayList = null;
    private Context mContext;

    public SpecialAdapter(Context mContext, ArrayList<Menu> menuArrayList) {
        this.mContext = mContext;
        this.menuArrayList = menuArrayList;
    }

    public void updateListView(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return 20; //
    }

    public Object getItem(int position) {
        return menuArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        // Menu menu = menuArrayList.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.special_adapter, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.question1)
        TextView question1;
        @Bind(R.id.question1_result)
        ImageView question1Result;
        @Bind(R.id.radio_btn_A1)
        RadioButton radioBtnA1;
        @Bind(R.id.radio_btn_B1)
        RadioButton radioBtnB1;
        @Bind(R.id.radio_btn_C1)
        RadioButton radioBtnC1;
        @Bind(R.id.radio_btn_D1)
        RadioButton radioBtnD1;
        @Bind(R.id.radioGroup1)
        RadioGroup radioGroup1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}