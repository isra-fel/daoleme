package cn.edu.fudan.daoleme;

import android.app.Application;
import android.content.Intent;

import cn.edu.fudan.daoleme.data.dao.Session;
import cn.edu.fudan.daoleme.module.HomeActivity;
import cn.edu.fudan.daoleme.module.LoginActivity;
import cn.edu.fudan.daoleme.net.DeliveryClient;
import cn.edu.fudan.daoleme.net.UserClient;
import cn.edu.fudan.daoleme.util.SessionUtil;
import cn.edu.fudan.daoleme.util.ToastUtil;

/**
 * Created by rinnko on 2015/11/13.
 */

public class DLMApplication extends Application {
    private static final String TAG = "DLMApplication";

    private Session mSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mSession = new Session(this);
        ToastUtil.registerContext(this);
//        navigate();
        setIP();
    }

    private void setIP() {
        UserClient.setRootUrl("http://192.168.1.116:8080/Daoleme");
        DeliveryClient.setRootUrl("http://192.168.1.116:8080/Daoleme");
    }

    public Session getSession() {
        return mSession;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ToastUtil.unregisterContext();
    }

    private void navigate() {
        Intent intent;
        if (mSession.isLogin()) {
            intent = new Intent(getApplicationContext(), HomeActivity.class);
            System.out.println("logged in");
        } else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            System.out.println("not logged in");
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}