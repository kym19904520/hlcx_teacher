package com.up.study.ui.my;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.common.utils.SPUtil;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.dialog.CommonDialog;
import com.up.study.ui.login.LoginActivity;
import com.up.study.ui.login.UpdatePswActivity;
import com.up.teacher.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 * Created by kym on 2017/7/20.
 */

public class SettingActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getContentViewId() {
        return R.layout.act_setting;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_setting);
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick({R.id.iv_back, R.id.rl_modification_password, R.id.btn_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_modification_password:
                gotoActivity(UpdatePswActivity.class,null);
                break;
            case R.id.btn_loginout:
                new CommonDialog.Builder(this)
                        .setTitle(R.string.hint)
                        .setMessage(R.string.my_affirm_quit)
                        .setPositiveButton(R.string.my_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SPUtil.clear(SettingActivity.this);
                                closeAllActivty();
                                gotoActivity(LoginActivity.class,null);
                            }
                        }, R.color.colorPrimaryDark)
                        .setNegativeButton(R.string.my_cancel, null, R.color.colorPrimaryDark).show();
                break;
        }
    }
}
