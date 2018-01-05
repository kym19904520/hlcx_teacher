package com.up.study.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.up.common.widget.MyListView;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kym on 2017/9/1.
 */

public class ZjDetailsAdapter extends BaseAdapter {

    private Map<String, Object> map;
    private LayoutInflater inflater;
    private Context context;
    private TextView tv_next;
    private String id01,id02,id03,id04,id05,id06,id07;
    private ArrayList<Map> arrayList01,arrayList02,arrayList03,arrayList04,arrayList05,arrayList06,arrayList07;

    public ZjDetailsAdapter(Context context, Map<String, Object> map, TextView tv_next) {
        this.map = map;
        this.context = context;
        this.tv_next = tv_next;
        inflater = LayoutInflater.from(context);
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return map.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List<Map.Entry<String,Object>> list = new ArrayList<>(map.entrySet());
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_zj, null);
            viewHolder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            viewHolder.mv_answer = (MyListView) convertView.findViewById(R.id.mv_answer);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getKey().equals("单选题")){
            arrayList01 = (ArrayList<Map>) map.get("单选题");
            id01 = "";
            if (arrayList01!= null) {
                for (int i = 0; i < arrayList01.size(); i++) {
                    id01 += (int) ((Double) arrayList01.get(i).get("id")).longValue() + ",";
                }
            }
            if (arrayList01.size()<=0){
                viewHolder.tv_type.setVisibility(View.GONE);
                viewHolder.tv_number.setVisibility(View.GONE);
            }else {
                viewHolder.tv_type.setVisibility(View.VISIBLE);
                viewHolder.tv_number.setVisibility(View.VISIBLE);
            }
//            int id = (int) ((Double) arrayList.get(position).get("id")).longValue();
            viewHolder.tv_number.setText("（" + arrayList01.size() + "）");
            TopicTypeAdapter adapter = new TopicTypeAdapter(context,arrayList01);
            viewHolder.mv_answer.setAdapter(adapter);
        }else if (list.get(position).getKey().equals("判断题")){
            arrayList02 = (ArrayList<Map>) map.get("判断题");
            id02 = "";
            if (arrayList02 != null) {
                for (int i = 0; i < arrayList02.size(); i++) {
                    id02 += (int) ((Double) arrayList02.get(i).get("id")).longValue() + ",";
                }
            }
            if (arrayList02.size()<=0){
                viewHolder.tv_type.setVisibility(View.GONE);
                viewHolder.tv_number.setVisibility(View.GONE);
            }else {
                viewHolder.tv_type.setVisibility(View.VISIBLE);
                viewHolder.tv_number.setVisibility(View.VISIBLE);
            }
            viewHolder.tv_number.setText("（" + arrayList02.size() + "）");
            TopicTypeAdapter adapter = new TopicTypeAdapter(context,arrayList02);
            viewHolder.mv_answer.setAdapter(adapter);
        }
        else if (list.get(position).getKey().equals("填空题")){
            arrayList03 = (ArrayList<Map>) map.get("填空题");
            id03 = "";
            if (arrayList03 != null) {
                for (int i = 0; i < arrayList03.size(); i++) {
                    id03 += (int) ((Double) arrayList03.get(i).get("id")).longValue() + ",";
                }
            }
            if (arrayList03.size()<=0){
                viewHolder.tv_type.setVisibility(View.GONE);
                viewHolder.tv_number.setVisibility(View.GONE);
            }else {
                viewHolder.tv_type.setVisibility(View.VISIBLE);
                viewHolder.tv_number.setVisibility(View.VISIBLE);
            }
            viewHolder.tv_number.setText("（" + arrayList03.size() + "）");
            TopicTypeAdapter adapter = new TopicTypeAdapter(context,arrayList03);
            viewHolder.mv_answer.setAdapter(adapter);
        }
        else if (list.get(position).getKey().equals("多选题")){
            arrayList04 = (ArrayList<Map>) map.get("多选题");
            id04 = "";
            if (arrayList04 != null) {
                for (int i = 0; i < arrayList04.size(); i++) {
                    id04 += (int) ((Double) arrayList04.get(i).get("id")).longValue() + ",";
                }
            }
            if (arrayList04.size()<=0){
                viewHolder.tv_number.setVisibility(View.GONE);
                viewHolder.tv_type.setVisibility(View.GONE);
            }else {
                viewHolder.tv_number.setVisibility(View.VISIBLE);
                viewHolder.tv_type.setVisibility(View.VISIBLE);
            }
            viewHolder.tv_number.setText("（" + arrayList04.size() + "）");
            TopicTypeAdapter adapter = new TopicTypeAdapter(context,arrayList04);
            viewHolder.mv_answer.setAdapter(adapter);
        }
        else if (list.get(position).getKey().equals("应用题")){
            arrayList05 = (ArrayList<Map>) map.get("应用题");
            id05 = "";
            if (arrayList05 != null) {
                for (int i = 0; i < arrayList05.size(); i++) {
                    id05 += (int) ((Double) arrayList05.get(i).get("id")).longValue() + ",";
                }
            }
            if (arrayList05.size()<=0){
                viewHolder.tv_number.setVisibility(View.GONE);
                viewHolder.tv_type.setVisibility(View.GONE);
            }else {
                viewHolder.tv_type.setVisibility(View.VISIBLE);
                viewHolder.tv_number.setVisibility(View.VISIBLE);
            }
            viewHolder.tv_number.setText("（" + arrayList05.size() + "）");
            TopicTypeAdapter adapter = new TopicTypeAdapter(context,arrayList05);
            viewHolder.mv_answer.setAdapter(adapter);
        }
        else if (list.get(position).getKey().equals("解答题")){
            arrayList06 = (ArrayList<Map>) map.get("解答题");
            id06 = "";
            if (arrayList06 != null) {
                for (int i = 0; i < arrayList06.size(); i++) {
                    id06 += (int) ((Double) arrayList06.get(i).get("id")).longValue() + ",";
                }
            }
            if (arrayList06.size()<=0){
                viewHolder.tv_number.setVisibility(View.GONE);
                viewHolder.tv_type.setVisibility(View.GONE);
            }else {
                viewHolder.tv_type.setVisibility(View.VISIBLE);
                viewHolder.tv_number.setVisibility(View.VISIBLE);
            }
            viewHolder.tv_number.setText("（" + arrayList06.size() + "）");
            TopicTypeAdapter adapter = new TopicTypeAdapter(context,arrayList06);
            viewHolder.mv_answer.setAdapter(adapter);
        }
        else if (list.get(position).getKey().equals("计算题")){
            arrayList07 = (ArrayList<Map>) map.get("计算题");
            id07 = "";
            if (arrayList07 != null) {
                for (int i = 0; i < arrayList07.size(); i++) {
                    id07 += (int) ((Double) arrayList07.get(i).get("id")).longValue() + ",";
                }
            }
            if (arrayList07.size()<=0){
                viewHolder.tv_number.setVisibility(View.GONE);
                viewHolder.tv_type.setVisibility(View.GONE);
            }else {
                viewHolder.tv_type.setVisibility(View.VISIBLE);
                viewHolder.tv_number.setVisibility(View.VISIBLE);
            }
            viewHolder.tv_number.setText("（" + arrayList07.size() + "）");
            TopicTypeAdapter adapter = new TopicTypeAdapter(context, arrayList07);
            viewHolder.mv_answer.setAdapter(adapter);
        }
        if (arrayList01 !=null&&arrayList02!=null&&arrayList03!=null&&arrayList04!=null&&arrayList05!=null&&arrayList06!=null
        &&arrayList07!=null){
            tv_next.setText("试卷共" + (arrayList01.size() + arrayList02.size() + arrayList03.size() + arrayList04.size()
                    + arrayList05.size() + arrayList06.size() + arrayList07.size()) + "题，确认");
            tv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (id01 + id02 + id03 + id04 + id05 + id06 + id07);
                    Log.i("" + id.substring(0,id.length()-1) , "ffff");
                    Toast.makeText(context,"diansji",Toast.LENGTH_SHORT).show();
                }
            });
        }
        viewHolder.tv_type.setText(list.get(position).getKey());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_type,tv_number;
        MyListView mv_answer;
    }
}
