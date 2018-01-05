package com.up.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.study.base.MyBaseAdapter;
import com.up.study.model.MyStudentListBean;
import com.up.teacher.R;

import java.util.List;


/**
 * Created by kym on 2017/7/20.
 */

public class StudentAnalyzeAdapter extends MyBaseAdapter<MyStudentListBean> {
    private int type;

    public StudentAnalyzeAdapter(Context context, List<MyStudentListBean> mDataSets, int type) {
        super(context, mDataSets);
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_student_analyze, null);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            viewHolder.tv_error_rate = (TextView) convertView.findViewById(R.id.tv_error_rate);
            viewHolder.tv_student_name = (TextView) convertView.findViewById(R.id.tv_student_name);
            viewHolder.tv_student_id = (TextView) convertView.findViewById(R.id.tv_student_id);
            viewHolder.iv_student_preview = (ImageView) convertView.findViewById(R.id.iv_student_preview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (type == 1) {
            viewHolder.tv_student_id.setVisibility(View.GONE);
            viewHolder.tv_number.setText((position + 1) + "");
            viewHolder.tv_student_name.setText(mDataSets.get(position).getName());
            viewHolder.tv_error_rate.setText(mDataSets.get(position).getPer() + "%");
        } else {
            viewHolder.tv_number.setText((position + 1) + "");
            viewHolder.tv_student_name.setText(mDataSets.get(position).getName());
            viewHolder.tv_student_id.setText(mDataSets.get(position).getCode());
            viewHolder.tv_error_rate.setText(mDataSets.get(position).getPer() + "%");
        }
        return convertView;
    }

    public void clearList(){
        mDataSets.clear();
    }

    static class ViewHolder {
        TextView tv_error_rate;
        TextView tv_number;
        ImageView iv_student_preview;
        TextView tv_student_name;
        TextView tv_student_id;
    }
}
