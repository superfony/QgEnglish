package qge.cn.com.qgenglish.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qge.cn.com.qgenglish.R;

/**
 * 创库查询列表
 *
 * @author fony
 */

public class CheckAdapter extends BaseAdapter {
    private List<String> list = null;
    private Context mContext;

    public CheckAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void updateListView(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final String value = list.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.check_list_item,
                    null);

            viewHolder.checkValue = (TextView) view.findViewById(R.id.value);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.checkValue.setText(value);

        return view;
    }

    final static class ViewHolder {
        TextView checkValue;

    }
}