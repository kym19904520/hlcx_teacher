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
import com.up.study.adapter.ClassStudentManageAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.StudentManageBean;
import com.up.teacher.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 学生管理
 * Created by kym on 2017/7/19.
 */

public class ClassStudentManageActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_student_manage)
    TextView tvStudentManage;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;
    @Bind(R.id.ll_no_class)
    LinearLayout llNoClass;
    @Bind(R.id.lv_student_manage)
    ListView lvStudentManage;

    private String classId;   //班级id
    private ClassStudentManageAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_student_manage;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        classId = getIntent().getStringExtra("cid");
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.class_student_manage);
        getStudentManageData();
    }

    /**
     * 获取学生的数据
     */
    private void getStudentManageData() {
        String url = Urls.CLASS_STUDENT_URL;
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        J.http().post(url, ctx, params, new HttpCallback<Respond<List<StudentManageBean>>>(ctx) {
            @Override
            public void success(Respond<List<StudentManageBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<StudentManageBean> dataList = res.getData();
                    adapter = new ClassStudentManageAdapter(ClassStudentManageActivity.this,dataList,
                            tvStudentManage,classId,0,llNoClass,tvNoData);
                    lvStudentManage.setAdapter(adapter);
                    if (adapter.mDataSets.size() <= 0){
                        llNoClass.setVisibility(View.VISIBLE);
                        lvStudentManage.setVisibility(View.GONE);
                        tvNoData.setText(R.string.no_student);
                    }
                    tvStudentManage.setText("学生（" + adapter.getListSize().size() + "）");
                    adapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnChildren(lvStudentManage);
                }
            }
        });
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

}
