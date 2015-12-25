package cn.edu.fudan.daoleme.service;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import cn.edu.fudan.daoleme.module.LockScreenActivity;
import cn.edu.fudan.daoleme.util.LoadingUtil;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.module.HomeActivity;
import cn.edu.fudan.daoleme.net.APIClient;

public class TimeRefreshService extends Service {

    static Timer timer = null;
    static String ticker = "";
    static String contentText = "";
    static String contentTitle = "";
    //清除通知
    public static void cleanAllNotification() {
        NotificationManager mn= (NotificationManager)  LockScreenActivity.getContext().getSystemService(NOTIFICATION_SERVICE);
        mn.cancelAll();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    //添加通知
    public static void setPeriod(boolean isOpen,int period)
    {
        if (isOpen) {
            Intent intent = new Intent(HomeActivity.getContext(), TimeRefreshService.class);
            intent.putExtra("period", period);
            HomeActivity.getContext().startService(intent);
        }
        else{
            cleanAllNotification();
        }
    }

    public void onCreate() {
        Log.e("TimeRefreshService", "===========create=======");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public int onStartCommand(final Intent intent, int flags, int startId) {

        int period = intent.getIntExtra("period",0);
        if (null == timer ) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //APIClient.query("yuantong", "700074134800",
                APIClient.query("yuantong", "700074134800", getApplicationContext(),new JsonHttpResponseHandler("UTF-8") {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            //onQuerySuccess(response);
                            ticker="成功";
                            contentText=response.toString();
                            contentTitle="圆通";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                        //onQueryFail(throwable);
                        ticker="失败";
                        contentText=response.toString();
                        contentTitle="圆通";
                    }
                });

                NotificationManager mn = (NotificationManager) TimeRefreshService.this.getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(TimeRefreshService.this);
                Intent notificationIntent = new Intent(TimeRefreshService.this, HomeActivity.class);//点击跳转位置
                PendingIntent contentIntent = PendingIntent.getActivity(TimeRefreshService.this, 0, notificationIntent, 0);
                builder.setContentIntent(contentIntent);
                builder.setSmallIcon(R.drawable.icon);
                builder.setTicker(ticker); //测试通知栏标题
                builder.setContentText(contentText); //下拉通知啦内容
                builder.setContentTitle(contentTitle);//下拉通知栏标题
                builder.setAutoCancel(true);
                builder.setDefaults(Notification.DEFAULT_ALL);
                Notification notification = builder.build();

            }
        },0, period);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        Log.e("addNotification", "===========destroy=======");
        super.onDestroy();
        //在此重新启动
        startService(new Intent(TimeRefreshService.this, TimeRefreshService.class));
    }
}

