package com.up.study.ui.home;

import android.view.View;

import com.up.study.base.BaseFragmentActivity;
import com.up.teacher.R;


/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/28.
 */

public class NoClassActivity extends BaseFragmentActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.act_no_class;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        bind(R.id.btn_add).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                break;
        }

    }
}
