package com.up.study.ui.home;

import android.content.Intent;
import android.view.View;

import com.up.study.TApplication;
import com.up.study.base.BaseFragmentActivity;
import com.up.teacher.R;

/**
 * TODO:布置作业
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class TaskActivity extends BaseFragmentActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.act_task;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}

    @Override
    protected void initEvent() {
        bind(R.id.ll_task_01).setOnClickListener(this);
        bind(R.id.ll_task_02).setOnClickListener(this);
        bind(R.id.ll_task_03).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 111 && resultCode == 111) || (requestCode == 112 && resultCode == 112) || (requestCode == 113 && resultCode == 113)) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_task_01://试卷错题录入
                gotoActivityResult(ChooseTestActivity.class, null, 111);
                TApplication.getInstant().setRelationType(1);
                break;

            case R.id.ll_task_02://在线练习
                gotoActivityResult(OnlineExerciseActivity.class, null, 112);
                TApplication.getInstant().setRelationType(2);
                break;
            case R.id.ll_task_03://线下作业通知
                gotoActivityResult(PublishTaskActivity.class, null, 113);
                break;
        }
    }
}
