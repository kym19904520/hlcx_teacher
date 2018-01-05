package com.up.study.ui.my;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.up.common.widget.MyListView;
import com.up.study.base.MyBaseAdapter;
import com.up.study.model.MyStudentAnalysisListBean;
import com.up.teacher.R;

import java.util.List;

/**
 * 我的---学情分析-----章节
 * Created by kym on 2017/7/20.
 */

public class SectionView extends LinearLayout{

    private MyListView lv_section_name;
    List<MyStudentAnalysisListBean.StructureBean> structureBeanList;
    private TextView tv_no_data;
    private LinearLayout ll_no_class;

    public SectionView(Context context, List<MyStudentAnalysisListBean.StructureBean> structureBeanList) {
        super(context);
        this.structureBeanList = structureBeanList;
        initView();
    }

    public SectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.view_section,this,true);
        lv_section_name = (MyListView) view.findViewById(R.id.lv_section_name);
        ll_no_class = (LinearLayout) view.findViewById(R.id.ll_no_class);
        tv_no_data = (TextView) view.findViewById(R.id.tv_no_data);
        initData();
    }

    private void initData() {
        if (structureBeanList == null || structureBeanList.size() <= 0){
            ll_no_class.setVisibility(VISIBLE);
            tv_no_data.setText("暂时没有章节喔~");
        }else {
            ll_no_class.setVisibility(GONE);
        }
        SectionAdapter sectionAdapter = new SectionAdapter(getContext(),structureBeanList);
        lv_section_name.setAdapter(sectionAdapter);
    }

    private class SectionAdapter extends MyBaseAdapter<MyStudentAnalysisListBean.StructureBean> {


        public SectionAdapter(Context context, List<MyStudentAnalysisListBean.StructureBean> mDataSets) {
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
