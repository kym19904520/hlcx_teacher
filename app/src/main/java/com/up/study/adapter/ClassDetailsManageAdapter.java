package com.up.study.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.teacher.R;


/**
 * Created by kym on 2017/7/19.
 */

public class ClassDetailsManageAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_student_ranking, null);
            viewHolder.tv_ranking = (TextView) convertView.findViewById(R.id.tv_ranking);
            viewHolder.tv_ranking01 = (TextView) convertView.findViewById(R.id.tv_ranking01);
            viewHolder.tv_student_name = (TextView) convertView.findViewById(R.id.tv_student_name);
            viewHolder.tv_student_number = (TextView) convertView.findViewById(R.id.tv_student_number);
            viewHolder.iv_student = (ImageView) convertView.findViewById(R.id.iv_student);
            viewHolder.iv_student_sex = (ImageView) convertView.findViewById(R.id.iv_student_sex);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        if (position == 0){
            setTextView(viewHolder);
        }
        if (position == 1){
            setTextView(viewHolder);
            viewHolder.tv_ranking.setBackgroundResource(R.mipmap.class_yj);
        }
        if (position == 2){
            setTextView(viewHolder);
            viewHolder.tv_ranking.setBackgroundResource(R.mipmap.class_jj);
        }
        viewHolder.tv_ranking01.setText((position + 1) + "");
        return convertView;
    }

    private void setTextView(ViewHolder viewHolder) {
        viewHolder.tv_ranking01.setVisibility(View.GONE);
        viewHolder.tv_ranking.setVisibility(View.VISIBLE);
    }

    static class ViewHolder {
        TextView tv_ranking;
        TextView tv_ranking01;
        ImageView iv_student;
        TextView tv_student_name;
        ImageView iv_student_sex;
        TextView tv_student_number;
    }
}
