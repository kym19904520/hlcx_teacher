package com.up.study.ui.home;

import android.view.View;
import android.widget.AdapterView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.adapter.MesAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.MesModel;
import com.up.study.weight.LoadListView;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 消息列表
 */
public class MessageActivity extends BaseFragmentActivity {

    private LoadListView dynamicList;
    private MesAdapter adapter;
    private List<MesModel> listDatas = new ArrayList<>();
    private int pageNo = 1;
    @Override
    protected int getContentViewId() {
        return R.layout.act_message;
    }

    @Override
    protected void initView() {
        dynamicList = (LoadListView)this.findViewById(R.id.lv);
        adapter = new MesAdapter(listDatas, this);
        dynamicList.setAdapter(adapter);
        dynamicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                gotoActivityWithBean(MessageDetailActivity.class,(MesModel)adapterView.getAdapter().getItem(position),null);
            }
        });
        setLoadLinster();
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        getMesList(false);
    }

    @Override
    public void onClick(View v) {
    }

    private void setLoadLinster() {
        dynamicList.setRefreshListener(new LoadListView.RefreshListener() {
            @Override
            public void refresh() {
                pageNo = 1;
                getMesList(true);
            }
        });
        dynamicList.setLoadListener(new LoadListView.LoadListener() {
            @Override
            public void onLoad() {
                pageNo += 1;
                getMesList(true);
            }
        });

    }


    //线上作业&试卷录入
    private void getMesList(boolean hideLoding) {
        HttpParams params = new HttpParams();
        params.put("position", 2);
        params.put("page", pageNo);
        params.put("rows", 10);
        J.http().post(Urls.GET_MES, ctx, params, new HttpCallback<Respond<List<MesModel>>>(ctx,hideLoding,true) {
            @Override
            public void success(Respond<List<MesModel>> res, Call call, Response response, boolean isCache) {
                List<MesModel> temp = res.getData();
                if (pageNo == 1) {//初始化或下拉刷新
                    listDatas.clear();
                    listDatas.addAll(temp);
                    if (dynamicList.isReFresh) {
                        dynamicList.refreshComplete();
                    }
                } else {//上拉加载
                    dynamicList.loadComplete();
                    if (temp.isEmpty()) {
                        pageNo--;
                    } else {
                        listDatas.addAll(temp);
                    }
                }

                if(listDatas.size()>0) {
                    adapter.notifyDataSetChanged();
                    bind(R.id.ll_empty).setVisibility(View.GONE);
                    dynamicList.setVisibility(View.VISIBLE);
                }
                else{
                    bind(R.id.ll_empty).setVisibility(View.VISIBLE);
                    dynamicList.setVisibility(View.GONE);
                }

            }
        });
    }


}
