package com.up.study.ui.clbum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.widget.MyListView;
import com.up.study.adapter.ClassNumberAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.base.MyBaseAdapter;
import com.up.study.model.AddClassGradeBean;
import com.up.study.model.ClassNumberBean;
import com.up.teacher.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 添加班级的页面
 * Created by kym on 2017/7/18.
 */

public class AddClassActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.lv_grade)
    MyListView lvGrade;
    @Bind(R.id.rv_class_number)
    RecyclerView rvClassNumber;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.ll_add_class)
    LinearLayout ll_add_class;

    private ReceiverFinish receiverFinish;
    private String classNumber;
    private String grade;
    private String className;
    private String gradeId;
    private GradeAdapter gradeAdapter;
    private View formerView = null;
    private ClassNumberAdapter classNumberAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.act_add_class;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        getGradeData();
        getClassNumber();

        receiverFinish = new ReceiverFinish();
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiverFinish, filter);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.class_add);
        GridLayoutManager layout = new GridLayoutManager(this, 3);
        rvClassNumber.setLayoutManager(layout);
    }

    /**
     * 获取年级
     */
    private View formerView_01 = null;

    private void getGradeData() {
        HttpParams params = new HttpParams();
        params.put("type", "grade");
        J.http().post(Urls.ADD_CLASS_GRADE_URL, ctx, params, new HttpCallback<Respond<List<AddClassGradeBean>>>(ctx) {
            @Override
            public void success(Respond<List<AddClassGradeBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<AddClassGradeBean> dataList = res.getData();
                    gradeAdapter = new GradeAdapter(AddClassActivity.this, dataList);
                    lvGrade.setAdapter(gradeAdapter);
                }
                lvGrade.setSelector(R.color.bg_select_gray);
                lvGrade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (formerView_01 != null) {
                            formerView_01.setBackgroundResource(R.color.text_white);
                        }
                        view.setBackgroundResource(R.color.bg_select_gray);
                        formerView_01 = view;
                        grade = gradeAdapter.mDataSets.get(position).getName();
                        gradeId = gradeAdapter.mDataSets.get(position).getCode() + "";
                        showLog(grade + gradeId + "banji");
                    }
                });
            }
        });
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        if (grade == null ||grade.isEmpty()){
            showToast("请选择年级");
            return;
        }
        if (classNumber == null || classNumber.isEmpty()) {
            showToast("请选择班级");
            return;
        }
        Intent intent = new Intent(AddClassActivity.this, ClassEstablishActivity.class);
        intent.putExtra("name", grade + className);
        intent.putExtra("grade", gradeId + "");
        intent.putExtra("class_type", classNumber + "");
        startActivity(intent);

    }

    /**
     * 获取班级
     */
    public void getClassNumber() {
        HttpParams params = new HttpParams();
        params.put("type", "classType");
        J.http().post(Urls.ADD_CLASS_NUMBER_URL, ctx, params, new HttpCallback<Respond<List<ClassNumberBean>>>(ctx) {
            @Override
            public void success(Respond<List<ClassNumberBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    final List<ClassNumberBean> dataList = res.getData();
                    ll_add_class.setVisibility(View.VISIBLE);
                    classNumberAdapter = new ClassNumberAdapter(AddClassActivity.this, dataList);
                    rvClassNumber.setAdapter(classNumberAdapter);
                    classNumberAdapter.setOnItemClickListener(new ClassNumberAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (formerView != null) {
                                formerView.setBackgroundResource(R.color.text_white);
                            }
                            view.setBackgroundResource(R.color.bg_select_gray);
                            formerView = view;
                            classNumber = dataList.get(position).getCode();
                            className = dataList.get(position).getName();
                        }
                    });
                }
            }
        });
    }

    /**
     * 创建班级成功后，发送一个广播关闭当前的activity
     */
    public class ReceiverFinish extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
            Intent intent1 = new Intent();
            intent1.setAction("com.add.class");
            ctx.sendBroadcast(intent1);   //发送广播
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiverFinish != null) {
            unregisterReceiver(receiverFinish);
        }
    }

    private class GradeAdapter extends MyBaseAdapter<AddClassGradeBean> {

        public GradeAdapter(Context context, List<AddClassGradeBean> mDataSets) {
            super(context, mDataSets);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_grade, null);
                viewHolder.tv_grade = (TextView) convertView.findViewById(R.id.tv_grade);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_grade.setText(mDataSets.get(position).getName());
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_grade;
    }
}
