package com.up.study.ui.my;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.study.base.BaseFragmentActivity;
import com.up.teacher.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 关于
 * Created by kym on 2017/7/20.
 */

public class AboutPrepActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getContentViewId() {
        return R.layout.act_about_prep;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_gy);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
