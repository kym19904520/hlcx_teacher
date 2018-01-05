package com.up.study.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Constants;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.model.BaseBean;
import com.up.common.utils.GlideImageLoader;
import com.up.common.utils.SPUtil;
import com.up.common.utils.StudyUtils;
import com.up.study.TApplication;
import com.up.study.adapter.OfflineWorkAdapter;
import com.up.study.base.BaseFragment;
import com.up.study.base.CallBack;
import com.up.study.model.ClassModel;
import com.up.study.model.EasyModel;
import com.up.study.model.HomeTaskModel;
import com.up.study.model.MesModel;
import com.up.study.model.OfflineWorkModel;
import com.up.study.ui.home.HomeWorkDetailActivity;
import com.up.study.ui.home.MessageActivity;
import com.up.study.ui.home.MessageDetailActivity;
import com.up.study.ui.home.OffineWorkDetailActivity;
import com.up.study.ui.home.TaskActivity;
import com.up.study.ui.home.TestInputDetailActivity;
import com.up.study.weight.AutoVerticalScrollTextView;
import com.up.study.weight.LoadListView;
import com.up.teacher.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dell on 2017/4/20.
 * 首页
 */

public class HomeFragment extends BaseFragment {

    private ImageView iv_bz, iv_bz_num, iv_mes, iv_mes_num;
    private TextView tv_more;
    //    private TextView tv_notice;
    private Banner banner;
    List<String> images = new ArrayList<>();
    public static HomeFragment instance = null;

    private Spinner spinner_1, spinner_2, spinner_3;


    private int pageNo_xs = 1;
    private int pageNo_xx = 1;
    private LoadListView lv_xs, lv_xx;
    private CommonAdapter<HomeTaskModel> xsAdapter;
    //    private QuickAdapter xsAdapter;
    private List<HomeTaskModel> xsDataList = new ArrayList<>();

//    private RecyclerView mRecyclerView;
//    private QuickAdapter pullToRefreshAdapter;
//    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OfflineWorkAdapter xxAdapter;
    private List<OfflineWorkModel> xxDataList = new ArrayList<>();

    private int curClassId;//当前classid
    private int curRelationType;//当前作业类型,1:试卷录入 2：线上作业 3：线下作业
    private int curWorkType;//当前作业状态

    private  int mesNum;
    @Override
    protected int getContentViewId() {
        return R.layout.fra_home;
    }

