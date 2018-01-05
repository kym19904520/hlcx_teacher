package com.up.study.ui.home.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.base.BaseFragment;
import com.up.study.model.Login;
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
 * TODO:在线练习，章节选择
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class SectionFragment extends BaseFragment {

    private TextView tv01, tv02, tv03, tv04, tv05, tv06, tv07, tv032, tv033;


    private TextView[] tvs = new TextView[9];

    private String[] urls = {
            Urls.GET_PUBLISHER,//出版社
            Urls.GET_GRADES,//年级
            Urls.GET_SECTION,//章节
            Urls.GET_PRO_TYPE,//题型
            Urls.GET_DIFFICULTY,//难度
            Urls.GET_TYPE,//类型
            Urls.GET_SOURCE,//来源
    };

    //出版社
    private List<NameValue> list1 = new ArrayList<>();
    //    private NameValue nameValue1 = null;
    //年级
    private List<NameValue> list2 = new ArrayList<>();
    //    private NameValue nameValue2 = null;
    //章节
    private List<NameValue> list3 = new ArrayList<>();
    //    private NameValue nameValue31 = null;
    //二级章节
    private List<NameValue> list32 = new ArrayList<>();
    //    private NameValue nameValue32 = null;
    //三级章节
    private List<NameValue> list33 = new ArrayList<>();
//    private NameValue nameValue33 = null;

    //    private NameValue nameValue3 = null;
    //题型
    private List<NameValue> list4 = new ArrayList<>();
    //    private NameValue nameValue4 = null;
    //难度
    private List<NameValue> list5 = new ArrayList<>();
    //    private NameValue nameValue5 = null;
    //类型
    private List<NameValue> list6 = new ArrayList<>();
    //    private NameValue nameValue6 = null;
    //来源
    private List<NameValue> list7 = new ArrayList<>();
//    private NameValue nameValue7 = null;

    private NameValue[] nameValues = {null, null, null, null, null, null, null, null, null};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showLog("SectionFragment  onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fra_section_select;
    }

    @Override
    protected void initView() {
        tv01 = bind(R.id.tv_01);
        tv02 = bind(R.id.tv_02);
        tv03 = bind(R.id.tv_03);
        tv04 = bind(R.id.tv_04);
        tv05 = bind(R.id.tv_05);
        tv06 = bind(R.id.tv_06);
        tv07 = bind(R.id.tv_07);

        tv032 = bind(R.id.tv_032);
        tv033 = bind(R.id.tv_033);

        tvs[0] = tv01;
        tvs[1] = tv02;
        tvs[2] = tv03;
        tvs[3] = tv04;
        tvs[4] = tv05;
        tvs[5] = tv06;
        tvs[6] = tv07;
        tvs[7] = tv032;
        tvs[8] = tv033;

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        bind(R.id.btn_next).setOnClickListener(this);
        bind(R.id.ll_01).setOnClickListener(this);
        bind(R.id.ll_02).setOnClickListener(this);
        bind(R.id.ll_03).setOnClickListener(this);
        bind(R.id.ll_04).setOnClickListener(this);
        bind(R.id.ll_05).setOnClickListener(this);
        bind(R.id.ll_06).setOnClickListener(this);
        bind(R.id.ll_07).setOnClickListener(this);
        bind(R.id.ll_033).setOnClickListener(this);
        bind(R.id.ll_032).setOnClickListener(this);
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
                loadList1();
                break;
            case R.id.ll_02:
                loadList2();
                break;
            case R.id.ll_03:
                loadList3();
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
            case R.id.ll_07:
                loadList7();
                break;
            case R.id.ll_032:
                choose(7, nameValues[2].getChildRen());
                break;
            case R.id.ll_033:
                choose(8, nameValues[7].getChildRen());
                break;

            case R.id.btn_next:
                next();
                break;
        }
    }

    private void next() {
        Map<String, Object> map = new HashMap<>();
        /**
         * 章节筛选参数
         *
         * @param press       出版社1
         * @param grade       年段1
         * @param relativeId  章节Id2
         * @param subjectType 题型2
         * @param difficulty  难度2
         * @param uploadType  类型2
         * @param source      来源1
         * @return
         */
        if (null == OnlineExerciseActivity.chooseSubject) {
            showToast("请选择科目");
            return;
        }
        if (nameValues[0] == null) {
            showToast("请选择出版社");
            return;
        }
        if (null == nameValues[1]) {
            showToast("请选择年段");
            return;
        }

        if (null == nameValues[2]) {
            showToast("请选择章节");
            return;
        }
        if (null == nameValues[6]) {
            showToast("请选择来源");
            return;
        }
        HttpParams params = Params.getSectionParams(OnlineExerciseActivity.chooseSubject.getValue(), nameValues[0], nameValues[1], nameValues[2], nameValues[3], nameValues[4], nameValues[5], nameValues[6]);
        map.put("params", params);
        map.put("grade",nameValues[1].getValue());
        gotoActivityResult(ChooseQuestionActivity.class, map, 112);
    }

    public void choose(final int index, List<NameValue> nameValueList) {
        new ChooseTypeDialog(ctx, nameValueList).setChooseListener(new ChooseTypeDialog.ChooseListener() {
            @Override
            public void choose(NameValue nameValue) {
                if (index == 1){
                    tvs[0].setText(R.string.section11);
                    nameValues[0] = null;
                    tvs[2].setText(R.string.section11);
                    nameValues[2] = null;
                }else if (index == 0){
                    tvs[2].setText(R.string.section11);
                    nameValues[2] = null;
                }
                tvs[index].setText(nameValue.getName());
                nameValues[index] = nameValue;

                if (index == 2) {//一级章节获取
                    nameValues[2] = nameValue;
                    if (nameValues[index].getChildRen() != null) {
                        if (!nameValues[index].getChildRen().isEmpty()) {
                            bind(R.id.ll032).setVisibility(View.VISIBLE);
                            bind(R.id.ll033).setVisibility(View.GONE);
                            return;
                        }
                    }
                    bind(R.id.ll032).setVisibility(View.GONE);
                    bind(R.id.ll033).setVisibility(View.GONE);
                } else if (index == 7) {
                    nameValues[2] = nameValue;
                    if (nameValues[index].getChildRen() != null) {
                        if (!nameValues[index].getChildRen().isEmpty()) {
                            bind(R.id.ll033).setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    bind(R.id.ll033).setVisibility(View.GONE);
                } else if (index == 8) {
                    nameValues[2] = nameValue;
                }
            }
        }).show();


    }

    //出版社
    public void loadList1() {
        if (OnlineExerciseActivity.chooseSubject == null) {
            showToast("请先选择学科");
            return;
        }
        if (nameValues[1] == null) {
            showToast("请先选择年段");
            return;
        }
        J.http().post(urls[0], ctx,
                Params.getPublisher(nameValues[1].getValue(), OnlineExerciseActivity.chooseSubject.getValue()),
                new HttpCallback<Respond<List<NameValue>>>(ctx) {
                    @Override
                    public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                        list1 = res.getData();
                        if (list1.isEmpty()) {
                            showToast("暂无出版社信息");
                            return;
                        }
                        choose(0, list1);
                    }
                });
    }

    //学段
    public void loadList2() {
        if (OnlineExerciseActivity.chooseSubject==null){
            showToast("请先选择学科");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userId", new Login().get().getUser_id());
        params.put("type","term");
        params.put("courseId", OnlineExerciseActivity.chooseSubject.getValue());
        J.http().post(urls[1], ctx,
                params,
                new HttpCallback<Respond<List<NameValue>>>(ctx) {
                    @Override
                    public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                        list2 = res.getData();
                        if (list2 == null || list2.isEmpty()) {
                            showToast("暂无学段信息");
                            return;
                        }
                        choose(1, list2);
                    }
                });
    }

    //章节
    public void loadList3() {
        //暂时屏蔽
        if (OnlineExerciseActivity.chooseSubject == null) {
            showToast("请先选择学科");
            return;
        }
        if (nameValues[1] == null) {
            showToast("请先选择年段");
            return;
        }
        if (nameValues[0] == null) {
            showToast("请先选择出版社");
            return;
        }
        J.http().post(urls[2], ctx,
                Params.getSection(nameValues[1].getValue(), OnlineExerciseActivity.chooseSubject.getValue(), nameValues[0].getValue()),
//                Params.getSection(3, 2, 14),
                new HttpCallback<Respond<List<NameValue>>>(ctx) {
                    @Override
                    public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
//                        res = TestUtils.getSection();
                        list3 = res.getData();
                        if (list3.isEmpty()) {
                            showToast("暂无章节信息");
                            return;
                        }
                        choose(2, list3);
                    }
                });
    }

    //题型
    public void loadList4() {
        J.http().post(urls[3], ctx,
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
        J.http().post(urls[4], ctx,
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

    //类型
    public void loadList6() {
        J.http().post(urls[5], ctx,
                null,
                new HttpCallback<Respond<List<NameValue>>>(ctx) {
                    @Override
                    public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                        list6 = res.getData();
                        if (list6.isEmpty()) {
                            showToast("暂无类型信息");
                            return;
                        }
                        choose(5, list6);
                    }
                });
    }

    //来源
    public void loadList7() {
        J.http().post(urls[6], ctx,
                null,
                new HttpCallback<Respond<List<NameValue>>>(ctx) {
                    @Override
                    public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                        list7 = res.getData();
                        if (list7.isEmpty()) {
                            showToast("暂无类型信息");
                            return;
                        }
                        choose(6, list7);
                    }
                });
    }


}
