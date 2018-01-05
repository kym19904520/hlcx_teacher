package com.up.study.ui.home;

import android.view.View;
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
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dell on 2017/7/25.
 */

public class KnowWeaknessAnalyzeActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.lv)
    ListView lv;

    private CommonAdapter<Know> lvAdapter;
    private List<Know> lvDataList = new ArrayList<Know>();

    HomeTaskModel taskModel;
    int studentId;

    @Override
    protected int getContentViewId() {
        return R.layout.act_know_weakness_analyze;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Map<String, Object> map =  getMap();
        taskModel = (HomeTaskModel) map.get("data");
        tvTopTitle.setText(taskModel.getTitle());
        studentId = (int) map.get("studentId");
        tvTitle.setText((String) map.get("studentName"));
        lvAdapter = new CommonAdapter<Know>(ctx, lvDataList, R.layout.item_know_weakness) {
            @Override
            public void convert(ViewHolder vh, Know item, int position) {
                TextView tv_no = vh.getView(R.id.tv_no);
                tv_no.setText(position+1+"");
                TextView tv_name = vh.getView(R.id.tv_name);
                tv_name.setText(lvDataList.get(position).getKnowledgeName());
            }
        };
        lv.setAdapter(lvAdapter);
        requestData();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    //获取知识薄弱点
    private void requestData() {
        lvDataList.clear();
        HttpParams params = new HttpParams();
        params.put("relationId", taskModel.getId());
        params.put("classsId", taskModel.getClassId());
        params.put("studentId", studentId);
        J.http().post(Urls.ZSBRD_FX, ctx, params, new HttpCallback<Respond<List<Know>>>(null) {
            @Override
            public void success(Respond<List<Know>> res, Call call, Response response, boolean isCache) {
                List<Know> temp = res.getData();
                if (temp!=null||temp.size()>0){
                    lv.setVisibility(View.VISIBLE);
                    HideEmptyView();
                    lvDataList.addAll(temp);
                    lvAdapter.notifyDataSetChanged();
                }
                else{
                    showEmptyView(null);
                }
            }
        });
    }
    private class Know extends BaseBean{
        private String knowledgeName;

        public String getKnowledgeName() {
            return knowledgeName;
        }

        public void setKnowledgeName(String knowledgeName) {
            this.knowledgeName = knowledgeName;
        }
    }
}
