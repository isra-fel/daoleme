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
    /**
     * 我们服务器的根url
     */
    private static final String rootUrl = "http://10.0.2.2:9500/";

    /**
     * 注册
     * @param username
     * @param password
     * @param email
     * @param context
     * @param callback
     */
    public static void signup(String username, String password, String email, Context context, JsonHttpResponseHandler callback) {
        JSONObject params = new JSONObject();
        try {
            params.put("username", username);
            params.put("password", password);
            params.put("email", email);
            StringEntity entity = new StringEntity(params.toString());
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(context, rootUrl + "/register", entity, "application/json", callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更改密码
     * @param password
     * @param context
     * @param callback
     */
    public static void updatePassword(String password, Context context, JsonHttpResponseHandler callback) {

    }

    /**
     * 登陆
     * @param username
     * @param password
     * @param callback
     */
    public static void signin(String username, String password, Context context, JsonHttpResponseHandler callback) {
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", password);
        client.post(rootUrl + "login", params, callback);
    }

    /**
     * 注销
     * @param context
     * @param callback
     */
    public static void signout(Context context, JsonHttpResponseHandler callback) {

    }

}