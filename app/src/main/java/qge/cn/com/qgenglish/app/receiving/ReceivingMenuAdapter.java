package qge.cn.com.qgenglish.app.receiving;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

public class ReceivingMenuAdapter extends BaseAdapter {
    private ArrayList<Menu> menuArray = null;
    private Context mContext;

    public ReceivingMenuAdapter(Context mContext, ArrayList<Menu> menuArray) {
        this.mContext = mContext;
        this.menuArray = menuArray;
    }

    public void updateListView(ArrayList<Menu> menuArray) {
        this.menuArray = menuArray;
        notifyDataSetChanged();
    }

    public int getCount() {
        return menuArray.size();
    }

    public Object getItem(int position) {
        return menuArray.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        Menu menu = menuArray.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.threemenu_adapter, null);
            viewHolder.menuName = (TextView) view.findViewById(R.id.menu_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.menuName.setText(menu.menuName);
        return view;
    }

    static class ViewHolder {
        TextView menuName;

    }
}