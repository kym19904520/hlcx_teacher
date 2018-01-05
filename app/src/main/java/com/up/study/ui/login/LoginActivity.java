package com.up.study.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.common.utils.WidgetUtils;
import com.up.study.MainActivity;
import com.up.study.TApplication;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.Login;
import com.up.teacher.R;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseFragmentActivity {

    private TextView tv_forget_psw, tv_register;
    private Button btn_login;
    private EditText et_psw, et_phone;
    private ImageView iv_eye,iv_clean_phone,iv_clean_password;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showToast("onNewIntent");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.act_login;
    }

    @Override
    protected void initView() {
        tv_forget_psw = bind(R.id.tv_forget_psw);
        btn_login = bind(R.id.btn_login);
        tv_register = bind(R.id.tv_register);
        et_phone = bind(R.id.et_phone);
        et_psw = bind(R.id.et_psw);
        iv_eye = bind(R.id.iv_eye);
        iv_clean_phone = bind(R.id.iv_clean_phone);
        iv_clean_password = bind(R.id.iv_clean_password);

        //获取的保存账号
        SharedPreferences share=getSharedPreferences("account",MODE_PRIVATE);
        String accountNumber = share.getString("accountNumber", "");
        et_phone.setText(accountNumber);
        if (!TextUtils.isEmpty(et_phone.getText())){
            iv_clean_phone.setVisibility(View.VISIBLE);
            et_phone.setSelection(et_phone.getText().length());
        }
    }

    @Override
    protected void initEvent() {
        tv_register.setOnClickListener(this);
        tv_forget_psw.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        iv_eye.setOnClickListener(this);
        iv_clean_phone.setOnClickListener(this);
        iv_clean_password.setOnClickListener(this);
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_phone.setVisibility(View.GONE);
                }
            }
        });
        et_psw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_password.getVisibility() == View.GONE) {
                    iv_clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    s.delete(temp.length() - 1, temp.length());
                    et_psw.setSelection(s.length());
                }
            }
        });
    }

    @Override
    protected void initData() {
        boolean nologin = getIntent().getBooleanExtra("noLogin", false);
        if (nologin) {
            SPUtil.clear(ctx);
            showToast("请重新登录");
        }
    }

    @Override
    public void onClick(View v) {
        if (tv_forget_psw == v) {
            gotoActivity(ForgetPswActivity.class, null);        //忘记密码
        } else if (btn_login == v) {
            login();                                            //登录
        } else if (v == tv_register) {
            gotoActivity(RegisterActivity.class, null);         //注册
        } else if (v == iv_eye) {
            WidgetUtils.eye(iv_eye, et_psw);
        }else if (v == iv_clean_phone){
            et_phone.setText("");
        }else if (v == iv_clean_password){
            et_psw.setText("");
        }
    }

    /**
     * 登录的请求
     */
    private void login() {
        final String userTel = et_phone.getText().toString();
        final String userPassword = et_psw.getText().toString();
        if (TextUtils.isEmpty(userTel)) {
            showToast("手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            showToast("密码不能为空");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("account", userTel);
        params.put("password", userPassword);
        J.http().post(Urls.LOGIN, ctx, params, new HttpCallback<Respond<Login>>(ctx) {
            @Override
            public void success(Respond<Login> res, Call call, Response response, boolean isCache) {

                if (Respond.SUC.equals(res.getCode())) {
                    SPUtil.clear(LoginActivity.this);
                    Login loginData = res.getData();
                    //保存用户登录的信息
                    SPUtil.putString(LoginActivity.this,"user_head",loginData.getUser_head());
                    SPUtil.putString(LoginActivity.this,"user_name",loginData.getUser_name());
                    SPUtil.putString(LoginActivity.this,"user_sex", loginData.getUser_sex());
                    SPUtil.putString(LoginActivity.this,"user_id",loginData.getUser_id()+"");
                    if (loginData.getCalsss() != null) {
                        SPUtil.putString(LoginActivity.this, "grade_name", loginData.getCalsss().getGrade_name());
                        SPUtil.putString(LoginActivity.this, "grade_id", loginData.getCalsss().getGrade() + "");
                    }
                    //保存账号
                    SharedPreferences sp = getSharedPreferences("account",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("accountNumber",userTel);
                    editor.commit();

                    loginData.save(loginData);
                    TApplication.getInstant().setHasGotoLogin(false);
                    gotoActivity(MainActivity.class, null);
                    LoginActivity.this.finish();
                }
            }
        });
    }
}
