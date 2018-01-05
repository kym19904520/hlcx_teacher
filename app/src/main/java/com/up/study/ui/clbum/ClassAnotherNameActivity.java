package com.up.study.ui.clbum;

import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.MessageEventBus;
import com.up.teacher.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 班级别称
 * Created by kym on 2017/7/19.
 */

public class ClassAnotherNameActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_manage)
    TextView tvManage;
    @Bind(R.id.et_class_another_name)
    EditText etClassAnotherName;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_another_name;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        tvManage.setVisibility(View.VISIBLE);
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
        gotoActivity(ClassEstablishActivity.class,null);
    }
}
