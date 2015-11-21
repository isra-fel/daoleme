package cn.edu.fudan.daoleme.util;

import android.content.Context;

/**
 * Created by rinnko on 2015/11/15.
 */
public class DeviceUtil {

    // http://blog.csdn.net/arui319/article/details/6777133
    public static float pixelsPerDip(Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (scale + 0.5f) / 160;
    }

}
