package com.up.study;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.up.common.utils.SPUtil;
import com.up.study.adapter.FragmentAdapter;
import com.up.study.base.BaseFragmentActivity;
import com.up.study.ui.ClassFragment;
import com.up.study.ui.HomeFragment;
import com.up.study.ui.MyFragment;
import com.up.study.ui.login.LoginActivity;
import com.up.teacher.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {
    private com.up.study.weight.MyViewPager viewPager;
    private HomeFragment fra01;
    private ClassFragment fra02;
    private MyFragment fra03;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private BottomNavigationView navigation;
    private long exitTime = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0,false);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1,false);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2,false);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //获取登录的用户的id如果不为空，直接进入到主页面
        String userId = SPUtil.getString(this,"user_id","");
        if (TextUtils.isEmpty(userId)){
            gotoActivity(LoginActivity.class,null);
            finish();
        }
        viewPager = bind(R.id.viewpager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }

    @Override
    protected void initEvent() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void initData() {
        fra01 = new HomeFragment();
        fra02 = new ClassFragment();
        fra03 = new MyFragment();
        fragmentList.add(fra01);
        fragmentList.add(fra02);
        fragmentList.add(fra03);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onClick(View v) {}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {

            if((System.currentTimeMillis()-exitTime) > 2000)
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
