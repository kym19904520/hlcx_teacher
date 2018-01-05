package com.up.study.ui.clbum;

import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.MessageEventBus;
import com.up.teacher.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 修改班级别称
 * Created by kym on 2017/7/19.
 */

public class ClassAnotherNameAmendActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_manage)
    TextView tvManage;
    @Bind(R.id.et_class_another_name)
    EditText etClassAnotherName;
    private String classId;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_another_name;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        tvManage.setVisibility(View.VISIBLE);
        classId = getIntent().getStringExtra("cid");
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.class_another_name);
        tvManage.setText(R.string.class_save);
        etClassAnotherName.setInputType(InputType.TYPE_CLASS_TEXT);
        InputFilter[] filters = {new InputFilter.LengthFilter(20)};
        etClassAnotherName.setFilters(filters);
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick(R.id.tv_manage)
    public void onViewClicked() {
        EventBus.getDefault().post(new MessageEventBus(etClassAnotherName.getText().toString().trim()));
        submitData();
    }

    /**
     * 提交数据
     */
    private void submitData() {
        HttpParams params = new HttpParams();
        params.put("cid",classId);
        params.put("alias",etClassAnotherName.getText().toString());
        J.http().post(Urls.MODIFICATION_CLASS_MESSAGE_URL, ctx, params, new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    showToast(res.getMsg());
                }
                Intent intent = new Intent(ClassAnotherNameAmendActivity.this,ClassManageActivity.class);
                startActivity(intent);

                Intent intent1 = new Intent();
                intent1.setAction("com.add.class");
                ctx.sendBroadcast(intent1);   //发送广播
            }
        });
    }
}
