package com.up.study.model;

import android.text.TextUtils;

import com.up.common.base.BaseBean;

/**
 * TODO:
 * Created by 王剑洪
 * On 2017/8/17.
 */

public class ExerTestModel extends BaseBean {
    private int difficulty;
    private String year;
    int count;
    int id;
    String title;
    String per;
    private boolean isSelect;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPer() {
        if (TextUtils.isEmpty(per)) {
            return 0 + "";
        }
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
