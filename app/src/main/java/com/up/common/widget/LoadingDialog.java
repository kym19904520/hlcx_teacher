package com.up.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.up.teacher.R;


/**
 * Todo
 * Created by 王剑洪
 * on 2016/7/13.
 */
public class LoadingDialog extends Dialog {

    private TextView tv;
    private String text="";

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading);
        tv = (TextView)findViewById(R.id.tv);
        tv.setText(TextUtils.isEmpty(getText())? "加载中...":text);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.ll);
        linearLayout.getBackground().setAlpha(210);
        setCancelable(false);
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setCancelable(true);
        dismiss();
    }
}
