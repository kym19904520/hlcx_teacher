package com.up.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.study.base.MyBaseAdapter;
import com.up.study.model.MyStudentAnalysisListBean;
import com.up.study.weight.tvhtml.RichText;
import com.up.teacher.R;

import java.util.List;


/**
 * Created by kym on 2017/7/20.
 */

public class SectionViewAdapter extends MyBaseAdapter<MyStudentAnalysisListBean.SubjectBean> {

    public SectionViewAdapter(Context context, List<MyStudentAnalysisListBean.SubjectBean> mDataSets) {
        super(context, mDataSets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_section, null);
            viewHolder.tv_topic = (RichText) convertView.findViewById(R.id.tv_topic);
            viewHolder.tv_error = (TextView) convertView.findViewById(R.id.tv_error);
            viewHolder.iv_difficulty = (ImageView) convertView.findViewById(R.id.iv_difficulty);
            viewHolder.iv_difficulty01 = (ImageView) convertView.findViewById(R.id.iv_difficulty01);
            viewHolder.iv_difficulty02 = (ImageView) convertView.findViewById(R.id.iv_difficulty02);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int difficulty = mDataSets.get(position).getDifficulty();
        switch (difficulty){
            case 1:
                viewHolder.iv_difficulty.setImageResource(R.mipmap.my_xx01);
                break;
            case 2:
                viewHolder.iv_difficulty.setImageResource(R.mipmap.my_xx01);
                viewHolder.iv_difficulty01.setImageResource(R.mipmap.my_xx01);
                break;
            case 3:
                viewHolder.iv_difficulty.setImageResource(R.mipmap.my_xx01);
                viewHolder.iv_difficulty01.setImageResource(R.mipmap.my_xx01);
                viewHolder.iv_difficulty02.setImageResource(R.mipmap.my_xx01);
                break;
        }
        viewHolder.tv_topic.setRichText(mDataSets.get(position).getName());
//        viewHolder.tv_topic.setText(mDataSets.get(position).getName());
        viewHolder.tv_error.setText(mDataSets.get(position).getPer() + "%");
        return convertView;
    }

    static class ViewHolder {
        RichText tv_topic;
        ImageView iv_difficulty;
        ImageView iv_difficulty01;
        ImageView iv_difficulty02;
        TextView tv_error;
    }
}
