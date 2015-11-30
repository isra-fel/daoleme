package cn.edu.fudan.daoleme.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.adapter.HomePagerAdapter;
import cn.edu.fudan.daoleme.service.LockScreenService;

/**
 * Created by rinnko on 2015/11/9.
 */
public class HomeActivity extends AppCompatActivity implements
        View.OnClickListener,
        ViewPager.OnPageChangeListener {
    private static final String TAG = "HomeActivity";

    private ViewGroup mTabMail, mTabExpress, mTabMe;
    private ViewPager mPager;
    private int mCurrentTabIndex; // start from 0, 0->mTabMail, 1->mTabExpress, 2->mTabMe

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // inflate layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // setup toolbar for material design
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add listener to tabs
        mTabMail = (ViewGroup)findViewById(R.id.tab_receive);
        mTabExpress = (ViewGroup)findViewById(R.id.tab_send);
        mTabMe = (ViewGroup)findViewById(R.id.tab_me);
        mTabMail.setOnClickListener(this);
        mTabExpress.setOnClickListener(this);
        mTabMe.setOnClickListener(this);

        // set view pagers and pageChangeListener
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ReceiveFragment());
        fragments.add(new SendFragment());
        fragments.add(new MeFragment());
        mCurrentTabIndex = 0;
        FragmentPagerAdapter pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), fragments);
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(this);

        startService(new Intent(HomeActivity.this, LockScreenService.class));
    }

    private void onSwitchTab(int tabIndex) {
        Log.d(TAG, "onSwitchTab, " + tabIndex);
        if (mCurrentTabIndex == tabIndex) {
            return;
        }
        mCurrentTabIndex = tabIndex;
        // will trigger onPageSelected()
        mPager.setCurrentItem(tabIndex);
        // TODO update ui when tab switch
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_receive:
                onSwitchTab(0);
                break;
            case R.id.tab_send:
                onSwitchTab(1);
                break;
            case R.id.tab_me:
                onSwitchTab(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int i) {
        onSwitchTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

}
