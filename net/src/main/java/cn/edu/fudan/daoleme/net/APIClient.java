package cn.edu.fudan.daoleme.net;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * 调用别的API
 * Created by felix on 2015/11/29.
 */
public class APIClient {
    /**
     * 要调用的API的根url
     */
    private static final String rootUrl = "http://10.0.2.2:9500/";

    /**
     * 查询快递
     * @param company
     * @param deliveryId
     * @param context
     * @param callback
     */
    public static void query(String company, String deliveryId, Context context, JsonHttpResponseHandler callback) {

    }
}
