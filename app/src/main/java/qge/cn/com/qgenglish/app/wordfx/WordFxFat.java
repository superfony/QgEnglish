package qge.cn.com.qgenglish.app.wordfx;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import qge.cn.com.qgenglish.R;
import qge.cn.com.qgenglish.app.BaseFragment;
import qge.cn.com.qgenglish.app.Common;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointAdapter;
import qge.cn.com.qgenglish.app.word.wordmenu.CpointBean;

/**
 * 复习选关
 */
public class WordFxFat extends BaseFragment {

    @Bind(R.id.word_fx_lv)
    ListView wordFxLv;
    private View view;
    private Activity activity;
    private List<CpointBean> cpointBeanList = new ArrayList<CpointBean>();
    private CpointAdapter cpointAdapter;

    public WordFxFat() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fx_fragment_item_list, container,
                    false);
        } catch (Exception e) {
        }
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=this.getActivity();
        initUI();
        initData();
    }

    private void initUI() {


    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            CpointBean cpointBean = new CpointBean();
            cpointBean.name = "第" + i + "关";
            cpointBeanList.add(cpointBean);
        }
        cpointAdapter = new CpointAdapter(getActivity(), cpointBeanList);
        wordFxLv.setAdapter(cpointAdapter);

        wordFxLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Common.replaceRightFragment(activity, new WordFxInfoFat(),
//                        false, R.id.info_container, "wordinfo");
                Common.replaceRightFragment(activity, new WordFxInfoFat(),
                        false, R.id.info_container);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
