package com.up.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.up.study.model.ClassNumberBean;
import com.up.teacher.R;

import java.util.List;

/**
 * Created by kym on 2017/7/23.
 */

public class ClassNumberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private List<ClassNumberBean> dataList;
    private Context context;
    private OnItemClickListener mOnItemClickListener = null;

    public ClassNumberAdapter(Context context, List<ClassNumberBean> dataList) {
        this.dataList = dataList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_class_number, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (dataList != null){
            return dataList.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_class_name;
        public ViewHolder(View view) {
            super(view);
            tv_class_name = (TextView) itemView.findViewById(R.id.tv_class_name);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            tv_class_name.setText(dataList.get(position).getName());
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v,getPosition());
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
