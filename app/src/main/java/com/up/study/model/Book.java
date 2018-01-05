package com.up.study.model;

import java.io.Serializable;

/**
 * Created by win10 on 2017/7/24.
 */

public class Book implements Serializable{

    private int id;

    private String name;

    //0-未选中 1-选中
    private int status;


    public Book(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
