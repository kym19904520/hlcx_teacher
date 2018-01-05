package com.up.study.ui.home;

import android.view.View;
import android.widget.AdapterView;
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
import com.up.common.utils.StudyUtils;
import com.up.common.widget.MyListView;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.HomeTaskModel;
import com.up.study.model.SeqModel;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dell on 2017/7/24.
 * 试卷统计
 */

public class TestStatisticsActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_error_rate)
    TextView tvErrorRate;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.tv_right)
    TextView tvRight;

    private CommonAdapter<SubTypeModel> lvAdapter;
    private List<SubTypeModel>  lvDataList = new ArrayList<>();

    private HomeTaskModel taskModel;
    private int queryType=0;//查看类型 0：全部 1：错题

    private int seqTotal = 0;//题目总数
    @Override
    protected int getContentViewId() {
        return R.layout.act_test_statistics;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvLeft.setSelected(true);
    }

    @Override
    protected void initData() {
        Map<String, Object> map =  getMap();
        taskModel = (HomeTaskModel) map.get("data");
        lvAdapter = new CommonAdapter<SubTypeModel>(ctx, lvDataList, R.layout.item_test_subtype) {
            @Override
            public void convert(ViewHolder vh, final SubTypeModel item, int position) {
                MyListView mlv = vh.getView(R.id.mlv);
                List<SeqModel> seqList = item.getSeqList();
                TextView tv = vh.getView(R.id.tv_sub_type);
                tv.setText(item.getSubjectTypeName());
                TextView tv_num = vh.getView(R.id.tv_num);
                tv_num.setText("（"+seqList.size()+"）");
                CommonAdapter<SeqModel> adapter = new CommonAdapter<SeqModel>(ctx, seqList, R.layout.item_test_statistics) {
                    @Override
                    public void convert(ViewHolder vh, SeqModel seq, int position) {
                        StudyUtils.initSeq(TestStatisticsActivity.this,vh,seq,1);
                        TextView tv_seq = vh.getView(R.id.tv_seq);//题号/总题数
                        tv_seq.setText(seq.getSeq()+"/"+seqTotal);
                        TextView tv_error_num = vh.getView(R.id.tv_error_num);
                        tv_error_num.setText(seq.getWrongTotal()+"次");
                        TextView tv_error_rate = vh.getView(R.id.tv_error_rate);
                        tv_error_rate.setText(seq.getWrongRate()+"%");
                        MyListView lv = vh.getView(R.id.mlv);
                        lv.setClickable(false);
                        lv.setPressed(false);
                        lv.setEnabled(false);
                    }
                };
                mlv.setAdapter(adapter);
                mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("data", taskModel);
                        map.put("seqModel",item.getSeqList().get(position));
                        map.put("total",seqTotal);
                        gotoActivity(SeqDetailActivity.class,map);
                    }
                });
            }
        };
        lv.setAdapter(lvAdapter);

        requestData();
    }

    /**
     * 请求试卷统计数据
     */
    private void requestData() {
        lvDataList.clear();
        seqTotal = 0;
        HttpParams params = new HttpParams();
        params.put("relationId", taskModel.getId());
        params.put("classsId", taskModel.getClassId());
        params.put("relationType", taskModel.getRelationType());
        params.put("queryType", queryType);
        J.http().post(Urls.ZYXQ_SJTj, ctx, params, new HttpCallback<Respond<resData>>(ctx) {
            @Override
            public void success(Respond<resData> res, Call call, Response response, boolean isCache) {
                resData temp = res.getData();
                if(temp!=null) {
                    lvDataList.addAll(temp.getResults());
                    lvAdapter.notifyDataSetChanged();
                    tvErrorRate.setText(temp.getAvgPont() + "%");

                    for (int i = 0;i<temp.getResults().size();i++){
                        seqTotal += temp.getResults().get(i).getSeqList().size();
                    }
                }
            }
        });
    }

    @Override
    protected void initEvent() {
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.tv_left, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                initAll();
                break;
            case R.id.tv_right:
                initError();
                break;
        }
    }

    private void initAll() {
        queryType = 0;
        tvLeft.setSelected(true);
        tvRight.setSelected(false);
        requestData();
    }

    private void initError() {
        queryType = 1;
        tvLeft.setSelected(false);
        tvRight.setSelected(true);
        requestData();
    }

    private class resData extends BaseBean{
        List<SubTypeModel> results;//题目列表
        private double avgPont;//平均分错误率

        public List<SubTypeModel> getResults() {
            return results;
        }

        public void setResults(List<SubTypeModel> results) {
            this.results = results;
        }

        public double getAvgPont() {
            return avgPont;
        }

        public void setAvgPont(double avgPont) {
            this.avgPont = avgPont;
        }
    }

    private class SubTypeModel extends BaseBean{
        private List<SeqModel> seqList;//题目列表
        private int total;//题目总数
        private String subjectTypeName;//题目类型名称

        public List<SeqModel> getSeqList() {
            return seqList;
        }

        public void setSeqList(List<SeqModel> seqList) {
            this.seqList = seqList;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getSubjectTypeName() {
            return subjectTypeName;
        }

        public void setSubjectTypeName(String subjectTypeName) {
            this.subjectTypeName = subjectTypeName;
        }
    }
}
