package com.up.study.model;

import com.up.common.model.BaseBean;

/**
 * Created by dell on 2017/8/8.
 */

public class HomeTaskModel extends BaseBean {
    private int id;
    private int peopleTotal;
    private int submitTotal;
    private String title;
    private String className;
    private String deadline;

    private int classId;//传递参数用的
    private int relationType;//传递参数用的

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeopleTotal() {
        return peopleTotal;
    }

    public void setPeopleTotal(int peopleTotal) {
        this.peopleTotal = peopleTotal;
    }

    public int getSubmitTotal() {
        return submitTotal;
    }

    public void setSubmitTotal(int submitTotal) {
        this.submitTotal = submitTotal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
