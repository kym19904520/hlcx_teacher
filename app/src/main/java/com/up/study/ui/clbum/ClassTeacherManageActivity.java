package com.up.study.ui.clbum;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.Utility;
import com.up.study.adapter.ClassTeacherManageAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.TeacherManageBean;
import com.up.teacher.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 老师管理
 * Created by kym on 2017/7/19.
 */

public class ClassTeacherManageActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;
    @Bind(R.id.ll_no_class)
    LinearLayout llNoClass;
    @Bind(R.id.lv_teacher_manage)
    ListView lvTeacherManage;
    @Bind(R.id.tv_teacher_manage)
    TextView tvTeacherManage;

    private String classId;  //班级id
    private ClassTeacherManageAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_teacher_manage;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        classId = getIntent().getStringExtra("cid");
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.class_teacher_manage);
        getTeacherManage();
    }

    /**
     * 获取老师的数据
     */
    private void getTeacherManage() {
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        J.http().post(Urls.CLASS_TEACHER_URL, ctx, params, new HttpCallback<Respond<List<TeacherManageBean>>>(ctx) {
            @Override
            public void success(Respond<List<TeacherManageBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<TeacherManageBean> dataList = res.getData();
                    if (dataList.size() <= 0) {
                        lvTeacherManage.setVisibility(View.GONE);
                        llNoClass.setVisibility(View.VISIBLE);
                        tvNoData.setText(R.string.no_teacher);
                    }
                    adapter = new ClassTeacherManageAdapter(ClassTeacherManageActivity.this, dataList);
                    lvTeacherManage.setAdapter(adapter);
                    tvTeacherManage.setText("老师（" + adapter.mDataSets.size() + "）");
                    Utility.setListViewHeightBasedOnChildren(lvTeacherManage);
                }
            }

            @Override
            public void error(Call call, Response response, Exception e, boolean isCache) {
                llNoClass.setVisibility(View.VISIBLE);
                tvNoData.setText(R.string.class_no_data);
            }
        });
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

}
