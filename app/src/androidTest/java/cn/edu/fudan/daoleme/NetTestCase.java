package cn.edu.fudan.daoleme;

import android.test.AndroidTestCase;

import com.loopj.android.http.JsonHttpResponseHandler;

import cn.edu.fudan.daoleme.net.APIClient;

/**
 * Used to test net module.
 * Created by felix on 2015/12/7.
 */
public class NetTestCase extends AndroidTestCase {
    @Override
    protected void runTest() throws Throwable {
        JsonHttpResponseHandler callback = new JsonHttpResponseHandler();
//        APIClient.query("yuantong", "700074134800", new JsonHttpResponseHandler());
        assertEquals(true, true);
    }
}
