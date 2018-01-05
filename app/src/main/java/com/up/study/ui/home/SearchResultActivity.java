package com.up.study.ui.home;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.common.utils.SPUtil;
import com.up.common.utils.StudyUtils;
import com.up.study.adapter.TopicPagerAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.db.CollectDao;
import com.up.study.db.ZJBean;
import com.up.study.model.TopicBean;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class SearchResultActivity extends BaseFragmentActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tv_next)
    TextView tvNext;
    @Bind(R.id.tv_num)
    TextView tv_num;

    private LayoutInflater mInflater;
    private String title,title01,typeName,typeName01;
    private List<View> mViews = new ArrayList<View>();
    private List<TopicBean.SubListBean> subListBeanList;
    private int topicId,paperId01;
    private CollectDao dao;

    @Override
    protected int getContentViewId() {
        return R.layout.act_search_result;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dao = new CollectDao(this);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.replace);
        Intent intent = getIntent();
        title01 = intent.getStringExtra("title");
        typeName01 = intent.getStringExtra("typeName");
        paperId01 = intent.getIntExtra("paperId",0);
        showLog(paperId01 + "相似题替换id" + title01 + typeName01);
    }

    @Override
    protected void initEvent() {}

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        HttpParams params = new HttpParams();
        params.put("userId", SPUtil.getString(SearchResultActivity.this,"user_id",""));
        params.put("subId",paperId01);
        J.http().post(Urls.REPLACE_TOPIC_URL, ctx, params, new HttpCallback<Respond<TopicBean>>(ctx) {
            @Override
            public void success(Respond<TopicBean> res, Call call, Response response, boolean isCache) {
                TopicBean topicBean = res.getData();
                if (topicBean != null) {
                    subListBeanList = topicBean.getSubList();
                    initSeqCardData(subListBeanList);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {}

    private void initSeqCardData(final List<TopicBean.SubListBean> subListBeanList) {
        mInflater = getLayoutInflater();
        for (int i = 0; i < subListBeanList.size(); i++) {
            View view = mInflater.inflate(R.layout.view_topic_kgt_list_1, null);
            StudyUtils.initSeqView(view,subListBeanList.get(i),this);
            mViews.add(view);
        }
        tv_num.setText("1/" + subListBeanList.size());
        topicId = subListBeanList.get(0).getId();
        title = subListBeanList.get(0).getTitle();
        typeName = subListBeanList.get(0).getSubjectTypeName();
        TopicPagerAdapter adapter = new TopicPagerAdapter(mViews);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mViews.size());//限制存储在内存的页数
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                topicId = subListBeanList.get(position).getId();
                title = subListBeanList.get(position).getTitle();
                typeName = subListBeanList.get(position).getSubjectTypeName();
                tv_num.setText((position+1) + "/" + subListBeanList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        showLog(topicId + "相似题id" + title);
        if (title == null && typeName == null){
            showToast("没有相似题");
            return;
        }
        boolean b = dao.update(title01,title,typeName01,typeName,paperId01,topicId);
        if (b = true){
            showToast("替换成功");
        }else {
            showToast("替换失败");
        }
        List<ZJBean> allList = dao.findAll();
        for (ZJBean zjBean : allList){
            showLog(zjBean.getPaperName() + "替换sql" + zjBean.getPaperId());
        }
        Intent intent = new Intent();
        intent.setAction("com.finish.zj");
        SearchResultActivity.this.sendBroadcast(intent);
        finish();
    }
}
