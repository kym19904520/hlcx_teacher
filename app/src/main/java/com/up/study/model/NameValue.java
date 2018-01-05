package com.up.study.model;

import android.text.TextUtils;

import com.up.common.base.BaseBean;

import java.util.List;

/**
 * TODO:
 * <<<<<<< Updated upstream
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/8/7.
 */

public class NameValue extends BaseBean {
    private String name;
    private int value;
    private boolean isSelected;

    private List<NameValue> childRen;

    private String courseName;
    private int courseId;

    public List<NameValue> getChildRen() {
        return childRen;
    }

    public void setChildRen(List<NameValue> childRen) {
        this.childRen = childRen;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        if (TextUtils.isEmpty(name)) {
            return courseName;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        if (value==0){
            return courseId;
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
