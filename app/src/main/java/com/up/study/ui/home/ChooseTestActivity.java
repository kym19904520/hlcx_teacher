package com.up.study.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.common.J;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.NameValue;
import com.up.study.model.TestModel;
import com.up.study.params.Params;
import com.up.study.weight.ChooseDialog;
import com.up.study.weight.LoadListView;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:试卷录入
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class ChooseTestActivity extends BaseFragmentActivity {

    private TextView tv1, tv2, tv3, tv4;
    private LoadListView lv;

    private List<TestModel> list = new ArrayList<>();
    private CommonAdapter<TestModel> adapter;

    //学科
    private List<NameValue> list1 = new ArrayList<>();
    private NameValue nameValue1 = null;
    //年级
    private List<NameValue> list2 = new ArrayList<>();
    private NameValue nameValue2 = null;
    //试卷类型
    private List<NameValue> list3 = new ArrayList<>();
    private NameValue nameValue3 = null;
    //共享私有
    private List<NameValue> list4 = new ArrayList<>();
    private NameValue nameValue4 = null;

    private int pageNo = 1;


    @Override
    protected int getContentViewId() {
        return R.layout.act_choose_test;
    }

    @Override
    protected void initView() {
        tv1 = bind(R.id.tv_1);
        tv2 = bind(R.id.tv_2);
        tv3 = bind(R.id.tv_3);
        tv4 = bind(R.id.tv_4);

        lv = bind(R.id.lv);


    }

    @Override
    protected void initData() {
        getSubjectList(true);
        getGrade(true);
        getTestType(true);
        getShareType(true);
        adapter = new CommonAdapter<TestModel>(ctx, list, R.layout.item_lv_test) {
            @Override
            public void convert(ViewHolder vh, TestModel item, int position) {
                final int index = position;
                ImageView iv = vh.getView(R.id.iv_select);
                iv.setSelected(item.isSelected());
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int k = 0; k < list.size(); k++) {
                            list.get(k).setSelected(false);
                        }
                        list.get(index).setSelected(true);
                        adapter.NotifyDataChanged(list);
                    }
                });
                vh.setText(R.id.tv_title, item.getName());
                vh.setText(R.id.tv_tag1, item.getPaperType());
                vh.setText(R.id.tv_tag2, item.getGradeName());
                vh.setText(R.id.tv_count, item.getSubjectNum()+"题");
            }
        };
        lv.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> map = new HashMap<>();
                map.put("test", adapterView.getAdapter().getItem(i));
                gotoActivity(TestInfoActivity.class, map);
            }
        });

        bind(R.id.btn_next).setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        lv.setLoadListener(new LoadListView.LoadListener() {
            @Override
            public void onLoad() {
                pageNo = pageNo + 1;
                loadTest();
            }
        });
        lv.setRefreshListener(new LoadListView.RefreshListener() {
            @Override
            public void refresh() {
                pageNo = 1;
                loadTest();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 111 && resultCode == 111) || (requestCode == 110 && resultCode == 110)) {
            setResult(111);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                next();
                break;
            case R.id.tv_1:
                getSubjectList(false);
                break;
            case R.id.tv_2:
                getGrade(false);
                break;
            case R.id.tv_3:
                getTestType(false);
                break;
            case R.id.tv_4:
                getShareType(false);
                break;
        }

    }

    //科目载入
    private void getSubjectList(final boolean isInit) {
        if (!list1.isEmpty()) {
            if (!isInit) {
                choose(list1, 1);
            }
            return;
        }
        J.http().post(Urls.GET_SUBJECT, ctx, Params.getSubject(null), new HttpCallback<Respond<List<NameValue>>>(ctx) {
            @Override
            public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
//                res = TestUtils.getSubjectList();
                list1 = (null == res.getData()) ? new ArrayList<NameValue>() : res.getData();
                if (!list1.isEmpty()) {
                    list1.get(0).setSelected(true);
                    nameValue1 = list1.get(0);
                    tv1.setText(nameValue1.getName());
                    loadTest();
                }
                if (!isInit) {
                    choose(list1, 1);
                }
            }
        });
    }

    //年级载入
    private void getGrade(final boolean isInit) {
        if (!list2.isEmpty()) {
            if (!isInit) {
                choose(list2, 2);
            }
            return;
        }
        J.http().post(Urls.GET_GRADES, ctx, Params.userId(), new HttpCallback<Respond<List<NameValue>>>(ctx) {
            @Override
            public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
//                res = TestUtils.getGrade();
                list2 = (null == res.getData()) ? new ArrayList<NameValue>() : res.getData();
                if (null != list2) {
                    if (!list2.isEmpty()) {
                        list2.get(0).setSelected(true);
                        nameValue2 = list2.get(0);
                        tv2.setText(nameValue2.getName());
                        loadTest();
                    }
                }
                if (!isInit) {
                    choose(list2, 2);
                }
            }
        });
    }

    //试卷类型载入
    private void getTestType(final boolean isInit) {
        if (!list3.isEmpty()) {
            if (!isInit) {
                choose(list3, 3);
            }
            return;
        }
        J.http().post(Urls.GET_TEST_TYPE, ctx, Params.type("papertype"), new HttpCallback<Respond<List<NameValue>>>(ctx) {
            @Override
            public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                list3 = (null == res.getData()) ? new ArrayList<NameValue>() : res.getData();
                for (int i = 0;i< list3.size();i++){
                    if (list3.get(i).getName().equals("同步练习卷")){
                        list3.remove(i);
                    }
                    if (list3.get(i).getName().equals("月考试卷")){
                        list3.remove(i);
                    }
                }
                if (!list3.isEmpty()) {
                    list3.get(0).setSelected(true);
                    nameValue3 = list3.get(0);
                    tv3.setText(nameValue3.getName());
                    loadTest();
                }
                if (!isInit) {
                    choose(list3, 3);
                }
            }
        });
    }

    //分享类型载入
    private void getShareType(final boolean isInit) {
        if (!list4.isEmpty()) {
            if (!isInit) {
                choose(list4, 4);
            }
            return;
        }
        J.http().post(Urls.GET_SHARE_TYPE, ctx, Params.type("shareType"), new HttpCallback<Respond<List<NameValue>>>(ctx) {
            @Override
            public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                list4 = (null == res.getData()) ? new ArrayList<NameValue>() : res.getData();
                if (!list4.isEmpty()) {
                    list4.get(0).setSelected(true);
                    nameValue4 = list4.get(0);
                    tv4.setText(nameValue4.getName());
                    loadTest();
                }
                if (!isInit) {
                    choose(list4, 4);
                }
            }
        });
    }

    private void choose(List<NameValue> list, final int index) {
        if (list.isEmpty()) {
            if (index == 1) {
                showToast("暂无学科数据！");
            } else if (index == 2) {
                showToast("暂无年级数据！");
            } else if (index == 3) {
                showToast("暂无试卷类型数据！");
            } else {
                showToast("暂无分享类型！");
            }
            return;
        }

        new ChooseDialog(ctx, list).setChooseListener(new ChooseDialog.ChooseListener() {
            @Override
            public void choose(NameValue res) {
                if (index == 1) {
                    tv1.setText(res.getName());
                    nameValue1 = res;
                } else if (index == 2) {
                    nameValue2 = res;
                    tv2.setText(res.getName());
                } else if (index == 3) {
                    nameValue3 = res;
                    tv3.setText(res.getName());
                } else {
                    nameValue4 = res;
                    tv4.setText(res.getName());
                }
                loadTest();
            }
        }).show();

    }

    //试卷载入
    private void loadTest() {
        if (nameValue1 == null || nameValue2 == null || nameValue3 == null || nameValue4 == null) {
            pageNo = 1;
            return;
        }
        J.http().post(Urls.TEST_LIST, ctx,
                Params.getTestList(nameValue1.getValue(), nameValue2.getValue(), nameValue3.getValue(), nameValue4.getValue(), pageNo),
                new HttpCallback<Respond<List<TestModel>>>(ctx) {
                    @Override
                    public void success(Respond<List<TestModel>> res, Call call, Response response, boolean isCache) {
//                        res = TestUtils.testList();
                        List<TestModel> temp = (null == res.getData()) ? new ArrayList<TestModel>() : res.getData();
                        if (pageNo == 1) {
                            list = temp;
                        } else {
                            if (temp.isEmpty()) {
                                pageNo = pageNo - 1;
                            } else {
                                list.addAll(temp);
                            }
                        }
                        lv.loadComplete();
                        lv.refreshComplete();
                        adapter.NotifyDataChanged(list);
                    }
                });
    }

    private void next() {
        TestModel testModel = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                testModel = list.get(i);
            }
        }
        if (testModel == null) {
            showToast("请先选择试卷！");
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("test", testModel);
            map.put("paperId", testModel.getId());//试卷ID
            map.put("courseId", nameValue1.getValue());//科目
            map.put("grade", nameValue2.getValue());//年级
            map.put("code", 111);//年级
            //判断选中试卷是否有标准试卷，有标准试卷，跳转到选择班级页，没有标准卷则跳转到标准卷上传页
            if (testModel.getFlag() == 1) {//有
                gotoActivityResult(ChooseClassActivity.class, map, 111);
            } else {//没有,跳转到上传标准卷
                gotoActivityResult(UpTestActivity.class, map, 111);
            }
        }
    }
}
