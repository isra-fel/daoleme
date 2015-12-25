package cn.edu.fudan.daoleme.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.pojo.Delivery;
import cn.edu.fudan.daoleme.data.constant.ExpressCompany;
import cn.edu.fudan.daoleme.net.DeliveryClient;
import cn.edu.fudan.daoleme.net.UserClient;
import cz.msebera.android.httpclient.Header;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        APIClient.query("yuantong", "700074134800", getApplicationContext(), new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                System.out.println(response.toString());
//            }
//        });
        UserClient.setRootUrl("http://192.168.1.116:8080/Daoleme");
        DeliveryClient.setRootUrl("http://192.168.1.116:8080/Daoleme");
    }

    public void onRegisterClick(View view) {
        UserClient.register("shabifeng", "shabifeng", "sha@bi.feng", TestActivity.this, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());
            }
        });
    }

    public void onLoginClick(View view) {
        UserClient.login("shabifeng", "shabifeng", TestActivity.this, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());
            }
        });
    }

    public void onLogoutClick(View view) {
        UserClient.logout(TestActivity.this, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());
            }
        });
    }

    public void onNewDeliveryClick(View view) {
        Delivery delivery = new Delivery(ExpressCompany.yuantong.getCompanyId(), "A09138121", "", false, false, null);
        DeliveryClient.favor(3, delivery, TestActivity.this, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());
            }
        });
    }

    public void onQueryDeliveryClick(View view) {
        DeliveryClient.listDelivery(TestActivity.this, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response.toString());
            }
        });
    }

}
