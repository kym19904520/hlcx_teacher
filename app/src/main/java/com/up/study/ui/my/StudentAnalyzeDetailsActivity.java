package com.up.study.ui.my;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.widget.MyListView;
import com.up.study.adapter.StudentAnalyzeDetailsAdapter;
import com.up.study.adapter.StudentAnalyzeDetailsAdapter01;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.MyStudentDataBean;
import com.up.teacher.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 学生分析详情
 * Created by kym on 2017/7/20.
 */

public class StudentAnalyzeDetailsActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.lv_point_name)
    MyListView lvPointName;
    @Bind(R.id.lv_section_name)
    MyListView lvSectionName;
    @Bind(R.id.ll_gone)
    LinearLayout ll_gone;
    @Bind(R.id.ll_no_class)
    LinearLayout ll_no_class;
    @Bind(R.id.tv_no_data)
    TextView tv_no_data;
    @Bind(R.id.ll_no_zsd)
    LinearLayout ll_no_zsd;
    @Bind(R.id.ll_no_zj)
    LinearLayout ll_no_zj;

    private String classId;
    private String courseId;
    private String grade;
    private String studentId;
    private String studentName;

    private StudentAnalyzeDetailsAdapter adapter;
    private StudentAnalyzeDetailsAdapter01 adapter01;

    @Override
    protected int getContentViewId() {
        return R.layout.act_student_analyze_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        classId = intent.getStringExtra("classId");
        courseId = String.valueOf(intent.getIntExtra("courseId", 0));
        grade = intent.getStringExtra("grade");
        studentId = intent.getStringExtra("studentId");
        studentName = intent.getStringExtra("studentName");
        showLog("获取到学生分析详情的id" + classId + courseId + grade + studentId);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        tvTitle.setText(studentName);
        getStudentData();
    }

    @Override
    protected void initEvent() {
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 获取学生知识，章节列表
     */
    private void getStudentData() {
        HttpParams params = new HttpParams();
        params.put("classId", classId);
        params.put("courseId", courseId + "");
        params.put("grade", grade);
        params.put("studentId", studentId);
        //params.put("page",8);
        J.http().post(Urls.STUDENT_ANALYZE_DETAILS, ctx, params, new HttpCallback<Respond<MyStudentDataBean>>(ctx) {
            @Override
            public void success(Respond<MyStudentDataBean> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    MyStudentDataBean myStudentDataBean = res.getData();
                    List<MyStudentDataBean.KnowBean> knowBeanList = myStudentDataBean.getKnow();
                    adapter = new StudentAnalyzeDetailsAdapter(StudentAnalyzeDetailsActivity.this, knowBeanList);
                    lvPointName.setAdapter(adapter);
                    if (knowBeanList.size() <=0){
                        ll_no_zsd.setVisibility(View.VISIBLE);
                    }

                    List<MyStudentDataBean.StructureBean> structureBeanList = myStudentDataBean.getStructure();
                    adapter01 = new StudentAnalyzeDetailsAdapter01(StudentAnalyzeDetailsActivity.this, structureBeanList);
                    lvSectionName.setAdapter(adapter01);
                    if (structureBeanList.size() <= 0){
                        ll_no_zj.setVisibility(View.VISIBLE);
                    }

                    if (knowBeanList.size() <= 0 || structureBeanList.size() <= 0){
//                        ll_no_class.setVisibility(View.VISIBLE);
//                        tv_no_data.setText(R.string.class_no_data);
                    }
                }
            }
        });
    }
}
