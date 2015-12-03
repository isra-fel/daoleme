package cn.edu.fudan.daoleme.net;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 与我们的服务器间的用户方面的通讯
 * Created by felix on 2015/11/25.
 */
public class UserClient extends NetClient {
    /**
     * 我们服务器的根url
     */
    private static final String rootUrl = "http://10.0.2.2:9500";

    /**
     * 注册
     * @param username -
     * @param password -
     * @param email -
     * @param context -
     * @param callback -
     */
    public static void signup(String username, String password, String email, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("username", username);
            params.put("password", password);
            params.put("email", email);
            client.post(context, rootUrl + "/user", createStringEntity(params), "application/json", callback);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更改密码
     * @param password -
     * @param context -
     * @param callback -
     */
    public static void updatePassword(String password, Context context, JsonHttpResponseHandler callback) {
        if (password.isEmpty()) {
            return;
        }
        JSONObject params = new JSONObject();
        try {
            params.put("password", password);
            client.put(context, rootUrl + "/user", createStringEntity(params), "application/json", callback);
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登陆
     * @param username -
     * @param password -
     * @param  context -
     * @param callback -
     */
    public static void signin(String username, String password, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("username", username);
            params.put("password", password);
            client.post(context, rootUrl + "/user/signin", createStringEntity(params), "application/json", callback);
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销
     * @param context -
     * @param callback -
     */
    public static void signout(Context context, JsonHttpResponseHandler callback) {
        client.get(context, rootUrl + "/user/signout", callback);
    }

}