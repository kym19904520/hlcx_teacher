package com.up.study.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.StringUtils;
import com.up.study.base.MyBaseAdapter;
import com.up.study.model.TeacherManageBean;
import com.up.teacher.R;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kym on 2017/7/18.
 */

public class ClassTeacherManageAdapter extends MyBaseAdapter<TeacherManageBean> {
    private int uid;
    private int classId;
    private int is_activate;
    private Context context;

    public ClassTeacherManageAdapter(Context context, List<TeacherManageBean> mDataSets) {
        super(context, mDataSets);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_teacher_manage, null);
            viewHolder.ivTeacherPicture = (ImageView) convertView.findViewById(R.id.iv_teacher_picture);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvLanguage = (TextView) convertView.findViewById(R.id.tv_language);
            viewHolder.tvMath = (TextView) convertView.findViewById(R.id.tv_math);
            viewHolder.tvEnglish = (TextView) convertView.findViewById(R.id.tv_english);
            viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.tvStart = (TextView) convertView.findViewById(R.id.tv_start);
            viewHolder.tvForbid = (TextView) convertView.findViewById(R.id.tv_forbid);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ViewHolder finalViewHolder = viewHolder;
        is_activate = mDataSets.get(position).getIs_activate();
        classId = mDataSets.get(position).getClass_id();
        switch (is_activate) {
            case 0:
                viewHolder.tvForbid.setVisibility(View.GONE);
                viewHolder.tvStart.setVisibility(View.VISIBLE);
                break;
            case 1:
                viewHolder.tvForbid.setVisibility(View.VISIBLE);
                viewHolder.tvStart.setVisibility(View.GONE);
                break;
        }
        if (mDataSets.get(position).getHead() != null) {
            Glide.with(context)
                    .load(mDataSets.get(position).getHead())
                    .placeholder(R.mipmap.class_touxiang_01)
                    .error(R.mipmap.class_touxiang_01)
                    .override(StringUtils.dip2Px(context, 50), StringUtils.dip2Px(context, 50))
                    .into(viewHolder.ivTeacherPicture);
        }
        viewHolder.tvName.setText(mDataSets.get(position).getName());
        viewHolder.tvPhone.setText(mDataSets.get(position).getPhone());
        String[] array = mDataSets.get(position).getCoursename().split("\\+");
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("语文")) {
                viewHolder.tvLanguage.setVisibility(View.VISIBLE);
            } else if (array[i].equals("数学")) {
                viewHolder.tvMath.setVisibility(View.VISIBLE);
            } else if (array[i].equals("英语")) {
                viewHolder.tvEnglish.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = mDataSets.get(position).getUid();
                finalViewHolder.tvForbid.setVisibility(View.VISIBLE);
                finalViewHolder.tvStart.setVisibility(View.GONE);
                teacherJurisdiction(classId, uid, 1);
            }
        });
        viewHolder.tvForbid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = mDataSets.get(position).getUid();
                finalViewHolder.tvForbid.setVisibility(View.GONE);
                finalViewHolder.tvStart.setVisibility(View.VISIBLE);
                teacherJurisdiction(classId, uid, 0);
            }
        });
        return convertView;
    }

    private void teacherJurisdiction(int classId, int uid, int is_activate) {
        String url = Urls.TEACHER_MANAGE;
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        params.put("uid", uid);
        params.put("flag", is_activate);
        J.http().post(url, context, params, new HttpCallback<Respond>(context) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    Toast.makeText(context, "修改" + res.getMsg(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction("com.class.teacher");
                    context.sendBroadcast(intent);
                }
            }
        });
    }

    static class ViewHolder {
        ImageView ivTeacherPicture;
        TextView tvName;
        TextView tvLanguage;
        TextView tvMath;
        TextView tvEnglish;
        TextView tvPhone;
        TextView tvStart;
        TextView tvForbid;
    }
}
