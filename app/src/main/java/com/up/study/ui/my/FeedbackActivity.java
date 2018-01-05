package com.up.study.ui.my;

import android.text.TextUtils;
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
import com.up.teacher.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 意见反馈
 * Created by kym on 2017/7/20.
 */

public class FeedbackActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_opinion)
    EditText etOpinion;

    @Override
    protected int getContentViewId() {
        return R.layout.act_feedback;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_feedback);
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick({R.id.iv_back, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_sure:
                submitOpinion();
                break;
        }
    }

    private void submitOpinion() {
        String opinion = etOpinion.getText().toString();
        if(TextUtils.isEmpty(opinion)){
            showToast("请填写您的意见");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("content",opinion);
        params.put("position","2");
        J.http().post(Urls.MY_FEEDBACK_URL, ctx, params, new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())){
                    showToast(res.getData() + "");
                    FeedbackActivity.this.finish();
                }
            }
        });
    }
}
