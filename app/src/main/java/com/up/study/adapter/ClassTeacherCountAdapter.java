package com.up.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.up.common.J;
import com.up.study.model.ClassTeacherDataBean;
import com.up.teacher.R;

import java.util.List;

/**
 * Created by kym on 2017/7/19.
 */

public class ClassTeacherCountAdapter extends RecyclerView.Adapter<ClassTeacherCountAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<ClassTeacherDataBean> listData;
    private Context context;
    private int count;

    public ClassTeacherCountAdapter(Context context, List<ClassTeacherDataBean> listData) {
        mInflater = LayoutInflater.from(context);
        this.listData = listData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_teacher_number, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 设置值
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_teacher_name.setText(listData.get(position).getName());
        if (listData.get(position).getHead() != null) {
            J.image().loadImage(context, listData.get(position).getHead(), holder.iv_teacher_picture);
        }
        String[] array = listData.get(position).getCoursename().split("\\+");
        if (listData.get(position).getType() == 1) {
            holder.tv_class_teacher.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < array.length; i++) {
                if (array[0].equals("数学")) {
                    holder.tv_math.setVisibility(View.VISIBLE);
                } else if (array[0].equals("语文")) {
                    holder.tv_language.setVisibility(View.VISIBLE);
                } else if (array[0].equals("英语")) {
                    holder.tv_english.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_class_teacher.setText(array[0]);
                }
            }
        }
    }

    public int getList(){
        return count;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_teacher_picture;
        TextView tv_class_teacher;
        TextView tv_teacher_name;
        TextView tv_language;
        TextView tv_math;
        TextView tv_english;
        RelativeLayout rl_class_teacher;

        public ViewHolder(View view) {
            super(view);
            iv_teacher_picture = (ImageView) itemView.findViewById(R.id.iv_teacher_picture);
            tv_class_teacher = (TextView) itemView.findViewById(R.id.tv_class_teacher);
            tv_teacher_name = (TextView) itemView.findViewById(R.id.tv_teacher_name);
            tv_language = (TextView) itemView.findViewById(R.id.tv_language);
            tv_math = (TextView) itemView.findViewById(R.id.tv_math);
            tv_english = (TextView) itemView.findViewById(R.id.tv_english);
            rl_class_teacher = (RelativeLayout) itemView.findViewById(R.id.rl_class_teacher);
        }
    }
}
