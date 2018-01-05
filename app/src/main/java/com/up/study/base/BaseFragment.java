package com.up.study.base;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.up.common.base.AbsFragment;
import com.up.teacher.R;


/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/3/8.
 */

public abstract class BaseFragment extends AbsFragment {
    protected BaseFragmentActivity mParentActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        super.onAttach(context);
        mParentActivity=(BaseFragmentActivity)context;
    }

    private AlertDialog dialog;

    public void showDialog(Context ctx, String title, String content,final CallBack callBack){
        if (dialog==null){
            dialog = new AlertDialog.Builder(ctx).create();
        }
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_hint);

        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) window.findViewById(R.id.tv_content);
        TextView tv_left = (TextView) window.findViewById(R.id.tv_left);
        TextView tv_right = (TextView) window.findViewById(R.id.tv_right);

        tv_title.setText(title+"");
        tv_content.setText(content+"");
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.suc(0);
                dialog.cancel();
            }
        });

    }

}
