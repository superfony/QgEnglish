package qge.cn.com.qgenglish.app.sentence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

public class HearAdapter extends BaseAdapter {
    private ArrayList<HearBean> menuArrayList = null;
    private Context mContext;

    public HearAdapter(Context mContext, ArrayList<HearBean> menuArrayList) {
        this.mContext = mContext;
        this.menuArrayList = menuArrayList;
    }

    public void updateListView(ArrayList<HearBean> menuArrayList) {
        this.menuArrayList = menuArrayList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return menuArrayList.size();
    }

    public Object getItem(int position) {
        return menuArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        HearBean menu = menuArrayList.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.sentence_adapter, null);
            viewHolder.menuName = (TextView) view.findViewById(R.id.menu_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.menuName.setText(menu.getDisplayName());
        return view;
    }

    static class ViewHolder {
        TextView menuName;

    }
}