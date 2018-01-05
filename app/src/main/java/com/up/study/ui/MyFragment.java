package com.up.study.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.up.common.J;
import com.up.common.utils.SPUtil;
import com.up.study.base.BaseFragment;
import com.up.study.ui.my.AboutPrepActivity;
import com.up.study.ui.my.FeedbackActivity;
import com.up.study.ui.my.MyMessageActivity;
import com.up.study.ui.my.SettingActivity;
import com.up.study.ui.my.StudentAnalyzeActivity;
import com.up.study.ui.my.StudyAnalyzeActivity;
import com.up.teacher.R;

/**
 * Created by dell on 2017/4/20.
 * 我的---fragment
 */

public class MyFragment extends BaseFragment {

    private ImageView iv_set,iv_student_preview,iv_student_sex;
    private RelativeLayout rl_xqfx,rl_student_fx,rl_yjfk,rl_gy;
    private TextView tv_student_name;
    public static MyFragment instance = null;

    private String user_head,user_name,user_sex;

    @Override
    protected int getContentViewId() {
        return R.layout.fra_my;
    }

    @Override
    protected void initView() {
        instance = this;
        iv_set = bind(R.id.iv_set);
        iv_student_preview = bind(R.id.iv_student_preview);
        tv_student_name = bind(R.id.tv_student_name);
        iv_student_sex = bind(R.id.iv_student_sex);
        rl_xqfx = bind(R.id.rl_xqfx);
        rl_student_fx = bind(R.id.rl_student_fx);
        rl_yjfk = bind(R.id.rl_yjfk);
        rl_gy = bind(R.id.rl_gy);
    }

    @Override
    protected void initEvent() {
        iv_set.setOnClickListener(this);
        iv_student_preview.setOnClickListener(this);
        rl_xqfx.setOnClickListener(this);
        rl_student_fx.setOnClickListener(this);
        rl_yjfk.setOnClickListener(this);
        rl_gy.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        user_head = SPUtil.getString(getContext(),"user_head","");
        user_name = SPUtil.getString(getContext(),"user_name","");
        user_sex = SPUtil.getString(getContext(),"user_sex","");
        J.image().loadCircle(ctx,user_head,iv_student_preview);
        tv_student_name.setText(user_name);
        if (user_sex.equals("男")){
            iv_student_sex.setImageResource(R.mipmap.class_nan);
        }else {
            iv_student_sex.setImageResource(R.mipmap.class_nv);
        }
    }

    public void refresh(){
        initData();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_set){
            gotoActivity(SettingActivity.class,null);          //设置
        }else if (v.getId() == R.id.iv_student_preview){
            gotoActivity(MyMessageActivity.class,null);         //个人信息
        }else if (v.getId() == R.id.rl_xqfx){
            gotoActivity(StudyAnalyzeActivity.class,null);   //学情分析
        }else if (v.getId() == R.id.rl_student_fx){
            gotoActivity(StudentAnalyzeActivity.class,null);   //学生分析
        }else if (v.getId() == R.id.rl_yjfk){
            gotoActivity(FeedbackActivity.class,null);          //意见反馈
        }else if (v.getId() == R.id.rl_gy){
            gotoActivity(AboutPrepActivity.class,null);         //关于
        }
    }
}
