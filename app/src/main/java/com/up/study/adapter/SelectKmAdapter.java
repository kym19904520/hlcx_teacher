package com.up.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.up.study.base.MyBaseAdapter;
import com.up.study.model.MyTextbookBean;
import com.up.teacher.R;

import java.util.List;

/**
 * Created by kym on 2017/7/22.
 */

public class SelectKmAdapter extends MyBaseAdapter<MyTextbookBean> {

    public SelectKmAdapter(Context context, List<MyTextbookBean> mDataSets) {
        super(context, mDataSets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_jf,null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(mDataSets.get(position).getCourseName());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
    }
}
