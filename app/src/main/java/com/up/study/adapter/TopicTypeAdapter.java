package com.up.study.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.up.common.widget.MyListView;
import com.up.study.db.CollectDao;
import com.up.study.ui.home.SearchResultActivity;
import com.up.study.weight.tvhtml.RichText;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kym on 2017/9/1.
 */

public class TopicTypeAdapter extends BaseAdapter {
    private ArrayList<Map> arrayList;
    private LayoutInflater inflater;
    private Context context;
    private CollectDao dao;

    public TopicTypeAdapter(Context context, ArrayList<Map> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        dao = new CollectDao(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
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
            viewHolder.tv_topic = (TextView) convertView.findViewById(R.id.tv_topic);
            viewHolder.tv_replace = (TextView) convertView.findViewById(R.id.tv_replace);
            viewHolder.tv_remove = (TextView) convertView.findViewById(R.id.tv_remove);
            viewHolder.wv_title = (RichText) convertView.findViewById(R.id.wv_title);
            viewHolder.mv_answer = (MyListView) convertView.findViewById(R.id.mv_answer);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.wv_title.setRichText((String) arrayList.get(position).get("title"));
        viewHolder.tv_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchResultActivity.class);
                intent.putExtra("paperId", (int) ((Double) arrayList.get(position).get("id")).longValue());
                intent.putExtra("title",(String) arrayList.get(position).get("title"));
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
                                arrayList.remove(position).get("id");
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.my_cancel, null)
                        .show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_topic, tv_replace, tv_remove;
        RichText wv_title;
        MyListView mv_answer;
    }
}
