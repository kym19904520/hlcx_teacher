package com.up.study.ui.home;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;
import com.up.common.J;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.StudyUtils;
import com.up.common.widget.MyListView;
import com.up.study.TApplication;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.IdModel;
import com.up.study.model.NameValue;
import com.up.study.model.SeqModel;
import com.up.study.weight.ChooseDialog;
import com.up.study.weight.LoadListView;
import com.up.teacher.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO: 线上作业-选择题目
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class ChooseQuestionActivity extends BaseFragmentActivity {

    private ImageView ivSort01, ivSort02, ivSort03;
    private LoadListView lv;
    private Button btnSure;
    private LinearLayout ll_empty;
    private TextView tv_empty_text;

    private ImageView[] ivSort = new ImageView[3];
    private String[] sort = {"asc", "asc", "asc"};
    private String[] sortType = {"subjectNum", "wrongNum", "per"};
    private String order = "per";//错误率
    private String orderBy = "desc";//错误率

    private String subIds="";


    private List<SeqModel> list = new ArrayList<>();
    private CommonAdapter<SeqModel> adapter;

    private HttpParams params;
    private Integer grade = null;
    private String type = null;

    private int num = 0;

    private int page=1;//（每页20个，默认1）

    //废弃
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111) {
                subIds="";
                num = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        num++;
                        subIds += list.get(i).getId() + ",";
                    }
                }
                if (subIds.endsWith(",")) {
                    subIds = subIds.substring(0,subIds.length() - 1);
                }
                showLog("subIds="+subIds);
                btnSure.setText("已选择" + num + "题，确认");
            }

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.act_choose_question;
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

        if(TApplication.getInstant().isClear()){
            TApplication.getInstant().setSubIds("");
        }
        else{
            params.put("isClear",false);
            subIds=TApplication.getInstant().getSubIds();
            num = subIds.split(",").length;
            btnSure.setText("已选择" + num + "题，确认");
            showLog("之前保留的subIds="+subIds);
        }

        try {
            grade = (Integer) map.get("grade");
        } catch (Exception e) {

        }
        try {
            type = (String) map.get("type");
        } catch (Exception e) {

        }


        adapter = new CommonAdapter<SeqModel>(ctx, list, R.layout.item_lv_question) {
            @Override
            public void convert(ViewHolder vh, final SeqModel seq, final int position) {
                StudyUtils.initSeq(ChooseQuestionActivity.this, vh, seq, 1);
                TextView tv_value01 = vh.getView(R.id.tv_value01);
                tv_value01.setText((seq.getSubjectNum() == null ? 0 : seq.getSubjectNum()) + "次");
                TextView tv_value02 = vh.getView(R.id.tv_value02);
                tv_value02.setText((seq.getWrongNum() == null ? 0 : seq.getWrongNum()) + "次");
                TextView tv_value03 = vh.getView(R.id.tv_value03);
                tv_value03.setText((seq.getPer() == null ? 0 : seq.getPer()) + "%");
                vh.setText(R.id.tv_seq, position + 1 + "/" + list.size());
                MyListView lv = vh.getView(R.id.mlv);
                lv.setClickable(false);
                lv.setPressed(false);
                lv.setEnabled(false);
                ImageView ivSelect = vh.getView(R.id.iv_select);
                ivSelect.setSelected(seq.isSelect());
                ivSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.get(position).setSelect(!list.get(position).isSelect());
                        adapter.NotifyDataChanged(list);
                        //handler.sendEmptyMessage(111);
                        if (list.get(position).isSelect()){//选中
                            showLog("+++++++++++++++++++++++");
                            num++;
                            subIds += list.get(position).getId() + ",";
                        }
                        else{
                            showLog("----------------------");
                            num--;
                            String[] subs = subIds.split(",");
                            subIds = "";
                            for (int i = 0;i<subs.length;i++){
                                if (!subs[i].equals(list.get(position).getId())){
                                    subIds += subs[i] + ",";
                                }
                            }
                        }
                       /* if (subIds.endsWith(",")) {
                            subIds = subIds.substring(0,subIds.length() - 1);
                        }*/
                        showLog("subIds="+subIds);
                        btnSure.setText("已选择" + num + "题，确认");
                    }
                });
            }
        };
        lv.setAdapter(adapter);

        selectSort(2);
    }

    @Override
    protected void initEvent() {
        bind(R.id.btn_sure).setOnClickListener(this);
        bind(R.id.ll_sort1).setOnClickListener(this);
        bind(R.id.ll_sort2).setOnClickListener(this);
        bind(R.id.ll_sort3).setOnClickListener(this);
        bind(R.id.tv_back).setOnClickListener(this);
        bind(R.id.iv_daochu).setOnClickListener(this);
        lv.setRefreshListener(new LoadListView.RefreshListener() {
            @Override
            public void refresh() {
                page = 1;
                loadTestInfo();
            }
        });
        lv.setLoadListener(new LoadListView.LoadListener() {
            @Override
            public void onLoad() {
                page += 1;
                loadTestInfo();
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
                TApplication.getInstant().setSubIds(subIds);
                TApplication.getInstant().setClear(false);
                finish();
                break;
            case R.id.iv_daochu:
                List<NameValue> list = new ArrayList<>();
                NameValue nameValue1 = new NameValue();
                nameValue1.setName("图片");
                nameValue1.setValue(1);
                NameValue nameValue2 = new NameValue();
                nameValue2.setName("WORD");
                nameValue2.setValue(2);
                list.add(nameValue1);
                list.add(nameValue2);
                new ChooseDialog(ctx, list).setChooseListener(new ChooseDialog.ChooseListener() {
                    @Override
                    public void choose(NameValue nameValue) {
                        importImg(nameValue.getValue() == 1);
                    }
                }).show();
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
        //list.clear();
        String url = Urls.QUERY_SUBUJECT;
        if (!TextUtils.isEmpty(type)) {//废弃
            if (type.equals("exer")) {
                url = Urls.QUERY_SUBUJECT_E;
            }
        }
        params.put("page", page);
        params.put("rows", 10);
        J.http().post(url, ctx, params, new HttpCallback<Respond<List<SeqModel>>>(ctx) {
            @Override
            public void success(Respond<List<SeqModel>> res, Call call, Response response, boolean isCache) {
                List<SeqModel> temp = res.getData();
                if (temp.size() <= 0){
                    ll_empty.setVisibility(View.VISIBLE);
                    tv_empty_text.setText("没有相应的试题~");
                    lv.setVisibility(View.GONE);
                }else {
                    lv.setVisibility(View.VISIBLE);
                    ll_empty.setVisibility(View.GONE);
                }
                if (page == 1) {//初始化或下拉刷新
                    list.clear();
                    if (temp!=null&&temp.size()>0){
                        list.addAll(temp);
                    }
                    if (lv.isReFresh) {
                        lv.refreshComplete();
                    }
                } else {//上拉加载
                    lv.loadComplete();
                    if (temp==null||temp.size()==0) {
                        page--;
                    } else {
                        list.addAll(temp);
                    }
                }
                for (int i=0;i<list.size();i++){
                    if(subIds.contains(list.get(i).getId())){
                        list.get(i).setSelect(true);
                    }
                }
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void next() {
        if (TextUtils.isEmpty(subIds)||num==0) {
            showToast("请选择试题");
            return;
        }
        if (subIds.endsWith(",")) {
            subIds = subIds.substring(0,subIds.length() - 1);
        }
        HttpParams httpParams = new HttpParams();
        httpParams.put("subIds", subIds);
        J.http().post(Urls.SAVE_PARS, ctx, httpParams, new HttpCallback<Respond<IdModel>>(ctx) {
            @Override
            public void success(Respond<IdModel> res, Call call, Response response, boolean isCache) {
                Map<String, Object> map = new HashMap<>();
//                map.put("test", testModel);
                map.put("paperId", res.getData().getId());//试卷ID
                map.put("courseId", OnlineExerciseActivity.chooseSubject.getValue());//科目
                if (grade != null) {
                    map.put("grade", grade);//年级
                }
                map.put("code", 110);//
                gotoActivityResult(ChooseClassActivity.class, map, 110);

                TApplication.getInstant().setSubIds("");
                TApplication.getInstant().setClear(true);

            }
        });

    }

    //导出图片
    private void importImg(boolean isImage) {
        HttpParams params = new HttpParams();
        params.put("ids", subIds);

        OkGo.get(isImage ? Urls.IMAGE_LOAD : Urls.DOC_LOAD)//
                .tag(this)//
                .params(params)
                .execute(new FileCallback(new Date().getTime() + (isImage ? ".png" : ".doc")) {  //文件下载时，可以指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        showLog(file.getName());
//                        showToast("下载完成，图片已经保存在sdcard/download文件下");
                        Toast.makeText(ctx, "下载完成，图片已经保存在sdcard/download文件下", Toast.LENGTH_LONG).show();
                        hideLoading();
                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        initLoadingDialog();
                        showLoading("下载中...");
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                    }
                });
    }


}
