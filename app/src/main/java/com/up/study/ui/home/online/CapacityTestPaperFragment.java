package com.up.study.ui.home.online;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.study.TApplication;
import com.up.study.base.BaseFragment;
import com.up.study.model.DifficultyBean;
import com.up.study.model.NameValue;
import com.up.study.ui.home.OnlineExerciseActivity;
import com.up.study.ui.home.ZjDetailsActivity;
import com.up.study.weight.ChooseTypeDialog;
import com.up.teacher.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 在线练习---智能组卷
 * Created by kym on 2017/8/31.
 */

public class CapacityTestPaperFragment extends BaseFragment {

    private EditText et_number01, et_number02, et_number03;
    private TextView tv_selected, tv_class_name;
    private Button btn_next;
    private String difficulty01, difficulty02, difficulty03, gradeId;
    private int number01, number02, number03;
    private RelativeLayout rl_class_selected;
    private TextView tv_total_number, tv_01, tv_02, tv_03;
    private DifficultyBean difficultyBean;

    @Override
    protected int getContentViewId() {
        return R.layout.fra_capacity_zj;
    }

    @Override
    protected void initView() {
        et_number01 = bind(R.id.et_number01);
        et_number02 = bind(R.id.et_number02);
        et_number03 = bind(R.id.et_number03);
        tv_selected = bind(R.id.tv_selected);
        tv_total_number = bind(R.id.tv_total_number);
        rl_class_selected = bind(R.id.rl_class_selected);
        tv_class_name = bind(R.id.tv_class_name);
        tv_01 = bind(R.id.tv_01);
        tv_02 = bind(R.id.tv_02);
        tv_03 = bind(R.id.tv_03);
        btn_next = bind(R.id.btn_next);

        String className = SPUtil.getString(ctx, "grade_name", "");
        gradeId = SPUtil.getString(ctx, "grade_id", "");
//        if (!TextUtils.isEmpty(className)) {
//            tv_class_name.setText(className);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 112 && resultCode == 112)) {
            getActivity().setResult(112);
            getActivity().finish();
        }
    }

    /**
     * 获取题目难度
     *
     * @param gradeId
     */
    private void getDifficultyData(String gradeId) {
        HttpParams params = new HttpParams();
        params.put("userId", TApplication.getInstant().getLoginInfo().getUser_id() + "");
        params.put("gradeId", gradeId);
        if (OnlineExerciseActivity.chooseSubject != null) {
            params.put("courseId", OnlineExerciseActivity.chooseSubject.getValue());
        }
        J.http().post(Urls.CAPACITY_ZJ_URL, ctx, params, new HttpCallback<Respond<DifficultyBean>>(ctx) {
            @Override
            public void success(Respond<DifficultyBean> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    difficultyBean = res.getData();
                    tv_total_number.setText("题库数量：" + difficultyBean.getSubjectTotal() + "题");
                    tv_01.setText("题/" + difficultyBean.getOneStar() + "题");
                    tv_02.setText("题/" + difficultyBean.getTwoStar() + "题");
                    tv_03.setText("题/" + difficultyBean.getThreeStar() + "题");
                }
            }
        });
    }

    @Override
    protected void initData() {

        et_number01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s.length() <= 0) {
                        number01 = 0;
                        tv_selected.setText("已选：" + ((number01 + number02 + number03)) + "题");
                    } else {
                        number01 = Integer.parseInt(s.toString().trim());
                        tv_selected.setText("已选：" + ((number01 + number02 + number03)) + "题");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_number02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s.length() <= 0) {
                        number02 = 0;
                        tv_selected.setText("已选：" + ((number01 + number02 + number03)) + "题");
                    } else {
                        number02 = Integer.parseInt(s.toString().trim());
                        tv_selected.setText("已选：" + (number01 + number02 + number03) + "题");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_number03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s.length() <= 0) {
                        number03 = 0;
                        tv_selected.setText("已选：" + ((number01 + number02 + number03)) + "题");
                    } else {
                        number03 = Integer.parseInt(s.toString().trim());
                        tv_selected.setText("已选：" + (number01 + number02 + number03) + "题");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initEvent() {
        btn_next.setOnClickListener(this);
        rl_class_selected.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        difficulty01 = et_number01.getText().toString();
        difficulty02 = et_number02.getText().toString();
        difficulty03 = et_number03.getText().toString();
        if (v == btn_next) {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            if (OnlineExerciseActivity.chooseSubject == null) {
                showToast("请选择科目");
                return;
            }
            if (tv_class_name.getText().length() == 0 && tv_class_name.getText().toString().isEmpty()) {
                showToast("请选择年级");
                return;
            }
            if (difficulty01.isEmpty() && difficulty02.isEmpty() && difficulty03.isEmpty()) {
                showToast("请选择题数");
                return;
            }
            if (difficultyBean != null) {
                if (number01 > difficultyBean.getOneStar() || number02 > difficultyBean.getTwoStar() || number03 > difficultyBean.getThreeStar()) {
                    showToast("题目超出");
                    return;
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("difficulty01", difficulty01);
            map.put("difficulty02", difficulty02);
            map.put("difficulty03", difficulty03);
            map.put("gradeId", gradeId);
            map.put("number", number01 + number02 + number03);
            if (OnlineExerciseActivity.chooseSubject != null) {
                map.put("courseId", OnlineExerciseActivity.chooseSubject.getValue() + "");
            }
            gotoActivityResult(ZjDetailsActivity.class, map, 112);

        } else if (v.getId() == R.id.rl_class_selected) {
            getClassName();
        }
    }

    /**
     * 获取年级
     */
    private void getClassName() {
        HttpParams params = new HttpParams();
        params.put("type", "grade");
        J.http().post(Urls.ADD_CLASS_GRADE_URL, ctx, params, new HttpCallback<Respond<List<NameValue>>>(ctx, true, true) {
            @Override
            public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    List<NameValue> dataList = res.getData();
                    choose(dataList);
                }
            }
        });
    }

    private ChooseTypeDialog dialog;

    public void choose(final List<NameValue> nameValueList) {
        if (dialog == null) {
            dialog = new ChooseTypeDialog(ctx, nameValueList);
        }
        dialog.setChooseListener(new ChooseTypeDialog.ChooseListener() {
            @Override
            public void choose(NameValue nameValue) {
                tv_class_name.setText(nameValue.getName());
                if (nameValue.getName().equals("一年级")) {
                    gradeId = 1 + "";
                } else if (nameValue.getName().equals("二年级")) {
                    gradeId = 2 + "";
                } else if (nameValue.getName().equals("三年级")) {
                    gradeId = 3 + "";
                } else if (nameValue.getName().equals("四年级")) {
                    gradeId = 4 + "";
                } else if (nameValue.getName().equals("五年级")) {
                    gradeId = 5 + "";
                } else if (nameValue.getName().equals("六年级")) {
                    gradeId = 6 + "";
                } else if (nameValue.getName().equals("七年级")) {
                    gradeId = 7 + "";
                } else if (nameValue.getName().equals("八年级")) {
                    gradeId = 8 + "";
                } else if (nameValue.getName().equals("九年级")) {
                    gradeId = 9 + "";
                } else if (nameValue.getName().equals("十年级")) {
                    gradeId = 10 + "";
                } else if (nameValue.getName().equals("十一年级")) {
                    gradeId = 11 + "";
                } else if (nameValue.getName().equals("十二年级")) {
                    gradeId = 12 + "";
                }
                getDifficultyData(gradeId);
            }
        }).show();
    }
}
