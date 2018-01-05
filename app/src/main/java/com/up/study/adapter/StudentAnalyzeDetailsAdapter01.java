package com.up.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.up.study.base.MyBaseAdapter;
import com.up.study.model.MyStudentDataBean;
import com.up.teacher.R;

import java.util.List;

/**
 * Created by kym on 2017/7/20.
 */

public class StudentAnalyzeDetailsAdapter01 extends MyBaseAdapter<MyStudentDataBean.StructureBean> {

    public StudentAnalyzeDetailsAdapter01(Context context, List<MyStudentDataBean.StructureBean> mDataSets) {
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
        viewHolder.tv_probability.setText(mDataSets.get(position).getPer()+"%");
        return convertView;
    }

    static class ViewHolder {
        TextView tv_data;
        TextView tv_probability;
    }
}
