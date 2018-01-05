package com.up.study.ui.clbum;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.Utility;
import com.up.study.adapter.ClassAuditAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.ClassAuditBean;
import com.up.teacher.R;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 加入班级审核
 * Created by kym on 2017/7/18.
 */

public class ClassAuditActivity extends BaseFragmentActivity {

    private TextView tv_title;
    private ImageView iv_back;
    private TextView tv_apply_for_teacher;
    private ListView lv_class_audit;
    private ClassAuditAdapter auditAdapter;
    private LinearLayout ll_no_class;
    private TextView tv_no_data;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_audit;
    }

    @Override
    protected void initView() {
        tv_title = bind(R.id.tv_title);
        iv_back = bind(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        tv_apply_for_teacher = bind(R.id.tv_apply_for_teacher);
        lv_class_audit = bind(R.id.lv_class_audit);
        ll_no_class = bind(R.id.ll_no_class);
        tv_no_data = bind(R.id.tv_no_data);
    }

    @Override
    protected void initData() {
        tv_title.setText(R.string.class_audit);
        getTeacherData();
    }

    private void getTeacherData() {
        J.http().post(Urls.CLASS_AUDIT_URL, ctx, null, new HttpCallback<Respond<List<ClassAuditBean>>>(ctx) {
            @Override
            public void success(Respond<List<ClassAuditBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<ClassAuditBean> dataList = res.getData();
                    if (dataList.size() <= 0){
                        lv_class_audit.setVisibility(View.GONE);
                        ll_no_class.setVisibility(View.VISIBLE);
                        tv_no_data.setText(R.string.class_no_data);
                    }
                    auditAdapter = new ClassAuditAdapter(ClassAuditActivity.this,dataList);
                    lv_class_audit.setAdapter(auditAdapter);
                    tv_apply_for_teacher.setText("申请老师（"+dataList.size() +"）");
                    Utility.setListViewHeightBasedOnChildren(lv_class_audit);
                }
            }
        });
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

}
