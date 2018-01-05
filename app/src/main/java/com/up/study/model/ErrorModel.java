package com.up.study.model;

import com.up.common.model.BaseBean;

/**
 * Created by dell on 2017/8/10.
 */

public class ErrorModel extends BaseBean {
    private String head;
    private String errorName;
    private String studentName;
    private String err_attached;
    private String content;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getErr_attached() {
        return err_attached;
    }

    public void setErr_attached(String err_attached) {
        this.err_attached = err_attached;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
