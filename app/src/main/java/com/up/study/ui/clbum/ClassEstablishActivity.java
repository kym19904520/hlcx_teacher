package com.up.study.ui.clbum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Constants;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.widget.MyListView;
import com.up.study.adapter.GalleryAdapter;
import com.up.study.adapter.SelectSubjectAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.base.MyBaseAdapter;
import com.up.study.model.ClassSubjectsBean;
import com.up.study.model.MessageEventBus;
import com.up.teacher.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.getContext;


/**
 * 创建班级
 * Created by kym on 2017/7/20.
 */

public class ClassEstablishActivity extends BaseFragmentActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_class_portrait)
    ImageView ivClassPortrait;
    @Bind(R.id.rl_class_portrait)
    RelativeLayout rlClassPortrait;
    @Bind(R.id.tv_class_name)
    TextView tvClassName;
    @Bind(R.id.rl_class_name)
    RelativeLayout rlClassName;
    @Bind(R.id.tv_class_another_name)
    TextView tvClassAnotherName;
    @Bind(R.id.rl_class_another_name)
    RelativeLayout rlClassAnotherName;
    @Bind(R.id.tv_class_subject)
    TextView tvClassSubject;
    @Bind(R.id.rl_select_subject)
    RelativeLayout rlSelectSubject;
    @Bind(R.id.tv_jf_name)
    TextView tvJfName;
    @Bind(R.id.tv_class_jf)
    TextView tvClassJf;
    @Bind(R.id.ll_select_jf)
    LinearLayout llSelectJf;
    @Bind(R.id.rl_select_jf)
    RelativeLayout rl_select_jf;
    @Bind(R.id.btn_establish_class)
    Button btnEstablishClass;
    @Bind(R.id.ll_replace)
    LinearLayout ll_replace;
    @Bind(R.id.rl_class_year)
    RelativeLayout rl_class_year;
    @Bind(R.id.tv_class_year)
    TextView tv_class_year;
    @Bind(R.id.my_list)
    MyListView my_list;

    private List<Integer> listData;
    private String subjectId;       //学科id
    private String portrait;        //班级头像
    private String className;       //班级名称
    private String gradeId;       //年级id
    private String classId;         //班级id
    private int year;               //学年
    private String classAnotherName;
    private List<ClassSubjectsBean> subjectsData;
    private List<String> list;
    private SelectSubjectAdapter subjectAdapter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_establish;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        //注册
        EventBus.getDefault().register(ClassEstablishActivity.this);
        Intent intent = getIntent();
        className = intent.getStringExtra("name");
        gradeId = intent.getStringExtra("grade");
        classId = intent.getStringExtra("class_type");
        showLog(className + "---" + classId + "---" + gradeId + "创建班级获取的数据");
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        tv_class_year.setText(year + "年");
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.class_add_class);
        tvClassName.setText(className);
        getSubjectsData();
        list = new ArrayList<>();
        for (int j = year - 5; j < year; j++) {
            list.add(j + "");
        }
        for (int i = year; i < year + 5; i++) {
            list.add(i + "");
        }

        listData = new ArrayList<>(Arrays.asList(R.mipmap.bj_t1, R.mipmap.bj_t2,
                R.mipmap.bj_t3, R.mipmap.bj_t4, R.mipmap.bj_t5,
                R.mipmap.bj_t6, R.mipmap.bj_t7, R.mipmap.bj_t8));
    }

    /**
     * 获取学科
     */
    private void getSubjectsData() {
        HttpParams params = new HttpParams();
        params.put("type", "major");
        J.http().post(Urls.ADD_CLASS_SUBJECTS_URL, ctx, params, new HttpCallback<Respond<List<ClassSubjectsBean>>>(ctx) {
            @Override
            public void success(Respond<List<ClassSubjectsBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    subjectsData = res.getData();
                }
            }
        });
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEventBus(MessageEventBus eventBus) {
        classAnotherName = eventBus.message;
        if (eventBus.message.length() > 10){
            tvClassAnotherName.setText(classAnotherName.substring(0,10)+"...");
        }else {
            tvClassAnotherName.setText(classAnotherName);
        }
    }

    @OnClick({R.id.iv_back, R.id.rl_class_portrait, R.id.rl_class_another_name,
            R.id.rl_select_subject, R.id.rl_select_jf, R.id.btn_establish_class, R.id.rl_class_year})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_class_portrait:       //班级头像
                getHeadPortrait();
                break;
            case R.id.rl_class_another_name:      //班级别称
                gotoActivity(ClassAnotherNameActivity.class, null);
                break;
            case R.id.rl_select_subject:        //选择学科
                showSubjectDialog();
                break;
            case R.id.rl_class_year:            //选择学年
                showClassYear();
                break;
            case R.id.btn_establish_class:          //创建班级按钮
                if (tv_class_year.getText() == null || tv_class_year.getText().toString().isEmpty()) {
                    showToast("请选择学年");
                    break;
                }
                if (tvClassSubject.getText() == null || tvClassSubject.getText().toString().isEmpty()) {
                    showToast("请选择学科");
                    break;
                }
                establishClass();
        }
    }

    /**
     * 获取学年
     */
    private int whichIndex = -1;

    private void showClassYear() {
        final String[] array = list.toArray(new String[list.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(array, whichIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_class_year.setText(array[which] + "年");
                year = Integer.parseInt(array[which]);
                whichIndex = which;
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 创建班级
     */
    List<Map.Entry<Integer, String>> mapList;
    public void establishClass() {
        String str = "";
        if (subjectAdapter.getMap().entrySet() != null) {
            mapList = new ArrayList<>(subjectAdapter.getMap().entrySet());
        }
        for (int i = 0;i < mapList.size();i++){
            str += mapList.get(i).getKey() + ",";
        }
        if (str.isEmpty() || str.length() <=0){
            showToast("请选择教辅");
            return;
        }
        if (portrait == null || portrait.isEmpty()) {
            portrait = "bj_t1";
        }
        HttpParams params = new HttpParams();
        params.put("name", className);
        params.put("grade", gradeId);
        params.put("class_type", classId);
        params.put("year", year + "");
        params.put("major_ids", subjectId.substring(0, subjectId.length() - 1));
        params.put("auxiliary_ids",str.substring(0,str.length()-1));
        showLog(str + "-------学科id");
        params.put("alias", classAnotherName);
        params.put("head", portrait);
        J.http().post(Urls.ADD_CLASS_URL, ctx, params, new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    showToast("创建" + res.getMsg());
                }
                Intent intent = new Intent();
                intent.setAction("finish");
                ClassEstablishActivity.this.sendBroadcast(intent);
                gotoActivity(AddClassActivity.class, null);

                Intent intent1 = new Intent();
                intent1.setAction(Constants.REFRESH_CLASS);
                ctx.sendBroadcast(intent1);   //发送广播
            }
        });
    }

    /**
     * 选择学科
     */
    private Map<String, String> stringMap = new HashMap<>();

    private void showSubjectDialog() {
        stringMap.clear();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_list, null);
        builder.setView(view);
        final ListView lv_list = (ListView) view.findViewById(R.id.lv_list);
        TextView tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
        tv_dialog_title.setText("请选择学科");
        TextView tv_off = (TextView) view.findViewById(R.id.tv_off);
        TextView tv_affirm = (TextView) view.findViewById(R.id.tv_affirm);
        LinearLayout ll_gone = (LinearLayout) view.findViewById(R.id.ll_gone);
        ll_gone.setVisibility(View.VISIBLE);
        final ClassEstablishAdapter2 adapter = new ClassEstablishAdapter2(this, subjectsData, llSelectJf);
        lv_list.setAdapter(adapter);
        tv_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tv_affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvClassSubject.setText("");
                subjectId = "";
                for (ClassSubjectsBean s : adapter.getSubjects()) {
                    if (s.getStatu() == 1) {
                        tvClassSubject.setText(tvClassSubject.getText() == null || tvClassSubject.getText().length() == 0 ? s.getName() : tvClassSubject.getText() + "," + s.getName());
                        subjectId += s.getCode() + ",";
                        stringMap.put(s.getName(), s.getCode());
                    }
                }
                //showLog(subjectId.substring(0,subjectId.length()-1) + "科目id");
                subjectAdapter = new SelectSubjectAdapter(ClassEstablishActivity.this, stringMap, gradeId, adapter.getSubjects());
                my_list.setAdapter(subjectAdapter);
                alertDialog.dismiss();
                subjectAdapter.getMap().clear();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 获取头像
     *
     * @return
     */
    private View formerView = null;
    private int index = 0;

    public void getHeadPortrait() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.dialog_tx, null);
        builder.setView(view);
        GridLayoutManager layout = new GridLayoutManager(getContext(), 4);
        RecyclerView rv_head_portrait = (RecyclerView) view.findViewById(R.id.rv_head_portrait);
        rv_head_portrait.setLayoutManager(layout);
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, listData);
        rv_head_portrait.setAdapter(galleryAdapter);
        galleryAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (formerView != null) {
                    formerView.setBackgroundResource(R.color.text_white);
                }
                view.setBackgroundResource(R.color.bg_blue_select);
                formerView = view;
                index = position;
                switch (index) {
                    case 0:
                        portrait = "bj_t1";
                        break;
                    case 1:
                        portrait = "bj_t2";
                        break;
                    case 2:
                        portrait = "bj_t3";
                        break;
                    case 3:
                        portrait = "bj_t4";
                        break;
                    case 4:
                        portrait = "bj_t5";
                        break;
                    case 5:
                        portrait = "bj_t6";
                        break;
                    case 6:
                        portrait = "bj_t7";
                        break;
                    case 7:
                        portrait = "bj_t8";
                        break;
                }
            }
        });
        TextView tv_left = (TextView) view.findViewById(R.id.tv_left);
        TextView tv_right = (TextView) view.findViewById(R.id.tv_right);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivClassPortrait.setImageResource(listData.get(index));
                if (portrait == null || portrait.isEmpty()) {
                    return;
                }
                alertDialog.dismiss();
                portrait = "";
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(ClassEstablishActivity.this);
    }

    public class ClassEstablishAdapter2 extends MyBaseAdapter<ClassSubjectsBean> {
        private HashMap<Integer, View> map = new HashMap<Integer, View>();

        public ClassEstablishAdapter2(Context context, List<ClassSubjectsBean> mDataSets, LinearLayout llSelectJf) {
            super(context, mDataSets);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            final TextView textView;
            final CheckBox checkBox;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.act_class_establish_xk_item, null);
                textView = (TextView) convertView.findViewById(R.id.tv_xk_name);
                checkBox = (CheckBox) convertView.findViewById(R.id.cb_xk);
                viewHolder.textView = textView;
                viewHolder.checkBox = checkBox;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                textView = viewHolder.textView;
                checkBox = viewHolder.checkBox;
            }

            final ClassSubjectsBean subject = mDataSets.get(position);
            textView.setText(subject.getName());

            checkBox.setChecked(mDataSets.get(position).getStatu() == 0 ? false : true);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        map.put(position, checkBox);
                        subject.setStatu(1);
                    } else {
                        subject.setStatu(0);
                        map.remove(position);
                    }
                }
            });
            return convertView;
        }

        public List<ClassSubjectsBean> getSubjects() {
            return mDataSets;
        }

        public class ViewHolder {
            TextView textView;
            CheckBox checkBox;
        }
    }
}
