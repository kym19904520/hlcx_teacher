package com.up.study.ui.home;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.up.common.J;
import com.up.common.base.CommonAdapter;
import com.up.common.base.ViewHolder;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.MainActivity;
import com.up.study.TApplication;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.ClassModel;
import com.up.study.params.Params;
import com.up.study.ui.HomeFragment;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:
 * Created by 王剑洪
 * On 2017/7/27.
 */

public class ChooseClassActivity extends BaseFragmentActivity {

    private TextView tvOneDay, tvTowDay, tvThreeDay;
    private ListView lv;

    private TextView[] tvDays = new TextView[3];

    private List<ClassModel> list = new ArrayList<>();
    private CommonAdapter<ClassModel> adapter;

    private int paperId = 0;//试卷ID
    private Integer grade = null;//年级
    private int courseId = 0;//科目Id
    private int day = 1;
    private int code = 110;


    @Override
    protected int getContentViewId() {
        return R.layout.act_choose_class;
    }

    @Override
    protected void initView() {
        tvOneDay = bind(R.id.tv_one_day);
        tvTowDay = bind(R.id.tv_tow_day);
        tvThreeDay = bind(R.id.tv_three_day);
        tvDays[0] = tvOneDay;
        tvDays[1] = tvTowDay;
        tvDays[2] = tvThreeDay;

        lv = bind(R.id.lv);

    }

    @Override
    protected void initData() {
        Map<String, Object> map = getMap();
        paperId = (int) map.get("paperId");
        courseId = (int) map.get("courseId");
        code = (int) map.get("code");
        try {
            grade = (Integer) map.get("grade");
        } catch (Exception e) {
        }
        adapter = new CommonAdapter<ClassModel>(ctx, list, R.layout.item_lv_class) {
            @Override
            public void convert(ViewHolder vh, ClassModel item, int position) {
                ImageView iv = vh.getView(R.id.iv_select);
                iv.setSelected(item.isCheck());
                vh.setText(R.id.tv_name, item.getName());
                vh.setText(R.id.tv_num, item.getStudentCount() + "人");

            }
        };
        lv.setAdapter(adapter);
        setDay(0);
        loadClass();

    }

    @Override
    protected void initEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean is = list.get(i).isCheck();
                list.get(i).setCheck(!is);
                adapter.NotifyDataChanged(list);
            }
        });
        tvOneDay.setOnClickListener(this);
        tvTowDay.setOnClickListener(this);
        tvThreeDay.setOnClickListener(this);
        bind(R.id.btn_sure).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one_day:
                setDay(0);
                break;
            case R.id.tv_tow_day:
                setDay(1);
                break;
            case R.id.tv_three_day:
                setDay(2);
                break;
            case R.id.btn_sure:
                publish();
                break;
        }

    }

    private void setDay(int index) {
        for (int i = 0; i < 3; i++) {
            tvDays[i].setSelected(false);
        }
        tvDays[index].setSelected(true);
        day = index + 1;
    }

    private void loadClass() {

        J.http().post(Urls.GET_CLASS_SCREEN, ctx, Params.getTeacherClass(paperId, grade, courseId,TApplication.getInstant().isSection()), new HttpCallback<Respond<List<ClassModel>>>(ctx) {
            @Override
            public void success(Respond<List<ClassModel>> res, Call call, Response response, boolean isCache) {
                list = (null == res.getData()) ? new ArrayList<ClassModel>() : res.getData();
                adapter.NotifyDataChanged(list);
            }
        });
    }

    private void publish() {
        String classId = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheck()) {
                if (!TextUtils.isEmpty(classId)) {
                    classId += "@";
                }
                classId += list.get(i).getClassId();
            }
        }
        if (TextUtils.isEmpty(classId)) {
            showToast("请选择班级");
            return;
        }
        J.http().post(Urls.NORMAL_PUBLISH, ctx, Params.publish(paperId, classId, day, TApplication.getInstant().getRelationType()), new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                showToast(res.getMsg());
                setResult(code);
                ChooseClassActivity.this.finish();
                if (code == 0){
                    gotoActivity(MainActivity.class,null);
                }
                HomeFragment.instance.refresh();
            }
        });
    }
}
