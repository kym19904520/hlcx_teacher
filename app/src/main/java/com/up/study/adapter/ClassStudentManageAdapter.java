package com.up.study.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.StringUtils;
import com.up.common.utils.Utility;
import com.up.study.base.MyBaseAdapter;
import com.up.study.model.StudentManageBean;
import com.up.teacher.R;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kym on 2017/7/19.
 */

public class ClassStudentManageAdapter extends MyBaseAdapter<StudentManageBean> {
    private TextView number;
    private String classId;
    private int studentId, type;
    private Context context;
    private LinearLayout llNoClass;
    private TextView tvNoData;

    public ClassStudentManageAdapter(Context context, List<StudentManageBean> mDataSets, TextView number,
                                     String classId, int type, LinearLayout llNoClass, TextView tvNoData) {
        super(context, mDataSets);
        this.context = context;
        this.classId = classId;
        this.number = number;
        this.type = type;
        this.llNoClass = llNoClass;
        this.tvNoData = tvNoData;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_student_manage, null);
            viewHolder.ivStudentPreview = (ImageView) convertView.findViewById(R.id.iv_student_preview);
            viewHolder.tvStudentName = (TextView) convertView.findViewById(R.id.tv_student_name);
            viewHolder.ivStudentSex = (ImageView) convertView.findViewById(R.id.iv_student_sex);
            viewHolder.tvStudentId = (TextView) convertView.findViewById(R.id.tv_student_id);
            viewHolder.btnRemove = (Button) convertView.findViewById(R.id.btn_remove);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (type == 1) {
            viewHolder.btnRemove.setVisibility(View.GONE);
        }
        int sex = mDataSets.get(position).getSex();
        switch (sex) {
            case 0:
                viewHolder.ivStudentSex.setImageResource(R.mipmap.class_nan);
                break;
            case 1:
                viewHolder.ivStudentSex.setImageResource(R.mipmap.class_nv);
                break;
        }
        viewHolder.tvStudentName.setText(mDataSets.get(position).getName());
        viewHolder.tvStudentId.setText(mDataSets.get(position).getCode() + "");
        if (mDataSets.get(position).getHead() != null) {
            Glide.with(context)
                    .load(mDataSets.get(position).getHead())
                    .placeholder(R.mipmap.class_student)
                    .error(R.mipmap.class_student)
                    .override(StringUtils.dip2Px(context, 50), StringUtils.dip2Px(context, 50))
                    .into(viewHolder.ivStudentPreview);
            //.diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.delete)
                        .setPositiveButton(R.string.my_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                studentId = mDataSets.get(position).getId();
                                mDataSets.remove(position);
                                number.setText("学生（" + mDataSets.size() + "）");
                                if (mDataSets.size() <= 0){
                                    llNoClass.setVisibility(View.VISIBLE);
                                    tvNoData.setText(R.string.no_student);
                                }else {
                                    llNoClass.setVisibility(View.GONE);
                                }
                                removeStudent();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.my_cancel, null)
                        .show();
            }
        });
        return convertView;
    }

    public List<StudentManageBean> getListSize() {
        return mDataSets;
    }

    private void removeStudent() {
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        params.put("sid", studentId + "");
        J.http().post(Urls.REMOVE_STUDENT_URL, Utility.getContext(), params, new HttpCallback<Respond>(Utility.getContext()) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    Toast.makeText(context, "移除" + res.getMsg(), Toast.LENGTH_SHORT).show();
                    //删除成功，发送广播更新学生列表
                    Intent intent = new Intent();
                    intent.setAction("com.class.teacher");
                    context.sendBroadcast(intent);
                }
            }
        });
    }

    static class ViewHolder {
        ImageView ivStudentPreview;
        TextView tvStudentName;
        ImageView ivStudentSex;
        TextView tvStudentId;
        Button btnRemove;
    }
}
