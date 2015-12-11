package cn.edu.fudan.daoleme.util;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by rinnko on 2015/12/9.
 */
public class ToastUtil {

    private static Context mApplicationContext;

    public static void registerContext(Application application) {
        mApplicationContext = application.getApplicationContext();
    }

    public static void unregisterContext() {
        mApplicationContext = null;
    }

    public static void toast(int messageResId) {
        Toast.makeText(mApplicationContext, messageResId, Toast.LENGTH_SHORT).show();
    }

}
