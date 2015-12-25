package cn.edu.fudan.daoleme.net;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.*;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * 与我们的服务器间的用户方面的通讯
 * Created by felix on 2015/11/25.
 */
public class UserClient extends NetClient {
    public static void setRootUrl(String rootUrl) {
        UserClient.rootUrl = rootUrl;
    }

    /**
     * 我们服务器的根url
     */
    private static String rootUrl = "http://10.0.2.2:9500";

    /**
     * 注册
     * @param username
     * @param password
     * @param email
     * @param context
     * @param callback
     */
    public static void register(String username, String password, String email, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("username", username);
            params.put("password", password);
            params.put("email", email);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/Register", entity, "application/json", callback);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更改密码
     * @param password
     * @param context
     * @param callback
     */
    public static void changePassword(long userId,String password, String newPassword,Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("userId", userId);
            params.put("password", password);
            params.put("newPassword", newPassword);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/ChangePassword", entity, "application/json", callback);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @param callback
     */
    public static void login(String username, String password, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("username", username);
            params.put("password", password);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/Login", entity, "application/json", callback);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销
     * @param context
     * @param callback
     */
    public static void logout(Context context, JsonHttpResponseHandler callback) {
        client.get(context, rootUrl + "/Logout", callback);
    }

    public static void resetPassword(String email,Context context, JsonHttpResponseHandler callback){
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/ResetPassword", entity, "application/json", callback);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }
}