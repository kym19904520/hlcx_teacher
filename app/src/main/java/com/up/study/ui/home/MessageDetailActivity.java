package com.up.study.ui.home;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.up.common.utils.WidgetUtils;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.MesModel;
import com.up.teacher.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息详情
 */
public class MessageDetailActivity extends BaseFragmentActivity {
    WebView content;
    TextView tv_time;
    MesModel mesModel;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected int getContentViewId() {
        return R.layout.act_message_detail;
    }

    @Override
    protected void initView() {

        content=(WebView)this.findViewById(R.id.wv_content);
        tv_time = bind(R.id.tv_time);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        mesModel = (MesModel)getIntent().getSerializableExtra("bean1");

        String createTime = mesModel.getCreate_date();
        if (!TextUtils.isEmpty(createTime)){
            createTime  =  sdf.format(new Date(Long.parseLong(createTime)));
        }
        tv_time.setText(createTime);
        WidgetUtils.initWebView(content,mesModel.getContent());
    }

    @Override
    public void onClick(View v) {
    }

}
