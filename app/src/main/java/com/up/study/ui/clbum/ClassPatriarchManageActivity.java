package com.up.study.ui.clbum;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.common.utils.StringUtils;
import com.up.study.adapter.ClassPatriarchManageAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.PatriarchManageBean;
import com.up.teacher.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 家长管理
 * Created by kym on 2017/7/19.
 */

public class ClassPatriarchManageActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_student_preview)
    ImageView ivStudentPreview;
    @Bind(R.id.tv_student_name)
    TextView tvStudentName;
    @Bind(R.id.iv_student_sex)
    ImageView ivStudentSex;
    @Bind(R.id.tv_student_id)
    TextView tvStudentId;
    @Bind(R.id.tv_patriarch_manage)
    TextView tvPatriarchManage;
    @Bind(R.id.lv_patriarch_manage)
    ListView lvPatriarchManage;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;
    @Bind(R.id.ll_no_class)
    LinearLayout llNoClass;

    private String studentId;
    private String studentName;
    private String className;
    private String code;
    private String head;
    private int sex;
    private ClassPatriarchManageAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_patriarc_manage;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        studentId = intent.getStringExtra("sid");
        studentName = intent.getStringExtra("studentName");
        className = SPUtil.getString(this,"className","");
        code = intent.getStringExtra("code");
        head = intent.getStringExtra("head");
        sex = intent.getIntExtra("sex",0);
    }

    @Override
    protected void initData() {
        tvTitle.setText(className +  " " + studentName);
        tvStudentName.setText(studentName);
        tvStudentId.setText(code);
        Glide.with(this)
                .load(head)
                .placeholder(R.mipmap.class_student)
                .error(R.mipmap.class_student)
                .override(StringUtils.dip2Px(this, 50), StringUtils.dip2Px(this, 50))
                .into(ivStudentPreview);
        if (sex == 0){
            ivStudentSex.setImageResource(R.mipmap.class_nan);
        }else {
            ivStudentSex.setImageResource(R.mipmap.class_nv);
        }
        getPatriarchManageData();
    }

    private void getPatriarchManageData() {
        final HttpParams params = new HttpParams();
        params.put("sid", studentId);
        J.http().post(Urls.CLASS_PATRIARCH_URL, ctx, params, new HttpCallback<Respond<List<PatriarchManageBean>>>(ctx) {
            @Override
            public void success(Respond<List<PatriarchManageBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<PatriarchManageBean> dataList = res.getData();
                    adapter = new ClassPatriarchManageAdapter(ClassPatriarchManageActivity.this, dataList,
                            studentId,tvPatriarchManage,llNoClass,tvNoData);
                    lvPatriarchManage.setAdapter(adapter);
                    tvPatriarchManage.setText("家长（" + adapter.getListSize().size() + "）");
                    if (dataList.size() <= 0){
                        llNoClass.setVisibility(View.VISIBLE);
                        lvPatriarchManage.setVisibility(View.GONE);
                        tvNoData.setText(R.string.no_patriarch);
                    }
                    lvPatriarchManage.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
