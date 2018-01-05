package com.up.study.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.up.teacher.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by kym on 2017/8/10.
 */

public class NorisukeAdapter extends BaseAdapter {
    public LayoutInflater inflater;
    private Context context;
    private  Map<String, String> mapData;
    private  Map<String, String> mapData1;

    public NorisukeAdapter(Context context,Map<String, String> mapData,Map<String, String> mapData1){
        this.context = context;
        this.mapData = mapData;
        this.mapData1 = mapData1;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mapData.size();
    }

    @Override
    public Object getItem(int position) {
        return mapData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_class_norisuke,null);
            viewHolder.tv_norisuke = (TextView) convertView.findViewById(R.id.tv_norisuke);
            viewHolder.tv_jf_name = (TextView) convertView.findViewById(R.id.tv_jf_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        List<Map.Entry<String,String>> list = new ArrayList<>(mapData.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,String>>(){
            @Override
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                int flag = o1.getValue().compareTo(o2.getValue());
                if(flag==0){
                    return o1.getKey().compareTo(o2.getKey());
                }
                return flag;
            }
        });
        List<String> data = new ArrayList<>();
        //遍历list得到map里面排序后的元素
        for(Map.Entry<String, String> en : list){
            data.add(en.getValue());
        }
        List<Map.Entry<String,String>> list1 = new ArrayList<>(mapData1.entrySet());
        Collections.sort(list1,new Comparator<Map.Entry<String,String>>(){
            @Override
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                int flag = o1.getValue().compareTo(o2.getValue());
                if(flag==0){
                    return o1.getKey().compareTo(o2.getKey());
                }
                return flag;
            }
        });
        try {
            if (list.get(position).getKey().equals(list1.get(position).getKey())){
                viewHolder.tv_jf_name.setText(list1.get(position).getValue());
            }
        }catch (Exception e){
            Toast.makeText(context,"数据异常",Toast.LENGTH_SHORT).show();
        }
        viewHolder.tv_norisuke.setText(list.get(position).getValue());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_norisuke;
        TextView tv_jf_name;
    }
}
