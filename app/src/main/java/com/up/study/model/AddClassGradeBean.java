package com.up.study.model;

/**
 * Created by kym on 2017/8/8.
 */

public class AddClassGradeBean {
    private int id;
    private long create_date;
    private Object name_en;
    private int status;
    private String name;
    private Object modify_date;
    private String remarks;
    private String code;
    private String type;
    private int order_by;
    private int parent_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public Object getName_en() {
        return name_en;
    }

    public void setName_en(Object name_en) {
        this.name_en = name_en;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getModify_date() {
        return modify_date;
    }

    public void setModify_date(Object modify_date) {
        this.modify_date = modify_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder_by() {
        return order_by;
    }

    public void setOrder_by(int order_by) {
        this.order_by = order_by;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
