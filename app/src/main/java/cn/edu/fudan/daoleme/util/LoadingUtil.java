package cn.edu.fudan.daoleme.util;

import android.app.Activity;
import android.content.Context;

import cn.edu.fudan.daoleme.module.dialog.LoadingFragment;

/**
 * Created by rinnko on 2015/12/9.
 */
public class LoadingUtil {

    public static void showLoading(Activity activity, int loadingMessageResId) {
        LoadingFragment loadingFragment = (LoadingFragment)activity.getFragmentManager().findFragmentByTag("loadingFragment");
        if (loadingFragment == null) {
            loadingFragment = new LoadingFragment();
        }
        loadingFragment.show(activity.getFragmentManager(), "loadingFragment");
        loadingFragment.setMessage(activity.getString(loadingMessageResId));
    }

    public static void showLoading(Activity activity, CharSequence loadingMessage) {
        LoadingFragment loadingFragment = (LoadingFragment)activity.getFragmentManager().findFragmentByTag("loadingFragment");
        if (loadingFragment == null) {
            loadingFragment = new LoadingFragment();
        }
        loadingFragment.show(activity.getFragmentManager(), "loadingFragment");
        loadingFragment.setMessage(loadingMessage);
    }

    public static void hideLoading(Activity activity) {
        LoadingFragment loadingFragment = (LoadingFragment)activity.getFragmentManager().findFragmentByTag("loadingFragment");
        if (loadingFragment.isAdded()) {
            loadingFragment.dismiss();
        }
    }

}
