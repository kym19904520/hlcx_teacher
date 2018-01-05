package com.up.study.ui.my;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.study.adapter.StudentAnalyzeAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.MyStudentListBean;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 知识点学生分析
 * Created by kym on 2017/7/20.
 */

public class KnowledgePointAnalyzeActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.lv_student_analyze)
    ListView lvStudentAnalyze;

    private StudentAnalyzeAdapter analyzeAdapter;
    private String classId;
    private String courseId;
    private String grade;
    private int kId;

    @Override
    protected int getContentViewId() {
        return R.layout.act_knowledge_point_analyze;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        classId = SPUtil.getString(this,"classId","");
        courseId = SPUtil.getString(this,"courseId","");
        grade = SPUtil.getString(this,"grade","");
        readExtra();
    }

    public void readExtra(){
        Intent intent=getIntent();
        kId=intent.getIntExtra("kId",0);
        showLog("KnowledgePointAnalyzeActivity :id" + classId + "---" + courseId + "---" + grade + "---" + kId);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_knowledge_point_analysis);
        lvStudentAnalyze.setAdapter(new StudentAnalyzeAdapter(this, new ArrayList<MyStudentListBean>(), 1));
        getStudentAnalyzeData();
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    public static void startKnowledgePointAnalyzeActivity(Context context, int kId){
        Intent intent = new Intent(context,KnowledgePointAnalyzeActivity.class);
        intent.putExtra("kId",kId);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    /**
     * 获取数据
     */
    public void getStudentAnalyzeData() {
        HttpParams params = new HttpParams();
        params.put("courseId", courseId);
        params.put("classId", classId);
        params.put("kid",kId +"");
        J.http().post(Urls.STUDENT_ANALYZE_URL, ctx, params, new HttpCallback<Respond<List<MyStudentListBean>>>(ctx) {
            @Override
            public void success(Respond<List<MyStudentListBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<MyStudentListBean> listData = res.getData();
                    analyzeAdapter = new StudentAnalyzeAdapter(KnowledgePointAnalyzeActivity.this, listData,1);
                    lvStudentAnalyze.setAdapter(analyzeAdapter);
                }
            }
        });
    }
}
