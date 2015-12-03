package cn.edu.fudan.daoleme.net;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 与我们的服务器间的快递方面的通讯
 * Created by felix on 2015/11/29.
 */
public class DeliveryClient extends NetClient {
    /**
     * 我们服务器的根url
     */
    private static final String rootUrl = "http://10.0.2.2:9500";

    /**
     * 列出我的所有快递
     * @param context -
     * @param callback -
     */
    public static void listDelivery(Context context, JsonHttpResponseHandler callback) {
        client.get(context, rootUrl + "/delivery", callback);
    }

    /**
     * 新建快递
     * @param deliveryId 单号
     * @param company 公司
     * @param alias 快递别名
     * @param isPinned 是否被贴到锁屏上
     * @param isReceived 是否收到
     * @param context -
     * @param callback -
     */
    public static void createDelivery(String deliveryId, String company,String alias, boolean isPinned, boolean isReceived, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("company", company);
            params.put("alias", alias);
            params.put("isPinned", isPinned);
            params.put("isReceived", isReceived);
            client.put(context, rootUrl + "/delivery/" + deliveryId, createStringEntity(params), "application/json", callback);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改快递
     * @param deliveryId -
     * @param alias optional
     * @param isPinned -
     * @param isReceived -
     * @param context -
     * @param callback -
     */
    public static void editDelivery(String deliveryId, String alias, boolean isPinned, boolean isReceived, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            if (!alias.trim().isEmpty()) {
                params.put("alias", alias);
            }
            params.put("isPinned", isPinned);
            params.put("isReceived", isReceived);
            client.put(context, rootUrl + "/delivery/" + deliveryId, createStringEntity(params), "application/json", callback);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除快递
     * @param deliveryId -
     * @param context -
     * @param callback -
     */
    public static void deleteDelivery(String deliveryId, Context context, JsonHttpResponseHandler callback) {
        client.delete(context, rootUrl + "/delivery/" + deliveryId, callback);
    }
}
