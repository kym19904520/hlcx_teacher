package com.up.study.ui.my;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.github.mikephil.charting.charts.LineChart;
import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.common.widget.MyListView;
import com.up.study.adapter.SectionViewAdapter;
import com.up.study.adapter.SelectKmAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.lineChart.MPChartHelper;
import com.up.study.model.MyClassBean;
import com.up.study.model.MyStudentAnalysisListBean;
import com.up.study.model.MyTextbookBean;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 学情分析
 * Created by kym on 2017/7/20.
 */

public class StudyAnalyzeActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_select_textbook)
    TextView tvSelectTextbook;
    @Bind(R.id.tv_capacity)
    TextView tvCapacity;
    @Bind(R.id.tv_province_ranking)
    TextView tvProvinceRanking;
    @Bind(R.id.tv_grade_ranking)
    TextView tvGradeRanking;
    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.lineChart)
    LineChart lineChart;
    @Bind(R.id.fl_layout)
    FrameLayout flLayout;
    @Bind(R.id.lv_section_puzzle)
    MyListView lvSectionPuzzle;
    @Bind(R.id.ll_puzzle_analyze)
    LinearLayout ll_puzzle_analyze;
    @Bind(R.id.tv_class_select)
    TextView tv_class_select;

    private List<String> titles;
    private List<List<Float>> yAxisValues;
    private List<Float> yAxisValue;
    private List<String> xAxisValues;

    private static final int SECTION = 1;
    private static final int KNOWLEDGE_POINT = 0;
    private int curTabPosition = KNOWLEDGE_POINT;

    private int courseId;
    private String bj_id;
    private SectionViewAdapter sectionViewAdapter;
    private String grade;
    private List<MyTextbookBean> myTextbookBeanList;
    private SelectKmAdapter adapter;
    private List<MyStudentAnalysisListBean.KnowBean> knowBeanList;
    private List<MyStudentAnalysisListBean.StructureBean> structureBeanList;
    private List<Float> yAxisValues1;
    private OptionsPickerView pvNoLinkOptions;
    private List<MyStudentAnalysisListBean.SubjectBean> subjectBeanList;

    @Override
    protected int getContentViewId() {
        return R.layout.act_study_analyze;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        getClassRequestData();
        ivBack.setVisibility(View.VISIBLE);
        tv_class_select.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_student_analysis);
        flLayout.addView(new KnowledgePointView(getApplicationContext(), knowBeanList));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case KNOWLEDGE_POINT:
                        curTabPosition = KNOWLEDGE_POINT;
                        flLayout.removeAllViews();
                        flLayout.addView(new KnowledgePointView(getApplicationContext(), knowBeanList));
                        break;
                    case SECTION:
                        curTabPosition = SECTION;
                        flLayout.removeAllViews();
                        flLayout.addView(new SectionView(getApplicationContext(), structureBeanList));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 设置折线图的数据
     * @param powerLine
     */
    private void getChart(MyStudentAnalysisListBean.PowerLineBean powerLine) {
        xAxisValues = new ArrayList<>();
        yAxisValues = new ArrayList<>();
        yAxisValue = new ArrayList<>();
        try {
            List<String> xAxisList = powerLine.getXAxis();
            for (int i = 0; i < xAxisList.size(); ++i) {
                xAxisValues.add(xAxisList.get(i));//X轴的班级数据
            }
            List<List<MyStudentAnalysisListBean.PowerLineBean.DatasBean>> datasBeanList = powerLine.getDatas();
            showLog(datasBeanList.size() + "年级的个数");
            if (datasBeanList.size() > 1) {
                for (int i = 0; i < datasBeanList.size(); i++) {
                    //第i条折线图的y轴值集合
                    yAxisValues1 = new ArrayList<>();
                    List<MyStudentAnalysisListBean.PowerLineBean.DatasBean> list = datasBeanList.get(i);
                    for (int j = 0; j < list.size(); j++) {//第i条折线图的y轴值
                        yAxisValues1.add((float) list.get(j).getValue());
                    }
                    yAxisValues.add(yAxisValues1);
                }

                titles = powerLine.getLegend();
                int[] lineColor = new int[titles.size()];
                Random random = new Random();
                for (int i = 0; i < titles.size(); i++) {
                    int color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    lineColor[i] = color;
                }
                lineChart.clear();
                MPChartHelper.setLinesChart(lineChart, xAxisValues, yAxisValues, titles, false, lineColor);
            } else {
                for (int i = 0; i < datasBeanList.size(); i++) {
                    //第i条折线图的y轴值集合
                    yAxisValue = new ArrayList<>();
                    List<MyStudentAnalysisListBean.PowerLineBean.DatasBean> list = datasBeanList.get(i);
                    for (int j = 0; j < list.size(); j++) {//第i条折线图的y轴值
                        yAxisValue.add((float) list.get(j).getValue());
                    }
                }
                String title = "";
                if (powerLine.getLegend().size() != 0) {
                    title = powerLine.getLegend().get(0);
                }
                showLog("1条折线的title" + title);
                showLog("1条折线的y轴" + yAxisValue.get(0).toString());
                lineChart.clear();
                MPChartHelper.setLineChart(lineChart, xAxisValues, yAxisValue, title, false);
            }
        } catch (Exception e) {
            lineChart.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick({R.id.tv_select_textbook,R.id.tv_class_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_textbook:       //选择学科
                showSubject();
                break;
            case R.id.tv_class_select:          //选择班级
                pvNoLinkOptions.show();
                break;
        }
    }

    /**
     * 选择科目
     */
    private AlertDialog alertDialog;

    public void showSubject() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_xk_list, null);
        builder.setView(view);
        builder.setTitle(R.string.section11);
        ListView lv_list = (ListView) view.findViewById(R.id.lv_list);
        adapter = new SelectKmAdapter(this, myTextbookBeanList);
        lv_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvSelectTextbook.setText(adapter.mDataSets.get(position).getCourseName());
                courseId = adapter.mDataSets.get(position).getCourseId();
                getRequestData(bj_id, courseId, grade);
                tab.getTabAt(0).select();
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 获取班级请求的数据
     */
    public void getClassRequestData() {
        HttpParams params = new HttpParams();
        //params.put("userId", "4");
        J.http().post(Urls.GET_CLASS_URL, ctx, params, new HttpCallback<Respond<List<MyClassBean>>>(ctx) {
            @Override
            public void success(Respond<List<MyClassBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<MyClassBean> myClassBeanList = res.getData();
                    bj_id = myClassBeanList.get(0).getClassId() + "";
                    grade = myClassBeanList.get(0).getGrade() + "";
                    tv_class_select.setText(myClassBeanList.get(0).getClassName());
                    getTextbookRequestData(bj_id);
                    initNoLinkOptionsPicker(myClassBeanList);
                }
            }
        });
    }

    private void initNoLinkOptionsPicker(List<MyClassBean> myClassBeanList) {
        final List<String> stringList = new ArrayList<>();
        final List<Integer> integerList = new ArrayList<>();
        final List<Integer> gradeList = new ArrayList<>();
        for (MyClassBean bean : myClassBeanList) {
            stringList.add(bean.getClassName());
            integerList.add(bean.getClassId());
            gradeList.add(bean.getGrade());
        }
        pvNoLinkOptions = new OptionsPickerView.Builder(StudyAnalyzeActivity.this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_class_select.setText(stringList.get(options1));
                bj_id = integerList.get(options1) + "";
                grade = gradeList.get(options1) + "";
                getTextbookRequestData(bj_id);
                tab.getTabAt(0).select();
            }
        }).setBackgroundId(0x00FFFFFF).build();
        pvNoLinkOptions.setNPicker(stringList, null, null);
    }

    /**
     * 获取科目的数据
     *
     * @param bj_id
     */
    public void getTextbookRequestData(final String bj_id) {
        showLog(bj_id + "---" + grade + "---" + courseId + "---获取科目的数据");
        HttpParams params = new HttpParams();
//        params.put("userId", 4);
        params.put("classId", bj_id);
        J.http().post(Urls.GET_SUBJECT_URL, ctx, params, new HttpCallback<Respond<List<MyTextbookBean>>>(ctx) {
            @Override
            public void success(Respond<List<MyTextbookBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    myTextbookBeanList = res.getData();
                    tvSelectTextbook.setText(myTextbookBeanList.get(0).getCourseName());
                    courseId = myTextbookBeanList.get(0).getCourseId();
                    getRequestData(bj_id, courseId, grade);
                }
            }
        });
    }

    /**
     * 获取学情分析所有的集合
     *
     * @param bj_id
     * @param courseId
     * @param grade
     */
    public void getRequestData(String bj_id, int courseId, String grade) {
        showLog("getRequestData" + bj_id + courseId + grade);
        SPUtil.putString(this, "classId", bj_id);
        SPUtil.putString(this, "courseId", courseId + "");
        SPUtil.putString(this, "grade", grade);
        HttpParams params = new HttpParams();
        params.put("grade", grade);
        params.put("classId", bj_id);
        params.put("courseId", courseId + "");
        J.http().post(Urls.STUDENT_ANALYSIS_URL, ctx, params, new HttpCallback<Respond<MyStudentAnalysisListBean>>(ctx) {
            @Override
            public void success(Respond<MyStudentAnalysisListBean> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    lineChart.setVisibility(View.VISIBLE);
                    if (res.getData() != null) {
                        MyStudentAnalysisListBean valueBean = res.getData();
                        if (valueBean.getPowerInfo() != null) {
                            MyStudentAnalysisListBean.PowerInfoBean powerInfoBeanList = valueBean.getPowerInfo();
                            tvCapacity.setText(powerInfoBeanList.getPoint() + "");
                            tvProvinceRanking.setText(powerInfoBeanList.getPRank() + "/" + powerInfoBeanList.getPCount());
                            tvGradeRanking.setText(powerInfoBeanList.getGRank() + "/" + powerInfoBeanList.getGCount());
                        }else {
                            tvCapacity.setText("");
                            tvProvinceRanking.setText("0/0");
                            tvGradeRanking.setText("0/0");
                        }
                        //获取难题解析
                        subjectBeanList = valueBean.getSubject();
                        sectionViewAdapter = new SectionViewAdapter(StudyAnalyzeActivity.this, subjectBeanList);
                        lvSectionPuzzle.setAdapter(sectionViewAdapter);
                        //获取知识点
                        knowBeanList = valueBean.getKnow();
                        flLayout.removeAllViews();
                        flLayout.addView(new KnowledgePointView(getApplicationContext(), knowBeanList));

                        //获取章节
                        structureBeanList = valueBean.getStructure();

                        //获取图表
                        MyStudentAnalysisListBean.PowerLineBean powerLine = valueBean.getPowerLine();
                        getChart(powerLine);
                    }
                }
            }
        });
    }
}
