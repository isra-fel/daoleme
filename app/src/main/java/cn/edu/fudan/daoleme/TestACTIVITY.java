package cn.edu.fudan.daoleme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.fudan.daoleme.net.UserClient;
import cz.msebera.android.httpclient.Header;

public class TestACTIVITY extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activity);
    }

    public void onPostClick(View view) {
        UserClient.register("felix", "123456", "a@b.com", getApplicationContext(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.getString("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
