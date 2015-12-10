package cn.edu.fudan.daoleme.net;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * 调用别的API
 * Created by felix on 2015/11/29.
 */
public class APIClient extends NetClient {
    /**
     * 要调用的API的根url
     */
    private static final String rootUrl = "http://www.kuaidi100.com";

    /**
     * 查询快递
     * 示例： http://www.kuaidi100.com/query?type=yuantong&postid=700074134800&id=1&valicode=&temp=0.7667662254534662
     * @param company 公司
     * @param deliveryId 单号
     * @param callback 回调
     */
    public static void query(String company, String deliveryId, JsonHttpResponseHandler callback) {
        client.get(rootUrl + "/query?type=" + company + "&postid=" + deliveryId, callback);
    }
}
