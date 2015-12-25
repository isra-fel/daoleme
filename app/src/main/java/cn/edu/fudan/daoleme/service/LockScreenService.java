package cn.edu.fudan.daoleme.service;

/**
 * Created by Ryan on 2015/11/24 0024.
 */
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import cn.edu.fudan.daoleme.module.LockScreenActivity;

public class LockScreenService extends Service {

    private static String TAG = "LockScreenService";
    private Intent LockIntent = null ;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onCreate(){
        super.onCreate();


        LockIntent = new Intent(LockScreenService.this , LockScreenActivity.class);
        LockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		/*注册广播*/
        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        LockScreenService.this.registerReceiver(mScreenOnReceiver, mScreenOnFilter);

		/*注册广播*/
        IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        LockScreenService.this.registerReceiver(mScreenOffReceiver, mScreenOffFilter);
    }

    public int onStartCommand(Intent intent , int flags , int startId){

        return Service.START_STICKY;

    }

    public void onDestroy(){
        super.onDestroy();
        LockScreenService.this.unregisterReceiver(mScreenOnReceiver);
        LockScreenService.this.unregisterReceiver(mScreenOffReceiver);
        //在此重新启动
        startService(new Intent(LockScreenService.this, LockScreenService.class));
    }

    private KeyguardManager mKeyguardManager = null ;
    private KeyguardManager.KeyguardLock mKeyguardLock = null ;
    //屏幕变亮的广播,我们要隐藏默认的锁屏界面
    private BroadcastReceiver mScreenOnReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context , Intent intent) {

            Log.i(TAG, intent.getAction());

            if(intent.getAction().equals("android.intent.action.SCREEN_ON")){
                Log.i(TAG, "----------------- android.intent.action.SCREEN_ON------");
                startActivity(LockIntent);
				mKeyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
				mKeyguardLock = mKeyguardManager.newKeyguardLock("Lock 1");
				mKeyguardLock.disableKeyguard();
            }
        }
    };

    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context , Intent intent) {

//            Log.i(TAG, intent.getAction());
//
            if(intent.getAction().equals("android.intent.action.SCREEN_OFF")){
//                Log.i(TAG, "----------------- android.intent.action.SCREEN_OFF------");
//                mKeyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
//                mKeyguardLock = mKeyguardManager.newKeyguardLock("Lock 1");
//                mKeyguardLock.disableKeyguard();
                startActivity(LockIntent);
            }
        }

    };
}
