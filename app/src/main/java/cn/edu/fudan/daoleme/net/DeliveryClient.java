package cn.edu.fudan.daoleme.net;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.edu.fudan.daoleme.data.pojo.Delivery;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * 与我们的服务器间的快递方面的通讯
 * Created by felix on 2015/11/29.
 */
public class DeliveryClient extends NetClient {
    public static void setRootUrl(String rootUrl) {
        DeliveryClient.rootUrl = rootUrl;
    }

    /**
     * 我们服务器的根url
     */
    private static String rootUrl = "http://10.0.2.2:9500";

    /**
     * 列出我的所有快递
     * @param context
     * @param callback
     */
    public static void listDelivery(Context context, JsonHttpResponseHandler callback) {
            try {
                client.get(context, rootUrl + "/GetFavorites", callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * 添加喜欢的快递
     * @param userId
     * @param delivery
     * @param context
     * @param callback
     */
    public static void favor(long userId,Delivery delivery, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("userId",userId);
            params.put("deliveryId",delivery.getId());
            params.put("company", delivery.getExpressCompanyName());
            params.put("isPinned", delivery.isPinned());
            params.put("isReceived", delivery.isReceived());
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/Favor", entity, "application/json", callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改快递
     * @param isPinned
     * @param isReceived
     * @param context
     * @param callback
     */
    private static void editDelivery(boolean isPinned, boolean isReceived, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("isPinned", isPinned);
            params.put("isReceived", isReceived);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/Favor", entity, "application/json", callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param userId
     * @param deliveryId
     * @param isPinned
     * @param context
     * @param callback
     */
    public static void setPin(long userId,String[] deliveryId,boolean isPinned, Context context, JsonHttpResponseHandler callback){
        JSONObject params = new JSONObject();
        try {
            params.put("userId", userId);
            params.put("deliveryId", deliveryId);
            params.put("isPinned", isPinned);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/SetPin", entity, "application/json", callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param userId
     * @param deliveryId
     * @param context
     * @param callback
     */
    public static void receive(long userId,String[] deliveryId, Context context, JsonHttpResponseHandler callback){
        JSONObject params = new JSONObject();
        try {
            params.put("userId", userId);
            params.put("deliveryId", deliveryId);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/Receive", entity, "application/json", callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void deleteDelivery(long userId,String deliveryId, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("userId", userId);
            params.put("deliveryId", deliveryId);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/Delete", entity, "application/json", callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
