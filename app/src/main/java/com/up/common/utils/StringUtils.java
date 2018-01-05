package com.up.common.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/7/8.
 */

public class StringUtils {
    /**
     * 判断数组里是否每个值都为空
     */
    public static boolean isArrayEmpty(String[] strs){
        for (int i=0;i<strs.length;i++){
            if (!TextUtils.isEmpty(strs[i])){
                return false;
            }
        }
        return true;
    }
    public static void Log(String[] strs){
        String s ="";
        for (int i = 0;i<strs.length;i++){
            s += "index=["+i+"],value="+strs[i]+"-----";
        }
        Logger.i(Logger.TAG,"字符串数组各个值："+s);
    }

    /**
     * 非空的才转换
     */
    public static List<String> ArrayToList(String[] strs){
        List<String> list = new ArrayList<>();
        for (int i = 0;i<strs.length;i++){
            if (!TextUtils.isEmpty(strs[i])){
                list.add(strs[i]);
            }
        }
        return list;
    }

    /**
     * 将double转为数值，并最多保留num位小数。例如当num为2时，1.268为1.27，1.2仍为1.2；1仍为1，而非1.00;100.00则返回100。
     *
     * @param d
     * @param num 小数位数
     * @return
     */
    public static String double2String(double d, int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(num);//保留两位小数
        nf.setGroupingUsed(false);//去掉数值中的千位分隔符

        String temp = nf.format(d);
        if (temp.contains(".")) {
            String s1 = temp.split("\\.")[0];
            String s2 = temp.split("\\.")[1];
            for (int i = s2.length(); i > 0; --i) {
                if (!s2.substring(i - 1, i).equals("0")) {
                    return s1 + "." + s2.substring(0, i);
                }
            }
            return s1;
        }
        return temp;
    }

    /**
     * 将double转为数值，并最多保留num位小数。
     *
     * @param d
     * @param num 小数个数
     * @param defValue 默认值。当d为null时，返回该值。
     * @return
     */
    public static String double2String(Double d, int num, String defValue){
        if(d==null){
            return defValue;
        }

        return double2String(d,num);
    }

    public static int dip2Px(Context context,int dip) {
        //dp转成px
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    public static int px2Dip(Context context,int px) {
        //px转成dp
        float density = context.getResources().getDisplayMetrics().density;

        int dip = (int) (px / density + .5f);
        return dip;
    }
}
