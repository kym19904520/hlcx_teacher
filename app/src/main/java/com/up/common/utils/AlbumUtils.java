package com.up.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;

import com.up.common.J;
import com.up.teacher.R;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class AlbumUtils {

    public static final int REQUEST_CODE = 10086;

    private static ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            J.image().load(context, path, imageView);
        }
    };

    public static void init(Context context, int maxCount) {
        ImgSelConfig config = new ImgSelConfig.Builder(context, loader)
                // 是否多选, 默认true
                .multiSelect(maxCount != 1)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(context.getResources().getColor(R.color.colorPrimary))
                // 返回图标ResId
                .backResId(R.mipmap.ic_back)
                // 标题
                .title("图片选择")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(context.getResources().getColor(R.color.colorPrimary))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(maxCount)
                .build();
        // 跳转到图片选择器
        ImgSelActivity.startActivity((Activity) context, config, REQUEST_CODE);
    }

    public static ArrayList<String> activityResult(int requestCode, int resultCode, Intent data) {
        // 图片选择结果回调//RESULT_OK
        if (requestCode == REQUEST_CODE && resultCode == -1 && data != null) {
            ArrayList<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            return pathList;
        }
        return new ArrayList<>();
    }


}
