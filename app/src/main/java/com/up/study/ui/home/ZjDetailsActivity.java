package com.up.study.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.adapter.ZjDetailsAdapter01;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.db.CollectDao;
import com.up.study.db.ZJBean;
import com.up.study.model.IdModel;
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
 * 智能组卷详情
 * Created by kym on 2017/8/31.
 */

public class ZjDetailsActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_next)
    TextView tv_next;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;
    @Bind(R.id.ll_no_class)
    LinearLayout llNoClass;
    @Bind(R.id.lv_list)
    ListView lvList;

    private String difficulty01, difficulty02, difficulty03;
    private String courseId;
    private ZjDetailsAdapter01 adapter;
    private Integer gradeId = null;
    private ArrayList<Map> resData01, resData02, resData03, resData04, resData05, resData06, resData07;
    private int number;
    private CollectDao dao;
    private ReceiverFinish receiverFinish;

    @Override
    protected int getContentViewId() {
        return R.layout.act_zj_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dao = new CollectDao(this);
        dao.delete1();
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.capacity_zj);
        Map<String, Object> map = getMap();
        difficulty01 = (String) map.get("difficulty01");
        difficulty02 = (String) map.get("difficulty02");
        difficulty03 = (String) map.get("difficulty03");
        gradeId = Integer.valueOf((String) map.get("gradeId"));
        courseId = (String) map.get("courseId");
        number = (int) map.get("number");
        showLog(gradeId + "---zj" + courseId + number);
        getRequestData();

        receiverFinish = new ReceiverFinish();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.finish.zj");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiverFinish, filter);
    }

    private void getRequestData() {
        HttpParams params = new HttpParams();
        params.put("gradeId", gradeId);
        params.put("courseId", courseId);
        if (difficulty01 == null || difficulty01.isEmpty()) {
            params.put("difficulty1", "0");
        } else {
            params.put("difficulty1", difficulty01);
        }
        if (difficulty02 == null || difficulty02.isEmpty()) {
            params.put("difficulty2", "0");
        } else {
            params.put("difficulty2", difficulty02);
        }
        if (difficulty03 == null || difficulty03.isEmpty()) {
            params.put("difficulty3", "0");
        } else {
            params.put("difficulty3", difficulty03);
        }
        J.http().post(Urls.CAPACITY_ZJ_DETAILS_URL, ctx, params, new HttpCallback<Respond<Map<String, Object>>>(ctx) {
            @Override
            public void success(Respond<Map<String, Object>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    Map<String, Object> map = res.getData();
//                    List<Map.Entry<String,Object>> list = new ArrayList<>(map.entrySet());
                    if (map != null) {
                        resData01 = (ArrayList<Map>) map.get("单选题");
                        resData02 = (ArrayList<Map>) map.get("判断题");
                        resData03 = (ArrayList<Map>) map.get("填空题");
                        resData04 = (ArrayList<Map>) map.get("多选题");
                        resData05 = (ArrayList<Map>) map.get("应用题");
                        resData06 = (ArrayList<Map>) map.get("解答题");
                        resData07 = (ArrayList<Map>) map.get("计算题");
                    }
                    if (resData01 != null) {
                        for (int i = 0; i < resData01.size(); i++) {
                            dao.add((String) resData01.get(i).get("title"), (String) resData01.get(i).get("subjectTypeName"),(int) ((Double) resData01.get(i).get("id")).longValue());
                        }
                    }
                    if (resData02 != null) {
                        for (int i = 0; i < resData02.size(); i++) {
                            dao.add((String) resData02.get(i).get("title"),(String) resData02.get(i).get("subjectTypeName"),(int) ((Double) resData02.get(i).get("id")).longValue());
                        }
                    }
                    if (resData03 != null) {
                        for (int i = 0; i < resData03.size(); i++) {
                            dao.add((String) resData03.get(i).get("title"),(String) resData03.get(i).get("subjectTypeName"),(int) ((Double) resData03.get(i).get("id")).longValue());
                        }
                    }
                    if (resData04 != null) {
                        for (int i = 0; i < resData04.size(); i++) {
                            dao.add((String) resData04.get(i).get("title"),(String) resData04.get(i).get("subjectTypeName"),(int) ((Double) resData04.get(i).get("id")).longValue());
                        }
                    }
                    if (resData05 != null) {
                        for (int i = 0; i < resData05.size(); i++) {
                            dao.add((String) resData05.get(i).get("title"),(String) resData05.get(i).get("subjectTypeName"),(int) ((Double) resData05.get(i).get("id")).longValue());
                        }
                    }
                    if (resData06 != null) {
                        for (int i = 0; i < resData06.size(); i++) {
                            dao.add((String) resData06.get(i).get("title"),(String) resData06.get(i).get("subjectTypeName"),(int) ((Double) resData06.get(i).get("id")).longValue());
                        }
                    }
                    if (resData07 != null) {
                        for (int i = 0; i < resData07.size(); i++) {
                            dao.add((String) resData07.get(i).get("title"),(String) resData07.get(i).get("subjectTypeName"),(int) ((Double) resData07.get(i).get("id")).longValue());
                        }
                    }
                    List<ZJBean> allList = dao.findAll();
                    for (ZJBean zjBean : allList){
                        showLog("组卷数据sql" + zjBean.getPaperId() + zjBean.getPaperName() + zjBean.getTypeName());
                    }
                    if (adapter == null) {
                        adapter = new ZjDetailsAdapter01(ZjDetailsActivity.this,allList,tv_next,tvNoData,llNoClass);
                        lvList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {}

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 110 && resultCode == 110)) {
            setResult(112);
            finish();
        }
    }

    @OnClick(R.id.tv_next)
    public void onViewClicked(View view) {
        List<ZJBean> listData = dao.findAll();
        String id = "";
        for (int i = 0;i < listData.size();i++){
            id +=listData.get(i).getPaperId()+ ",";
            Log.i("tag",listData.get(i).getPaperName() + "---" + listData.get(i).getPaperId());
        }
        HttpParams params = new HttpParams();
        params.put("subIds",id.substring(0,id.length()-1));
        J.http().post(Urls.SAVE_PARS, ctx, params, new HttpCallback<Respond<IdModel>>(ctx) {
            @Override
            public void success(Respond<IdModel> res, Call call, Response response, boolean isCache) {
                Map<String, Object> map = new HashMap<>();
                map.put("paperId",res.getData().getId());
                map.put("courseId",OnlineExerciseActivity.chooseSubject.getValue());
                map.put("code", 0);
                map.put("grade", gradeId);//年级
                showLog(gradeId + "--- "+ res.getData().getId() + "智能组卷");
                gotoActivityResult(ChooseClassActivity.class, map,110);
            }
        });
    }

    public class ReceiverFinish extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<ZJBean> allList = dao.findAll();
            adapter = new ZjDetailsAdapter01(ZjDetailsActivity.this, allList, tv_next, tv_next, llNoClass);
            lvList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiverFinish != null) {
            unregisterReceiver(receiverFinish);
        }
    }

}
