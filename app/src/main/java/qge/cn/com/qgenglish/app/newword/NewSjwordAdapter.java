package qge.cn.com.qgenglish.app.newword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.word.table.Word_niujinban_7_1;
import qge.cn.com.qgenglish.app.word.table.Word_unskilled;

public class NewSjwordAdapter extends BaseAdapter {
    private List<Word_unskilled> wordBeanOldList = null;
    private Context mContext;

    public NewSjwordAdapter(Context mContext, List<Word_unskilled> wordBeanOldList) {
        this.mContext = mContext;
        this.wordBeanOldList = wordBeanOldList;
    }

    public void updateListView(List<Word_unskilled> wordBeanOldList) {
        this.wordBeanOldList = wordBeanOldList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return wordBeanOldList.size();
    }

    public Object getItem(int position) {
        return wordBeanOldList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        Word_unskilled wordBeanOld = wordBeanOldList.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.sj_adapter, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.word_value.setText(wordBeanOld.english);
        viewHolder.phonetic.setText(wordBeanOld.phonetic);
        viewHolder.sense.setText(wordBeanOld.sense);
        viewHolder.interpretationLay.setVisibility(View.INVISIBLE);
        return view;
    }


    class ViewHolder {
        @Bind(R.id.word_value)
        TextView word_value;
        @Bind(R.id.phonetic)
        TextView phonetic;
        @Bind(R.id.sense)
        TextView sense;
        @Bind(R.id.interpretation_lay)
        LinearLayout interpretationLay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}