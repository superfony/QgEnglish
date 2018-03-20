package qge.cn.com.qgenglish.app.sentence;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.fourlevel.Menu;

/**
 *
 */

public class SpecialAdapter extends BaseAdapter {
    private ArrayList<SpecialBean> menuArrayList = null;
    private Context mContext;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private boolean isShow = false;

    public SpecialAdapter(Context mContext, ArrayList<SpecialBean> menuArrayList) {
        this.mContext = mContext;
        this.menuArrayList = menuArrayList;
    }

    public void updateListView(ArrayList<SpecialBean> menuArrayList) {
        this.menuArrayList = menuArrayList;
        notifyDataSetChanged();
    }

    public int getCount() {
        return menuArrayList.size(); //
    }

    public Object getItem(int position) {
        return menuArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        final SpecialBean specialBean = menuArrayList.get(position);
//        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.special_adapter, null);
            viewHolder = new ViewHolder(view);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
        viewHolder.question1.setText(specialBean.getContent());
        viewHolder.num.setText((position + 1) + "、");
        List<String> list = specialBean.getItems();
        if (list != null && list.size() == 4) {
            viewHolder.radioBtnA1.setText("A、" + specialBean.getItems().get(0));
            viewHolder.radioBtnB1.setText("B、" + specialBean.getItems().get(1));
            viewHolder.radioBtnC1.setText("C、" + specialBean.getItems().get(2));
            viewHolder.radioBtnD1.setText("D、" + specialBean.getItems().get(3));
        }
        if (TextUtils.isEmpty(specialBean.getMyanswer())) {
            viewHolder.radioGroup1.clearCheck();
        } else {
            switch (specialBean.getMyanswer()) {
                case "A":
                    viewHolder.radioBtnA1.setChecked(true);

                    break;
                case "B":
                    viewHolder.radioBtnB1.setChecked(true);
                    break;
                case "C":
                    viewHolder.radioBtnC1.setChecked(true);
                    break;
                case "D":
                    viewHolder.radioBtnD1.setChecked(true);
                    break;
                default:
                    break;
            }
        }

        if (isShow) {
            if (specialBean.getIsornot() == 1) {
                viewHolder.question1Result.setVisibility(View.VISIBLE);
                viewHolder.question1Result.setBackgroundResource(R.mipmap.right);
            } else if (specialBean.getIsornot() == 2) {
                viewHolder.question1Result.setVisibility(View.VISIBLE);
                viewHolder.question1Result.setBackgroundResource(R.mipmap.wrong);
            } else {
                viewHolder.question1Result.setVisibility(View.INVISIBLE);
            }
        }


        viewHolder.radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 对比答案 改变isornot的值 ,提交的时候 显示正确答案
                // answerValue(checkedId);
                switch (checkedId) {
                    case R.id.radio_btn_A1:
                        specialBean.setMyanswer("A");
                        break;
                    case R.id.radio_btn_B1:
                        specialBean.setMyanswer("B");
                        break;
                    case R.id.radio_btn_C1:
                        specialBean.setMyanswer("C");
                        break;
                    case R.id.radio_btn_D1:
                        specialBean.setMyanswer("D");
                        break;
                }

                if (specialBean.getAnswer().equals(specialBean.getMyanswer())) {
                    specialBean.setIsornot(1); // 正确
                } else {
                    specialBean.setIsornot(2); // 错误
                }
            }
        });

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.num)
        TextView num;
        @Bind(R.id.question1)
        TextView question1;
        @Bind(R.id.question1_result)
        ImageView question1Result;
        @Bind(R.id.radio_btn_A1)
        RadioButton radioBtnA1;
        @Bind(R.id.radio_btn_B1)
        RadioButton radioBtnB1;
        @Bind(R.id.radio_btn_C1)
        RadioButton radioBtnC1;
        @Bind(R.id.radio_btn_D1)
        RadioButton radioBtnD1;
        @Bind(R.id.radioGroup1)
        RadioGroup radioGroup1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}