package qge.cn.com.qgenglish.app.word;

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

public class SjwordAdapter extends BaseAdapter {
    private List<Word_niujinban_7_1> wordBeanOldList = null;
    private Context mContext;

    public SjwordAdapter(Context mContext, List<Word_niujinban_7_1> wordBeanOldList) {
        this.mContext = mContext;
        this.wordBeanOldList = wordBeanOldList;
    }

    public void updateListView(List<Word_niujinban_7_1> wordBeanOldList) {
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
        Word_niujinban_7_1 wordBeanOld = wordBeanOldList.get(position);
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
        viewHolder.word_value1.setText(wordBeanOld.english);
        viewHolder.phonetic1.setText(wordBeanOld.phonetic);
        viewHolder.sense1.setText(wordBeanOld.sense);
        viewHolder.interpretationLay.setVisibility(View.INVISIBLE);
        viewHolder.interpretationLay1.setVisibility(View.INVISIBLE);

        if (wordBeanOld.english.length() > 20) {
            viewHolder.fristlay.setVisibility(View.GONE);
            viewHolder.fristlay1.setVisibility(View.VISIBLE);
        } else {
            viewHolder.fristlay.setVisibility(View.VISIBLE);
            viewHolder.fristlay1.setVisibility(View.GONE);
        }
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.frist_lay)
        LinearLayout fristlay;
        @Bind(R.id.word_value)
        TextView word_value;
        @Bind(R.id.phonetic)
        TextView phonetic;
        @Bind(R.id.sense)
        TextView sense;
        @Bind(R.id.interpretation_lay)
        LinearLayout interpretationLay;
        @Bind(R.id.word_value1)
        TextView word_value1;
        @Bind(R.id.phonetic1)
        TextView phonetic1;
        @Bind(R.id.sense1)
        TextView sense1;
        @Bind(R.id.frist_lay1)
        LinearLayout fristlay1;
        @Bind(R.id.interpretation_lay1)
        LinearLayout interpretationLay1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}