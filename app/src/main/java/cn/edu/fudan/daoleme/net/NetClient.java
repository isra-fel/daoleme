package cn.edu.fudan.daoleme.net;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * Base class for API calls.
 * Created by felix on 2015/11/25.
 */
public abstract class NetClient {
    protected static AsyncHttpClient client = new AsyncHttpClient();


    protected static StringEntity createStringEntity(JSONObject params) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(params.toString());
        entity.setContentType(new BasicHeader("Content-Type", "application/json"));
        return entity;
    }
}
