package cn.edu.fudan.daoleme.module;

/**
 * Created by Ryan on 2015/11/24 0024.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.adapter.DeliveryListAdapter2;
import cn.edu.fudan.daoleme.data.pojo.Delivery;


public class LockScreenActivity extends Activity {

    private static String TAG = "LockScreenAcitivity";
    private SliderRelativeLayout sliderLayout = null;
    private ImageView imgView_getup_arrow; // 动画图片
    private AnimationDrawable animArrowDrawable = null;
    private ListView mListView;
    public static int MSG_LOCK_SUCESS = 1;

    private static Context mContext = null ;
    public static Context getContext() {
        return mContext;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = LockScreenActivity.this;
		/*设置全屏，无标题*/
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lock_screen);
        initViews();
        sliderLayout.setMainHandler(mHandler);
    }

    private void initViews(){
        sliderLayout = (SliderRelativeLayout)findViewById(R.id.slider_layout);
        //获得动画，并开始转动
        imgView_getup_arrow = (ImageView)findViewById(R.id.getup_arrow);
        animArrowDrawable = (AnimationDrawable) imgView_getup_arrow.getBackground() ;
        mListView = (ListView)findViewById(R.id.delivery_list);
        ListAdapter adapter = new DeliveryListAdapter2(this, getData(), mListView);
        mListView.setAdapter(adapter);
    }

    private List<Delivery> getData() {
        List<Delivery> deliveryList = new ArrayList<>();
        for ( int i = 0; i < 100; i++) {
            Delivery delivery = new Delivery();
            delivery.setExpressCompanyName("Company");
            delivery.setTag("tag");
            delivery.setIsReceived(true);
            delivery.setIsPinned(true);
            ArrayList<String> state = new ArrayList<>();
            state.add("asdsadsadsd");
            delivery.setState(state);
            deliveryList.add(delivery);
        }
        return deliveryList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置动画
        mHandler.postDelayed(AnimationDrawableTask, 300);  //开始绘制动画
    }
    @Override
    protected void onPause() {
        super.onPause();
        animArrowDrawable.stop();
    }

    protected void onDestory(){
        super.onDestroy();
    }

    //通过延时控制当前绘制bitmap的位置坐标
    private Runnable AnimationDrawableTask = new Runnable(){
        public void run(){
            animArrowDrawable.start();
            mHandler.postDelayed(AnimationDrawableTask, 300);
        }
    };

    private Handler mHandler =new Handler (){
        public void handleMessage(Message msg){
            Log.i(TAG, "handleMessage :  #### " );
            if(MSG_LOCK_SUCESS == msg.what)
                finish(); // 锁屏成功时，结束我们的Activity界面
        }
    };

    //这个函数可以屏蔽Back键，Menu键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}