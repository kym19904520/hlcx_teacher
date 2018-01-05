package com.up.study.model;

import java.util.Map;

/**
 * Created by kym on 2017/8/10.
 */

public class ClassMessageBean {

    private String alias;
    private int status;
    private long modify_date;
    private Object blurb;
    private String code;
    private int attestation;
    private int school_id;

    public void setCoursename(Map<String, String> coursename) {
        this.coursename = coursename;
    }

    public void setAuxiliary(Map<String, String> auxiliary) {
        this.auxiliary = auxiliary;
    }

    private Map<String,String> coursename;
    private Object creator;
    private int id;
    private long create_date;

    public Map<String, String> getCoursename() {
        return coursename;
    }

    public Map<String, String> getAuxiliary() {
        return auxiliary;
    }

    private Map<String,String> auxiliary;
    private String name;
    private int class_type;
    private String invite_code;
    private int grade;
    private String year;
    private String gradename;
    private String head;
    private String cname;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getModify_date() {
        return modify_date;
    }

    public void setModify_date(long modify_date) {
        this.modify_date = modify_date;
    }

    public Object getBlurb() {
        return blurb;
    }

    public void setBlurb(Object blurb) {
        this.blurb = blurb;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAttestation() {
        return attestation;
    }

    public void setAttestation(int attestation) {
        this.attestation = attestation;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public Object getCreator() {
        return creator;
    }

    public void setCreator(Object creator) {
        this.creator = creator;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClass_type() {
        return class_type;
    }

    public void setClass_type(int class_type) {
        this.class_type = class_type;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
