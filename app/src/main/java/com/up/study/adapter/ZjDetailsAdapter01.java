package com.up.study.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.up.study.db.CollectDao;
import com.up.study.db.ZJBean;
import com.up.study.ui.home.SearchResultActivity;
import com.up.study.weight.tvhtml.RichText;
import com.up.teacher.R;

import java.util.List;

/**
 * Created by kym on 2017/9/1.
 */

public class ZjDetailsAdapter01 extends BaseAdapter {

    private List<ZJBean> allList,list;
    private LayoutInflater inflater;
    private Context context;
    private TextView tv_next,tvNoData;
    private CollectDao dao;
    private LinearLayout llNoClass;

    public ZjDetailsAdapter01(Context context, List<ZJBean> allList, TextView tv_next, TextView tvNoData, LinearLayout llNoClass) {
        this.context = context;
        this.allList = allList;
        inflater = LayoutInflater.from(context);
        this.tv_next = tv_next;
        this.tvNoData = tvNoData;
        this.llNoClass = llNoClass;
        dao = new CollectDao(context);
    }

    @Override
    public int getCount() {
        return allList.size();
    }

    @Override
    public Object getItem(int position) {
        return allList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_topic, null);
            viewHolder.wv_title = (RichText) convertView.findViewById(R.id.wv_title);
            viewHolder.tv_replace = (TextView) convertView.findViewById(R.id.tv_replace);
            viewHolder.tv_remove = (TextView) convertView.findViewById(R.id.tv_remove);
            viewHolder.tv_subject_type = (TextView) convertView.findViewById(R.id.tv_subject_type);
//            viewHolder.mv_answer = (MyListView) convertView.findViewById(R.id.mv_answer);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.wv_title.setRichText(allList.get(position).getPaperName());
        viewHolder.tv_subject_type.setText(allList.get(position).getTypeName());
        viewHolder.tv_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchResultActivity.class);
                intent.putExtra("paperId", allList.get(position).getPaperId());
                intent.putExtra("title", allList.get(position).getPaperName());
                intent.putExtra("typeName",allList.get(position).getTypeName());
                context.startActivity(intent);
            }
        });
        viewHolder.tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.delete)
                        .setPositiveButton(R.string.my_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dao.isExist(allList.get(position).getPaperName())) {
                                    dao.delete(allList.get(position).getPaperName());
                                    allList.remove(position);
                                }
                                list = dao.findAll();
                                for (ZJBean zjBean : list) {
                                    Log.i("tag", "删除sql" + zjBean.getPaperId() + zjBean.getPaperName());
                                }
                                if (list.size() == 0){
                                    llNoClass.setVisibility(View.VISIBLE);
                                    tvNoData.setText("请添加题目喔~");
                                }else {
                                    llNoClass.setVisibility(View.GONE);
                                }
                                tv_next.setText("试卷共" + list.size() + "题，确认");
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton(R.string.my_cancel, null)
                        .show();
            }
        });
        tv_next.setText("试卷共" + allList.size() + "题，确认");
        return convertView;
    }

    static class ViewHolder {
        RichText wv_title;
        TextView tv_replace;
        TextView tv_remove;
        TextView tv_subject_type;
//        MyListView mv_answer;
    }
}
