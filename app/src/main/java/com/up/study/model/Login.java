package com.up.study.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.up.common.base.BaseBean;
import com.up.common.utils.SPUtil;
import com.up.study.TApplication;

/**
 * TODO:登录
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by yy_cai
 * On 2017/3/12.
 */

public class Login extends BaseBean {
    /*private  ClasssModel classs;
    private  StudentModel student;
    private  UserModel user;

    public ClasssModel getClasss() {
        return classs;
    }

    public void setClasss(ClasssModel classs) {
        this.classs = classs;
    }

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }*/

    private ClassModel calsss;


    private String IMGROOT;
    private String user_name;
    private String school_name;
    private String user_head;
    private String major_name;
    private int user_sex;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIMGROOT() {
        return IMGROOT;
    }

    public void setIMGROOT(String IMGROOT) {
        this.IMGROOT = IMGROOT;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getUser_sex() {
        return user_sex == 0 ? "男" : "女";
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public ClassModel getCalsss() {
        return calsss;
    }

    public void setCalsss(ClassModel calsss) {
        this.calsss = calsss;
    }

    public void save(Login login) {
        SPUtil.put(TApplication.getInstance(), "user", new Gson().toJson(login));
    }

    public Login get() {
        String loginInfo = (String) SPUtil.get(TApplication.getInstance(), "user", "");
        if (TextUtils.isEmpty(loginInfo)) {
            return null;
        }
        return new Gson().fromJson(loginInfo, Login.class);
    }
}
