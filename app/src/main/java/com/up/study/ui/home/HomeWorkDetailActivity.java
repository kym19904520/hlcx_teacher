package com.up.study.ui.home;

import android.graphics.drawable.Drawable;
import android.view.View;
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
 * 作业详情-家庭作业
 */
public class HomeWorkDetailActivity extends BaseFragmentActivity {

    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.tv_class)
    TextView tvClass;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.tv_num_seq)
    TextView tvNumSeq;


    private CommonAdapter<orderData> lvAdapter;
    private List<orderData> lvDataList = new ArrayList<orderData>();

    private HomeTaskModel taskModel;

    @Override
    protected int getContentViewId() {
        return R.layout.act_homework_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        ivRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {


        /*lvDataList.add(null);
        lvDataList.add(null);
        lvDataList.add(null);
        lvDataList.add(null);
        lvDataList.add(null);
        lvAdapter = new CommonAdapter<String>(ctx, lvDataList, R.layout.item_stu_ranking) {
            @Override
            public void convert(ViewHolder vh, String item, int position) {

            }
        };
        lv.setAdapter(lvAdapter);*/

        Map<String, Object> map =  getMap();
        taskModel = (HomeTaskModel) map.get("data");
        tvTitle.setText(taskModel.getTitle());
        tvClass.setText(taskModel.getClassName());

        lvAdapter = new CommonAdapter<orderData>(ctx, lvDataList, R.layout.item_stu_ranking_homework) {
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
                    tv_ranking.setText(item.getSeq()+"");
                    tv_ranking.setCompoundDrawables(null, null, null, null);
                }
                TextView tv_name = vh.getView(R.id.tv_name);
                tv_name.setText(item.getStudentName());
                TextView tv_scode = vh.getView(R.id.tv_scode);
                tv_scode.setText(item.getCorrentRate() + "%");
            }
        };
        lv.setAdapter(lvAdapter);
        getDetail();

    }

    @Override
    public void onClick(View v) {
        if (v == ivRight) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("data", taskModel);
            gotoActivity(TestStatisticsActivity.class, map);
        }
    }

    //获取作业详情数据
    private void getDetail() {
        HttpParams params = new HttpParams();
        params.put("relationId", taskModel.getId());
        params.put("classsId", taskModel.getClassId());
        J.http().post(Urls.ZYXQ_JTZY, ctx, params, new HttpCallback<Respond<ResData>>(ctx) {
            @Override
            public void success(Respond<ResData> res, Call call, Response response, boolean isCache) {
                ResData temp = res.getData();
                tvCode.setText(temp.getAvgRate()+ "%");
                tvNumSeq.setText(temp.getSubmitTotal());
                lvDataList.addAll(temp.getResults());
                lvAdapter.notifyDataSetChanged();
            }
        });
    }
    private class ResData extends BaseBean {
        private List<orderData> results;
        private String submitTotal;
        private double avgRate;

        public String getSubmitTotal() {
            return submitTotal;
        }

        public void setSubmitTotal(String submitTotal) {
            this.submitTotal = submitTotal;
        }

        public List<orderData> getResults() {
            return results;
        }

        public void setResults(List<orderData> results) {
            this.results = results;
        }

        public double getAvgRate() {
            return avgRate;
        }

        public void setAvgRate(double avgRate) {
            this.avgRate = avgRate;
        }
    }

    /**
     * 学生排名
     */
    private class orderData extends BaseBean {
        private int classs_id;
        private String studentName;
        private int sid;
        private int seq;
        private double correntRate;//学生正确率

        public int getClasss_id() {
            return classs_id;
        }

        public void setClasss_id(int classs_id) {
            this.classs_id = classs_id;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public double getCorrentRate() {
            return correntRate;
        }

        public void setCorrentRate(double correntRate) {
            this.correntRate = correntRate;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }
    }

}
