package qge.cn.com.qgenglish.app.articel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

public class ArticelMenuAdapter extends BaseAdapter {
    private List<Menu> articelMenuBeanList = null;
    private Context mContext;

    public ArticelMenuAdapter(Context mContext, List<Menu> articelMenuBeanList) {
        this.mContext = mContext;
        this.articelMenuBeanList = articelMenuBeanList;
    }

    public void updateListView(List<Menu> articelMenuBeanList) {
        this.articelMenuBeanList = articelMenuBeanList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return articelMenuBeanList.size();
    }

    public Object getItem(int position) {
        return articelMenuBeanList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        Menu articelMenuBean = articelMenuBeanList.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.articelmenu_adapter, null);
            viewHolder.menuName = (TextView) view.findViewById(R.id.menu_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.menuName.setText(articelMenuBean.menuName);
        return view;
    }

    static class ViewHolder {
        TextView menuName;
    }
}