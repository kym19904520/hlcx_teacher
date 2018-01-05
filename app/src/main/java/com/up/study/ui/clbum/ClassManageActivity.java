package com.up.study.ui.clbum;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.widget.MyListView;
import com.up.study.adapter.GalleryAdapter;
import com.up.study.adapter.NorisukeAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.base.MyBaseAdapter;
import com.up.study.model.Book;
import com.up.study.model.ClassMessageBean;
import com.up.study.model.ClassTeacherMakeOverBean;
import com.up.study.model.MapEventBus;
import com.up.study.model.MessageEventBus;
import com.up.teacher.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.getContext;

/**
 * 班级管理
 * Created by kym on 2017/7/18.
 */

public class ClassManageActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.iv_class_portrait)
    ImageView iv_class_portrait;
    @Bind(R.id.rl_class_portrait)
    RelativeLayout rl_class_portrait;
    @Bind(R.id.tv_class_name)
    TextView tv_class_name;
    @Bind(R.id.tv_class_code)
    TextView tv_class_code;
    @Bind(R.id.tv_class_another_name)
    TextView tv_class_another_name;
    @Bind(R.id.rl_class_another_name)
    RelativeLayout rl_class_another_name;
    @Bind(R.id.rl_teacher_manage)
    RelativeLayout rl_teacher_manage;
    @Bind(R.id.rl_student_manage)
    RelativeLayout rl_student_manage;
    @Bind(R.id.rl_class_work)
    RelativeLayout rl_class_work;
    @Bind(R.id.lv_subject)
    MyListView lv_subject;
    /*  @Bind(R.id.tv_yw_name)
      TextView tvYwName;
      @Bind(R.id.rl_yw_jf)
      RelativeLayout rlYwJf;
      @Bind(R.id.tv_sx_name)
      TextView tvSxName;
      @Bind(R.id.rl_sx_jf)
      RelativeLayout rlSxJf;
      @Bind(R.id.tv_yy_name)
      TextView tvYyName;
      @Bind(R.id.rl_yy_jf)
      RelativeLayout rlYyJf;*/
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private String jfName;
    private String id;
    private List<Integer> listData;
    private String portrait;
    private String classId;
    private List<ClassTeacherMakeOverBean> dataList;
    private NorisukeAdapter norisukeAdapter;
    private View formerView = null;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_manage;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tv_title = bind(R.id.tv_title);
        iv_back = bind(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        classId = getIntent().getStringExtra("classId");
        showLog(classId + "管理获取的班级id");
    }

    @Override
    protected void initData() {
        tv_title.setText(R.string.class_manage);
        getClassTeacherData();
        getClassMessage();
        //注册
        EventBus.getDefault().register(ClassManageActivity.this);
        //班级头像
        listData = new ArrayList<>(Arrays.asList(R.mipmap.bj_t1, R.mipmap.bj_t2,
                R.mipmap.bj_t3, R.mipmap.bj_t4, R.mipmap.bj_t5,
                R.mipmap.bj_t6, R.mipmap.bj_t7, R.mipmap.bj_t8));
    }

    /**
     * 获取班级信息
     */
    private void getClassMessage() {
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        J.http().post(Urls.CLASS_MESSAGE_URL, ctx, params, new HttpCallback<Respond<ClassMessageBean>>(ctx) {
            @Override
            public void success(Respond<ClassMessageBean> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    ClassMessageBean messageBean = res.getData();
                    tv_class_name.setText(messageBean.getName());
                    tv_class_code.setText(messageBean.getInvite_code());
                    tv_class_another_name.setText(messageBean.getAlias());
                    if (messageBean.getHead() != null) {
                        switch (messageBean.getHead()) {
                            case "bj_t1":
                                iv_class_portrait.setImageResource(R.mipmap.bj_t1);
                                break;
                            case "bj_t2":
                                iv_class_portrait.setImageResource(R.mipmap.bj_t2);
                                break;
                            case "bj_t3":
                                iv_class_portrait.setImageResource(R.mipmap.bj_t3);
                                break;
                            case "bj_t4":
                                iv_class_portrait.setImageResource(R.mipmap.bj_t4);
                                break;
                            case "bj_t5":
                                iv_class_portrait.setImageResource(R.mipmap.bj_t5);
                                break;
                            case "bj_t6":
                                iv_class_portrait.setImageResource(R.mipmap.bj_t6);
                                break;
                            case "bj_t7":
                                iv_class_portrait.setImageResource(R.mipmap.bj_t7);
                                break;
                            case "bj_t8":
                                iv_class_portrait.setImageResource(R.mipmap.bj_t8);
                                break;
                        }
                    }
                    Map<String, String> mapData = messageBean.getCoursename();
                    Map<String, String> mapData1 = messageBean.getAuxiliary();
                    if (norisukeAdapter == null) {
                        norisukeAdapter = new NorisukeAdapter(ClassManageActivity.this, mapData, mapData1);
                    }
                    lv_subject.setAdapter(norisukeAdapter);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(ClassManageActivity.this);
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEventBus(MessageEventBus eventBus) {
        tv_class_another_name.setText(eventBus.message);
    }

    //接收教辅消息--------废弃（暂时不用）
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEnglishEventBus(MapEventBus eventBus) {
        Set set = eventBus.map.entrySet();
        Iterator iterator = set.iterator();
        Map.Entry<Integer, Book> entry = (Map.Entry) iterator.next();
        jfName = entry.getValue().getName().toString();
        for (Integer integer : eventBus.map.keySet()) {
            id = id + eventBus.map.get(integer).getId() + ",";
        }
        showToast("object" + id);
        id = "";
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(this);
        rl_class_portrait.setOnClickListener(this);
        rl_class_another_name.setOnClickListener(this);
        rl_teacher_manage.setOnClickListener(this);
        rl_student_manage.setOnClickListener(this);
        rl_class_work.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_class_portrait) {     //班级头像
            getHeadPortrait();
        } else if (v.getId() == R.id.rl_class_another_name) {      //班级别称
            gotoActivity(ClassAnotherNameAmendActivity.class, "cid", classId);
        } else if (v.getId() == R.id.rl_teacher_manage) {           //老师管理
            gotoActivity(ClassTeacherManageActivity.class, "cid", classId);
        } else if (v.getId() == R.id.rl_student_manage) {             //学生管理
            gotoActivity(ClassStudentManageActivity.class, "cid", classId);
        } else if (v.getId() == R.id.rl_class_work) {             //班主任转让
            if (dataList.size() <= 0) {
                showToast("该班级没有别的教师");
                return;
            }
            showTeacherList();
        }
    }

    /**
     * 班主任转让对话框
     */
    public void showTeacherList() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_xk_list, null);
        final ListView lv_teacher_select = (ListView) view.findViewById(R.id.lv_list);
        builder.setView(view);
        final SelectClassTeacherAdapter selectClassTeacherAdapter = new SelectClassTeacherAdapter(this, dataList);
        lv_teacher_select.setAdapter(selectClassTeacherAdapter);
        lv_teacher_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                affirmDialog(selectClassTeacherAdapter.mDataSets.get(position).getName(),
                        selectClassTeacherAdapter.mDataSets.get(position).getId());
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getClassTeacherData();
    }

    /**
     * 获取班主任转让--老师列表数据
     */
    public void getClassTeacherData() {
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        J.http().post(Urls.CLASS_TEACHER_MAKE_OVER_URL, ctx, params, new HttpCallback<Respond<List<ClassTeacherMakeOverBean>>>(ctx, true, true) {
            @Override
            public void success(Respond<List<ClassTeacherMakeOverBean>> res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    dataList = res.getData();
                }
            }
        });
    }

    /**
     * 班主任转让确定提示框
     *
     * @param teacherName
     * @param teacherId
     */
    private void affirmDialog(final String teacherName, final int teacherId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.class_matters_need_attention);
        builder.setMessage("班主任改为" + teacherName + "后，你将失去班级的管理权，是否确认？");
        builder.setPositiveButton(R.string.my_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submit(teacherId);
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.my_cancel, null).show();
    }

    /**
     * 提交班主任转让
     *
     * @param teacherId
     */
    private void submit(int teacherId) {
        HttpParams params = new HttpParams();
        params.put("uid", teacherId + "");
        params.put("cid", classId);
        J.http().post(Urls.SUBMIT_CLASS_TEACHER_URL, ctx, params, new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    showToast(res.getMsg() + "转让班主任");
                }
            }
        });
    }

    /**
     * 获取头像
     *
     * @return
     */
    private int index;

    public void getHeadPortrait() {
        builder = new AlertDialog.Builder(this);
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
                if (portrait == null || portrait.isEmpty()) {
                    return;
                }
                iv_class_portrait.setImageResource(listData.get(index));
                submitPortrait(portrait);
                portrait = "";
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 修改班级头像
     * @param portrait
     */
    private void submitPortrait(String portrait) {
        HttpParams params = new HttpParams();
        params.put("cid", classId);
        params.put("head", portrait);
        J.http().post(Urls.MODIFICATION_CLASS_MESSAGE_URL, ctx, params, new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {
                if (Respond.SUC.equals(res.getCode())) {
                    showToast("修改" + res.getMsg());
                }
            }
        });
    }

    private class SelectClassTeacherAdapter extends MyBaseAdapter<ClassTeacherMakeOverBean> {

        public SelectClassTeacherAdapter(Context context, List<ClassTeacherMakeOverBean> mDataSets) {
            super(context, mDataSets);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(ClassManageActivity.this, R.layout.item_teacher_select, null);
                viewHolder.tv_teacher_name = (TextView) convertView.findViewById(R.id.tv_teacher_name);
                viewHolder.tv_teacher_number = (TextView) convertView.findViewById(R.id.tv_teacher_number);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_teacher_name.setText(mDataSets.get(position).getName());
            viewHolder.tv_teacher_number.setText(mDataSets.get(position).getPhone());
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_teacher_name;
        TextView tv_teacher_number;
    }
}
