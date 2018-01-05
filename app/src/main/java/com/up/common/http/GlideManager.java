package com.up.common.http;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.up.common.utils.Logger;
import com.up.common.widget.GlideCircleTransform;
import com.up.study.TApplication;
import com.up.teacher.R;


/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/4/8.
 */

public class GlideManager {
    private static GlideManager instance;
    private int defResId;//加载时候默认图片
    private int errorResId;//加载错误时候的图片

    public static synchronized GlideManager getInstance() {
        if (null == instance) {
            instance = new GlideManager();
        }
        return instance;
    }

    public GlideManager(){

    }

    private void init(int defResId, int errorResId) {
        this.defResId = defResId;
        this.errorResId = errorResId;
    }


    /**
     * load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
     * load assets资源：load("file:///android_asset/f003.gif")
     * load raw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
     * load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
     * load ContentProvider资源：load("content://media/external/images/media/139469")
     * load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
     * load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
     *
     * @param ctx
     * @param load
     * @param iv
     */
    public void load(Context ctx, Object load, ImageView iv) {
        if (null==load){
            Glide.with(ctx).load(R.mipmap.def_img).into(iv);
        }else {
            Logger.i(Logger.TAG,"wwwwwwwww图片地址："+load.toString());
            if(!load.toString().contains("http")&&load.toString().contains("user-dir")){
                Logger.i(Logger.TAG,"wwwwwwwww添加域名头:"+TApplication.getInstant().getLoginInfo().getIMGROOT());
                load = "http://" + TApplication.getInstant().getLoginInfo().getIMGROOT()+ "/" + load;
            }

            Glide.with(ctx).load(load).placeholder(R.mipmap.def_img).error(R.mipmap.def_img).into(iv);
        }

    }

    public void loadImage(Context ctx, Object load, ImageView iv) {
        if (null==load){
            Glide.with(ctx).load(R.mipmap.class_touxiang_01) .placeholder(R.mipmap.class_touxiang_01)
                    .error(R.mipmap.class_touxiang_01).transform(new GlideCircleTransform(ctx)).into(iv);
        }else {
            Glide.with(ctx).load(load). placeholder(R.mipmap.class_touxiang_01)
                  .transform(new GlideCircleTransform(ctx)).error(R.mipmap.class_touxiang_01).into(iv);
        }
    }

    public void loadCircle(Context ctx, Object load, ImageView iv){
        if (null==load){
            Glide.with(ctx).load(R.mipmap.logo).transform(new GlideCircleTransform(ctx)).into(iv);
        }else {
            Logger.i(Logger.TAG,"wwwwwwwww图片地址："+load.toString());
            if(!load.toString().contains("http")&&load.toString().contains("user-dir")){
                Logger.i(Logger.TAG,"wwwwwwwww添加域名头:"+TApplication.getInstant().getLoginInfo().getIMGROOT());
                load = "http://" + TApplication.getInstant().getLoginInfo().getIMGROOT()+ "/" + load;
            }
            Glide.with(ctx).load(load).transform(new GlideCircleTransform(ctx)).error(R.mipmap.def_img).into(iv);
        }
    }
}
