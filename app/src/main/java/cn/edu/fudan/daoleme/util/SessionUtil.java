package cn.edu.fudan.daoleme.util;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import cn.edu.fudan.daoleme.DLMApplication;
import cn.edu.fudan.daoleme.data.dao.Session;

/**
 * Created by rinnko on 2015/12/7.
 */
public class SessionUtil {

    public static Session getSession(Fragment fragment) {
        return ((DLMApplication)fragment.getActivity().getApplication()).getSession();
    }

    public static Session getSession(Activity activity) {
        return ((DLMApplication)activity.getApplication()).getSession();
    }

    public static Session getSession(Application application) {
        return ((DLMApplication)application).getSession();
    }
}
