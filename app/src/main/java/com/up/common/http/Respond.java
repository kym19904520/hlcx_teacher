package com.up.common.http;

import com.up.common.base.BaseBean;
import com.up.study.model.PageInfo;

/**
 * TODO:
 * Created by 王剑洪
 * On 2017/5/12.
 */

public class Respond<T> extends BaseBean {

    public final static String SUC = "success";
    public final static String NO_LOGIN = "err_not_login";
    private String code;
    private String msg;
    private T data;
    private PageInfo pageInfo;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
