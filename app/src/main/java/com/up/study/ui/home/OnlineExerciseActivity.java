package com.up.study.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.up.common.J;
import com.up.common.conf.Urls;
import com.up.common.http.HttpCallback;
import com.up.common.http.Respond;
import com.up.study.TApplication;
import com.up.study.adapter.FragmentAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.model.NameValue;
import com.up.study.params.Params;
import com.up.study.ui.home.online.CapacityTestPaperFragment;
import com.up.study.ui.home.online.ExerciseFragment;
import com.up.study.ui.home.online.KnowledgeFragment;
import com.up.study.ui.home.online.SectionFragment;
import com.up.study.weight.ChooseDialog;
import com.up.teacher.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * TODO:在线练习
 * Created by 王剑洪
 * On 2017/7/26.
 */

public class OnlineExerciseActivity extends BaseFragmentActivity {

//    private TabLayout tabLayout;
    private TextView tvSubject;

    private ViewPager viewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    private List<NameValue> list = new ArrayList<>();
    public static NameValue chooseSubject = null;


    @Override
    protected int getContentViewId() {
        return R.layout.act_online_exercise_viewpager;
    }

    @Override
    protected void initView() {
        //tabLayout = bind(R.id.tabs);
        viewPager =  bind(R.id.viewpager);

        tvSubject = bind(R.id.tv_subject);


        titleList.add("章节");
        titleList.add("知识点");
        titleList.add("练习卷");
        titleList.add("智能组卷");

        fragmentList.add(new SectionFragment());
        fragmentList.add(new KnowledgeFragment());
        fragmentList.add(new ExerciseFragment());
        fragmentList.add(new CapacityTestPaperFragment());

    }

    @Override
    protected void initData() {
        showLog("setClear----------------------11--------------");
        TApplication.getInstant().setSubIds("");
        TApplication.getInstant().setClear(true);

        initMagicIndicator();
        getSubject(true);

    }

    @Override
    protected void initEvent() {
        bind(R.id.ll_subject).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_subject:
                getSubject(false);
                break;
        }

    }

    private void initMagicIndicator() {

        /*for (int i = 0;i<majorModelList.size();i++){
            ErrorZSDJXFragment fra = new ErrorZSDJXFragment();
            Bundle bundle = new Bundle();
            bundle.putString("majorId",majorModelList.get(i).getCode());
            fra.setArguments(bundle);
            fragmentList.add(fra);
        }*/

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),fragmentList));

        //final String title[] = {"语文","数学","英语","体育","哲学"};
        //viewPager.setAdapter(new TopicPagerAdapter(mViews));
        viewPager.setOffscreenPageLimit(3);//限制存储在内存的页数
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        MagicIndicator magicIndicator =bind(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList == null ? 0 : fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorPrimary));
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setTextSize(15);

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index,false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(getResources().getColor(R.color.colorPrimary));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void changeTab(int index){
        showLog("切换tab index="+index);
        if (index==0){//章节tab
            TApplication.getInstant().setSection(true);
        }
        else {
            TApplication.getInstant().setSection(false);
        }
    }
    /*private void initTab() {


        for (int i = 0; i < 4; i++) {
            tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab));
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.tv_title).setSelected(true);//第一个tab被选中
                tab.getCustomView().findViewById(R.id.v).setVisibility(View.VISIBLE);//第一个tab被选中
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, fragmentList.get(0))
                        .commitAllowingStateLoss();
            }
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tv_title);
            textView.setText(titleList.get(i));//设置tab上的文字
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_title).setSelected(true);
                tab.getCustomView().findViewById(R.id.v).setVisibility(View.VISIBLE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, fragmentList.get(tab.getPosition()))
                        .commitAllowingStateLoss();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_title).setSelected(false);
                tab.getCustomView().findViewById(R.id.v).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }*/

    private void getSubject(final boolean isInit) {
        if (!list.isEmpty()) {
            new ChooseDialog(ctx, list).setChooseListener(new ChooseDialog.ChooseListener() {
                @Override
                public void choose(NameValue nameValue) {
                    chooseSubject = nameValue;
                    tvSubject.setText(nameValue.getName());
                }
            }).show();
            return;
        }
        J.http().post(Urls.GET_SUBJECT, ctx, Params.getSubject(null), new HttpCallback<Respond<List<NameValue>>>(isInit ? null : ctx) {
            @Override
            public void success(Respond<List<NameValue>> res, Call call, Response response, boolean isCache) {
//                res = TestUtils.getSubjectList();
                if (res.getData()!=null) {
                    list = res.getData();
                }
                if (!list.isEmpty()) {
                    chooseSubject = list.get(0);
                    tvSubject.setText(chooseSubject.getName());
                }
                if (!isInit) {
                    if (list.isEmpty()) {
                        showToast("该老师暂无科目");
                        return;
                    } else {
                        getSubject(false);
                    }
                }
            }
        });

    }
}
