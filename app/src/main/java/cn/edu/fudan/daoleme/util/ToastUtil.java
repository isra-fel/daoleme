package cn.edu.fudan.daoleme.util;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by rinnko on 2015/12/9.
 */
public class ToastUtil {

    public static void toast(Fragment fragment, int messageResId) {
        Toast.makeText(fragment.getActivity().getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Activity activity, int messageResId) {
        Toast.makeText(activity.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context applicationContext, int messageResId) {
        Toast.makeText(applicationContext, messageResId, Toast.LENGTH_SHORT).show();
    }

}
