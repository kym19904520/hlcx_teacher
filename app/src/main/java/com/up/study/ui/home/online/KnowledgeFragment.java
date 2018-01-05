package com.up.study.ui.home.online;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.base.BaseFragment;
import com.up.study.model.NameValue;
import com.up.study.params.Params;
import com.up.study.ui.home.ChooseQuestionActivity;
import com.up.study.ui.home.OnlineExerciseActivity;
import com.up.study.weight.ChooseTypeDialog;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:在线练习，知识点选择
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class KnowledgeFragment extends BaseFragment {

    private TextView tv01, tv02, tv03, tv04, tv05, tv06;
    private TextView[] tvs = new TextView[6];


    //知识点
    private List<NameValue> list1 = new ArrayList<>();
    //    private NameValue nameValue1 = null;
    //二级知识点
    private List<NameValue> list2 = new ArrayList<>();
    //    private NameValue nameValue2 = null;
    //三级知识点
    private List<NameValue> list3 = new ArrayList<>();
    //    private NameValue nameValue3 = null;
    //题型
    private List<NameValue> list4 = new ArrayList<>();
    //    private NameValue nameValue4 = null;
    //难度
    private List<NameValue> list5 = new ArrayList<>();
    //    private NameValue nameValue5 = null;
    //来源
    private List<NameValue> list6 = new ArrayList<>();
//    private NameValue nameValue6 = null;

    private NameValue[] nameValues = {null, null, null, null, null, null};


//    private String[] urls = {
//            Urls.GET_KNOWLEDGE,//章节
//            Urls.GET_PRO_TYPE,//题型
//            Urls.GET_DIFFICULTY,//难度
//            Urls.GET_SOURCE,//来源
//    };

    @Override
    protected int getContentViewId() {
        return R.layout.fra_knowledge;
    }

    @Override
    protected void initView() {
        tv01 = bind(R.id.tv_01);
        tv02 = bind(R.id.tv_02);
        tv03 = bind(R.id.tv_03);
        tv04 = bind(R.id.tv_04);
        tv05 = bind(R.id.tv_05);
        tv06 = bind(R.id.tv_06);
        tvs[0] = tv01;
        tvs[1] = tv02;
        tvs[2] = tv03;
        tvs[3] = tv04;
        tvs[4] = tv05;
        tvs[5] = tv06;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        bind(R.id.ll_01).setOnClickListener(this);
        bind(R.id.ll_02).setOnClickListener(this);
        bind(R.id.ll_03).setOnClickListener(this);
        bind(R.id.ll_04).setOnClickListener(this);
        bind(R.id.ll_05).setOnClickListener(this);
        bind(R.id.ll_06).setOnClickListener(this);
        bind(R.id.btn_next).setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 112 && resultCode == 112)) {
            getActivity().setResult(112);
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_01:
                loadKnowledge();
                break;
            case R.id.ll_02:
                if (nameValues[0] == null) {
                    showToast("无一级知识点");
                    return;
                }
                if (nameValues[0].getChildRen().isEmpty()) {
                    showToast("无二级知识点");
                    return;
                }
                choose(1, nameValues[0].getChildRen());
                break;
            case R.id.ll_03:
                if (nameValues[0] == null) {
                    showToast("无一级知识点");
                    return;
                }
                if (nameValues[0].getChildRen().isEmpty()) {
                    showToast("无二级知识点");
                    return;
                }
                if (nameValues[1].getChildRen().isEmpty()) {
                    showToast("无二级知识点");
                    return;
                }
                choose(2, nameValues[1].getChildRen());
                break;
            case R.id.ll_04:
                loadList4();
                break;
            case R.id.ll_05:
                loadList5();
                break;
            case R.id.ll_06:
                loadList6();
                break;
            case R.id.btn_next:
                next();
                break;
        }
    }

    private void next() {
        Map<String, Object> map = new HashMap<>();
        if (null == OnlineExerciseActivity.chooseSubject) {
            showToast("请先选择科目");
            return;
        }
        NameValue know = nameValues[0];
        if (null != nameValues[1]) {
            know = nameValues[1];
        }
        if (null != nameValues[2]) {
            know = nameValues[2];
        }
        if (know == null) {
            showToast("请选择知识点");
            return;
        }
        if (null == nameValues[5]) {
            showToast("请选择来源");
            return;
        }

        HttpParams params = Params.getKnowledgeParams(OnlineExerciseActivity.chooseSubject.getValue(), know, nameValues[3], nameValues[4], nameValues[5]);
        map.put("params", params);
        map.put("grade", null);
        gotoActivityResult(ChooseQuestionActivity.class, map, 112);
    }

    public void choose(final int index, final List<NameValue> nameValueList) {
        new ChooseTypeDialog(ctx, nameValueList).setChooseListener(new ChooseTypeDialog.ChooseListener() {
            @Override
            public void choose(NameValue nameValue) {
                if (index == 0){
                    tvs[1].setText(R.string.section11);
                    nameValues[1] = null;
                    tvs[2].setText(R.string.section11);
                    nameValues[2] = null;
                }else if (index == 1){
                    tvs[2].setText(R.string.section11);
                    nameValues[2] = null;
                }
                tvs[index].setText(nameValue.getName());
                nameValues[index] = nameValue;
            }
        }).show();
    }


    //知识点选择
    private void loadKnowledge() {
        if (OnlineExerciseActivity.chooseSubject == null) {
            showToast("请先选择学科");
            return;
        }
        J.http().post(Urls.GET_KNOWLEDGE, ctx, Params.getKnowledge(OnlineExerciseActivity.chooseSubject.getValue()), new HttpCallback<Respond<List<NameValue>>>(ctx) {
            @Override
            public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                list1 = res.getData();
                if (list1.isEmpty()) {
                    showToast("暂无知识点");
                    return;
                }
                choose(0, list1);
            }
        });
    }

    //题型
    public void loadList4() {
        J.http().post(Urls.GET_PRO_TYPE, ctx,
                null,
                new HttpCallback<Respond<List<NameValue>>>(ctx) {
                    @Override
                    public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                        list4 = res.getData();
                        if (list4.isEmpty()) {
                            showToast("暂无题型信息");
                            return;
                        }
                        choose(3, list4);
                    }
                });
    }

    //难度
    public void loadList5() {
        J.http().post(Urls.GET_DIFFICULTY, ctx,
                null,
                new HttpCallback<Respond<List<NameValue>>>(ctx) {
                    @Override
                    public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                        list5 = res.getData();
                        if (list5.isEmpty()) {
                            showToast("暂无难度信息");
                            return;
                        }
                        choose(4, list5);
                    }
                });
    }

    //来源
    public void loadList6() {
        list6.clear();
        J.http().post(Urls.GET_SOURCE, ctx,
                null,
                new HttpCallback<Respond<List<NameValue>>>(ctx) {
                    @Override
                    public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                        for (int i = 0;i<res.getData().size();i++){
                            if("平台".equals(res.getData().get(i).getName())){
                                list6.add(res.getData().get(i));
                            }
                        }
                        //list6 = res.getData();
                        if (list6.isEmpty()) {
                            showToast("暂无来源信息");
                            return;
                        }
                        choose(5, list6);
                    }
                });
    }
}
