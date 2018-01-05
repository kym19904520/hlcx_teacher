package com.up.study.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.up.common.base.AbsFragmentActivity;
import com.up.common.conf.Constants;
import com.up.teacher.R;

import butterknife.ButterKnife;

/**
 * TODO:
 * Created by 王剑洪
 * On 2017/3/6.
 */

public abstract class BaseFragmentActivity extends AbsFragmentActivity {

    @Override
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        View back=bind(R.id.iv_back);
        if (null!=back){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTIVITY_FINISH);    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);
    }


    private ReceiveBroadCast receiveBroadCast;

    public class ReceiveBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiveBroadCast!=null) {
            unregisterReceiver(receiveBroadCast);
        }
        ButterKnife.unbind(this);
    }

    protected void closeAllActivty(){
        Intent intent = new Intent();  //Itent就是我们要发送的内容
        intent.setAction(Constants.ACTIVITY_FINISH);   //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        ctx.sendBroadcast(intent);   //发送广播
    }


    /**
     * 有数据，隐藏空view
     */
    protected void HideEmptyView(){
        bind(R.id.ll_empty).setVisibility(View.GONE);
    }
    /**
     * 无数据，展示空view
     */
    protected void showEmptyView(String text){
        bind(R.id.ll_empty).setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(text)){
            TextView tv = bind(R.id.tv_empty_text);
            tv.setText(text);
        }
    }
}
