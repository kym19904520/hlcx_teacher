package com.up.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.study.base.MyBaseAdapter;
import com.up.study.model.ClassListBean;
import com.up.teacher.R;

import java.util.List;


/**
 * 班级adapter
 * Created by kym on 2017/7/18.
 */

public class ClassAdapter extends MyBaseAdapter<ClassListBean> {

    public ClassAdapter(Context context, List<ClassListBean> mDataSets) {
        super(context, mDataSets);
    }

//    @Override
//    public boolean areAllItemsEnabled() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled(int position) {
//        if (mDataSets.get(position).getType() == 0){
//            return false;
//        }else {
//            return true;
//        }
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_class, null);
            viewHolder.iv_teacher_picture = (ImageView) convertView.findViewById(R.id.iv_teacher_picture);
            viewHolder.tv_class_teacher = (TextView) convertView.findViewById(R.id.tv_class_teacher);
            viewHolder.tv_class_name = (TextView) convertView.findViewById(R.id.tv_class_name);
            viewHolder.tv_class_another_name = (TextView) convertView.findViewById(R.id.tv_class_another_name);
            viewHolder.tv_class_code = (TextView) convertView.findViewById(R.id.tv_class_code);
            viewHolder.tv_class_number = (TextView) convertView.findViewById(R.id.tv_class_number);
            viewHolder.lv_gone = (ImageView) convertView.findViewById(R.id.lv_gone);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int type = mDataSets.get(position).getType();
        switch (type){
            case 0:
                viewHolder.tv_class_teacher.setText("普通");
                viewHolder.lv_gone.setVisibility(View.GONE);
                break;
            case 1:
                viewHolder.tv_class_teacher.setText("班主任");
                break;
        }
        viewHolder.tv_class_name.setText(mDataSets.get(position).getName());
        if (mDataSets.get(position).getAlias()!=null && !mDataSets.get(position).getAlias().trim().equals("")) {
            String string = mDataSets.get(position).getAlias();
            if (string.length() >=8) {
                viewHolder.tv_class_another_name.setText("(" + string.substring(0,6) + "...)");
            }else {
                viewHolder.tv_class_another_name.setText("(" + string + ")");
            }
        }else {
            viewHolder.tv_class_another_name.setText("");
        }
        viewHolder.tv_class_code.setText("邀请码：" + mDataSets.get(position).getInvite_code());
        viewHolder.tv_class_number.setText(mDataSets.get(position).getSnum() + "");
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_teacher_picture,lv_gone;
        TextView tv_class_teacher;
        TextView tv_class_name;
        TextView tv_class_another_name;
        TextView tv_class_code;
        TextView tv_class_number;
    }
}
