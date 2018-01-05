package com.up.study.ui.my;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.common.utils.StudyUtils;
import com.up.common.widget.GlideCircleTransform;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.base.CallBack;
import com.up.study.model.ImgUrl;
import com.up.study.model.MessageEventBus;
import com.up.study.model.MyMessageBean;
import com.up.study.ui.MyFragment;
import com.up.teacher.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 个人信息
 * Created by kym on 2017/7/20.
 */

public class MyMessageActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_my_portrait)
    RelativeLayout rlMyPortrait;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.tv_school)
    TextView tvSchool;
    @Bind(R.id.tv_my_name)
    TextView tvMyName;
    @Bind(R.id.rl_my_name)
    RelativeLayout rlMyName;
    @Bind(R.id.tv_my_sex)
    TextView tvMySex;
    @Bind(R.id.rl_my_sex)
    RelativeLayout rlMySex;
    @Bind(R.id.tv_my_birthday)
    TextView tvMyBirthday;
    @Bind(R.id.rl_my_birthday)
    RelativeLayout rlMyBirthday;
    @Bind(R.id.tv_teacher_authentication)
    TextView tvTeacherAuthentication;
    @Bind(R.id.iv_portrait)
    ImageView ivPortrait;

    private String[] sex;
    private String mySex;
    private int choiceId = -1;
    private String name;
    private int sexId;
    private String textString;
    private String head;
    private List<String> imageList = new ArrayList<>();
    private String imgPath;
    private TimePickerView pickerView;

    @Override
    protected int getContentViewId() {
        return R.layout.act_my_message;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        setBirthday();
        Resources res = getResources();
        sex = res.getStringArray(R.array.my_sex);
        head = SPUtil.getString(this, "user_head", "");
        J.image().loadCircle(ctx,head,ivPortrait);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.my_personage_message);
        getData();
        //注册
        EventBus.getDefault().register(MyMessageActivity.this);
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick({R.id.iv_back, R.id.rl_my_portrait, R.id.rl_my_name, R.id.rl_my_sex, R.id.rl_my_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_my_portrait:
                setPortrait();
                break;
            case R.id.rl_my_name:
                gotoActivity(PersonageNameActivity.class, "sex", sexId + "");
                break;
            case R.id.rl_my_sex:
                setSex(choiceId);
                break;
            case R.id.rl_my_birthday:
                pickerView.show();
                break;
        }
    }

    /**
     * 设置头像
     */
    private void setPortrait() {
        GalleryFinal.openGallerySingle(0, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                imgPath = resultList.get(0).getPhotoPath();
                imageList.add(imgPath);
                StudyUtils.uploadImgUrl(imageList, ctx, new CallBack<List<ImgUrl>>() {
                    @Override
                    public void suc(List<ImgUrl> obj) {
                        saveMessage(sexId, textString, obj.get(0).getUrl());
                        SPUtil.putString(MyMessageActivity.this, "user_head", imgPath);
                    }
                });
                Glide.with(MyMessageActivity.this).load(resultList.get(0).getPhotoPath()).
                        bitmapTransform(new GlideCircleTransform(MyMessageActivity.this)).crossFade(1000).into(ivPortrait);
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    /**
     * 获取个人的信息
     */
    public void getData() {
        HttpParams params = new HttpParams();
        J.http().post(Urls.MY_MESSAGE_URL, ctx, params, new HttpCallback<Respond<MyMessageBean>>(ctx) {
            @Override
            public void success(Respond<MyMessageBean> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    MyMessageBean myMessageBean = res.getData();
                    if (myMessageBean != null) {
                        tvAccount.setText(myMessageBean.getAccount());
                        tvSchool.setText(myMessageBean.getSchool_name());
                        name = myMessageBean.getName();
                        tvMyName.setText(name);
                        sexId = myMessageBean.getSex();
                        switch (sexId) {
                            case 0:
                                tvMySex.setText("男");
                                break;
                            case 1:
                                tvMySex.setText("女");
                                break;
                        }
                        tvMyBirthday.setText((new SimpleDateFormat("yyyy-MM-dd")).format(myMessageBean.getBirthday()));
                        switch (myMessageBean.getAttestation()) {
                            case 0:
                                tvTeacherAuthentication.setText("未认证");
                                break;
                            case 1:
                                tvTeacherAuthentication.setText("已认证");
                                break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 设置生日
     */
    private void setBirthday() {
//        Calendar c = Calendar.getInstance();
//        // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
//        new DoubleDatePickerDialog(MyMessageActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
//                textString = startYear + "-" + (startMonthOfYear + 1) + "-" + startDayOfMonth;
//                String string = startYear + ""+(startMonthOfYear + 1)+ ""+startDayOfMonth;
//                tvMyBirthday.setText(textString);
//                saveMessage(sexId, textString,null);
//            }
//        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 28);
        //时间选择器
        pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textString = getTime(date);
                tvMyBirthday.setText(textString);
                saveMessage(sexId, textString,null);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.WHITE)
                .setContentSize(21)
                .isCyclic(true)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEventBus(MessageEventBus eventBus) {
        tvMyName.setText(eventBus.message);
        SPUtil.putString(MyMessageActivity.this, "user_name", eventBus.message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyFragment.instance.refresh();
        //解注册
        EventBus.getDefault().unregister(MyMessageActivity.this);
    }

    /**
     * 设置性别
     */
    private void setSex(int choiceItemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.my_select_sex);
        builder.setSingleChoiceItems(sex, choiceItemId, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mySex = sex[which];
                choiceId = which;
            }
        });
        builder.setPositiveButton(R.string.my_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvMySex.setText(mySex);
                saveMessage(choiceId, textString, null);
                SPUtil.putString(MyMessageActivity.this, "user_sex", mySex);
            }
        }).setNegativeButton(R.string.my_cancel, null);
        builder.show();
    }

    /**
     * 提交修改的信息
     * @param sex  性别
     * @param birthday  生日
     * @param img   头像
     */
    public void saveMessage(int sex, String birthday, String img) {
        HttpParams params = new HttpParams();
        params.put("name", name);
        params.put("sex", sex + "");
        params.put("birthday", birthday);
        params.put("head", img);
        J.http().post(Urls.SAVE_MESSAGE_URL, ctx, params, new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    showLog("修改" + res.getMsg());
                }
            }
        });
    }

    //时间格式化
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}

