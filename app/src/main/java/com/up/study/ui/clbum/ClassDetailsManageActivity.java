package com.up.study.ui.clbum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
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
import com.up.study.adapter.ClassTeacherCountAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.ClassTeacherDataBean;
import com.up.study.model.StudentManageBean;
import com.up.study.ui.ClassFragment;
import com.up.teacher.R;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 班级详情管理
 * Created by kym on 2017/7/18.
 */

public class ClassDetailsManageActivity extends BaseFragmentActivity {

    private TextView tv_manage;
    private TextView tv_apply_for_teacher;
    private RecyclerView rv_teacher_list;
    private LinearLayout ll_no_student;
    private TextView tv_ranking;
    private ListView lv_student_ranking;
    private ImageView iv_back;
    private ClassTeacherCountAdapter teacherManageAdapter;
    private ClassStudentManageAdapter studentManageAdapter;
    private String classId;
    private TextView tv_no_teacher;
    private ReceiveBroadCast receiveBroadCast;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_details_manage;
    }

    @Override
    protected void initView() {
        tv_manage = bind(R.id.tv_manage);
        tv_apply_for_teacher = bind(R.id.tv_apply_for_teacher);
        rv_teacher_list = bind(R.id.rv_teacher_list);
        ll_no_student = bind(R.id.ll_no_student);
        tv_ranking = bind(R.id.tv_ranking);
        lv_student_ranking = bind(R.id.lv_student_ranking);
        iv_back = bind(R.id.iv_back);
        tv_no_teacher = bind(R.id.tv_no_teacher);
        iv_back.setVisibility(View.VISIBLE);
        tv_manage.setVisibility(View.VISIBLE);

        Map<String, Object> map = getMap();
        classId = (String) map.get("classId");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_teacher_list.setLayoutManager(linearLayoutManager);

        receiveBroadCast = new ClassDetailsManageActivity.ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.class.teacher");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
    }

    @Override
    protected void initData() {
        getClassTeacherData();
        getClassStudentData();
    }

    /**
     * 获取老师数据
     */
    private void getClassTeacherData() {
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        J.http().post(Urls.CLASS_TEACHER_LIST, ctx, params, new HttpCallback<Respond<List<ClassTeacherDataBean>>>(ctx) {
            @Override
            public void success(Respond<List<ClassTeacherDataBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<ClassTeacherDataBean> listData = res.getData();
//                    if (listData.size() <= 0){
//                        tv_no_teacher.setVisibility(View.VISIBLE);
//                    }else {
//                        tv_no_teacher.setVisibility(View.GONE);
//                    }
                    teacherManageAdapter = new ClassTeacherCountAdapter(ClassDetailsManageActivity.this, listData);
                    tv_apply_for_teacher.setText("教师（" + listData.size() + "）");
                    rv_teacher_list.setAdapter(teacherManageAdapter);
                }
            }
        });
    }

    /**
     * 获取学生数据
     */
    private void getClassStudentData() {
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        J.http().post(Urls.CLASS_STUDENT_URL, ctx, params, new HttpCallback<Respond<List<StudentManageBean>>>(ctx) {
            @Override
            public void success(Respond<List<StudentManageBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<StudentManageBean> dataList = res.getData();
                    studentManageAdapter = new ClassStudentManageAdapter(ClassDetailsManageActivity.this,
                            dataList, null, classId, 1, null, null);
                    tv_ranking.setText("学生（" + dataList.size() + "）");
                    if (dataList.size() <= 0) {
                        ll_no_student.setVisibility(View.VISIBLE);
                        lv_student_ranking.setVisibility(View.GONE);
                    }else {
                        ll_no_student.setVisibility(View.GONE);
                        lv_student_ranking.setVisibility(View.VISIBLE);
                    }
                    lv_student_ranking.setAdapter(studentManageAdapter);
                    studentManageAdapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnChildren(lv_student_ranking);
                    lv_student_ranking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ClassDetailsManageActivity.this, ClassPatriarchManageActivity.class);
                            intent.putExtra("sid", studentManageAdapter.mDataSets.get(position).getId() + "");
                            intent.putExtra("studentName", studentManageAdapter.mDataSets.get(position).getName());
                            intent.putExtra("code", studentManageAdapter.mDataSets.get(position).getCode());
                            intent.putExtra("head", studentManageAdapter.mDataSets.get(position).getHead());
                            intent.putExtra("sex", studentManageAdapter.mDataSets.get(position).getSex());
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        tv_manage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_manage) {              //管理
            gotoActivity(ClassManageActivity.class, "classId", classId);
        }
    }

    /**
     * 发送广播更新数据
     */
    public class ReceiveBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新老师列表
            getClassTeacherData();
            //刷新学生列表
            getClassStudentData();
            //刷新班级审核的个数
            ClassFragment.instance.refreshClassAudit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiveBroadCast != null) {
            unregisterReceiver(receiveBroadCast);
        }
    }
}
