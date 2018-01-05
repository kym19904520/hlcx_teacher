package com.up.study.ui.my;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.up.common.widget.MyListView;
import com.up.study.base.MyBaseAdapter;
import com.up.study.model.MyStudentAnalysisListBean;
import com.up.teacher.R;

import java.util.List;

/**
 * 我的----学情分析-----知识点
 * Created by kym on 2017/7/20.
 */

public class KnowledgePointView extends LinearLayout {
    private MyListView lv_section_name;
    private TextView tv_knowledge_point_name,tv_knowledge_weakness,tv_no_data;
    private List<MyStudentAnalysisListBean.KnowBean> knowBeanList;
    private LinearLayout ll_no_class;

    public KnowledgePointView(Context context, List<MyStudentAnalysisListBean.KnowBean> knowBeanList) {
        super(context);
        this.knowBeanList = knowBeanList;
        initView();
    }

    public KnowledgePointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_section, this, true);
        tv_knowledge_weakness = (TextView) view.findViewById(R.id.tv_knowledge_weakness);
        tv_knowledge_point_name = (TextView) view.findViewById(R.id.tv_knowledge_point_name);
        lv_section_name = (MyListView) view.findViewById(R.id.lv_section_name);
        ll_no_class = (LinearLayout) view.findViewById(R.id.ll_no_class);
        tv_no_data = (TextView) view.findViewById(R.id.tv_no_data);
        initData();
    }

    private void initData() {
        tv_knowledge_weakness.setText(R.string.my_knowledge_point);
        tv_knowledge_point_name.setText(R.string.my_knowledge_name);
        if (knowBeanList == null || knowBeanList.size() <= 0){
            ll_no_class.setVisibility(VISIBLE);
            tv_no_data.setText("暂时没有知识点喔~");
        }else {
            ll_no_class.setVisibility(GONE);
        }
        lv_section_name.setAdapter(new KnowledgePointAdapter(getContext(), knowBeanList));
        lv_section_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KnowledgePointAnalyzeActivity.startKnowledgePointAnalyzeActivity(getContext(),knowBeanList.get(position).getKId());
            }
        });
    }

    private class KnowledgePointAdapter extends MyBaseAdapter<MyStudentAnalysisListBean.KnowBean> {

        public KnowledgePointAdapter(Context context, List<MyStudentAnalysisListBean.KnowBean> mDataSets) {
            super(context, mDataSets);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_student_analyze_details, null);
                viewHolder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
                viewHolder.tv_probability = (TextView) convertView.findViewById(R.id.tv_probability);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_data.setText(mDataSets.get(position).getName());
            viewHolder.tv_probability.setText(mDataSets.get(position).getPer() + "%");
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_data;
        TextView tv_probability;
    }
}
