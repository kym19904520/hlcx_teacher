package com.up.study.ui.clbum;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.adapter.ClassNorisukeAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.Book;
import com.up.study.model.MapDataList;
import com.up.teacher.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 语文教辅
 * Created by kym on 2017/7/19.
 */

public class ClassLanguageNorisukeActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.lv_class_jf)
    ListView lvClassJf;
    @Bind(R.id.tv_selected)
    TextView tvSelected;
    private ClassNorisukeAdapter adapter;
    private HashMap<Integer,Book> map = new HashMap<>();
    private String jf;
    private View formerView = null;

    @Override
    protected int getContentViewId() {
        return R.layout.act_class_norisuke;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ivBack.setVisibility(View.VISIBLE);
        tvSelected.setText("未选择");
        jf = getIntent().getStringExtra("majorcode");
        getJfData();
        final ArrayList list = new ArrayList<Book>();
        list.add(new Book(1,"测试教材1",0));
        list.add(new Book(2,"测试教材2",0));
        list.add(new Book(3,"测试教材3",0));
        list.add(new Book(4,"测试教材4",0));
        list.add(new Book(5,"测试教材5",0));
        list.add(new Book(6,"测试教材6",0));
        list.add(new Book(7,"测试教材7",0));
        list.add(new Book(8,"测试教材8",0));
        list.add(new Book(9,"测试教材9",0));
        list.add(new Book(10,"测试教材10",0));
        list.add(new Book(11,"测试教材11",0));
        list.add(new Book(12,"测试教材12",0));
        list.add(new Book(13,"测试教材13",0));
        list.add(new Book(14,"测试教材14",0));
        adapter = new ClassNorisukeAdapter(this, tvSelected, list);
        lvClassJf.setAdapter(adapter);
        lvClassJf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (formerView != null) {
                    formerView.setBackgroundResource(R.color.text_white);
                }
                view.setBackgroundResource(R.color.bg_blue_select);
                showToast(list.get(position).toString() + "www");
                tvSelected.setText("已选中");
                formerView = view;
            }
        });
//        tvSelected.setText(adapter.getMap().size() + "");
//        map = adapter.getMap();
    }

    /**
     * 获取教辅
     */
    private void getJfData() {
        String url = Urls.NORISUKE_URL + "?majorcode=" + jf;
        HttpParams params = new HttpParams();
        J.http().post(url, ctx, params, new HttpCallback<Respond>(ctx) {
            @Override
            public void success(Respond res, Call call, Response response, boolean isCache) {

            }
        });
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.class_language_jf);
    }

    @Override
    protected void initEvent() {}

    @Override
    public void onClick(View v) {}

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        EventBus.getDefault().post(new MapDataList(map));
        finish();
    }
}
