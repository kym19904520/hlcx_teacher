package com.up.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.up.study.model.ClassAuditBean;
import com.up.study.ui.ClassFragment;
import com.up.teacher.R;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kym on 2017/7/19.
 */

public class ClassAuditAdapter extends MyBaseAdapter<ClassAuditBean> {
    private Context context;

    public ClassAuditAdapter(Context context, List<ClassAuditBean> mDataSets) {
        super(context, mDataSets);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_class_audit, null);
            viewHolder.ivTeacherPicture = (ImageView) convertView.findViewById(R.id.iv_teacher_picture);
            viewHolder.tvTeacherName = (TextView) convertView.findViewById(R.id.tv_teacher_name);
            viewHolder.tvLanguage = (TextView) convertView.findViewById(R.id.tv_language);
            viewHolder.tvMath = (TextView) convertView.findViewById(R.id.tv_math);
            viewHolder.tvEnglish = (TextView) convertView.findViewById(R.id.tv_english);
            viewHolder.tvTeacherNumber = (TextView) convertView.findViewById(R.id.tv_teacher_number);
            viewHolder.tvAddClass = (TextView) convertView.findViewById(R.id.tv_add_class);
            viewHolder.tvHasJoined = (TextView) convertView.findViewById(R.id.tv_has_joined);
            viewHolder.btnConsent = (Button) convertView.findViewById(R.id.btn_consent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mDataSets.get(position).getHead() != null){
            Glide.with(context)
                    .load(mDataSets.get(position).getHead())
                    .placeholder(R.mipmap.class_touxiang_01)
                    .error(R.mipmap.class_touxiang_01)
                    .override(StringUtils.dip2Px(context,50),StringUtils.dip2Px(context,50))
                    .into(viewHolder.ivTeacherPicture);
        }
        viewHolder.tvTeacherName.setText(mDataSets.get(position).getUname());
        viewHolder.tvTeacherNumber.setText(mDataSets.get(position).getPhone());
        viewHolder.tvAddClass.setText("申请加入：" + mDataSets.get(position).getCname());
        String[] array = mDataSets.get(position).getCoursename().split("\\+");
        for (int i = 0;i<array.length;i++){
            if (array[i].equals("语文")){
                viewHolder.tvLanguage.setVisibility(View.VISIBLE);
            }else if (array[i].equals("数学")){
                viewHolder.tvMath.setVisibility(View.VISIBLE);
            }else if (array[i].equals("英语")){
                viewHolder.tvEnglish.setVisibility(View.VISIBLE);
            }
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnConsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalViewHolder.btnConsent.setVisibility(View.GONE);
                finalViewHolder.tvHasJoined.setVisibility(View.VISIBLE);
                submitTeacherAudit(mDataSets.get(position).getUid(),mDataSets.get(position).getCid());
            }
        });
        return convertView;
    }

    private void submitTeacherAudit(int teacherId, int classId) {
        HttpParams params = new HttpParams();
        params.put("cid",classId);
        params.put("uid",teacherId);
        params.put("flag",1);
        J.http().post(Urls.TEACHER_MANAGE, context, params, new HttpCallback<Respond>(context) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())){
                    Toast.makeText(context,"加入" + res.getMsg(),Toast.LENGTH_SHORT).show();
                    ClassFragment.instance.refreshClassAudit();
                }
            }
        });
    }

    static class ViewHolder {
        ImageView ivTeacherPicture;
        TextView tvTeacherName;
        TextView tvLanguage;
        TextView tvMath;
        TextView tvEnglish;
        TextView tvTeacherNumber;
        TextView tvAddClass;
        Button btnConsent;
        TextView tvHasJoined;
    }
}
