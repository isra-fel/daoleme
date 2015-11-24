package cn.edu.fudan.daoleme.net;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.*;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * Created by felix on 2015/11/25.
 */
public class UserClient extends NetClient {
    private static final String rootUrl = "http://10.0.2.2:9500/";

    public static void register(String username, String password, String email, Context context, JsonHttpResponseHandler callback) {
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

    public static void login(String username, String password, JsonHttpResponseHandler callback) {
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", password);
        client.post(rootUrl + "login", params, callback);
    }


}