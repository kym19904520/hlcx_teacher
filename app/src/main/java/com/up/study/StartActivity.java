package com.up.study;

import android.os.Handler;
import android.view.View;

import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.Login;
import com.up.study.ui.login.LoginActivity;
import com.up.teacher.R;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by yy_Cai
 * On 2017/5/20.
 */

public class StartActivity extends BaseFragmentActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.act_start;
    }

    @Override
    protected void initView() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Login login = new Login().get();
                if (login == null) {
                    gotoActivity(LoginActivity.class, null);
                } else {
                    // StudyUtils.saveLoginInfo(StartActivity.this);
                    gotoActivity(MainActivity.class, null);
                }
                finish();
            }
        }, 100);
    }

    @Override
    protected void initData() {}

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}
}


