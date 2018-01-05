package com.up.study.ui.my;

import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.MessageEventBus;
import com.up.study.ui.MyFragment;
import com.up.teacher.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 个人信息----姓名
 * Created by kym on 2017/7/19.
 */

public class PersonageNameActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_manage)
    TextView tvManage;
    @Bind(R.id.et_class_another_name)
    EditText etMyName;
    private String sex;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_another_name;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        tvManage.setVisibility(View.VISIBLE);
        sex = getIntent().getStringExtra("sex");
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_name);
        tvManage.setText(R.string.class_save);
        etMyName.setHint(R.string.my_import_name);
        etMyName.setInputType(InputType.TYPE_CLASS_TEXT);
        InputFilter[] filters = {new InputFilter.LengthFilter(10)};
        etMyName.setFilters(filters);
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick(R.id.tv_manage)
    public void onViewClicked() {
        if (etMyName.getText().toString().isEmpty()){
            showToast("请输入姓名");
            return;
        }
        //发送消息+
        EventBus.getDefault().post(new MessageEventBus(etMyName.getText().toString().trim()));
        saveMyName(etMyName.getText().toString());
    }

    /**
     * 提交修改的姓名
     * @param name  姓名
     */
    public void saveMyName(final String name) {
        HttpParams params = new HttpParams();
        params.put("name",name);
        params.put("sex",sex);
        J.http().post(Urls.SAVE_MESSAGE_URL, ctx, params, new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    showToast(res.getMsg());
                    SPUtil.putString(PersonageNameActivity.this,"user_name",name);
                    MyFragment.instance.refresh();
                    gotoActivity(MyMessageActivity.class,null);
                }
            }
        });
    }
}
