package qge.cn.com.qgenglish.app.word.wordmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import qge.cn.com.qgenglish.R;

public class CpointAdapter extends BaseAdapter {
    private List<CpointBean> cpointBeanList = null;
    private Context mContext;

    public CpointAdapter(Context mContext, List<CpointBean> cpointBeanList) {
        this.mContext = mContext;
        this.cpointBeanList = cpointBeanList;
    }

    public void updateListView(List<CpointBean> cpointBeanList) {
        this.cpointBeanList = cpointBeanList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return cpointBeanList.size();
    }

    public Object getItem(int position) {
        return cpointBeanList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        CpointBean cpointBean = cpointBeanList.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.cpoint_adapter, null);
            viewHolder.menuName = (TextView) view.findViewById(R.id.menu_name);
            viewHolder.cpointitem_bg = (RelativeLayout) view.findViewById(R.id.cpoint_item_bg);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.menuName.setText(cpointBean.name);

        if (cpointBean.state == 1) {
            viewHolder.cpointitem_bg.setBackgroundResource(R.mipmap.item_c);
        } else {
            viewHolder.cpointitem_bg.setBackgroundResource(R.drawable.item_bg);
        }
        return view;
    }

    static class ViewHolder {
        TextView menuName;
        RelativeLayout cpointitem_bg;
    }
}