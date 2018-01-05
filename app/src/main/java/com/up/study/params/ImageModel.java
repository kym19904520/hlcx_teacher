package com.up.study.params;

import com.google.gson.Gson;
import com.up.common.base.BaseBean;

import java.util.List;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/8/11.
 */

public class ImageModel extends BaseBean {
    private int id;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String toJson(List<ImageModel> imageModel){
        return  new Gson().toJson(imageModel);
    }
}
