package com.up.study.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.common.widget.LoadingDialog;
import com.up.study.adapter.ClassAdapter;
import com.up.study.base.BaseFragment;
import com.up.study.model.ClassAuditBean;
import com.up.study.model.ClassListBean;
import com.up.study.ui.clbum.AddClassActivity;
import com.up.study.ui.clbum.ClassAuditActivity;
import com.up.study.ui.clbum.ClassDetailsManageActivity;
import com.up.teacher.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dell on 2017/4/20.
 * 班级---fragment
 */

public class ClassFragment extends BaseFragment {
    private ListView lv_class;
    private RelativeLayout rl_class_audit;
    private ImageView iv_add;
    private ClassAdapter adapter;
    private LinearLayout ll_no_class;
    public static ClassFragment instance = null;
    private TextView tv_number;
    private ReceiveBroadCast receiveBroadCast;

    @Override
    protected int getContentViewId() {
        return R.layout.fra_class;
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(ctx);
        rl_class_audit = bind(R.id.rl_class_audit);
        lv_class = bind(R.id.lv_class);
        iv_add = bind(R.id.iv_add);
        iv_add.setVisibility(View.VISIBLE);
        ll_no_class = bind(R.id.ll_no_class);
        tv_number = bind(R.id.tv_number);
        instance = this;

        //创建班级刷新界面的广播
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.add.class");
        mParentActivity.registerReceiver(receiveBroadCast, filter);
    }

    public void refreshClassAudit(){
        getData();
    }

    @Override
    protected void initData() {
        getData();
        getClassData();
    }

    /**
     * 获取班级列表
     */
    private void getClassData() {
        J.http().post(Urls.CLASS_LIST_URL, ctx, null, new HttpCallback<Respond<List<ClassListBean>>>(ctx,true,true) {
            @Override
            public void success(Respond<List<ClassListBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<ClassListBean> dataList =res.getData();
                    if (dataList.size() <=0){
                        ll_no_class.setVisibility(View.VISIBLE);
                        lv_class.setVisibility(View.GONE);
                    }else {
                        lv_class.setVisibility(View.VISIBLE);
                        ll_no_class.setVisibility(View.GONE);
                    }
                    adapter = new ClassAdapter(ctx,dataList);
                    lv_class.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        iv_add.setOnClickListener(this);
        rl_class_audit.setOnClickListener(this);
        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SPUtil.putString(getContext(),"className",adapter.mDataSets.get(position).getName());
                Map<String,Object> map = new HashMap<>();
                map.put("classId",adapter.mDataSets.get(position).getId()+"");
                if (adapter.mDataSets.get(position).getType() == 0){
                    showToast("你不是该班班主任");
                    return;
                }
                gotoActivity(ClassDetailsManageActivity.class,map);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_add){
            gotoActivity(AddClassActivity.class,null);
        }else if (v.getId() == R.id.rl_class_audit){
            gotoActivity(ClassAuditActivity.class,null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getClassData();
    }

    /**
     * 获取班级审核
     */
    public void getData(){
        J.http().post(Urls.CLASS_AUDIT_URL, ctx, null, new HttpCallback<Respond<List<ClassAuditBean>>>(ctx,true,true) {
            @Override
            public void success(Respond<List<ClassAuditBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<ClassAuditBean> dataList = res.getData();
                    if (dataList.size() <=0){
                        tv_number.setVisibility(View.GONE);
                    }else {
                        tv_number.setVisibility(View.VISIBLE);
                    }
                    tv_number.setText(dataList.size() + "");
                }
            }
        });
    }

    public class ReceiveBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getClassData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiveBroadCast != null) {
            mParentActivity.unregisterReceiver(receiveBroadCast);
        }
    }
}
