package com.up.common.utils;

import android.text.InputType;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

import com.up.common.conf.Constants;
import com.up.study.TApplication;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class WidgetUtils {
    /**
     * 设置密码明文或者暗文
     * @param iv_eye 眼睛控件
     * @param et_psw 密码控件
     */
    public static void eye(ImageView iv_eye, EditText et_psw){
        if (iv_eye.isSelected()){
            iv_eye.setSelected(false);
            et_psw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            et_psw.setSelection(et_psw.getText().toString().length());
        }
        else{
            iv_eye.setSelected(true);
            et_psw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            et_psw.setSelection(et_psw.getText().toString().length());
        }
    }
    public static void initWebView(WebView webView,String content){
        if(!content.toString().contains("http")&&(content.toString().contains("doc-dir")||content.toString().contains("user-dir"))){
            String host = TApplication.getInstant().getLoginInfo().getIMGROOT();
            Logger.i(Logger.TAG,"webview添加域名头:"+host);
            content = content.replace("src=\"","src=\""+"http://"+ host+ "/" );
            Logger.i(Logger.TAG,"answerJson:"+content);
        }

        webView.setVisibility(View.VISIBLE);
        WebSettings mysettings = webView.getSettings();
        mysettings.setSupportZoom(true);
        mysettings.setJavaScriptEnabled(true);// 设置支持Javascript
        //mysettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //mysettings.setBuiltInZoomControls(true);
        mysettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.addJavascriptInterface(new JavaScriptInterface(null), "imagelistner");//这个是给图片设置点击监
        webView.requestFocus();// 触摸焦点起作用
        //webView.loadUrl("http://mall.5i84.cn/?CASTGC=TGT-93962-ajhGWVtVTP7QG0kKVRPiALaZN9ynr7HWLd9blBgpld4VgOS5rO-5i84.cn");
        // webView.loadData(seqModel.getTitle(),null,"UTF-8");
        webView.loadDataWithBaseURL(null,content, "text/html",  "utf-8", null);
    }
    /**
     * 答案的webview
     */
    public static void initAnswerWebView(WebView webView,String answerJson){
        try {
            String answer = FormatUtils.List2String(GsonUtils.toList(answerJson));//解析不了，为HTML文本
            initWebView(webView, answer);
        }
        catch (Exception e){
            String html = answerJson.substring(2,answerJson.length()-2);//只会有一个HTML文本，
            Logger.i(Logger.TAG,"抛异常，解析HTML文本："+html);
            initWebView(webView, html);
        }
    }

}
