package qge.cn.com.qgenglish.app.word.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.ChooseWordListion;
import qge.cn.com.qgenglish.app.experience.WordBeanOlds;
import qge.cn.com.qgenglish.app.word.check.JcChoseWordAct;

public class FxWordAdapter extends BaseAdapter {
    private List<WordBeanOlds> wordBeanOldLists = null;
    private Context mContext;
    public int chooseNum = 0;

    public void setChooseWordListion(ChooseWordListion chooseWordListion) {
        this.chooseWordListion = chooseWordListion;
    }

    private ChooseWordListion chooseWordListion;

    public FxWordAdapter(Context mContext, List<WordBeanOlds> wordBeanOldLists) {
        this.mContext = mContext;
        this.wordBeanOldLists = wordBeanOldLists;
    }

    public void updateListView(List<WordBeanOlds> wordBeanOldLists) {
        this.wordBeanOldLists = wordBeanOldLists;
        notifyDataSetChanged();
    }

    public int getCount() {
        return wordBeanOldLists.size();
    }

    public Object getItem(int position) {
        return wordBeanOldLists.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        final WordBeanOlds wordBeanOlds = wordBeanOldLists.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fx_adapter, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.word_value.setText(wordBeanOlds.wordBeanOld.english);
        viewHolder.phonetic.setText(wordBeanOlds.wordBeanOld.phonetic);
        viewHolder.sense.setText(wordBeanOlds.wordBeanOld.sense);
        if (wordBeanOlds.wordBeanOld1 != null) {
            viewHolder.word_value1.setText(wordBeanOlds.wordBeanOld1.english);
            viewHolder.phonetic1.setText(wordBeanOlds.wordBeanOld1.phonetic);
            viewHolder.sense1.setText(wordBeanOlds.wordBeanOld1.sense);
        }
        viewHolder.expCbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wordBeanOlds.state = true;
                    chooseNum++;
                } else {
                    wordBeanOlds.state = false;
                    chooseNum--;
                }
                chooseWordListion.chooseCount();
            }
        });

        viewHolder.expCbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wordBeanOlds.state1 = true;
                    chooseNum++;
                } else {
                    wordBeanOlds.state1 = false;
                    chooseNum--;
                }

                chooseWordListion.chooseCount();

            }
        });

        viewHolder.exp_rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordBeanOlds.isShow) {
                    wordBeanOlds.isShow = false;
                } else {
                    wordBeanOlds.isShow = true;
                }
                chooseWordListion.switchChose(position, v, wordBeanOlds.isShow);
            }
        });
        viewHolder.exp_rela1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordBeanOlds.isShow1) {
                    wordBeanOlds.isShow1 = false;
                } else {
                    wordBeanOlds.isShow1 = true;
                }
                chooseWordListion.switchChose1(position, v, wordBeanOlds.isShow1);
            }
        });
        viewHolder.expCbox.setChecked(wordBeanOlds.state);
        viewHolder.expCbox1.setChecked(wordBeanOlds.state1);


        return view;
    }

    static class ViewHolder {
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
        @Bind(R.id.interpretation_lay1)
        LinearLayout interpretationLay1;
        @Bind(R.id.exp_cbox)
        CheckBox expCbox;
        @Bind(R.id.exp_cbox1)
        CheckBox expCbox1;
        @Bind(R.id.exp_rela)
        RelativeLayout exp_rela;
        @Bind(R.id.exp_rela1)
        RelativeLayout exp_rela1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}