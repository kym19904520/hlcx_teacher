package com.up.common.http;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.BaseRequest;
import com.up.common.base.BaseApplication;
import com.up.common.conf.Constants;
import com.up.common.utils.L;
import com.up.common.widget.LoadingDialog;
import com.up.study.TApplication;
import com.up.study.ui.login.LoginActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/3/25.
 */

public abstract class HttpCallback<T> extends BaseCallback<T> {
    LoadingDialog loadingDialog;
    private Context ctx;//ctx不能为空
    private String str="加载中..";
    private boolean isCanCancel=true;//点击dialog周围区域是否关闭dialog
    private boolean hideDialog=false;//是否隐藏dialog
    private boolean returnSuc = true;//直接返回成功状态
    public HttpCallback(Context context, String str) {
        this.ctx = context;
        this.str=str;
    }

    public HttpCallback(Context context){
        this.ctx = context;
    }
    public HttpCallback(Context context,boolean returnSuc){
        this.ctx = context;
        this.returnSuc = returnSuc;
    }
    public HttpCallback(Context context,boolean hideDialog,boolean isCanCancel){
        this.ctx = context;
        this.hideDialog = hideDialog;
        this.isCanCancel = isCanCancel;
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();
        L.d("服务端返回数据------->>", s);
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        T t = new Gson().fromJson(s, type);
        Respond respond = (Respond) t;
        if(Respond.NO_LOGIN.equals(respond.getCode())){
            //TApplication.getInstant().showToast("您的账号已在其他机子上登录，请重新登录");
            //关闭所有的Activity
            if(!TApplication.getInstant().isHasGotoLogin()){
                TApplication.getInstant().setHasGotoLogin(true);//登录接口完成设为false
                Intent intent = new Intent();  //Itent就是我们要发送的内容
                intent.setAction(Constants.ACTIVITY_FINISH);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
                ctx.sendBroadcast(intent);   //发送广播

                //重新登录
                Intent intent1 = new Intent();
                intent1.setClass(ctx, LoginActivity.class);
                intent1.putExtra("noLogin",true);
                ctx.startActivity(intent1);
            }

        }
        else if (!Respond.SUC.equals(respond.getCode())&&returnSuc) {
            throw new Exception(respond.getMsg());
        }

        return new Gson().fromJson(s, type);
    }

    @Override
    public void start(BaseRequest request) {
        try {
            if (ctx != null && !hideDialog) {
                loadingDialog = new LoadingDialog(ctx);
                loadingDialog.setText("加载中..");
                loadingDialog.setCancelable(isCanCancel);
                loadingDialog.show();
            }
        }catch (Exception e) {
           e.printStackTrace();
        }
    }

    @Override
    public abstract void success(T res, Call call, Response response, boolean isCache);

    @Override
    public void error(Call call, Response response, Exception e, boolean isCache) {
        L.e("error----->>", e.getMessage());
//        loadingDialog.dismiss();
        if (e.getMessage().contains("Failed to connect")){
            Toast.makeText(BaseApplication.getInstance(), "网络信号差，无法连接到服务器", Toast.LENGTH_SHORT).show();
            return;
        }
        if(e.toString().contains("TimeoutException")){
            Toast.makeText(BaseApplication.getInstance(),"连接超时，暂时连接不上服务器~",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(BaseApplication.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish(T s, Exception e) {
        try {
            if (null != loadingDialog) {
                loadingDialog.dismiss();
            }
        }catch (Exception ex) {
           ex.printStackTrace();
        }
    }
}
