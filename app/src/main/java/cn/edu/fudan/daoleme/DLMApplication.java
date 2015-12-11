package cn.edu.fudan.daoleme;

import android.app.Application;

import cn.edu.fudan.daoleme.data.dao.Session;
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
    }

    public Session getSession() {
        return mSession;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ToastUtil.unregisterContext();
    }
}