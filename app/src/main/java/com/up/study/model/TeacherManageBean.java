package com.up.study.model;

/**
 * Created by kym on 2017/8/7.
 */

public class TeacherManageBean {

    /**
     * tccid : 5
     * uid : 6
     * class_id : 1
     * create_date : 1500207773000
     * is_activate : 1
     * name : 易老师
     * type : 0
     * cname : 三年级（3）班
     * coursename : 语文
     * phone : 13950406317
     * head :
     */

    private int tccid;
    private int uid;
    private int class_id;
    private long create_date;
    private int is_activate;
    private String name;
    private int type;
    private String cname;
    private String coursename;
    private String phone;
    private String head;

    public int getTccid() {
        return tccid;
    }

    public void setTccid(int tccid) {
        this.tccid = tccid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public int getIs_activate() {
        return is_activate;
    }

    public void setIs_activate(int is_activate) {
        this.is_activate = is_activate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
