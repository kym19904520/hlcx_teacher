package com.up.study.ui.home;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.model.BaseBean;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.HomeTaskModel;
import com.up.study.model.KnowModel;
import com.up.study.weight.OnItemClick;
import com.up.study.weight.SearchTipsGroupView;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作业详情-试卷录入
 */
public class TestInputDetailActivity extends BaseFragmentActivity implements OnItemClick {

    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.tv_class)
    TextView tvClass;
    @Bind(R.id.search_tips)
    SearchTipsGroupView search_tips;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.tv_knowledge_weakness)
    TextView tvKnowledgeWeakness;
    @Bind(R.id.tv_num_seq)
    TextView tvNumSeq;

    private List<String> zsdDataList = new ArrayList<>();
    private CommonAdapter<orderData> lvAdapter;
    private List<orderData> lvDataList = new ArrayList<orderData>();
    private HomeTaskModel taskModel;

    @Override
    protected int getContentViewId() {
        return R.layout.act_test_input_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        ivRight.setOnClickListener(this);
        tvKnowledgeWeakness.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Map<String, Object> map = getMap();
        taskModel = (HomeTaskModel) map.get("data");
        tvTitle.setText(taskModel.getTitle());
        tvClass.setText(taskModel.getClassName());

        lvAdapter = new CommonAdapter<orderData>(ctx, lvDataList, R.layout.item_stu_ranking) {
            @Override
            public void convert(ViewHolder vh, orderData item, int position) {
                TextView tv_ranking = vh.getView(R.id.tv_ranking);
                if (item.getSeq() == 1) {//金牌
                    Drawable image = getResources().getDrawable(R.mipmap.class_gz);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
                    tv_ranking.setCompoundDrawables(image, null, null, null);
                    tv_ranking.setText("");
                } else if (item.getSeq() == 2) {
                    Drawable image = getResources().getDrawable(R.mipmap.class_yj);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
                    tv_ranking.setCompoundDrawables(image, null, null, null);
                    tv_ranking.setText("");
                } else if (item.getSeq() == 3) {
                    Drawable image = getResources().getDrawable(R.mipmap.class_jj);
                    image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
                    tv_ranking.setCompoundDrawables(image, null, null, null);
                    tv_ranking.setText("");
                } else {
                    tv_ranking.setText(item.getSeq() + "");
                    tv_ranking.setCompoundDrawables(null, null, null, null);
                }
                TextView tv_name = vh.getView(R.id.tv_name);
                tv_name.setText(item.getStudentName());
                TextView tv_scode = vh.getView(R.id.tv_scode);
                tv_scode.setText(item.getPoint() + "");
            }
        };
        lv.setAdapter(lvAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("data", taskModel);
                map.put("studentName", lvDataList.get(position).getStudentName());
                map.put("studentId", lvDataList.get(position).getStudentId());
                gotoActivity(KnowWeaknessAnalyzeActivity.class, map);
            }
        });
        getDetail();
    }

    @Override
    public void onClick(View v) {
        if (v == ivRight) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", taskModel);
            gotoActivity(TestStatisticsActivity.class, map);
        } else if (v == tvKnowledgeWeakness) {

        }
    }

    //获取作业详情数据
    private void getDetail() {
        HttpParams params = new HttpParams();
        params.put("relationId", taskModel.getId());
        params.put("classsId", taskModel.getClassId());
        J.http().post(Urls.ZYXQ_SJLUR, ctx, params, new HttpCallback<Respond<ResData>>(ctx) {
            @Override
            public void success(Respond<ResData> res, Call call, Response response, boolean isCache) {
                ResData temp = res.getData();
                tvCode.setText(temp.getAvgPoint().getAvgPoint() + "");
                tvNumSeq.setText(temp.getSubmitTotal());
                //设置知识点的数据

                for (int i = 0; i < temp.getKnowData().size(); i++) {
                    zsdDataList.add(temp.getKnowData().get(i).getKnowledgeName());
                }
                if (zsdDataList != null) {
                    String[] array = zsdDataList.toArray(new String[zsdDataList.size()]);
                    search_tips.initViews(array, TestInputDetailActivity.this);
                }
                lvDataList.addAll(temp.getOrderData());
                lvAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(int position) {
    }

    private class ResData extends BaseBean {
        private List<KnowModel> knowData;
        private List<orderData> orderData;
        private avgPoint avgPoint;
        private String submitTotal;

        public List<KnowModel> getKnowData() {
            return knowData;
        }

        public void setKnowData(List<KnowModel> knowData) {
            this.knowData = knowData;
        }

        public List<TestInputDetailActivity.orderData> getOrderData() {
            return orderData;
        }

        public void setOrderData(List<TestInputDetailActivity.orderData> orderData) {
            this.orderData = orderData;
        }

        public TestInputDetailActivity.avgPoint getAvgPoint() {
            return avgPoint;
        }

        public void setAvgPoint(TestInputDetailActivity.avgPoint avgPoint) {
            this.avgPoint = avgPoint;
        }

        public String getSubmitTotal() {
            return submitTotal;
        }

        public void setSubmitTotal(String submitTotal) {
            this.submitTotal = submitTotal;
        }
    }

    private class avgPoint extends BaseBean {
        private double avgPoint;

        public double getAvgPoint() {
            return avgPoint;
        }

        public void setAvgPoint(double avgPoint) {
            this.avgPoint = avgPoint;
        }
    }

    /**
     * 学生排名
     */
    private class orderData extends BaseBean {
        private int studentId;
        private String studentName;
        private int seq;
        private double point;

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public double getPoint() {
            return point;
        }

        public void setPoint(double point) {
            this.point = point;
        }
    }
}
