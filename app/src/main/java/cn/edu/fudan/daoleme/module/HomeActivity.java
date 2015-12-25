package cn.edu.fudan.daoleme.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.adapter.HomePagerAdapter;
import cn.edu.fudan.daoleme.data.dao.Session;
import cn.edu.fudan.daoleme.service.LockScreenService;
import cn.edu.fudan.daoleme.service.TimeRefreshService;
import cn.edu.fudan.daoleme.util.SessionUtil;

/**
 * Created by rinnko on 2015/11/9.
 */
public class HomeActivity extends AppCompatActivity implements
        View.OnClickListener,
        ViewPager.OnPageChangeListener {
    private static final String TAG = "HomeActivity";

    private static final int REQUEST_LOGIN  = 11;

    private ImageView mIconReceive, mIconSend, mIconMe;
    private TextView mTextReceive, mTextSend, mTextMe;
    private ViewPager mPager;
    private int mColorNormal, mColorActive;
    private int mIndexCurrentTab; // start from 0, 0->mTabReceive, 1->mTabSend, 2->mTabMe

    private static Context mContext = null ;
    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        checkLogin();

        // inflate layout
        super.onCreate(savedInstanceState);
        mContext = HomeActivity.this;
        setContentView(R.layout.activity_home);
        // setup toolbar for material design
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add listener for clickEvents
        ViewGroup mTabReceive = (ViewGroup)findViewById(R.id.tab_receive);
        mIconReceive = (ImageView)mTabReceive.findViewById(R.id.tab_receive_icon);
        mTextReceive = (TextView)mTabReceive.findViewById(R.id.tab_receive_title);
        ViewGroup mTabSend = (ViewGroup)findViewById(R.id.tab_send);
        mIconSend = (ImageView)mTabSend.findViewById(R.id.tab_send_icon);
        mTextSend = (TextView)mTabSend.findViewById(R.id.tab_send_title);
        ViewGroup mTabMe = (ViewGroup)findViewById(R.id.tab_me);
        mIconMe = (ImageView)mTabMe.findViewById(R.id.tab_me_icon);
        mTextMe = (TextView)mTabMe.findViewById(R.id.tab_me_title);
        mTabReceive.setOnClickListener(this);
        mTabSend.setOnClickListener(this);
        mTabMe.setOnClickListener(this);

        // set view pagers and pageChangeListener
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ReceiveFragment());
        fragments.add(new SendFragment());
        fragments.add(new MeFragment());
        mIndexCurrentTab = 0;
        FragmentPagerAdapter pagerAdapter = new HomePagerAdapter(getFragmentManager(), fragments);
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(this);

        mColorNormal = getResources().getColor(R.color.tab_color);
        mColorActive = getResources().getColor(R.color.tab_color_active);

        startService(new Intent(HomeActivity.this, LockScreenService.class));
        //TimeRefreshService.setPeriod(SessionUtil.getSession(HomeActivity.this).IsWallpaperNotifyOpen(),SessionUtil.getSession(HomeActivity.this).getPollFrequency()*60*1000);
    }

    private void checkLogin() {
        if (!SessionUtil.getSession(this).isLogin()) {
            Intent intent;
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, REQUEST_LOGIN);
        }
    }

    private void setCurrentTab(int tabIndex) {
        if (mIndexCurrentTab == tabIndex) {
            return;
        }
        // will trigger onPageSelected()
        mPager.setCurrentItem(tabIndex);
        // update ui when tab changed
        switch (mIndexCurrentTab) {
            case 0:
                mTextReceive.setTextColor(mColorNormal);
                mIconReceive.setImageResource(R.drawable.ic_action_mail);
                break;
            case 1:
                mTextSend.setTextColor(mColorNormal);
                mIconSend.setImageResource(R.drawable.ic_action_search);
                break;
            case 2:
                mTextMe.setTextColor(mColorNormal);
                mIconMe.setImageResource(R.drawable.ic_action_user);
                break;
        }
        switch (tabIndex) {
            case 0:
                mTextReceive.setTextColor(mColorActive);
                mIconReceive.setImageResource(R.drawable.ic_action_mail_active);
                break;
            case 1:
                mTextSend.setTextColor(mColorActive);
                mIconSend.setImageResource(R.drawable.ic_action_search_active);
                break;
            case 2:
                mTextMe.setTextColor(mColorActive);
                mIconMe.setImageResource(R.drawable.ic_action_user_active);
                break;
        }
        mIndexCurrentTab = tabIndex;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_receive:
                setCurrentTab(0);
                break;
            case R.id.tab_send:
                setCurrentTab(1);
                break;
            case R.id.tab_me:
                setCurrentTab(2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int i) {
        setCurrentTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

}
