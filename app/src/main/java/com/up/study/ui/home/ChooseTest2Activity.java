package com.up.study.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.ExerTestModel;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class ChooseTest2Activity extends BaseFragmentActivity {

    private ImageView ivSort01, ivSort02, ivSort03;
    private ListView lv;
    private Button btnSure;
    private LinearLayout ll_empty;
    private TextView tv_empty_text;

    private ImageView[] ivSort = new ImageView[3];
    private String[] sort = {"asc", "asc", "asc"};
    private String[] sortType = {"count", "difficulty", "per"};
    private String order = "per";//错误率
    private String orderBy = "desc";

    private String subIds;


    private List<ExerTestModel> list = new ArrayList<>();
    private CommonAdapter<ExerTestModel> adapter;

    private HttpParams params;
    private Integer grade = null;
    private String type = null;

    private int num = 0;


    @Override
    protected int getContentViewId() {
        return R.layout.act_exer_test;
    }

    @Override
    protected void initView() {
        ivSort01 = bind(R.id.iv_sort1);
        ivSort02 = bind(R.id.iv_sort2);
        ivSort03 = bind(R.id.iv_sort3);
        ivSort[0] = ivSort01;
        ivSort[1] = ivSort02;
        ivSort[2] = ivSort03;

        lv = bind(R.id.lv);
        ll_empty = bind(R.id.ll_empty);
        tv_empty_text = bind(R.id.tv_empty_text);

        btnSure = bind(R.id.btn_sure);
    }

    @Override
    protected void initData() {
        Map<String, Object> map = getMap();
        params = (HttpParams) map.get("params");
        grade = (Integer) map.get("grade");
        adapter = new CommonAdapter<ExerTestModel>(ctx, list, R.layout.item__lv_exer_test) {
            @Override
            public void convert(ViewHolder vh, ExerTestModel item, final int position) {
                LinearLayout llItem = vh.getView(R.id.ll_item);
                llItem.setSelected(item.isSelect());
                vh.setText(R.id.tv_time, item.getYear());
                vh.setText(R.id.tv_name, item.getTitle());
                vh.setText(R.id.tv_count, item.getCount() + "");
                vh.setText(R.id.tv_ratio, item.getPer());
                ImageView iv_difficulty = vh.getView(R.id.iv_difficulty);
                ImageView iv_difficulty01 = vh.getView(R.id.iv_difficulty01);
                ImageView iv_difficulty02 = vh.getView(R.id.iv_difficulty02);
                switch (item.getDifficulty()) {
                    case 1:
                        iv_difficulty.setImageResource(R.mipmap.my_xx01);
                        break;
                    case 2:
                        iv_difficulty.setImageResource(R.mipmap.my_xx01);
                        iv_difficulty01.setImageResource(R.mipmap.my_xx01);
                        break;
                    case 3:
                        iv_difficulty.setImageResource(R.mipmap.my_xx01);
                        iv_difficulty01.setImageResource(R.mipmap.my_xx01);
                        iv_difficulty02.setImageResource(R.mipmap.my_xx01);
                        break;
                }

            }
        };
        lv.setAdapter(adapter);
        selectSort(2);
        bind(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    protected void initEvent() {
        bind(R.id.btn_sure).setOnClickListener(this);
        bind(R.id.ll_sort1).setOnClickListener(this);
        bind(R.id.ll_sort2).setOnClickListener(this);
        bind(R.id.ll_sort3).setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int k = 0; k < list.size(); k++) {
                    list.get(k).setSelect(false);
                }
                list.get(i).setSelect(true);
                adapter.NotifyDataChanged(list);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 110 && resultCode == 110)) {
            setResult(112);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_sort1:
                selectSort(0);
                break;
            case R.id.ll_sort2:
                selectSort(1);
                break;
            case R.id.ll_sort3:
                selectSort(2);
                break;
            case R.id.btn_sure:
                next();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    private void selectSort(int index) {
        String s = sort[index];
        sort[index] = sort[index].equals("asc") ? "desc" : "asc";
        for (int i = 0; i < 3; i++) {
            ivSort[i].setImageResource(R.mipmap.icon_sort_null);
        }
        ivSort[index].setImageResource(sort[index].equals("asc") ? R.mipmap.icon_sort_up : R.mipmap.icon_sort_down);
        order = sortType[index];
        orderBy = sort[index];
        params.put("order", order);
        params.put("orderBy", orderBy);
        loadTestInfo();
    }

    private void loadTestInfo() {
        list.clear();
        String url = Urls.QUERY_SUBUJECT_E;
        J.http().post(url, ctx, params, new HttpCallback<Respond<List<ExerTestModel>>>(ctx) {
            @Override
            public void success(Respond<List<ExerTestModel>> res, Call call, Response response, boolean isCache) {
                if (res.getData() != null) {
                    if (res.getData().size() <= 0){
                        lv.setVisibility(View.GONE);
                        ll_empty.setVisibility(View.VISIBLE);
                        tv_empty_text.setText("没有相应的试卷~");
                    }else {
                        lv.setVisibility(View.VISIBLE);
                        ll_empty.setVisibility(View.GONE);
                    }
                    list.addAll(res.getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void next() {
        int testId = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelect()) {
                testId = list.get(i).getId();
            }
        }
        if(testId==0){
            showToast("请选择试卷");
            return;
        }
        Map<String, Object> map = new HashMap<>();
//                map.put("test", testModel);
        map.put("paperId", testId);//试卷ID
        map.put("courseId", OnlineExerciseActivity.chooseSubject.getValue());//科目
        map.put("grade", grade);//年级
        map.put("code", 110);//
        gotoActivityResult(ChooseClassActivity.class, map, 110);


    }

    private void choose() {
    }

}
