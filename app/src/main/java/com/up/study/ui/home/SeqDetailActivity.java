package com.up.study.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.StudyUtils;
import com.up.common.widget.MyListView;
import com.up.study.adapter.ErrorCauseAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.ErrorModel;
import com.up.study.model.HomeTaskModel;
import com.up.study.model.SeqModel;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.up.teacher.R.id.lv_error;

/**
 * Created by dell on 2017/7/25.
 * 试题详情
 */

public class SeqDetailActivity extends BaseFragmentActivity {

    @Bind(lv_error)
    MyListView lvError;
    @Bind(R.id.recylist)
    RecyclerView recylist;
    @Bind(R.id.mlv)
    MyListView mlv;
    @Bind(R.id.tv_error_num)
    TextView tvErrorNum;
    @Bind(R.id.tv_error_rate)
    TextView tvErrorRate;
    @Bind(R.id.tv_seq)
    TextView tvSeq;

    private ErrorCauseAdapter adapter;
    private List<ErrorModel> dataList = new ArrayList<ErrorModel>();

    private HomeTaskModel taskModel;
    private SeqModel seqModel;
    private int total;//总题数

    @Override
    protected int getContentViewId() {
        return R.layout.act_seq_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

        Map<String, Object> map = getMap();
        taskModel = (HomeTaskModel) map.get("data");
        seqModel = (SeqModel) map.get("seqModel");
        total = (int) map.get("total");
        tvSeq.setText(seqModel.getSeq()+"/"+total);
        tvErrorNum.setText(seqModel.getWrongTotal() + "次");
        if (seqModel.getWrongTotal() == 0){
            lvError.setVisibility(View.GONE);
        }
        tvErrorRate.setText(seqModel.getWrongRate() + "%");
        StudyUtils.initSeq(this, this, seqModel, 2);
        /*String mesJson = "[{\"id\":\"8\",\"createDate\":null,\"modifyDate\":null,\"status\":\"0\",\"cuid\":\"2\",\"attached\":\"[{\\\"id\\\":1,\\\"url\\\":\\\"http://hlcximg.oss-cn-hangzhou.aliyuncs.com/user-dir/73111b5f-6f76-4c50-9af7-61fb72caf9fd.jpg\\\"},{\\\"id\\\":1,\\\"url\\\":\\\"http://hlcximg.oss-cn-hangzhou.aliyuncs.com/user-dir/73111b5f-6f76-4c50-9af7-61fb72caf9fd.jpg\\\"},{\\\"id\\\":1,\\\"url\\\":\\\"http://hlcximg.oss-cn-hangzhou.aliyuncs.com/user-dir/73111b5f-6f76-4c50-9af7-61fb72caf9fd.jpg\\\"}]\",\"content\":\"B,知识点不懂\",\"name\":\"陈涵\",\"major\":\"数学\",\"head\":null,\"urlList\":null},{\"id\":\"7\",\"createDate\":null,\"modifyDate\":null,\"status\":\"0\",\"cuid\":\"2\",\"attached\":\"[{\\\"id\\\":1,\\\"url\\\":\\\"http://hlcximg.oss-cn-hangzhou.aliyuncs.com/user-dir/692d8995-5623-41a8-9c82-6e44bc3b1b64.jpg\\\"}]\",\"content\":\"今天的作业是黑板作业3题\",\"name\":\"陈涵\",\"major\":\"数学\",\"head\":null,\"urlList\":null},{\"id\":\"4\",\"createDate\":null,\"modifyDate\":null,\"status\":\"0\",\"cuid\":\"2\",\"attached\":\"[{\\\"id\\\":1,\\\"url\\\":\\\"http://hlcximg.oss-cn-hangzhou.aliyuncs.com/user-dir/2fb93444-88bf-4874-ac24-789f086dcb92.jpg\\\"}]\",\"content\":\"今天的作业是黑板作业3题asdasdadasdasdasda\",\"name\":\"陈涵\",\"major\":\"数学\",\"head\":null,\"urlList\":null},{\"id\":\"2\",\"createDate\":null,\"modifyDate\":null,\"status\":\"0\",\"cuid\":\"2\",\"attached\":\"[{\\\"id\\\":2,\\\"url\\\":\\\"http://hlcximg.oss-cn-hangzhou.aliyuncs.com/user-dir/73111b5f-6f76-4c50-9af7-61fb72caf9fd.jpg\\\"}]\",\"content\":\"今天的作业是黑板作业3题\",\"name\":\"陈涵\",\"major\":\"数学\",\"head\":null,\"urlList\":null}]";

        Type type = new TypeToken<List<MesModel>>() {
        }.getType();
        List<MesModel> mesList = new Gson().fromJson(mesJson, type);
        dataList.addAll(mesList);*/
        adapter = new ErrorCauseAdapter(dataList, this);
        lvError.setAdapter(adapter);

        requestData();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 请求试卷统计数据
     */
    private void requestData() {
        dataList.clear();
        HttpParams params = new HttpParams();
        params.put("relationId", taskModel.getId());
        params.put("classsId", taskModel.getClassId());
        params.put("relationType", taskModel.getRelationType());
        params.put("subjectId", seqModel.getSubjectId());
        J.http().post(Urls.ZYXQ_SJXX, ctx, params, new HttpCallback<Respond<List<ErrorModel>>>(ctx) {
            @Override
            public void success(Respond<List<ErrorModel>> res, Call call, Response response, boolean isCache) {
                List<ErrorModel> temp = res.getData();
                dataList.addAll(temp);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
