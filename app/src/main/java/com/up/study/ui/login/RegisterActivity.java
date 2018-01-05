package com.up.study.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.base.AbsFragmentActivity;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.PhoneUtils;
import com.up.common.utils.SPUtil;
import com.up.common.utils.TimeUtils;
import com.up.common.utils.WidgetUtils;
import com.up.study.MainActivity;
import com.up.study.TApplication;
import com.up.study.adapter.TopicPagerAdapter;
import com.up.study.base.CallBack;
import com.up.study.model.Login;
import com.up.study.weight.MyViewPager;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 注册页面
 */
public class RegisterActivity extends AbsFragmentActivity {
    private int mStep=0;
    private MyViewPager viewPager;
    private LayoutInflater mInflater;
    private List<View> mViews= new ArrayList<View>();

    private ImageView iv_back,iv_psw,iv_stu,iv_eye;
    private TextView tv_get_code;
    private EditText et_phone,et_psw,et_code,et_sname,et_ccode;
    @Override
    protected int getContentViewId() {
        return R.layout.act_register;
    }

    @Override
    protected void initView() {
        viewPager =  bind(R.id.viewpager);
        iv_psw =  bind(R.id.iv_psw);
        iv_stu =  bind(R.id.iv_stu);
        iv_back =  bind(R.id.iv_back);
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mViews.clear();
        mInflater = this.getLayoutInflater();
        View view1=mInflater.inflate(R.layout.view_register_1, null);
        initRegister1(view1);
        mViews.add(view1);
        View view2=mInflater.inflate(R.layout.view_register_2, null);
        initRegister2(view2);
        mViews.add(view2);
        View view3=mInflater.inflate(R.layout.view_register_3, null);
        initRegister3(view3);
        mViews.add(view3);
        viewPager.setAdapter(new TopicPagerAdapter(mViews));
        viewPager.setOffscreenPageLimit(mViews.size());//限制存储在内存的页数
        viewPager.setNoScroll(true);
    }

    private void initRegister1(View view1) {
        view1.findViewById(R.id.btn_get_code).setOnClickListener(this);
        et_phone = (EditText) view1.findViewById(R.id.et_phone);
    }
    private void initRegister2(View view2) {
        view2.findViewById(R.id.btn_select_school).setOnClickListener(this);
        tv_get_code =  (TextView) view2.findViewById(R.id.tv_get_code);
        tv_get_code.setOnClickListener(this);
        et_psw = (EditText)view2.findViewById(R.id.et_psw);
        et_code = (EditText)view2.findViewById(R.id.et_code);
        iv_eye = (ImageView)view2.findViewById(R.id.iv_eye);
        iv_eye.setOnClickListener(this);

    }
    private void initRegister3(View view3) {
        view3.findViewById(R.id.btn_finish).setOnClickListener(this);
        view3.findViewById(R.id.tv_get_yqm).setOnClickListener(this);

        et_sname = (EditText) view3.findViewById(R.id.et_sname);
        et_ccode = (EditText) view3.findViewById(R.id.et_ccode);
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_get_code){
            if (!PhoneUtils.is(et_phone.getText().toString())){
                showToast("请输入正确的手机号码");
                return;
            }
            mStep = 1;
            viewPager.setCurrentItem(1);
            iv_psw.setSelected(true);
        }
        else if (v.getId()==R.id.btn_select_school){//选择学校
            if(TextUtils.isEmpty(et_code.getText().toString())){
                showToast("验证码不能为空");
                return;
            }
            String psw = et_psw.getText().toString();
            if (psw.length()<6||psw.length()>16){
                showToast("密码只能为6-16位");
                return;
            }
            mStep = 2;
            viewPager.setCurrentItem(2);
            iv_stu.setSelected(true);
        }
        else if (v.getId()==R.id.btn_finish){//完成注册
            String sname =  et_sname.getText().toString();
            //String scode =  et_scode.getText().toString();
            String ccode =  et_ccode.getText().toString();
            if(TextUtils.isEmpty(sname)){
                showToast("学生姓名不能为空");
                return;
            }
            /*if(TextUtils.isEmpty(scode)){
                showToast("学生姓名不能为空");
                return;
            }*/
            if(TextUtils.isEmpty(ccode)){
                showToast("学校邀请码不能为空");
                return;
            }
            showDialog(this, "确定注册信息", "您的学校注册后将无法修改，是否确定当前注册信息。", new CallBack() {
                @Override
                public void suc(Object obj) {
                    register();
                }
            });

        }
        else if(v.getId()==R.id.iv_back){
            switch (mStep){
                case 0:
                    this.finish();
                    break;
                case 1:
                    viewPager.setCurrentItem(0);
                    iv_psw.setSelected(false);
                    break;
                case 2:
                    viewPager.setCurrentItem(1);
                    iv_stu.setSelected(false);
                    break;
                default:
                    break;
            }
            mStep--;
        }
        else if(v==tv_get_code){
            getSms(et_phone.getText().toString());
        }
        else if(v.getId()==R.id.iv_eye){
            WidgetUtils.eye(iv_eye,et_psw);
        }
        else if(v.getId()==R.id.tv_get_yqm){//获取班级邀请码
            Intent i = new Intent();
            i.setClass(this,SelectSchoolActivity.class);
            startActivityForResult(i,1);
        }
    }
    private void getSms(String phone) {
        HttpParams params = new HttpParams();
        params.put("phone", phone);
        J.http().post(Urls.GET_CODE, ctx, params, new HttpCallback< Respond<String>>(ctx) {
            @Override
            public void success(Respond<String> res, Call call, Response response, boolean isCache) {
                if(Respond.SUC.equals(res.getCode())){
                    showToast(res.getMsg());
                    new TimeUtils(tv_get_code,"获取短信验证码").RunTimer();
                }
            }
        });
    }

    /**
     * 注册
     */
    private void register() {
        HttpParams params = new HttpParams();
        params.put("phone", et_phone.getText().toString());
        params.put("name", et_sname.getText().toString());
        params.put("code", et_code.getText().toString());
        params.put("scode", et_ccode.getText().toString());
        params.put("password", et_psw.getText().toString());
        J.http().post(Urls.REGISTER, ctx, params, new HttpCallback< Respond<Login>>(ctx) {
            @Override
            public void success(Respond<Login> res, Call call, Response response, boolean isCache) {
                if(Respond.SUC.equals(res.getCode())){
                    SPUtil.clear(RegisterActivity.this);
                    /*Login loginData = res.getData();
                    loginData.save(loginData);*/
                    showToast("注册成功！");
                    /*gotoActivity(MainActivity.class, null);
                    RegisterActivity.this.finish();*/

                    Login loginData = res.getData();
                    SPUtil.putString(RegisterActivity.this,"user_head",loginData.getUser_head());
                    SPUtil.putString(RegisterActivity.this,"user_name",loginData.getUser_name());
                    SPUtil.putString(RegisterActivity.this,"user_sex", loginData.getUser_sex());
                    SPUtil.putString(RegisterActivity.this,"user_id",loginData.getUser_id()+"");
                    loginData.save(loginData);
                    TApplication.getInstant().setHasGotoLogin(false);
                    gotoActivity(MainActivity.class, null);
                    RegisterActivity.this.finish();

                }
                else{
                    showToast(res.getMsg());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            String code = data.getStringExtra("code");
           // showToast(resultCode+","+code);
            et_ccode.setText(code);
        }
    }
}