    @Override
    protected void initView() {
        instance = this;
        iv_bz = bind(R.id.iv_bz);
        iv_bz_num = bind(R.id.iv_bz_num);
        iv_mes = bind(R.id.iv_mes);
        iv_mes_num = bind(R.id.iv_mes_num);
        tv_more = bind(R.id.tv_more);
        banner = bind(R.id.banner);
//        tv_notice = bind(R.id.tv_notice);

        spinner_1 = bind(R.id.spinner_1);
        spinner_2 = bind(R.id.spinner_2);
        spinner_3 = bind(R.id.spinner_3);

        lv_xs = bind(R.id.lv_xs);
        lv_xx = bind(R.id.lv_xx);

        //注册刷新班级的广播
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.REFRESH_CLASS);
        mParentActivity.registerReceiver(receiveBroadCast, filter);

    }

    public void refresh(){
        getOnlineWork();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initEvent() {
        setLoadLinster();
        iv_bz.setOnClickListener(this);
        iv_mes.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curClassId = classModelList.get(position).getClassId();
                pageNo_xs = 1;
                pageNo_xx = 1;
                if (curRelationType == 1 || curRelationType == 2) {
                    onlineInit();
                } else if (curRelationType == 3) {
                    offlineInit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                curRelationType = workTypeModellList.get(i).getValue();
                pageNo_xs = 1;
                pageNo_xx = 1;
                if (spinnerList2.get(i).equals("线下作业")) {
                    offlineInit();
                } else if (spinnerList2.get(i).equals("线上作业")) {//线上作业
                    onlineInit();
                } else {//试卷录入
                    onlineInit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curWorkType = workStatusModellList.get(position).getValue();
                pageNo_xs = 1;
                pageNo_xx = 1;
                if (curRelationType == 1 || curRelationType == 2) {
                    onlineInit();
                } else if (curRelationType == 3) {
                    offlineInit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onlineInit() {
        lv_xx.setVisibility(View.GONE);
        lv_xs.setVisibility(View.VISIBLE);
        getOnlineWork();
    }

    private void offlineInit() {
        lv_xx.setVisibility(View.VISIBLE);
        lv_xs.setVisibility(View.GONE);
        getOfflineWork();
    }

    private void setLoadLinster() {
        lv_xs.setRefreshListener(new LoadListView.RefreshListener() {
            @Override
            public void refresh() {
                pageNo_xs = 1;
                getOnlineWork();
            }
        });
        lv_xs.setLoadListener(new LoadListView.LoadListener() {
            @Override
            public void onLoad() {
                pageNo_xs += 1;
                getOnlineWork();
            }
        });

        lv_xx.setRefreshListener(new LoadListView.RefreshListener() {
            @Override
            public void refresh() {
                pageNo_xx = 1;
                getOfflineWork();
            }
        });

        lv_xx.setLoadListener(new LoadListView.LoadListener() {
            @Override
            public void onLoad() {
                pageNo_xx += 1;
                getOfflineWork();
            }
        });

    }

    @Override
    protected void initData() {
        getBanner();
        initList();
        getHomeSpinnerData();
    }


    private void initList() {
        xsAdapter = new CommonAdapter<HomeTaskModel>(ctx, xsDataList, R.layout.item_home_xszy_list) {
            @Override
            public void convert(ViewHolder vh, final HomeTaskModel item, int position) {

                TextView tv_title = vh.getView(R.id.tv_title);
                ImageView iv_1 = vh.getView(R.id.iv_1);
                tv_title.setText(item.getTitle());
                TextView tv_class = vh.getView(R.id.tv_class);
                tv_class.setText(item.getClassName());
                TextView tv_num = vh.getView(R.id.tv_num);
                tv_num.setText(item.getSubmitTotal() + "/" + item.getPeopleTotal());
                TextView tv_time = vh.getView(R.id.tv_time);
                tv_time.setText(item.getDeadline() + "截止");

                ImageView imageView = vh.getView(R.id.iv_delete);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getSubmitTotal() > 0) {
                            showToast("已有学生提交作业，不允许删除作业");
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("是否删除？")
                                    .setPositiveButton(R.string.my_confirm, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            delTask(item.getId());
                                        }
                                    })
                                    .setNegativeButton(R.string.my_cancel, null)
                                    .show();
                        }
                    }
                });

                TextView tv_sjlr = vh.getView(R.id.tv_sjlr);
                if(curRelationType==2){//线上作业
                    tv_sjlr.setVisibility(View.VISIBLE);
                    iv_1.setImageResource(R.mipmap.sy_24);
                    tv_sjlr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("是否将该作业作为“试卷录入”任务布置给该班级？")
                                    .setPositiveButton(R.string.my_confirm, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sjlr(item.getId());
                                        }
                                    })
                                    .setNegativeButton(R.string.my_cancel, null)
                                    .show();
                        }
                    });
                }
                else {
                    iv_1.setImageResource(R.mipmap.sy_25);
                    tv_sjlr.setVisibility(View.INVISIBLE);
                }
            }
        };
        lv_xs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = new HashMap<String, Object>();
                HomeTaskModel model = (HomeTaskModel) parent.getAdapter().getItem(position);
                model.setClassId(curClassId);
                model.setRelationType(curRelationType);
                map.put("data", model);
                if (curRelationType == 1) {
                    gotoActivity(TestInputDetailActivity.class, map);
                } else {
                    gotoActivity(HomeWorkDetailActivity.class, map);
                }
            }
        });

        lv_xs.setAdapter(xsAdapter);
        xxAdapter = new OfflineWorkAdapter(xxDataList, mParentActivity);
        lv_xx.setAdapter(xxAdapter);
        lv_xx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoActivityWithBean(OffineWorkDetailActivity.class, (OfflineWorkModel) parent.getAdapter().getItem(position), null);
            }
        });
    }

    private void initBanner(List<CarouselModel> carousel) {
        if (carousel.size() <= 0){
            banner.setBackgroundResource(R.mipmap.banner);
        }
        for (int i = 0; i < carousel.size(); i++) {
            images.add("https://" + TApplication.getInstant().getImgUrlHead() + "/" + carousel.get(i).getPath());
        }
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }

    @Override
    public void onClick(View v) {
        if (v == iv_bz) {                       //布置作业
//            Login login = new Login().get();
//            if (login.getCalsss() == null) {
//                gotoActivity(NoClassActivity.class, null);
//            } else {
//                gotoActivity(TaskActivity.class, null);
//            }
            gotoActivity(TaskActivity.class, null);
        } else if (v == iv_mes) {               //消息界面
            Intent intent = new Intent();
            intent.setClass(getActivity(), MessageActivity.class);
            SPUtil.saveMesNum(getActivity(),mesNum);
            startActivityForResult(intent, 2);
        } else if (v == tv_more) {              //更多消息
            Intent intent = new Intent();
            intent.setClass(getActivity(), MessageActivity.class);
            SPUtil.saveMesNum(getActivity(),mesNum);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 2://消息列表返回
                iv_mes_num.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
    //线上作业&试卷录入
    private void getOnlineWork() {
        HttpParams params = new HttpParams();
        params.put("relationType", curRelationType);
        params.put("classsId", curClassId);
        params.put("cuid", SPUtil.getString(getContext(),"user_id",""));
        params.put("workType", curWorkType);
        params.put("page", pageNo_xs);
        J.http().post(Urls.GET_HOME_TASK_LIST, ctx, params, new HttpCallback<Respond<List<HomeTaskModel>>>(ctx,true,true) {
            @Override
            public void success(Respond<List<HomeTaskModel>> res, Call call, Response response, boolean isCache) {
                List<HomeTaskModel> temp = res.getData();
                if (pageNo_xs == 1) {//初始化或下拉刷新
                    xsDataList.clear();
                    xsDataList.addAll(temp);
                    if (lv_xs.isReFresh) {
                        lv_xs.refreshComplete();
                    }
                } else {//上拉加载
                    lv_xs.loadComplete();
                    if (temp.isEmpty()) {
                        pageNo_xs--;
                    } else {
                        xsDataList.addAll(temp);
                    }
                }
                xsAdapter.NotifyDataChanged(xsDataList);

                if(xsDataList==null||xsDataList.size()==0){
                    bind(R.id.tv_no_task).setVisibility(View.VISIBLE);
                    lv_xs.setVisibility(View.GONE);
                }
                else{
                    bind(R.id.tv_no_task).setVisibility(View.GONE);
                    lv_xs.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //线下作业
    private void getOfflineWork() {
        HttpParams params = new HttpParams();
        params.put("relationType", curRelationType);
        params.put("classId", curClassId);
        params.put("workType", curWorkType);
        params.put("page", pageNo_xx);
        J.http().post(Urls.GET_HOME_XX_TASK_LIST, ctx, params, new HttpCallback<Respond<List<OfflineWorkModel>>>(ctx,true,true) {
            @Override
            public void success(Respond<List<OfflineWorkModel>> res, Call call, Response response, boolean isCache) {
                List<OfflineWorkModel> temp = res.getData();
                if (pageNo_xx == 1) {//初始化或下拉刷新
                    xxDataList.clear();
                    xxDataList.addAll(temp);
                    if (lv_xx.isReFresh) {
                        lv_xx.refreshComplete();
                    }
                } else {//上拉加载
                    lv_xx.loadComplete();
                    if (temp.isEmpty()) {
                        pageNo_xx--;
                    } else {
                        xxDataList.addAll(temp);
                    }
                }
                xxAdapter.notifyDataSetChanged();

                if(xxDataList==null||xxDataList.size()==0){
                    bind(R.id.tv_no_task).setVisibility(View.VISIBLE);
                    lv_xx.setVisibility(View.GONE);
                }
                else{
                    bind(R.id.tv_no_task).setVisibility(View.GONE);
                    lv_xx.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //banner和最近三条
    private void getBanner() {
        HttpParams params = new HttpParams();
        params.put("position", 2);
        J.http().post(Urls.GET_BANNER, ctx, params, new HttpCallback<Respond<HomeBannerAdtModel>>(ctx) {
            @Override
            public void success(Respond<HomeBannerAdtModel> res, Call call, Response response, boolean isCache) {
                HomeBannerAdtModel temp = res.getData();
                if (temp != null) {
                    initBanner(temp.getCarousel());
                    initScrollText(temp.getList3());
                    mesNum = SPUtil.getMesNum(getActivity());
                    List<IdModel> allIds = temp.getIds();
                    if(allIds.size()!=0&&mesNum!=allIds.size()){//有未读消息
                        iv_mes_num.setVisibility(View.VISIBLE);
                        mesNum = allIds.size();
                    }
                }
//                tv_notice.setText("");
            }
        });
    }
    //线上作业转为试卷录入
    private void sjlr(int id) {
        HttpParams params = new HttpParams();
        params.put("relationId", id);
        params.put("classsId", curClassId);
        params.put("cuid", SPUtil.getString(getContext(),"user_id",""));
        J.http().post(Urls.SJLR, ctx, params, new HttpCallback<Respond<String>>(ctx,true,true) {
            @Override
            public void success(Respond<String> res, Call call, Response response, boolean isCache) {
                showToast("布置成功");
            }
        });
    }

    //删除任务
    private void delTask(int id) {
        HttpParams params = new HttpParams();
        params.put("relationId", id);
        J.http().post(Urls.DEL_TASK, ctx, params, new HttpCallback<Respond<String>>(ctx,true,true) {
            @Override
            public void success(Respond<String> res, Call call, Response response, boolean isCache) {
                showToast("删除成功");
                pageNo_xs = 1;
                pageNo_xx = 1;
                if (curRelationType == 1 || curRelationType == 2) {
                    onlineInit();
                } else if (curRelationType == 3) {
                    offlineInit();
                }
            }
        });
    }

    AutoVerticalScrollTextView verticalScrollTV;

    List<MesModel> strings = new ArrayList<>();
    private int number = 0;
    private boolean isRunning = true;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                verticalScrollTV.next();
                number++;
                verticalScrollTV.setText(strings.get(number % strings.size()).getTitle());
            }
        }
    };

    private void initScrollText(List<MesModel> list) {
        strings.clear();
        strings.addAll(list);
        verticalScrollTV = bind(R.id.textview_auto_roll);
        if (strings.size() == 0) {
            verticalScrollTV.setText("暂无公告");
        } else if (strings.size() == 1) {
            verticalScrollTV.setText(strings.get(0).getTitle());
        } else {
            verticalScrollTV.setText(strings.get(0).getTitle());
            verticalScrollTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mParentActivity,strings.get(number%strings.size()).getTitle(),Toast.LENGTH_SHORT).show();
                    gotoActivityWithBean(MessageDetailActivity.class, strings.get(number % strings.size()), null);
                }
            });

            new Thread() {
                @Override
                public void run() {
                    while (isRunning) {
                        SystemClock.sleep(3000);
                        handler.sendEmptyMessage(199);
                    }
                }
            }.start();
        }

    }

    /**
     * 获取spinner的全部数据
     */
    public void getHomeSpinnerData() {
        classModelList.clear();
        spinnerList1.clear();
        workTypeModellList.clear();
        spinnerList2.clear();
        workStatusModellList.clear();
        spinnerList3.clear();
        HttpParams params = new HttpParams();
        J.http().post(Urls.GET_HOME_SPINNER_DATA, ctx, params, new HttpCallback<Respond<SpinnerModel>>(ctx, true, true) {
            @Override
            public void success(Respond<SpinnerModel> res, Call call, Response response, boolean isCache) {
                SpinnerModel spinnerModel = res.getData();
                if (spinnerModel == null) {
                    return;
                }
                //班级列表
                if (spinnerModel.getClasses() != null) {
                    classModelList.addAll(spinnerModel.getClasses());
                    for (int i = 0; i < spinnerModel.getClasses().size(); i++) {
                        if (i == 0) {
                            curClassId = spinnerModel.getClasses().get(i).getClassId();
                        }
                        spinnerList1.add(spinnerModel.getClasses().get(i).getClassName());
                    }
                    //android.R.layout.simple_spinner_item
                    CommonAdapter adapter1 = new CommonAdapter<String>(ctx, spinnerList1, R.layout.item_spinner) {
                        @Override
                        public void convert(ViewHolder vh, String item, int position) {
                            vh.setText(android.R.id.text1, item.replace("年级",""));
                            showLog("获取的年级" + item.replace("年级",""));
                        }
                    };
                    spinner_1.setAdapter(adapter1);
                }
                //作业类型列表
                if (spinnerModel.getRelation() != null) {
                    workTypeModellList.addAll(spinnerModel.getRelation());
                    for (int i = 0; i < spinnerModel.getRelation().size(); i++) {
                        if (i == 0) {
                            curRelationType = spinnerModel.getRelation().get(i).getValue();
                        }
                        spinnerList2.add(spinnerModel.getRelation().get(i).getName());
                    }
                    CommonAdapter adapter2 = new CommonAdapter<String>(ctx, spinnerList2, R.layout.item_spinner) {
                        @Override
                        public void convert(ViewHolder vh, String item, int position) {
                            vh.setText(android.R.id.text1, item);
                        }
                    };
                    spinner_2.setAdapter(adapter2);
                }

                //作业状态列表
                if (spinnerModel.getWorkTypes() != null) {
                    workStatusModellList.addAll(spinnerModel.getWorkTypes());
                    for (int i = 0; i < spinnerModel.getWorkTypes().size(); i++) {
                        if (i == 0) {
                            curWorkType = spinnerModel.getWorkTypes().get(i).getValue();
                        }
                        spinnerList3.add(spinnerModel.getWorkTypes().get(i).getName());
                    }
                    CommonAdapter adapter3 = new CommonAdapter<String>(ctx, spinnerList3, R.layout.item_spinner) {
                        @Override
                        public void convert(ViewHolder vh, String item, int position) {
                            vh.setText(android.R.id.text1, item);
                        }
                    };
                    spinner_3.setAdapter(adapter3);
                }

                getOnlineWork();
                //getOfflineWork();
            }
        });
    }

    /**
     * 获取班级列表
     */
    List<ClassModel> classModelList = new ArrayList<>();
    List<String> spinnerList1 = new ArrayList<String>();

    private void getClassList() {
        StudyUtils.getClass(this.getActivity(), new CallBack<List<ClassModel>>() {
            @Override
            public void suc(List<ClassModel> obj) {
                if (obj == null) {
                    showLoge("无班级数据");
                    return;
                }
                classModelList.addAll(obj);
                for (int i = 0; i < obj.size(); i++) {
                    if (i == 0) {
                        curClassId = obj.get(i).getClassId();
                    }
                    spinnerList1.add(obj.get(i).getClassName());
                }
                //android.R.layout.simple_spinner_item
                CommonAdapter adapter1 = new CommonAdapter<String>(ctx, spinnerList1, R.layout.item_spinner) {
                    @Override
                    public void convert(ViewHolder vh, String item, int position) {
                        vh.setText(android.R.id.text1, item);
                    }
                };
                spinner_1.setAdapter(adapter1);
            }
        });
    }

    /**
     * 获取作业类型
     */
    List<EasyModel> workTypeModellList = new ArrayList<>();
    final List<String> spinnerList2 = new ArrayList<String>();

    public void getWorkTypeList() {
        StudyUtils.getWorkType(this.getActivity(), new CallBack<List<EasyModel>>() {

            @Override
            public void suc(List<EasyModel> obj) {
                if (obj == null) {
                    showLoge("无作业类型");
                    return;
                }
                workTypeModellList.addAll(obj);
                for (int i = 0; i < obj.size(); i++) {
                    if (i == 0) {
                        curRelationType = obj.get(i).getValue();
                    }
                    spinnerList2.add(obj.get(i).getName());
                }
                CommonAdapter adapter2 = new CommonAdapter<String>(ctx, spinnerList2, R.layout.item_spinner) {
                    @Override
                    public void convert(ViewHolder vh, String item, int position) {
                        vh.setText(android.R.id.text1, item);
                    }
                };
                spinner_2.setAdapter(adapter2);
            }
        });
    }

    /**
     * 获取作业状态
     */
    List<EasyModel> workStatusModellList = new ArrayList<>();
    List<String> spinnerList3 = new ArrayList<String>();

    public void getWorkStatusList() {
        StudyUtils.getWorkStatus(this.getActivity(), new CallBack<List<EasyModel>>() {
            @Override
            public void suc(List<EasyModel> obj) {
                if (obj == null) {
                    showLoge("无作业状态");
                    return;
                }
                workStatusModellList.addAll(obj);
                for (int i = 0; i < obj.size(); i++) {
                    if (i == 0) {
                        curWorkType = obj.get(i).getValue();
                    }
                    spinnerList3.add(obj.get(i).getName());
                }
                CommonAdapter adapter3 = new CommonAdapter<String>(ctx, spinnerList3, R.layout.item_spinner) {
                    @Override
                    public void convert(ViewHolder vh, String item, int position) {
                        vh.setText(android.R.id.text1, item);
                    }
                };
                spinner_3.setAdapter(adapter3);

            }
        });
    }

    private class SpinnerModel extends BaseBean {

        private List<ClassModel> classes;
        private List<EasyModel> relation;
        private List<EasyModel> workTypes;

        public List<ClassModel> getClasses() {
            return classes;
        }

        public void setClasses(List<ClassModel> classes) {
            this.classes = classes;
        }

        public List<EasyModel> getRelation() {
            return relation;
        }

        public void setRelation(List<EasyModel> relation) {
            this.relation = relation;
        }

        public List<EasyModel> getWorkTypes() {
            return workTypes;
        }

        public void setWorkTypes(List<EasyModel> workTypes) {
            this.workTypes = workTypes;
        }
    }

    private class HomeBannerAdtModel extends BaseBean {
        List<MesModel> list3;
        List<IdModel> ids;
        List<CarouselModel> carousel;

        public List<MesModel> getList3() {
            return list3;
        }

        public void setList3(List<MesModel> list3) {
            this.list3 = list3;
        }

        public List<IdModel> getIds() {
            return ids;
        }

        public void setIds(List<IdModel> ids) {
            this.ids = ids;
        }

        public List<CarouselModel> getCarousel() {
            return carousel;
        }

        public void setCarousel(List<CarouselModel> carousel) {
            this.carousel = carousel;
        }
    }

    private class IdModel extends BaseBean {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    private class CarouselModel extends BaseBean {
        private int sort;
        private String title;
        private String path;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    private ReceiveBroadCast receiveBroadCast;
    public class ReceiveBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getHomeSpinnerData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiveBroadCast!=null) {
            mParentActivity.unregisterReceiver(receiveBroadCast);
        }
    }
}
