package com.up.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.study.base.MyBaseAdapter;
import com.up.study.model.Book;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kym on 2017/7/19.
 */

public class ClassNorisukeAdapter extends MyBaseAdapter<Book>{
    private HashMap<Integer, Book> map = new HashMap<Integer, Book>();
    private TextView tv_selected;

    public ClassNorisukeAdapter(Context context,TextView tv_selected, ArrayList<Book> mDataSets) {
        super(context,mDataSets);
        this.tv_selected = tv_selected;
    }

    public HashMap<Integer, Book> getMap() {
        return map;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        TextView tvJfName;
        ImageView ivSelected;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_class_jf, null);
            tvJfName = (TextView) convertView.findViewById(R.id.tv_jf_name);
            ivSelected = (ImageView) convertView.findViewById(R.id.iv_selected);
            viewHolder.tvJfName = tvJfName;
            viewHolder.ivSelected = ivSelected;
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            tvJfName = viewHolder.tvJfName;
            ivSelected = viewHolder.ivSelected;
        }
        //初始化数据
        tvJfName.setText(mDataSets.get(position).getName());
//        if(list.get(position).getStatus() == 1) {
//            map.put(position,list.get(position));
//        }

//        ivSelected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!map.containsKey(position)){
//                    map.put(position,list.get(position));
//                    ((ImageView) v).setImageResource(R.mipmap.class_gx);
//                    Log.i("22222222222" ,map.get(position).getName());
//                }else {
//                    map.remove(position);
//                    ((ImageView) v).setImageResource(R.mipmap.class_gx01);
//                }
//                tv_selected.setText(map.size()+"");
//            }
//        });
//        if (map.containsKey(position)){
//            ivSelected.setImageResource(R.mipmap.class_gx);
//        } else {
//            ivSelected.setImageResource(R.mipmap.class_gx01);
//        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvJfName;
        ImageView ivSelected;
    }
}
