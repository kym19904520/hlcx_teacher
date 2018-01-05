package com.up.study.ui.my;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.adapter.SelectKmAdapter;
import com.up.study.adapter.StudentAnalyzeAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.MyClassBean;
import com.up.study.model.MyStudentListBean;
import com.up.study.model.MyTextbookBean;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 学生分析
 * Created by kym on 2017/7/20.
 */

public class StudentAnalyzeActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_subject_change)
    RelativeLayout rlSubjectChange;
    @Bind(R.id.lv_student_analyze)
    ListView lvStudentAnalyze;
    @Bind(R.id.tv_class_subject)
    TextView tv_class_subject;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;
    @Bind(R.id.ll_no_class)
    LinearLayout llNoClass;
    @Bind(R.id.tv_class_select)
    TextView tv_class_select;

    private String bj_id;
    private List<MyTextbookBean> myTextbookBeanList;
    private String courseName;
    private int courseId;
    private String grade;
    private StudentAnalyzeAdapter adapter;
    private OptionsPickerView pvNoLinkOptions;

    @Override
    protected int getContentViewId() {
        return R.layout.act_student_analyze;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        tv_class_select.setVisibility(View.VISIBLE);
//        spinner.setVisibility(View.VISIBLE);
        getClassRequestData();
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_students_analysis);
    }

    /**
     * 暂时不用
     */
   /* private void initSpinner(final List<MyClassBean> myClassBeanList) {
        CommonAdapter adapter1 = new CommonAdapter<MyClassBean>(ctx, myClassBeanList, android.R.layout.simple_spinner_item) {
            @Override
            public void convert(ViewHolder vh, MyClassBean item, int position) {
                vh.setText(android.R.id.text1, item.getClassName().toString());
                bj_id = String.valueOf(((MyClassBean) spinner.getSelectedItem()).getClassId());
            }
        };
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(Color.WHITE);
                ((MyClassBean) spinner.getSelectedItem()).getClassName();
                bj_id = String.valueOf(((MyClassBean) spinner.getSelectedItem()).getClassId());
                grade = String.valueOf(((MyClassBean) spinner.getSelectedItem()).getGrade());
                getSubjectData(bj_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter1);
    }*/

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick({R.id.iv_back, R.id.rl_subject_change,R.id.tv_class_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_subject_change:        //选择学科
                getSubject(myTextbookBeanList);
                break;
            case R.id.tv_class_select:          //选择班级
                pvNoLinkOptions.show();
                break;
        }
    }

    /**
     * 选择学科
     */
    private SelectKmAdapter selectKmAdapter;
    private AlertDialog alertDialog;

    public void getSubject(List<MyTextbookBean> myTextbookBeanList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_xk_list, null);
        builder.setView(view);
        builder.setTitle(R.string.section11);
        final ListView lv_list_jf = (ListView) view.findViewById(R.id.lv_list);
        selectKmAdapter = new SelectKmAdapter(this, myTextbookBeanList);
        lv_list_jf.setAdapter(selectKmAdapter);
        lv_list_jf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                courseName = selectKmAdapter.mDataSets.get(position).getCourseName();
                courseId = selectKmAdapter.mDataSets.get(position).getCourseId();
                tv_class_subject.setText(courseName);
                getStudentList(bj_id, courseId);
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 获取学科数据
     *
     * @param bj_id
     * @return
     */
    public void getSubjectData(final String bj_id) {
        HttpParams params = new HttpParams();
//        params.put("userId", 4);
        params.put("classId", bj_id);
        J.http().post(Urls.GET_SUBJECT_URL, ctx, params, new HttpCallback<Respond<List<MyTextbookBean>>>(null) {
            @Override
            public void success(Respond<List<MyTextbookBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    myTextbookBeanList = res.getData();
                    tv_class_subject.setText(myTextbookBeanList.get(0).getCourseName());
                    courseId = myTextbookBeanList.get(0).getCourseId();
                    getStudentList(bj_id, courseId);
                }
            }
        });
    }

    /**
     * 获取学生列表的数据
     *
     * @param bj_id--班级id
     * @param courseId--学科id
     */
    private void getStudentList(final String bj_id, final int courseId) {
        showLog(this.bj_id + "获取的id" + this.courseId);
        HttpParams params = new HttpParams();
        params.put("classId", bj_id);
        params.put("courseId", courseId);
        J.http().post(Urls.STUDENT_LIST_URL, ctx, params, new HttpCallback<Respond<List<MyStudentListBean>>>(ctx) {
            @Override
            public void success(Respond<List<MyStudentListBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<MyStudentListBean> listData = res.getData();
                    if (listData.size() <= 0) {
                        lvStudentAnalyze.setVisibility(View.GONE);
                        llNoClass.setVisibility(View.VISIBLE);
                        tvNoData.setText(R.string.no_student);
                    } else {
                        lvStudentAnalyze.setVisibility(View.VISIBLE);
                    }
                    adapter = new StudentAnalyzeAdapter(StudentAnalyzeActivity.this, listData, 0);
                    lvStudentAnalyze.setAdapter(adapter);
                }
                lvStudentAnalyze.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(StudentAnalyzeActivity.this, StudentAnalyzeDetailsActivity.class);
                        intent.putExtra("classId", bj_id);
                        intent.putExtra("courseId", courseId);
                        intent.putExtra("grade", grade);
                        intent.putExtra("studentName", adapter.mDataSets.get(position).getName());
                        intent.putExtra("studentId", adapter.mDataSets.get(position).getId() + "");
                        showLog("获取详情的id" + bj_id + courseId + grade + adapter.mDataSets.get(position).getId());
                        startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 获取班级的数据
     */
    public void getClassRequestData() {
        HttpParams params = new HttpParams();
        //params.put("userId", "4");
        J.http().post(Urls.GET_CLASS_URL, ctx, params, new HttpCallback<Respond<List<MyClassBean>>>(ctx) {
            @Override
            public void success(Respond<List<MyClassBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    if (res.getData() != null) {
                        List<MyClassBean> myClassBeanList = res.getData();
                        bj_id = myClassBeanList.get(0).getClassId() + "";
                        grade = myClassBeanList.get(0).getGrade() + "";
                        getSubjectData(bj_id);
                        tv_class_select.setText(myClassBeanList.get(0).getClassName());
//                        initSpinner(myClassBeanList);
                        initNoLinkOptionsPicker(myClassBeanList);
                    }
                }
            }
        });
    }

    /**
     * 选择班级的弹窗
     * @param myClassBeanList
     */
    private void initNoLinkOptionsPicker(final List<MyClassBean> myClassBeanList) {
        final List<String> stringList = new ArrayList<>();
        final List<Integer> integerList = new ArrayList<>();
        final List<Integer> gradeList = new ArrayList<>();
        for (MyClassBean bean : myClassBeanList){
            stringList.add(bean.getClassName());
            integerList.add(bean.getClassId());
            gradeList.add(bean.getGrade());
        }
        pvNoLinkOptions = new OptionsPickerView.Builder(StudentAnalyzeActivity.this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_class_select.setText(stringList.get(options1));
                bj_id = integerList.get(options1) + "";
                grade = gradeList.get(options1) + "";
                getSubjectData(bj_id);
            }
        }).setBackgroundId(0x00FFFFFF).build();
        pvNoLinkOptions.setNPicker(stringList, null, null);
    }
}
