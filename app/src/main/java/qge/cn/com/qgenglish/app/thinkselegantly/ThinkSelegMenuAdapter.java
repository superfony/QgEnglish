package qge.cn.com.qgenglish.app.thinkselegantly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import qge.cn.com.qgenglish.R;

public class ThinkSelegMenuAdapter extends BaseAdapter {
    private String[] menuArray = null;
    private Context mContext;

    public ThinkSelegMenuAdapter(Context mContext, String[] menuArray) {
        this.mContext = mContext;
        this.menuArray = menuArray;
    }

    public void updateListView(String[] menuArray) {
        this.menuArray = menuArray;
        notifyDataSetChanged();
    }

    public int getCount() {
        return menuArray.length;
    }

    public Object getItem(int position) {
        return menuArray[position];
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.threemenu_adapter, null);
            viewHolder.menuName = (TextView) view.findViewById(R.id.menu_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.menuName.setText(menuArray[position]);
        return view;
    }

    static class ViewHolder {
        TextView menuName;

    }
}