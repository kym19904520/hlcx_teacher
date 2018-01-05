package com.up.study.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.up.study.model.PatriarchManageBean;
import com.up.teacher.R;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by kym on 2017/7/19.
 */

public class ClassPatriarchManageAdapter extends MyBaseAdapter<PatriarchManageBean> {
    private String studentId,pid;
    private Context context;
    private TextView number,tvNoData;
    private LinearLayout llNoClass;

    public ClassPatriarchManageAdapter(Context context, List<PatriarchManageBean> mDataSets, String
            studentId, TextView number, LinearLayout llNoClass, TextView tvNoData) {
        super(context, mDataSets);
        this.number = number;
        this.context = context;
        this.studentId = studentId;
        this.llNoClass = llNoClass;
        this.tvNoData = tvNoData;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_class_patriarc_manage, null);
            viewHolder.iv_student_preview = (ImageView) convertView.findViewById(R.id.iv_student_preview);
            viewHolder.tv_patriarch_code = (TextView) convertView.findViewById(R.id.tv_patriarch_code);
            viewHolder.btn_remove = (Button) convertView.findViewById(R.id.btn_remove);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        pid = mDataSets.get(position).getId() + "";
        viewHolder.tv_patriarch_code.setText(mDataSets.get(position).getAccount());
        if (mDataSets.get(position).getHead() != null) {
            Glide.with(context).load(mDataSets.get(position).getHead()).error(R.mipmap.class_moren)
                    .override(StringUtils.dip2Px(context, 50), StringUtils.dip2Px(context, 50))
                    .into(viewHolder.iv_student_preview);
        }
        viewHolder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.delete)
                        .setPositiveButton(R.string.my_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDataSets.remove(position);
                                if (mDataSets.size() <= 0){
                                    llNoClass.setVisibility(View.VISIBLE);
                                    tvNoData.setText(R.string.no_patriarch);
                                }
                                removePatriarch();
                                number.setText("家长（" + mDataSets.size() + "）");
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.my_cancel, null)
                        .show();

            }
        });
        return convertView;
    }

    public List<PatriarchManageBean> getListSize() {
        return mDataSets;
    }

    private void removePatriarch() {
        String url = Urls.CLASS_REMOVE_PATRIARCH_URL;
        HttpParams params = new HttpParams();
        params.put("pid", pid);
        params.put("sid", studentId);
        J.http().post(url, Utility.getContext(), params, new HttpCallback<Respond>(Utility.getContext()) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    Toast.makeText(context, "移除" + res.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static class ViewHolder {
        ImageView iv_student_preview;
        TextView tv_patriarch_code;
        Button btn_remove;
    }
}
