package com.up.study;

import android.content.Context;
import android.widget.Toast;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.base.BaseApplication;
import com.up.common.utils.Logger;
import com.up.study.model.Login;
import com.up.study.weight.GlideImageLoader;
import com.up.teacher.R;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by yy_Cai
 * On 2017/3/12.
 */

public class TApplication extends BaseApplication {

    private static Context mContext;
    private static TApplication instant;
    public static TApplication getInstant(){
        return instant;
    }

    private boolean hasGotoLogin=false;
    private Login loginInfo;
    private int relationType;//试卷类型 1：试卷录入 2：线上作业

    private boolean isClear=true;//是否清除已经选中的试题id
    private String subIds="";//点击筛选，保存当前已选中的试题
    private boolean isSection=true;//在线练习，当前是否处于章节tab下
    @Override
    public void onCreate() {
        super.onCreate();
        instant=this;
        HttpParams params = new HttpParams();
        params.put("rows", 10);//默认每页20条
        J.initHttp(this, null, params);
        initGalleryFinal();
    }

    public static Context getContext() {
        return mContext;
    }

    public static TApplication get(Context context) {
        return (TApplication) context.getApplicationContext();
    }

    private void initGalleryFinal(){
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(getResources().getColor(R.color.colorPrimary))
        .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        ImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(instant, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public Login getLoginInfo(){

        if(loginInfo==null) {
            Logger.i(Logger.TAG,"111111111111111");
            return  new Login().get();
        }
        else{
            Logger.i(Logger.TAG,"22222222222222");
            return loginInfo;
        }
    }
    public String getImgUrlHead(){
        return getLoginInfo().getIMGROOT();
    }
    public boolean isHasGotoLogin() {
        return hasGotoLogin;
    }

    public void setHasGotoLogin(boolean hasGotoLogin) {
        this.hasGotoLogin = hasGotoLogin;
    }

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public String getSubIds() {
        return subIds;
    }

    public void setSubIds(String subIds) {
        this.subIds = subIds;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
    }
}
