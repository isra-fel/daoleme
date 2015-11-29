package cn.edu.fudan.daoleme.net;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * 与我们的服务器间的快递方面的通讯
 * Created by felix on 2015/11/29.
 */
public class DeliveryClient {
    /**
     * 我们服务器的根url
     */
    private static final String rootUrl = "http://10.0.2.2:9500/";

    /**
     * 列出我的所有快递
     * @param context
     * @param callback
     */
    public static void listDelivery(Context context, JsonHttpResponseHandler callback) {

    }

    /**
     * 新建快递
     * @param deliveryId 单号
     * @param company 公司
     * @param isPinned 是否被贴到锁屏上
     * @param isReceived 是否收到
     * @param context
     * @param callback
     */
    public static void createDelivery(String deliveryId, String company, boolean isPinned, boolean isReceived, Context context, JsonHttpResponseHandler callback) {

    }

    /**
     * 修改快递
     * @param isPinned
     * @param isReceived
     * @param context
     * @param callback
     */
    public static void editDelivery(boolean isPinned, boolean isReceived, Context context, JsonHttpResponseHandler callback) {

    }

    /**
     * 删除快递
     * @param deliveryId
     * @param context
     * @param callback
     */
    public static void deleteDelivery(String deliveryId, Context context, JsonHttpResponseHandler callback) {

    }
}
