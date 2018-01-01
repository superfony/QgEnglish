package qge.cn.com.qgenglish.app.word.check;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;

public class JcCpointAdapter extends BaseAdapter {
    private List<CpointBean> cpointBeanList = null;
    private Context mContext;

    public JcCpointAdapter(Context mContext, List<CpointBean> cpointBeanList) {
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
        final CpointBean cpointBean = cpointBeanList.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.jc_cpoint_adapter, null);
            viewHolder.menuName = (TextView) view.findViewById(R.id.menu_name);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.jc_cb);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.menuName.setText(cpointBean.name);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    cpointBean.isChecked = true;
                } else {
                    cpointBean.isChecked = false;
                }
            }
        });
        return view;
    }

    static class ViewHolder {
        TextView menuName;
        CheckBox checkBox;
    }
}